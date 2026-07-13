/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.ProjetoResposta;
import com.main.servicoFinalFront.model.PropostaEnvioDto;
import com.main.servicoFinalFront.model.PropostaRespostaDto;
import com.main.servicoFinalFront.model.UsuarioServico;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Mateus
 */
@Controller
public class PropostaController {
    
    @Autowired
    private AuthService service;
    
    @GetMapping("/proposta")
public String paginaProposta(@RequestParam Long projetoId, HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        ProjetoResposta projeto = service.listarprojetoPorId(projetoId, token);
        model.addAttribute("projeto", projeto);
        model.addAttribute("dto", new PropostaEnvioDto());
    } catch (HttpClientErrorException e) {
        return "redirect:/projetoFiltro";
    }
    return "proposta";
}
    
    
    @PostMapping("/adicionarProposta")
    public String adicionarProposta(@ModelAttribute PropostaEnvioDto dto, HttpSession session, Model model){
        String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";

    try {
        service.adicionarProposta(token, dto);
    } catch (HttpClientErrorException e) {
        model.addAttribute("errorMessage", "Erro ao enviar proposta");
        return "perfil";
    }
    return "redirect:/projetoFiltro";
    }
    
    @GetMapping("/propostas")
public String listarPropostas(HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        List<PropostaRespostaDto> propostas = service.listarProjetoFiltro(token);
        model.addAttribute("propostas", propostas);
    } catch (HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("erro", "Erro ao carregar propostas.");
    } catch (Exception e) {
        model.addAttribute("erro", "Erro ao carregar propostas.");
    }
    return "propostas";
}
    
}
