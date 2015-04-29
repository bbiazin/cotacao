package modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	private String placa;
	private String placa2;
	private String placa3;
	private String tipo;
	private Double pesoLiquido;
	private String motorista;
	private Conta dono;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getPlaca2() {
		return placa2;
	}
	public void setPlaca2(String placa2) {
		this.placa2 = placa2;
	}
	public String getPlaca3() {
		return placa3;
	}
	public void setPlaca3(String placa3) {
		this.placa3 = placa3;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMotorista() {
		return motorista;
	}
	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}
	public Conta getDono() {
		return dono;
	}
	public void setDono(Conta dono) {
		this.dono = dono;
	}
	public String getPlacasToString() {
		StringBuffer sb = new StringBuffer(placa);
		if (placa2 != null) {
			sb.append(" - ".concat(placa2));
		}
		if (placa3 != null) {
			sb.append(" - ".concat(placa3));
		}
		return sb.toString();
	}
	public Double getPesoLiquido() {
		return pesoLiquido;
	}
	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}
}
