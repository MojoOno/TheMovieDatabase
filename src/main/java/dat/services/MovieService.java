package dat.services;

import dat.config.HibernateConfig;
import dat.daos.SauronDAO;
import dat.dtos.CreditDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Credit;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.utils.DataAPIReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Service class responsible for fetching, processing, and storing movies concurrently.
 */
public class MovieService {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private final SauronDAO sauronDAO = SauronDAO.getInstance(emf);
    private final DataAPIReader reader = new DataAPIReader();
    private final APIReaderService service = new APIReaderService(reader);
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Thread-safe map to prevent duplicate insertions of Credit entities
    private static final ConcurrentHashMap<Long, Credit> creditCache = new ConcurrentHashMap<>();

    /**
     * Fetches and processes movies in parallel to improve performance.
     */
    public void createMovies() {
        // Load genres first (single-threaded)
        List<GenreDTO> genreDTOs = service.getGenres();
        List<Genre> genres = genreDTOs.stream().map(Genre::new).toList();
        sauronDAO.create(genres);

        // Fetch movies in parallel
        List<MovieDTO> movieDTOs = fetchMoviesConcurrently("da");

        // Process movies in parallel
        List<Future<Void>> futures = movieDTOs.stream()
                .map(movieDTO -> executor.submit(new MovieTask(movieDTO, sauronDAO, service)))
                .toList();

        // Wait for all tasks to complete
        for (Future<Void> future : futures) {
            try {
                future.get(); // Ensure each movie processing finishes
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the executor after processing all movies
        executor.shutdown();
    }

    /**
     * Fetches movies concurrently using multiple threads.
     * @param countryCode Country code for filtering movies.
     * @return A list of MovieDTOs.
     */
    private List<MovieDTO> fetchMoviesConcurrently(String countryCode) {
        int threadCount = Runtime.getRuntime().availableProcessors(); // Optimal thread count
        ExecutorService fetchExecutor = Executors.newFixedThreadPool(threadCount);

        List<Callable<List<MovieDTO>>> fetchTasks = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            fetchTasks.add(() -> service.getMoviesFromCountryFromLastFiveYears(countryCode));
        }

        List<MovieDTO> allMovies = new ArrayList<>();
        try {
            List<Future<List<MovieDTO>>> results = fetchExecutor.invokeAll(fetchTasks);

            for (Future<List<MovieDTO>> result : results) {
                allMovies.addAll(result.get()); // Merge results from all threads
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            fetchExecutor.shutdown();
        }

        return allMovies;
    }

    /**
     * Inner class responsible for processing a single movie concurrently.
     */
    static class MovieTask implements Callable<Void> {
        private final MovieDTO movieDTO;
        private final SauronDAO sauronDAO;
        private final APIReaderService service;

        public MovieTask(MovieDTO movieDTO, SauronDAO sauronDAO, APIReaderService service) {
            this.movieDTO = movieDTO;
            this.sauronDAO = sauronDAO;
            this.service = service;
        }

        @Override
        public Void call() {
            try {
                // Create movie entity
                Movie movie = new Movie(movieDTO);
                sauronDAO.create(movie);

                // Add genres
                movieDTO.getGenreIds().forEach(id -> movie.addGenre(sauronDAO.read(Genre.class, id.longValue())));

                // Fetch and add cast
                List<CreditDTO> castList = service.getCast(movieDTO.getMovieId());
                for (CreditDTO creditDTO : castList) {
                    Long creditId = creditDTO.getCreditId();

                    // Check if credit already exists in DB or cache
                    Credit credit = creditCache.computeIfAbsent(creditId, id -> {
                        Credit newCredit = new Credit(creditDTO);
                        if (sauronDAO.read(Credit.class, id) == null) {
                            sauronDAO.create(newCredit);
                        }
                        return newCredit;
                    });

                    movie.addActor(credit);
                }

                // Update movie with genres and actors
                sauronDAO.update(movie);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
// Add directors to the movie
//            List<CreditDTO> directorList = service.getCrew(movieDTO.getMovieId());
//            List<Credit> directors = directorList.stream()
//                    .map(Credit::new)
//                    .toList();
//            sauronDAO.update(directors);
//            directors.forEach(movie::addDirector);
//            sauronDAO.update(movie);

        //create a new Credit object for each movie in the list
