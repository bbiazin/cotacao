package rest;

import java.util.HashSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import modelo.Contato;
import modelo.Filho;
import modelo.Pai;
import util.Ejb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import facade.ContatoFacade;
import facade.FilhoFacade;
import facade.PaiFacade;

@Path("/pai")
public class PaiRest  {

	private PaiFacade paiFacade = Ejb.lookup(PaiFacade.class);
	private FilhoFacade filhoFacade = Ejb.lookup(FilhoFacade.class);
	private ContatoFacade contatoFacade = Ejb.lookup(ContatoFacade.class);


	@Path("/post")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response salvarRascunho(String entidade)  throws Exception {
		try {
			
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(entidade, JsonObject.class);
			System.out.println(jsonObject.toString());
			Pai pai = new Pai();

			JsonElement paiId = jsonObject.get("id");
			if (paiId != null) {
				pai.setId(paiId.getAsLong());
			}
			pai.setDescricao(jsonObject.get("descricao").getAsString());
			//pai.setFilhos(new ArrayList<Filho>());
			//pai.setContatos(new ArrayList<Contato>());
			pai.setContatos(new HashSet<Contato>());
			JsonArray filhosJson = jsonObject.get("filhos").getAsJsonArray();
			for (JsonElement e: filhosJson) {
				Filho filho = new Filho();

				filho.setPai(pai);
				JsonElement filhoId = e.getAsJsonObject().get("id");
				if (filhoId != null) {
					filho.setId(filhoId.getAsLong());
				}
				filho.setDescricao(e.getAsJsonObject().get("descricao").getAsString());
				//pai.getFilhos().add(filho);
			}
			Contato c = contatoFacade.recuperar(1l);
			//c.setPai(pai);
			//pai.getContatos().add(c);
			//c = contatoFacade.recuperar(2l);
			//c.setPai(pai);
			//pai.getContatos().add(c);
			
			
			//filhoFacade.excluir(filhoFacade.recuperar(153954l));
			//contatoFacade.excluir(contatoFacade.recuperar(1l));
			paiFacade.salvarRascunho(pai);
			
			return montarResposta(Status.OK, pai);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}


	private Response montarResposta(StatusType status) {
		return Response.status(status).build();
	}

	private Response montarResposta(StatusType status, Object entidade) {
		return Response.status(status)
				.entity(entidade)
				.build();
	}
}
