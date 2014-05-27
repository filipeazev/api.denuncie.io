/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Cidade;
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
public class CidadeDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static CidadeDAO dao;

    public CidadeDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new CidadeDAO(em);
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
     * Test of listarTodos method, of class CidadeDAO.
     */
    @Test
    public void testListarTodos() throws Exception {
        limpar();
        tx.begin();
        Cidade cidade1 = new Cidade("Teste cidade1", null);
        Cidade cidade2 = new Cidade("Teste cidade2", null);
        Cidade cidade3 = new Cidade("Teste cidade3", null);
        dao.persiste(cidade1);
        dao.persiste(cidade2);
        dao.persiste(cidade3);
        tx.commit();
        List<Cidade> expResult = dao.listarTodos();
        //List<Cidade> result = instance.listarTodos();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        for(Cidade c:expResult){
            System.out.println(c.getId() + " " + c.getNome());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class CidadeDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Cidade cidade = new Cidade("Teste de cidade", null);
        tx.begin();
        dao.persiste(cidade);
        tx.commit();
        em.refresh(cidade);
        assertTrue("O objeto não foi persistido", cidade.getId() != null);
    }

    /**
     * Test of salvar method, of class CidadeDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
        Cidade cidade = new Cidade("Teste de update de cidade", null);
        dao.persiste(cidade);
        tx.commit();
        em.refresh(cidade);
        tx.begin();
        cidade.setNome("Update");
        cidade = dao.salvar(cidade);
        tx.commit();
        em.refresh(cidade);
        assertEquals("Update", cidade.getNome());
    }

    /**
     * Test of carregarPeloId method, of class CidadeDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Cidade cidade = new Cidade("Teste carregar cidade por id", null);
        dao.persiste(cidade);
        tx.commit();
        em.refresh(cidade);
        Long id = cidade.getId();
        Cidade result = dao.carregarPeloId(id);
        assertEquals(cidade, result);
        
    }

    /**
     * Test of remover method, of class CidadeDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Cidade cidade = new Cidade("Teste remover cidade", null);
        dao.persiste(cidade);
        tx.commit();
        em.refresh(cidade);
        tx.begin();
        dao.remover(cidade);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(cidade));
    }
    
    public void limpar() throws Exception{
        List<Cidade> todos = dao.listarTodos();
        if(!todos.isEmpty()){
            tx.begin();
            for(Cidade t:todos){
                dao.remover(t);
            }
            tx.commit();
        }
    }
}
