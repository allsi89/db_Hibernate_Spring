package core;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static constants.Constants.*;

public class Engine implements Runnable {
    private EntityManagerFactory factory;
    private EntityManager em;

    public Engine() {
        factory = Persistence.createEntityManagerFactory(DATABASE_NAME);
        em = factory.createEntityManager();
    }

    @Override
    public void run() {
        em.close();
        factory.close();
        System.out.println(CREATED_DATABASE_NAME);
        System.out.println(CHECK_DATABASE_MSG);
    }
}
