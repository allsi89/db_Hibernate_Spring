package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.PrimaryKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static orm.constants.Constants.*;

public class EntityManager<T> implements DbContext<T> {

    private final Connection connection;
    private final Class<T> klass;

    public EntityManager(Connection connection, Class<T> klass) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.connection = connection;
        this.klass = klass;

        if (checksIfTableExists()) {
            this.updateTable();
        } else {
            this.createTable();
        }
    }

    public boolean persist(T entity) throws IllegalAccessException, SQLException {
        Field primaryKeyField = getPrimaryKeyField();
        primaryKeyField.setAccessible(true);
        long primaryKey = (long) primaryKeyField.get(entity);
        if (primaryKey > 0) {
            return update(entity);
        }

        return insert(entity);
    }

    private boolean insert(T entity) throws SQLException {
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        getColumnFields()
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        String columnName = field.getAnnotation(Column.class)
                                .name();
                        Object value = field.get(entity);
                        columns.add(columnName);
                        values.add(value);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        String columnsNames = String.join(", ", columns);
        String columnValues = values
                .stream()
                .map(value -> {
                    if (value instanceof String) {
                        return "\'" + value + "\'";
                    }

                    return value;
                })
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String queryString = MessageFormat.format(
                INSERT_QUERY_TEMPLATE,
                getTableName(),
                columnsNames,
                columnValues
        );

        return connection.prepareStatement(queryString)
                .execute();
    }

    private boolean update(T entity) throws SQLException, IllegalAccessException {

        List<String> updateQueries =
                getColumnFields().stream()
                        .map(field -> {
                            field.setAccessible(true);
                            try {
                                String columnName = field.getAnnotation(Column.class)
                                        .name();
                                Object value = field.get(entity);
                                if (value instanceof String) {
                                    value = "\'" + value + "\'";
                                }

                                return MessageFormat.format(
                                        SET_QUERY_TEMPLATE,
                                        columnName,
                                        value
                                );
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .collect(Collectors.toList());

        String updateQueriesString = String.join(", ", updateQueries);

        Field primaryKey = getPrimaryKeyField();
        primaryKey.setAccessible(true);
        String primaryKeyName =
                primaryKey.getAnnotation(PrimaryKey.class)
                        .name();

        long primaryKeyValue =
                (long) primaryKey
                        .get(entity);

        String queryString = MessageFormat.format(
                UPDATE_QUERY_TEMPLATE,
                getTableName(),
                updateQueriesString,
                primaryKeyName,
                primaryKeyValue
        );

        return connection.prepareStatement(queryString)
                .execute();
    }

    public List<T> find() throws SQLException, IllegalAccessException, InstantiationException {
        return find(null);
    }

    public T findFirst() throws SQLException, IllegalAccessException, InstantiationException {
        return find(SELECT_SINGLE_QUERY_TEMPLATE, null)
                .get(0);
    }

    public List<T> find(String where) throws SQLException, IllegalAccessException, InstantiationException {
        String queryTemplate = where == null
                ? SELECT_QUERY_TEMPLATE
                : SELECT_WHERE_QUERY_TEMPLATE;

        return find(queryTemplate, where);
    }

    public T findFirst(String where) throws SQLException, IllegalAccessException, InstantiationException {
        return find(SELECT_SINGLE_WHERE_QUERY_TEMPLATE, where)
                .get(0);
    }

    public T findById(long id) throws IllegalAccessException, SQLException, InstantiationException {
        String primaryKeyName =
                getPrimaryKeyField().getAnnotation(PrimaryKey.class)
                        .name();


        String whereString = MessageFormat.format(
                WHERE_PRIMARY_KEY,
                primaryKeyName,
                id
        );
        return findFirst(whereString);
    }

    private String getTableName() {
        Annotation annotation = Arrays.stream(klass.getAnnotations())
                .filter(a -> a.annotationType() == Entity.class)
                .findFirst()
                .orElse(null);

        if (annotation == null) {
            return klass.getSimpleName().toLowerCase() + "s";
        }

        return klass.getAnnotation(Entity.class)
                .name();
    }

    private List<T> find(String template, String where) throws SQLException, IllegalAccessException, InstantiationException {
        String queryString = MessageFormat.format(
                template,
                getTableName(),
                where
        );

        PreparedStatement query = connection.prepareStatement(queryString);
        ResultSet resultSet = query.executeQuery();

        return toList(resultSet);
    }

    private List<T> toList(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();

        while (resultSet.next()) {
            T entity = this.createEntity(resultSet);
            result.add(entity);
        }

        return result;
    }

    private T createEntity(ResultSet resultSet) throws IllegalAccessException, InstantiationException, SQLException {
        T entity = klass.newInstance();
        List<Field> columnFields = getColumnFields();

        Field primaryKeyField = getPrimaryKeyField();

        primaryKeyField.setAccessible(true);
        String primaryKeyColumnName = primaryKeyField.getAnnotation(PrimaryKey.class)
                .name();
        long primaryKeyValue = resultSet.getLong(primaryKeyColumnName);
        primaryKeyField.set(entity, primaryKeyValue);

        columnFields.forEach(field -> {
            String columnName = field.getAnnotation(Column.class)
                    .name();
            try {
                field.setAccessible(true);
                if (field.getType() == Long.class || field.getType() == long.class) {
                    long value = resultSet.getLong(columnName);
                    field.set(entity, value);
                } else if (field.getType() == String.class) {
                    String value = resultSet.getString(columnName);
                    field.set(entity, value);
                }
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return entity;
    }

    private List<Field> getColumnFields() {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    private Field getPrimaryKeyField() {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Class " + klass + " does not have a primary key annotation"));
    }

    private boolean checksIfTableExists() throws SQLException {
        String query = String
                .format(
                       CHECK_IF_TABLE_EXIST_QUERY,
                        this.getTableName()
                );

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    private void createTable() throws SQLException {
        Field primaryKeyField = this.getPrimaryKeyField();
        String primaryKeyColumnName = primaryKeyField.getDeclaredAnnotation(PrimaryKey.class).name();
        String primaryKeyColumnType = this.getColumnTypeString(primaryKeyField);

        String primaryKeyColumnDefinition = String
                .format(
                        PRIMARY_KEY_DEFINITION_QUERY,
                        primaryKeyColumnName,
                        primaryKeyColumnType
                );

        List<Field> columnsFields = this.getColumnFields();

        List<String> columnsParams = new ArrayList<>();

        columnsFields
                .stream()
                .forEach(field -> {
                    String columnName = field.getDeclaredAnnotation(Column.class).name();
                    String columnType = this.getColumnTypeString(field);

                    String columnDefinition = String
                            .format(
                                    "%s %s",
                                    columnName,
                                    columnType
                            );

                    columnsParams.add(columnDefinition);
                });

        String createStatementBody = String
                .format(
                        "%s, %s",
                        primaryKeyColumnDefinition,
                        columnsParams.stream().collect(Collectors.joining(", "))
                );

        String query = String
                .format(
                        CREATE_TABLE_QUERY,
                        this.getTableName(),
                        createStatementBody
                );

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.execute();
    }

    private void updateTable() throws SQLException {
        List<String> entityColumnsNames = this.getColumnFields()
                .stream()
                .map(field -> {
                    return field.getDeclaredAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
        entityColumnsNames.add(this.getPrimaryKeyField().getDeclaredAnnotation(PrimaryKey.class).name());

        List<String> databaseTableColumnsNames = this.getDatabaseTableColumnsNames();

        List<String> newColumnsNames = entityColumnsNames
                .stream()
                .filter(columnName -> !databaseTableColumnsNames.contains(columnName))
                .collect(Collectors.toList());

        List<Field> newFields = this.getColumnFields()
                .stream()
                .filter(field -> {
                    return newColumnsNames.contains(field.getDeclaredAnnotation(Column.class).name());
                })
                .collect(Collectors.toList());

        List<String> columnsDefinitions = new ArrayList<>();

        newFields.stream().forEach(field -> {
            String columnDefinition = String
                    .format(
                            ADD_COLUMN_QUERY,
                            field.getDeclaredAnnotation(Column.class).name(),
                            this.getColumnTypeString(field)
                    );

            columnsDefinitions.add(columnDefinition);
        });

        String queryBody = String.join(", ", columnsDefinitions);

        String query = String
                .format(
                        ALTER_TABLE_QUERY,
                        this.getTableName(),
                        queryBody
                );

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.execute();
    }

    private String getColumnTypeString(Field field) {
        if (field.getType() == long.class || field.getType() == Long.class || field.getType() == int.class) {
            return INT_STR;
        } else if (field.getType() == String.class) {
            return VARCHAR_STR;
        }

        return null;
    }

    private List<String> getDatabaseTableColumnsNames() throws SQLException {
        String query = String
                .format(
                        GET_COLUMN_NAMES_FROM_TABLE_TEMPLATE,
                        this.getTableName()
                );

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> columnsNames = new ArrayList<>();

        while (resultSet.next()) {
            columnsNames.add(resultSet.getString(1));
        }

        return columnsNames;
    }

    @Override
    public boolean delete(String where) throws SQLException {
        String query = String.format(
                DELETE_TEMPLATE,
                this.getTableName(),
                where
        );

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        return preparedStatement.execute();
    }
}
