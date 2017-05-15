package br.inf.ids.zeus.app.resource.insumo;

import br.inf.ids.zeus.core.enums.EnumSiglaDescricao;

public enum EUnidadeMedida implements EnumSiglaDescricao {

	UNIDADE("Un","Unidade"),
	QUILO("Kg","Quilo"),
	LITRO("L", "Litro");
	
	private String sigla;
	private String descricao;
	
	EUnidadeMedida(String sigla, String descricao) {
		this.sigla = sigla;
		this.descricao = descricao;
	}
	
	@Override
	public String getSigla() {
		return this.sigla;
	}
	
	@Override
	public String getDescricao() {
		return this.descricao;
	}
	
}
