package co.edu.icesi.banco.modelo.control.webservices;


import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(targetNamespace="http://webservices.control.modelo.banco.icesi.edu.co/wsdl")
public interface IConsultarTipoDocumentoWS {
	
	public String consultarTipoDoc(@WebParam(name="idTipoDoc")Long id)throws Exception;

}
