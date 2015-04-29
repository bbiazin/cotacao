package facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.UnidadePreco;

@Stateless
public class UnidadePrecoFacade extends GenericFacade<UnidadePreco> {

	public List<UnidadePreco> autoComplete(String chave) 
			throws Exception {
		String sql = "SELECT x FROM UnidadePreco x WHERE"
				+ " UPPER(x.descricao) LIKE :chave" 
				+ " ORDER BY (x.descricao)"; 
		List<UnidadePreco> lista = getEntityManager()
				.createQuery(sql, UnidadePreco.class)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();

		// Coloca como primeiros elementos os que iniciam com a chave.
		List<UnidadePreco> lista2 = new ArrayList<UnidadePreco>(); 
		for (UnidadePreco m: lista) {
			if (m.getDescricao().toUpperCase().startsWith(chave.toUpperCase())) {
				lista2.add(m);
			}
		}
		for (UnidadePreco m: lista) {
			if (!m.getDescricao().toUpperCase().startsWith(chave.toUpperCase())) {
				lista2.add(m);
			}
		}
		return lista2;
	}

	public UnidadePreco recuperarPorDescricaoSeExistir(String descricao) 
			throws Exception {
		String sql = "select x from UnidadePreco x where" +
				" upper(x.descricao) = :descricao";
		try {
			return getEntityManager()
					.createQuery(sql, UnidadePreco.class)
					.setParameter("descricao", descricao.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	}
