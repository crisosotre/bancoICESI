package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.TiposDocumentos;

@Stateless
public class TiposDocumentosDAO implements ITiposDocumentosDAO{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(TiposDocumentos entity) {
		
		entityManager.persist(entity);
	}

	@Override
	public void update(TiposDocumentos entity) {
		
		entityManager.merge(entity);
	}

	@Override
	public void delete(TiposDocumentos entity) {

		entityManager.remove(entity);
	}

	@Override
	public TiposDocumentos findById(Long id) {
		return entityManager.find(TiposDocumentos.class, id);
	}

	@Override
	public List<TiposDocumentos> findAll() {
		
		String jpql = "SELECT tip FROM TiposDocumentos tip";
		return  entityManager.createQuery(jpql).getResultList();
	}

}
