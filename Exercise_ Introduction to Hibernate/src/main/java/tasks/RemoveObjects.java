package tasks;

import entities.Town;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;

import java.util.List;

import static constants.Constants.*;

//TASK 2
public class RemoveObjects{


    public static void execute(EntityManager em, Reader reader, Writer writer) {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_2));
        em.getTransaction().begin();

        List<Town> towns = em.createQuery(TASK_2_QL_STRING, Town.class).getResultList();

        for (Town town : towns) {
            if (town.getName().length() <= 5){
                town.setName(town.getName().toLowerCase());
                em.persist(town);
            }
        }

        towns = em.createQuery(TASK_2_QL_STRING, Town.class).getResultList();

        towns.forEach(t-> writer.println(String.format("%d %s", t.getId(), t.getName())));

        em.getTransaction().commit();

    }


}
