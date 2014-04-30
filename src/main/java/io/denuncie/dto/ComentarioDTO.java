/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.dto;

import io.denuncie.entidades.*;
import java.io.Serializable;

/**
 *
 * @author Filipe
 */
public class ComentarioDTO implements Serializable {

    private Long id;
    private String comentario;
    private Long usuario_id;
    private boolean inativo;
    private int reportado;
    private Long denuncia_id;

    public ComentarioDTO(Comentario c) {
        this.id = c.getId();
        this.comentario = c.getComentario();
        this.usuario_id = c.getUsuario().getId();
        this.inativo = c.isInativo();
        this.reportado = c.getReportado();
        this.denuncia_id = c.getDenuncia().getId();
    }

    public boolean isInativo() {
        return inativo;
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

    public Long getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Long usuario_id) {
        this.usuario_id = usuario_id;
    }

    public Long getDenuncia_id() {
        return denuncia_id;
    }

    public void setDenuncia_id(Long denuncia_id) {
        this.denuncia_id = denuncia_id;
    }

}
