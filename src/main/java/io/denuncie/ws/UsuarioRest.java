/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.entidades.Usuario;
import io.denuncie.dto.UsuarioDTO;
import io.denuncie.persistencia.UsuarioDAO;
import io.denuncie.util.Constantes;
import java.util.Calendar;
import java.util.Date;
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
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;

/**
 *
 * @author Filipe
 */
@Path("/usuarios")
public class UsuarioRest implements PostProcessInterceptor {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    UsuarioDAO dao = new UsuarioDAO(em);

    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsuarios() {
        List<UsuarioDTO> usuarios = dao.listarTodos();
        return gson.toJson(usuarios);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsuarioPorId(@PathParam("id") Long id) {
        Usuario usuario = dao.carregarPeloId(id);
        if (usuario != null) {
            return gson.toJson(new UsuarioDTO(usuario));
        }
        return "{}";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postUsuario(String content) {
        System.out.println(content);
        try {
            Usuario usuario = gson.fromJson(content, Usuario.class);
            usuario.setDataCadastro(Calendar.getInstance().getTime());
            tx.begin();
            dao.persiste(usuario);
            tx.commit();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putUsuario(String content) {
        try {
            Usuario update = gson.fromJson(content, Usuario.class);
            Usuario usuario = dao.carregarPeloId(update.getId());
            if (usuario != null) {
                usuario.setNome(update.getNome());
                usuario.setEmail(update.getEmail());
                usuario.setSenha(update.getSenha());
                tx.begin();
                dao.salvar(usuario);
                tx.commit();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (Exception ex) {
            System.out.println(content);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUsuario(@PathParam("id") Long id) {
        try {
            Usuario usuario = dao.carregarPeloId(id);
            tx.begin();
            dao.remover(usuario);
            tx.commit();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @Override
    public void postProcess(ServerResponse sr) {
        em.close();
        emf.close();
    }
}
