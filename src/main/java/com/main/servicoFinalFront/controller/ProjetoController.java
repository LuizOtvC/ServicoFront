/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.ProjetoListarDto;
import com.main.servicoFinalFront.model.ProjetoResposta;
import com.main.servicoFinalFront.model.ProjetoUserDto;
import com.main.servicoFinalFront.model.Servico;
import com.main.servicoFinalFront.model.UserPerfilDto;
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

public class ProjetoController {

    @Autowired
    private AuthService service;

    
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
    
    @GetMapping("/projeto/criar")
    public String telaCriarProjeto(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    List<Servico> servicos = service.listarServicos(token);
    model.addAttribute("servicos", servicos);
    model.addAttribute("projeto", new ProjetoUserDto());
    return "criarProjeto";
}

    @PostMapping("/projeto/criar")
    public String criarProjeto(
            @ModelAttribute ProjetoUserDto projeto, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        service.adicionarProjeto(projeto, token);
        return "redirect:/";
    }
    
    @GetMapping("/projetoFiltro")
public String listarProjetos(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        List<ProjetoListarDto> projetos = service.listarProjetosFiltro(token);
        model.addAttribute("projetos", projetos);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar projetos.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar projetos.");
    }
    return "projetoFiltro";
}

@GetMapping("/projeto")
public String meusProjetos(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        List<ProjetoListarDto> projetos = service.listarProjetos(token);
        model.addAttribute("projetos", projetos);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar projetos.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar projetos.");
    }
    return "projeto";
}

@GetMapping("/projetoporId/{id}")
public String meusProjetosId(@PathVariable Long id, HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        ProjetoResposta projeto = service.listarprojetoPorId(id, token);
        UserPerfilDto usuario = service.VerPerfilId(token, projeto.getUsuarioId());
        UserPerfilDto usuarioLogado = service.VerPerfil(token);
        boolean jaEnviou = service.existeProposta(id, token);
        
        model.addAttribute("projeto", projeto);
        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioLogadoId", usuarioLogado.getId());
        model.addAttribute("jaEnviouProposta", jaEnviou);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar projeto.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar projeto.");
    }
    return "projetoId";
}

@GetMapping("/projetoFiltroUser")
public String listarProjetosUser(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        List<ProjetoListarDto> projetos = service.listarProjetosFiltroUsuario(token);
        model.addAttribute("projetos", projetos);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar projetos.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar projetos.");
    }
    return "projetoFiltroUser";
}

@PostMapping("/andamento/{id}")
public String AndamentoProjeto(@PathVariable Long id, HttpSession session) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        service.ProjetoEmAndamento(id, token);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
    }
    return "redirect:/projetoFiltroUser";
    
}
@PostMapping("/concluido/{id}")
public String ConcluidoProjeto(@PathVariable Long id, HttpSession session) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        service.ProjetoConcluido(id, token);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
    }
    return "redirect:/projetoFiltroUser";
    
}
@PostMapping("/cancelarr/{id}")
public String CancelarProjeto(@PathVariable Long id, HttpSession session) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        service.ProjetoCancelado(id, token);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
    }
    return "redirect:/projetoFiltroUser";
    
}
}
