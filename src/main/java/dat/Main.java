package dat;

import dat.config.HibernateConfig;
import dat.daos.CreditDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import dat.services.MovieService;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main
{
    final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    final static MovieDAO movieDAO = MovieDAO.getInstance(emf);
    final static CreditDAO creditDAO = CreditDAO.getInstance(emf);
    final static GenreDAO genreDAO = GenreDAO.getInstance(emf);

    public static void main(String[] args)
    {

        DataAPIReader reader = new DataAPIReader();
        MovieService service = new MovieService(reader);

        List<MovieDTO> movieDTOs = service.getMoviesFromCountryFromLastFiveYears("da");

        //create a new movie object for each movie in the list
        for (MovieDTO movieDTO : movieDTOs)
        {
            Movie movie = new Movie(movieDTO);
            movieDAO.create(movie);
        }
    }
}