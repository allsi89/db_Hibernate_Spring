package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<T> {

    boolean persist(T entity) throws IllegalAccessException, SQLException;

    List<T> find() throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    List<T> find(String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    T findFirst() throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    T findFirst(String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    T findById(long id) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    boolean delete(String where) throws SQLException;
}
