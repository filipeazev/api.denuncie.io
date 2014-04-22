/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Filipe
 */
@Entity
public class Usuario implements Serializable {
    public Usuario(){
    }
    public Usuario(String nome,String email,Date dataCadastro, String senha){
        this.nome=nome;
        this.email=email;
        this.dataCadastro=dataCadastro;
        this.senha=senha;
    }
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private float pontos;
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
