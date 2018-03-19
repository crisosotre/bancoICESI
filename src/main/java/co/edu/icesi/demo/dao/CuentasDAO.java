package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;

@Stateless
public class CuentasDAO implements ICuentasDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Cuentas entity) {
		entityManager.persist(entity);
	}

	@Override
	public void update(Cuentas entity) {
		entityManager.merge(entity);
	
	}

	@Override
	public void delete(Cuentas entity) {
		entityManager.remove(entity);
	}

	@Override
	public Cuentas findById(String cueNumero) {
		return entityManager.find(Cuentas.class,cueNumero);
	}

	
	//consultar todos
	@Override
	public List<Cuentas> findAll() {
		String jpql = "SELECT cu FROM Cuentas cu";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Cuentas> findByProperty(String propertyName, Object value) {
		String jpql = "SELECT cuent FROM Cuentas cuent WHERE cuent."+propertyName+"="+value;
		return entityManager.createQuery(jpql).getResultList();
	}

	//consulta las cuentas de un cliente por medio del numero de la cedula
	@Override
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception {
		
		String jpql = "SELECT cuent FROM Cuentas cuent WHERE cuent.clientes.cliId = :parametro";
		return entityManager.createQuery(jpql).setParameter("parametro", idCliente).getResultList();
	}

	@Override
	public List<Consignaciones> getConsignaciones(String cueNumero) {
		String jpql = "SELECT con FROM Consignaciones con WHERE con.cuentas."+cueNumero+ "="+":parametro";
		return entityManager.createQuery(jpql).setParameter("parametro", cueNumero).getResultList();
	
	}

	@Override
	public List<Retiros> getRetiros(String cueNumero) {
		String jpql = "SELECT ret FROM Retiros ret WHERE ret.cuentas.cueNumero = :parametro";
		return entityManager.createQuery(jpql).setParameter("parametro", cueNumero).getResultList();
	
	}

}
