package dat.entities;
import dat.dtos.ActorDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Actor
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String originalName;
    private int gender;
    private String knownForDepartment;
    private double popularity;
    private String profilePath;
    private int castId;
    private String character;
    private String creditId;

    @ManyToOne
    @ToString.Exclude
    @Setter
    private Movie movie;

    public Actor(ActorDTO actorDTO)
    {
        this.name = actorDTO.getName();
        this.originalName = actorDTO.getOriginalName();
        this.gender = actorDTO.getGender();
        this.knownForDepartment = actorDTO.getKnownForDepartment();
        this.popularity = actorDTO.getPopularity();
        this.profilePath = actorDTO.getProfilePath();
        this.castId = actorDTO.getCastId();
        this.character = actorDTO.getCharacter();
        this.creditId = actorDTO.getCreditId();
    }
}
