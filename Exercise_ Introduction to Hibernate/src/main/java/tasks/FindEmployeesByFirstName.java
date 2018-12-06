package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import java.io.IOException;

import static constants.Constants.*;

//TASK 12
public class FindEmployeesByFirstName {


    public static void execute(EntityManager em, Reader reader, Writer writer) throws IOException {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_12));

        writer.println(PATTERN_INPUT);

        String pattern = reader.readLine();

        StringBuilder sb = new StringBuilder();
        em.createQuery(TASK_12_QL_STRING, Employee.class)
                .setParameter("pattern" , pattern + "%")
                .getResultList()
                .forEach(e -> sb.append(String.format("%s %s - %s - ($%.2f)",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()))
                .append(System.lineSeparator())
                );
        writer.println(sb.toString().trim());

    }
}
