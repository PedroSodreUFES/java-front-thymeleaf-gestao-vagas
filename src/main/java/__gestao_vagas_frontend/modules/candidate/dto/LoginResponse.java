package __gestao_vagas_frontend.modules.candidate.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String accessToken;
    private List<String> roles;
    private Long expires_in;
}
