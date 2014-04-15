/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.persistencia;

import io.denuncie.entidades.Estado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Filipe
 */
public class EstadoDAO {
    EntityManager em;
    
    public EstadoDAO(EntityManager em){
        this.em = em;
    }
    
    public void persiste(Estado estado) throws Exception {
        try {
            em.persist(estado);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public Estado salvar(Estado estado) throws Exception{
        try{
            estado = em.merge(estado);
        }catch(Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return estado;
    }
    
    public Estado carregarPeloId(Long id){
        return em.find(Estado.class, id);
    }
    
    public void remover(Estado estado) throws Exception{
        try{
            em.remove(estado);
        } catch(Exception e){
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }
    
    public List<Estado> listarTodos() {
        Query query = em.createQuery("FROM Estado e ORDER BY e.nome");
        return query.getResultList();
    }
    
}
