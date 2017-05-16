package br.inf.ids.zeus.core.dao;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.inf.ids.zeus.app.entity.Insumo;
import br.inf.ids.zeus.app.entity.InsumoGrupo;
import br.inf.ids.zeus.app.entity.ObjetoTeste;

public class EntityManagerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
			.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
			
			.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
			.setProperty("hibernate.connection.username", "postgres")
			.setProperty("hibernate.connection.password", "ids0207")
			.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/zeus")
			
			.setProperty("hibernate.c3p0.min_size", "5")
			.setProperty("hibernate.c3p0.max_size", "20")
			.setProperty("hibernate.c3p0.timeout", "1800")
			.setProperty("hibernate.c3p0.max_statements", "50")
			
			.setProperty("hibernate.hbm2ddl.auto", "update")
			.setProperty("hibernate.format_sql", "true")
			.setProperty("hibernate.show_sql", "true");
		
		cfg.addAnnotatedClass(ObjetoTeste.class);
		cfg.addAnnotatedClass(Insumo.class);
		cfg.addAnnotatedClass(InsumoGrupo.class);
		//cfg.addAnnotatedClass(Acesso.class);
		//cfg.addAnnotatedClass(Sessao.class);
		
		sessions = cfg.buildSessionFactory();
		
	}

	public static EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	
}
