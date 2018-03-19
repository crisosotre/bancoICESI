package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.Retiros;


@Stateless
public class RetirosDAO implements IRetirosDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Retiros entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Retiros entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Retiros entity) {
		entityManager.remove(entity);
	}

	@Override
	public Retiros findById(Long id) {
		return entityManager.find(Retiros.class, id);
	}

	@Override
	public List<Retiros> findAll() {
		String jpql ="SELECT re FROM Retiros re";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Retiros> findByProperty(String propertyName, Object Value) {
		String jpql ="SELECT re FROM Retiros re WHERE re."+propertyName+"="+Value;
		return entityManager.createQuery(jpql).getResultList();
	}

}
