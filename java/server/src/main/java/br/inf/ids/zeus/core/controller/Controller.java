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
import br.inf.ids.zeus.core.entity.Campo;
import br.inf.ids.zeus.core.entity.CampoInfo;
import br.inf.ids.zeus.core.entity.SiglaDescricao;
import br.inf.ids.zeus.core.entity.Tabela;
import br.inf.ids.zeus.core.enums.EnumSiglaDescricao;

//@Produces(MediaType.APPLICATION_JSON)
@Produces("application/json;charset=UTF-8")
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
	public Tabela estrutura() {
		
		Tabela tabela = new Tabela();
		tabela.setNome(getClasse().getSimpleName());
		
		List<Campo> campos = new ArrayList<>();
		
		Field[] fields = getClasse().getDeclaredFields();
		for (Field field : fields) {
			
			Campo campo = new Campo();
			campo.setNomeFisico(field.getName());
			campo.setTipo(field.getType().getSimpleName());
			
			if (field.isAnnotationPresent(Id.class)) {
				campo.setIsPk(true);
				campo.setDescricao("Código");
			}
			
			CampoInfo cInfo = field.getAnnotation(CampoInfo.class);
			if (cInfo!=null) {
				campo.setDescricao(cInfo.descricao());
			}
			
			Column coluna = field.getAnnotation(Column.class);
			if (coluna!=null) {
				campo.setNullable(coluna.nullable());
				
				if (field.getType().equals(String.class)) {
					if (!coluna.columnDefinition().equalsIgnoreCase("TEXT")) {
						campo.setTamanho(coluna.length());
					}
				} else if (field.getType().equals(BigDecimal.class)) {
					campo.setPrecisao(coluna.precision());
					campo.setEscala(coluna.scale());
					
				}
				
			}
			
			Enumerated enumerado = field.getAnnotation(Enumerated.class);
			if (enumerado!=null) {
				if (Arrays.asList(field.getType().getInterfaces()).contains(EnumSiglaDescricao.class)) {
					
					campo.setEnumSiglaDescricao(new ArrayList<>());
					
					@SuppressWarnings("unchecked")
					List<EnumSiglaDescricao> list = (List<EnumSiglaDescricao>)Arrays.asList(field.getType().getEnumConstants());
					int id = 0;
					for (EnumSiglaDescricao enumSiglaDescricao : list) {
						SiglaDescricao sd = new SiglaDescricao();
						sd.setId(id++);
						sd.setSigla(enumSiglaDescricao.getSigla());
						sd.setDescricao(enumSiglaDescricao.getDescricao());
						campo.getEnumSiglaDescricao().add(sd);
					}
					
				}
			}
			
			
			ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
			if (manyToOne!=null) {
				
				campo.setCamposPesquisa(new ArrayList<>());
				
				Field codigo = this.getFieldId(field.getType());
				CampoInfo codigoInfo = codigo.getAnnotation(CampoInfo.class);
				Campo campoCodigo = new Campo();
				campoCodigo.setNomeFisico(codigo.getName());
				if (codigoInfo!=null) {
					campoCodigo.setDescricao(codigoInfo.descricao());
				} else {
					campoCodigo.setDescricao("Código");
				}
				
				campo.getCamposPesquisa().add(campoCodigo);
				
				List<Field> fieldsDescricao = this.getFieldDescricao(field.getType());
				for (Field fieldDesc : fieldsDescricao) {
					CampoInfo descInfo = fieldDesc.getAnnotation(CampoInfo.class);
					Campo campoDesc = new Campo();
					campoDesc.setNomeFisico(fieldDesc.getName());
					if (descInfo!=null) {
						campoDesc.setDescricao(descInfo.descricao());
					}
					campo.getCamposPesquisa().add(campoDesc);
				}
				
			}
			
			
			campos.add(campo);
			
		}
		
		tabela.setCampos(campos);
		
		return tabela;
		
		/*
		Field[] campos = getClasse().getDeclaredFields();
		for (Field field : campos) {
			
			Column coluna = field.getAnnotation(Column.class);
			ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
			Id pk = field.getAnnotation(Id.class);
			Enumerated enumerado = field.getAnnotation(Enumerated.class);
			CampoInfo cInfo = field.getAnnotation(CampoInfo.class);
			
			
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
			
		}
		
		*/
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
