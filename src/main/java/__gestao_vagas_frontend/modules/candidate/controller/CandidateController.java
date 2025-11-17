package __gestao_vagas_frontend.modules.candidate.controller;

import __gestao_vagas_frontend.modules.candidate.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/login")
    public String login(){
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes ,String username, String password){

        this.candidateService.login(username, password);

        redirectAttributes.addFlashAttribute("error_message", "Usuário/senha incorretos");
        return "redirect:/candidate/login";
    }
}
