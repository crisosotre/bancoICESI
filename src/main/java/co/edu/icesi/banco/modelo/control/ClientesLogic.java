package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IClientesDAO;
import co.edu.icesi.demo.dao.ICuentasDAO;
import co.edu.icesi.demo.dao.ITiposDocumentosDAO;


@Stateless
public class ClientesLogic implements IClientesLogic {
	
	@EJB
	private ITiposDocumentosDAO tiposDocumentosDAO;
	
	@EJB
	private IClientesDAO clientesDAO;
	
	@EJB
	private ICuentasDAO cuentasDAO;
	
	@EJB
	private ICuentasLogic cuentaLogic;
	
	private static final Logger log = LoggerFactory.getLogger(ClientesLogic.class);
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveClientes(Clientes entity) throws Exception {
		try {
			log.info("inicia saveClientes");
			if (entity.getCliId() == 0) {
				throw new Exception("La identificaci�n del cliente es obligatoria");
			}			
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCliId()+"", 10, 0)==false || (entity.getCliId() + "").length() < 8) {
				throw new Exception("El tama�o de la identificaci�n del cliente no debe ser mayor a 10 d�gitos ni menor a 8 digitos");
			}
			if (getClientesById(entity.getCliId()) != null) {
				throw new Exception("Ya existe un cliente con id "+entity.getCliId());
			}
			if (entity.getTiposDocumentos() == null) {
				throw new Exception("El tipo de documento del cliente es obligatorio");
			}
			if (entity.getTiposDocumentos().getTdocCodigo()== -1) {
				throw new Exception("Por favor seleccione un tipo de documento");
			}
			if (tiposDocumentosDAO.findById(entity.getTiposDocumentos().getTdocCodigo()) == null) {
				throw new Exception("No existe un tipo de documento con el c�digo "+entity.getTiposDocumentos().getTdocCodigo());
			}
			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatorio");
			}
			if(Utilities.isOnlyLetters(entity.getCliNombre())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50)==false) {
				throw new Exception("El tama�o del nombre del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La direcci�n del cliente es obligatoria");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50)==false) {
				throw new Exception("El tama�o de la direcci�n del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El n�mero de t�lefono del cliente es obligatorio");
			}
			if(Utilities.isNumeric(entity.getCliTelefono())==false) {
				throw new Exception("El n�mero de tel�fono debe ser un valor numerico entero");
			}
			if(Utilities.isDecimal(entity.getCliTelefono())==false) {
				throw new Exception("El n�mero de tel�fono no puede contener caracteres extra�os ");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50)==false) {
				throw new Exception("El tama�o del tel�fono del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliMail() == null || entity.getCliMail().equals("")) {
				throw new Exception("El email del cliente es obligatorio");
			}
			if (!entity.getCliMail().contains("@")) {
				throw new Exception("El email del cliente es inv�lido");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50)==false) {
				throw new Exception("El tama�o del email del cliente no debe ser mayor a 50 car�cteres");
			}
			
			clientesDAO.save(entity);
			
			//se asigana la cuenta al cliente que se acabo de registrar
			Cuentas cuentaAsociada= new Cuentas();
			cuentaAsociada.setClientes(entity);
			cuentaLogic.saveCuentas(cuentaAsociada);
			
			log.info("Guard� satisfactoriamente");
		} catch (Exception e) {
			log.error("saveClientes fall�", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateClientes(Clientes entity) throws Exception {
		try {
			log.info("inicia updateCliente");
			
			if (entity.getCliId() == 0) {
				throw new Exception("La identificaci�n del cliente es obligatoria");
			}
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCliId()+"", 10, 0)==false || (entity.getCliId() + "").length() <= 8) {
				throw new Exception("El tama�o de la identificacion cliente no debe ser mayor a 10 d�gitos ni menor a 8 d�gitos");
			}
			if (entity.getTiposDocumentos() == null) {
				throw new Exception("El tipo de documento del cliente es obligatorio");
			}
			if(entity.getTiposDocumentos().getTdocCodigo()==-1) {
				throw new Exception("Por favor seleccione un tipo de documento");
			}
			if (tiposDocumentosDAO.findById(entity.getTiposDocumentos().getTdocCodigo()) == null) {
				throw new Exception("No existe un tipo de documento con el c�digo "+entity.getTiposDocumentos().getTdocCodigo());
			}
			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatoria");
			}
			if(Utilities.isOnlyLetters(entity.getCliNombre())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50)==false) {
				throw new Exception("El tama�o del nombre del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La direcci�n del cliente es obligatoria");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50)==false) {
				throw new Exception("El tama�o de la direcci�n del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El n�mero de tel�fono del cliente es obligatorio");
			}
			if(Utilities.isNumeric(entity.getCliTelefono())==false) {
				throw new Exception("El n�mero de tel�fono debe ser un valor numerico entero");
			}
			if(Utilities.isDecimal(entity.getCliTelefono())==false) {
				throw new Exception("El n�mero de tel�fono no puede contener caracteres extra�os ");
			}	
			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50)==false) {
				throw new Exception("El tama�o del tel�fono del cliente no debe ser mayor a 50 car�cteres");
			}
			if (entity.getCliMail() == null || entity.getCliMail().equals("")) {
				throw new Exception("El email del cliente es obligatorio");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50)==false) {
				throw new Exception("El tama�o del email del cliente no debe ser mayor a 50 car�cteres");
			}
			if (!entity.getCliMail().contains("@")) {
				throw new Exception("El email del cliente es inv�lido");
			}
			if (getClientesById(entity.getCliId()) == null) {
				throw new Exception("No existe un cliente con id "+entity.getCliId());
			}
			clientesDAO.update(entity);
			log.info("Modific� satisfactoriamente");
		} catch (Exception e) {
			log.error("saveClientes fall�", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteClientes(Long codigo) throws Exception {
		try {
			if(codigo == null || codigo == 0) {
				throw new Exception("El c�digo del tipo de documento es obligatorio");
			}
			List<Cuentas> cuentas =  cuentasDAO.findByProperty("clientes.cliId", codigo);
			if (cuentas != null && !cuentas.isEmpty()) {
				throw new Exception("El cliente con id "+codigo+" no se puede eliminar ya que tiene cuentas asociadas");
			}
			
			Clientes entity = getClientesById(codigo);
			
			if (entity == null) {
				throw new Exception("No existe un cliente con id "+codigo);
			}
			clientesDAO.delete(entity);
			log.info("elimin� satisfactoriamente");
		} catch (Exception e) {
			log.error("deleteClientes fall�", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Clientes> getClientes() throws Exception {
		return clientesDAO.findAll();
	}

	@TransactionAttribute
	public Clientes getClientesById(Long codigo) throws Exception {
		Clientes clientes = null;
		try {
			if(codigo == null || codigo == 0) {
				throw new Exception("La identificaci�n del cliente es obligatoria");
			}
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(codigo+"", 10, 0)==false || (codigo + "").length() <= 8) {
				throw new Exception("El tama�o de la identificaci�n del cliente no debe ser mayor a 10 d�gitos ni menor a 8 digitos");
			}
			clientes = clientesDAO.findById(codigo);
		} catch (Exception e) {
			log.error("getClientesById fall�", e);
			throw e;
		}
		return clientes;
	}

	@Override
	public Object getClientes(long cliId) {
		// TODO Auto-generated method stub
		return null;
	}
}
