/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Mateus
 */
public class PropostaScoreDto {
    private Long propostaId;
    private String nomeUsuario;
    private Double valorProposto;
    private String descricao;
    private String status;
    private Double scoreTotal;
    private Double scoreServicos;
    private Double scoreOrcamento;
    private Double scoreHistorico;

    public Long getPropostaId() {
        return propostaId;
    }

    public void setPropostaId(Long propostaId) {
        this.propostaId = propostaId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Double scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public Double getScoreServicos() {
        return scoreServicos;
    }

    public void setScoreServicos(Double scoreServicos) {
        this.scoreServicos = scoreServicos;
    }

    public Double getScoreOrcamento() {
        return scoreOrcamento;
    }

    public void setScoreOrcamento(Double scoreOrcamento) {
        this.scoreOrcamento = scoreOrcamento;
    }

    public Double getScoreHistorico() {
        return scoreHistorico;
    }

    public void setScoreHistorico(Double scoreHistorico) {
        this.scoreHistorico = scoreHistorico;
    }

    private Long usuarioId;
 public Long getUsuarioId() { return usuarioId; }
public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}
    
