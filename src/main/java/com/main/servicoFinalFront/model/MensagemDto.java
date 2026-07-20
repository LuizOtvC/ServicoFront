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
public class MensagemDto {
    private Long id;
    private Long projetoId;
    private String mensagem;
    private String status;
    private LocalDateTime enviadoEm;

    public MensagemDto() {
    }

    public MensagemDto(Long id, Long projetoId, String mensagem, String status, LocalDateTime enviadoEm) {
        this.id = id;
        this.projetoId = projetoId;
        this.mensagem = mensagem;
        this.status = status;
        this.enviadoEm = enviadoEm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getEnviadoEm() {
        return enviadoEm;
    }

    public void setEnviadoEm(LocalDateTime enviadoEm) {
        this.enviadoEm = enviadoEm;
    }


    
}
