/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.entidades.Estado;
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
public class EstadoDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static EstadoDAO dao;

    public EstadoDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new EstadoDAO(em);
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
     * Test of listarTodos method, of class EstadoDAO.
     */
    @Test
    public void testListarTodos() throws Exception {
        limpar();
        tx.begin();
        Estado estado1 = new Estado("Teste estado1", null);
        Estado estado2 = new Estado("Teste estado2", null);
        Estado estado3 = new Estado("Teste estado3", null);
        dao.persiste(estado1);
        dao.persiste(estado2);
        dao.persiste(estado3);
        tx.commit();
        List<Estado> expResult = dao.listarTodos();
        //List<Estado> result = instance.listarTodos();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        for(Estado c:expResult){
            System.out.println(c.getId() + " " + c.getNome());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class EstadoDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Estado estado = new Estado("Teste de estado", null);
        tx.begin();
        dao.persiste(estado);
        tx.commit();
        em.refresh(estado);
        assertTrue("O objeto não foi persistido", estado.getId() != null);
    }

    /**
     * Test of salvar method, of class EstadoDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
        Estado estado = new Estado("Teste de update de estado", null);
        dao.persiste(estado);
        tx.commit();
        em.refresh(estado);
        tx.begin();
        estado.setNome("Update");
        estado = dao.salvar(estado);
        tx.commit();
        em.refresh(estado);
        assertEquals("Update", estado.getNome());
    }

    /**
     * Test of carregarPeloId method, of class EstadoDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Estado estado = new Estado("Teste carregar estado por id", null);
        dao.persiste(estado);
        tx.commit();
        em.refresh(estado);
        Long id = estado.getId();
        Estado result = dao.carregarPeloId(id);
        assertEquals(estado, result);
        
    }

    /**
     * Test of remover method, of class EstadoDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Estado estado = new Estado("Teste remover estado", null);
        dao.persiste(estado);
        tx.commit();
        em.refresh(estado);
        tx.begin();
        dao.remover(estado);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(estado));
    }
    
    public void limpar() throws Exception{
        List<Estado> todos = dao.listarTodos();
        if(!todos.isEmpty()){
            tx.begin();
            for(Estado t:todos){
                dao.remover(t);
            }
            tx.commit();
        }
    }
}
