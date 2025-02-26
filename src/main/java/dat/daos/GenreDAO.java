package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenreDAO extends GenericDAO<Genre>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static GenreDAO instance;

    private GenreDAO(EntityManagerFactory emf)
    {
        super(emf);
    }
    public Genre create(Genre object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Genre read(Genre object)
    {
        return null;
    }


    public Genre update(Genre object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Genre delete(Genre object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public GenreDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new GenreDAO(emf);
        }
        return instance;
    }
}
