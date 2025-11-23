package __gestao_vagas_frontend.modules.candidate.service;

import __gestao_vagas_frontend.modules.candidate.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Value("${host.api.gestao.vagas}")
    private String host;

    public LoginResponse login(String username, String password){
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        String url = host.concat("/candidate/auth");

        LoginResponse result = rt.postForObject(url, request, LoginResponse.class);

        return result;
    }
}
