package dto;

import java.util.Date;

import modelo.Conta;
import modelo.Cotacao;

public class CotacaoListaDto {
	private long id;
	private String assunto;
	private Date dataPublicacao;
	private Date dataFechamento;
	private Date dataEncerramento;
	private Conta remetente;
	
	public CotacaoListaDto(Cotacao c) {
		this.id = c.getId();
		this.assunto = c.getAssunto();
		this.dataPublicacao = c.getDataPublicacao();
		this.dataFechamento = c.getDataFechamento();
		this.dataEncerramento = c.getDataEncerramento();
		this.remetente = c.getDono();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
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

	public Date getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public Conta getRemetente() {
		return remetente;
	}

	public void setRemetente(Conta remetente) {
		this.remetente = remetente;
	}
	
}

