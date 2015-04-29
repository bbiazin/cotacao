package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import modelo.Habilitado;
import modelo.Oferta;
import modelo.Resposta;

@Stateless
public class RespostaFacade extends GenericFacade<Resposta> {

	@EJB
	private OfertaFacade ofertaFacade;
	
	@EJB
	private ContatoFacade contatoFacade;
	
	public Resposta recuperarSeExistir(Habilitado habilitado) throws Exception {
		String query = "SELECT x FROM Resposta x"
				+ " JOIN x.ofertas"
				+ " WHERE x.habilitado = :habilitado" 
				+ " ORDER BY x.id DESC";
		List<Resposta> respostas = getEntityManager()
				.createQuery(query, Resposta.class)
				.setParameter("habilitado", habilitado)
				.getResultList();
		if (respostas != null) {
			return respostas.get(0);
		}
		return null;

	}

	public Resposta salvarRascunhoResposta(Resposta entidade) throws Exception {
		/*
		Resposta x = recuperar(153848l);
		
		x.getOfertas().remove(x.getOfertas().iterator().next());
		System.out.println("Total de ofertas: " + x.getOfertas().size());
		salvar(x);
		*/
		/*
		Resposta resposta = recuperarSeExistir(entidade.getHabilitado());
		if (resposta == null) {
			resposta = entidade;
		
		} else {
			entidade.setId(resposta.getId());
			for (Oferta oferta: resposta.getOfertas()) {
				for (Oferta ofertaNova: entidade.getOfertas()) {
					if (oferta.getId().equals(ofertaNova.getId())) {
						ofertaNova.setId(oferta.getId());
					}
				}
			}
		}
		*/
		/*
		for (Oferta o: r.getOfertas()) {
			r.getOfertas().remove(o);
			//o.setResposta(null);
		}*/

		//r.setOfertas(entidade.getOfertas());
		entidade.setDataEnvio(null);
		/*
		for (Oferta o: entidade.getOfertas()) {
			if (o.getValor() == null || o.getValor() <= 0) {
				System.out.println("Excluindo: " + o.getId() + " - " + o.getValor());
				//ofertaFacade.excluir(o);
				o.setResposta(null);
				entidade.getOfertas().remove(o);
			}
		}*/

		/*
		//contatoFacade.excluir(contatoFacade.recuperar(1l));
		
	*/
		
		/*
		Resposta retorno = salvar(entidade);
		List<Oferta> ofertas = new ArrayList<Oferta>();
		for (Oferta o: retorno.getOfertas()) {
			if (o.getValor() == null || o.getValor() <= 0) {
				System.out.println("Excluindo: " + o.getId() + " - " + o.getValor());
				//ofertaFacade.excluir(o);
				ofertas.add(o);
				//o.setResposta(null);
				//entidade.getOfertas().remove(o);
			}
		}
		for (Oferta o: ofertas) {
			//o.setResposta(null);
			ofertaFacade.excluir(ofertaFacade.recuperar(o.getId()));
			System.out.println("Acabou de excluir id " + o.getId());
			retorno.getOfertas().remove(o);
		}
		
		return retorno;*/

		return salvar(entidade);
		
		/*
		Oferta o = ofertaFacade.recuperar(153943l);
		System.out.println("Oferta recuperada para excluir: " + o.getId() + " - " + o.getValor());
		ofertaFacade.excluir(o);
		System.out.println("Oferta excluída.");
		o = ofertaFacade.recuperar(153943l);
		System.out.println(o);
		if (o != null) {
			System.out.println("Não excluiu");
		} else {
			System.out.println("Excluiu");
		}

		return null;
		*/
	}
	
	public Resposta responderCotacao(Resposta entidade) throws Exception {
		entidade.setDataEnvio(new Date());
		return salvar(entidade);
	}
	
	private Resposta merge(Resposta nova, Resposta atual) {
		/*
		atual.setObservacao(nova.getObservacao());
		for (Oferta ofertaNova: nova.getOfertas()) {
			for (Oferta ofertaAtual: atual.getOfertas()) {
				if (ofertaNova.getItemCotacao().getId().equals(ofertaAtual.getItemCotacao().getId())) {
					ofertaAtual.setValor(ofertaNova.getValor());
				}
			}
		}*/
		return atual;
	}
}
