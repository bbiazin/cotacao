package rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import modelo.GrupoContato;
import facade.GrupoContatoFacade;

@Stateless
@Path("/grupoContato")
public class GrupoContatoRest  {

	@EJB
	private GrupoContatoFacade facade;

	@GET
	@Produces({ MediaType.APPLICATION_JSON})
	public Response listar() throws Exception {
		try {
			return Response.ok()
					.entity(
							facade.listar())
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
					.build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(e.getMessage()).build());
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperar(@PathParam("id") Long id) throws Exception {
		try {
			return Response.ok()
					.entity(
							facade.recuperar(id))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
					.build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(e.getMessage()).build());
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON})
	public Response salvar(GrupoContato entidade)  throws Exception {
		try {
			return Response.ok()
					.entity(
							facade.salvar(entidade))
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
					.build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(e.getMessage()).build());
		}
	}

	@DELETE
	@Path("/{id}")
	public Response excluir(@PathParam("id") Long id) throws Exception {
		try {
			facade.excluir(
					facade.recuperar(id));
			return Response.ok()
					.header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
					.build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Status.INTERNAL_SERVER_ERROR)
						.entity(e.getMessage()).build());
		}
	}

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

	@OPTIONS
	@Path("/{id}")
	public Response optionsId()  throws Exception {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
				.header("Access-Control-Allow-Headers",
						"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
						"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}


}
