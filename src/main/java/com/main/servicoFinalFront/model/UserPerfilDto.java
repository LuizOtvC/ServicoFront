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
public class UserPerfilDto {

    private String nome;
    private String descricao;
    private String email;
    private String telefone;
    private Double reputacao;
    private Long id;
    private List<String> diasTrabalho;
    private String status;
    private String cidade;

    public UserPerfilDto() {
    }

    public UserPerfilDto(String nome, String descricao, String email, String telefone, Double reputacao, Long id, List<String> diasTrabalho, String status, String cidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.email = email;
        this.telefone = telefone;
        this.reputacao = reputacao;
        this.id = id;
        this.diasTrabalho = diasTrabalho;
        this.status = status;
        this.cidade = cidade;
    }

    

   
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Double getReputacao() {
        return reputacao;
    }

    public void setReputacao(Double reputacao) {
        this.reputacao = reputacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(List<String> diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    

    
}
