package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IConsignacionesDAO;
import co.edu.icesi.demo.dao.IRetirosDAO;
import co.edu.icesi.demo.dao.IUsuariosDAO;

@Stateless
public class UsuariosLogic implements IUsuariosLogic{
	
	private static final Logger log = LoggerFactory.getLogger(UsuariosLogic.class);
	
	@EJB
	private IUsuariosDAO usuariosDAO;
	
	@EJB
	private IConsignacionesDAO consignacionesDAO;
	
	@EJB
	private IRetirosDAO retirosDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveUsuarios(Usuarios entity) throws Exception {

		try {
			
			log.info("inicio saveUsuarios");
			
			if(entity.getUsuCedula()==0 ) {
				throw new Exception("La cédula del usuario es obligatoria");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+entity.getUsuCedula(),10 ,0)==false) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}
			
			if(entity.getTiposUsuarios().getTusuCodigo()==0) {
				throw new Exception("El código del tipo de usuario es obligatorio");
			}
					
			if(getUsuariosById(entity.getUsuCedula())!=null) {
				throw new Exception("Ya existe un usuario con la cédula "+entity.getUsuCedula());
			}
			
			if(entity.getUsuNombre()==null || entity.getUsuNombre().equals("")) {
				throw new Exception ("El nombre del usuario es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuNombre(), 50)==false) {
				throw new Exception ("El tamaño del nombre del usuario no debe ser mayor a 50 caracteres");
			}
			
			if(entity.getUsuLogin()==null || entity.getUsuLogin().equals("")) {
				throw new Exception ("El login del usuario es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuLogin(), 30)==false) {
				throw new Exception ("El login del usuario no debe ser mayor a 30 caracteres");
			}
			
			List<Usuarios> usuarios = getUsuariosByProperty("usuLogin", entity.getUsuLogin());
			
			if(usuarios != null && usuarios.isEmpty() == false) {
				throw new Exception("El login del usuario "+entity.getUsuLogin()+" ya existe");
			}
			
			if(entity.getUsuClave()==null || entity.getUsuClave().equals("")) {
				throw new Exception ("La clave del usuario es obligatoria");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuLogin(), 50)==false) {
				throw new Exception ("La clave del usuario no debe ser mayor a 50 caracteres");
			}
			
			if(entity.getTiposUsuarios().getTusuCodigo()==-1) {
				throw new Exception ("Por favor seleccione un Tipo de Documento");
			}
			
			usuariosDAO.save(entity);
			log.info("Guardó satisfactoriamente");
			
		} catch (Exception e) {
			log.error("saveUsuarios falló",e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateUsuarios(Usuarios entity) throws Exception {

		try {
			
			log.info("inicio updateUsuarios");
			
			if(entity.getUsuCedula()==0 ) {
				throw new Exception("La cédula del usuario es obligatoria");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+entity.getUsuCedula(),10 ,0)==false) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}
			
			if(entity.getTiposUsuarios().getTusuCodigo()==0) {
				throw new Exception("El código del tipo de usuario es obligatorio");
			}
					
			if(getUsuariosById(entity.getUsuCedula())==null) {
				throw new Exception("No existe un usuario con la cédula: "+entity.getUsuCedula()+" para ser modificado");
			}
			
			if(entity.getUsuNombre()==null || entity.getUsuNombre().equals("")) {
				throw new Exception ("El nombre del usuario es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuNombre(), 50)==false) {
				throw new Exception ("El tamaño del nombre del usuario no debe ser mayor a 50 caracteres");
			}
			
			if(entity.getUsuLogin()==null || entity.getUsuLogin().equals("")) {
				throw new Exception ("El login del usuario es obligatorio");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuLogin(), 30)==false) {
				throw new Exception ("El login del usuario no debe ser mayor a 30 caracteres");
			}
			
			List<Usuarios> usuarios = getUsuariosByProperty("usuLogin", entity.getUsuLogin());
			
			if(usuarios != null && usuarios.isEmpty() == false) {
				if(usuarios.get(0).getUsuCedula()!=entity.getUsuCedula()) {
				throw new Exception("El login del usuario "+entity.getUsuLogin()+" ya existe");
				}
			}
			
			if(entity.getUsuClave()==null || entity.getUsuClave().equals("")) {
				throw new Exception ("La clave del usuario es obligatoria");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getUsuClave(), 50)==false) {
				throw new Exception ("La clave del usuario no debe ser mayor a 50 caracteres");
			}
			
			if(entity.getTiposUsuarios().getTusuCodigo()==-1) {
				throw new Exception ("Por favor seleccione un Tipo de Documento");
			}
			
			usuariosDAO.update(entity);
			log.info("Modificó satisfactoriamente");
			
		} catch (Exception e) {
			log.error("updateUsuarios falló",e);
			throw e;
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteUsuarios(Long usuCedula) throws Exception {

		try {
			
			log.info("deleteUsuarios inició");
			
			if(usuCedula == null || usuCedula==0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+usuCedula,10 ,0)==false) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}
			
			List<Consignaciones> consignaciones = consignacionesDAO.findByProperty("usuarios.usuCedula", usuCedula);
			
			if(consignaciones != null && consignaciones.isEmpty()==false) {
				throw new Exception("El usuario con la cédula "+usuCedula+" no se puede eliminar porque tiene consignaciones asociadas");
			}
			
			List<Retiros> retiros = retirosDAO.findByProperty("usuarios.usuCedula", usuCedula);
			
			if(retiros !=null && retiros.isEmpty()==false) {
				throw new Exception("El usuario con la cédula "+usuCedula+" no se puede eliminar porque tiene retiros asociados");
			}
			
			Usuarios entity = getUsuariosById(usuCedula);
			
			if(getUsuariosById(usuCedula)== null) {
				throw new Exception("No existe un usuario con la cédula "+usuCedula);
			}
			
			usuariosDAO.delete(entity);
			log.info("Eliminó satisfactoriamente");
			
		} catch (Exception e) {
			log.error("deleteUsuarios falló",e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Usuarios> getUsuarios() throws Exception {
		return usuariosDAO.findAll();
	}

	@TransactionAttribute
	public Usuarios getUsuariosById(Long usuCedula) throws Exception {

		Usuarios usuarios = null;

		try {

			if (usuCedula == null || usuCedula == 0) {
				throw new Exception("La cédula del usuario es obligatoria");
			}

			if (Utilities.checkNumberAndCheckWithPrecisionAndScale("" + usuCedula, 10, 0) == false) {
				throw new Exception("El tamaño de la cédula del usuario no debe ser mayor a 10 digitos");
			}

			usuarios = usuariosDAO.findById(usuCedula);

		} catch (Exception e) {
			log.error("getUsuariosById falló", e);
			throw e;
		}
		return usuarios;
	}

	@TransactionAttribute
	public List<Usuarios> getUsuariosByProperty(String propertyName, Object value) throws Exception {
		return usuariosDAO.findByProperty(propertyName, value);
	}

	@Override
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception {
		// TODO Auto-generated method stub
		
		Usuarios usuario = usuariosDAO.getUsuario(usuLogin);

		if (usuario == null) {
			throw new Exception("El usuario no Existe!");
		}

		if (usuario.getUsuClave().equals(usuClave)) {
			
			//load
			usuario.getTiposUsuarios().getTusuNombre();

			return usuario;

		} else {
			throw new Exception("Password no valido.");
		}
	
	}

}
