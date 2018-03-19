package co.edu.icesi.banco.test.ws;

import static org.junit.Assert.*;
import java.rmi.RemoteException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.control.webservices.wsdl.IConsultarTipoDocumentoWS;
import co.edu.icesi.banco.modelo.control.webservices.wsdl.IConsultarTipoDocumentoWSProxy;

public class TestWS {

	private static final Logger log = LoggerFactory.getLogger(TestWS.class);
	@Test
	public void test() {
		
		try {
			IConsultarTipoDocumentoWS consultarTipoDocumento = new IConsultarTipoDocumentoWSProxy();
			String tipoDocumento = consultarTipoDocumento.consultarTipoDoc(10L);	
		log.info(tipoDocumento);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
