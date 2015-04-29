package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Conta;
import modelo.Membro;
import modelo.Usuario;

@Stateless
public class ContaFacade extends GenericFacade<Conta> {

	@EJB
	private MembroFacade membroFacade;
	@EJB
	private UsuarioFacade usuarioFacade;
	
	public void criarConta(Conta novaConta, Usuario novoUsuario) throws Exception {
		Conta conta = salvar(novaConta);
		novoUsuario.setRole("USER");
		novoUsuario.setLogin(novoUsuario.getEmail());
		Usuario usuario = usuarioFacade.salvar(novoUsuario);
		Membro membro = new Membro();
		membro.setConta(conta);
		membro.setUsuario(usuario);
		membro.setAdministrador(true);
		membroFacade.salvar(membro);
		//throw new Exception("Olá");
	}
	
	public Conta recuperarPeloEmail(String email) throws Exception {
		String sql = "select x from Conta x where" +
				" x.email = :email";
		return getEntityManager()
				.createQuery(sql, Conta.class)
				.setParameter("email", email)
				.getSingleResult();
	}
	

}
