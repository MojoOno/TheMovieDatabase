package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenreDAO implements IDAO<Genre>
{
    private static EntityManagerFactory emf;
    private static GenreDAO instance;

    private GenreDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    public static GenreDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new GenreDAO(emf);
        }
        return instance;
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

    public Genre read(int id)
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

    public void delete(Long id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(id);
            em.getTransaction().commit();
        }
    }
}
