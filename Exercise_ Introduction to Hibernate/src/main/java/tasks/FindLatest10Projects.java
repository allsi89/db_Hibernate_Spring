package tasks;

import entities.Project;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import java.util.Comparator;

import static constants.Constants.*;

// TASK 9
public class FindLatest10Projects {


    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_9));

        StringBuilder sb = new StringBuilder();
        em.createQuery(TASK_9_QL_STRING, Project.class)
                .setMaxResults(10)
                .getResultList()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p ->
                        sb.append(PROJECT_NAME_STR).append(p.getName())
                                .append(System.lineSeparator())
                        .append(leftPad()).append(PROJECT_DESCR_STR).append(p.getDescription()).append(System.lineSeparator())
                        .append(leftPad()).append(PROJECT_START_DATE_STR).append(p.getStartDate()).append(System.lineSeparator())
                        .append(leftPad()).append(PROJECT_END_DATE_STR).append(p.getEndDate()).append(System.lineSeparator())
                        );

        writer.println(sb.toString().trim());

    }

    private static String leftPad() {
        return String.format("%8s", " ");
    }
}
