package co.edu.icesi.banco.modelo.control;

import java.util.List;

import javax.ejb.Local;

import co.edu.icesi.banco.modelo.TiposDocumentos;

@Local
public interface ITiposDocumentosLogic {

	public void saveTiposDocumentos(TiposDocumentos entity) throws Exception;
	public void updateTiposDocumentos(TiposDocumentos entity) throws Exception;
	public void deleteTiposDocumentos(Long codigo) throws Exception;
	public List<TiposDocumentos> getTiposDocumentos() throws Exception;
	public TiposDocumentos getTiposDocumentosById(Long codigo) throws Exception;
}
