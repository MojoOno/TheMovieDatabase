package dat.entities;
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


}
