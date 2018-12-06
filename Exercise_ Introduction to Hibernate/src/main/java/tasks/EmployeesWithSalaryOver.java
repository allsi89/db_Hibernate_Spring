package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static constants.Constants.CHOSEN_TASK;
import static constants.Constants.TASK_4;
import static constants.Constants.TASK_4_QL_STRING;

//TASK 4
public class EmployeesWithSalaryOver {


    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_4));

        try{
            em
                    .createQuery(TASK_4_QL_STRING, Employee.class)
                    .getResultList()
                    .forEach(e->writer.println(e.getFirstName()));

        } catch (NoResultException e){
            e.printStackTrace();
        }

    }
}
