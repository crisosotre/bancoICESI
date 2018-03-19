package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.ConsignacionesId;

@Local
public interface IConsignacionesDAO {

	public void save(Consignaciones entity);
	public void update(Consignaciones entity);
	public void delete(Consignaciones entity);
	public Consignaciones findById(Long codigo, String numCuenta);
	public List<Consignaciones> findAll();
	public List<Consignaciones>findByProperty(String propertyName, Object value);
	
}
