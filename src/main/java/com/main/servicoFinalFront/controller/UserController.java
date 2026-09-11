/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.*;
import com.main.servicoFinalFront.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
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
            authService.executarComRefresh(session, tk -> {
                UserPerfilDto usuario = authService.VerPerfil(tk);
                List<ProjetoListarDto> projetos = authService.listarProjetosFiltroUsuario(tk);
                List<PropostaRespostaDto> propostas = authService.listarProjetoFiltro(tk);

                List<ProjetoListarDto> projetosFinal = projetos == null ? new ArrayList<>() : projetos;
                List<PropostaRespostaDto> propostasFinal = propostas == null ? new ArrayList<>() : propostas;

                if (projetosFinal.size() > 4) projetosFinal = projetosFinal.subList(0, 4);
                if (propostasFinal.size() > 4) propostasFinal = propostasFinal.subList(0, 4);

                long naoLidas = authService.contarNaoLidas(tk);

                model.addAttribute("usuario", usuario);
                model.addAttribute("projetos", projetosFinal);
                model.addAttribute("propostas", propostasFinal);
                model.addAttribute("naoLidas", naoLidas);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar a página inicial.");
            model.addAttribute("projetos", new ArrayList<>());
            model.addAttribute("propostas", new ArrayList<>());
        }

        return "home";
    }
    @PostMapping("/logar")
    public String fazerLogin(@ModelAttribute UserLogarDto user, HttpSession session, Model model) {
        try {
            TokenResponseDto tokens = authService.logar(user);
            session.setAttribute("token", tokens.getAccessToken());
            session.setAttribute("refreshToken", tokens.getRefreshToken());
            return "redirect:/";
        } catch (HttpClientErrorException e) {
            String msg = extrairMensagemDeErro(e);
            model.addAttribute("errorMessage", msg);
            model.addAttribute("user", user);
            return "logar";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
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
            authService.executarComRefresh(session, tk -> {
                UserPerfilDto usuario = authService.VerPerfil(tk);
                List<Servico> servicos = authService.listarServicos(tk);
                List<ServicoListar> habilidades = authService.listarServicosId(tk);

                model.addAttribute("usuario", usuario);
                model.addAttribute("servicos", servicos);
                model.addAttribute("habilidades", habilidades);

                UserUpdDto atualizar = new UserUpdDto();
                atualizar.setNome(usuario.getNome());
                atualizar.setTelefone(usuario.getTelefone());
                atualizar.setDescricao(usuario.getDescricao());
                List<String> dias = usuario.getDiasTrabalho().stream().map(String::toUpperCase).toList();
                atualizar.setDiasTrabalho(dias);
                atualizar.setCidade(usuario.getCidade());
                model.addAttribute("atualizar", atualizar);
                return null;
            });
            return "atualizar";
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
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
            authService.executarComRefresh(session, tk -> {
                UserPerfilDto usuario = authService.VerPerfil(tk);
                List<Servico> servicos = authService.listarServicos(tk);
                List<ServicoListar> habilidades = authService.listarServicosId(tk);
                long naoLidas = authService.contarNaoLidas(tk);

                model.addAttribute("usuario", usuario);
                model.addAttribute("servicos", servicos);
                model.addAttribute("habilidades", habilidades);
                model.addAttribute("naoLidas", naoLidas);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
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
            authService.executarComRefresh(session, tk -> {
                UserPerfilDto usuario = authService.VerPerfilId(tk, id);
                List<ServicoListar> habilidades = authService.listarServicosIdPorUsuario(tk, id);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("usuario", usuario);
                model.addAttribute("habilidades", habilidades);
                model.addAttribute("naoLidas", naoLidas);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar perfil.");
        }
        return "perfilId";
    }



}
