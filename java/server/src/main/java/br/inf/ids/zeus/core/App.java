package br.inf.ids.zeus.core;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import br.inf.ids.zeus.core.dao.EntityManagerUtil;

@ApplicationPath("/api")
public class App extends Application {
	
	@Context
	public static ServletContext context;
	
	@Override
	public Set<Object> getSingletons() {
		
		try {
			EntityManagerUtil.getEntityManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.getSingletons();
	}
	
	public App() {
	}
	
}