package __gestao_vagas_frontend.modules.company.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompanyLoginResponseDTO {
    private String accessToken;
    private List<String> roles;
    private Long expiresIn;
}
