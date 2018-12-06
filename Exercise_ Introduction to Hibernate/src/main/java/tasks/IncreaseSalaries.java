package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import java.math.BigDecimal;

import static constants.Constants.*;

//TASK 10
public class IncreaseSalaries {

    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_10));

        em.getTransaction().begin();

        StringBuilder sb = new StringBuilder();
        em.createQuery(TASK_10_QL_STRING, Employee.class)
                .getResultList()
                .forEach(e->{
                    e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
                    sb.append(String.format("%s %s ($%.2f)",
                            e.getFirstName(), e.getLastName(), e.getSalary()))
                    .append(System.lineSeparator());
                        });

        writer.println(sb.toString().trim());
        em.getTransaction().commit();

    }
}
