package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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

    public List<Genre> create(List<Genre> objects)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (Genre object : objects)
            {
                em.persist(object);
            }
            em.getTransaction().commit();
        }
        return objects;
    }

    public Genre read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Genre.class, id);
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading genre from db", e);
        }
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
