/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.controller;

import com.main.servicoFinalFront.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author Aluno
 */
@Controller
public class AvaliacaoController {

    @Autowired
    private AuthService service;

    @PostMapping("/criar/{id}")
    public String avaliar(@PathVariable Long id,@RequestParam Double nota,@RequestParam(required = false) String comentario,HttpSession session) {

        String token = (String) session.getAttribute("token");

        if (token == null) {
            return "redirect:/logar";
        }

        try {
            service.Avaliar(id, nota, comentario, token);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatusCode.valueOf(401)) {
                session.invalidate();
                return "redirect:/logar";
            }
        }

        return "redirect:/projetoporId/" + id;
    }
}
