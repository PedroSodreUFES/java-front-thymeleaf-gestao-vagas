package __gestao_vagas_frontend.modules.candidate.service;

import __gestao_vagas_frontend.modules.candidate.dto.JobDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class FindJobsService {

    @Value("${host.api.gestao.vagas}")
    private String host;

    public List<JobDTO> execute(String token, String queryParam) {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(host + "/candidate/job")
                .queryParam("filter", queryParam);

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        try {
            var response = rt.exchange(builder.toUriString(), HttpMethod.GET, request, responseType);
            return response.getBody();
        } catch(HttpClientErrorException.Unauthorized ex){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
