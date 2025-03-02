package dat;

import dat.services.DBReaderService;
import dat.services.MovieService;

public class Main
{
    private final static MovieService service = new MovieService();
    private final static DBReaderService dbReaderService = new DBReaderService();
    public static void main(String[] args)
    {

    service.createMovies();


    dbReaderService.printPopularMovies();
    dbReaderService.printTop10Movies();
    dbReaderService.printBot10Movies();
    System.out.println("Total Average Rating: " + dbReaderService.getTotalAverageRating());
    dbReaderService.printMoviesByTitle("druk");
    dbReaderService.getMovies();

    }
}