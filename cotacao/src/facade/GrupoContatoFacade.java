package facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Conta;
import modelo.ContatoAgrupado;
import modelo.GrupoContato;
import util.Paginador;

@Stateless
public class GrupoContatoFacade extends GenericFacade<GrupoContato> {

	@EJB
	private ContatoAgrupadoFacade contatoAgrupadoFacade;

	public GrupoContato recuperarParaEdicao(Long id) throws Exception {
		GrupoContato grupoContato = recuperar(id);
		grupoContato.getContatos().size();
		return grupoContato;
	}

	public List<GrupoContato> autoComplete(Conta dono, String chave) 
			throws Exception {
		String sql = "select x from GrupoContato x where" +
				" x.dono = :dono" + 
				" and upper(x.nome) like :chave" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, GrupoContato.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	public List<GrupoContato> autoComplete(Conta dono, String chave, Paginador paginador) 
			throws Exception {
		String sql = "select x from GrupoContato x where" +
				" x.dono = :dono" + 
				" and upper(x.nome) like :chave" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, GrupoContato.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}


	public List<GrupoContato> listar(Conta dono, Paginador paginador) 
			throws Exception {
		String sql = "select x from GrupoContato x where" +
				" x.dono = :dono" +
				" order by x.nome"; 
		return getEntityManager()
				.createQuery(sql, GrupoContato.class)
				.setParameter("dono", dono)
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	@Override
	// TODO Investigar forma de salvar entidades filhas cujo relacionamento não está atribuído o cascade.
	public GrupoContato salvar(GrupoContato entidade) throws Exception{
		List<ContatoAgrupado> contatosNovos = new ArrayList<ContatoAgrupado>();
		for (ContatoAgrupado c: entidade.getContatos()) {
			if (c.getId() == null) {
				contatosNovos.add(c);
			}
		}
		List<ContatoAgrupado> contatosAtuais = new ArrayList<ContatoAgrupado>();
		if (entidade.getId() != null) {
			contatosAtuais = contatoAgrupadoFacade.listar(entidade);
		}
		List<ContatoAgrupado> contatosRemovidos = new ArrayList<ContatoAgrupado>();
		for (ContatoAgrupado o: contatosAtuais) {
			boolean achou = false;
			for (ContatoAgrupado x: entidade.getContatos()) {
				if (x.getContato().getId().equals(o.getContato().getId())) {
					achou = true;
					break;
				}
			}
			if (!achou) {
				contatosRemovidos.add(o);
			}
		}
		super.salvar(entidade);
		if (contatosNovos.size() > 0) {
			contatoAgrupadoFacade.salvar(contatosNovos);
		}
		for (ContatoAgrupado c: contatosRemovidos) {
			contatoAgrupadoFacade.excluir(c);
		}
		return entidade;
	}

	// TODO Remover este método
	public List<GrupoContato> autoComplete(String chave) 
			throws Exception {
		String sql = "select x from GrupoContato x where" +
				" upper(x.nome) like :chave"; 
		return getEntityManager()
				.createQuery(sql, GrupoContato.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	// TODO Remover este método
	public List<GrupoContato> autoComplete(String chave, Paginador paginador) 
			throws Exception {
		String sql = "select x from GrupoContato x where" +
				" upper(x.nome) like :chave"; 
		return getEntityManager()
				.createQuery(sql, GrupoContato.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

}
