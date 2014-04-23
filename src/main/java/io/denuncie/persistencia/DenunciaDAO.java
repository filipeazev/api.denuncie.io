/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.persistencia;

import io.denuncie.dto.DenunciaDTO;
import io.denuncie.entidades.Cidade;
import io.denuncie.entidades.Denuncia;
import io.denuncie.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author filipeoa
 */
public class DenunciaDAO {
    EntityManager em;
    
    public DenunciaDAO(EntityManager em){
        this.em = em;
    }
    
    public void persiste(Denuncia denuncia) throws Exception {
        try {
            em.persist(denuncia);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public Denuncia salvar(Denuncia denuncia) throws Exception{
        try{
            denuncia = em.merge(denuncia);
        }catch(Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return denuncia;
    }
    
    public Denuncia carregarPeloId(Long id){
        return em.find(Denuncia.class, id);
    }
    
    public void remover(Denuncia denuncia) throws Exception{
        try{
            em.remove(denuncia);
        } catch(Exception e){
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public List<DenunciaDTO> listarTodos() {
        Query query = em.createQuery("SELECT new io.denuncie.dto.DenunciaDTO (d) FROM Denuncia d");
        return query.getResultList();
    }
    
    public List<DenunciaDTO> listarPorUsuario(Usuario usuario) {
        Query query = em.createQuery("SELECT new io.denuncie.dto.DenunciaDTO (d) FROM Denuncia d WHERE d.usuario.id = :u");
        query.setParameter("u", usuario.getId());
        return query.getResultList();
    }
    
    //Retorna ocorrências contendo <Cidade> no enderecoStr
     public List<DenunciaDTO> listarPorCidade(Cidade cidade) {
        Query query = em.createQuery("SELECT new io.denuncie.dto.DenunciaDTO (d) FROM Denuncia d WHERE d.endereco LIKE :cidade");
        query.setParameter("cidade", "%"+cidade.getNome()+"%");
        return query.getResultList();
    }
    
    
   
    
    
}
