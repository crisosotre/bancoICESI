package co.edu.icesi.banco.modelo.control.webservices;

import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless
@WebService(portName = "OperacinesMatematicasPort", serviceName = "OperacionesMatematicasService", targetNamespace = "http://webservices.control.modelo.banco.icesi.co/wsdl", endpointInterface = "co.edu.icesi.banco.modelo.control.webservices.IOperacionesMatematicas")
public class OperacionesMatematicas implements IOperacionesMatematicas {

	@Override
	public int sumar(int n1, int n2) {
		// TODO Auto-generated method stub
		return n1 + n2;
	}

}
