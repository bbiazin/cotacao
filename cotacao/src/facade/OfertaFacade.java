package facade;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import modelo.Conta;
import modelo.Cotacao;
import modelo.Evento;
import modelo.ItemCotacao;
import modelo.Oferta;
import modelo.Usuario;

@Stateless
public class OfertaFacade extends GenericFacade<Oferta> {
	@EJB
	private SessaoFacade sessaoFacade;
	@EJB
	private ItemCotacaoFacade itemCotacaoFacade;
	@EJB
	private EventoFacade eventoFacade;
	@EJB
	private CotacaoFacade cotacaoFacade;

	@Override
	public void excluir(Oferta entidade) throws Exception {
		this.getEntityManager().remove(getEntityManager().merge(entidade));
		if (recuperar(153935l) != null) {
			System.out.println("OfertaFacade.excluir() - não excluiu");
		} else {
			System.out.println("OfertaFacade.excluir() - excluiu");
		}
	}

	
	public Oferta recuperarSeExistir2(Usuario remetente, ItemCotacao item) throws Exception {
		String sql = "select x from Oferta x where" +
				" x.remetente = :remetente" +
				" and x.itemCotacao = :item";
		try {
			return getEntityManager()
					.createQuery(sql, Oferta.class)
					.setParameter("remetente", remetente)
					.setParameter("item", item)
					.getSingleResult();
		} catch (NoResultException e) {
			// não lança exceção
		}
		return null;
	}
	
	public List<Oferta> prepararOfertas() throws Exception {
		List<Oferta> ofertas = new ArrayList<Oferta>();
		Conta remetente = sessaoFacade.getConta();
		for (Cotacao cotacao: cotacaoFacade.listarParaResponder()) {
			for (ItemCotacao item: cotacao.getItens()) {
				Oferta oferta = null;
				/*
				for (Oferta o: item.getOfertas()) {
					if (o.getHabilitado().getId().equals(remetente.getId())) {
						oferta = o;
					}
				}*/
				if (oferta == null) {
					oferta = new Oferta();
					oferta.setItemCotacao(item);
				}
			}
		}
		return ofertas;
	}
}
