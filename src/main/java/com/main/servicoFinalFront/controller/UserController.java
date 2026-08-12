/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.ProjetoListarDto;
import com.main.servicoFinalFront.model.ProjetoResposta;
import com.main.servicoFinalFront.model.PropostaRespostaDto;
import com.main.servicoFinalFront.model.Servico;
import com.main.servicoFinalFront.model.ServicoListar;
import com.main.servicoFinalFront.model.UsuarioServico;
import com.main.servicoFinalFront.model.UserDto;
import com.main.servicoFinalFront.model.UserLogarDto;
import com.main.servicoFinalFront.model.UserPerfilDto;
import com.main.servicoFinalFront.model.UserRegistroDto;
import com.main.servicoFinalFront.model.UserUpdDto;
import com.main.servicoFinalFront.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Mateus
 */
@Controller
public class UserController {

    @Autowired
    private AuthService authService;

    private String extrairMensagemDeErro(HttpClientErrorException e) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(e.getResponseBodyAsString());
            if (root.has("message")) {
                return root.get("message").asText();
            }
        } catch (Exception ex) {
        }
        return "Ocorreu um erro inesperado na comunicação.";
    }

    @GetMapping("/logar")
    public String paginaLogin(Model model) {
        model.addAttribute("user", new UserLogarDto());
        return "logar";
    }

    @GetMapping("/")
public String home(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) {
        return "redirect:/logar";
    }
    try {
        UserPerfilDto usuario = authService.VerPerfil(token);

        List<ProjetoListarDto> projetos = authService.listarProjetosFiltroUsuario(token);
        if (projetos.size() > 4) {
            projetos = projetos.subList(0, 4);
        }

        List<PropostaRespostaDto> propostas = authService.listarProjetoFiltro(token);
        if (propostas.size() > 4) {
            propostas = propostas.subList(0, 4);
        }

        long naoLidas = authService.contarNaoLidas(token);

        model.addAttribute("usuario", usuario);
        model.addAttribute("projetos", projetos);
        model.addAttribute("propostas", propostas);
        model.addAttribute("naoLidas", naoLidas);

    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar a página inicial.");
    } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("erro", "Erro ao carregar a página inicial.");
    }
    return "home";
}
    @PostMapping("/logar")
    public String fazerLogin(@ModelAttribute UserLogarDto user, HttpSession session, Model model) {
        try {
            String token = authService.logar(user);
            session.setAttribute("token", token);
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("user", user);
            return "logar";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.setAttribute("token", "");
        return "redirect:/logar";
    }

    @GetMapping("/registro")
    public String paginaRegistro(Model model) {
        model.addAttribute("registro", new UserDto());
        return "registro";
    }

    @PostMapping("/registro")
    public String fazerRegistro(@ModelAttribute UserRegistroDto user, HttpSession session, Model model) {
        try {
            String token = authService.Registrar(user);
            session.setAttribute("token", token);
            return "redirect:/logar";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("registro", user);
            return "registro";
        }

    }

    @GetMapping("/atualizar")
    public String PaginaAtualizar(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            UserPerfilDto usuario = authService.VerPerfil(token);
            List<Servico> servicos = authService.listarServicos(token);
            List<ServicoListar> habilidades = authService.listarServicosId(token);

            model.addAttribute("usuario", usuario);
            model.addAttribute("servicos", servicos);
            model.addAttribute("habilidades", habilidades);

            UserUpdDto atualizar = new UserUpdDto();
            atualizar.setNome(usuario.getNome());
            atualizar.setTelefone(usuario.getTelefone());
            atualizar.setDescricao(usuario.getDescricao());
            List<String> dias = usuario.getDiasTrabalho()
                    .stream()
                    .map(String::toUpperCase)
                    .toList();

            atualizar.setDiasTrabalho(dias);
            model.addAttribute("atualizar", atualizar);
            return "atualizar";
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
                session.invalidate();
                return "redirect:/logar";
            }
            model.addAttribute("erro", "Erro ao carregar perfil.");
            return "perfil";
        }
    }

    @PostMapping("/atualizar")
    public String fazerRegistro(@ModelAttribute UserUpdDto user, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            authService.AtualizarPerfil(user, token);
            return "redirect:/perfil";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("atualizar", user);
            return "atualizar";
        }

    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            UserPerfilDto usuario = authService.VerPerfil(token);
            List<Servico> servicos = authService.listarServicos(token);
            List<ServicoListar> habilidades = authService.listarServicosId(token);
            long naoLidas = authService.contarNaoLidas((String) token);

            model.addAttribute("usuario", usuario);
            model.addAttribute("servicos", servicos);
            model.addAttribute("habilidades", habilidades);
            model.addAttribute("naoLidas", naoLidas);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
                session.invalidate();
                return "redirect:/logar";
            }
            model.addAttribute("erro", "Erro ao carregar o perfil.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar o perfil.");
        }

        return "perfil";
    }

    @GetMapping("/perfilId/{id}")
    public String perfilPorId(@PathVariable Long id, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            UserPerfilDto usuario = authService.VerPerfilId(token, id);
            List<ServicoListar> habilidades = authService.listarServicosIdPorUsuario(token, id);
            long naoLidas = authService.contarNaoLidas((String) token);
            model.addAttribute("usuario", usuario);
            model.addAttribute("habilidades", habilidades);
            model.addAttribute("naoLidas", naoLidas);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
                session.invalidate();
                return "redirect:/logar";
            }
            model.addAttribute("erro", "Erro ao carregar perfil.");
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar perfil.");
        }
        return "perfilId";
    }

}
