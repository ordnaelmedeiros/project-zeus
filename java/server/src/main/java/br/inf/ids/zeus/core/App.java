package br.inf.ids.zeus.core;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/rest")
public class App extends Application {
	
	@Context
	public static ServletContext context;
	
	@Override
	public Set<Object> getSingletons() {
		return super.getSingletons();
	}
	
	public App() {
	}
	
}