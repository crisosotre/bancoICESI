package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.TiposUsuarios;


@Local
public interface ITiposUsuariosLogic {
	
	public void saveTiposUsuarios(TiposUsuarios entity) throws Exception;
	public void updateTiposUsuarios(TiposUsuarios entity) throws Exception;
	public void deleteTiposUsuarios(Long codigo) throws Exception;
	public List<TiposUsuarios> getTiposUsuarios() throws Exception;
	public TiposUsuarios getTiposUsuariosById(Long codigo) throws Exception;
	
	
	
}
