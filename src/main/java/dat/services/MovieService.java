package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.utils.DataAPIReader;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService
{
    private final ObjectMapper objectMapper;
    private final DataAPIReader dataAPIReader;
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("api_key");


    public MovieService(DataAPIReader dataAPIReader)
    {
        this.dataAPIReader = dataAPIReader;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Supports LocalDate
    }

    public MovieDTO getMovieById(int movieId)
    {
        String url = BASE_URL + "/movie/" + movieId + "?api_key=" + API_KEY;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieDTO movieDTO = objectMapper.readValue(json, MovieDTO.class);
            return movieDTO;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void getByRating(double minRating, double maxRating)
    {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY + "&vote_average.gte=" + minRating + "&vote_average.lte=" + maxRating;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            response.getMovies().stream()
                    .map(movie -> movie.getTitle() + " - " + movie.getRating())
                    .forEach(System.out::println);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    public List<MovieDTO> getSortedByReleaseDate(String query)
    {
        String url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + query;
        objectMapper.registerModule(new JavaTimeModule());
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            List<MovieDTO> movieDTOList = response.getMovies().stream()
                    .sorted(Comparator.comparing(MovieDTO::getReleaseDate).reversed())
                    .collect(Collectors.toList());
            return movieDTOList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return List.of();
        }
    }


    public MovieDTO getMovieByTitle(String title)
    {
        String url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + title;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies().get(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public void displayMovieDetails(MovieDTO movie)
    {
        if (movie != null)
        {
            System.out.println("Title: " + movie.getTitle());
            System.out.println("Release Date: " + movie.getReleaseDate());
            System.out.println("Rating: " + movie.getRating());
            System.out.println("Description: " + movie.getDescription());
            System.out.println("Language:" + movie.getLangueage());
        }
        else
        {
            System.out.println("Movie not found.");
        }
    }
}
