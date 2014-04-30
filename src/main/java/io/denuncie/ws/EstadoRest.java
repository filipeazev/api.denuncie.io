/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.entidades.Estado;
import io.denuncie.persistencia.EstadoDAO;
import io.denuncie.util.Constantes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

/**
 *
 * @author Filipe
 */
@Path("/estados")
public class EstadoRest implements PostProcessInterceptor {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EstadoDAO dao = new EstadoDAO(em);

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEstados() {
        List<Estado> estados = dao.listarTodos();
        return gson.toJson(estados);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEstadoPorId(@PathParam("id") Long id) {
        Estado estado = dao.carregarPeloId(id);
        return gson.toJson(estado);
    }

    @Override
    public void postProcess(ServerResponse sr) {
        em.close();
        emf.close();
    }
}
