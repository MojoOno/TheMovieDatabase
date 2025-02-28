package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.*;
import dat.utils.DataAPIReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class APIReaderService
{
    private final ObjectMapper objectMapper;
    private final DataAPIReader dataAPIReader;
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("api_key");


    public APIReaderService(DataAPIReader dataAPIReader)
    {
        this.dataAPIReader = dataAPIReader;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Supports LocalDate
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

    public List<CreditDTO> getCast(Long movieId) {
        String url = BASE_URL + "/movie/" + movieId + "/credits?api_key=" + API_KEY;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            CreditResponseDTO response = objectMapper.readValue(json, CreditResponseDTO.class);
            return response.getCast();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    public List<CreditDTO> getCastFromMoviesFromCountryFromLastFiveYears(String country)
    {
        List<MovieDTO> movies = getMoviesFromCountryFromLastFiveYears(country);
        List <CreditDTO> castList = new ArrayList<>();
        for (MovieDTO movie : movies)
        {
            Long movieId = movie.getMovieId();
            List<CreditDTO> cast = getCast(movieId);
            castList.addAll(cast);
        }
        return castList;
    }

    public List<CreditDTO> getCrew(Long movieId) {
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
//                totalPages = response.getTotalPages();

                pageNumber++; // Move to the next page
            } while (pageNumber <= totalPages); // Continue until all pages are fetched

        } catch (Exception e) {
            e.printStackTrace();
        }

        return allMovies;
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
