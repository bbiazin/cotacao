package modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class DestinatarioCotacao implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Cotacao cotacao;
	@ManyToOne
	private Contato contato;
	@ManyToOne
	private GrupoContato grupoContato;
	
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
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public GrupoContato getGrupoContato() {
		return grupoContato;
	}
	public void setGrupoContato(GrupoContato grupoContato) {
		this.grupoContato = grupoContato;
	}

	// TODO Refactoring
	public String getNome() {
		if (contato != null) {
			return contato.getNome();
		} else {
			return grupoContato.getNome();
		}
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> cotacaoMap = new HashMap<String, Object>();
		map.put("id", getId());
		map.put("cotacao", cotacaoMap);
		map.put("contato", getContato().toMap());
		cotacaoMap.put("id", getCotacao().getId());
		return map;
	}
	
	
}
