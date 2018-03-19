package co.edu.icesi.demo.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.TiposUsuarios;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.modelo.TiposDocumentos;

@Local
public interface IBusinessDelegate {

	public void saveUsuarios(Usuarios entity) throws Exception;
	public void updateUsuarios(Usuarios entity) throws Exception;
	public void deleteUsuarios(Long usuCedula) throws Exception;
	public List<Usuarios> getUsuarios() throws Exception;
	public Usuarios getUsuariosById(Long usuCedula) throws Exception;
	public List<Usuarios> getUsuariosByProperty(String propertyName, Object value) throws Exception;
	public List<TiposUsuarios> findAllTiposUsuarios() throws Exception;

	public Object getClientes(long cliId);	
	public void saveClientes(Clientes entity) throws Exception;
	public void updateCliente(Clientes entity) throws Exception;
	public void deleteCliente(Long cliCedula) throws Exception;
	public List<Clientes> getClientes() throws Exception;
	public Clientes getClientesById(Long cliCedula)throws Exception;


	public Object getCuentas(String cueNumero);
	public void saveCuenta(Cuentas entity) throws Exception;
	public void updateCuenta(Cuentas entity) throws Exception;
	public void deleteCuenta(String numCuenta) throws Exception;
	public Cuentas getCuentasById(String cuenta) throws Exception;
	public List<Cuentas> getCuentas() throws Exception;


	public Usuarios validarUsuarioLogin(String usuLogin, String usuClave) throws Exception;
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception;
	//public void asociarCuenta(CuentasRegistradas nCuentaReg) throws Exception;
	public void activarCuenta(Cuentas cuenta) throws Exception;
	
	public List<TiposDocumentos> findAllTiposDocumentos() throws Exception;
	public void saveTipoDocumentos(TiposDocumentos tipoDocumento) throws Exception;
	public void updateTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception;
	public void deleteTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception;
	public TiposDocumentos getTiposDocumentoByID(Long id) throws Exception;
	
	
	
	public void retiro(Retiros entity) throws Exception;

	public void consignacion(Consignaciones entity) throws Exception;
	
	public List<Consignaciones> consultarConsignaciones(String cueNumero);
	public List<Retiros> consultarRetiros(String cueNumero);
	
	public List<Retiros> findAllRetiros() throws Exception;
	public List<Consignaciones> findAllConsignaciones() throws Exception;

}

