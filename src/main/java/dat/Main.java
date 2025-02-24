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
        service.displayMovieDetails(service.getMovieById(18));


        System.out.println("----------------------------------------");
        System.out.println("Movies by rating with the format: rating(1.0), rating(2.0)");
        service.getByRating(7.5, 8.5);

        System.out.println("----------------------------------------");
        System.out.println("Movies sorted by release date:");
        service.getSortedByReleaseDate("1994").forEach(System.out::println);


        System.out.println("----------------------------------------");
        System.out.println("Movie by title with the format: Title-Without-Spaces:");
        service.displayMovieDetails(service.getMovieByTitle("The-Shawshank-Redemption"));
    }
}