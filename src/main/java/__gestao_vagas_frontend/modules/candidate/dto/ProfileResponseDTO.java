package __gestao_vagas_frontend.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseDTO {
    private String email;
    private UUID id;
    private String description;
    private String username;
    private String name;
}
