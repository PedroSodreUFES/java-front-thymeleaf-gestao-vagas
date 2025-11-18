package __gestao_vagas_frontend.modules.candidate.controller;

import __gestao_vagas_frontend.modules.candidate.dto.LoginResponse;
import __gestao_vagas_frontend.modules.candidate.service.CandidateProfileService;
import __gestao_vagas_frontend.modules.candidate.service.CandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateProfileService candidateProfileService;

    @GetMapping("/login")
    public String login(){
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password){
        try {
            LoginResponse response = this.candidateService.login(username, password);

            var grants = response
                    .getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                    .toList();

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(null,null, grants);
            auth.setDetails(response.getAccessToken());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", response);

            return "redirect:/candidate/profile";
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Usuário/senha incorretos");
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var result = this.candidateProfileService.execute(authentication.getDetails().toString());

        return "candidate/profile";
    }
}
