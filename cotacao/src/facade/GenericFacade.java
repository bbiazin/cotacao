package facade;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Cotacao;

import util.Paginador;

//@Stateless
public abstract class GenericFacade<T> {

	@Resource
	private SessionContext context;

	private final static String UNIT_NAME = "carregamentoPU";

	@PersistenceContext(unitName = UNIT_NAME)
	private EntityManager em;

	private Class<T> classeEntidade;

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void inicializar() {
		this.classeEntidade = (Class<T>)((ParameterizedType)
				getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public T recuperar(Object id) throws Exception {
		T entidade = (T)getEntityManager().find(classeEntidade, id);
		/*
		if (entidade == null) {
			throw new Exception("Registro de id " + id + " não encontrado para a entidade " + classeEntidade.getSimpleName());
		}
		 */
		return entidade;
	}

	public T salvar(T entidade) throws Exception {
		completarEdicao(entidade);
		validar(entidade);
		entidade = this.getEntityManager().merge(entidade);
		return entidade;
	}

	public List<T> salvar(List<T> lista) throws Exception {
		List<T> retorno = new ArrayList<T>();
		for (T entidade: lista) {
			completarEdicao(entidade);
			validar(entidade);
		}
		for (T entidade: lista) {
			retorno.add(salvar(entidade));
		}
		return retorno;
	}

	public void excluir(T entidade) throws Exception {
		validarExclusao(entidade);
		this.getEntityManager().remove(getEntityManager().merge(entidade));
	}

	public List<T> listar() throws Exception {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(classeEntidade);
		Root<T> root = cq.from(classeEntidade);
		cq.select(root);

		TypedQuery<T> q = em.createQuery(cq);
		return q.getResultList();
	}

	public List<T> listar(int firstResult, int maxResults) throws Exception {
		String query = "SELECT x FROM "
				.concat(classeEntidade.getSimpleName().concat(" x"));
		//System.out.println(query);
		//return listar();
		return getEntityManager().createQuery(query, classeEntidade)
				.setFirstResult(firstResult)
				.setMaxResults(maxResults)
				.getResultList();

	}

	public List<T> listar(Paginador paginador) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(classeEntidade);
		Root<T> root = cq.from(classeEntidade);
		cq.select(root);

		TypedQuery<T> q = em.createQuery(cq);
		q.setFirstResult(paginador.primeiroRegistro());
		q.setMaxResults(paginador.getTamanhoPagina());
		return q.getResultList();
	}

	protected void validar(T entidade) throws Exception {
		//implementar na classe especialista
		//TODO pensar numa solução melhor
	}

	protected void validarExclusao(T entidade) throws Exception {
		//implementar na classe especialista
		//TODO pensar numa solução melhor
	}

	protected void completarEdicao(T entidade) throws Exception {
		//implementar na classe especialista
		//TODO pensar numa solução melhor
	}


}