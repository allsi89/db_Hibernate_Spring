package core;

import io.*;
import io.consoleIO.ConsoleReader;
import io.consoleIO.ConsoleWriter;
import tasks.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static constants.Constants.*;

public class Engine implements Runnable {


    private Reader reader;
    private Writer writer;
    private EntityManagerFactory factory;
    private EntityManager em;

    public Engine() {
        reader = new ConsoleReader();
        writer = new ConsoleWriter();
        factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        em = factory.createEntityManager();
    }

    @Override
    public void run() {
        String input = null;
        writer.println(CHOOSE_TASK_STR);
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int taskNum = ZERO;
        try {
            taskNum = Integer.parseInt(input);
            selectTask(taskNum);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        reader.close();
        em.close();
        factory.close();


    }

    private void selectTask(int taskNum) throws IOException {
        switch (taskNum) {
            case 2:
                RemoveObjects.execute(em, reader, writer);
                break;
            case 3:
                ContainsEmployee.execute(em, reader, writer);
                break;
            case 4:
                EmployeesWithSalaryOver.execute(em, reader, writer);
                break;
            case 5:
                EmployeesFromDepartment.execute(em, reader, writer);
                break;
            case 6:
                AddingANewAddressAndUpdatingEmp.execute(em, reader, writer);
                break;
            case 7:
                AddessesWithEmpCount.execute(em, reader, writer);
                break;
            case 8:
                GetEmployeeWithProject.execute(em, reader, writer);
                break;
            case 9:
                FindLatest10Projects.execute(em, reader, writer);
                break;
            case 10:
                IncreaseSalaries.execute(em, reader, writer);
                break;
            case 11:
                RemoveTowns.execute(em, reader, writer);
                break;
            case 12:
                FindEmployeesByFirstName.execute(em, reader, writer);
                break;
            case 13:
                EmployeesMaximumSalaries.execute(em, reader, writer);
                break;
            default:
                writer.println(INVALID_INPUT_MSG);
                break;
        }

    }


}
