package facade;

import javax.ejb.Stateless;

import modelo.Pai;

@Stateless
public class PaiFacade extends GenericFacade<Pai> {

	public Pai salvarRascunho(Pai entidade) throws Exception {
		return salvar(entidade);
	}
}
