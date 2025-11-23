package __gestao_vagas_frontend.modules.company.services;

import __gestao_vagas_frontend.modules.company.dto.CreateJobDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CreateJobService {

    @Value("${host.api.gestao.vagas}")
    private String host;

    public void execute(CreateJobDTO createJobDTO, String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CreateJobDTO> request = new HttpEntity<>(createJobDTO, headers);

        String url = host.concat("/company/job");

        rt.postForObject(url, request, String.class);
    }
}
