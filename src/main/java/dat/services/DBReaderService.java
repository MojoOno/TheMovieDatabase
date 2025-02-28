package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Movie> readMoviesByGenre(String genreName)
    {
        List<Movie> movies = null;
        try {
            movies = sauronDAO.findAll(Movie.class);
            List<Movie> filteredMovies = movies.stream()
                .filter(movie -> movie.getGenres().stream()
                    .anyMatch(genre -> genre.getName().equalsIgnoreCase(genreName)))
                .collect(Collectors.toList());

            filteredMovies.forEach(System.out::println);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies; // Return the filtered list
    }

    public List<Genre> readAllGenres()
    {
        List<Genre> genres = null;
        try
        {
            genres = sauronDAO.findAll(Genre.class);
            genres.stream()
                .map(genre -> genre.getName())
                .forEach(System.out::println);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return genres;
    }

    public void getKnownForDepartment(String keyword)
    {
        sauronDAO.findAll(Credit.class).stream()
            .filter(credit -> credit.getKnownForDepartment().equalsIgnoreCase(keyword))
            .collect(Collectors.toList())
            .forEach(System.out::println);
    }


    public List<Movie> getMovies()
    {
        List<Movie> movies = null;
        try
        {
            movies =  sauronDAO.findAll(Movie.class);
            int lastIndex = movies.size() -1;


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
