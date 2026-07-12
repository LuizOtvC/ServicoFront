/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Mateus
 */
public class PropostaEnvioDto {

    private Long projetoId;
    private Double valorProposto;
    private String descricao;

    public Long getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Long projetoId) {
        this.projetoId = projetoId;
    }

    public Double getValorProposto() {
        return valorProposto;
    }

    public void setValorProposto(Double valorProposto) {
        this.valorProposto = valorProposto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
