package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {
    public static void main(String[] args) throws SQLException, IOException {

        /*
        See Engine class for detailed solutions to the problems from Database Apps Exercises.
        Please change String user and password accordingly.
         */

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String user = "root";
        String password = "1001";

        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db?serverTimezone=UTC",
                        properties);



        System.out.println("Select a number from 2 to 9 for each corresponding task.");

        int problemNum;
        try{
            problemNum = Integer.parseInt(reader.readLine());

        } catch (NumberFormatException e){
            System.out.println("Invalid input! You must choose an integer in range [2-9].");
            return;
        }

        Engine engine = new Engine(connection, reader, problemNum);
        engine.run();
        //reader.close();
       // connection.close();

    }
}
