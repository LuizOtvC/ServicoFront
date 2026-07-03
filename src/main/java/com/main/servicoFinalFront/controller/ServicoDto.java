/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.Servico;
import com.main.servicoFinalFront.model.ServicoListar;
import com.main.servicoFinalFront.model.UsuarioServico;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Mateus
 */
@Controller
public class ServicoDto {
    @Autowired
    private AuthService authService;
    
    @GetMapping("/habilidades")
public String telaAdicionarHabilidade(Model model, HttpSession session) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        List<Servico> servicos = authService.listarServicos(token);
        model.addAttribute("servicos", servicos);
        model.addAttribute("dto", new UsuarioServico());
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
    }
    return "habilidades";
}
    
    @PostMapping("/habilidades")
    public String adicionarHabilidade(@ModelAttribute UsuarioServico dto, HttpSession session, Model model) {

    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";

    try {
        authService.adicionarServico(dto, token);
    } catch (HttpClientErrorException e) {
        model.addAttribute("errorMessage", "Você já possui essa habilidade no perfil!");
        model.addAttribute("usuario", authService.VerPerfil(token));
        model.addAttribute("servicos", authService.listarServicos(token));
        return "perfil";
    }
    return "redirect:/perfil";
}
    
}  
       
            
   
