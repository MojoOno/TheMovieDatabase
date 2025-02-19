package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.enums.MovieGenres;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO
{
        private String title;
        @JsonProperty("overview")
        private String description;
        private Long id;
        @JsonProperty("release_date")
        private LocalDate releaseDate;
        @JsonProperty("vote_average")
        private Double score;
        @JsonProperty("original_language")
        private String langueage;


//    public static class MovieGenres
//    {
//        @JsonProperty("genre_ids")
//        private Integer[] genres;
//    }
}
