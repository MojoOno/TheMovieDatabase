package dat;

import dat.services.MovieService;
import dat.utils.DataAPIReader;

import java.time.LocalDate;

public class Main
{
    public static void main(String[] args)
    {
        DataAPIReader reader = new DataAPIReader();
        MovieService service = new MovieService(reader);

        service.getMoviesFromCountryFromLastFiveYears("da").forEach(System.out::println);
    }
}