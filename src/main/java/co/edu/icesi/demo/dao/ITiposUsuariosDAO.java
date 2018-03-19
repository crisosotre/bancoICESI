package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.TiposUsuarios;

@Local
public interface ITiposUsuariosDAO {

	public void save(TiposUsuarios entity);
	public void update(TiposUsuarios entity);
	public void delete(TiposUsuarios entity);
	public TiposUsuarios findById(Long tusuCodigo);
	public List<TiposUsuarios> findAll();
}
