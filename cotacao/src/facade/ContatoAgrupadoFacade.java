package facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Conta;
import modelo.Contato;
import modelo.ContatoAgrupado;
import modelo.GrupoContato;

@Stateless
public class ContatoAgrupadoFacade extends GenericFacade<ContatoAgrupado> {

	public List<ContatoAgrupado> listar(Contato contato) 
			throws Exception {
		String sql = "select x from ContatoAgrupado x where" +
				" x.contato = :contato";
		return getEntityManager()
				.createQuery(sql, ContatoAgrupado.class)
				.setParameter("contato", contato)
				.getResultList();
	}

	// TODO Remover este método
	public List<ContatoAgrupado> listar(GrupoContato grupo) 
			throws Exception {
		String sql = "select x from ContatoAgrupado x where" +
				" x.grupoContato = :grupo";
		return getEntityManager()
				.createQuery(sql, ContatoAgrupado.class)
				.setParameter("grupo", grupo)
				.getResultList();

	}

	public ContatoAgrupado recuperarSeExistir(GrupoContato grupo, Contato contato)  
			throws Exception {
		String sql = "select x from ContatoAgrupado x where" +
				" x.grupoContato = :grupo" +
				" and x.contato = :contato";
		try {
			return getEntityManager()
					.createQuery(sql, ContatoAgrupado.class)
					.setParameter("grupo", grupo)
					.setParameter("contato", contato)
					.getSingleResult();
		} catch (NoResultException e) {
			// nada a fazer.
		}
		return null;
	}

	public void vincularGrupo(List<Contato> contatos, GrupoContato grupo)
			throws Exception {
		for (Contato c: contatos) {
			if (recuperarSeExistir(grupo, c) == null) {
				ContatoAgrupado contatoAgrupado = new ContatoAgrupado();
				contatoAgrupado.setContato(c);
				contatoAgrupado.setGrupoContato(grupo);
				salvar(contatoAgrupado);
			}
		}
	}
	
	public void desvincularGrupo(List<Contato> contatos, GrupoContato grupo)
			throws Exception {
		for (Contato c: contatos) {
			ContatoAgrupado entidade = recuperarSeExistir(grupo, c);
			if (entidade != null) {
				excluir(entidade);
			}
		}
	}

}
