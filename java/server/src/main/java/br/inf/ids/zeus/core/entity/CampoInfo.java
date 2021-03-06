package br.inf.ids.zeus.core.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CampoInfo {

	
	public String descricao();
	public boolean isDescricao() default false;
	
}
