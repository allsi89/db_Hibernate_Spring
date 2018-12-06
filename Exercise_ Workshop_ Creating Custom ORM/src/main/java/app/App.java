package app;

import entities.Employee;
import entities.User;
import orm.Connector;
import orm.DbContext;
import orm.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class App {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    public static void main(String[] args) throws SQLException, IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = getConnection(reader);

        User user = new User("username15", 32);
        DbContext<User> userDbContext = getDbContext(connection, User.class);
        //add
        userDbContext.persist(user);
        //find
        userDbContext.find();

        //add 1 more
       userDbContext.persist(new User("username18",
                22));

        //update
       userDbContext.persist(user);

        //findFirst
        userDbContext.findFirst();

        //findWhere
        userDbContext.find("id = 1");

        Employee employee = new Employee("Martin", "Petkov",
                23, 1250);
        DbContext<Employee> employeeDbContext = getDbContext(connection,
                Employee.class);

        employeeDbContext.persist(employee);
        employeeDbContext.persist(employee);

        //Change id number if there isn't the same id in your table
        employeeDbContext.delete("id = 1");

        System.out.println("You can check your data in the created tables.");



    }

    private static<T> DbContext<T> getDbContext(Connection connection, Class<T> klass) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return new EntityManager<>(connection, klass);
    }

    private static Connection getConnection(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Username for MySQL:");
        String user = reader.readLine();
        System.out.println("Password for MySQL:");
        String password = reader.readLine();
        System.out.println("Name of the database:");
        String dbName = reader.readLine();
        Connector.createConnection(user, password, dbName);
        return Connector.getConnection();
    }
}
