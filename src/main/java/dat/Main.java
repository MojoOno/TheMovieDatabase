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
import java.util.stream.Collectors;

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

//        List<GenreDTO> genreDTOs = service.getGenres();
//        List<Genre> genres = genreDTOs.stream()
//                .map(Genre::new)
//                .toList();
//        genres.forEach(System.out::println);
//        genreDAO.create(genres);

        List<CreditDTO> castList = service.getCastFromMoviesFromCountryFromLastFiveYears("da");
        List<Credit> cast = castList.stream()
                .map(Credit::new)
                .toList();
        creditDAO.create(cast);

        List<MovieDTO> movieDTOs = service.getMoviesFromCountryFromLastFiveYears("da");

//        create a new movie object for each movie in the list
        for (MovieDTO movieDTO : movieDTOs)
        {
            Movie movie = new Movie(movieDTO);
//            movieDTO.getGenreIds().forEach(id -> {
//                movie.addGenre(genreDAO.read(id));
//            });

            movieDAO.create(movie);
        }
        //create a new Credit object for each movie in the list




    }
}