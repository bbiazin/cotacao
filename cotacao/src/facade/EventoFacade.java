package facade;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Cotacao;
import modelo.Evento;

@Stateless
public class EventoFacade extends GenericFacade<Evento> {

	public Evento registrar(Evento evento) throws Exception {
		//evento.setData(new Date());
		return salvar(evento);
	}

	public List<Evento> listar(Cotacao cotacao) throws Exception {
		String sql = "select x from Evento x where" +
				" x.cotacao = :cotacao";
		return getEntityManager()
				.createQuery(sql, Evento.class)
				.setParameter("cotacao", cotacao)
				.getResultList();
	}
}
