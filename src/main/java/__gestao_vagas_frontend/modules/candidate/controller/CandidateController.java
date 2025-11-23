package __gestao_vagas_frontend.modules.candidate.controller;

import __gestao_vagas_frontend.modules.candidate.dto.CreateCandidateDTO;
import __gestao_vagas_frontend.modules.candidate.dto.JobDTO;
import __gestao_vagas_frontend.modules.candidate.dto.LoginResponse;
import __gestao_vagas_frontend.modules.candidate.dto.ProfileResponseDTO;
import __gestao_vagas_frontend.modules.candidate.service.*;
import __gestao_vagas_frontend.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CandidateProfileService candidateProfileService;

    @Autowired
    private FindJobsService findJobsService;

    @Autowired
    private ApplyJobService applyJobService;

    @Autowired
    private CreateCandidateService createCandidateService;

    @GetMapping("/login")
    public String login() {
        return "candidate/login";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("candidate", new CreateCandidateDTO());
        return "candidate/create";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password){
        try {
            LoginResponse response = this.loginService.login(username, password);

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
    public String profile(Model model) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            ProfileResponseDTO user = this.candidateProfileService.execute(authentication.getDetails().toString());

            model.addAttribute("user", user);
            return "candidate/profile";
        } catch (HttpClientErrorException ex){
            return "redirect:/candidate/login";
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String jobs(Model model, String filter) {
        try {
            if (filter != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String token = authentication.getDetails().toString();
                List<JobDTO> jobs = this.findJobsService.execute(token, filter);
                model.addAttribute("jobs", jobs);
            }
        } catch (HttpClientErrorException ex){
            return "redirect:/candidate/login";
        }
        return "candidate/jobs";
    }

    @PostMapping("/jobs/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String applyJob(@RequestParam("jobId") UUID jobId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getDetails().toString();
        this.applyJobService.execute(token, jobId);
        return "redirect:/candidate/jobs";
    }

    @PostMapping("/create")
    public String createCandidate(CreateCandidateDTO candidate, Model model){
        try {
            this.createCandidateService.execute(candidate);
        } catch(HttpClientErrorException ex) {
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(ex.getResponseBodyAsString()));
        }
        model.addAttribute("candidate", candidate);
        return "candidate/create";
    }
}
