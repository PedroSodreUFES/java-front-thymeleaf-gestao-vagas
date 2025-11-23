package __gestao_vagas_frontend.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCandidateDTO {
    private String password;
    private String name;
    private String username;
    private String email;
    private String description;
}
