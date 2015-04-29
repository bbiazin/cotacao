package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import modelo.Conta;
import modelo.ItemCotacao;

public class FiltroCotacaoRemetente {
	private String origem;
	private String destino;
	private String produto;
	private Date publicacaoInicio;
	private Date publicacaoTermino;
	private Date fechamentoInicio;
	private Date fechamentoTermino;
	private Date encerramentoInicio;
	private Date encerramentoTermino;
	private Conta dono;

	/*
	public void limpar() {
		origem = null;
		destino = null;
		produto = null;
		publicacaoInicio = null;
		publicacaoTermino = null;
		fechamentoInicio = null;
		fechamentoTermino = null;
		encerramentoInicio = null;
		encerramentoTermino = null;
		dono = null;
	}
	*/
	
	public TypedQuery<ItemCotacao> getTypedQuery(EntityManager em) {
		
		List<Object[]> condicoes = new ArrayList<Object[]>();

		if (origem != null && origem.trim().length() > 0) {
			condicoes.add(new String[] {"origem", "upper(x.carregamento.origem) like", "%" + origem.toUpperCase() + "%"});
		}
		
		if (destino != null && destino.trim().length() > 0) {
			condicoes.add(new String[] {"destino", "upper(x.carregamento.destino) like", "%" + destino.toUpperCase() + "%"});
		}
		if (produto != null && produto.trim().length() > 0) {
			condicoes.add(new String[] {"produto", "upper(x.carregamento.produto) like", "%" + produto.toUpperCase() + "%"});
		}

		if (publicacaoInicio != null) {
			condicoes.add(new Object[] {"publicacaoInicio", "x.cotacao.dataPublicacao >= ", publicacaoInicio});
		}
		Calendar c = Calendar.getInstance();
		if (publicacaoTermino != null) {
			c.setTime(publicacaoTermino);
			c.add(Calendar.DAY_OF_MONTH, 1);
			condicoes.add(new Object[] {"publicacaoTermino", "x.cotacao.dataPublicacao < ", c.getTime()});
		}
		if (fechamentoInicio != null) {
			condicoes.add(new Object[] {"fechamentoInicio", "x.cotacao.dataFechamento >= ", fechamentoInicio});
		}
		if (fechamentoTermino != null) {
			c.setTime(fechamentoTermino);
			c.add(Calendar.DAY_OF_MONTH, 1);
			condicoes.add(new Object[] {"fechamentoTermino", "x.cotacao.dataFechamento < ", c.getTime()});
		}
		if (encerramentoInicio != null) {
			condicoes.add(new Object[] {"encerramentoInicio", "x.cotacao.dataEncerramento >= ", encerramentoInicio});
		}
		if (encerramentoTermino != null) {
			c.setTime(encerramentoTermino);
			c.add(Calendar.DAY_OF_MONTH, 1);
			condicoes.add(new Object[] {"encerramentoTermino", "x.cotacao.dataEncerramento < ", c.getTime()});
		}
		if (dono != null) {
			condicoes.add(new Object[] {"dono", "x.cotacao.dono = ", dono});
			System.out.println("Dono: " + dono.getNome());
		}

		String sql = "SELECT x FROM ItemCotacao x";
		if (condicoes.size() > 0) {
			sql += " WHERE";
		}
		
		
		int pos = 1;
		for (Object[] s: condicoes) {
			if (pos > 1) {
				sql += " AND";
			}
			sql += " " + s[1] + " :" + s[0];
			pos++;
		}

		sql += " ORDER BY x.cotacao.dataFechamento";
		
		System.out.println(sql);

		TypedQuery<ItemCotacao> query = em.createQuery(sql, ItemCotacao.class);
		
		for (Object[] s: condicoes) {
			query.setParameter((String)s[0], s[2]);
		}
		return query;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public Date getPublicacaoInicio() {
		return publicacaoInicio;
	}

	public void setPublicacaoInicio(Date publicacaoInicio) {
		this.publicacaoInicio = publicacaoInicio;
	}

	public Date getPublicacaoTermino() {
		return publicacaoTermino;
	}

	public void setPublicacaoTermino(Date publicacaoTermino) {
		this.publicacaoTermino = publicacaoTermino;
	}

	public Date getFechamentoInicio() {
		return fechamentoInicio;
	}

	public void setFechamentoInicio(Date fechamentoInicio) {
		this.fechamentoInicio = fechamentoInicio;
	}

	public Date getFechamentoTermino() {
		return fechamentoTermino;
	}

	public void setFechamentoTermino(Date fechamentoTermino) {
		this.fechamentoTermino = fechamentoTermino;
	}

	public Date getEncerramentoInicio() {
		return encerramentoInicio;
	}

	public void setEncerramentoInicio(Date encerramentoInicio) {
		this.encerramentoInicio = encerramentoInicio;
	}

	public Date getEncerramentoTermino() {
		return encerramentoTermino;
	}

	public void setEncerramentoTermino(Date encerramentoTermino) {
		this.encerramentoTermino = encerramentoTermino;
	}

	public Conta getDono() {
		return dono;
	}

	public void setDono(Conta dono) {
		this.dono = dono;
	}
}
