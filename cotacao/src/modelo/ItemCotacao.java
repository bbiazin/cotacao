package modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class ItemCotacao implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Cotacao cotacao;
	private String descricao;
	private Double quantidade;

	@ManyToOne
	private Carregamento carregamento;

	@OneToMany(mappedBy="itemCotacao", 
			cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, 
			fetch=FetchType.EAGER)
	@OrderBy(value="valor, id")
	private List<Oferta> ofertas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cotacao getCotacao() {
		return cotacao;
	}
	public void setCotacao(Cotacao cotacao) {
		this.cotacao = cotacao;
	}
	public Carregamento getCarregamento() {
		return carregamento;
	}
	public void setCarregamento(Carregamento carregamento) {
		this.carregamento = carregamento;
	}
	public List<Oferta> getOfertas() {
		return ofertas;
	}
	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	public String getDescricao() {
		//return carregamento.getDescricao();
		return descricao;
	}
	public String getDescricao2() {
		return carregamento.getDescricao2();
	}
	public String getInfo() {
		return carregamento.getDadosAdicionais();
	}


	public Oferta getOferta(Conta habilitado) {
		/*
		for (Oferta o: ofertas) {
			if (o.getResposta().getHabilitado().equals(habilitado)) {
				return o;
			}
		}*/
		return null;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> cotacaoMap = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("cotacao", cotacaoMap);
		map.put("descricao", getDescricao());
		map.put("quantidade", getQuantidade());
		cotacaoMap.put("id", getCotacao().getId());
		return map;
	}
}
