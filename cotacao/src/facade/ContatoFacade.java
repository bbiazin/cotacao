package facade;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import modelo.Conta;
import modelo.Contato;
import modelo.Usuario;
import util.Paginador;

@Stateless
public class ContatoFacade extends GenericFacade<Contato> {
	@EJB
	private SessaoFacade sessaoFacade;
	@EJB
	private UsuarioFacade usuarioFacade;
	@Context 
	private HttpServletResponse request;
	
	public Contato recuperarParaEdicao(Long id) throws Exception {
		Contato contato = recuperar(id);
		return contato;
	}
	
	public List<Contato> autoComplete(Conta dono, String chave) 
			throws Exception {
		String sql = "select distinct x from Contato x left join fetch x.grupos where" +
				" x.dono = :dono" + 
				" and (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, Contato.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	public List<Contato> autoComplete(Conta dono, String chave, Paginador paginador) 
			throws Exception {
		String sql = "select distinct x from Contato x left join fetch x.grupos where" +
				" x.dono = :dono" + 
				" and (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, Contato.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	
	public List<Contato> listar(Conta dono, Paginador paginador) 
			throws Exception {
		String sql = "select distinct x from Contato x left join fetch x.grupos where" +
				" x.dono = :dono" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, Contato.class)
				.setParameter("dono", dono)
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	
	// TODO Remover este método
	public List<Contato> autoComplete(String chave) 
			throws Exception {
		String sql = "select x from Contato x where" +
				" (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)"; 
		return getEntityManager()
				.createQuery(sql, Contato.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	// TODO Remover este método
	public List<Contato> autoComplete(String chave, Paginador paginador) 
			throws Exception {
		String sql = "select x from Contato x where" +
				" (upper(x.nome) like :chave" +
				" or upper(x.email) like :chave)"; 
		return getEntityManager()
				.createQuery(sql, Contato.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}
	
	public Contato recuperarPeloEmailSeExistir(String email) throws Exception {
		String sql = "SELECT x FROM Contato x WHERE" +
				" x.email = :email";
		try {
			return getEntityManager()
					.createQuery(sql, Contato.class)
					.setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			// Não lança exceção.
		}
		return null;
	}

	@Override
	protected void completarEdicao(Contato entidade) throws Exception {
		if (entidade.getId() == null) {
			System.out.println("ContatoFacade.completarEdicao() - setando o dono");
			//entidade.setDono(sessaoFacade.getConta());
		}
		// Remove espaços em branco antes e depois.
		//entidade.setNome(entidade.getNome().trim());
		//entidade.setEmail(entidade.getEmail().trim());
		
	}

	@Override
	protected void validar(Contato entidade) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (entidade.getNome() == null || entidade.getNome().trim().length() == 0) {
			//erros.add("Nome do contato é obrigatorio.");
		}
		if (entidade.getEmail() == null || entidade.getEmail().trim().length() == 0) {
			erros.add("E-mail do contato é obrigatorio.");
		}

		// Se existe algum contato com o e-mail cadastrado.
		Contato contato = recuperarPeloEmailSeExistir(entidade.getEmail());
		if (contato != null && !contato.getId().equals(entidade.getId())) {
			//erros.add("E-mail deste contato já existe cadastrado para o contato " + contato.getNome());
		}

		if (erros.size() > 0) {
			throw new Exception(erros.toString());
		}
	}
}
