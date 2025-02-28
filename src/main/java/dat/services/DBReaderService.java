package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.daos.SauronDAO;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DBReaderService
{
    private ObjectMapper objectMapper;
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final SauronDAO sauronDAO = SauronDAO.getInstance(emf);

    public DBReaderService()
    {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Supports LocalDate
    }

    public List<Movie> getMovies()
    {
        List<Movie> movies = null;
        try
        {
            movies =  sauronDAO.findAll(Movie.class);
            int lastIndex = movies.size() -1;

            System.out.println("First movie: "+ movies.get(0));
            System.out.println("Last movie: "+  movies.get(lastIndex));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
    public Double getTotalAverageRating()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT AVG(m.rating) FROM Movie m", Double.class)
                    .getSingleResult();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading object from db", e);
        }
    }

    public List<Movie> getTop10Movies()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading object from db", e);
        }
    }

    public List<Movie> getBot10Movies()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating ASC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading object from db", e);
        }
    }

    public List<Movie> getPopularMovies()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading object from db", e);
        }
    }
    public List<Movie> getMoviesByTitle(String title)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class)
                    .setParameter("title", "%" + title + "%")
                    .getResultList();
        }
        catch (Exception e)
        {
            throw new ApiException(401, "Error reading object from db", e);
        }
    }

}
