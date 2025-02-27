package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.*;
import dat.entities.Movie;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService
{
    private final ObjectMapper objectMapper;
    private final DataAPIReader dataAPIReader;
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("api_key");
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final  MovieDAO movieDAO = MovieDAO.getInstance(emf);

    public MovieService(DataAPIReader dataAPIReader)
    {
        this.dataAPIReader = dataAPIReader;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Supports LocalDate
    }


    public List<Movie> getMovies()
    {
        List<Movie> movies = null;
        try
        {
            movies =  movieDAO.findAll();
            int lastIndex = movies.size() -1;

            System.out.println("First movie: "+ movies.get(0));
            System.out.println("Last movie: "+  movies.get(lastIndex));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }

    public List<GenreDTO> getGenres()
    {
        String url = BASE_URL + "/genre/movie/list?language=da&api_key=" + API_KEY;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            GenreResponseDTO response = objectMapper.readValue(json, GenreResponseDTO.class);
            return response.getGenres();
        } catch (Exception e)
        {
            e.printStackTrace();
            return List.of();
        }
    }

//    public List<MovieDTO> getMoviesFromCountryFromLastFiveYears(String country, int pageNumber) {
//        String url = BASE_URL + "/discover/movie?include_adult=false&include_video=false&language=da-DK&page=%PAGE%&release_date.gte=%TODAY%&sort_by=popularity.desc&with_original_language=%COUNTRY%&api_key=" + API_KEY;
//        try {
//            String json = dataAPIReader.getDataFromClient(url.replace("%COUNTRY%", country).replace("%TODAY%", LocalDate.now().toString()).replace("%PAGE%", String.valueOf(pageNumber)));
//            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
//            return response.getMovies();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return List.of();
//        }
//    }

    public List<MovieDTO> getMoviesFromCountryFromLastFiveYears(String country) {
        List<MovieDTO> allMovies = new ArrayList<>();
        int pageNumber = 1;
        int totalPages = 1; // Initial assumption; will be updated after the first request.

        String urlTemplate = BASE_URL + "/discover/movie?include_adult=false&include_video=false&language=da-DK&page=%PAGE%&release_date.gte=%DATE%&sort_by=popularity.desc&with_original_language=%COUNTRY%&api_key=" + API_KEY;

        try {
            do {
                // Replace placeholders
                String url = urlTemplate
                        .replace("%COUNTRY%", country)
                        .replace("%DATE%", LocalDate.now().minusYears(5).toString()) // Adjust to last 5 years
                        .replace("%PAGE%", String.valueOf(pageNumber));

                // Fetch data
                String json = dataAPIReader.getDataFromClient(url);
                MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);

                if (response.getMovies() != null) {
                    allMovies.addAll(response.getMovies());
                }

                // Set totalPages from the response (only needs to be done once)
                totalPages = response.getTotalPages();

                pageNumber++; // Move to the next page
            } while (pageNumber <= totalPages); // Continue until all pages are fetched

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allMovies;
    }


    public List<CreditDTO> getActors(int movieId) {
        String url = BASE_URL + "/movie/" + movieId +"/credits?language=da-DK&api_key=" + API_KEY;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            CreditResponseDTO response = objectMapper.readValue(json, CreditResponseDTO.class);
            return response.getCast();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<CreditDTO> getDirectors(int movieId) {
        String url = BASE_URL + "/movie/" + movieId +"/credits?language=da-DK&api_key=" + API_KEY;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            CreditResponseDTO response = objectMapper.readValue(json, CreditResponseDTO.class);
            return response.getCrew().stream().filter(actor -> actor.getKnownForDepartment().equals("Directing")).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public MovieDTO getMovieById(int movieId)
    {
        String url = BASE_URL + "/movie/" + movieId + "?api_key=" + API_KEY;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            return objectMapper.readValue(json, MovieDTO.class);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<MovieDTO> getByRating(double minRating, double maxRating)
    {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY + "&vote_average.gte=" + minRating + "&vote_average.lte=" + maxRating;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies();
        } catch (Exception e)
        {
            e.printStackTrace();
            return List.of();
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
            return response.getMovies().stream()
                    .sorted(Comparator.comparing(MovieDTO::getReleaseDate).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e)
        {
            e.printStackTrace();
            return List.of();
        }
    }

    //I want a method that sorts by description
    public List<MovieDTO> getSortedByDescription(String query)
    {
        String url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + query;
        try
        {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies().stream()
                    .sorted(Comparator.comparing(MovieDTO::getDescription))
                    .collect(Collectors.toList());
        } catch (Exception e)
        {
            e.printStackTrace();
            return List.of();
        }
    }
}
