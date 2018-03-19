package co.edu.icesi.banco.modelo.control.webservices;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import co.edu.icesi.banco.modelo.control.ITiposDocumentosLogic;

@Stateless
@WebService(
		portName="ConsultarTipoDocumentoWSPort",
		serviceName="ConsultarTipoDocumentoWSService",
		targetNamespace="http://webservices.control.modelo.banco.icesi.edu.co/wsdl",
		endpointInterface="co.edu.icesi.banco.modelo.control.webservices.IConsultarTipoDocumentoWS"
			)
public class ConsultarTipoDocumentoWS implements IConsultarTipoDocumentoWS {

	
	@EJB
	private ITiposDocumentosLogic tipoDocLogic;
	
	@Override
	public String consultarTipoDoc(Long id) throws Exception {
		// TODO Auto-generated method stub
		String nombreTipoDoc = tipoDocLogic.getTiposDocumentosById(id).getTdocNombre();
		return nombreTipoDoc;
	}

}
