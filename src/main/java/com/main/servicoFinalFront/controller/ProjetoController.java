/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.model.*;
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
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 *
 * @author Mateus
 */
@Controller

public class ProjetoController {

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

    @GetMapping("/projeto/criar")
    public String telaCriarProjeto(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                List<Servico> servicos = authService.listarServicos(tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("servicos", servicos);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        model.addAttribute("projeto", new ProjetoUserDto());
        return "criarProjeto";
    }

    @PostMapping("/projeto/criar")
    public String criarProjeto(@ModelAttribute ProjetoUserDto projeto, HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            authService.executarComRefresh(session, tk -> {
                authService.adicionarProjeto(projeto, tk);
                return null;
            });
            return "redirect:/projetoFiltroUser";

        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
    }

    @GetMapping("/projetoFiltro")
    public String projetoFiltro(@RequestParam(required = false) Double orcamentoMin, @RequestParam(required = false) List<Long> servicosIds, @RequestParam(required = false) List<String> diasSemana, @RequestParam(defaultValue = "0") int page, HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                UserPerfilDto usuario = authService.VerPerfil(tk);
                List<Servico> todosServicos = authService.listarServicos(tk);

                PageResponseDto<ProjetoResposta> pagina = authService.listarProjetosComFiltro(tk, orcamentoMin, servicosIds, diasSemana, page, 20);

                long naoLidas = authService.contarNaoLidas(tk);

                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("projetos", pagina.getContent());
                model.addAttribute("paginaAtual", pagina.getNumber());
                model.addAttribute("totalPaginas", pagina.getTotalPages());
                model.addAttribute("temProxima", !pagina.isLast());
                model.addAttribute("orcamentoMin", orcamentoMin);
                model.addAttribute("servicosSelecionados", servicosIds);
                model.addAttribute("diasSelecionados", diasSemana);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "projetoFiltro";
    }

    @GetMapping("/projetoporId/{id}")
    public String meusProjetosId(@PathVariable Long id, HttpSession session, Model model) {

        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }

        try {
            authService.executarComRefresh(session, tk -> {
                ProjetoResposta projeto = authService.listarprojetoPorId(id, tk);
                UserPerfilDto usuario = authService.VerPerfilId(tk, projeto.getUsuarioId());
                UserPerfilDto usuarioLogado = authService.VerPerfil(tk);
                boolean jaEnviou = authService.existeProposta(id, tk);

                boolean participou = usuarioLogado.getId().equals(projeto.getUsuarioId())
                        || (projeto.getPropostaAceita() != null
                        && usuarioLogado.getId().equals(projeto.getPropostaAceita().getUsuarioId()));

                UserPerfilDto profissional = null;
                if (projeto.getPropostaAceita() != null) {
                    Long profissionalId = projeto.getPropostaAceita().getUsuarioId();
                    profissional = authService.VerPerfilId(tk, profissionalId);
                }

                boolean jaAvaliou = false;
                if (participou && "CONCLUIDO".equals(projeto.getStatus())) {
                    jaAvaliou = authService.jaAvaliei(id, tk);
                }

                Double scoreProjeto = authService.getScoreProjeto(tk, id);
                long naoLidas = authService.contarNaoLidas(tk);

                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("scoreProjeto", scoreProjeto);
                model.addAttribute("projeto", projeto);
                model.addAttribute("usuario", usuario);
                model.addAttribute("profissional", profissional);
                model.addAttribute("usuarioLogadoId", usuarioLogado.getId());
                model.addAttribute("jaEnviouProposta", jaEnviou);
                model.addAttribute("participou", participou);
                model.addAttribute("jaAvaliou", jaAvaliou);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar projeto.");
        }

        return "projetoId";
    }

    @GetMapping("/projetoFiltroUser")
    public String listarProjetosUser(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                List<ProjetoListarDto> projetos = authService.listarProjetosFiltroUsuario(tk);
                long naoLidas = authService.contarNaoLidas(tk);
                model.addAttribute("naoLidas", naoLidas);
                model.addAttribute("projetos", projetos);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar projetos.");
        }
        return "projetoFiltroUser";
    }

    @PostMapping("/andamento/{id}")
    public String AndamentoProjeto(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.ProjetoEmAndamento(id, tk);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "redirect:/projetoFiltroUser";
    }

    @PostMapping("/concluido/{id}")
    public String ConcluidoProjeto(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.ProjetoConcluido(id, token);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "redirect:/projetoFiltroUser";

    }

    @PostMapping("/cancelarr/{id}")
    public String CancelarProjeto(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.ProjetoCancelado(id, token);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "redirect:/projetoFiltroUser";

    }

    @PostMapping("/arquivar/{id}")
    public String ArquivarProjeto(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("token");
        if (token == null) {
            return "redirect:/logar";
        }
        try {
            authService.executarComRefresh(session, tk -> {
                authService.ProjetoArquivado(id, token);
                return null;
            });
        } catch (HttpClientErrorException e) {
            session.invalidate();
            return "redirect:/logar";
        }
        return "redirect:/projetoFiltroUser";

    }
}
