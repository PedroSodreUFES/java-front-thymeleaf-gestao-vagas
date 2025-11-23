package __gestao_vagas_frontend.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCompanyDTO {

    private String username;
    private String email;
    private String website;
    private String description;
    private String name;
    private String password;
}
