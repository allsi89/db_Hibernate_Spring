package tasks;

import entities.Employee;
import entities.Project;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.io.IOException;
import java.util.Comparator;

import static constants.Constants.*;

public class GetEmployeeWithProject {

    public static void execute(EntityManager em, Reader reader, Writer writer) throws IOException {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_8));
        int id = ZERO;
        try{
            id = Integer.parseInt(reader.readLine());
            Employee employee = em.createQuery(TASK_8_QL_STRING, Employee.class)
                    .setParameter("id", id)
                    .getSingleResult();

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%s %s - %s",
                    employee.getFirstName(), employee.getLastName(), employee.getJobTitle()))
                    .append(System.lineSeparator());

                    employee
                            .getProjects()
                            .stream()
                            .sorted(Comparator.comparing(Project::getName))
                            .forEach(project -> sb.append(leftPad()).append(project.getName())
                                    .append(System.lineSeparator()));

                    writer.println(sb.toString().trim());
        } catch (NumberFormatException | NoResultException e){
            writer.println(ERR_ID);
        }

    }

    private static String leftPad() {
        return String.format("%6s", " ");
    }
}
