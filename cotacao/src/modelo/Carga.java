package modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Carga implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Carregamento carregamento;
	@ManyToOne
	private Veiculo veiculo;
	private Date dataProgramacao;
	private Date previsaoCarga;
	private Long quantidade;
	@ManyToOne
	private Conta transportador;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Carregamento getCarregamento() {
		return carregamento;
	}
	public void setCarregamento(Carregamento carregamento) {
		this.carregamento = carregamento;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Date getDataProgramacao() {
		return dataProgramacao;
	}
	public void setDataProgramacao(Date dataProgramacao) {
		this.dataProgramacao = dataProgramacao;
	}
	public Date getPrevisaoCarga() {
		return previsaoCarga;
	}
	public void setPrevisaoCarga(Date previsaoCarga) {
		this.previsaoCarga = previsaoCarga;
	}
	public Long getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	public Conta getTransportador() {
		return transportador;
	}
	public void setTransportador(Conta transportador) {
		this.transportador = transportador;
	}
}
