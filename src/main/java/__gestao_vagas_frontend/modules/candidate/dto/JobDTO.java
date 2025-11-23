package __gestao_vagas_frontend.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {
    private UUID id;
    private String description;
    private String benefits;
    private String level;
    private UUID companyId;
    private Date createdAt;
}
