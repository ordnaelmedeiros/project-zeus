package br.inf.ids.zeus.core.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

import br.inf.ids.zeus.core.dao.EntityManagerUtil;
import br.inf.ids.zeus.core.entity.CampoInfo;
import br.inf.ids.zeus.core.enums.EnumSiglaDescricao;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class Controller<Entidade> {
	
	protected abstract Class<Entidade> getClasse();
	
	@Context
	ServletContext context;
	
	private void setId(Entidade entidade, Long id) throws Exception {
		
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			
			Id pk = field.getAnnotation(Id.class);
			if (pk!=null) {
				field.setAccessible(true);
				field.set(entidade, id);
			}
			
		}
		
	}
	
	private Field getFieldId(Class<?> clazz) {
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			Id pk = field.getAnnotation(Id.class);
			if (pk!=null) {
				return field;
			}
		}
		return null;
	}
	
	private List<Field> getFieldDescricao(Class<?> clazz) {
		
		List<Field> fields = new ArrayList<>();
		
		Field[] campos = clazz.getDeclaredFields();
		for (Field field : campos) {
			CampoInfo cInfo = field.getAnnotation(CampoInfo.class);
			if (cInfo!=null && cInfo.isDescricao()) {
				fields.add(field);
			}
		}
		return fields;
	}
	
	@GET
	@Path("/estrutura")
	public String estrutura() {
		
		String texto = " --> ";
		
		String nomeclasse = getClasse().getSimpleName();
		texto += nomeclasse;
		
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			texto += "\n\t" + field.getName() + ":" + field.getType().getSimpleName();
			
			Column coluna = field.getAnnotation(Column.class);
			ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
			Id pk = field.getAnnotation(Id.class);
			Enumerated enumerado = field.getAnnotation(Enumerated.class);
			CampoInfo cInfo = field.getAnnotation(CampoInfo.class);
			
			
			if (coluna!=null) {
				
				System.out.println("teste ");
				
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
			
			if (manyToOne!=null) {
				
				texto += "(";
				
				Field codigo = this.getFieldId(field.getType());
				if (codigo!=null) {
					texto += codigo.getName() +":";
					CampoInfo info = codigo.getAnnotation(CampoInfo.class);
					
					if (info!=null) {
						texto += info.descricao();
					} else {
						texto += "Código";
					}
					texto += "; ";
					
					List<Field> fieldsDescricao = this.getFieldDescricao(field.getType());
					boolean first = true;
					for (Field field2 : fieldsDescricao) {
						
						if (first) {
							first = false;
						} else {
							texto += ", ";
						}
						
						texto += field2.getName() + ":";
						
						CampoInfo info2 = field2.getAnnotation(CampoInfo.class);
						if (info2!=null) {
							texto += info2.descricao();
						}
						
					}
					
				}
				
				texto += ")";
				
			}
			
			if (pk!=null) {
				texto += " PK ";
			}
			
			if (enumerado!=null) {
				
				if (Arrays.asList(field.getType().getInterfaces()).contains(EnumSiglaDescricao.class)) {
					
					@SuppressWarnings("unchecked")
					List<EnumSiglaDescricao> list = (List<EnumSiglaDescricao>)Arrays.asList(field.getType().getEnumConstants());
					texto += "[";
					boolean first = true;
					for (EnumSiglaDescricao enumSiglaDescricao : list) {
						
						if (!first) {
							texto += ", ";
						} else {
							first = false;
						}
						
						texto += enumSiglaDescricao.toString() + "("+enumSiglaDescricao.getSigla()+" - "+enumSiglaDescricao.getDescricao()+")";
						
						
					}
						
					texto += "]";
				}
				
			}
			
			if (cInfo!=null) {
				texto += " -- " + cInfo.descricao();
			} else if (pk!=null) {
				texto += " -- Código";
			}
			
		}
		
		return texto;
	}
	
	@GET
	@Path("{id}")
	public Entidade get(@PathParam("id") Long id) {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			Entidade obj = em.find(this.getClasse(), id);
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
	public Entidade post(Entidade obj) {
		
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
	public Entidade put(@PathParam("id") Long id, Entidade obj) throws Exception {
		
		EntityManager em = null;
		
		try {
			
			em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			this.setId(obj, id);
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
			Entidade obj = em.find(this.getClasse(), id);
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
