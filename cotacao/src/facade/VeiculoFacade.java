package facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Conta;
import modelo.Veiculo;
import util.Paginador;

@Stateless
public class VeiculoFacade extends GenericFacade<Veiculo> {

	public Veiculo recuperarPorPlacaSeExistir(Conta dono, String placa) 
			throws Exception {
		String sql = "select x from Veiculo x"
				+ " where x.dono = :dono" 
				+ " and upper(x.placa) = :placa";
		try {
			return getEntityManager().createQuery(sql, Veiculo.class)
					.setParameter("dono", dono)
					.setParameter("placa", placa.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} 
	}
	
	public List<Veiculo> autoComplete(Conta dono, String chave) 
			throws Exception {
		String sql = "select distinct x from Veiculo x where" +
				" x.dono = :dono" + 
				" and (upper(x.placa) like :chave" +
				" or upper(x.placa1) like :chave" +
				" or upper(x.placa2) like :chave" +
				" or upper(x.motorista) like :chave)" +
				" order by x.placa"; 
		return getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.getResultList();
	}

	
	public List<Veiculo> autoComplete(Conta dono, String chave, Paginador paginador) 
			throws Exception {
		String sql = "select distinct x from Veiculo x where" +
				" x.dono = :dono" + 
				" and (upper(x.placa) like :chave" +
				" or upper(x.placa1) like :chave" +
				" or upper(x.placa2) like :chave" +
				" or upper(x.motorista) like :chave)" +
				" order by x.placa"; 
		return getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setParameter("dono", dono)
				.setParameter("chave", "%" + chave.toUpperCase() + "%")
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	
	public List<Veiculo> listar(Conta dono, Paginador paginador) 
			throws Exception {
		String sql = "select distinct x from Veiculo x where" +
				" x.dono = :dono" +
				" order by x.placa"; 
		return getEntityManager()
				.createQuery(sql, Veiculo.class)
				.setParameter("dono", dono)
				.setFirstResult(paginador.primeiroRegistro())
				.setMaxResults(paginador.getTamanhoPagina())
				.getResultList();
	}

	@Override
	protected void validar(Veiculo entidade) throws Exception {
		List<String> erros = new ArrayList<String>();
		if (entidade.getPlaca() == null || entidade.getPlaca().trim().length() == 0) {
			erros.add("Placa do veículo é obrigatorio.");
		}
		if (erros.size() > 0) {
			throw new Exception(erros.toString());
		}
	}
}
