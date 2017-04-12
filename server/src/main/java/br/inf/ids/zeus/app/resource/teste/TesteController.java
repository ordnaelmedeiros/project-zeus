package br.inf.ids.zeus.app.resource.teste;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import br.inf.ids.zeus.app.entity.ObjetoTeste;

@Path("/teste")
public class TesteController {

	@Context
	ServletContext context;
	
	@GET
	@Path("/ping")
	@Produces(MediaType.TEXT_HTML)
	public String ping() {
		
		return "ping: " + LocalDateTime.now();
		
	}
	
	@GET
	@Path("/json")
	@Produces(MediaType.APPLICATION_JSON)
	public ObjetoTeste json() {
		ObjetoTeste obj = new ObjetoTeste();
		obj.criar();
		return obj;
	}
	
	@GET
	@Path("/xml")
	@Produces(MediaType.APPLICATION_XML)
	public ObjetoTeste xml() {
		ObjetoTeste obj = new ObjetoTeste();
		obj.criar();
		return obj;
	}
	
}
