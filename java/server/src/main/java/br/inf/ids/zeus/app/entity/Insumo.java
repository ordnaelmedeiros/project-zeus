package br.inf.ids.zeus.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.inf.ids.zeus.app.resource.insumo.EUnidadeMedida;

@Entity
@Table
public class Insumo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=100)
	private String descricao;
	
	@Enumerated(EnumType.ORDINAL)
	private EUnidadeMedida unidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public EUnidadeMedida getUnidade() {
		return unidade;
	}

	public void setUnidade(EUnidadeMedida unidade) {
		this.unidade = unidade;
	}
	
}
