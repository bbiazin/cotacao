package facade;

import java.util.List;

import javax.ejb.Stateless;

import modelo.Membro;
import modelo.Usuario;

@Stateless
public class MembroFacade extends GenericFacade<Membro> {

	public Membro listar(Usuario usuario) throws Exception {
		String sql = "SELECT x FROM Membro x WHERE" +
				" x.usuario = :usuario";
		List<Membro> lista = getEntityManager()
				.createQuery(sql, Membro.class)
				.setParameter("usuario", usuario)
				.getResultList();
		if (lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}
	

}
