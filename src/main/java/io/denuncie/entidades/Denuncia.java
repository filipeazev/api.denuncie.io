/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Filipe
 */
@Entity
public class Denuncia implements Serializable {

    public Denuncia() {
    }

    public Denuncia(String descricao, double latitude, double longitude, Date dataCadastro, String imagem, Categoria categoria, Usuario usuario, String endereco) {
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataCadastro = dataCadastro;
        this.imagem = imagem;
        this.categoria = categoria;
        this.usuario = usuario;
        this.endereco=endereco;
    }
    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    private double latitude;
    private double longitude;
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;
    private String imagem;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Cidade cidade;
    private int persiste;
    private int resolvido;
    private boolean inativo = false;
    private int reportado;
    private String endereco;
    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comentario> comentarios;

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    
}
