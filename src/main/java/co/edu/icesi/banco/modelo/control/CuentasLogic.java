package co.edu.icesi.banco.modelo.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IClientesDAO;
import co.edu.icesi.demo.dao.IConsignacionesDAO;
import co.edu.icesi.demo.dao.ICuentasDAO;
import co.edu.icesi.demo.dao.IRetirosDAO;


@Stateless
public class CuentasLogic implements ICuentasLogic {
	
	private final static String CLAVE_INICIAL = "****";
	
	@EJB
	private ICuentasDAO cuentasDAO;
	
	@EJB
	private IClientesDAO clientesDAO;
	
	@EJB
	private IConsignacionesDAO consignacionesDAO;
	
	@EJB
	private IRetirosDAO retirosDAO;
	
	private static final Logger log = LoggerFactory.getLogger(CuentasLogic.class);
		
	
	//ya se modifico con la generacion de numero de cuenta aleatorio
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCuentas(Cuentas entity) throws Exception {
		try {
			log.info("inicia saveCuentas");

			if (entity.getClientes() == null) {
				throw new Exception("El cliente asociado a la cuenta es obligatorio");
			}
			if (clientesDAO.findById(entity.getClientes().getCliId()) == null) {
				throw new Exception("No existe un cliente con id "+entity.getClientes().getCliId());
			}
			
			List<String> cuentaYclave = generarNumeroCuentaYClave(entity.getClientes().getCliId());
			entity.setCueNumero(cuentaYclave.get(0));
			entity.setCueSaldo(new BigDecimal("0.0"));
			entity.setCueActiva("N");
			entity.setCueClave(cuentaYclave.get(1));
			cuentasDAO.save(entity);	
			log.info("Guardó satisfactoriamente");
		} catch (Exception e) {
			log.error("saveCuentas falló", e);
			throw e;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateCuentas(Cuentas entity) throws Exception {
		try {
			log.info("inicia updateCuentas");
			if (entity.getCueNumero() == null || entity.getCueNumero().equals("")) {
				throw new Exception("el número de la cuenta es obligatorio");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCueNumero(), 30)==false) {
				throw new Exception("El tamaño del número de cuenta no debe ser mayor a 30 dígitos");
			}
			if (entity.getClientes() == null) {
				throw new Exception("El cliente asociado a la cuenta es obligatorio");
			}
			if (clientesDAO.findById(entity.getClientes().getCliId()) == null) {
				throw new Exception("No existe un cliente con identificación "+entity.getClientes().getCliId());
			}
			if (entity.getCueSaldo() == null ) {
				throw new Exception("El saldo de la cuenta es obligatorio");
			}
			if (entity.getCueSaldo().intValue()<0) {
				throw new Exception("El saldo no puede ser negativo");
			}
	
			if (entity.getCueActiva() == null || entity.getCueActiva().equals("")) {
				throw new Exception("El estado de la cuenta activa debe ser obligatorio");
			}
			if (entity.getCueClave() == null || entity.getCueClave().equals("")) {
				throw new Exception("La clave de la cuenta debe ser obligatorio");
			}
			if (Utilities.checkWordAndCheckWithlength(entity.getCueClave(), 50)==false) {
				throw new Exception("El tamaño de la clave de la cuenta no debe ser mayor a 50 dígitos");
			}
			if (getCuentasById(entity.getCueNumero()) == null) {
				throw new Exception("No existe una cuenta con número "+entity.getCueNumero());
			}
			cuentasDAO.update(entity);
			log.info("Modificó satisfactoriamente");
		} catch (Exception e) {
			log.error("saveCuentas falló", e);
			throw e;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCuentas(String codigo) throws Exception {
		try {
			if(codigo == null || codigo.equals("")) {
				throw new Exception("El número de cuenta es obligatorio");
			}
			List<Consignaciones> consignaciones =  consignacionesDAO.findByProperty("cuentas.cueNumero", codigo);
			if (consignaciones != null && !consignaciones.isEmpty()) {
				throw new Exception("La cuenta con número "+codigo+" no se puede eliminar ya que tiene consignaciones asociadas");
			}
			
			List<Retiros> retiros =  retirosDAO.findByProperty("cuentas.cueNumero", codigo);
			if (retiros != null && !retiros.isEmpty()) {
				throw new Exception("La cuenta con número "+codigo+" no se puede eliminar ya que tiene retiros asociados");
			}
			Cuentas entity = getCuentasById(codigo);
			
			if (entity == null) {
				throw new Exception("No existe un cliente con id "+codigo);
			}
			
			// pasa a ser inactiva
			entity.setCueActiva("N");
			updateCuentas(entity);
			
			log.info("se inactivo satisfactoriamente");
		} catch (Exception e) {
			log.error("inactivacion de la cuenta falló", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Cuentas> getCuentas() throws Exception {
		return cuentasDAO.findAll();
	}

	@TransactionAttribute
	public Cuentas getCuentasById(String codigo) throws Exception {
		Cuentas cuentas = null;
		try {
			if(codigo == null || codigo.equals("")) {
				throw new Exception("El número de la cuenta es obligatorio");
			}
			cuentas = cuentasDAO.findById(codigo);
		} catch (Exception e) {
			log.error("getCuentasById falló", e);
			throw e;
		}
		return cuentas;
	}

	@Override
	public Object getCuentas(String cueNumero) {
		// TODO Auto-generated method stub
		return cueNumero;
	}
	
	
	public List<String> generarNumeroCuentaYClave(Long idCliente) {

		Calendar calendario = Calendar.getInstance();
		String año = calendario.get(Calendar.YEAR) + "";
		año = año.substring(2, 4);
		String mes = (calendario.get(Calendar.MONTH) + 1) + "";
		String dia = calendario.get(Calendar.DATE) + "";
		String hora = calendario.get(Calendar.HOUR_OF_DAY) + "";
		String minuto = calendario.get(Calendar.MINUTE) + "";
		String segundos = calendario.get(Calendar.SECOND) + "";
		String tiempoEje = System.currentTimeMillis() + "";
		tiempoEje = tiempoEje.substring(tiempoEje.length() - 4, tiempoEje.length());

		String cueNumero = año + mes + "-" + dia + hora + "-" + minuto + segundos + "-" + tiempoEje;
		String cuatroDigitos=idCliente+"";
		cuatroDigitos=cuatroDigitos.substring(cuatroDigitos.length() - 4, cuatroDigitos.length());
		String clave = cuatroDigitos+año+mes+dia+hora+minuto+segundos+tiempoEje;
		
		List<String> cuentaYclave = new ArrayList<String>();
		cuentaYclave.add(cueNumero);
		cuentaYclave.add(clave);
		return cuentaYclave;
	}

	
	//apertura de la cuenta La clave debe estar compuesta por los cuatro últimos dígitos de la cédula del cliente, más el
	//número de la cuenta.
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void activarCuenta(Cuentas cuenta) throws Exception {
		if (cuenta == null)
			throw new Exception("La Cuenta es Nula");

		cuenta.setCueActiva("S");

		if (cuenta.getCueClave().equalsIgnoreCase(CLAVE_INICIAL)) {

			String nuevaClave = "";

			String cedulaCliente = cuenta.getClientes().getCliId() + "";
			cedulaCliente = cedulaCliente.substring(cedulaCliente.length() - 4, cedulaCliente.length());

			nuevaClave = cedulaCliente + cuenta.getCueNumero();

			cuenta.setCueClave(nuevaClave);
		}

		updateCuentas(cuenta);

	}

	@TransactionAttribute
	public List<Cuentas> getCuentasDeUnCliente(long idCliente) throws Exception {
		// TODO Auto-generated method stub
		return cuentasDAO.getCuentasDeUnCliente(idCliente);
	}

	@TransactionAttribute
	public List<Consignaciones> getConsignaciones(String cueNumero) {
		// TODO Auto-generated method stub
		return cuentasDAO.getConsignaciones(cueNumero);
	}

	@TransactionAttribute
	public List<Retiros> consultarRetiros(String cueNumero) {
		// TODO Auto-generated method stub
		return cuentasDAO.getRetiros(cueNumero);
	}
}
