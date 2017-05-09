package br.inf.ids.zeus.app.resource.objetoteste;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.inf.ids.zeus.app.entity.ObjetoTeste;
import br.inf.ids.zeus.core.dao.EntityManagerUtil;

@Path("/objetoteste")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ObjetoTesteController {

	@Context
	ServletContext context;
	
	@GET
	@Path("/ping")
	public String ping() {
		return "ping: " + LocalDateTime.now();
	}
	
	@GET
	@Path("/estrutura")
	public String estrutura() {
		
		String texto = "";
		
		String nomeclasse = ObjetoTeste.class.getSimpleName();
		texto += nomeclasse;
		
		Field[] campos = ObjetoTeste.class.getDeclaredFields();
		for (Field field : campos) {
			texto += "\n\t" + field.getName() + ":" + field.getType().getSimpleName();
			
			Column coluna = field.getAnnotation(Column.class);
			Id pk = field.getAnnotation(Id.class);
			
			
			if (coluna!=null) {
				if (field.getType().equals(String.class)) {
					if (!coluna.columnDefinition().equalsIgnoreCase("TEXT")) {
						texto += " (" + coluna.length() + ")";
					}
				} else if (field.getType().equals(BigDecimal.class)) {
					texto += "("+coluna.precision()+","+coluna.scale() + ")";
				}
				
				if (!coluna.nullable()) {
					texto += " NOT NULL ";
				}
				
			}
			
			if (pk!=null) {
				texto += " PK ";
			}
			
		}
		
		return texto;
	}
	
	@GET
	@Path("{id}")
	public ObjetoTeste get(@PathParam("id") Long id) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			ObjetoTeste obj = em.find(ObjetoTeste.class, id);
			em.close();
			
			return obj;
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		
	}
	
	@POST
	public ObjetoTeste post(ObjetoTeste obj) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		return obj;
		
	}
	
	@PUT
	@Path("{id}")
	public ObjetoTeste put(@PathParam("id") Long id, ObjetoTeste obj) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			obj.setVlong(id);
			em.merge(obj);
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
		return obj;
		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			ObjetoTeste obj = em.find(ObjetoTeste.class, id);
			em.remove(obj);
			em.getTransaction().commit();
			em.close();
			
			return Response.ok().build();
			
		} catch (Exception e) {
			if (em!=null) {
				em.close();
			}
			throw e;
		}
		
	}
	
}