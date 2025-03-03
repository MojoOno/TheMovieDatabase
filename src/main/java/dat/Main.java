package dat;

import dat.entities.Movie;
import dat.services.DBReaderService;
import dat.services.MovieService;

import java.util.List;

public class Main
{
    private final static MovieService service = new MovieService();
    private final static DBReaderService dbReaderService = new DBReaderService();
    public static void main(String[] args)
    {

    //service.fetchAndPersistMovies("da");

    System.out.println("#### Popular movies ####");
    dbReaderService.printPopularMovies();
    System.out.println("#### Top 10 movies ####");
    dbReaderService.printTop10Movies();
    System.out.println("#### Bottom 10 movies ####");
    dbReaderService.printBot10Movies();
    System.out.println("Total Average Rating: " + dbReaderService.getTotalAverageRating());
    dbReaderService.printMoviesByTitle("druk");
    List<Movie> movies = dbReaderService.getMovies();

    Movie movie = movies.get(0);
    //System.out.println("First movie: " + movie);
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Release Date: " + movie.getReleaseDate());
    System.out.println("Directors: " + movie.getDirectors());
    System.out.println("Actors: " + movie.getActors());
    System.out.println("Genres: " + movie.getGenres());
    System.out.println("Rating: " + movie.getRating());
    System.out.println("Popularity: " + movie.getPopularity());


    }
}