package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static constants.Constants.*;

//TASK 5
public class EmployeesFromDepartment {
    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_5));
        try{
            em
                    .createQuery(TASK_5_QL_STRING, Employee.class)
                    .getResultList()
                    .forEach(e-> writer.println(String.format("%s %s from %s - $%.2f",
                            e.getFirstName(), e.getLastName(),
                            e.getDepartment().getName(), e.getSalary())));
        } catch (NoResultException e){
            e.printStackTrace();
        }
    }
}
