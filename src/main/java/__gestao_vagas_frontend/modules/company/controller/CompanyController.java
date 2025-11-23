package __gestao_vagas_frontend.modules.company.controller;

import __gestao_vagas_frontend.modules.candidate.dto.LoginResponse;
import __gestao_vagas_frontend.modules.company.dto.CreateCompanyDTO;
import __gestao_vagas_frontend.modules.company.services.CompanyLoginService;
import __gestao_vagas_frontend.modules.company.services.CreateCompanyService;
import __gestao_vagas_frontend.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyService createCompanyService;

    @Autowired
    private CompanyLoginService companyLoginService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String createUser(Model model, CreateCompanyDTO createCompanyDTO) {
        try {
            this.createCompanyService.execute(createCompanyDTO);
        } catch (HttpClientErrorException ex){
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(ex.getResponseBodyAsString()));
        }
        model.addAttribute("company", createCompanyDTO);
        return "company/create";
    }

    @GetMapping("/login")
    public String login(){
        return "company/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password){
        try {
            LoginResponse response = this.companyLoginService.execute(username, password);
            System.out.println(response);
//            var grants = response
//                    .getRoles()
//                    .stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
//                    .toList();
//
//            UsernamePasswordAuthenticationToken auth =
//                    new UsernamePasswordAuthenticationToken(null,null, grants);
//            auth.setDetails(response.getAccessToken());
//
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//            session.setAttribute("token", response);

            return "redirect:/company/jobs";
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Usuário/senha incorretos");
            return "redirect:/company/login";
        }
    }
}
