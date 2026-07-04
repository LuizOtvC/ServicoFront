/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.main.servicoFinalFront.model;

/**
 *
 * @author Mateus
 */
public class ServicoAtualizar {
    private Long idAntigo;
    private Long idNovo;
    private String nivel;

    public ServicoAtualizar() {
    }

    public ServicoAtualizar(Long idAntigo, Long idNovo, String nivel) {
        this.idAntigo = idAntigo;
        this.idNovo = idNovo;
        this.nivel = nivel;
    }

   
    public Long getIdAntigo() {
        return idAntigo;
    }

    public void setIdAntigo(Long idAntigo) {
        this.idAntigo = idAntigo;
    }

    

    public Long getIdNovo() {
        return idNovo;
    }

    public void setIdNovo(Long idNovo) {
        this.idNovo = idNovo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    

    
    
    
}
