package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Retiros;


@Local
public interface IRetirosDAO {

	public void save(Retiros entity);
	public void update(Retiros entity);
	public void delete(Retiros entity);
	public Retiros findById(Long codigo);
	public List<Retiros> findAll();
	public List<Retiros> findByProperty(String propertyName, Object Value);
}
