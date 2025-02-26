package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class MovieDAO extends GenericDAO<Movie>
{
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static MovieDAO instance;

    private MovieDAO(EntityManagerFactory emf)
    {
        super(emf);
    }

    public Movie create(Movie object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Movie read(Movie object)
    {
        return null;
    }


    public Movie update(Movie object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public Movie delete(Movie object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        }
        return object;
    }

    public MovieDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new MovieDAO(emf);
        }
        return instance;
    }
}
