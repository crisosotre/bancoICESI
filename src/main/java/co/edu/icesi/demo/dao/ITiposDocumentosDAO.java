package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.TiposDocumentos;

@Local
public interface ITiposDocumentosDAO {

	public void save(TiposDocumentos entity);
	public void update(TiposDocumentos entity);
	public void delete(TiposDocumentos entity);
	public TiposDocumentos findById(Long id);
	public List<TiposDocumentos> findAll();
}
