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
public class UserDto {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String senha;

    private Double reputacao = 5.0;

    private Integer horasSemana;

    private Double pesoServicos;

    private Double pesoOrcamento;

    private Double pesoHistorico;

    private List<String> diasTrabalho;

    public UserDto() {
    }

    public UserDto(Long id, String nome, String email, String telefone, String senha, Integer horasSemana, Double pesoServicos, Double pesoOrcamento, Double pesoHistorico, List<String> diasTrabalho) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.horasSemana = horasSemana;
        this.pesoServicos = pesoServicos;
        this.pesoOrcamento = pesoOrcamento;
        this.pesoHistorico = pesoHistorico;
        this.diasTrabalho = diasTrabalho;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getReputacao() {
        return reputacao;
    }

    public void setReputacao(Double reputacao) {
        this.reputacao = reputacao;
    }

    public Integer getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(Integer horasSemana) {
        this.horasSemana = horasSemana;
    }

    public Double getPesoServicos() {
        return pesoServicos;
    }

    public void setPesoServicos(Double pesoServicos) {
        this.pesoServicos = pesoServicos;
    }

    public Double getPesoOrcamento() {
        return pesoOrcamento;
    }

    public void setPesoOrcamento(Double pesoOrcamento) {
        this.pesoOrcamento = pesoOrcamento;
    }

    public Double getPesoHistorico() {
        return pesoHistorico;
    }

    public void setPesoHistorico(Double pesoHistorico) {
        this.pesoHistorico = pesoHistorico;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<String> getDiasTrabalho() {
        return diasTrabalho;
    }

    public void setDiasTrabalho(List<String> diasTrabalho) {
        this.diasTrabalho = diasTrabalho;
    }

}
