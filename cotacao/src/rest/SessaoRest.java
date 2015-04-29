package rest;

import javax.ejb.SessionContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/login")
public class SessaoRest {

	@Context 
	private HttpServletRequest httpServletRequest;

	@Context 
	private SessionContext context;

	@GET
	public Response login() throws Exception {
		String sessionId = httpServletRequest.getSession(true).getId();
		httpServletRequest.login("1", "1");
		
		System.out.println("Logado: " + httpServletRequest.getUserPrincipal().getName());
		return Response.ok(httpServletRequest.getSession().getId())
				.header("Cookies", "JSESSIONID=" + sessionId)
		//		.header("Access-Control-Allow-Origin", "*")
		//		.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
		//		.header("Access-Control-Allow-Headers",
		//				"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
		//				"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();



	}
	
	@OPTIONS 
	public Response options()  throws Exception {
		return Response.ok()
		//		.header("Access-Control-Allow-Origin", "*")
		//		.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEADER")
		//		.header("Access-Control-Allow-Headers",
		//				"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
		//				"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type")
						.build();
	}
	
}
