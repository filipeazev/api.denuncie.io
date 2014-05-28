/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package io.denuncie.ws;

import com.google.gson.Gson;
import io.denuncie.dto.ComentarioDTO;
import io.denuncie.dto.DenunciaDTO;
import io.denuncie.entidades.Comentario;
import io.denuncie.entidades.Denuncia;
import io.denuncie.persistencia.CategoriaDAO;
import io.denuncie.persistencia.CidadeDAO;
import io.denuncie.persistencia.ComentarioDAO;
import io.denuncie.persistencia.DenunciaDAO;
import io.denuncie.persistencia.UsuarioDAO;
import io.denuncie.util.Constantes;
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
@Path("/denuncias")
public class DenunciaRest implements PostProcessInterceptor {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.PU);
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    DenunciaDAO dao = new DenunciaDAO(em);
    ComentarioDAO cDao = new ComentarioDAO(em);
    UsuarioDAO uDao = new UsuarioDAO(em);
    CidadeDAO cidadeDao = new CidadeDAO(em);
    CategoriaDAO categoriaDao = new CategoriaDAO(em);
    Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDenuncias() {
        List<DenunciaDTO> denuncias = dao.listarTodos();
        return gson.toJson(denuncias);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDenunciaPorId(@PathParam("id") Long id) {
        Denuncia denuncia = dao.carregarPeloId(id);
        if (denuncia != null) {
            String response = gson.toJson(new DenunciaDTO(denuncia));
            return response;
        }
        return "{}";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postDenuncia(String content) {
        try {
            DenunciaDTO denunciaDTO = gson.fromJson(content, DenunciaDTO.class);
            Denuncia denuncia = new Denuncia(
                    denunciaDTO.getDescricao(), 
                    denunciaDTO.getLatitude(), 
                    denunciaDTO.getLongitude(), 
                    new Date(), 
                    denunciaDTO.getImagem(), 
                    cidadeDao.carregarPeloId(denunciaDTO.getCidade()), 
                    categoriaDao.carregarPeloId(denunciaDTO.getCategoria()), 
                    uDao.carregarPeloId(denunciaDTO.getUsuario()), 
                    denunciaDTO.getEndereco());
            tx.begin();
            dao.persiste(denuncia);
            tx.commit();
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
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    //COMENTÁRIOS
    @GET
    @Path("{id}/comentarios")
    @Produces(MediaType.APPLICATION_JSON)
    public String getComentariosPorIdDenuncia(@PathParam("id") Long id) {
        List<ComentarioDTO> comentarios = cDao.listarPorDenuncia(id);
        return gson.toJson(comentarios);
    }

    @GET
    @Path("{id}/comentarios/{c_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getComentariosPorId(@PathParam("c_id") Long id) {
        Comentario comentario = cDao.carregarPeloId(id);
        if (comentario != null) {
            String response = gson.toJson(new ComentarioDTO(comentario));
            return response;
        }
        return "{}";
    }
    
    @POST
    @Path("{id}/comentarios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postComentario(String content) {
        try {
            ComentarioDTO comentarioDTO = gson.fromJson(content, ComentarioDTO.class);
            Comentario comentario = new Comentario(
                    comentarioDTO.getComentario(), 
                    uDao.carregarPeloId(comentarioDTO.getUsuario_id()), 
                    dao.carregarPeloId(comentarioDTO.getDenuncia_id()));
            tx.begin();
            cDao.persiste(comentario);
            tx.commit();
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{id}/comentarios")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putComentario(String content) {
        try {
            ComentarioDTO update = gson.fromJson(content, ComentarioDTO.class);
            Comentario comentario = cDao.carregarPeloId(update.getId());
            comentario.setComentario(update.getComentario());
            comentario.setInativo(update.isInativo());
            comentario.setReportado(update.getReportado());
            tx.begin();
            cDao.salvar(comentario);
            tx.commit();
        } catch (Exception ex) {
            System.out.println(content);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        System.out.println(content);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{id}/comentarios/{c_id}")
    public Response deleteComentario(@PathParam("c_id") Long id) {
        try {
            Comentario comentario = cDao.carregarPeloId(id);
            comentario.setInativo(true);
            tx.begin();
            cDao.salvar(comentario);
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
