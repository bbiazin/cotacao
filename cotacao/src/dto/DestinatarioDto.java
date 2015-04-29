package dto;

import modelo.Contato;
import modelo.GrupoContato;

public class DestinatarioDto {
	private Contato contato;
	private GrupoContato grupoContato;
	
	public DestinatarioDto(Contato contato) {
		this.contato = contato;
	}
	
	public DestinatarioDto(GrupoContato grupoContato) {
		this.grupoContato = grupoContato;
	}
	
	public String getAsStringToConverter() {
		String asString = null;
		if (contato != null) {
			asString = Contato.class.getSimpleName() + contato.getId();
		} else if (grupoContato != null){
			asString  = GrupoContato.class.getSimpleName() +  grupoContato.getId();
		}
		return asString;
	}
	
	public String getNome() {
		if (contato != null) {
			return contato.getNome();
		}
		return grupoContato.getNome();
	}
	
	public Contato getContato() {
		return contato;
	}
	
	public GrupoContato getGrupoContato() {
		return grupoContato;
	}
	
}
