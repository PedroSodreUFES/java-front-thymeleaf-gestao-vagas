package __gestao_vagas_frontend.modules.company.services;

import __gestao_vagas_frontend.modules.candidate.dto.JobDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ListAllJobsByCompanyIdService {

    @Value("${host.api.gestao.vagas}")
    private String host;

    public List<JobDTO> execute(String token) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> request = new HttpEntity<>(headers);

        String url = host.concat("/company/job");

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        var response = rt.exchange(url, HttpMethod.GET, request, responseType);

        return response.getBody();
    }
}
