package br.inf.ids.zeus.app.resource.objetoteste;

import java.time.LocalDateTime;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import br.inf.ids.zeus.app.entity.ObjetoTeste;
import br.inf.ids.zeus.core.controller.Controller;

@Path("/objetoteste")
public class ObjetoTesteController extends Controller<ObjetoTeste> {

	@Context
	ServletContext context;
	
	@GET
	@Path("/ping")
	public String ping() {
		return "ping: " + LocalDateTime.now();
	}

	@Override
	protected Class<ObjetoTeste> getClasse() {
		return ObjetoTeste.class;
	}
	
	
}