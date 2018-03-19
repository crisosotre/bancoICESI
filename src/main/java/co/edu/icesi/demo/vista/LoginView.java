package co.edu.icesi.demo.vista;


import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class LoginView {

	@EJB
	private IBusinessDelegate businessDelegate;

	private InputText txtLogin;
	private Password txtClave;
	private CommandButton btnIniciarSesion;

	private static final Logger log = LoggerFactory.getLogger(UsuariosView.class);

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public InputText getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}

	public Password getTxtClave() {
		return txtClave;
	}

	public void setTxtClave(Password txtClave) {
		this.txtClave = txtClave;
	}

	public CommandButton getBtnIniciarSesion() {
		return btnIniciarSesion;
	}

	public void setBtnIniciarSesion(CommandButton btnIniciarSesion) {
		this.btnIniciarSesion = btnIniciarSesion;
	}

	public String actionIniciarSesion() {

		log.info("inicia Login");

		try {
			
			List<Usuarios> usuario = businessDelegate.getUsuariosByProperty("usuLogin", txtLogin.getValue().toString());

			if (usuario == null || usuario.isEmpty()) {
				throw new Exception("No existe un usuario con ese Login, por favor ingrese nuevamente el Login");
			}
			
			Usuarios usuarioLogin = usuario.get(0);

			if (!usuarioLogin.getUsuClave().equals(txtClave.getValue().toString()) ) {
				throw new Exception("La clave es Incorrecta, por favor ingrese nuevamente La Clave");
			}

			// Sesión Cajero
			if (usuarioLogin.getTiposUsuarios().getTusuCodigo() == 10) {
				Long cedula = usuarioLogin.getUsuCedula();
				Utilities.setManagedBeanInSession("cedulaUsuario", cedula);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión Iniciada correctamente", ""));

				return "/cajeroView.xhtml";
			}

			// Sesión Asesor Comercial
			if (usuarioLogin.getTiposUsuarios().getTusuCodigo() == 20) {
				Long cedula = usuarioLogin.getUsuCedula();
				Utilities.setManagedBeanInSession("cedulaUsuario", cedula);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión Iniciada correctamente", ""));

				return "/asesorView.xhtml";
			}

			// Sesión Administrador
			if (usuarioLogin.getTiposUsuarios().getTusuCodigo() == 30) {
				Long cedula = usuarioLogin.getUsuCedula();
				Utilities.setManagedBeanInSession("cedulaUsuario", cedula);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión Iniciada correctamente", ""));

				return "/inicioView.xhtml";
			}

			FacesContext.getCurrentInstance().addMessage("",
			new FacesMessage(FacesMessage.SEVERITY_INFO, "Sesión Iniciada correctamente", ""));

		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			e.printStackTrace();
		}

		return "";

	}

}
