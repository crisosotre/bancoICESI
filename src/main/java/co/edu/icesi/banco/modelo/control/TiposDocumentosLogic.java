package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.TiposDocumentos;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IClientesDAO;
import co.edu.icesi.demo.dao.ITiposDocumentosDAO;

@Stateless
public class TiposDocumentosLogic implements ITiposDocumentosLogic {
	
	private static final Logger log = LoggerFactory.getLogger(TiposDocumentosLogic.class);
	
	@EJB
	private ITiposDocumentosDAO tiposDocumentosDAO;
	
	@EJB
	private IClientesDAO clientesDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception {
		
		try {
			
			log.info("inicio saveTiposDocumentos");
			
			if(entity.getTdocCodigo() == 0) {
				throw new Exception ("El c�digo es obligatorio");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+entity.getTdocCodigo(),10 ,0)==false) {
				throw new Exception("El tama�o del c�digo del tipo de documento no debe ser mayor a 10 digitos");	
			}
			
			if(entity.getTdocNombre()==null || entity.getTdocNombre().equals("")) {
				throw new Exception("El nombre del tipo de documento es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getTdocNombre(), 50)==false) {
				throw new Exception("El tama�o del nombre del tipo de documento no debe ser mayor a 50 caracteres");
			}
			
			if(getTiposDocumentosById(entity.getTdocCodigo())!= null) {
				throw new Exception("Ya existe un tipo de documento con el c�digo"+entity.getTdocCodigo());
			}
			
			tiposDocumentosDAO.save(entity);
			log.info("Guard� satisfactoriamente");
		}catch(Exception e){
			log.error("saveTiposDocumentos fall�",e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception {

try {
			
			log.info("inicio updateTiposDocumentos");
			
			if(entity.getTdocCodigo() == 0) {
				throw new Exception ("El c�digo es obligatorio");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+entity.getTdocCodigo(),10 ,0)==false) {
				throw new Exception("El tama�o del c�digo del tipo de documento no debe ser mayor a 10 digitos");
				
			}
			
			if(entity.getTdocNombre()==null || entity.getTdocNombre().equals("")) {
				throw new Exception("El nombre del tipo de documento es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getTdocNombre(), 50)==false) {
				throw new Exception("El tama�o del nombre del tipo de documento no debe ser mayor a 50 caracteres");
			}
			
			if(getTiposDocumentosById(entity.getTdocCodigo())== null) {
				throw new Exception("No existe un tipo de documento con el c�digo"+entity.getTdocCodigo());
			}
			
			tiposDocumentosDAO.update(entity);
			log.info("Modific� satisfactoriamente");
		}catch(Exception e){
			log.error("updateTiposDocumentos fall�",e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTiposDocumentos(Long codigo) throws Exception {
		try {
			
			if(codigo == null || codigo == 0) {
				throw new Exception("El c�digo del tipo de documento es obligatorio");
			}
			
			List<Clientes> clientes = clientesDAO.findByProperty("tiposDocumentos.tdocCodigo", codigo);
			
			if(clientes != null && clientes.isEmpty()==false) {
				throw new Exception("El tipo de documento con el codigo "+codigo+" no se puede eliminar ya que tiene clientes asociados ");
			}
			
			TiposDocumentos entity = getTiposDocumentosById(codigo);
			
			if(getTiposDocumentosById(codigo)== null) {
				throw new Exception("No existe un tipo de documento con el c�digo "+codigo);
			}
			
			tiposDocumentosDAO.delete(entity);
			log.info("Elimin� satisfactoriamente");
			
		} catch (Exception e) {
			
			log.error("deleteTiposDocumentos fall�",e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<TiposDocumentos> getTiposDocumentos() throws Exception {
		return tiposDocumentosDAO.findAll();
	}

	@TransactionAttribute
	public TiposDocumentos getTiposDocumentosById(Long codigo) throws Exception {
		
		TiposDocumentos tiposDocumentos = null;
		
		try {
			
			if(codigo == null || codigo ==0) {
				throw new Exception("El c�digo del tipo de documento es obligatorio");
			}
			
			tiposDocumentos = tiposDocumentosDAO.findById(codigo);
			
		} catch (Exception e) {
			log.error("getTiposDocumentosById fall�", e);
			throw e;
		}
		
		return tiposDocumentos;
	}

}
