package dat.entities;

import dat.entities.Actor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Movie
{
    @Id
    private Long id;
    private String title;

    @OneToMany
    private List<Actor>actorList;

    @OneToMany
    private List<Director>directorList;

    @ManyToMany
    private List<Genre>genreList;


}
