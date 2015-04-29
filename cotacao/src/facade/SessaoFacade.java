package facade;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpRequest;

import com.sun.net.httpserver.HttpContext;

import modelo.Conta;
import modelo.Membro;
import modelo.Usuario;
import util.JsfUtil;

@Stateful
public class SessaoFacade implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private MembroFacade membroFacade;
	@EJB
	private UsuarioFacade usuarioFacade;
	@Resource
	private SessionContext context;
	@EJB
	private ContaFacade contaFacade;
	@Context 
	private HttpServletRequest httpServletRequest;


	public Conta exigirUsuarioLogado() throws Exception {
		Usuario usuario = new Usuario();
		try {
			usuario = usuarioFacade.recuperarPeloEmail("1");
			/*
			if (httpServletRequest.getUserPrincipal() == null) {
				httpServletRequest.login("1", "1");
				System.out.println("Não estava logado~");
			}
			usuario = usuarioFacade.recuperarPeloEmail(
					httpServletRequest.getUserPrincipal().getName());
			System.out.println("Agora está logado~");
				//context.getCallerPrincipal().getName());*/
		} catch (Exception e)  {
				throw new Exception("Usuário não efetuou login no sistema");
			}
		return membroFacade.listar(usuario).getConta();
	}
	
	public Conta getConta() {
		try{
/*			return membroFacade.listar(
					usuarioFacade.recuperarPeloLogin(
							getLogin())).getConta();
							*/
			return exigirUsuarioLogado();
		} catch (Exception e) {
			JsfUtil.addMsgErro("Erro ao inicializar dados da conta: " + e.getMessage());
		}

		return null;
	}

	public String getLogin() {
		return JsfUtil.getLogin();
	}

	public void logout() throws Exception {
		JsfUtil.invalidarSessao();
	}

	public Usuario getUsuario() throws Exception {
		return usuarioFacade.recuperarPeloLogin(
				getLogin());
	}

}
