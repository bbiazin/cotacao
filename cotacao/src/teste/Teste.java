package teste;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modelo.Contato;
import modelo.Cotacao;
import modelo.DestinatarioCotacao;
import modelo.GrupoContato;
import modelo.ItemCotacao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		String entidade = "{\"descricao\": \"Material de expediente\","
				+ "\"dataFechamento\": \"24/12/2015 12:00\","
				+ "\"itens\": ["
				+ "{\"descricao\": \"item 001\", \"quantidade\": \"45\"},"
				+ "{\"descricao\": \"item 002\", \"quantidade\": \"66\"}"
				+ "],"
				+ "\"habilitados\": [\"agotran@hotmail.com.br\", \"atdl@hotmail.com.br\"]"
			  	+ "}";


		System.out.println(entidade);
		JsonObject json = gson.fromJson(entidade, JsonObject.class);

		System.out.println(json.toString());
		System.out.println(json.getAsJsonArray("itens"));
		
		JsonArray itensJson = json.get("itens").getAsJsonArray();
		//ItemCotacao item = gson.fromJson(itensJson.get(0), ItemCotacao.class);
		//System.out.println(item.getDescricao());
		//System.out.println(itensJson.get(0).getAsJsonObject().get("descricao").getAsString());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		
		//System.out.println(format.parse(json.get("dataFechamento").getAsString()));
		System.out.println(json.get("dataFechamento").toString());
		
		Cotacao cotacao = new Cotacao();
		List<DestinatarioCotacao> destinatarios = new ArrayList<DestinatarioCotacao>(); 
		List<ItemCotacao> itens = new ArrayList<ItemCotacao>(); 
		
		//cotacao.setDestinatarios(destinatarios);
		cotacao.setAssuntp(json.get("descricao").getAsString());
		//cotacao.setDataFechamento(format.parse(json.get("dataFechamento").toString()));
		for (JsonElement e: itensJson) {
			ItemCotacao item = gson.fromJson(e, ItemCotacao.class) ;
			item.setCotacao(cotacao);
			itens.add(item);
		}
		
		JsonArray habilitadosJson = json.get("habilitados").getAsJsonArray();
		System.out.println(habilitadosJson);
		for (JsonElement e: habilitadosJson) {
			DestinatarioCotacao d = new DestinatarioCotacao();
			String email = gson.fromJson(e,  String.class);
			System.out.println("Criando destinatário: " + email);
			Contato c = new Contato();
			c.setEmail(email);
			d.setContato(c);
			d.setCotacao(cotacao);
		}

		System.out.println(new Gson().toJson(cotacao));
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse( "24/12/2015 14:00"));
		cotacao.setDataFechamento(new SimpleDateFormat("dd/MM/yyyy HH:mm") 
				.parse(json.get("dataFechamento").getAsString()));
		System.out.println(format.format(cotacao.getDataFechamento()));

	}

}
