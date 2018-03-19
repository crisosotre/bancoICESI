package co.edu.icesi.demo.vista;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.ConsignacionesId;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.modelo.RetirosId;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class CajeroView {

	@EJB
	private IBusinessDelegate businessDelegate;

	private static final Logger log = LoggerFactory.getLogger(AsesorView.class);

	private Long usuCedula;

	// SelecOneMenus
	private String descripcionRetiro;
	private String descripcionConsignacion;

	// Sección Retiros

	// Nuevo Retiro
	private InputText txtNumeroCuentaRet;
	private InputText txtValorRet;
	private CommandButton btnLimpiarRet;
	private CommandButton btnRetirar;

	// Listado Retiros
	private List<Retiros> tableRetiros;
	private CommandButton btnVerRetiros;

	// Nueva Consignacion
	private InputText txtNumeroCuentaCons;
	private InputText txtValorCons;
	private CommandButton btnLimpiarCons;
	private CommandButton btnConsignar;

	// Listado Consignaciones
	private List<Consignaciones> tableConsignaciones;
	private CommandButton btnVerConsignaciones;

	@PostConstruct
	public void init() {
		usuCedula =  (Long) Utilities.getfromSession("cedulaUsuario");		
		showTableConsignaciones();
		//showTableRetiros();
	}

	public String actionLimpiarRetiros() {
		log.info("Inicia actionLimpiarRetiros");
		
		txtNumeroCuentaRet.setValue("");
		txtValorRet.setValue("");
		descripcionRetiro=-1+"";
		
		log.info("Limpieza Terminada");
		return "";
	}

	public String actionRetirar() {
	
		try {
			
			if (txtNumeroCuentaRet.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese el número de cuenta");
			}
			
			Cuentas cuenta = businessDelegate.getCuentasById(txtNumeroCuentaRet.getValue().toString());
			
			if(cuenta==null) {
				throw new Exception("El número de cuenta "+txtNumeroCuentaRet.getValue().toString()+" no existe o es incorrecta");
			}
			
			if (txtValorRet.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese el valor del retiro");
			}

			if (Utilities.isNumeric(txtValorRet.getValue().toString()) == false) {
				throw new Exception("El valor del retiro debe ser númerico, un número entero");
			}

			if (Utilities.isDecimal(getTxtValorRet().getValue().toString()) == false) {
				throw new Exception(
						"El valor del retiro no debe contener caracteres extraños ");
			}
			
			Usuarios usuario = businessDelegate.getUsuariosById(usuCedula);
			
			if(usuario == null) {
				throw new Exception(
						"El usuario con la cédula "+usuCedula+" no existe");
			}
			
			Retiros retiro = new Retiros();
			retiro.setId(new RetirosId(1,cuenta.getCueNumero()));
			retiro.setRetValor(new BigDecimal(txtValorRet.getValue().toString()));
			retiro.setUsuarios(usuario);
			retiro.setCuentas(cuenta);
			retiro.setRetDescripcion(descripcionRetiro);
			
			businessDelegate.retiro(retiro);
			log.info("Retiro Exitoso");
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Transacción realizada con exito", ""));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
		
		return "";
	}

	public String actionLimpiarcConsignaciones() {
       log.info("Inicia actionLimpiarConsignaciones");
		
		txtNumeroCuentaCons.setValue("");
		txtValorCons.setValue("");
		descripcionConsignacion=-1+"";
		
		log.info("Limpieza Terminada");
		return "";
	}

	public String actionConsignar() {
		try {
			if (txtNumeroCuentaCons.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese el número de cuenta");
			}
			
			Cuentas cuenta = businessDelegate.getCuentasById(txtNumeroCuentaCons.getValue().toString());
			
			if(cuenta==null) {
				throw new Exception("El número de cuenta "+txtNumeroCuentaCons.getValue().toString()+" no existe o es incorrecta");
			}
			
			if (txtValorCons.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese el valor de la Consignacón");
			}

			if (Utilities.isNumeric(txtValorCons.getValue().toString()) == false) {
				throw new Exception("El valor de la consignación debe ser númerico, un número entero");
			}

			if (Utilities.isDecimal(getTxtValorCons().getValue().toString()) == false) {
				throw new Exception(
						"El valor de la consignación no debe contener caracteres extraños ");
			}
			
			Usuarios usuarioBuscado = businessDelegate.getUsuariosById(usuCedula);
			
			if(usuarioBuscado == null) {
				throw new Exception(
						"El usuario con la cédula "+usuCedula+" no existe");
			}
			
			Consignaciones consignacion = new Consignaciones();
			consignacion.setId(new ConsignacionesId(1,cuenta.getCueNumero()));
			consignacion.setConValor(new BigDecimal(txtValorCons.getValue().toString()));
			consignacion.setUsuarios(usuarioBuscado);
			consignacion.setCuentas(cuenta);
			consignacion.setConDescripcion(descripcionConsignacion);
		
			businessDelegate.consignacion(consignacion);
			
			log.info("Consignación Exitosa");
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Transacción realizada con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
		
		return "";
	}

	public void showTableRetiros() {
		try {
			tableRetiros=businessDelegate.findAllRetiros();
			log.info("Operación exitosa");
		} catch (Exception e) {
			log.error(e.getMessage());
		}	

	}

	public void showTableConsignaciones() {
		
		try {
			tableConsignaciones=businessDelegate.findAllConsignaciones();
			log.info("Operación exitosa");
		} catch (Exception e) {
			log.error(e.getMessage());
		}	

	}

	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public Long getUsuCedula() {
		return usuCedula;
	}

	public void setUsuCedula(Long usuCedula) {
		this.usuCedula = usuCedula;
	}

	public String getDescripcionRetiro() {
		return descripcionRetiro;
	}

	public void setDescripcionRetiro(String descripcionRetiro) {
		this.descripcionRetiro = descripcionRetiro;
	}

	public String getDescripcionConsignacion() {
		return descripcionConsignacion;
	}

	public void setDescripcionConsignacion(String descripcionConsignacion) {
		this.descripcionConsignacion = descripcionConsignacion;
	}

	public InputText getTxtNumeroCuentaRet() {
		return txtNumeroCuentaRet;
	}

	public void setTxtNumeroCuentaRet(InputText txtNumeroCuentaRet) {
		this.txtNumeroCuentaRet = txtNumeroCuentaRet;
	}

	public InputText getTxtValorRet() {
		return txtValorRet;
	}

	public void setTxtValorRet(InputText txtValorRett) {
		this.txtValorRet = txtValorRett;
	}

	public CommandButton getBtnLimpiarRet() {
		return btnLimpiarRet;
	}

	public void setBtnLimpiarRet(CommandButton btnLimpiarRet) {
		this.btnLimpiarRet = btnLimpiarRet;
	}

	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}

	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}

	public List<Retiros> getTableRetiros() {
		return tableRetiros;
	}

	public void setTableRetiros(List<Retiros> tableRetiros) {
		this.tableRetiros = tableRetiros;
	}

	public CommandButton getBtnVerRetiros() {
		return btnVerRetiros;
	}

	public void setBtnVerRetiros(CommandButton btnVerRetiros) {
		this.btnVerRetiros = btnVerRetiros;
	}

	public InputText getTxtNumeroCuentaCons() {
		return txtNumeroCuentaCons;
	}

	public void setTxtNumeroCuentaCons(InputText txtNumeroCuentaCons) {
		this.txtNumeroCuentaCons = txtNumeroCuentaCons;
	}

	public InputText getTxtValorCons() {
		return txtValorCons;
	}

	public void setTxtValorCons(InputText txtValorCons) {
		this.txtValorCons = txtValorCons;
	}

	public CommandButton getBtnLimpiarCons() {
		return btnLimpiarCons;
	}

	public void setBtnLimpiarCons(CommandButton btnLimpiarCons) {
		this.btnLimpiarCons = btnLimpiarCons;
	}

	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}

	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}

	public List<Consignaciones> getTableConsignaciones() {
		return tableConsignaciones;
	}

	public void setTableConsignaciones(List<Consignaciones> tableConsignaciones) {
		this.tableConsignaciones = tableConsignaciones;
	}

	public CommandButton getBtnVerConsignaciones() {
		return btnVerConsignaciones;
	}

	public void setBtnVerConsignaciones(CommandButton btnVerConsignaciones) {
		this.btnVerConsignaciones = btnVerConsignaciones;
	}

}
