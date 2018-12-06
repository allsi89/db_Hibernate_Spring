package constants;

public class Constants {

    public static final String DATABASE_NAME = "soft_uni";
    public static final String CHOOSE_TASK_STR = "Enter task number from 2 to 13 inclusive.";
    public static final String INVALID_INPUT_MSG = "Invalid Input!";
    public static final String ENTER_EMP_NAME = "Enter employee name:";
    public static final String CHOSEN_TASK = "You selected problem";
    public static final String TASK_2 = "2. Remove Objects";
    public static final String TASK_3 = "3. Contains Employee";
    public static final String TASK_4 = "4. Employees with Salary Over 50 000";
    public static final String TASK_5 = "5. Employees from Department";
    public static final String TASK_6 = "6. Adding a New Address and Updating Employee";
    public static final String TASK_7 = "7. Addresses with Employee Count";
    public static final String TASK_8 = "8. Get Employee with Project";
    public static final String TASK_9 = "9. Find Latest 10 Projects";
    public static final String TASK_10 = "10. Increase Salaries";
    public static final String TASK_11 = "11. Remove Towns";
    public static final String TASK_12 = "12. Find Employees by First Name";
    public static final String TASK_13 = "13. Employees Maximum Salaries";

    public static final int ZERO = 0;
    public static final int TEN = 10;

    //QUERY STRINGS
    public static final String TASK_3_QL_STRING = "FROM Employee WHERE concat(first_name, ' ', last_name) = :name";

    public static final String TASK_2_QL_STRING = "FROM Town";

    public static final String TASK_4_QL_STRING = "FROM Employee WHERE salary > 50000";

    public static final String TASK_5_QL_STRING =
            "FROM Employee AS e " +
            "WHERE e.department.name = 'Research and Development' " +
            "ORDER BY e.salary, e.id";

    public static final String TASK_6_QL_EMPLOYEE_STR = "From Employee WHERE lastname = :name";
    public static final String TASK_5_QL_TOWN_SELECT_STR= "FROM Town WHERE name = 'Sofia'";
    public static final String ADDRESS_TEXT = "Vitoshka 15";
    public static final String ERR_NO_EMPLOYEE = "No such employee found.";
    public static final String TOWN_NAME = "Sofia";

    public static final String TASK_7_QL_STRING = "FROM Address AS a ORDER BY a.employees.size DESC, a.town.id ASC";

    public static final String TASK_8_QL_STRING = "FROM Employee WHERE id = :id";
    public static final String ERR_ID = "Invalid id!" ;

    public static final String PROJECT_NAME_STR = "Project name: ";
    public static final String TASK_9_QL_STRING = "FROM Project ORDER BY startDate DESC";
    public static final String PROJECT_DESCR_STR = "Project Description: ";
    public static final String PROJECT_START_DATE_STR = "Project Start Date: ";
    public static final String PROJECT_END_DATE_STR = "Project End Date: ";

    public static final String TASK_10_QL_STRING = "FROM Employee AS e " +
            "WHERE e.department.name IN ('Engeneering', 'Tool Design', 'Marketing', 'Information Services')" +
            "ORDER BY e.id";
    public static final String REQUIRED_TOWN_NAME = "Enter town name: ";
    public static final String TASK_11_QL_TOWN_QUERY = "FROM Town WHERE name = :name";
    public static final String NO_TOWN_MSG = "No such town found.";
    public static String TASK_11_QL_ADDRESS_QUERY = "FROM Address WHERE town.name = :name";

    public static final String PATTERN_INPUT = "Enter pattern: ";
    public static final String TASK_12_QL_STRING = "FROM Employee WHERE firstName LIKE :pattern";

    public static final String TASK_13_QL_STRING =
            "FROM Employee WHERE salary NOT BETWEEN 30000 AND 70000" +
                    "GROUP BY department.name ORDER BY salary DESC";


}
