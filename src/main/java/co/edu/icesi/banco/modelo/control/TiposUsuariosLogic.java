package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.TiposUsuarios;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.ITiposUsuariosDAO;
import co.edu.icesi.demo.dao.IUsuariosDAO;




@Stateless
public class TiposUsuariosLogic implements ITiposUsuariosLogic {

	private static final Logger log = LoggerFactory.getLogger(TiposDocumentosLogic.class);

	@EJB
	private ITiposUsuariosDAO tiposUsuariosDAO;
	@EJB
	private IUsuariosDAO usuariosDAO;
	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			log.info("Inició saveTiposUsuarios");

			if (entity.getTusuCodigo() == 0)
				throw new Exception("Debe existir un código para el  tipo de usuario");
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale("" + entity.getTusuCodigo(),10,0)==false)
				throw new Exception("la cantidad de dígitos del código no debe exceder  10");
			if (entity.getTusuNombre() == null || entity.getTusuNombre().equals(""))
				throw new Exception("Debe haber un nombre para el  tipo de usuario");
			if (Utilities.checkWordAndCheckWithlength(entity.getTusuNombre(), 50) == false)
				throw new Exception("la cántidad de caracteres del nombre no debe exceder a 50 caracteres");

			if (getTiposUsuariosById(entity.getTusuCodigo()) != null)
				throw new Exception("Ya existe un tipo de usuario con c�digo " + entity.getTusuCodigo());

			tiposUsuariosDAO.save(entity);
			log.info("Se guardó exitosamente");
		} catch (Exception e) {
			log.error("Error al guardar un tipo de usuario " + e.getMessage());
			throw e;
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception {
		// TODO Auto-generated method stub
		try {
			log.info("Inició updateTiposUsuarios");

			if (entity.getTusuCodigo() == 0)
				throw new Exception("Debe existir un código para el  tipo de usuario");
			if (Utilities.checkNumberAndCheckWithPrecisionAndScale("" + entity.getTusuCodigo(),10,0)==false)
				throw new Exception("la cantidad de dígitos del código no debe exceder  10");
			if (entity.getTusuNombre() == null || entity.getTusuNombre().equals(""))
				throw new Exception("Debe haber un nombre para el  tipo de usuario");
			if (Utilities.checkWordAndCheckWithlength(entity.getTusuNombre(), 50) == false)
				throw new Exception("la cántidad de caracteres del nombre no debe exceder a 50 caracteres");

			
			if (getTiposUsuariosById(entity.getTusuCodigo()) == null)
				throw new Exception("Ya existe un tipo de usuario con código " + entity.getTusuCodigo());

			tiposUsuariosDAO.update(entity);
			log.info("modifico exitosamente");
		} catch (Exception e) {
			log.error("Error al modificar tipo usuario " + e.getMessage());
			throw e;
		}

		

	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTiposUsuarios(Long codigo) throws Exception {
		// TODO Auto-generated method stub
		try {
			log.info("inició deleteTiposUsuarios");

			if (codigo == 0)
				throw new Exception("Debe existir un código para el  tipo de usuario");

			List<Usuarios> users = usuariosDAO.findByProperty("tiposUsuarios.tusuCodigo", "" + codigo);
			if (users != null && users.isEmpty() == false)
				throw new Exception("Existen Usuarios asociados al tipo de usuario");

			TiposUsuarios entity = tiposUsuariosDAO.findById(codigo);
			if (entity == null)
				throw new Exception("No existe tipo de usuario con código" + codigo);
			
			tiposUsuariosDAO.delete(entity);

			log.info("Elimino exitosamente");

		} catch (Exception e) {
			log.error("error al eliminar " + e.getMessage());
			throw e;
		}
		
		
	}

	@TransactionAttribute
	public List<TiposUsuarios> getTiposUsuarios() throws Exception {
		// TODO Auto-generated method stub
		return tiposUsuariosDAO.findAll();
	}

	@TransactionAttribute
	public TiposUsuarios getTiposUsuariosById(Long codigo) throws Exception {
		// TODO Auto-generated method stub
		TiposUsuarios tiposUsuarios=null;
		
		try {
			if (codigo == 0||codigo==null)
				throw new Exception("Debe existir el código");
			
			tiposUsuarios=tiposUsuariosDAO.findById(codigo);
			
			return tiposUsuarios;

		} catch (Exception e) {
			log.error("Falló el getTiposUsuariosID");
			throw e;
		}
	
	}

}
