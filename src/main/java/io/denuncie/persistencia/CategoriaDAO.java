/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Filipe
 */
public class CategoriaDAO {

    EntityManager em;

    public CategoriaDAO(EntityManager em) {
        this.em = em;
    }

    public void persiste(Categoria categoria) throws Exception {
        try {
            em.persist(categoria);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public Categoria salvar(Categoria categoria) throws Exception {

        try {
            categoria = em.merge(categoria);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return categoria;
    }

    public Categoria carregarPeloId(Long id) {
        return em.find(Categoria.class, id);
    }

    public void remover(Categoria categoria) throws Exception {
        try {
            em.remove(categoria);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public List<Categoria> listarTodos() {
        Query query = em.createQuery("FROM Categoria c");
        return query.getResultList();
    }

}
