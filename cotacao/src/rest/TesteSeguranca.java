package rest;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import util.Ejb;
import util.JsfUtil;

import modelo.Contato;
import facade.ContatoFacade;


@Path("/seguranca")
public class TesteSeguranca  {


	//@EJB
	private ContatoFacade facade = Ejb.lookup(ContatoFacade.class);
	@Context 
	private HttpServletRequest httpServletRequest;

	@Path("/metodoProtegido")
	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response listar(@Context HttpServletResponse res) throws Exception {
		try {
			/*
			if (httpServletRequest.getUserPrincipal() == null) {
				httpServletRequest.login("1", "1");
				System.out.println("Não estava logado~");
			}
			*/
			if (httpServletRequest.getUserPrincipal() == null) {
				System.out.println("Não deu certo");
			} else {
				System.out.println("Deu certo");
			}
			return montarResposta(Status.OK, facade.listar());
			//res.sendRedirect("http://localhost:8080/cotacao/paginas/teste/grupoContato.html");
			//res.setHeader("Location", "http://localhost:8080/cotacao/paginas/teste/grupoContato.html");
			/*
			return Response.ok("tudo certo")
							.header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
							.header("Location", "http://localhost:8080/cotacao/paginas/teste/grupoContato.html")
							.build();
			*/
			
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@GET
	@Path("/metodoNaoProtegido")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listar2(@Context HttpServletResponse res) throws Exception {
		try {
			/*
			if (httpServletRequest.getUserPrincipal() == null) {
				httpServletRequest.login("1", "1");
				System.out.println("Não estava logado~");
			}
			*/
			if (httpServletRequest.getUserPrincipal() == null) {
				System.out.println("Não deu certo");
			} else {
				System.out.println("Deu certo");
			}
			return montarResposta(Status.OK, facade.listar());
			//res.sendRedirect("http://localhost:8080/cotacao/paginas/teste/grupoContato.html");
			//res.setHeader("Location", "http://localhost:8080/cotacao/paginas/teste/grupoContato.html");
			/*
			return Response.ok("tudo certo")
							.header("Access-Control-Allow-Origin", "*")
							.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
							.header("Location", "http://localhost:8080/cotacao/paginas/teste/grupoContato.html")
							.build();
			*/
			
		} catch (Exception e) {
			throw new WebApplicationException(
					montarResposta(Status.INTERNAL_SERVER_ERROR, e.getMessage()));
		}
	}

	@Path("/metodoProtegido")
	@OPTIONS 
	public Response options()  throws Exception {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				.header("Access-Control-Allow-Headers",
						"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}

	@Path("/metodoNaoProtegido")
	@OPTIONS
	public Response optionsId()  throws Exception {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				.header("Access-Control-Allow-Headers",
						"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}


	private Response montarResposta(StatusType status) {
		return Response.status(status)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				.header("Access-Control-Allow-Headers",
						"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}
	private Response montarResposta(StatusType status, Object entidade) {
		return Response.status(status)
				.entity(entidade)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				.header("Access-Control-Allow-Headers",
						"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
		
	}
}
