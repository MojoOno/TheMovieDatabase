package dat.daos;

import dat.entities.Movie;

import java.util.List;

public interface readerDAO
{
    Double getTotalAverageRating();
    List<Movie> getTop10Movies();
    List<Movie> getBot10Movies();
    List<Movie> getPopularMovies();
    List<Movie> getMoviesByTitle(String title);

}
