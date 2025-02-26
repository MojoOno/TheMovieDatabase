package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DirectorDAO extends GenericDAO<Director>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static DirectorDAO instance;

    private DirectorDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public Director create(Director object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Director read(Director object)
    {
        return null;
    }


    public Director update(Director object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Director delete(Director object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public DirectorDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new DirectorDAO(emf);
        }
        return instance;
    }
}
