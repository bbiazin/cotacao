package modelo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pai implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String descricao;

	
	@OneToMany(mappedBy="pai", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<Contato> contatos;

	/*
	@OneToMany(mappedBy="pai", cascade=CascadeType.ALL)
	private List<Filho> filhos;
*/
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
/*
	public List<Filho> getFilhos() {
		return filhos;
	}
	public void setFilhos(List<Filho> filhos) {
		this.filhos = filhos;
	}
*/
	
	public Set<Contato> getContatos() {
		return contatos;
	}
	public void setContatos(Set<Contato> contatos) {
		this.contatos = contatos;
	}
	
}

