package __gestao_vagas_frontend.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobDTO {
    private String level;
    private String description;
    private String benefits;
}
