/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Usuario;
import io.denuncie.dto.UsuarioDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author filipeoa
 */
public class UsuarioDAO {

    EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void persiste(Usuario usuario) throws Exception {
        try {
            em.persist(usuario);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public Usuario salvar(Usuario usuario) throws Exception {
        try {
            usuario = em.merge(usuario);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
        return usuario;
    }

    public Usuario carregarPeloId(Long id) {
        return em.find(Usuario.class, id);
    }

    public void remover(Usuario usuario) throws Exception {
        try {
            em.remove(usuario);
        } catch (Exception e) {
            throw new Exception("Erro na transação: " + e.getMessage());
        }
    }

    public List<UsuarioDTO> listarTodos() {
        Query query = em.createQuery("SELECT new io.denuncie.dto.UsuarioDTO (u) FROM Usuario u ORDER BY u.nome");
        return query.getResultList();
    }

}
