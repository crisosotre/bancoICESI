package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.ConsignacionesId;

@Stateless
public class ConsignacionesDAO implements IConsignacionesDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Consignaciones entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Consignaciones entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Consignaciones entity) {
		entityManager.remove(entity);
	}

	@Override
	public Consignaciones findById(Long codigo, String numCuenta) {
		ConsignacionesId id = new ConsignacionesId(codigo, numCuenta);
		return entityManager.find(Consignaciones.class, id);
	}

	@Override
	public List<Consignaciones> findAll() {
		String jpql = "SELECT con FROM Consignaciones con";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Consignaciones> findByProperty(String propertyName, Object value) {
		String jpql = "SELECT con FROM Consignaciones con WHERE con."+propertyName+"="+value;
		return entityManager.createQuery(jpql).getResultList();
	}

}
