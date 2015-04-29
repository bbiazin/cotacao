package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Resposta implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Habilitado habilitado;
	private Date dataEnvio;
	private String observacao;

	@OneToMany(mappedBy="resposta", cascade=CascadeType.ALL, fetch=FetchType.EAGER, orphanRemoval=true)
	private List<Oferta> ofertas;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Habilitado getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Habilitado habilitado) {
		this.habilitado = habilitado;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date data) {
		this.dataEnvio = data;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}
	
	public Oferta getOferta(Long itemId) {
		for (Oferta o: ofertas) {
			if (o.getItemCotacao().getId().equals(itemId)) {
				return o;
			}
		}
		return null;
	}
	
}
