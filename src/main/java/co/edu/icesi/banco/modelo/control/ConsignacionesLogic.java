package co.edu.icesi.banco.modelo.control;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import co.edu.icesi.banco.modelo.Clientes;
import co.edu.icesi.banco.modelo.Consignaciones;
import co.edu.icesi.banco.modelo.ConsignacionesId;
import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IConsignacionesDAO;



@Stateless
public class ConsignacionesLogic implements IConsignacionesLogic {

	private static Logger log = LoggerFactory.getLogger(ConsignacionesLogic.class);

	@EJB
	private IConsignacionesDAO consignacionesDAO;
	
	@EJB
	private IClientesLogic clientesLogic;
	
	@EJB
	private ICuentasLogic cuentasLogic;
	
	@EJB
	private IUsuariosLogic usuarioLogic;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveConsignaciones(Consignaciones entity) throws Exception {
		try {
			log.info("Inicia saveConsignaciones");
			
			if(entity.getId()==null ){
				throw new Exception("El ID de la ó es obligatoria");
			}	
			if(entity.getCuentas()==null || entity.getCuentas().equals("")){
				throw new Exception("No hay una cuenta asociada");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getCuentas().getCueNumero(), 30, 0)){
				throw new Exception("El tamaño del número de  cuenta no debe ser mayor a 30 digitos");
			}
					
			if(entity.getUsuarios()==null || entity.getUsuarios().equals("")){
				throw new Exception(" Debe existir usuario para realizar la consignación");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getUsuarios(),10, 0)){
				throw new Exception("El número de la cedula del usuario no debe superar los 10 digitos");
			}
			
			if(entity.getConValor()==null||entity.getConValor().intValue()<0){
				throw new Exception("Debe haber un valor a consignar positivo");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale("" + entity.getConValor(), 10, 2)){
				throw new Exception("El total de digitos en el valor a retirar debe ser como max 10 en la"
						+ "parte entera y 2 en la decimal");
			}				
			
			if(entity.getConDescripcion().equals("") || entity.getConDescripcion().equals("-1")){
				throw new Exception(" La descripcion de la consignación es necesaria");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getConDescripcion(), 50)==false){
				throw new Exception("La descripción  no debe exceder los 50 caracteres");
			}
			
			Cuentas cuentaConsignacion= cuentasLogic.getCuentasById(entity.getCuentas().getCueNumero());
			if(cuentaConsignacion==null) {
				throw new Exception("La cuenta con el número " + entity.getCuentas().getCueNumero() + " no existe.");
			}
			
			BigDecimal cienMil = new BigDecimal("100000");
			if(cuentaConsignacion.getCueActiva().equals("N ") && entity.getConValor().intValue()>=100000) {
				if(entity.getConDescripcion().equals("APERTURA DE CUENTA") && cuentaConsignacion.getCueSaldo().intValue()==0) {
				int saldoNuevo = cuentaConsignacion.getCueSaldo().intValue();
				saldoNuevo=saldoNuevo+entity.getConValor().intValue();
				BigDecimal nuevo = new BigDecimal(saldoNuevo+"");
				cuentaConsignacion.setCueSaldo(nuevo);
				cuentaConsignacion.setCueActiva("S");
				}else {
					throw new Exception("La descripción para esta transacción debe ser APERTURA DE CUENTA");
				}
			}else if(cuentaConsignacion.getCueActiva().equals("N ") && entity.getConValor().intValue()<100000){
				if(entity.getConDescripcion().equals("APERTURA DE CUENTA") && cuentaConsignacion.getCueSaldo().intValue()==0) {
					throw new Exception("No se puede hacer la apertura de cuenta porque el valor a consignar es menor a $100000");
				}				
			}
			else if(cuentaConsignacion.getCueActiva().equals("N ") && entity.getConValor().intValue()>=100000){
				if(entity.getConDescripcion().equals("CONSIGNACION") && cuentaConsignacion.getCueSaldo().intValue()==0){
					throw new Exception("Para Activar la cuenta , la descripción es APERTURA DE CUENTA");
				}
				{
			}
				if(entity.getConDescripcion().equals("CONSIGNACION")) {
				int saldoNuevo = cuentaConsignacion.getCueSaldo().intValue();
				saldoNuevo=saldoNuevo+entity.getConValor().intValue();
				BigDecimal nuevo = new BigDecimal(saldoNuevo+"");
				cuentaConsignacion.setCueSaldo(nuevo);
				}else {
					throw new Exception("La descripción para esta transacción debe ser CONSIGNACION");
				}
			}
			
			Consignaciones cons=new Consignaciones();
			ConsignacionesId consId = new ConsignacionesId();
			
			consId.setConCodigo(generarCodigoConsignacion());
			consId.setCueNumero(entity.getCuentas().getCueNumero());
			
			cons.setId(consId);
			cons.setConValor(entity.getConValor());
			cons.setConFecha(new Date());
			cons.setConDescripcion(entity.getConDescripcion());
			cons.setUsuarios(entity.getUsuarios());//puede fallar
			cons.setCuentas(cuentaConsignacion);
			
			cuentasLogic.updateCuentas(cuentaConsignacion);
			consignacionesDAO.save(cons);
			
			
			log.info("Guardó satisfactoriamente");

		} catch (Exception e) {
			log.info("saveConsignaciones falló", e.getMessage());
			throw e;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateConsignaciones(Consignaciones entity) throws Exception {
		try {
			log.info("Inicia updateConsignaciones");
			if (entity.getId() != null) {
				throw new Exception("El codigo del cliente es obligatorio");
			}
			if ((entity.getId().getConCodigo() + "").length() > 10) {
				throw new Exception("El tamaño del código de la consignación no debe ser mayor a 10 de lenght");
			}

			if (entity.getId().getCueNumero().length() > 30) {
				throw new Exception(
						"El tamaño de la cuenta número de la consignación no debe ser mayor a 30 de lenght");
			}

			if (entity.getConValor().toString().length() > 10)
				throw new Exception("La cantidad de caracteres del valor no debe ser mayor a 10");

			if (entity.getConValor().precision() > 2)
				throw new Exception("La precisión del valor no debe ser mayor a 2");

			if (entity.getConValor() == null)
				throw new Exception("Debe haber un valor");

			if (entity.getConFecha() == null)
				throw new Exception("Debe tener fecha");

			if (entity.getConDescripcion() == null)
				throw new Exception("Debe tener descripción");

			if (entity.getConDescripcion().length() > 50)
				throw new Exception("La descripción debe ser menor a 51 caracteres");

			Long codigo = entity.getId().getConCodigo();
			String cueNumero = entity.getId().getCueNumero();
			if (consignacionesDAO.findById(codigo, cueNumero) == null)
				throw new Exception(
						"Debe existir una consignación con el código " + codigo + " y cuenta de número " + cueNumero);

			consignacionesDAO.save(entity);
			log.info("Actualizó satisfactoriamente");

		} catch (Exception e) {
			log.info("updateConsignaciones falló", e.getMessage());
			throw e;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteConsignaciones(Long codigo, String numCuenta) throws Exception {
		try {
			log.info("Inicia deleteConsignaciones");

			if (codigo == 0 || codigo == null) {
				throw new Exception("El código de la consignación debe existir");
			}

			if ((codigo + "").length() > 10) {
				throw new Exception("El tamaño del código de la consignación no debe ser mayor a 10 de lenght");
			}

			if (numCuenta == null || numCuenta.equals("")) {
				throw new Exception("La cuenta número de la consignación debe existir");
			}

			if (numCuenta.length() > 30) {
				throw new Exception(
						"El tamaño de la cuenta número de la consignación no debe ser mayor a 30 de lenght");
			}

			Consignaciones consignaciones = consignacionesDAO.findById(codigo, numCuenta);
			consignacionesDAO.delete(consignaciones);
			log.info("Eliminó satisfactoriamente");

		} catch (Exception e) {
			log.error("deleteConsignaciones falló " + e.getMessage());
			throw e;
		}
	}

	@Override
	@TransactionAttribute
	public List<Consignaciones> getConsignaciones() throws Exception {
		log.info("inicia getconsignaciones");
		return consignacionesDAO.findAll();
	}

	@Override
	@TransactionAttribute
	public Consignaciones getConsignaciones(Long codigo, String numCuenta) throws Exception {
		try {
			log.info("Inicia findByIdConsignaciones");

			if (codigo==null||codigo==0) {
				throw new Exception("El código debe existir");
			}
			
			if(numCuenta.equals("")||numCuenta==null)
				throw new Exception("El número de cuenta debe existir");

		return consignacionesDAO.findById(codigo, numCuenta);
		}catch (Exception e) {
			log.error("error en el findByIdConsignaciones no existe una consignación con códiog "+codigo+" y num de cuenta "+numCuenta+" "+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void consignacionDeCienMil(Clientes cliente, List<Cuentas> cuentas) throws Exception {
		try {
			log.info("Inicia consignacionDeCienMil");

			if (clientesLogic.getClientesById(cliente.getCliId()) == null)
				clientesLogic.saveClientes(cliente);
			else
				clientesLogic.updateClientes(cliente);

			for (int i = 0; i < cuentas.size(); i++) {
				Cuentas cu = cuentas.get(i);
				

				Consignaciones consignaciones = new Consignaciones();
				consignaciones.setId(new ConsignacionesId(cliente.getCliId(), cu.getCueNumero()));
				consignaciones.setConValor(new BigDecimal(100000));
				cu.setCueSaldo(cu.getCueSaldo().add(new BigDecimal(100000)));
				consignaciones.setConDescripcion("Transaccion apertura realizada");
				consignaciones.setConFecha(new Date());
				consignaciones.setCuentas(cu);
				
				if (cuentasLogic.getCuentasById(cu.getCueNumero()) == null)
					cuentasLogic.saveCuentas(cu);
				else
					cuentasLogic.updateCuentas(cu);
				
				saveConsignaciones(consignaciones);
			}
			log.info("exito al consignar");

		} catch (Exception e) {
			log.error(" consignaciónDeCienMil falló " + e.getMessage());
			throw e;
		}
	}

	
	private long generarCodigoConsignacion() {
		String nano = "" + System.nanoTime();
		nano = nano.substring(nano.length() - 7, nano.length());

		return Long.parseLong(nano);
	}

	@Override
	public void ConsignacionCajero(String cueNumero, BigDecimal valor, long usuCedula) throws Exception {
		// TODO Auto-generated method stub
		
	}


}
