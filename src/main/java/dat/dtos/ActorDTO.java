package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {
    private int id;
    private String name;

    @JsonProperty("original_name")
    private String originalName;

    private int gender;

    @JsonProperty("known_for_department")
    private String knownForDepartment;

    private double popularity;

    @JsonProperty("profile_path")
    private String profilePath;

    @JsonProperty("cast_id")
    private int castId;

    private String character;

    @JsonProperty("credit_id")
    private String creditId;
}

