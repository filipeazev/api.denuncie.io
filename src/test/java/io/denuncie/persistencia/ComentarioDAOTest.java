/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.dto.ComentarioDTO;
import io.denuncie.entidades.Categoria;
import io.denuncie.entidades.Cidade;
import io.denuncie.entidades.Comentario;
import io.denuncie.entidades.Denuncia;
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
public class ComentarioDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static ComentarioDAO dao;
    static Usuario usuario;
    static Categoria categoria;
    static Cidade cidade;
    static Denuncia denuncia;

    public ComentarioDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new ComentarioDAO(em);
        cidade = new Cidade("Cidade", null);
        usuario = new Usuario("Usuario", "usuario@email.com", new Date(), "123456");
        categoria = new Categoria("Categoria");
        denuncia = new Denuncia("Teste denuncia1", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", cidade, categoria, usuario, "Av. Endereço, 00");
        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(cidade);
        em.persist(categoria);
        em.persist(denuncia);
        em.getTransaction().commit();
        em.refresh(usuario);
        em.refresh(categoria);
        em.refresh(cidade);
        em.refresh(denuncia);
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
     * Test of listarTodos method, of class ComentarioDAO.
     */
    @Test
    public void testlistarPorDenuncia() throws Exception {
        limpar();
        tx.begin();
        Comentario comentario1 = new Comentario("Teste comentario1", usuario, denuncia);
        Comentario comentario2 = new Comentario("Teste comentario2", usuario, denuncia);
        Comentario comentario3 = new Comentario("Teste comentario3", usuario, denuncia);
        dao.persiste(comentario1);
        dao.persiste(comentario2);
        dao.persiste(comentario3);
        tx.commit();
        List<ComentarioDTO> expResult = dao.listarPorDenuncia(denuncia.getId());
        //List<Comentario> result = instance.listarTodos();
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        for(ComentarioDTO c:expResult){
            System.out.println(c.getId() + " " + c.getComentario());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class ComentarioDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Comentario comentario = new Comentario("Teste de comentario", usuario, denuncia);
        tx.begin();
        dao.persiste(comentario);
        tx.commit();
        em.refresh(comentario);
        assertTrue("O objeto não foi persistido", comentario.getId() != null);
    }

    /**
     * Test of salvar method, of class ComentarioDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
        Comentario comentario = new Comentario("Teste de update de comentario", usuario, denuncia);
        dao.persiste(comentario);
        tx.commit();
        em.refresh(comentario);
        tx.begin();
        comentario.setComentario("Update");
        comentario = dao.salvar(comentario);
        tx.commit();
        em.refresh(comentario);
        assertEquals("Update", comentario.getComentario());
    }

    /**
     * Test of carregarPeloId method, of class ComentarioDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Comentario comentario = new Comentario("Teste carregar comentario por id", usuario, denuncia);
        dao.persiste(comentario);
        tx.commit();
        em.refresh(comentario);
        Long id = comentario.getId();
        Comentario result = dao.carregarPeloId(id);
        assertEquals(comentario, result);
        
    }

    /**
     * Test of remover method, of class ComentarioDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Comentario comentario = new Comentario("Teste remover comentario", usuario, denuncia);
        dao.persiste(comentario);
        tx.commit();
        em.refresh(comentario);
        tx.begin();
        dao.remover(comentario);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(comentario));
    }
    
    public void limpar() throws Exception{
        List<ComentarioDTO> todos = dao.listarPorDenuncia(denuncia.getId());
        if(!todos.isEmpty()){
            tx.begin();
            for(ComentarioDTO t:todos){
                dao.remover(dao.carregarPeloId(t.getId()));
            }
            tx.commit();
        }
    }
}
