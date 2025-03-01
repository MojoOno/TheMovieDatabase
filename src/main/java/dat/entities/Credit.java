package dat.entities;
import dat.dtos.CreditDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Credit
{
    @Id
    private Long id;
    private String name;
    private String knownForDepartment;

    @ManyToMany(mappedBy = "actors", fetch = FetchType.EAGER)
        @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Movie> actorMovies = new HashSet<>();

    @ManyToMany(mappedBy = "directors", fetch = FetchType.EAGER)
       @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Movie> directorMovies = new HashSet<>();

//    @ManyToOne
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Movie movie;

    public Credit(CreditDTO creditDTO)
    {
        this.id = creditDTO.getCreditId();
        this.name = creditDTO.getName();
        this.knownForDepartment = creditDTO.getKnownForDepartment();
    }

    public void addActorMovie(Movie movie)
    {
        if (movie != null)
        {
            actorMovies.add(movie);
        }
    }

    public void addDirectorMovie(Movie movie)
    {
        if (movie != null)
        {
            directorMovies.add(movie);
        }
    }
}
