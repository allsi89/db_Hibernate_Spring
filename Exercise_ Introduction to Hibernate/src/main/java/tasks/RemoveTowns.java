package tasks;

import entities.Address;
import entities.Employee;
import entities.Town;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.io.IOException;
import java.util.List;

import static constants.Constants.*;

//TASK 11
public class RemoveTowns {
    public static void execute(EntityManager em, Reader reader, Writer writer) throws IOException {
        writer.println(String.format("%s %s", CHOSEN_TASK, TASK_11));
        writer.println(REQUIRED_TOWN_NAME);

        String name = reader.readLine();

        try {
            Town town = em.createQuery(TASK_11_QL_TOWN_QUERY, Town.class)
                    .setParameter("name", name)
                    .getSingleResult();

            List<Address> addresses = em.createQuery(TASK_11_QL_ADDRESS_QUERY, Address.class)
                    .setParameter("name", name)
                    .getResultList();
            int addressCount = addresses.size();

            em.getTransaction().begin();
            for (Address address : addresses) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }

                address.setTown(null);
                em.remove(address);
            }

            em.remove(town);
            em.getTransaction().commit();
            writer.println(String.format("%d address%s in %s deleted",
                    addressCount, addressCount != 1 ? "es" : "", name));

        } catch (NoResultException e) {
            writer.println(NO_TOWN_MSG);
            em.getTransaction().rollback();
        }

    }

}
