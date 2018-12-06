package tasks;

import entities.Address;
import entities.Employee;
import entities.Town;
import io.Reader;
import io.Writer;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.io.IOException;

import static constants.Constants.*;

//TASK 6
public class AddingANewAddressAndUpdatingEmp {
    public static void execute(EntityManager em, Reader reader, Writer writer) throws IOException {
        writer.println(String.format("%s %s", CHOOSE_TASK_STR, TASK_6));

        String name = reader.readLine();

        try{
            Employee emp = em
                    .createQuery(TASK_6_QL_EMPLOYEE_STR, Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();

            Town town = em.createQuery(TASK_5_QL_TOWN_SELECT_STR, Town.class)
                        .getSingleResult();

            if (town == null){
                town = new Town();
                town.setName(TOWN_NAME);
                em.persist(town);
            }
            Address address = new Address();
            address.setText(ADDRESS_TEXT);
            address.setTown(town);

            em.getTransaction().begin();
            em.persist(address);
            emp.setAddress(address);
            em.getTransaction().commit();

        } catch (NoResultException e){
            writer.println(ERR_NO_EMPLOYEE);
            em.getTransaction().rollback();
        }
    }
}
