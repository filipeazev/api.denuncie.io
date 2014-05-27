/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.persistencia;

import io.denuncie.dto.DenunciaDTO;
import io.denuncie.entidades.Categoria;
import io.denuncie.entidades.Cidade;
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
public class DenunciaDAOTest {

    static EntityManagerFactory emf;
    static EntityManager em;
    static EntityTransaction tx;
    static DenunciaDAO dao;
    static Usuario usuario;
    static Categoria categoria;
    static Cidade cidade;

    public DenunciaDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
        em = emf.createEntityManager();
        dao = new DenunciaDAO(em);
        cidade = new Cidade("Cidade", null);
        usuario = new Usuario("Usuario", "usuario@email.com", new Date(), "123456");
        categoria = new Categoria("Categoria");
        em.getTransaction().begin();
        em.persist(usuario);
        em.persist(cidade);
        em.persist(categoria);
        em.getTransaction().commit();
        em.refresh(usuario);
        em.refresh(categoria);
        em.refresh(cidade);
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
     * Test of listarTodos method, of class DenunciaDAO.
     */
    @Test
    public void testListarTodos() throws Exception {
        limpar();
        tx.begin();
        Denuncia denuncia1 = new Denuncia("Teste denuncia1", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", cidade, categoria, usuario, "Av. Endereço, 00");
        Denuncia denuncia2 = new Denuncia("Teste denuncia2", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", cidade, categoria, usuario, "Av. Endereço, 00");
        Denuncia denuncia3 = new Denuncia("Teste denuncia3", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", cidade, categoria, usuario, "Av. Endereço, 00");
        dao.persiste(denuncia1);
        dao.persiste(denuncia2);
        dao.persiste(denuncia3);
        tx.commit();
        List<DenunciaDTO> expResult = dao.listarTodos();
        for (DenunciaDTO c : expResult) {
            System.out.println(c.getId() + " " + c.getDescricao());
        }
        assertEquals(3, expResult.size());
    }

    /**
     * Test of persiste method, of class DenunciaDAO.
     */
    @Test
    public void testPersiste() throws Exception {
        Denuncia denuncia = new Denuncia("Teste de novo denuncia", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", categoria, usuario, "Av. Endereço, 00");
        tx.begin();
        dao.persiste(denuncia);
        tx.commit();
        em.refresh(denuncia);
        assertTrue("O objeto não foi persistido", denuncia.getId() != null);
    }

    /**
     * Test of salvar method, of class DenunciaDAO.
     */
    @Test
    public void testSalvar() throws Exception {
        tx.begin();
        Denuncia denuncia = new Denuncia("Teste de update denuncia", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", categoria, usuario, "Av. Endereço, 00");
        dao.persiste(denuncia);
        tx.commit();
        em.refresh(denuncia);
        tx.begin();
        denuncia.setDescricao("Update");
        denuncia = dao.salvar(denuncia);
        tx.commit();
        em.refresh(denuncia);
        assertEquals("Update", denuncia.getDescricao());
    }

    /**
     * Test of carregarPeloId method, of class DenunciaDAO.
     */
    @Test
    public void testCarregarPeloId() throws Exception {
        tx.begin();
        Denuncia denuncia = new Denuncia("Teste carregar denuncia por id", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", categoria, usuario, "Av. Endereço, 00");
        dao.persiste(denuncia);
        tx.commit();
        em.refresh(denuncia);
        Long id = denuncia.getId();
        Denuncia result = dao.carregarPeloId(id);
        assertEquals(denuncia, result);

    }

    /**
     * Test of remover method, of class DenunciaDAO.
     */
    @Test
    public void testRemover() throws Exception {
        tx.begin();
        Denuncia denuncia = new Denuncia("Teste remover denuncia", -21.426335, -21.426335, new Date(), "/src/img/1.jpg", categoria, usuario, "Av. Endereço, 00");
        dao.persiste(denuncia);
        tx.commit();
        em.refresh(denuncia);
        tx.begin();
        dao.remover(denuncia);
        tx.commit();
        assertFalse("O objeto ainda persiste", em.contains(denuncia));
    }

    public void limpar() throws Exception {
        List<DenunciaDTO> todos = dao.listarTodos();
        Denuncia temp;
        if (!todos.isEmpty()) {
            tx.begin();
            for (DenunciaDTO t : todos) {
                temp = dao.carregarPeloId(t.getId());
                dao.remover(temp);
            }
            tx.commit();
        }
    }

}
