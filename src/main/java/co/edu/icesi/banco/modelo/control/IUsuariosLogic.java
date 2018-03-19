package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Usuarios;

@Local
public interface IUsuariosLogic {

	public void saveUsuarios(Usuarios entity) throws Exception;
	public void updateUsuarios(Usuarios entity) throws Exception;
	public void deleteUsuarios(Long usuCedula) throws Exception;
	public List<Usuarios> getUsuarios() throws Exception;
	public Usuarios getUsuariosById(Long usuCedula) throws Exception;
	public List<Usuarios> getUsuariosByProperty(String propertyName, Object value) throws Exception;
	
	public Usuarios validarUsuario(String usuLogin, String usuClave) throws Exception;
}
