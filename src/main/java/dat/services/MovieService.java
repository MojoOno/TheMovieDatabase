package dat.services;

import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.dtos.CreditDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MovieService
{
    public final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final SauronDAO sauronDAO = SauronDAO.getInstance(emf);
    DataAPIReader reader = new DataAPIReader();
    APIReaderService service = new APIReaderService(reader);

    public void createMovies()
    {
        List<GenreDTO> genreDTOs = service.getGenres();
        List<Genre> genres = genreDTOs.stream()
                .map(Genre::new)
                .toList();
        sauronDAO.create(genres);


        List<MovieDTO> movieDTOs = service.getMoviesFromCountryFromLastFiveYears("da",1);

//        create a new movie object for each movie in the list
        for (MovieDTO movieDTO : movieDTOs)
        {
            Movie movie = new Movie(movieDTO);
            sauronDAO.create(movie);

            // Add genres to the movie
            movieDTO.getGenreIds().forEach(id -> movie.addGenre(sauronDAO.read(Genre.class, id)));


            // Add actors and directors to the movie
            List<CreditDTO> castList = service.getCastAndDirectors(movieDTO.getMovieId());
            List<Credit> cast = castList.stream()
                    .filter(creditDTO -> "Actor".equals(creditDTO.getJob()))
                    .map(Credit::new)
                    .toList();
            List<Credit> persistedCast = sauronDAO.update(cast);
            persistedCast.forEach(movie::addActor);
            List<Credit> directors = castList.stream()
                    .filter(creditDTO -> "Director".equals(creditDTO.getJob()))
                    .map(Credit::new)
                    .toList();
            List<Credit> persistedDirectors = sauronDAO.update(directors);
            persistedDirectors.forEach(movie::addDirector);

            // Finally update the movie
            sauronDAO.update(movie);

        }
    }
}
