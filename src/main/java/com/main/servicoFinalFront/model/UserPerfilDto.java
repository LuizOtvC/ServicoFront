/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Mateus
 */
public class UserPerfilDto {
    
    private String nome;
    private String email;
    private String telefone;
    private Double reputacao;
    private Integer horasSemana;
    private Long id;

    public UserPerfilDto() {
    }

    public UserPerfilDto(String nome, String email, String telefone, Double reputacao, Integer horasSemana, Long id) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.reputacao = reputacao;
        this.horasSemana = horasSemana;
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

    public Integer getHorasSemana() {
        return horasSemana;
    }

    public void setHorasSemana(Integer horasSemana) {
        this.horasSemana = horasSemana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    
}
