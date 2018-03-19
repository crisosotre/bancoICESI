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


import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.icesi.banco.modelo.TiposUsuarios;
import co.edu.icesi.banco.modelo.Usuarios;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.business.IBusinessDelegate;

@ManagedBean
@ViewScoped
public class UsuariosView {
	
	@EJB
	private IBusinessDelegate businessDelegate;
	
	private String tipoUsuarioSeleccionado;
	private String tipoUsuarioModificado;
	
	private List<SelectItem> listTipoUsuario;
		
	private List<Usuarios> tableUser;
	
	private InputText txtNombreNuevo;
	private InputText txtCedulaNuevo;
	private InputText txtLoginNuevo;
	private Password txtClaveNuevo;	
	private CommandLink btnGuardar;
	
	private InputText txtNombreModificar;;
	private InputText txtCedulaModificar;
	private InputText txtLoginModificar;
	private Password txtClaveModificar;	
	private CommandLink btnActualizar;
	
	private InputText txtCedulaEliminar;
	private CommandLink btnEliminar;
	
	private InputText txtCedulaBusqueda;
	private CommandLink btnBuscar;
	
	private String cedula;
	private String nombre;
	private String login;
	private String clave;
	private String codUsuario;
	
	private static final Logger log = LoggerFactory.getLogger(UsuariosView.class);

	public String actionGuardar() {
		try {
			
			if (txtNombreNuevo.getValue().equals("")) {
				throw new Exception("El nombre del usuario es obligatorio");
			}
			if(Utilities.isOnlyLetters(txtNombreNuevo.getValue().toString())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			
			if(txtCedulaNuevo.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la cedula del usuario");
			}
			
			if(Utilities.isNumeric(txtCedulaNuevo.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser numerica, no puede contener letras ");
			}
			
			if(Utilities.isDecimal(txtCedulaNuevo.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
		
			if (txtLoginNuevo.getValue().equals("")) {
				throw new Exception("El login del usuario es obligatorio");
			}
			if (txtClaveNuevo.getValue().equals("")) {
				throw new Exception("La clave del usuario es obligatoria");
			}
			
			Usuarios usuarios = new Usuarios();
			TiposUsuarios tiposUsuarios = new TiposUsuarios();
			tiposUsuarios.setTusuCodigo(Long.parseLong(tipoUsuarioSeleccionado));
			usuarios.setTiposUsuarios(tiposUsuarios);
			usuarios.setUsuNombre(txtNombreNuevo.getValue().toString());
			usuarios.setUsuLogin(txtLoginNuevo.getValue().toString());
			usuarios.setUsuClave(txtClaveNuevo.getValue().toString());
			usuarios.setUsuCedula(Long.parseLong(txtCedulaNuevo.getValue().toString()));
			businessDelegate.saveUsuarios(usuarios);
			log.info("Agrego correctamente");
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Se guardó satisfactoriamente",""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String actionActualizar()  {
		try {
		
			if (txtNombreModificar.getValue().equals("") ) {
				throw new Exception("El nombre del usuario es obligatorio");
			}
			if(Utilities.isOnlyLetters(txtNombreModificar.getValue().toString())==false) {
				throw new Exception("Asegurese de que el nombre solo contenga letras");
			}
			
			if(txtCedulaModificar.getValue().toString().equals("") ) {
				throw new Exception("Por favor ingrese la cedula del usuario");
			}
			
			if(Utilities.isNumeric(txtCedulaModificar.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser numerica, no puede contener letras ");
			}
			
			if(Utilities.isDecimal(txtCedulaModificar.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
		
			if (txtLoginModificar.getValue().equals("")) {
				throw new Exception("El login del usuario es obligatorio");
			}
			if (txtClaveModificar.getValue().equals("")) {
				throw new Exception("La clave del usuario es obligatoria");
			}
			
			Usuarios usuarios = new Usuarios();
			TiposUsuarios tiposUsuarios = new TiposUsuarios();
			tiposUsuarios.setTusuCodigo(Long.parseLong(tipoUsuarioModificado));
			usuarios.setTiposUsuarios(tiposUsuarios);
			usuarios.setUsuNombre(txtNombreModificar.getValue().toString());
			usuarios.setUsuLogin(txtLoginModificar.getValue().toString());
			usuarios.setUsuClave(txtClaveModificar.getValue().toString());
			usuarios.setUsuCedula(Long.parseLong(txtCedulaModificar.getValue().toString()));
			businessDelegate.updateUsuarios(usuarios);
			log.info("Actualizó correctamente");
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Se Actualizó satisfactoriamente",""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String actionEliminar() {
		try {
			
			if(txtCedulaEliminar.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la cedula del usuario");
			}
			
			if(Utilities.isNumeric(txtCedulaEliminar.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser numerica, no puede contener letras ");
			}
			
			if(Utilities.isDecimal(txtCedulaEliminar.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser un valor numerico entero, no puede contener caracteres extraños o estar vacia ");
			}
			
			Long cedula =Long.parseLong(txtCedulaEliminar.getValue().toString());
			businessDelegate.deleteUsuarios(cedula);
			log.info("Eliminó correctamente");
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Se Eliminó satisfactoriamente",""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
			log.error(e.getMessage());
		}
		return "";
	}
	
	public String actionBuscar() {
		try {
			
			if(txtCedulaBusqueda.getValue().toString().equals("")) {
				throw new Exception("Por favor ingrese la cedula del usuario");
			}
			
			if(Utilities.isNumeric(txtCedulaBusqueda.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser numerica, no puede contener letras ");
			}
			
			if(Utilities.isDecimal(txtCedulaBusqueda.getValue().toString())==false) {
				throw new Exception("La cedula del usuario debe ser un valor numerico entero, no puede contener caracteres extraños");
			}
			Long ced =Long.parseLong(txtCedulaBusqueda.getValue().toString());
			Usuarios usuario = businessDelegate.getUsuariosById(ced);
			
			if(usuario==null) {
				throw new Exception("El usuario con cédula: "+ced+" no existe");
			}
			 
			cedula=usuario.getUsuCedula()+"";
			nombre=usuario.getUsuNombre();
			login=usuario.getUsuLogin();
			clave=usuario.getUsuClave();
			codUsuario=usuario.getTiposUsuarios().getTusuCodigo()+"";
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Busqueda Exitosa",""));
			log.info("Buscó correctamente");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),""));
			log.error(e.getMessage());
		}
		return "";
	}
	
	@PostConstruct
	public void showTable(){
		try {
			tableUser=businessDelegate.getUsuarios();
			log.info("Operación exitosa");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
	public List<Usuarios> getTableUser() {
	
		return tableUser;
	}

	public void setTableUser(List<Usuarios> tableUser) {
		this.tableUser = tableUser;
	}
	
	public IBusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}

	public void setBusinessDelegate(IBusinessDelegate businessDelegate) {
		this.businessDelegate = businessDelegate;
	}

	public String getTipoUsuarioSeleccionado() {
		return tipoUsuarioSeleccionado;
	}

	public void setTipoUsuarioSeleccionado(String tipoUsuarioSeleccionado) {
		this.tipoUsuarioSeleccionado = tipoUsuarioSeleccionado;
	}

	public List<SelectItem> getListTipoUsuario() {
		
		try {
			if(listTipoUsuario==null) {
				List<TiposUsuarios> list = businessDelegate.findAllTiposUsuarios();
				if(list != null && !list.isEmpty()) {
					listTipoUsuario = new ArrayList<>();
					for(TiposUsuarios tiposUsuarios : list) {
						SelectItem item = new SelectItem(tiposUsuarios.getTusuCodigo(), tiposUsuarios.getTusuNombre());
						listTipoUsuario.add(item);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listTipoUsuario;
	}

	public void setListTipoUsuario(List<SelectItem> listTipoUsuario) {
		this.listTipoUsuario = listTipoUsuario;
	}

	public InputText getTxtNombreNuevo() {
		return txtNombreNuevo;
	}

	public void setTxtNombreNuevo(InputText txtNombreNuevo) {
		this.txtNombreNuevo = txtNombreNuevo;
	}

	public InputText getTxtCedulaNuevo() {
		return txtCedulaNuevo;
	}

	public void setTxtCedulaNuevo(InputText txtCedulaNuevo) {
		this.txtCedulaNuevo = txtCedulaNuevo;
	}

	public InputText getTxtLoginNuevo() {
		return txtLoginNuevo;
	}

	public void setTxtLoginNuevo(InputText txtLoginNuevo) {
		this.txtLoginNuevo = txtLoginNuevo;
	}

	public Password getTxtClaveNuevo() {
		return txtClaveNuevo;
	}

	public void setTxtClaveNuevo(Password txtClaveNuevo) {
		this.txtClaveNuevo = txtClaveNuevo;
	}

	public CommandLink getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandLink btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public InputText getTxtNombreModificar() {
		return txtNombreModificar;
	}

	public void setTxtNombreModificar(InputText txtNombreModificar) {
		this.txtNombreModificar = txtNombreModificar;
	}

	public InputText getTxtCedulaModificar() {
		return txtCedulaModificar;
	}

	public void setTxtCedulaModificar(InputText txtCedulaModificar) {
		this.txtCedulaModificar = txtCedulaModificar;
	}

	public InputText getTxtLoginModificar() {
		return txtLoginModificar;
	}

	public void setTxtLoginModificar(InputText txtLoginModificar) {
		this.txtLoginModificar = txtLoginModificar;
	}

	public Password getTxtClaveModificar() {
		return txtClaveModificar;
	}

	public void setTxtClaveModificar(Password txtClaveModificar) {
		this.txtClaveModificar = txtClaveModificar;
	}

	public CommandLink getBtnActualizar() {
		return btnActualizar;
	}

	public void setBtnActualizar(CommandLink btnActualizar) {
		this.btnActualizar = btnActualizar;
	}

	public InputText getTxtCedulaEliminar() {
		return txtCedulaEliminar;
	}

	public void setTxtCedulaEliminar(InputText txtCedulaEliminar) {
		this.txtCedulaEliminar = txtCedulaEliminar;
	}

	public CommandLink getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(CommandLink btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

	public InputText getTxtCedulaBusqueda() {
		return txtCedulaBusqueda;
	}

	public void setTxtCedulaBusqueda(InputText txtCedulaBusqueda) {
		this.txtCedulaBusqueda = txtCedulaBusqueda;
	}

	public CommandLink getBtnBuscar() {
		return btnBuscar;
	}

	public void setBtnBuscar(CommandLink btnBuscar) {
		this.btnBuscar = btnBuscar;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getTipoUsuarioModificado() {
		return tipoUsuarioModificado;
	}

	public void setTipoUsuarioModificado(String tipoUsuarioModificado) {
		this.tipoUsuarioModificado = tipoUsuarioModificado;
	}
	
	
	
}
