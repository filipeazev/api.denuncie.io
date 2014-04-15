/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
/**
 *
 * @author Filipe
 */
@Entity
public class Comentario implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String comentario;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Denuncia denuncia;
    private boolean inativo=false;
    private int reportado;

    public boolean isInativo() {
        return inativo;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }
    

    public void setInativo(boolean inativo) {
        this.inativo = inativo;
    }

    public int getReportado() {
        return reportado;
    }

    public void setReportado(int reportado) {
        this.reportado = reportado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
