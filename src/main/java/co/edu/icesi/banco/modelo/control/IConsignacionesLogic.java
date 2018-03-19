package co.edu.icesi.banco.modelo.control;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Cuentas;

@Local
public interface IConsignacionesLogic {
	public void saveConsignaciones(Consignaciones entity) throws Exception;
	public void updateConsignaciones(Consignaciones entity) throws Exception;
	public void deleteConsignaciones(Long codigo, String numCuenta) throws Exception;
	public List<Consignaciones> getConsignaciones() throws Exception;
	public Consignaciones getConsignaciones(Long codigo, String numCuenta) throws Exception;
	public void consignacionDeCienMil(Clientes cliente, List<Cuentas> cuentas) throws Exception;
	
	public void ConsignacionCajero(String cueNumero, BigDecimal valor, long usuCedula) throws Exception;

}
