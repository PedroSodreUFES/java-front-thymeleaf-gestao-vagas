package __gestao_vagas_frontend.modules.candidate.service;

import __gestao_vagas_frontend.modules.candidate.dto.CreateCandidateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCandidateService {

    @Value("${host.api.gestao.vagas}")
    private String host;

    public void execute(CreateCandidateDTO candidate) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String url = host.concat("/candidate");

        HttpEntity<CreateCandidateDTO> request = new HttpEntity<>(candidate, headers);

        String result = rt.postForObject(url, request, String.class);
        System.out.println(result);
    }
}
