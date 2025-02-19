package dat;

import dat.services.MovieService;
import dat.utils.DataAPIReader;

public class Main
{
    public static void main(String[] args)
    {
        DataAPIReader reader = new DataAPIReader();
        MovieService service = new MovieService(reader);

        System.out.println("Movie by ID:");
        System.out.println(service.getMovieById(139));
        System.out.println("----------------------------------------");
        System.out.println("Movies by rating:");
        System.out.println(service.getByRating(7.5, 8.5));
        System.out.println("----------------------------------------");
        System.out.println("Movies sorted by release date:");
        System.out.println(service.getSortedByReleaseDate("1994"));
    }
}