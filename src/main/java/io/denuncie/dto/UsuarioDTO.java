/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.dto;

import io.denuncie.entidades.Usuario;
import java.util.Date;

/**
 *
 * @author filipe.azevedo
 */
public class UsuarioDTO {
    
    private Long id;
    private String nome;
    private float pontos;
    private Date dataCadastro;
        
    public UsuarioDTO(Usuario user){
        this.id=user.getId();
        this.nome=user.getNome();
        this.pontos=user.getPontos();
        this.dataCadastro=user.getDataCadastro();
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

    public float getPontos() {
        return pontos;
    }

    public void setPontos(float pontos) {
        this.pontos = pontos;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
}
