package dat.daos;

import dat.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenericDAO<T>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static GenericDAO instance;

    public  GenericDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public T create(T object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public T read(T object)
    {
        return null;
    }


    public T update(T object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public T delete(T object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public GenericDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new GenericDAO<T>(emf);
        }
        return instance;
    }
}
