package dat;

import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.entities.Credit;
import dat.entities.Movie;
import dat.services.APIReaderService;
import dat.services.DBReaderService;
import dat.services.MovieService;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Main
{
    private final static MovieService service = new MovieService();
    private final static DBReaderService dbService = new DBReaderService();
    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final static SauronDAO sauronDAO = SauronDAO.getInstance(emf);


    public static void main(String[] args)
    {


        //service.createMovies();
        //dbService.getMovies().forEach(System.out::println);
        //dbService.getKnownForDepartment("Directing");
        //dbService.getKnownForDepartment("Acting");
        //dbService.readMoviesByGenre("Action");
        //dbService.readAllGenres();

        // create new movie object and persist to db
/*
        Movie movie = new Movie();
        movie.setId(1337L);
        sauronDAO.create(movie);
*/
        // updating the created movie
/*
        Movie movieToUpdate;
        movieToUpdate = movie;
        movieToUpdate.setTitle("Ringenes herre");
        sauronDAO.update(movieToUpdate);
*/

        // delete persisted object
        //sauronDAO.delete(Movie.class, 1337);


        // search a movie
        //dbService.getMoviesByTitle("puSher").forEach(System.out::println);

        // total average of all movies
        System.out.println(dbService.getTotalAverageRating());

        // top 10 lowest movies
        dbService.getBot10Movies().forEach(System.out::println);

        // top 10 best movies
        dbService.getTop10Movies().forEach(System.out::println);

        // most popular movies - top 10
        dbService.getPopularMovies().forEach(System.out::println);
    }



}
