package dat.daos;

import dat.entities.Movie;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class MovieDAO implements IDAO<Movie>
{
    private static EntityManagerFactory emf;
    private static MovieDAO instance;

    private MovieDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static MovieDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new MovieDAO(emf);
        }
        return instance;
    }

    @Override
    public Movie create(Movie object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return object;
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating movie");
        }
    }
    @Override
    public Movie read(int id)
    {
        return null;
    }

    @Override
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
    @Override
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
