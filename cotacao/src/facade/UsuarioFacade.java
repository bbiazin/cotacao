package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Usuario;
import teste.MailSender;
import util.JsfUtil;
import util.Paginador;

@Stateless
public class UsuarioFacade extends GenericFacade<Usuario> {

	@EJB
	private SessaoFacade sessaoFacade;

	public Usuario getUsuarioLogado() throws Exception {
		return recuperarPeloEmail(JsfUtil.getLogin());
	}

	public Usuario recuperarParaEdicao(Long id) throws Exception {
		Usuario usuario = recuperar(id);
		usuario.getContas().size();
		return usuario;
	}

	public Usuario recuperarPeloEmailSeExistir(String email) throws Exception {
		String sql = "SELECT x FROM Usuario x WHERE" +
				" x.email = :email";
		try {
			return getEntityManager()
					.createQuery(sql, Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new Exception(e);
			
		}
	}


	public Usuario recuperarPeloEmail(String email) throws Exception {
		String sql = "SELECT x FROM Usuario x WHERE" +
				" x.email = :email";
		try {
			return getEntityManager()
					.createQuery(sql, Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new NoResultException("Usuário " + email + " não existe");
		}
	}

	public Usuario recuperarPeloLogin(String login) throws Exception {
		String sql = "select x from Usuario x where" +
				" x.login = :login";
		return getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("login", login)
				.getSingleResult();
	}

	public Usuario criarUsuario(String nome, String email, String senha) throws Exception {
		Usuario usuario = null;
		try {
			usuario = recuperarPeloEmail(email);
		} catch (NoResultException nre) {
			// nada a fazer
		}
		if (usuario != null) {
			throw new Exception("Email já existe.");
		}
		usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setRole("USER");
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario = salvar(usuario);

		MailSender sender = new MailSender();
		sender.sendMail6(email, senha);
		return usuario;
	}

	public void enviarSenha(String email) throws Exception {
		Usuario usuario = null;
		try {
			usuario = recuperarPeloEmail(email);
		} catch (NoResultException nre) {
			throw new Exception("E-mail não cadastrado."); 
		}
		MailSender sender = new MailSender();
		sender.sendMail7(usuario);
	}

	public void enviarSenhaLogin(String login) throws Exception {
		Usuario usuario = null;
		try {
			usuario = recuperarPeloLogin(login);
		} catch (NoResultException nre) {
			throw new Exception("Usuário não cadastrado."); 
		}
		MailSender sender = new MailSender();
		sender.sendMail7(usuario);
	}


	public List<Usuario> autoComplete(String chave) 
			throws Exception {
		String sql = "select x from Usuario x where" +
				" (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)"; 
		return getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	public List<Usuario> autoComplete(String chave, Paginador paginador) 
			throws Exception {
		String sql = "select x from Usuario x where" +
				" (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)"; 
		return getEntityManager()
				.createQuery(sql, Usuario.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	public void alterarSenha(String atual, String nova, String confirmacao) 
			throws Exception {
		if (!nova.equals(confirmacao)) {
			throw new Exception("Confirmação da senha não bate com a nova senha");
		}
		Usuario usuario = sessaoFacade.getUsuario();
		if (!atual.equals(usuario.getSenha())) {
			throw new Exception("Senha atual não bate com a senha do usuário");
		}
		usuario.setSenha(nova);
		salvar(usuario);
	}

}
