package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditDTO
{
    @JsonProperty("id")
    private Long creditId;
    private String name;

    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job = "Actor";


}

