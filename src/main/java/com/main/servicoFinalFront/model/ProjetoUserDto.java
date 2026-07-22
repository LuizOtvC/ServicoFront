package com.main.servicoFinalFront.model;

import java.util.List;
import java.util.Set;

public class ProjetoUserDto {

    private String titulo;
    private String descricao;
    private Double orcamento;
    private String status;
    private List<Long> servicosId;
    private Set<String> diasTrabalho;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public List<Long> getServicosId() {
        return servicosId;
    }

    public void setServicosId(List<Long> servicosId) {
        this.servicosId = servicosId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(Set<String> diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
    }
}