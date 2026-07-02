/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

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
        public String home(HttpSession session) {
            Object token = (String) session.getAttribute("token");
            if(token == null){            
         return "redirect:/logar";
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
            model.addAttribute("credenciais", user);
            return "logar";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
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
    public String PaginaAtualizar(HttpSession session, Model model){
        Object token = (String) session.getAttribute("token");
        if(token == null){            
         return "redirect:/logar";
        }
        model.addAttribute("atualizar", new UserUpdDto());
        return "atualizar";
    }
    
    @PostMapping("/atualizar")
    public String fazerRegistro(@ModelAttribute UserUpdDto user, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) return "redirect:/logar";

        
    try {
        authService.AtualizarPerfil(user, token);
        return "redirect:/";
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
    if (token == null) return "redirect:/logar";

    try {
        UserPerfilDto usuario = authService.VerPerfil(token);
        List<Servico> servicos = authService.listarServicos(token);
        List<ServicoListar> habilidades = authService.listarServicosId(token);

        model.addAttribute("usuario", usuario);
        model.addAttribute("servicos", servicos);
        model.addAttribute("habilidades", habilidades);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar o perfil.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar o perfil.");
    }

    return "perfil";
}
         
}
