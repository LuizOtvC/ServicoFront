/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.service;

import com.main.servicoFinalFront.model.UserLogarDto;
import com.main.servicoFinalFront.model.UserRegistroDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 *
 * @author Mateus
 */
@Service
public class AuthService {
    
     private final RestClient restclient;
     
     public AuthService() {
        this.restclient = RestClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }
     
     
     public String logar(UserLogarDto user){
         return restclient.post()
                 .uri("/user/logar")
                 .body(user)
                 .retrieve()
                .body(String.class);
     }
     
    public String Registrar(UserRegistroDto user) {
        return restclient.post()
                .uri("/user/registrar")
                .body(user)
                .retrieve()
                .body(String.class);
}
}
