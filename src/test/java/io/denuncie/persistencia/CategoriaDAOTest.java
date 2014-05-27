/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Categoria;
import io.denuncie.util.Constantes;
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
public class CategoriaDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static CategoriaDAO dao;

    public CategoriaDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new CategoriaDAO(em);
    }

    @AfterClass
    public static void tearDownClass() {
        em.close();
        emf.close(); 
    }

    @Before
    public void setUp() throws Exception {
        tx = em.getTransaction();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Test of listarTodos method, of class CategoriaDAO.
     */
    @Test
    public void testListarTodos() throws Exception {
        limpar();
        tx.begin();
        Categoria categoria1 = new Categoria("Teste categoria1");
        Categoria categoria2 = new Categoria("Teste categoria2");
        Categoria categoria3 = new Categoria("Teste categoria3");
        dao.persiste(categoria1);
        dao.persiste(categoria2);
        dao.persiste(categoria3);
        tx.commit();
        List<Categoria> expResult = dao.listarTodos();
        //List<Categoria> result = instance.listarTodos();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        for(Categoria c:expResult){
            System.out.println(c.getId() + " " + c.getNome());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class CategoriaDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Categoria categoria = new Categoria("Teste de categoria");
        tx.begin();
        dao.persiste(categoria);
        tx.commit();
        em.refresh(categoria);
        assertTrue("O objeto não foi persistido", categoria.getId() != null);
    }

    /**
     * Test of salvar method, of class CategoriaDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
        Categoria categoria = new Categoria("Teste de update de categoria");
        dao.persiste(categoria);
        tx.commit();
        em.refresh(categoria);
        tx.begin();
        categoria.setNome("Update");
        categoria = dao.salvar(categoria);
        tx.commit();
        em.refresh(categoria);
        assertEquals("Update", categoria.getNome());
    }

    /**
     * Test of carregarPeloId method, of class CategoriaDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Categoria categoria = new Categoria("Teste carregar categoria por id");
        dao.persiste(categoria);
        tx.commit();
        em.refresh(categoria);
        Long id = categoria.getId();
        Categoria result = dao.carregarPeloId(id);
        assertEquals(categoria, result);
        
    }

    /**
     * Test of remover method, of class CategoriaDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Categoria categoria = new Categoria("Teste remover categoria");
        dao.persiste(categoria);
        tx.commit();
        em.refresh(categoria);
        tx.begin();
        dao.remover(categoria);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(categoria));
    }
    
    public void limpar() throws Exception{
        List<Categoria> todos = dao.listarTodos();
        if(!todos.isEmpty()){
            tx.begin();
            for(Categoria t:todos){
                dao.remover(t);
            }
            tx.commit();
        }
    }
}
