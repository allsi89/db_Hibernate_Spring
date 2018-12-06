package orm.constants;

public class Constants {
    public static final String GET_COLUMN_NAMES_FROM_TABLE_TEMPLATE = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'";
    public static final String DELETE_TEMPLATE = "DELETE FROM %s WHERE %s;";

    public static final String SELECT_QUERY_TEMPLATE = "SELECT * FROM {0}";
    public static final String SELECT_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1}";
    public static final String SELECT_SINGLE_QUERY_TEMPLATE = "SELECT * FROM {0} LIMIT 1";
    public static final String SELECT_SINGLE_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1} LIMIT 1";
    public static final String INSERT_QUERY_TEMPLATE = "INSERT INTO {0}({1}) VALUES({2})";
    public static final String UPDATE_QUERY_TEMPLATE = "UPDATE {0} SET {1} WHERE {2}={3}";

    public static final String SET_QUERY_TEMPLATE = "{0}={1}";
    public static final String WHERE_PRIMARY_KEY = " {0}={1} ";
    public static final String CHECK_IF_TABLE_EXIST_QUERY =  "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'";
    public static final String PRIMARY_KEY_DEFINITION_QUERY = "%s %s PRIMARY KEY AUTO_INCREMENT";
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE %s(%s)";
    public static final String ADD_COLUMN_QUERY = "ADD COLUMN %s %s";
    public static final String ALTER_TABLE_QUERY = "ALTER TABLE %s %s";
    public static final String INT_STR = "INT";
    public static final String VARCHAR_STR = "VARCHAR(255)";
}
