package dat.daos;

import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

import java.util.List;

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
        } catch (ConstraintViolationException e)
        {
            throw new ApiException(403, "Error movie violates constraint, likely already exists.", e);
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating movie. ", e);
        }
    }
    public List<Movie> create(List<Movie> objects)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (Movie object : objects)
            {
                em.persist(object);
            }
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error creating movies. ", e);
        }
        return objects;
    }
    @Override
    public Movie read(int id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Movie.class, id);
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading movie from db", e);
        }
    }

    @Override
    public Movie update(Movie object)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error updating movie. ", e);
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
        } catch (Exception e)
        {
            throw new ApiException(401, "Error deleting movie. ", e);
        }
    }

    public List<Movie> findAll()
    {
        List<Movie> movies = null;
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            String jpql = "SELECT m FROM Movie m ";
            Query query = em.createQuery(jpql);
            movies = query.getResultList();
            em.getTransaction().commit();

        }  catch (Exception e)
        {
            throw new ApiException(401, "Error reading movie. ", e);
        }
        return movies;
    }
}
