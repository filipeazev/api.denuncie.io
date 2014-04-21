/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.entidades.Categoria;
import io.denuncie.persistencia.CategoriaDAO;
import io.denuncie.util.Constantes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Filipe
 */
@Path("/categorias")
public class CategoriaRest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    CategoriaDAO dao = new CategoriaDAO(em);

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCategorias() {
        List<Categoria> categorias = dao.listarTodos();
        fecha();
        return gson.toJson(categorias);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCategoriaPorId(@PathParam("id") Long id) {
        Categoria categoria = dao.carregarPeloId(id);
        fecha();
        return gson.toJson(categoria);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCategoria(String content) {
        try {
            Categoria categoria = gson.fromJson(content, Categoria.class);
            tx.begin();
            dao.persiste(categoria);
            tx.commit();
            fecha();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putCategoria(String content) {
        try {
            Categoria update = gson.fromJson(content, Categoria.class);
            Categoria categoria = dao.carregarPeloId(update.getId());
            categoria.setNome(update.getNome());
            tx.begin();
            dao.salvar(categoria);
            tx.commit();
            fecha();
        } catch (Exception ex) {
            System.out.println(content);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteCategoria(@PathParam("id") Long id) {
        try {
            Categoria categoria = dao.carregarPeloId(id);
            tx.begin();
            dao.remover(categoria);
            tx.commit();
            fecha();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    public void fecha() {
        em.close();
        emf.close();
    }
}
