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
				throw new Exception("La identificación del cliente es obligatoria");
			}			
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCliId()+"", 10, 0)==false || (entity.getCliId() + "").length() < 8) {
				throw new Exception("El tamaño de la identificación del cliente no debe ser mayor a 10 dígitos ni menor a 8 digitos");
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
				throw new Exception("No existe un tipo de documento con el código "+entity.getTiposDocumentos().getTdocCodigo());
			}
			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatorio");
			}
			if(Utilities.isOnlyLetters(entity.getCliNombre())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50)==false) {
				throw new Exception("El tamaño del nombre del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La dirección del cliente es obligatoria");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50)==false) {
				throw new Exception("El tamaño de la dirección del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El número de télefono del cliente es obligatorio");
			}
			if(Utilities.isNumeric(entity.getCliTelefono())==false) {
				throw new Exception("El número de teléfono debe ser un valor numerico entero");
			}
			if(Utilities.isDecimal(entity.getCliTelefono())==false) {
				throw new Exception("El número de teléfono no puede contener caracteres extraños ");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50)==false) {
				throw new Exception("El tamaño del teléfono del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliMail() == null || entity.getCliMail().equals("")) {
				throw new Exception("El email del cliente es obligatorio");
			}
			if (!entity.getCliMail().contains("@")) {
				throw new Exception("El email del cliente es inválido");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50)==false) {
				throw new Exception("El tamaño del email del cliente no debe ser mayor a 50 carácteres");
			}
			
			clientesDAO.save(entity);
			
			//se asigana la cuenta al cliente que se acabo de registrar
			Cuentas cuentaAsociada= new Cuentas();
			cuentaAsociada.setClientes(entity);
			cuentaLogic.saveCuentas(cuentaAsociada);
			
			log.info("Guardó satisfactoriamente");
		} catch (Exception e) {
			log.error("saveClientes falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateClientes(Clientes entity) throws Exception {
		try {
			log.info("inicia updateCliente");
			
			if (entity.getCliId() == 0) {
				throw new Exception("La identificación del cliente es obligatoria");
			}
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(entity.getCliId()+"", 10, 0)==false || (entity.getCliId() + "").length() <= 8) {
				throw new Exception("El tamaño de la identificacion cliente no debe ser mayor a 10 dígitos ni menor a 8 dígitos");
			}
			if (entity.getTiposDocumentos() == null) {
				throw new Exception("El tipo de documento del cliente es obligatorio");
			}
			if(entity.getTiposDocumentos().getTdocCodigo()==-1) {
				throw new Exception("Por favor seleccione un tipo de documento");
			}
			if (tiposDocumentosDAO.findById(entity.getTiposDocumentos().getTdocCodigo()) == null) {
				throw new Exception("No existe un tipo de documento con el código "+entity.getTiposDocumentos().getTdocCodigo());
			}
			if (entity.getCliNombre() == null || entity.getCliNombre().equals("")) {
				throw new Exception("El nombre del cliente es obligatoria");
			}
			if(Utilities.isOnlyLetters(entity.getCliNombre())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliNombre(), 50)==false) {
				throw new Exception("El tamaño del nombre del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliDireccion() == null || entity.getCliDireccion().equals("")) {
				throw new Exception("La dirección del cliente es obligatoria");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliDireccion(), 50)==false) {
				throw new Exception("El tamaño de la dirección del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliTelefono() == null || entity.getCliTelefono().equals("")) {
				throw new Exception("El número de teléfono del cliente es obligatorio");
			}
			if(Utilities.isNumeric(entity.getCliTelefono())==false) {
				throw new Exception("El número de teléfono debe ser un valor numerico entero");
			}
			if(Utilities.isDecimal(entity.getCliTelefono())==false) {
				throw new Exception("El número de teléfono no puede contener caracteres extraños ");
			}	
			if (Utilities.checkWordAndCheckWithlength(entity.getCliTelefono(), 50)==false) {
				throw new Exception("El tamaño del teléfono del cliente no debe ser mayor a 50 carácteres");
			}
			if (entity.getCliMail() == null || entity.getCliMail().equals("")) {
				throw new Exception("El email del cliente es obligatorio");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCliMail(), 50)==false) {
				throw new Exception("El tamaño del email del cliente no debe ser mayor a 50 carácteres");
			}
			if (!entity.getCliMail().contains("@")) {
				throw new Exception("El email del cliente es inválido");
			}
			if (getClientesById(entity.getCliId()) == null) {
				throw new Exception("No existe un cliente con id "+entity.getCliId());
			}
			clientesDAO.update(entity);
			log.info("Modificó satisfactoriamente");
		} catch (Exception e) {
			log.error("saveClientes falló", e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteClientes(Long codigo) throws Exception {
		try {
			if(codigo == null || codigo == 0) {
				throw new Exception("El código del tipo de documento es obligatorio");
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
			log.info("eliminó satisfactoriamente");
		} catch (Exception e) {
			log.error("deleteClientes falló", e);
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
				throw new Exception("La identificación del cliente es obligatoria");
			}
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale(codigo+"", 10, 0)==false || (codigo + "").length() <= 8) {
				throw new Exception("El tamaño de la identificación del cliente no debe ser mayor a 10 dígitos ni menor a 8 digitos");
			}
			clientes = clientesDAO.findById(codigo);
		} catch (Exception e) {
			log.error("getClientesById falló", e);
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
