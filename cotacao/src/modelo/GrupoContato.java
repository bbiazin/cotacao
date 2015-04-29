package modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class GrupoContato implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	// Não habilitar cascade devido a relacionamento de ContatoAgrupado com Contato, para de funcionar
	// a gravação de ContatoAgrupado quando grava a entidade pai.
	@OneToMany(mappedBy="grupoContato")//, cascade=CascadeType.ALL)//, orphanRemoval=true)
	private List<ContatoAgrupado> contatos;
	@ManyToOne
	private Conta dono;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<ContatoAgrupado> getContatos() {
		return contatos;
	}
	public void setContatos(List<ContatoAgrupado> contatos) {
		this.contatos = contatos;
	}
	public Conta getDono() {
		return dono;
	}
	public void setDono(Conta dono) {
		this.dono = dono;
	}
	
}
