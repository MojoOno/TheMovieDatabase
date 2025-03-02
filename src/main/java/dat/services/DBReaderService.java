package dat.services;

import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DBReaderService
{
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final SauronDAO sauronDAO = SauronDAO.getInstance(emf);



    public List<Movie> getMovies()
    {
        List<Movie> movies;
        try
        {
            movies =  sauronDAO.findAll(Movie.class);
            int lastIndex = movies.size() -1;

            System.out.println("First movie: "+ movies.get(0));
            System.out.println("Last movie: "+  movies.get(lastIndex));
        } catch (Exception e)
        {
            throw new ApiException(401, "Error fetching data from db", e);
        }
        return movies;
    }


    public Double getTotalAverageRating()
    {
        Double totalAverageRating;
        try
        {
            totalAverageRating = sauronDAO.getTotalAverageRating();
        } catch (Exception e)
        {
            throw new ApiException(401, "Error calculating average rating from db", e);
        }
        return totalAverageRating;
    }


    public void printBot10Movies()
    {
        try
        {
            List<Movie> movies  = sauronDAO.getBot10Movies();
            int counter = 1;
            for (Movie movie : movies)
            {
                System.out.println(counter + ". " + movie.getTitle() + " - " + movie.getRating());
                counter++;
            }

        } catch (Exception e)
        {
            throw new ApiException(401, "Error fetching data from db", e);
        }
    }

    public void printTop10Movies()
    {
        try
        {
            List<Movie> movies  = sauronDAO.getTop10Movies();
            int counter = 1;
            for (Movie movie : movies)
            {
                System.out.println(counter + ". " + movie.getTitle() + " - " + movie.getRating());
                counter++;
            }

        } catch (Exception e)
        {
            throw new ApiException(401, "Error fetching data from db", e);
        }
    }

    public void printPopularMovies()
    {
        try
        {
            List<Movie> movies  = sauronDAO.getPopularMovies();
            int counter = 1;
            for (Movie movie : movies)
            {
                System.out.println(counter + ". " + movie.getTitle() + " - " + movie.getRating());
                counter++;
            }

        } catch (Exception e)
        {
            throw new ApiException(401, "Error fetching data from db", e);
        }
    }

    public void printMoviesByTitle(String title)
    {
        try
        {
            List<Movie> movies  = sauronDAO.getMoviesByTitle(title);
            int counter = 1;
            for (Movie movie : movies)
            {
                System.out.println(counter + ". " + movie.getTitle() + " - " + movie.getRating());
                counter++;
            }

        } catch (Exception e)
        {
            throw new ApiException(401, "Error fetching data from db", e);
        }
    }
}
