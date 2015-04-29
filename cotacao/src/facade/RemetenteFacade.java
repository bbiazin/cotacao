package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Conta;
import modelo.Cotacao;
import modelo.ItemCotacao;
import util.FiltroCotacaoRemetente;

@Stateless
public class RemetenteFacade extends GenericFacade<Cotacao> {

	@EJB
	private ItemCotacaoFacade itemCotacaoFacade;
	@EJB
	private OfertaFacade ofertaFacade;
	@EJB
	private SessaoFacade sessaoFacade;

	public List<ItemCotacao> listarItensFechados() throws Exception {
		List<ItemCotacao> itens = new ArrayList<ItemCotacao>();
		Conta conta = sessaoFacade.getConta();
		String sql = "SELECT DISTINCT c"
				+ " FROM Cotacao c JOIN c.itens i"
				+ " WHERE c.dono = :dono"
				+ " AND c.dataFechamento <= :dataFechamento"
				+ " AND c.dataEncerramento = NULL"
				//+ " AND i.dataEncerramento = NULL"
				+ " ORDER BY c.dataFechamento, c.id";
		for (Cotacao c: getEntityManager()
				.createQuery(sql, Cotacao.class)
				.setParameter("dono", conta)
				.setParameter("dataFechamento", new Date())
				.getResultList()) {
			// TODO Buscar no sql as entidades relacionadas.
			for (ItemCotacao i: c.getItens()) {
				//if (i.getDataEncerramento() == null) {
					//i.getOfertas().size();
					itens.add(i);
				//}
			}
		}
		return itens;
	}
	
	/*
	public List<ItemCotacao> listarCotacoesEnviadas() throws Exception {
		List<ItemCotacao> itens = new ArrayList<ItemCotacao>();
		Conta conta = sessaoFacade.getConta();
		String sql = "SELECT DISTINCT c"
				+ " FROM Cotacao c JOIN c.itens i"
				+ " WHERE c.dono = :dono"
				+ " ORDER BY c.dataFechamento, c.id";
		for (Cotacao c: getEntityManager()
				.createQuery(sql, Cotacao.class)
				.setParameter("dono", conta)
				.getResultList()) {
			// TODO Buscar no sql as entidades relacionadas.
			for (ItemCotacao i: c.getItens()) {
				if (i.getDataEncerramento() == null) {
					i.getOfertas().size();
					itens.add(i);
				}
			}
			//c.getDestinatarios().size();
			//c.getHabilitacoes().size();
		}
		return itens;
	}
	*/
	
	public List<ItemCotacao> listarCotacoesEnviadas(FiltroCotacaoRemetente filtro) throws Exception {
		
		filtro.setDono(sessaoFacade.getConta());
		List<ItemCotacao> itens = filtro.getTypedQuery(getEntityManager()).getResultList();

		/*
		String sql = "SELECT x FROM modelo.ItemCotacao x WHERE upper(x.carregamento.origem) like :chave";
		List<ItemCotacao> itens = getEntityManager()
			.createQuery(sql, ItemCotacao.class)
			.setParameter(1, "%" + filtro.getOrigem().toUpperCase() + "%")
			.getResultList();
			*/
		for (ItemCotacao i: itens) {


			// TODO Buscar no sql as entidades relacionadas.
					//i.getOfertas().size();
					i.getCotacao().getDestinatarios().size();
					i.getCotacao().getHabilitacoes().size();
			//c.getDestinatarios().size();
			//c.getHabilitacoes().size();
		}
		return itens;
	}
	
}
