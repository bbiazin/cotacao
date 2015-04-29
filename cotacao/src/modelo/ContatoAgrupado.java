package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ContatoAgrupado implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private GrupoContato grupoContato;
	@ManyToOne
	private Contato contato;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GrupoContato getGrupoContato() {
		return grupoContato;
	}
	public void setGrupoContato(GrupoContato grupoContato) {
		this.grupoContato = grupoContato;
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
	
}
