/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.MensagemRespostaDto;
import com.main.servicoFinalFront.service.AuthService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Mateus
 */
@Controller
public class MensagemController {

    @Autowired
    private AuthService authService;

    @GetMapping("/listarMensagens")
    public String listarMensagens(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.marcarMensagensComoLidas(tk);
                List<MensagemRespostaDto> mensagem = authService.listarMensagens(tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("mensagem", mensagem);
                return null;
            });
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            session.invalidate();
            return "redirect:/logar";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar mensagens.");
        }
        return "mensagens";
    }

    @PostMapping("/deletar/{id}")
    public String deletarMensagem(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            authService.executarComRefresh(session, tk -> {
                authService.apagarMensagem(tk, id);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/listarMensagens";
    }

    @PostMapping("/marcarLido")
    public String marcarMensagensComoLidas(HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.marcarMensagensComoLidas(tk);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "redirect:/";
    }

}
