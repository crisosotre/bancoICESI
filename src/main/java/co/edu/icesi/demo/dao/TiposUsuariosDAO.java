package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.TiposUsuarios;

@Stateless
public class TiposUsuariosDAO implements ITiposUsuariosDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(TiposUsuarios entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(TiposUsuarios entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(TiposUsuarios entity) {
		entityManager.remove(entity);
	}

	@Override
	public TiposUsuarios findById(Long tusuCodigo) {
		return entityManager.find(TiposUsuarios.class,tusuCodigo);
	}

	@Override
	public List<TiposUsuarios> findAll() {
		List<TiposUsuarios> list = null;
		try {
			String jpql = "SELECT tusu FROM TiposUsuarios tusu";
			list = entityManager.createQuery(jpql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
