/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.ProjetoResposta;
import com.main.servicoFinalFront.model.PropostaEnvioDto;
import com.main.servicoFinalFront.model.PropostaRespostaDto;
import com.main.servicoFinalFront.model.PropostaScoreDto;
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
import org.springframework.web.bind.annotation.PathVariable;
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
    private AuthService authService;

    @GetMapping("/proposta")
    public String paginaProposta(@RequestParam Long projetoId, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                ProjetoResposta projeto = authService.listarprojetoPorId(projetoId, tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("projeto", projeto);
                model.addAttribute("dto", new PropostaEnvioDto());
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/projetoFiltro";
        }
        return "proposta";
    }

    @PostMapping("/adicionarProposta")
    public String adicionarProposta(@ModelAttribute PropostaEnvioDto dto, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.adicionarProposta(tk, dto);
                return null;
            });
        } catch (HttpClientErrorException e) {
            model.addAttribute("errorMessage", "Erro ao enviar proposta");
            return "perfil";
        }
        return "redirect:/projetoFiltro";
    }

    @GetMapping("/propostaFiltro")
    public String listarPropostas(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                List<PropostaRespostaDto> propostas = authService.listarProjetoFiltro(tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("propostas", propostas);
                return null;
            });

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
                session.invalidate();
                return "redirect:/logar";
            }
            model.addAttribute("erro", "Erro ao carregar propostas.");
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar propostas.");
        }
        return "propostaFiltro";
    }

    @PostMapping("/aceitar/{id}")
    public String aceitarProposta(@PathVariable Long id, HttpSession session, @RequestParam Long projetoId) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.aceitarProposta(id, tk);
                return null;
            });

        } catch (HttpClientErrorException e) {
                session.invalidate();
                return "redirect:/logar";
        }
        return "redirect:/propostas/" + projetoId;
    }

    @GetMapping("/propostasUsuario")
    public String listarPropostasGeral(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                List<PropostaRespostaDto> propostas = authService.listarPropostas(tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("propostas", propostas);
                return null;
            });

        } catch (HttpClientErrorException e) {
                session.invalidate();
                return "redirect:/logar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar propostas.");
        }
        return "propostasUsuario";
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarProposta(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.cancelarProposta(id, tk);
                return null;
            });
        } catch (HttpClientErrorException e) {
                session.invalidate();
                return "redirect:/logar";
        }
        return "redirect:/propostasUsuario";

    }

    @PostMapping("/recusar/{id}")
    public String recusarProposta(@PathVariable Long id, HttpSession session, @RequestParam Long projetoId) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.recusarProposta(id, tk);
                return null;
            });
        } catch (HttpClientErrorException e) {
                session.invalidate();
                return "redirect:/logar";
        }
        return "redirect:/projetoporId/" + projetoId;

    }

    @GetMapping("/propostas/{id}")
public String propostasDoProjetoComScore(@PathVariable Long id, HttpSession session, Model model) {
    String token = (String) session.getAttribute("token");
    if (token == null) return "redirect:/logar";
    try {
        authService.executarComRefresh(session, tk -> {
            List<PropostaScoreDto> propostas = authService.listarPropostasComScore(tk, id);
            boolean temPropostaAceita = propostas.stream()
                    .anyMatch(p -> "ACEITA".equals(p.getStatus()));
            long naoLidas = authService.contarNaoLidas(tk);
            model.addAttribute("naoLidas", naoLidas);
            model.addAttribute("propostas", propostas);
            model.addAttribute("projetoId", id);
            model.addAttribute("temPropostaAceita", temPropostaAceita);
            return null;
        });

    } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
    }
    return "propostaScore";
}
}
