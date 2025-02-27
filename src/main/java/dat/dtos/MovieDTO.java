package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Credit;
import dat.enums.MovieGenres;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO
{
        @JsonProperty("id")
        private Long movieId;
        @JsonProperty("original_title")
        private String title;
        @JsonProperty("overview")
        private String description;
        @JsonProperty("release_date")
        private LocalDate releaseDate;
        @JsonProperty("vote_average")
        private Double score;
        private Double popularity;
        @JsonProperty("genre_ids")
        private List<Integer> genreIds;
        @JsonProperty("cast")
        private List<Credit> cast;


}
