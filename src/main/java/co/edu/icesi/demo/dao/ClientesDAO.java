package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.icesi.banco.modelo.Clientes;

@Stateless
public class ClientesDAO implements IClientesDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Clientes entity) {
		
		entityManager.persist(entity);
	}

	@Override
	public void update(Clientes entity) {
		
		entityManager.merge(entity);
	}

	@Override
	public void delete(Clientes entity) {
		
		entityManager.remove(entity);
	}

	@Override
	public Clientes findById(Long id) {
		return entityManager.find(Clientes.class, id);
	}

	@Override
	public List<Clientes> findAll() {
		
		String jpql = "SELECT cli FROM Clientes cli";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Clientes> findByProperty(String propertyName, Object value) {
		
		String jpql = "SELECT cli FROM Clientes cli WHERE cli."+propertyName+"="+value;
		return entityManager.createQuery(jpql).getResultList();

	}

}
