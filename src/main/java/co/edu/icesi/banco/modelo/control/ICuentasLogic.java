package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Retiros;


@Local
public interface ICuentasLogic {
	
	public final static String CUENTA_BANCO = "9999-9999-0000";

	public void saveCuentas(Cuentas entity) throws Exception;
	public void updateCuentas(Cuentas entity) throws Exception;
	public void deleteCuentas(String codigo) throws Exception;
	public List<Cuentas> getCuentas() throws Exception;
	public Cuentas getCuentasById(String codigo) throws Exception;
	public Object getCuentas(String cueNumero);
	
	
	
	public void activarCuenta(Cuentas cuenta) throws Exception;
	
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception;

	//public void crearCuentaRegistrada(CuentasRegistradas nCuentaReg)throws Exception;
	//public List<CuentasRegistradas> consultarCuentasRegistradasDeUnCliente(long cedulaCliente);

	public List<Consignaciones> getConsignaciones(String cueNumero);
	public List<Retiros> consultarRetiros(String cueNumero);
}
