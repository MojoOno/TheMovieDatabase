package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Credit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ActorDAO extends GenericDAO<Credit>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ActorDAO instance;

    private ActorDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public Credit create(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Credit read(Credit object)
    {
        return null;
    }


    public Credit update(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Credit delete(Credit object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public ActorDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new ActorDAO(emf);
        }
        return instance;
    }
}
