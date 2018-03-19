package co.edu.icesi.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.TiposDocumentos;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class AsesorView {
	
	@EJB
	private IBusinessDelegate businessDelegate;
	
	private static final Logger log = LoggerFactory.getLogger(AsesorView.class);
	
	private Long usuCedula;
	
//	SelectOneMenu
	private List<SelectItem> listTipoDocumento;
	
// Seccion Clientes
	
//	Nuevo Cliente
	
	private InputText txtIdentificacionNuevo;
	private String tipoDocumentoSeleccionado;
	private  InputText txtNombreNuevo;
	private  InputText txtDireccionNuevo;
	private  InputText txtTelefonoNuevo;
	private  InputText txtEmailNuevo;
	private CommandButton btnGuardarNuevo;
	private  InputText txtCuentaNueva;
	private String numeroCuentaNueva;
	private String numeroClaveNueva;
	private CommandButton btnLimpiarNuevo;
	private InputText txtClaveNueva;
	
//	Modificar Cliente
	
	private InputText txtIdModificar;
	private CommandButton btnBuscar;
	private String tipoDocumentoModificado;
	private  InputText txtNombreModificar;
	private  InputText txtDireccionModificar;
	private  InputText txtTelefonoModificar;
	private  InputText txtEmailModificar;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private CommandButton btnModificar;
	private CommandButton btnLimpiarModificar;
	
//	Listado de Clientes
	
	private List<Clientes> tableClientes;
	private CommandButton btnVerClientes;
	
//	Seccion Cuentas
	
// Nueva Cuenta
	
	private InputText txtIdCuenta;
	private CommandButton btnCrearCuenta;
	private InputText txtCuenta;
	private InputText txtClave;
	private String cuentaGenerada;
	private String claveGenerada;
	private CommandButton btnLimpiarCuenta;
	
//	Inactivar Cuenta
	
	private InputText txtIdCliente;
	private InputText txtNumeroCuenta;
	private CommandButton btnLimpiarInactiva;
	private CommandButton btnInactivarCuenta;
	
//	Listado Cuentas
	
	private List<Cuentas> tableCuentas;
	private CommandButton btnVerCuentas;
	
	@PostConstruct
	public void init() {
		usuCedula =  (Long) Utilities.getfromSession("cedulaUsuario");
		showTableClientes();
		showTableCuentas();
	}
	
	public String actionCrearCliente() {

		log.info("Inicia actionCrearCliente");

		try {

			if (txtIdentificacionNuevo.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la cedula del cliente");
			}

			if (Utilities.isNumeric(txtIdentificacionNuevo.getValue().toString()) == false) {
				throw new Exception("La cedula del Cliente debe ser numerica, un valor entero");
			}

			if (Utilities.isDecimal(txtIdentificacionNuevo.getValue().toString()) == false) {
				throw new Exception(
						"La cedula del cliente debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}

			Clientes cliente = new Clientes();
			cliente.setCliId(Long.parseLong(txtIdentificacionNuevo.getValue().toString()));
			TiposDocumentos tiposDocumentos = new TiposDocumentos();
			tiposDocumentos.setTdocCodigo(Long.parseLong(tipoDocumentoSeleccionado));
			cliente.setTiposDocumentos(tiposDocumentos);
			cliente.setCliNombre(txtNombreNuevo.getValue().toString());
			cliente.setCliDireccion(txtDireccionNuevo.getValue().toString());
			cliente.setCliTelefono(txtTelefonoNuevo.getValue().toString());
			cliente.setCliMail(txtEmailNuevo.getValue().toString());

			businessDelegate.saveClientes(cliente);
			log.info("Agregó correctamente");
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guardó satisfactoriamente", ""));
			
			List<Cuentas>cuenta = businessDelegate.getCuentasDeUnCliente(Long.parseLong(txtIdentificacionNuevo.getValue().toString()));
			numeroCuentaNueva = cuenta.get(0).getCueNumero();
			numeroClaveNueva= cuenta.get(0).getCueClave();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se generó el Número de cuenta y Clave satisfactoriamente", ""));
			log.info("Termino la operación con exito");

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String actionLimpiarCrearCliente() {
		
		txtIdentificacionNuevo.setValue("");
		txtNombreNuevo.setValue("");
		txtDireccionNuevo.setValue("");
		txtTelefonoNuevo.setValue("");
		txtEmailNuevo.setValue("");
		numeroCuentaNueva="";
		numeroClaveNueva="";
		tipoDocumentoSeleccionado=-1+"";
		
		return "";
	}
	
	public String actionBuscarCliente() {
	
		log.info("Inicia actionBuscarCliente");
		
		try {
			
			if (txtIdModificar.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la identificación del cliente");
			}

			if (Utilities.isNumeric(txtIdModificar.getValue().toString()) == false) {
				throw new Exception("La identificación del Cliente debe ser numerica, un valor entero");
			}

			if (Utilities.isDecimal(txtIdModificar.getValue().toString()) == false) {
				throw new Exception(
						"La Identificación del cliente debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
			
			Clientes clienteBuscado = businessDelegate.getClientesById(Long.parseLong(txtIdModificar.getValue().toString()));
			
			if(clienteBuscado == null) {
				throw new Exception(
						"No existe un cliente con el numero de identificación "+txtIdModificar.getValue().toString());
			}
			
			txtIdModificar.setReadonly(true);
			tipoDocumentoModificado=clienteBuscado.getTiposDocumentos().getTdocCodigo()+"";
			nombre=clienteBuscado.getCliNombre();
			direccion=clienteBuscado.getCliDireccion();
			telefono=clienteBuscado.getCliTelefono();
			email=clienteBuscado.getCliMail();
			
			
			txtNombreModificar.setReadonly(false);
			txtDireccionModificar.setReadonly(false);
			txtTelefonoModificar.setReadonly(false);
			txtEmailModificar.setReadonly(false);
			btnModificar.setDisabled(false);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se encontró un cliente con la identificación "+txtIdModificar.getValue().toString(), ""));
			
			log.info("Busqueda exitosa");
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
		
		return "";
	}
	
	public String actionModificarCliente() {

		log.info("Inicia actionModificarCliente");

		try {
			
			Clientes clienteModificar = new Clientes();
			clienteModificar.setCliId(Long.parseLong(txtIdModificar.getValue().toString()));
			TiposDocumentos tiposDocumentos = new TiposDocumentos();
			tiposDocumentos.setTdocCodigo(Long.parseLong(tipoDocumentoModificado));
			clienteModificar.setTiposDocumentos(tiposDocumentos);
			clienteModificar.setCliNombre(txtNombreModificar.getValue().toString());
			clienteModificar.setCliDireccion(txtDireccionModificar.getValue().toString());
			clienteModificar.setCliTelefono(txtTelefonoModificar.getValue().toString());
			clienteModificar.setCliMail(txtEmailModificar.getValue().toString());
			
			businessDelegate.updateCliente(clienteModificar);
			log.info("Se Modificó con Exito");
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Los cambios han sido guardados Satisfactoriamente", ""));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}

		return "";
	}
	
	public String actionLimpiarModificarCliente() {
		
		log.info("Inicia actionLimpiarModificarCliente");
		
		txtIdModificar.setReadonly(false);
		txtIdModificar.setValue("");
		txtIdModificar.setReadonly(false);
		tipoDocumentoModificado=-1+"";
		txtNombreModificar.setValue("");
		txtDireccionModificar.setValue("");
		txtTelefonoModificar.setValue("");
		txtEmailModificar.setValue("");
		txtNombreModificar.setReadonly(true);
		txtDireccionModificar.setReadonly(true);
		txtTelefonoModificar.setReadonly(true);
		txtEmailModificar.setReadonly(true);
		btnModificar.setDisabled(true);
		
		log.info("Limpieza Correcta");
		
		return "";
	}
	

	public void showTableClientes() {
		
		try {
			tableClientes=businessDelegate.getClientes();
			log.info("Operación exitosa");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	
	public String actionCrearCuenta() {
		
		log.info("Inicia actionCrearCuenta");
		
		try {
			
			if (txtIdCuenta.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la identificación del cliente");
			}

			if (Utilities.isNumeric(txtIdCuenta.getValue().toString()) == false) {
				throw new Exception("La identificación del Cliente debe ser numerica, un valor entero");
			}

			if (Utilities.isDecimal(txtIdCuenta.getValue().toString()) == false) {
				throw new Exception(
						"La Identificación del cliente debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
           
			Clientes clienteBuscado = businessDelegate.getClientesById(Long.parseLong(txtIdCuenta.getValue().toString()));
			
			if(clienteBuscado == null) {
				throw new Exception(
						"No existe un cliente con el numero de identificación "+txtIdCuenta.getValue().toString());
			}
			
			Cuentas asociarCuenta = new Cuentas();
			asociarCuenta.setClientes(clienteBuscado);
			businessDelegate.saveCuenta(asociarCuenta);
			log.info("Agregó correctamente");
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guardó satisfactoriamente", ""));
			
			List<Cuentas>cuenta = businessDelegate.getCuentasDeUnCliente(Long.parseLong(txtIdCuenta.getValue().toString()));
			
			int ultima = cuenta.size()-1;
			cuentaGenerada=cuenta.get(ultima).getCueNumero();
			claveGenerada=cuenta.get(ultima).getCueClave();
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se generó el Número de cuenta y Clave satisfactoriamente", ""));
			log.info("La operación fue satisfactoria");
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
			
		return "";
	}
	
	public String actionLimpiarCrearCuenta() {
		
		log.info("Inicia actionLimpiarCrearCuenta");
		
		txtIdCuenta.setValue("");
		txtCuenta.setValue("");
		txtClave.setValue("");
		
		log.info("Limpieza Exitosa");
		return "";
	}
	
	public String actionLimpiarInactivarCuenta() {
		
       log.info("Inicia actionLimpiarInactivarCuenta");
		
       txtIdCliente.setValue("");
       txtNumeroCuenta.setValue("");
		
       log.info("Limpieza Exitosa");
		
		return "";
	}
	
	public String actionInactivarCuenta() {
		
       log.info("Inicia actionInactivarCuenta");
		
		try {
			
			if (txtIdCliente.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la identificación del cliente");
			}

			if (Utilities.isNumeric(txtIdCliente.getValue().toString()) == false) {
				throw new Exception("La identificación del Cliente debe ser numerica, un valor entero");
			}

			if (Utilities.isDecimal(txtIdCliente.getValue().toString()) == false) {
				throw new Exception(
						"La Identificación del cliente debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
         
			Clientes clienteBuscado = businessDelegate.getClientesById(Long.parseLong(txtIdCliente.getValue().toString()));
			
			if(clienteBuscado == null) {
				throw new Exception(
						"No existe un cliente con el numero de identificación "+txtIdCliente.getValue().toString());
			}
			
			if (txtNumeroCuenta.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese el número de cuenta");
			}
			
			Cuentas cuenta = businessDelegate.getCuentasById(txtNumeroCuenta.getValue().toString());
			
			if(cuenta==null) {
				throw new Exception("El número de cuenta "+txtNumeroCuenta.getValue().toString()+" no existe");
			}
		  
			if(cuenta.getClientes().getCliId()!=clienteBuscado.getCliId()) {
			   throw new Exception("La cuenta solicitada para inactivación no es propiedad de el cliente con identificación "+clienteBuscado.getCliId());
		   }
			
			cuenta.setCueActiva("N");
			businessDelegate.updateCuenta(cuenta);
			log.info("Se realizó el cambio exitosamente");
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La cuenta numero "+cuenta.getCueNumero()+" ha sido inactivada", ""));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
			log.error(e.getMessage());
		}
		
		return "";
	}
	
	

	public void showTableCuentas() {
		
		try {
			tableCuentas=businessDelegate.getCuentas();
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
	public List<SelectItem> getListTipoDocumento() {
		
		try {
			if(listTipoDocumento==null) {
				List<TiposDocumentos> list = businessDelegate.findAllTiposDocumentos();
				if(list != null && !list.isEmpty()){
					listTipoDocumento = new ArrayList<>();
					for(TiposDocumentos tiposDocumentos : list) {
						SelectItem item = new SelectItem(tiposDocumentos.getTdocCodigo(),tiposDocumentos.getTdocNombre());
						listTipoDocumento.add(item);
					}
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return listTipoDocumento;
	}
	public void setListTipoDocumento(List<SelectItem> listTipoDocumento) {
		this.listTipoDocumento = listTipoDocumento;
	}
	public InputText getTxtIdentificacionNuevo() {
		return txtIdentificacionNuevo;
	}
	public void setTxtIdentificacionNuevo(InputText txtIdentificacionNuevo) {
		this.txtIdentificacionNuevo = txtIdentificacionNuevo;
	}
	public String getTipoDocumentoSeleccionado() {
		return tipoDocumentoSeleccionado;
	}
	public void setTipoDocumentoSeleccionado(String tipoDocumentoSeleccionado) {
		this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
	}
	public InputText getTxtNombreNuevo() {
		return txtNombreNuevo;
	}
	public void setTxtNombreNuevo(InputText txtNombreNuevo) {
		this.txtNombreNuevo = txtNombreNuevo;
	}
	public InputText getTxtDireccionNuevo() {
		return txtDireccionNuevo;
	}
	public void setTxtDireccionNuevo(InputText txtDireccionNuevo) {
		this.txtDireccionNuevo = txtDireccionNuevo;
	}
	public InputText getTxtTelefonoNuevo() {
		return txtTelefonoNuevo;
	}
	public void setTxtTelefonoNuevo(InputText txtTelefonoNuevo) {
		this.txtTelefonoNuevo = txtTelefonoNuevo;
	}
	public InputText getTxtEmailNuevo() {
		return txtEmailNuevo;
	}
	public void setTxtEmailNuevo(InputText txtEmailNuevo) {
		this.txtEmailNuevo = txtEmailNuevo;
	}
	public CommandButton getBtnGuardarNuevo() {
		return btnGuardarNuevo;
	}
	public void setBtnGuardarNuevo(CommandButton btnGuardarNuevo) {
		this.btnGuardarNuevo = btnGuardarNuevo;
	}
	public InputText getTxtCuentaNueva() {
		return txtCuentaNueva;
	}
	public void setTxtCuentaNueva(InputText txtCuentaNueva) {
		this.txtCuentaNueva = txtCuentaNueva;
	}
	public String getNumeroCuentaNueva() {
		return numeroCuentaNueva;
	}
	public void setNumeroCuentaNueva(String numeroCuentaNuevaset) {
		numeroCuentaNueva = numeroCuentaNuevaset;
	}
	public String getNumeroClaveNueva() {
		return numeroClaveNueva;
	}
	public void setNumeroClaveNueva(String numeroClaveNuevaset) {
		numeroClaveNueva = numeroClaveNuevaset;
	}
	public CommandButton getBtnLimpiarNuevo() {
		return btnLimpiarNuevo;
	}
	public void setBtnLimpiarNuevo(CommandButton btnLimpiarNuevo) {
		this.btnLimpiarNuevo = btnLimpiarNuevo;
	}
	public InputText getTxtIdModificar() {
		return txtIdModificar;
	}
	public void setTxtIdModificar(InputText txtIdModificar) {
		this.txtIdModificar = txtIdModificar;
	}
	public CommandButton getBtnBuscar() {
		return btnBuscar;
	}
	public void setBtnBuscar(CommandButton btnBuscar) {
		this.btnBuscar = btnBuscar;
	}
	public String getTipoDocumentoModificado() {
		return tipoDocumentoModificado;
	}
	public void setTipoDocumentoModificado(String tipoDocumentoModificado) {
		this.tipoDocumentoModificado = tipoDocumentoModificado;
	}
	public InputText getTxtNombreModificar() {
		return txtNombreModificar;
	}
	public void setTxtNombreModificar(InputText txtNombreModificar) {
		this.txtNombreModificar = txtNombreModificar;
	}
	public InputText getTxtDireccionModificar() {
		return txtDireccionModificar;
	}
	public void setTxtDireccionModificar(InputText txtDireccionModificar) {
		this.txtDireccionModificar = txtDireccionModificar;
	}
	public InputText getTxtTelefonoModificar() {
		return txtTelefonoModificar;
	}
	public void setTxtTelefonoModificar(InputText txtTelefonoModificar) {
		this.txtTelefonoModificar = txtTelefonoModificar;
	}
	public InputText getTxtEmailModificar() {
		return txtEmailModificar;
	}
	public void setTxtEmailModificar(InputText txtEmailModificar) {
		this.txtEmailModificar = txtEmailModificar;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public CommandButton getBtnModificar() {
		return btnModificar;
	}
	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}
	public List<Clientes> getTableClientes() {
		return tableClientes;
	}
	public void setTableClientes(List<Clientes> tableClientes) {
		this.tableClientes = tableClientes;
	}
	public CommandButton getBtnVerClientes() {
		return btnVerClientes;
	}
	public void setBtnVerClientes(CommandButton btnVerClientes) {
		this.btnVerClientes = btnVerClientes;
	}
	public InputText getTxtIdCuenta() {
		return txtIdCuenta;
	}
	public void setTxtIdCuenta(InputText txtIdCuenta) {
		this.txtIdCuenta = txtIdCuenta;
	}
	public CommandButton getBtnCrearCuenta() {
		return btnCrearCuenta;
	}
	public void setBtnCrearCuenta(CommandButton btnCrearCuenta) {
		this.btnCrearCuenta = btnCrearCuenta;
	}
	public InputText getTxtCuenta() {
		return txtCuenta;
	}
	public void setTxtCuenta(InputText txtCuenta) {
		this.txtCuenta = txtCuenta;
	}
	public InputText getTxtClave() {
		return txtClave;
	}
	public void setTxtClave(InputText txtClave) {
		this.txtClave = txtClave;
	}
	public String getCuentaGenerada() {
		return cuentaGenerada;
	}
	public void setCuentaGenerada(String cuentaGenerada) {
		this.cuentaGenerada = cuentaGenerada;
	}
	public String getClaveGenerada() {
		return claveGenerada;
	}
	public void setClaveGenerada(String claveGenerada) {
		this.claveGenerada = claveGenerada;
	}
	public CommandButton getBtnLimpiarCuenta() {
		return btnLimpiarCuenta;
	}
	public void setBtnLimpiarCuenta(CommandButton btnLimpiarCuenta) {
		this.btnLimpiarCuenta = btnLimpiarCuenta;
	}
	public InputText getTxtIdCliente() {
		return txtIdCliente;
	}
	public void setTxtIdCliente(InputText txtIdCliente) {
		this.txtIdCliente = txtIdCliente;
	}
	public InputText getTxtNumeroCuenta() {
		return txtNumeroCuenta;
	}
	public void setTxtNumeroCuenta(InputText txtNumeroCuenta) {
		this.txtNumeroCuenta = txtNumeroCuenta;
	}
	public CommandButton getBtnLimpiarInactiva() {
		return btnLimpiarInactiva;
	}
	public void setBtnLimpiarInactiva(CommandButton btnLimpiarInactiva) {
		this.btnLimpiarInactiva = btnLimpiarInactiva;
	}
	public CommandButton getBtnInactivarCuenta() {
		return btnInactivarCuenta;
	}
	public void setBtnInactivarCuenta(CommandButton btnInactivarCuenta) {
		this.btnInactivarCuenta = btnInactivarCuenta;
	}
	public List<Cuentas> getTableCuentas() {
		return tableCuentas;
	}
	public void setTableCuentas(List<Cuentas> tableCuentas) {
		this.tableCuentas = tableCuentas;
	}
	public CommandButton getBtnVerCuentas() {
		return btnVerCuentas;
	}
	public void setBtnVerCuentas(CommandButton btnVerCuentas) {
		this.btnVerCuentas = btnVerCuentas;
	}
	public InputText getTxtClaveNueva() {
		return txtClaveNueva;
	}
	public void setTxtClaveNueva(InputText claveNueva) {
		this.txtClaveNueva = claveNueva;
	}

	public CommandButton getBtnLimpiarModificar() {
		return btnLimpiarModificar;
	}

	public void setBtnLimpiarModificar(CommandButton btnLimpiarModificar) {
		this.btnLimpiarModificar = btnLimpiarModificar;
	}
	public Long getUsuCedula() {
		return usuCedula;
	}
	public void setUsuCedula(Long usuCedula) {
		this.usuCedula = usuCedula;
	}
	
}