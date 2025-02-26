package dat.entities;

import dat.dtos.GenreDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "genres", fetch = jakarta.persistence.FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();

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
            movie.getGenres().add(this);
        }
    }
}
