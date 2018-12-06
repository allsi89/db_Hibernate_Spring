package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import java.util.Comparator;

import static constants.Constants.*;

//TASK 13
public class EmployeesMaximumSalaries {


    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_13));

        em.createQuery(TASK_13_QL_STRING, Employee.class)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(emp -> emp.getDepartment().getId()))
                .forEach(e -> writer.println(e.getDepartment().getName() + " - " + e.getSalary()));


    }
}
