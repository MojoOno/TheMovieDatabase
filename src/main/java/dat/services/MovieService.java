package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.GenreDTO;
import dat.dtos.GenreResponseDTO;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.utils.DataAPIReader;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {
    private final ObjectMapper objectMapper;
    private final DataAPIReader dataAPIReader;
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String API_KEY = System.getenv("api_key");


    public MovieService(DataAPIReader dataAPIReader) {
        this.dataAPIReader = dataAPIReader;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // Supports LocalDate
    }

    public List<GenreDTO> getGenres() {
        String url = BASE_URL + "/genre/movie/list?language=da&api_key=" + API_KEY;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            GenreResponseDTO response = objectMapper.readValue(json, GenreResponseDTO.class);
            return response.getGenres();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public MovieDTO getMovieById(int movieId) {
        String url = BASE_URL + "/movie/" + movieId + "?api_key=" + API_KEY;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            return objectMapper.readValue(json, MovieDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MovieDTO> getByRating(double minRating, double maxRating) {
        String url = BASE_URL + "/discover/movie?api_key=" + API_KEY + "&vote_average.gte=" + minRating + "&vote_average.lte=" + maxRating;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<MovieDTO> getSortedByReleaseDate(String query) {
        String url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + query;
        objectMapper.registerModule(new JavaTimeModule());
        try {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies().stream()
                    .sorted(Comparator.comparing(MovieDTO::getReleaseDate).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    //I want a method that sorts by description
    public List<MovieDTO> getSortedByDescription(String query) {
        String url = BASE_URL + "/search/movie?api_key=" + API_KEY + "&query=" + query;
        try {
            String json = dataAPIReader.getDataFromClient(url);
            MovieResponseDTO response = objectMapper.readValue(json, MovieResponseDTO.class);
            return response.getMovies().stream()
                    .sorted(Comparator.comparing(MovieDTO::getDescription))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
