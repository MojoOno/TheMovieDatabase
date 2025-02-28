package dat;

import dat.services.MovieService;

public class Main
{
    private final static MovieService service = new MovieService();
    public static void main(String[] args)
    {

    service.createMovies();

    }
}