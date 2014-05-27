/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.dto;

import io.denuncie.entidades.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Filipe
 */
public class DenunciaDTO implements Serializable {

    public DenunciaDTO(Denuncia d) {
        this.id = d.getId();
        this.descricao = d.getDescricao();
        this.latitude = d.getLatitude();
        this.longitude = d.getLongitude();
        this.dataCadastro = d.getDataCadastro();
        this.imagem = d.getImagem();
        this.categoria = d.getCategoria().getId();
        this.usuario = d.getUsuario().getId();
        this.cidade = d.getCidade().getId();
        this.persiste = d.getPersiste();
        this.resolvido = d.getResolvido();
        this.inativo = d.isInativo();
        this.reportado = d.getReportado();
        this.endereco = d.getEndereco();
        if(d.getComentarios()!=null){
            this.qtdcomentarios = d.getComentarios().size();
        }
    }
    private Long id;
    private String descricao;
    private double latitude;
    private double longitude;
    private Date dataCadastro;
    private String imagem;
    private Long categoria;
    private Long usuario;
    private Long cidade;
    private int persiste;
    private int resolvido;
    private boolean inativo = false;
    private int reportado;
    private String endereco;
    private int qtdcomentarios;

    public Long getCidade() {
        return cidade;
    }

    public void setCidade(Long cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String Descricao) {
        this.descricao = Descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario.getId();
    }

    public int getPersiste() {
        return persiste;
    }

    public void setPersiste(int persiste) {
        this.persiste = persiste;
    }

    public int getResolvido() {
        return resolvido;
    }

    public void setResolvido(int resolvido) {
        this.resolvido = resolvido;
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

    public float getQtdcomentarios() {
        return qtdcomentarios;
    }

    public void setQtdcomentarios(int qtdcomentarios) {
        this.qtdcomentarios = qtdcomentarios;
    }

    

}
