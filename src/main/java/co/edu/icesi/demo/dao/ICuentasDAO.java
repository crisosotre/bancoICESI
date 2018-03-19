package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;


@Local
public interface ICuentasDAO {

	public void save(Cuentas entity);
	public void update(Cuentas entity);
	public void delete(Cuentas entity);
	public Cuentas findById(String cueNumero);
	public List<Cuentas> findAll();
	public List<Cuentas> findByProperty(String propertyName, Object value);
	
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception;
	public List<Consignaciones> getConsignaciones(String cueNumero);
	public List<Retiros> getRetiros(String cueNumero);
}
