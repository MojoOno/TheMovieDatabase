package dat.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GenreResponseDTO
{
    private List<GenreDTO> genres;
}
