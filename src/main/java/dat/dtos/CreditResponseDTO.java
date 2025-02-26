package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties (ignoreUnknown = true)
public class CreditResponseDTO
{
    private int id;
    private List<CreditDTO> cast;
    private List<CreditDTO> crew;
}
