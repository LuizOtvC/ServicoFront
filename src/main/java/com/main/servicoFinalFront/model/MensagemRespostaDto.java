/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

import java.time.LocalDateTime;

/**
 *
 * @author Mateus
 */
public class MensagemRespostaDto {
    private Long id;
    private String nomeProjeto;
    private Long projetoId;
    private String mensagem;
    private String status;
    private LocalDateTime enviadoEm;

    public MensagemRespostaDto(Long id, String nomeProjeto, Long projetoId, String mensagem,
                                String status, LocalDateTime enviadoEm) {
        this.id = id;
        this.nomeProjeto = nomeProjeto;
        this.projetoId = projetoId;
        this.mensagem = mensagem;
        this.status = status;
        this.enviadoEm = enviadoEm;
    }

    public Long getId() { return id; }
    public String getNomeProjeto() { return nomeProjeto; }
    public Long getProjetoId() { return projetoId; }
    public String getMensagem() { return mensagem; }
    public String getStatus() { return status; }
    public LocalDateTime getEnviadoEm() { return enviadoEm; }
    
}
