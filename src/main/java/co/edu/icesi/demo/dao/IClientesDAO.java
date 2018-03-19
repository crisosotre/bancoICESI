package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Clientes;

@Local
public interface IClientesDAO {
	
	public void save(Clientes entity);
	public void update(Clientes entity);
	public void delete(Clientes entity);
	public Clientes findById(Long id);
	public List<Clientes> findAll();
	public List<Clientes> findByProperty(String propertyName, Object value);

}
