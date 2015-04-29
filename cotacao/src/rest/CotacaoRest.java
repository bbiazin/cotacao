package rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import modelo.Contato;
import modelo.Cotacao;
import modelo.DestinatarioCotacao;
import modelo.ItemCotacao;
import util.Ejb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dto.CotacaoMapper;
import facade.CotacaoFacade;
import facade.SessaoFacade;

@Path("/cotacao")
public class CotacaoRest  {

	private CotacaoFacade facade = Ejb.lookup(CotacaoFacade.class);
	private SessaoFacade sessaoFacade = Ejb.lookup(SessaoFacade.class);

	@Path("/cotador")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response listar(
			@QueryParam("firstResult") int firstResult, 
			@QueryParam("maxResults") int maxResults) throws Exception {

		try {
			List<Map<String, Object>> cotacoes = new ArrayList<Map<String, Object>>();
			for (Cotacao cotacao: facade.listarCotador(firstResult, maxResults)) {
				cotacoes.add(new CotacaoMapper(cotacao).mapearCotador());
			}
			return montarResposta(Status.OK, cotacoes);
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@Path("/cotador/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response recuperarCotacaoCotador(
			@PathParam("id") Long id) throws Exception {
		try {
			Cotacao cotacao = facade.recuperarCotacaoCotador(id);
			return montarResposta(Status.OK, new CotacaoMapper(cotacao).mapearCotador());
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@Path("/cotador")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response publicar(String entidade)  throws Exception {
		try {
			Gson gson = new Gson();
			JsonObject json = gson.fromJson(entidade, JsonObject.class);
			System.out.println(json.toString());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Cotacao cotacao = new Cotacao();
			Set<DestinatarioCotacao> destinatarios = new HashSet<DestinatarioCotacao>(); 
			Set<ItemCotacao> itens = new HashSet<ItemCotacao>(); 
			cotacao.setDestinatarios(destinatarios);
			cotacao.setItens(itens);
			cotacao.setAssuntp(json.get("assunto").getAsString());
			cotacao.setDataFechamento(new SimpleDateFormat("dd/MM/yyyy HH:mm") 
			.parse(json.get("dataFechamento").getAsString()));
			JsonArray itensJson = json.get("itens").getAsJsonArray();
			for (JsonElement e: itensJson) {
				ItemCotacao item = gson.fromJson(e, ItemCotacao.class) ;
				item.setCotacao(cotacao);
				itens.add(item);
			}
			JsonArray habilitadosJson = json.get("habilitados").getAsJsonArray();
			for (JsonElement e: habilitadosJson) {
				DestinatarioCotacao d = new DestinatarioCotacao();
				String email = gson.fromJson(e,  String.class);
				System.out.println("Criando destinatário: " + email);
				Contato c = new Contato();
				c.setEmail(email);
				d.setContato(c);
				d.setCotacao(cotacao);
				destinatarios.add(d);
			}
			CotacaoMapper cotacaoMapper = new CotacaoMapper(
					facade.publicar(cotacao));
			return montarResposta(Status.OK, 
					cotacaoMapper.mapearCotador());
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@Path("/habilitado")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response listarHabilitado(
			@QueryParam("firstResult") int firstResult, 
			@QueryParam("maxResults") int maxResults) throws Exception {

		try {
			List<Map<String, Object>> cotacoes = new ArrayList<Map<String, Object>>();
			for (Cotacao cotacao: facade.listarHabilitado(firstResult, maxResults)) {
				cotacoes.add(new CotacaoMapper(cotacao).mapearHabilitado(sessaoFacade.getConta()));
			}
			return montarResposta(Status.OK, cotacoes);
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@Path("/habilitado/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response recuperarCotacaoHabilitado(
			@PathParam("id") Long id) throws Exception {
		try {
			Cotacao cotacao = facade.recuperarCotacaoHabilitado(id);
			return montarResposta(Status.OK, 
					new CotacaoMapper(cotacao).mapearHabilitado(sessaoFacade.getConta()));
		} catch (Exception e) {
			//System.out.println("mensagem de erro: " + e.getMessage() + "-"+ e.getLocalizedMessage() + "-"+e.getCause());
			// TODO Ver por que não apresenta mensagem de erro correta no browser.
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@OPTIONS
	@Path("/cotador")
	public Response optionsCotador()  throws Exception {
		return Response.ok().build();
	}

	@OPTIONS
	@Path("/cotador/{id}")
	public Response optionsCotadorId()  throws Exception {
		return Response.ok().build();
	}

	@OPTIONS 
	@Path("/habilitado")
	public Response optionsHabilitado()  throws Exception {
		return Response.ok().build();
	}

	@OPTIONS
	@Path("/habilitado/{id}")
	public Response optionsHabilitadoId()  throws Exception {
		return Response.ok().build();
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
