package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Carregamento implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String origem;
	private String municipioOrigem;
	private String destino;
	private String municipioDestino;
	private String produto;
	private Double quantidade;
	private String unidadeQuantidade;
	private String dadosAdicionais;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getMunicipioOrigem() {
		return municipioOrigem;
	}
	public void setMunicipioOrigem(String municipioOrigem) {
		this.municipioOrigem = municipioOrigem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getMunicipioDestino() {
		return municipioDestino;
	}
	public void setMunicipioDestino(String municipioDestino) {
		this.municipioDestino = municipioDestino;
	}
	public String getProduto() {
		return produto;
	}
	public void setProduto(String produto) {
		this.produto = produto;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public String getUnidadeQuantidade() {
		return unidadeQuantidade;
	}
	public void setUnidadeQuantidade(String unidadeQuantidade) {
		this.unidadeQuantidade = unidadeQuantidade;
	}
	
	public String getDescricao() {
		String descricao = "Origem: " + origem 
				+ " Destino: " + destino 
				+ " Produto: " + produto 
				+ " Qtde: " + quantidade 
				+ " " + unidadeQuantidade;
		if (dadosAdicionais != null && dadosAdicionais.trim().length() > 0) {
			descricao += " - " + dadosAdicionais;
		}
		return descricao;
	}
	
	public String getDescricao2() {
		String descricao = origem 
				+ " X " + destino 
				+ " X " + produto 
				+ " X " + quantidade 
				+ " " + unidadeQuantidade;
		if (dadosAdicionais != null && dadosAdicionais.trim().length() > 0) {
			descricao += " - " + dadosAdicionais;
		}
		return descricao;
	}
	
	public String getDadosAdicionais() {
		return dadosAdicionais;
	}
	public void setDadosAdicionais(String dadosAdicionais) {
		this.dadosAdicionais = dadosAdicionais;
	}
}
