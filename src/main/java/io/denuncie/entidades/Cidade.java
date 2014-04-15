/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Filipe
 */
@Entity
public class Cidade implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @ManyToOne
    private Estado estado;

    public Cidade() {
    }

    public Cidade(Long id) {
        this.id = id;
    }

    public Cidade(String nome, Estado estado) {
        this.nome = nome;
        this.estado=estado;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
