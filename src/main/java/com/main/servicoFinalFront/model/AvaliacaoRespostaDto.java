/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Aluno
 */
public class AvaliacaoRespostaDto {

    private Long id;

    private Long projeto;

    private Long avaliador;

    private Long avaliado;

    private Double nota;

    private String comentario;

    public AvaliacaoRespostaDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjeto() {
        return projeto;
    }

    public void setProjeto(Long projeto) {
        this.projeto = projeto;
    }

    public Long getAvaliador() {
        return avaliador;
    }

    public void setAvaliador(Long avaliador) {
        this.avaliador = avaliador;
    }

    public Long getAvaliado() {
        return avaliado;
    }

    public void setAvaliado(Long avaliado) {
        this.avaliado = avaliado;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
