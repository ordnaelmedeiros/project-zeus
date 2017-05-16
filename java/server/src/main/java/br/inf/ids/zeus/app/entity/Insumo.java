package br.inf.ids.zeus.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.inf.ids.zeus.app.resource.insumo.EUnidadeMedida;
import br.inf.ids.zeus.core.entity.CampoInfo;

@Entity
@Table
public class Insumo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CampoInfo(descricao="Descrição")
	@Column(length=100, nullable=false)
	private String descricao;
	
	@CampoInfo(descricao="Unidade de Medida", isDescricao=true)
	@Column(nullable=false)
	@Enumerated(EnumType.ORDINAL)
	private EUnidadeMedida unidade;
	
	@CampoInfo(descricao="Grupo de Insumo")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="insumoGrupoId", foreignKey=@ForeignKey(name="fk_Insumo_InsumoGrupo"))
	private InsumoGrupo insumoGrupo;
	
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

	public InsumoGrupo getInsumoGrupo() {
		return insumoGrupo;
	}

	public void setInsumoGrupo(InsumoGrupo insumoGrupo) {
		this.insumoGrupo = insumoGrupo;
	}
	
}
