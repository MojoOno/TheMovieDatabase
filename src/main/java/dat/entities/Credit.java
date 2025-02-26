package dat.entities;
import dat.dtos.CreditDTO;
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
public class Credit
{
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String knownForDepartment;

    @ManyToOne
    @ToString.Exclude
    @Setter
    private Movie movie;

    public Credit(CreditDTO creditDTO)
    {
        this.name = creditDTO.getName();
        this.knownForDepartment = creditDTO.getKnownForDepartment();
    }
}
