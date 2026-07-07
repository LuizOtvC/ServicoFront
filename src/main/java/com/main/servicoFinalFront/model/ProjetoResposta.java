/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

import java.util.List;

/**
 *
 * @author Mateus
 */
public class ProjetoResposta {
    
    private Long id;
    private String titulo;
    private String descricao;
    private Double orcamento;
    private Integer horasEstimadas;
    private String status;
    private List<String> servicos;

    public ProjetoResposta(Long id, String titulo, String descricao, Double orcamento,
                           Integer horasEstimadas, String status, List<String> servicos) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.orcamento = orcamento;
        this.horasEstimadas = horasEstimadas;
        this.status = status;
        this.servicos = servicos;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Double getOrcamento() { return orcamento; }
    public Integer getHorasEstimadas() { return horasEstimadas; }
    public String getStatus() { return status; }
    public List<String> getServicos() { return servicos; }
    
}
