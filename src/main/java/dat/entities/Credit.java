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

    @ManyToMany (mappedBy = "credits", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Movie> movies = new HashSet<>();

    public Credit(CreditDTO creditDTO)
    {
        this.id = creditDTO.getCreditId();
        this.name = creditDTO.getName();
        this.knownForDepartment = creditDTO.getKnownForDepartment();
    }

    public void addMovie(Movie movie)
    {
        if (movie != null)
        {
            movies.add(movie);
        }
    }
}
