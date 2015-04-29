
package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Conta;
import modelo.Contato;
import modelo.ContatoAgrupado;
import modelo.Cotacao;
import modelo.DestinatarioCotacao;
import modelo.Habilitacao;
import modelo.Habilitado;
import modelo.ItemCotacao;

@Stateless
public class CotacaoFacade extends GenericFacade<Cotacao> {

	@EJB
	private ContatoAgrupadoFacade contatoAgrupadoFacade;
	@EJB
	private HabilitacaoFacade habilitacaoFacade;
	@EJB
	private UsuarioFacade usuarioFacade;
	@EJB
	private EventoFacade eventoFacade;
	@EJB
	private SessaoFacade sessaoFacade;
	@EJB
	private ContatoFacade contatoFacade;

	
	public Cotacao publicar(Cotacao cotacao) throws Exception {
		cotacao.setHabilitados(new HashSet<Habilitado>());
		// Salva os contatos
		for (DestinatarioCotacao d: cotacao.getDestinatarios()) {
			System.out.println("Iterando sobre destinatários..");
			Contato c = null;
			if (d.getContato() != null && d.getContato().getId() == null) {
				c = contatoFacade.recuperarPeloEmailSeExistir(d.getContato().getEmail());
				if (c == null) {
					System.out.println("Salvando contato: " + d.getContato().getEmail());
					c = new Contato();
					c.setNome(d.getContato().getEmail());
					c.setEmail(d.getContato().getEmail());
					c = contatoFacade.salvar(c);
				}
				d.setContato(c);
			}
			Habilitado h = new Habilitado();
			h.setCotacao(cotacao);
			h.setContato(c);
			cotacao.getHabilitados().add(h);
		}
		cotacao.setDataPublicacao(new Date());
		cotacao.setDono(sessaoFacade.getConta());
		cotacao.setCotador(sessaoFacade.getConta());

		// Notifica nova cotação.
		/*
		MailSender sender = new MailSender();
		sender.notificarCotacao(remetente, habilitados);
		 */
		return salvar(cotacao);
	}

	public List<Cotacao> listarCotador(int firstResult, int maxResults) throws Exception {
		Conta conta = sessaoFacade.getConta();
		String query = "SELECT DISTINCT c FROM Cotacao c"
				+ " WHERE c.cotador = :cotador"
				+ " ORDER BY c.dataPublicacao DESC";
		return getEntityManager().createQuery(query, Cotacao.class)
				.setParameter("cotador", conta)
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.getResultList();
	}

	public Cotacao recuperarCotacaoCotador(Long id) throws Exception {
		Conta conta = sessaoFacade.getConta();
		Cotacao cotacao = recuperar(id);
		if (!cotacao.getCotador().getEmail().equals(conta.getEmail())) {
			throw new Exception("Cotação " + id + " não encontrada");
		}
		return cotacao;
	}

	public List<Cotacao> listarHabilitado(int firstResult, int maxResults) throws Exception {
		Conta conta = sessaoFacade.getConta();
		String query = "SELECT DISTINCT c FROM Cotacao c"
				+ " JOIN c.habilitados h"
				+ " WHERE h.contato.email = :email"
				+ " ORDER BY c.dataPublicacao DESC";
	
			System.out.println( "email: " + conta.getEmail());
		return getEntityManager().createQuery(query, Cotacao.class)
				.setParameter("email", conta.getEmail())
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.getResultList();
	}

	public Cotacao recuperarCotacaoHabilitado(Long id) throws Exception {
		Conta conta = sessaoFacade.getConta();
		Cotacao cotacao = recuperar(id);
		if (cotacao.getHabilitado(conta) == null) {
			throw new Exception("Cotação " + id + " não encontrada");
		}
		return cotacao;
	}


	
	

	
	
	
	
	
	
	
	
	
	public List<Cotacao> listarAbertas() throws Exception {
		return listar();
	}

	private List<Habilitacao> habilitarDestinatarios(Cotacao cotacao) 
			throws Exception {
		List<Habilitacao> retorno = new ArrayList<Habilitacao>();
		List<Contato> contatos = new ArrayList<Contato>();
		for (DestinatarioCotacao d: cotacao.getDestinatarios()) {
			if (d.getContato() != null) {
				if (!contatos.contains(d.getContato())) {
					contatos.add(d.getContato());
				}
			} else {
				for (ContatoAgrupado ca: contatoAgrupadoFacade.listar(d.getGrupoContato())) {
					if (!contatos.contains(ca.getContato())) {
						contatos.add(ca.getContato());
					}
				}
			}
		}
		List<Habilitacao> habilitacoes = new ArrayList<Habilitacao>();
		for (Contato contato: contatos) {
			Habilitacao habilitacao = new Habilitacao();
			habilitacao.setCotacao(cotacao);
			habilitacao.setContato(contato);
			habilitacoes.add(habilitacao);
		}
		for (Habilitacao h: habilitacoes) {
			retorno.add(habilitacaoFacade.salvar(h));
		}

		return retorno;
	}


	// TODO Avaliar forma de buscar entidades relacionadas a Cotacao.
	public Cotacao recuperarParaEdicao(Long id) throws Exception {
		Cotacao entidade = recuperar(id);
		lerRelacionamentos(entidade);
		return entidade;
	}

	public List<Cotacao> listarComItens(int offset, int maxHits) throws Exception {
		String query = "SELECT c FROM Cotacao c"; /*
				+ " LEFT JOIN FETCH c.itens i "
				+ " LEFT JOIN FETCH c.destinatarios d"
				+ " LEFT JOIN FETCH c.habilitados h"
				+ " LEFT JOIN FETCH i.ofertas o";*/

		return getEntityManager().createQuery(query, Cotacao.class)
				.setFirstResult(offset)
				.setMaxResults(maxHits)
				.getResultList();
	}

	public List<Cotacao> testarQuery(String query) throws Exception {
		return getEntityManager().createQuery(query, Cotacao.class)
				.getResultList();

		/*
		List<Cotacao> lista = listar();
		for (Cotacao c: lista) {
			lerRelacionamentos(c);
		}
		return lista;
		 */
	}

	private void lerRelacionamentos(Cotacao cotacao) {
		cotacao.getItens().size();
		cotacao.getDestinatarios().size();
		cotacao.getEventos().size();
		for (ItemCotacao item: cotacao.getItens()) {
			//item.getOfertas().size();
		}
	}

	public List<Cotacao> listarNaoEncerradasCotador(int firstResult, int maxResults) throws Exception {
		Date agora = new Date();
		Conta conta = sessaoFacade.getConta();
		String query = "SELECT DISTINCT c FROM Cotacao c"
				+ " WHERE c.cotador = :cotador"
				//+ " AND c.dataFechamento <= :agora"
				+ " AND c.dataEncerramento IS NULL"
				+ " ORDER BY c.dataFechamento";
		return getEntityManager().createQuery(query, Cotacao.class)
				.setParameter("cotador", conta)
				.setParameter("agora", agora)
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.getResultList();
	}


	public List<Cotacao> listarParaResponder() throws Exception {
		return listarParaResponder(0, 0);
	}

	public List<Cotacao> listarParaResponder(int firstResult, int maxResults) throws Exception {
		Conta conta = sessaoFacade.getConta();
		Date agora = new Date();
		String query = "SELECT DISTINCT c FROM Cotacao c"
				+ " JOIN c.habilitados h"
				+ " JOIN c.itens i"
				+ " WHERE c.dataFechamento > :agora"
				+ " AND h.contato.email = :email";
		return getEntityManager().createQuery(query, Cotacao.class)
				.setParameter("agora", agora)
				.setParameter("email", conta.getEmail())
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.getResultList();
	}


}
