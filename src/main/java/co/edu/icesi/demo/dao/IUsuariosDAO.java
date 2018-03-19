package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.Usuarios;

@Local
public interface IUsuariosDAO {


	public void save(Usuarios entity);
	public void update(Usuarios entity);
	public void delete(Usuarios entity);
	public Usuarios findById(Long usuCedula);
	public List<Usuarios> findAll();
	public List<Usuarios> findByProperty(String propertyName, Object value);
	
	public Usuarios getUsuario(String usuLogin);
}
