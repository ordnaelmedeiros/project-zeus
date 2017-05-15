package br.inf.ids.zeus.app.resource.insumo;

import javax.ws.rs.Path;

import br.inf.ids.zeus.app.entity.Insumo;
import br.inf.ids.zeus.core.controller.Controller;

@Path("/insumo")
public class InsumoController extends Controller<Insumo> {

	@Override
	protected Class<Insumo> getClasse() {
		return Insumo.class;
	}

	
}