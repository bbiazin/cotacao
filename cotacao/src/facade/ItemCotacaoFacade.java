package facade;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Evento;
import modelo.ItemCotacao;
import modelo.Oferta;

@Stateless
public class ItemCotacaoFacade extends GenericFacade<ItemCotacao> {

	@EJB
	private EventoFacade eventoFacade;
	@EJB
	private SessaoFacade sessaoFacade;
	@EJB
	private CotacaoFacade cotacaoFacade;
	
	@Override
	public List<ItemCotacao> listar() throws Exception {
		List<ItemCotacao> lista = super.listar();
		for (ItemCotacao item: lista) {
			//item.getOfertas().size();
		}
		return lista;
	}
	
	public ItemCotacao abrirComDetalhes(Long id) throws Exception {
		// TODO Refactoring
		ItemCotacao item = recuperar(id);
		//item.getOfertas().size();
		item.getCotacao().getEventos().size();
		item.getCotacao().getDestinatarios().size();
		item.getCotacao().getHabilitacoes().size();
		return item;
	}
	
}
