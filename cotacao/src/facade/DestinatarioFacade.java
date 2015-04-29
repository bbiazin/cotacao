package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Cotacao;
import modelo.ItemCotacao;
import modelo.Oferta;
import modelo.Usuario;
import util.FiltroCotacaoDestinatario;

@Stateless
public class DestinatarioFacade extends GenericFacade<Cotacao> {

	@EJB
	private UsuarioFacade usuarioFacade;
	@EJB
	private ItemCotacaoFacade itemCotacaoFacade;
	@EJB
	private OfertaFacade ofertaFacade;
	@EJB
	private SessaoFacade sessaoFacade;

	public List<Oferta> prepararOfertas2() throws Exception {
		Usuario usuario = usuarioFacade.getUsuarioLogado();
		List<Oferta> ofertas = new ArrayList<Oferta>();
		for (ItemCotacao item: listarItensAbertos()) {
			Oferta oferta = ofertaFacade.recuperarSeExistir2(usuario, item);
			if (oferta == null) {
				oferta = new Oferta();
				//oferta.setHabilitado(usuario);
				oferta.setItemCotacao(item);
				oferta.setValor(null);
				ofertas.add(oferta);
			}
		}
		return ofertas;
	}

	public List<ItemCotacao> listarItensAbertos() throws Exception {
		List<ItemCotacao> itens = new ArrayList<ItemCotacao>();
		Usuario usuario = usuarioFacade.getUsuarioLogado();
		String sql = "SELECT DISTINCT c"
				+ " FROM Cotacao c JOIN c.itens i JOIN c.habilitacoes h"
				+ " WHERE h.contato.email = :email"
				+ " AND c.dataFechamento > :dataFechamento"
				+ " ORDER BY c.dataFechamento, c.id";
		for (Cotacao c: getEntityManager()
				.createQuery(sql, Cotacao.class)
				.setParameter("email", usuario.getEmail())
				.setParameter("dataFechamento", new Date())
				.getResultList()) {
			itens.addAll(c.getItens());
		}
		return itens;
	}
	
	public List<ItemCotacao> listarCotacoesRecebidas(FiltroCotacaoDestinatario filtro) throws Exception {
		
		filtro.setHabilitado(sessaoFacade.getConta());
		List<ItemCotacao> itens = filtro.getTypedQuery(getEntityManager()).getResultList();

		for (ItemCotacao i: itens) {
			// TODO Buscar no sql as entidades relacionadas.
			//i.getOfertas().size();
		}
		System.out.println("listarCotacoesRecebidas() - " + itens.size());
		return itens;
	}

}
