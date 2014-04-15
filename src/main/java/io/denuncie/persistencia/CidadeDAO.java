/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Cidade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Filipe
 */
public class CidadeDAO {

    EntityManager em;

    public CidadeDAO(EntityManager em) {
        this.em = em;
    }

    public void persiste(Cidade cidade) throws Exception {
        try {
            em.persist(cidade);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public Cidade salvar(Cidade cidade) throws Exception {
        try {
            cidade = em.merge(cidade);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return cidade;
    }

    public Cidade carregarPeloId(Long id) {
        return em.find(Cidade.class, id);
    }

    public void remover(Cidade cidade) throws Exception {
        try {
            em.remove(cidade);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public List<Cidade> listarTodos() {
        Query query = em.createQuery("FROM Cidade c ORDER BY c.nome");
        return query.getResultList();
    }

}
