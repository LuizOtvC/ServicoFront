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
public class UserUpdDto {
    
    private String nome;
    private String descricao;
    private String telefone;
     private List<String> diasTrabalho;

    public UserUpdDto(String nome, String descricao, String telefone, List<String> diasTrabalho) {
        this.nome = nome;
        this.descricao = descricao;
        this.telefone = telefone;
        this.diasTrabalho = diasTrabalho;
    }

    

    

    

    public UserUpdDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    

  

    
    
}
