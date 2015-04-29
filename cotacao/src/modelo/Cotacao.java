package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class Cotacao implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	private String assunto;
	private Date dataPublicacao;
	private Date dataFechamento;
	private Date dataEncerramento;
	private String obsEncerramento;

	@ManyToOne
	private Usuario usuarioEncerramento;
	
	@ManyToOne
	private Conta dono;
	
	@ManyToOne
	private Conta cotador;
	
	@ManyToOne
	private Usuario criador;
	
	@OneToMany(mappedBy="cotacao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="id")
	private Set<ItemCotacao> itens;
	
	@OneToMany(mappedBy="cotacao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="id")
	private Set<DestinatarioCotacao> destinatarios;

	@OneToMany(mappedBy="cotacao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="id")
	private Set<Habilitacao> habilitacoes;

	@OneToMany(mappedBy="cotacao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(value="id")
	private Set<Habilitado> habilitados;

	@OneToMany(mappedBy="cotacao", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("data")
	private Set<Evento> eventos;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssuntp(String assunto) {
		this.assunto = assunto;
	}
	public Date getDataPublicacao() {
		return dataPublicacao;
	}
	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}
	public Date getDataFechamento() {
		return dataFechamento;
	}
	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}
	public String getObsEncerramento() {
		return obsEncerramento;
	}
	public void setObsEncerramento(String obsEncerramento) {
		this.obsEncerramento = obsEncerramento;
	}
	public Usuario getUsuarioEncerramento() {
		return usuarioEncerramento;
	}
	public void setUsuarioEncerramento(Usuario usuarioEncerramento) {
		this.usuarioEncerramento = usuarioEncerramento;
	}
	public Set<ItemCotacao> getItens() {
		return itens;
	}
	public void setItens(Set<ItemCotacao> itens) {
		this.itens = itens;
	}
	public Set<DestinatarioCotacao> getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(Set<DestinatarioCotacao> destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getDestinatariosDescricao() {
		String s = "";
		if (destinatarios != null) {
			for (DestinatarioCotacao d: destinatarios) {
				if (s.length() > 0) {
					s = s + ", ";
				}
				s = s + d.getNome();
			}
		} else {
			System.out.println("Cotacao.getDestinatariosDescricao() - destinatarios está null *************");
		}
		return s;
	}

	public String getHabilitacoesToString() {
		String s = "";
		if (habilitacoes != null) {
			for (Habilitacao h: habilitacoes) {
				if (s.length() > 0) {
					s = s + ", ";
				}
				s = s + h.getContato().getNome();
			}
		} else {
			System.out.println("Cotacao.getDestinatariosDescricao() - destinatarios está null *************");
		}
		return s;
	}

	public String getItensDescricao() {
		String s = "[";
		for (ItemCotacao i: itens) {
			if (s.length() > 1) {
				s = s + ",  [";
			}
			s = s + i.getDescricao() + "]";
		}
		return s;
	}
	public Date getDataEncerramento() {
		return dataEncerramento;
	}
	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}
	public Set<Habilitacao> getHabilitacoes() {
		return habilitacoes;
	}
	public void setHabilitacoes(Set<Habilitacao> habilitacoes) {
		this.habilitacoes = habilitacoes;
	}
	public Set<Evento> getEventos() {
		return eventos;
	}
	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}
	public Conta getDono() {
		return dono;
	}
	public void setDono(Conta dono) {
		this.dono = dono;
	}
	public Usuario getCriador() {
		return criador;
	}
	public void setCriador(Usuario criador) {
		this.criador = criador;
	}
	public String getSituacao() {
		String situacao = "";
		if (dataEncerramento != null) {
			situacao = "Encerrada";
		} else if (dataFechamento.before(new Date())) {
			situacao = "Fechada";
		} else {
			situacao = "Aberta";
		}
		return situacao;
	}

	public Habilitado getHabilitado(Conta conta) {
		if (habilitados != null) {
			for (Habilitado h: habilitados) {
				if (h.getContato().getEmail().equals(conta.getEmail())) {
					return h;
				}
			}
		}
		return null;
	}

	public Set<Habilitado> getHabilitados() {
		return habilitados;
	}
	public void setHabilitados(Set<Habilitado> habilitados) {
		this.habilitados = habilitados;
	}
	public Conta getCotador() {
		return cotador;
	}
	public void setCotador(Conta cotador) {
		this.cotador = cotador;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	

}
