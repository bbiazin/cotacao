package modelo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Entity
public class Contato implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String email;
	@ManyToOne
	private Conta dono;
	@ManyToOne
	private Pai pai;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Conta getDono() {
		return dono;
	}
	public void setDono(Conta dono) {
		this.dono = dono;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		//Map<String, Long> mapDono = new HashMap<String, Long>();
		map.put("id", getId());
		map.put("nome", getNome());
		map.put("email", getEmail());
		//mapDono.put("id", getDono().getId());
		//map.put("dono", mapDono);
		return map;
	}
	
	
	public JsonObject toJsonObject() {
		JsonObject objeto = new JsonObject();
		objeto.addProperty("id", getId());
		objeto.addProperty("nome", getNome());
		objeto.addProperty("email", getEmail());
		//JsonObject dono = new JsonObject();
		//dono.addProperty("id", getDono().getId());
		//dono.addProperty("nome", getDono().getNome());
		//objeto.add("dono", dono);
		return objeto;
	}
	public Pai getPai() {
		return pai;
	}
	public void setPai(Pai pai) {
		this.pai = pai;
	}
}
