package tasks;

import entities.Employee;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.io.IOException;

import static constants.Constants.*;

//TASK 3

public class ContainsEmployee  {
    public static void execute(EntityManager em, Reader reader, Writer writer) throws IOException {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_3));
        writer.println(ENTER_EMP_NAME);
        String name = reader.readLine();

        try{
            em
                    .createQuery(TASK_3_QL_STRING, Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();
            writer.println("Yes");
        } catch (NoResultException e){
            writer.println("No");
        }

    }


}
