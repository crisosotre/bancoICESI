package co.edu.icesi.demo.vista;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class ClientesView {
	
	@EJB
	private IBusinessDelegate businessDelegate;

}
