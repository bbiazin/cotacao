package rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
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

import modelo.Conta;
import modelo.Cotacao;
import modelo.Habilitado;
import modelo.ItemCotacao;
import modelo.Oferta;
import modelo.Resposta;
import util.Ejb;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dto.CotacaoMapper;
import facade.CotacaoFacade;
import facade.OfertaFacade;
import facade.RespostaFacade;
import facade.SessaoFacade;

@Path("/resposta")
public class RespostaRest  {

	private CotacaoFacade facade = Ejb.lookup(CotacaoFacade.class);
	private RespostaFacade respostaFacade = Ejb.lookup(RespostaFacade.class);
	private OfertaFacade ofertaFacade = Ejb.lookup(OfertaFacade.class);
	private SessaoFacade sessaoFacade = Ejb.lookup(SessaoFacade.class);

	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response salvarResposta(String entidade)  throws Exception {
		/*
		{
		  cotacaoId,
		  observacao,
		  ofertas [
		    itemId,
		    valor
		  ]
		}
			 */
		try {
			Conta conta = sessaoFacade.getConta();
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(entidade, JsonObject.class);
			System.out.println(jsonObject.toString());

			// Extrai informações do primeiro nível do json.
			Long cotacaoId = jsonObject.get("cotacaoId").getAsLong();
			String observacao = null;
			JsonElement observacaoElement = jsonObject.get("observacao");
			if (observacaoElement != null) {
				observacao = observacaoElement.getAsString();
			}
			JsonArray ofertasJson = jsonObject.get("ofertas").getAsJsonArray();
			
			// Recupera cotação, habilitado e resposta se já existir.
			Cotacao cotacao = facade.recuperarCotacaoHabilitado(cotacaoId);
			Habilitado habilitado = cotacao.getHabilitado(conta);
			Resposta resposta = habilitado.getResposta();
			
			// Não existe resposta, então cria.
			if (resposta == null) {
				resposta = new Resposta();
				resposta.setHabilitado(habilitado);
				resposta.setOfertas(new ArrayList<Oferta>());
			}
			resposta.setObservacao(observacao);
			List<Oferta> ofertas = new ArrayList<Oferta>();

			// Monta as ofertas.
			for (JsonElement e: ofertasJson) {
				Long itemId = e.getAsJsonObject().get("itemId").getAsLong();
				JsonElement valorElement = e.getAsJsonObject().get("valor");
				Double valor = null;
				if (valorElement != null) {
					valor = valorElement.getAsDouble();
				}
				
				// Recupera a oferta se já existir.
				Oferta oferta = resposta.getOferta(itemId);
				
				// Oferta não existe, então cria.
				if (oferta == null) {
					oferta = new Oferta();
					ItemCotacao item = new ItemCotacao();
					item.setId(itemId);
					oferta.setItemCotacao(item);
					oferta.setResposta(resposta);
					oferta.setData(new Date());
				}
				oferta.setValor(valor);
				ofertas.add(oferta);
			}
			resposta.setOfertas(ofertas);
			respostaFacade.salvarRascunhoResposta(resposta);
			
			return montarResposta(Status.OK, "Tudo certo por aqui");
		} catch (Exception e) {
			e.printStackTrace();
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
	public Response options()  throws Exception {
		return Response.ok().build();
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
