/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.service;

import com.main.servicoFinalFront.controller.ServicoDto;
import com.main.servicoFinalFront.model.Servico;
import com.main.servicoFinalFront.model.ServicoListar;
import com.main.servicoFinalFront.model.UsuarioServico;
import com.main.servicoFinalFront.model.UserLogarDto;
import com.main.servicoFinalFront.model.UserPerfilDto;
import com.main.servicoFinalFront.model.UserRegistroDto;
import com.main.servicoFinalFront.model.UserUpdDto;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
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
                .baseUrl("http://localhost:9000")
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
    public UserPerfilDto VerPerfil(String token){
        return restclient.get()
                .uri("/user/perfil")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(UserPerfilDto.class);
    }
    public void AtualizarPerfil(UserUpdDto user, String token) {
        restclient.put()
                .uri("/user/atualizar")
                .header("Authorization", "Bearer " + token)
                .body(user)
                .retrieve()
                .body(String.class);
}
    public void adicionarServico(UsuarioServico servico, String token) {
        try{
    restclient.post()
            .uri("/servico/" + servico.getServicoId() + "/habilidades?nivel=" + servico.getNivel())
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .body(Void.class);
        }catch(HttpClientErrorException e){
            throw e;
        }
}
    public List<Servico> listarServicos(String token) {
    return restclient.get()
            .uri("/servico/listar")
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .body(new ParameterizedTypeReference<List<Servico>>() {});
}
    public List<ServicoListar> listarServicosId(String token) {
    return restclient.get()
            .uri("/servico/listarHabilidadesId")
            .header("Authorization", "Bearer " + token)
            .retrieve()
            .body(new ParameterizedTypeReference<List<ServicoListar>>() {});
}
    
}
