/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.dto.DenunciaDTO;
import io.denuncie.entidades.Denuncia;
import io.denuncie.persistencia.DenunciaDAO;
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
@Path("/denuncias")
public class DenunciaRest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    DenunciaDAO dao = new DenunciaDAO(em);

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDenuncias() {
        List<DenunciaDTO> denuncias = dao.listarTodos();
        fecha();
        return gson.toJson(denuncias);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDenunciaPorId(@PathParam("id") Long id) {
        Denuncia denuncia = dao.carregarPeloId(id);
        if (denuncia != null) {
            String response = gson.toJson(new DenunciaDTO(denuncia));
            fecha();
            return response;
        }
        return "{}";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDenuncia(String content) {
        try {
            Denuncia denuncia = gson.fromJson(content, Denuncia.class);
            tx.begin();
            dao.persiste(denuncia);
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
    public Response putDenuncia(String content) {
        try {
            Denuncia update = gson.fromJson(content, Denuncia.class);
            Denuncia denuncia = dao.carregarPeloId(update.getId());
            denuncia.setDescricao(update.getDescricao());
            denuncia.setImagem(update.getImagem());
            tx.begin();
            dao.salvar(denuncia);
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
    public Response deleteDenuncia(@PathParam("id") Long id) {
        try {
            Denuncia denuncia = dao.carregarPeloId(id);
            denuncia.setInativo(true);
            tx.begin();
            dao.salvar(denuncia);
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
