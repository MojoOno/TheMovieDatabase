package dat;

import dat.config.HibernateConfig;
import dat.daos.CreditDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.CreditDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.services.MovieService;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main
{
    public final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    final static MovieDAO movieDAO = MovieDAO.getInstance(emf);
    final static CreditDAO creditDAO = CreditDAO.getInstance(emf);
    final static GenreDAO genreDAO = GenreDAO.getInstance(emf);

    public static void main(String[] args)
    {

        DataAPIReader reader = new DataAPIReader();
        MovieService service = new MovieService(reader);

        List<GenreDTO> genreDTOs = service.getGenres();
        List<Genre> genres = genreDTOs.stream()
                .map(Genre::new)
                .toList();
        genres.forEach(System.out::println);
        genreDAO.create(genres);



        List<MovieDTO> movieDTOs = service.getMoviesFromCountryFromLastFiveYears("da");

//        create a new movie object for each movie in the list
        for (MovieDTO movieDTO : movieDTOs)
        {
            Movie movie = new Movie(movieDTO);
            movieDAO.create(movie);

            // Add genres to the movie
            movieDTO.getGenreIds().forEach(id -> movie.addGenre(genreDAO.read(Long.valueOf(id))));
            //movieDAO.update(movie);


            // Add cast to the movie
            List<CreditDTO> castList = service.getCast(movieDTO.getMovieId());
            List<Credit> cast = castList.stream()
                    .map(Credit::new)
                    .toList();
            creditDAO.update(cast);
            cast.forEach(movie::addActor);
            movieDAO.update(movie);

            // Add directors to the movie
//            List<CreditDTO> directorList = service.getCrew(movieDTO.getMovieId());
//            List<Credit> directors = directorList.stream()
//                    .map(Credit::new)
//                    .toList();
//            creditDAO.update(directors);
//            directors.forEach(movie::addDirector);
//            movieDAO.update(movie);
        }
        //create a new Credit object for each movie in the list




    }
}