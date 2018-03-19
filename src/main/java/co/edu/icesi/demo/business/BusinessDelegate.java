package co.edu.icesi.demo.business;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.jpa.criteria.predicate.NullnessPredicate;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.modelo.TiposDocumentos;
import co.edu.icesi.banco.modelo.TiposUsuarios;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.modelo.control.IClientesLogic;
import co.edu.icesi.banco.modelo.control.IConsignacionesLogic;
import co.edu.icesi.banco.modelo.control.ICuentasLogic;
import co.edu.icesi.banco.modelo.control.IRetirosLogic;
import co.edu.icesi.banco.modelo.control.ITiposDocumentosLogic;
import co.edu.icesi.banco.modelo.control.ITiposUsuariosLogic;
import co.edu.icesi.banco.modelo.control.IUsuariosLogic;

@Stateless
public class BusinessDelegate implements IBusinessDelegate {
	
	@EJB
    private IUsuariosLogic usuariosLogic;
	@EJB
    private ITiposUsuariosLogic tiposUsuariosLogic;
	@EJB
	private IClientesLogic clientesLogic;
	@EJB
	private ICuentasLogic cuentasLogic;
	@EJB
	private IRetirosLogic retirosLogic;
	@EJB
	private IConsignacionesLogic consignacionesLogic;
	@EJB
	private ITiposDocumentosLogic tiposDocumentosLogic;

	@Override
	public void saveUsuarios(Usuarios entity) throws Exception {
		
		usuariosLogic.saveUsuarios(entity);
	}

	@Override
	public void updateUsuarios(Usuarios entity) throws Exception {
		
		usuariosLogic.updateUsuarios(entity);
	}

	@Override
	public void deleteUsuarios(Long usuCedula) throws Exception {
		
		usuariosLogic.deleteUsuarios(usuCedula);
	}

	@Override
	public List<Usuarios> getUsuarios() throws Exception {
		
		return usuariosLogic.getUsuarios();
	}

	@Override
	public Usuarios getUsuariosById(Long usuCedula) throws Exception {
		
		return usuariosLogic.getUsuariosById(usuCedula);
	}

	@Override
	public List<Usuarios> getUsuariosByProperty(String propertyName, Object value) throws Exception {
		
		return usuariosLogic.getUsuariosByProperty(propertyName, value);
	}

	@Override
	public List<TiposUsuarios> findAllTiposUsuarios() throws Exception {
		
		return tiposUsuariosLogic.getTiposUsuarios();
	}

	@Override
	public void saveClientes(Clientes entity) throws Exception {
		
		clientesLogic.saveClientes(entity);
		
	}

	@Override
	public void updateCliente(Clientes entity) throws Exception {
	
		
		clientesLogic.updateClientes(entity);
	}

	@Override
	public void deleteCliente(Long cliCedula) throws Exception {
		
		clientesLogic.deleteClientes(cliCedula);
	}

	@Override
	public List<Clientes> getClientes() throws Exception {
		
		return clientesLogic.getClientes();
	}

	@Override
	public Clientes getClientesById(Long cliCedula) throws Exception {
		
		return clientesLogic.getClientesById(cliCedula);
	}

	@Override
	public Object getClientes(long cliId) {
		
		return clientesLogic.getClientes(cliId);
	}
	
	@Override
	public void saveCuenta(Cuentas entity) throws Exception {
		
		cuentasLogic.saveCuentas(entity);
		
	}

	@Override
	public void updateCuenta(Cuentas entity) throws Exception {
		cuentasLogic.updateCuentas(entity);
		
	}

	@Override
	public void deleteCuenta(String numCuenta) throws Exception {
		cuentasLogic.deleteCuentas(numCuenta);
		
	}

	@Override
	public Cuentas getCuentasById(String cuenta) throws Exception {
		return cuentasLogic.getCuentasById(cuenta);
	}

	@Override
	public List<Cuentas> getCuentas() throws Exception {
		
		return cuentasLogic.getCuentas();
	}

	@Override
	public Object getCuentas(String cueNumero) {
		
		return cuentasLogic.getCuentas(cueNumero);
	}

	@Override
	public Usuarios validarUsuarioLogin(String usuLogin, String usuClave) throws Exception {
		
		return usuariosLogic.validarUsuario(usuLogin, usuClave);
	}

	@Override
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception {
		return cuentasLogic.getCuentasDeUnCliente(idCliente);
	}

	@Override
	public void activarCuenta(Cuentas cuenta) throws Exception {
		cuentasLogic.activarCuenta(cuenta);
		
	}

	@Override
	public List<TiposDocumentos> findAllTiposDocumentos() throws Exception {		
		return tiposDocumentosLogic.getTiposDocumentos();
	}

	@Override
	public void saveTipoDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTiposDocumentos(TiposDocumentos tipoDocumento) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TiposDocumentos getTiposDocumentoByID(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void retiro(Retiros entity) throws Exception {
		retirosLogic.savedRetiros(entity);
		
	}

	@Override
	public void consignacion(Consignaciones entity) throws Exception {
		consignacionesLogic.saveConsignaciones(entity);
		
	}
	

	@Override
	public List<Consignaciones> consultarConsignaciones(String cueNumero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retiros> consultarRetiros(String cueNumero) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Retiros> findAllRetiros() throws Exception {	
		return retirosLogic.getRetiros() ;
	}

	@Override
	public List<Consignaciones> findAllConsignaciones() throws Exception {
		return consignacionesLogic.getConsignaciones();
	}

	

}
