/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.entidades.Cidade;
import io.denuncie.persistencia.CidadeDAO;
import io.denuncie.util.Constantes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Filipe
 */
@Path("/cidades")
public class CidadeRest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    CidadeDAO dao = new CidadeDAO(em);

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCidades() {
        List<Cidade> cidades = dao.listarTodos();
        fecha();
        return gson.toJson(cidades);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCidadePorId(@PathParam("id") Long id) {
        Cidade cidade = dao.carregarPeloId(id);
        fecha();
        return gson.toJson(cidade);
    }
    
    @GET
    @Path("/estado/{sigla}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCidadePorEstado(@PathParam("sigla") String sigla) {
        List<Cidade> cidades = dao.carregarPorEstado(sigla.toUpperCase());
        fecha();
        return gson.toJson(cidades);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCidade(String content) {
        try {
            Cidade cidade = gson.fromJson(content, Cidade.class);
            if (dao.carregarPeloId(cidade.getId()) == null) {
                tx.begin();
                dao.persiste(cidade);
                tx.commit();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            fecha();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

   

    public void fecha() {
        em.close();
        emf.close();
    }
}
