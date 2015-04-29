package util;

import java.util.Date;

public class Periodo {
	private Date inicio;
	private Date termino;
	
	public Periodo(Date inicio, Date termino) {
		this.inicio = inicio;
		this.termino = termino;
	}
	
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public Date getTermino() {
		return termino;
	}
	public void setTermino(Date termino) {
		this.termino = termino;
	}
	
	

}
