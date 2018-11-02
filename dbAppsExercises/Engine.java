package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Engine implements  Runnable {

    private Connection connection;
    private BufferedReader reader;
    private int problemNum;
    

    public Engine(Connection connection, BufferedReader reader, int problemNum) {
        this.connection = connection;
        this.reader = reader;
        this.problemNum = problemNum;
    }


    public void run() {
        try {

            switch (this.problemNum){
                case 2:
                    System.out.println("You selected Problem 2. Get Villains’ Names.");
                    this.getVillainsNames();
                   break;
                case 3:
                    System.out.println("You selected Problem 3. Get Minion Names.");
                    this.getMinionNames();
                    break;
                case 4:
                    System.out.println("You selected Problem 4. Add Minion.");
                    this.addMinion();
                    break;
                case 5:
                    System.out.println("You selected Problem 5. Change Town Names Casing.");
                    this.changeTownNamesCasing();
                    break;
                case 6:
                    System.out.println("You selected Problem 6. *Remove Villain.");
                    this.removeVillain();
                    break;
                case 7:
                    System.out.println("You selected Problem 7. Print All Minion Names.");
                    this.printAllMinionNames();
                    break;
                case 8:
                    System.out.println("You selected Problem 8. Increase Minions Age.");
                    this.increaseMinionsAge();
                    break;
                case 9:
                    System.out.println("You selected Problem 9. Increase Age Stored Procedure.");
                    this.increaseAgeStoredProcedure();
                    break;
                default:
                    System.out.println("Invalid input! Please select an integer in range [2 - 9].");
                    break;

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    /**
     Problem -- 2.	Get Villains’ Names
     */
    private void getVillainsNames() throws SQLException {

        String query = "SELECT v.name, count(mv.minion_id) AS minions " +
                "FROM villains AS v JOIN minions_villains mv " +
                "ON v.id = mv.villain_id GROUP BY v.id " +
                "HAVING minions > ? ORDER BY minions DESC";

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(query);

        preparedStatement.setInt(1, 3);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(String.format("%s %d",
                    resultSet.getString("name"),
                    resultSet.getInt("minions")));
        }
    }


    /**
     Problem -- 3.	Get Minion Names
     */
    private void getMinionNames() throws SQLException, IOException {

        int villainId = Integer.parseInt(this.reader.readLine());
        String villainName = this.getVillainName(villainId);

        if (villainName == null) {
            System.out.println(String.format("No villain with ID %d exists in the database.", villainId));
            return;
        }
        String minions = this.getMinionsNameAndAge(villainId);

        System.out.println(String.format("Villain: %s", villainName ));
        System.out.println(minions);
    }

    private String getMinionsNameAndAge(int villainId) throws SQLException {
        String query = "SELECT m.name, m.age " +
                "FROM villains v " +
                "JOIN minions_villains mv " +
                "ON v.id = mv.villain_id " +
                "JOIN minions m " +
                "ON mv.minion_id = m.id " +
                "WHERE v.id = ?";

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(query);


        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()){
            return "<no minions>";
        }

        int count = 1;
        StringBuilder sb = new StringBuilder();

        while (true){
            sb.append(String.format("%d. %s %d", count, resultSet.getString(1), resultSet.getInt(2)));
            count++;
            if (!resultSet.next()){
                break;
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private String getVillainName(int villainId) throws SQLException {
        String query = "SELECT name FROM villains WHERE id = ?";

        PreparedStatement preparedStatement = this.connection
                .prepareStatement(query);


        preparedStatement.setInt(1, villainId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }
        return resultSet.getString(1);
    }


    /**
     Problem -- 4.	Add Minion
     */
    private void addMinion() throws IOException, SQLException {

        String[] mParams = this.reader.readLine().split("\\s+");
        String[] vParams = reader.readLine().split("\\s+");

        String mName = mParams[1];
        int mAge = Integer.parseInt(mParams[2]);
        String townName = mParams[3];

        String vName = vParams[1];

        if (!this.checkIfEntityExists(townName, "towns")){
            this.insertTown(townName);
            System.out.println(String.format("Town %s was added to the database.", townName));
        }

        if (!this.checkIfEntityExists(vName, "villains")){
            this.insertVillain(vName);
            System.out.println(String.format("Villain %s was added to the database.", vName));
        }

        int townId = this.getEntityId(townName, "towns");

        this.insertMinion(mName, mAge, townId);

        System.out.println(String.format("Successfully added %s to be minion of %s.", mName, vName));
    }

    private void insertTown(String name) throws SQLException {
        String query = String.format("INSERT INTO towns(name, country) VALUES('%s', NULL)", name);
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.execute();
    }

    private void insertVillain(String name) throws SQLException {
        String query = String.format("INSERT INTO villains(name, evilness_factor) VALUES('%s', 'evil')", name);
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.execute();
    }

    private void insertMinion(String name, int age, int townId) throws SQLException {

        String query = String.format("INSERT INTO minions(name, age, town_id) VALUES('%s', '%d', '%d')",
                name, age, townId);
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.execute();
    }

    private boolean checkIfEntityExists(String name, String tableName) throws SQLException {

        String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private int getEntityId(String name, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = '%s'", tableName, name);
        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }


    /*
    Problem -- 5. Change Town Names Casing
     */
    private void changeTownNamesCasing() throws IOException, SQLException {

        String country = this.reader.readLine();

        String query = String.format("UPDATE towns t SET t.name = UPPER(t.name) WHERE t.country = '%s'", country);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.execute();

        query = String.format("SELECT*FROM towns WHERE country = '%s'", country);

        preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> towns = new ArrayList<>();

        while (resultSet.next()){
            towns.add(resultSet.getString("name"));
        }

        if (towns.isEmpty()){
            System.out.println("No town names were affected.");
            return;
        }
        System.out.println(String.format("%d town names were affected.", towns.size()));
        System.out.println(towns);
    }


    /*
    Problem -- 6.  *Remove Villain
     */

    private void removeVillain() throws IOException, SQLException {
        int villainId = Integer.parseInt(this.reader.readLine());

        ResultSet resultSet = this.findRequestedVillain(villainId);

        if (!resultSet.next()){
            System.out.println("No such villain was found");
            return;
        }
        String villainName = resultSet.getString("name");

        int countOfMinions = this.getCountOfMinionsServingVillain(villainId);

        this.modifyFkConstraintsVillainsMinionsTable();

        this.deleteVillain(villainId);

        System.out.println(String.format("%s was deleted%n%d minions released", villainName, countOfMinions));

    }

    private ResultSet findRequestedVillain(int villainId) throws SQLException {
        String query = String.format("SELECT*FROM villains WHERE id = %d", villainId);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    private int getCountOfMinionsServingVillain(int villainId) throws SQLException {
        String query = String.format("SELECT count(*)FROM minions_villains WHERE villain_id = %d", villainId);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        return resultSet.getInt(1);
    }

    private void modifyFkConstraintsVillainsMinionsTable() throws SQLException {

        String query = "ALTER TABLE minions_villains " +
                "DROP FOREIGN KEY fk_villains_minions";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();

        query = "ALTER TABLE minions_villains " +
                "ADD CONSTRAINT fk_villains_minions " +
                "FOREIGN KEY (villain_id) " +
                "REFERENCES villains (id) ON DELETE CASCADE";

        preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();
    }

    private void deleteVillain(int villainId) throws SQLException {
        String query = String.format("DELETE v FROM villains v WHERE v.id = %d", villainId);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        preparedStatement.execute();
    }



    /*
    Problem -- 7.	Print All Minion Names
     */

    private void printAllMinionNames() throws SQLException {
        String query = "SELECT name FROM minions";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> minionNames = new ArrayList<>();

        while (resultSet.next()){
            minionNames.add(resultSet.getString("name"));
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;

        while (count < minionNames.size()/2){
            sb.append(minionNames.get(count))
                    .append(System.lineSeparator())
                    .append(minionNames.get(minionNames.size() - count - 1))
                    .append(System.lineSeparator());
            count++;
        }

        if (( minionNames.size()/2 + 1) % 2 == 0){
            sb.append(minionNames.get(minionNames.size()/2));
        }
        System.out.println(sb.toString().trim());
    }



    /*
    Problem -- 8.   Increase Minions Age
     */

    private void increaseMinionsAge() throws IOException, SQLException {

        int[] ids = Arrays.stream(this.reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();

        String query ="UPDATE minions " +
                "SET name = concat(UPPER(LEFT(name, 1)), substr(name, 2)), " +
                "age = age + 1 " +
                "WHERE id = ?";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        for (int id : ids) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }

        query = "SELECT name, age FROM minions";
        preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        StringBuilder sb = new StringBuilder();
        while (resultSet.next()){
            sb.append(resultSet.getString("name"))
                    .append(" ")
                    .append(resultSet.getInt("age"))
                    .append(System.lineSeparator());
        }

        System.out.println(sb.toString().trim());

    }



    /*
    Problem -- 9.	Increase Age Stored Procedure
     */

    private void increaseAgeStoredProcedure() throws IOException, SQLException {

        int id = Integer.parseInt(this.reader.readLine());
        String query = String.format("CALL usp_get_older(%d)", id);

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);
        try {
            preparedStatement.execute();
        }catch (Exception e){

            this.createProcedureIfNotExists();
            preparedStatement.execute();

        }

        query = String.format("SELECT name, age FROM minions WHERE id = %d", id);

        preparedStatement = this.connection.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        System.out.println(String.format("%s %d", resultSet.getString("name"), resultSet.getInt("age")));

    }

    private void createProcedureIfNotExists() throws SQLException {
        String query = "CREATE PROCEDURE usp_get_older (id INT (11))\n" +
                "  BEGIN\n" +
                "    START TRANSACTION;\n" +
                "    UPDATE minions m\n" +
                "        SET m.age = m.age + 1\n" +
                "    WHERE m.id = id;\n" +
                "    IF(SELECT count(m2.id)\n" +
                "      FROM minions m2\n" +
                "    WHERE m2.id = id ) > 0\n" +
                "      THEN commit;\n" +
                "      ELSE rollback;\n" +
                "      END IF;\n" +
                "  end;";

        PreparedStatement preparedStatement = this.connection.prepareStatement(query);

        preparedStatement.execute();
    }

}
