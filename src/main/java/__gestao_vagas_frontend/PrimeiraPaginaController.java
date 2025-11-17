package __gestao_vagas_frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PrimeiraPaginaController {

    // enviar informações para a página home
    @GetMapping("/home")
    public String primeiraPaginaHTML(Model model) {

        // enviar dados para a página
        model.addAttribute("mensagem", "Mensagem do controller");
        return "home-page";
    }

    @GetMapping("/login")
    public String getPaginaDeCandidato(){
        return "candidate/login";
    }

    // recupera dados do formulário.
    // nome é o nome do input lá na pagina do front
    @PostMapping("/create")
    public String cadastroNome(String nome) {
        System.out.println("Nome do candidato é: " + nome);
        return "redirect:/home";
    }

    @PostMapping("/createPerson")
    public String cadastroCandidate(Model model, Pessoa pessoa) {
        System.out.println("Nome do candidato é: " + pessoa.nome);
        System.out.println("Email do candidato é:" + pessoa.email);
        System.out.println("Username do candidato é:" + pessoa.username);

        model.addAttribute("pessoa", pessoa);
        return "candidate/info";
    }

    record Pessoa(String username, String email, String nome) {}
}
