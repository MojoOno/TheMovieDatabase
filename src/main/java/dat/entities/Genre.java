package dat.entities;

import dat.dtos.GenreDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
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
    private Long id;
    private String name;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "genres", fetch = jakarta.persistence.FetchType.EAGER)
    private Set<Movie> movies = new HashSet<>();

    public Genre(GenreDTO genreDTO)
    {
        this.id = genreDTO.getId();
        this.name = genreDTO.getName();
    }

    public Genre(String name)
    {
        this.name = name;
    }

    public void addMovie(Movie movie)
    {
        if (movie != null)
        {
            movies.add(movie);
            //movie.getGenres().add(this);
        }
    }
}
