package modelo;

import java.io.Serializable;
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
public class Habilitado implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Cotacao cotacao;
	@ManyToOne
	private Contato contato;
	@OneToMany(mappedBy="habilitado", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="id desc")
	private Set<Resposta> respostas;
	
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
	public Set<Resposta> getRespostas() {
		return respostas;
	}
	public void setRespostas(Set<Resposta> respostas) {
		this.respostas = respostas;
	}
	
	public Resposta getResposta() {
		Resposta resposta = null;
		if (respostas != null && respostas.size() > 0) {
			resposta = respostas.iterator().next();
		}
		return resposta;
	}
}
