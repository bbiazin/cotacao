package rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import modelo.Contato;
import util.Ejb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import facade.ContatoFacade;


@Path("/contato")
public class ContatoRest  {


	//@EJB
	private ContatoFacade facade = Ejb.lookup(ContatoFacade.class);
//	@Context 
//	private HttpServletRequest httpServletRequest;

	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response listar() throws Exception {
		try {
			List<Map<String, Object>> contatos = new ArrayList<Map<String, Object>>();
			for (Contato contato: facade.listar()) {
				contatos.add(contato.toMap());
			}
			return montarResposta(Status.OK, contatos);
			
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception {
		try {
			return montarResposta(Status.OK, 
					facade.recuperar(id).toMap());
					//new Gson().toJson(facade.recuperar(id).toJsonObject()));
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response salvar(@Context HttpServletRequest request, Contato entidade)  throws Exception {
		try {
			return montarResposta(Status.OK, 
					facade.salvar(entidade).toMap());
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		try {
			facade.excluir(
					facade.recuperar(id));
			return montarResposta(Status.OK);
		} catch (Exception e) {
			// TODO Reportar mensagem correta. Testar excluir um contato que esteja em uma cotação para 
			// gerar exceção de violação de constraint.
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR,
							e.getMessage().concat(": ").concat(e.getCause().getMessage())
							.concat(": ").concat(e.getLocalizedMessage())));
		}
	}
	
	@OPTIONS 
	public Response options()  throws Exception {
		return Response.ok()
				//.header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				//.header("Access-Control-Allow-Headers",
						//"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						//"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}

	@OPTIONS
	@Path("/{id}")
	public Response optionsId()  throws Exception {
		return Response.ok()
				//.header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				//.header("Access-Control-Allow-Headers",
						//"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						//"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}


	private Response montarResposta(StatusType status) {
		return Response.status(status)
				//.header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				//.header("Access-Control-Allow-Headers",
						//"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						//"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}
	private Response montarResposta(StatusType status, Object entidade) {
		return Response.status(status)
				.entity(entidade)
				//.header("Access-Control-Allow-Origin", "*")
				//.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				//.header("Access-Control-Allow-Headers",
						//"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						//"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
		
	}
}
