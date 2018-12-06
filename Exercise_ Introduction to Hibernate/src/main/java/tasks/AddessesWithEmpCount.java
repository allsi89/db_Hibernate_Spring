package tasks;

import entities.Address;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import static constants.Constants.*;

// TASK 7
public class AddessesWithEmpCount {

    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_7));

        em.createQuery(TASK_7_QL_STRING,
                Address.class)
                .setMaxResults(TEN)
                .getResultList()
                .forEach(a->writer.println(String.format("%s, %s - %d employees",
                        a.getText(), a.getTown().getName(), a.getEmployees().size())));

    }
}
