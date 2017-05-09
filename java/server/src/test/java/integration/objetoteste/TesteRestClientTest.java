package integration.objetoteste;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;

import br.inf.ids.zeus.app.entity.ObjetoTeste;

public class TesteRestClientTest {
	
	private final String url = "http://localhost:8080/zeus/api/objetoteste";
	
	public String getUrl() {
		return url;
	}
	
	private ObjetoTeste criar() {
		
		ObjetoTeste obj = new ObjetoTeste();
		obj.setVinteger(1);
		//obj.setVlong(2l);
		obj.setVbooleantrue(true);
		obj.setVbooleanfalse(false);
		obj.setVdouble(1.2);
		obj.setVfloat(2.33f);
		obj.setVbigdecimal(new BigDecimal("3.444"));
		obj.setVstring("Texto menor que 100 caracteres");
		obj.setVmemo("Não viva para que a sua presença seja notada, mas para que a sua falta seja sentida.\nBob Marley");
		obj.setVlocaldate(LocalDate.of(1986, 9, 17));
		
		return obj;
		
	}
	
	@Test
	public void postObjeto() {
		
		//gravar
		ObjetoTeste objGravar = this.criar();
		
		ObjetoTeste objGravado = ClientBuilder.newClient().target(this.getUrl()).request().post(Entity.entity(objGravar, MediaType.APPLICATION_JSON), ObjetoTeste.class);
		Assert.assertNotNull(objGravado);
		//this.assertCompara(objGravar, objGravado);

		ObjetoTeste usuSalvo2 = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", objGravado.getVlong()).request().get(ObjetoTeste.class);
		Assert.assertNotNull(usuSalvo2);
		Assert.assertEquals(objGravado.getVlong(), usuSalvo2.getVlong());
		//this.assertCompara(objGravar, usuSalvo2);

		//alterar
		//this.alterarObjeto(objGravado);
		ObjetoTeste usuAlterado = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", objGravado.getVlong()).request().put(Entity.entity(objGravado, MediaType.APPLICATION_JSON), ObjetoTeste.class);
		Assert.assertNotNull(usuAlterado);
		Assert.assertEquals(usuAlterado.getVlong(), objGravado.getVlong());
		//this.assertCompara(usuAlterado, objGravado);
		
		//remover
		Response response = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", usuAlterado.getVlong()).request().delete();
		Assert.assertEquals("Objeto remover.", Response.ok().build().getStatus(), response.getStatus());
		
		ObjetoTeste obj = ClientBuilder.newClient().target(this.getUrl()).path("/{id}").resolveTemplate("id", usuAlterado.getVlong()).request().get(ObjetoTeste.class);
		Assert.assertNull(obj);
		
	}

}
