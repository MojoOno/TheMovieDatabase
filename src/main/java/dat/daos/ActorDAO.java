package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ActorDAO extends GenericDAO<Actor>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static ActorDAO instance;

    private ActorDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public Actor create(Actor object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Actor read(Actor object)
    {
        return null;
    }


    public Actor update(Actor object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Actor delete(Actor object)
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
