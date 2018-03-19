package co.edu.icesi.banco.modelo.control;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Retiros;

@Local
public interface IRetirosLogic {
	
	
	public void savedRetiros (Retiros  entity) throws Exception;
	public void updateRetiros(Retiros entity) throws Exception;
	public void deleteRetiros(Long codigo) throws Exception;
	public List<Retiros>getRetiros() throws Exception;
	public Retiros getRetiroById(Long codigo) throws Exception;
	
	public void retiroCajero(String cueNumero, BigDecimal valor, long usuCedula) throws Exception;

}
