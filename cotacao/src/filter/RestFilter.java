package filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Context;

@WebFilter({"/rest/*"})
public class RestFilter implements Filter {

	@Context 
	private HttpServletRequest httpServletRequest;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//System.out.println("Passou em RestFilter.doFilter() - " );
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers",
				"X-Requested-With,Host,User-Agent,Accept,Accept-Language," +
				"Accept-Encoding,Accept-Charset,Keep-Alive,Connection,Referer,Origin,Content-Type");
		chain.doFilter(request, response);
		/*
		if (((HttpServletRequest) request).getUserPrincipal() == null) {
			System.out.println("Vai autenticar..");
			//((HttpServletRequest) request).authenticate((HttpServletResponse)response);
		}
		String path = ((HttpServletRequest) request).getRequestURI();
		System.out.println(request.getAttribute("j_username"));
		System.out.println(request.getAttribute("j_password"));
		//System.out.println(request.getParameterValues("j_password")[0]);
		System.out.println(request.getParameter("senha"));
		Set<Entry<String, String[]>> lista = request.getParameterMap().entrySet();
		for (Entry<String, String[]>o: lista) {
			System.out.println(o.getKey() + " - " + o.getValue());
		}
	    System.out.println(((HttpServletRequest) request).getParameterMap().keySet().toArray().toString());

		if (path.contains("/rest/login")) {
		    System.out.println("passou no if ");
		    //((HttpServletRequest) request).authenticate((HttpServletResponse)response);
		    chain.doFilter(request, response); // Just continue chain.
		} else {
		    System.out.println("Náo passou no if: " + path);
		    // Do your business stuff here for all paths other than /specialpath.
		}
		*/
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	

}
