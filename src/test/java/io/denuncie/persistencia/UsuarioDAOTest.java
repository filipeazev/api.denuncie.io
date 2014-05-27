/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.dto.UsuarioDTO;
import io.denuncie.entidades.Usuario;
import io.denuncie.util.Constantes;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Filipe
 */
public class UsuarioDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static UsuarioDAO dao;

    public UsuarioDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new UsuarioDAO(em);
    }

    @AfterClass
    public static void tearDownClass() {
        em.close();
        emf.close(); 
    }

    @Before
    public void setUp() {
        tx = em.getTransaction();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of listarTodos method, of class UsuarioDAO.
     */
    @Test
    public void testListarTodos() throws Exception {
        tx.begin();
        Usuario usuario1 = new Usuario("Teste usuario1", "teste1@email.com", new Date(), "123456");
        Usuario usuario2 = new Usuario("Teste usuario2", "teste2@email.com", new Date(), "123456");
        Usuario usuario3 = new Usuario("Teste usuario3", "teste3@email.com", new Date(), "123456");
        dao.persiste(usuario1);
        dao.persiste(usuario2);
        dao.persiste(usuario3);
        tx.commit();
        List<UsuarioDTO> expResult = dao.listarTodos();
        //List<Usuario> result = instance.listarTodos();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        for(UsuarioDTO c:expResult){
            System.out.println(c.getId() + " " + c.getNome());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class UsuarioDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Usuario usuario = new Usuario("Teste de novo usuario", "novo@email.com", new Date(), "123456");
        tx.begin();
        dao.persiste(usuario);
        tx.commit();
        em.refresh(usuario);
        assertTrue("O objeto não foi persistido", usuario.getId() != null);
    }

    /**
     * Test of salvar method, of class UsuarioDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
       Usuario usuario = new Usuario("Teste de update usuario", "update@email.com", new Date(), "123456");
        dao.persiste(usuario);
        tx.commit();
        em.refresh(usuario);
        tx.begin();
        usuario.setNome("Update");
        usuario = dao.salvar(usuario);
        tx.commit();
        em.refresh(usuario);
        assertEquals("Update", usuario.getNome());
    }

    /**
     * Test of carregarPeloId method, of class UsuarioDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Usuario usuario = new Usuario("Teste carregar usuario por id", "carregar@email.com", new Date(), "123456");
        dao.persiste(usuario);
        tx.commit();
        em.refresh(usuario);
        Long id = usuario.getId();
        Usuario result = dao.carregarPeloId(id);
        assertEquals(usuario, result);
        
    }

    /**
     * Test of remover method, of class UsuarioDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Usuario usuario = new Usuario("Teste remover usuario", "remover@email.com", new Date(), "123456");
        dao.persiste(usuario);
        tx.commit();
        em.refresh(usuario);
        tx.begin();
        dao.remover(usuario);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(usuario));
    }

}
