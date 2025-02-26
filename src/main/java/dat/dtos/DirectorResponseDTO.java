package dat.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DirectorResponseDTO
{
    private List<DirectorDTO> directors;
}
