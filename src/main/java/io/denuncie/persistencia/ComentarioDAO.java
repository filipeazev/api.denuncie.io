/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.persistencia;

import io.denuncie.entidades.Comentario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Filipe
 */
public class ComentarioDAO {
    EntityManager em;
    
    public ComentarioDAO(EntityManager em){
        this.em = em;
    }
    
    public void persiste(Comentario comentario) throws Exception {
        try {
            em.persist(comentario);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public Comentario salvar(Comentario comentario) throws Exception{
        try{
            comentario = em.merge(comentario);
        }catch(Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return comentario;
    }
    
    public Comentario carregarPeloId(Long id){
        return em.find(Comentario.class, id);
    }
    
    public void remover(Comentario comentario) throws Exception{
        try{
            em.remove(comentario);
        } catch(Exception e){
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public List<Comentario> listarPorDenuncia(Long idDenuncia) {
        Query query = em.createQuery("FROM Comentario c WHERE c.denuncia.id = :id");
        query=query.setParameter("id", idDenuncia);
        return query.getResultList();
    }
    
}
