/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Mateus
 */
public class ServicoListar {
    private Long ServicoId;
    private String nomeServico;
    private String nivel;

    public ServicoListar() {
    }

    public ServicoListar(Long ServicoId, String nomeServico, String nivel) {
        this.ServicoId = ServicoId;
        this.nomeServico = nomeServico;
        this.nivel = nivel;
    }

    public Long getServicoId() {
        return ServicoId;
    }

    public void setServicoId(Long ServicoId) {
        this.ServicoId = ServicoId;
    }

    

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    
    
    
    
}
