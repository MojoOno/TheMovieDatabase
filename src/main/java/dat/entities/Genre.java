package dat.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.dtos.GenreDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Genre
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private int genreId;
    private String name;
    @ManyToMany(mappedBy = "movies")
    private List<Movie> movies; //skal måske være et Set? Så vi ikke har den samme film to gange i listen

    public Genre(GenreDTO genreDTO)
    {
        this.genreId = genreDTO.getId();
        this.name = genreDTO.getName();
    }

    public void addMovie(Movie movie)
    {
        if (movie != null)
        {
            movies.add(movie);
            //movie.addGenre(this);
        }
    }
}
