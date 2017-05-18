package br.inf.ids.zeus.core.entity;

import java.util.List;

public class Campo {
	
	private String nomeFisico;
	private String tipo;
	private Boolean isPk = false;
	private String descricao;
	private Boolean nullable = true;
	private int tamanho;
	private int precisao;
	private int escala;
	private List<SiglaDescricao> enumSiglaDescricao;
	private List<Campo> camposPesquisa;
	
	public String getNomeFisico() {
		return nomeFisico;
	}
	public void setNomeFisico(String nomeFisico) {
		this.nomeFisico = nomeFisico;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Boolean getIsPk() {
		return isPk;
	}
	public void setIsPk(Boolean isPk) {
		this.isPk = isPk;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<SiglaDescricao> getEnumSiglaDescricao() {
		return enumSiglaDescricao;
	}
	public void setEnumSiglaDescricao(List<SiglaDescricao> enumSiglaDescricao) {
		this.enumSiglaDescricao = enumSiglaDescricao;
	}
	public Boolean getNullable() {
		return nullable;
	}
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public int getPrecisao() {
		return precisao;
	}
	public void setPrecisao(int precisao) {
		this.precisao = precisao;
	}
	public int getEscala() {
		return escala;
	}
	public void setEscala(int escala) {
		this.escala = escala;
	}
	public List<Campo> getCamposPesquisa() {
		return camposPesquisa;
	}
	public void setCamposPesquisa(List<Campo> camposPesquisa) {
		this.camposPesquisa = camposPesquisa;
	}
	
}
