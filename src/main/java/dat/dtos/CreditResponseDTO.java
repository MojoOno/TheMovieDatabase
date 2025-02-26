package dat.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreditResponseDTO
{
    private int id;
    private List<CreditDTO> cast;
    private List<CreditDTO> crew;
}
