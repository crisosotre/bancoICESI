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

import co.edu.icesi.banco.modelo.Cuentas;
import co.edu.icesi.banco.modelo.Retiros;
import co.edu.icesi.banco.modelo.RetirosId;
import co.edu.icesi.banco.utilities.Utilities;
import co.edu.icesi.demo.dao.IClientesDAO;
import co.edu.icesi.demo.dao.IRetirosDAO;


@Stateless
public class RetirosLogic implements IRetirosLogic {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(Retiros.class);
	
	@EJB
	private IRetirosDAO retirosDAO;
	@EJB
	private IClientesDAO cuentasDAO;
	@EJB
	private ICuentasLogic cuentalogic;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void savedRetiros(Retiros entity) throws Exception {
		
		try {
			
			log.info(("inicia "));
			
			if(entity.getId()==null ){
				throw new Exception("El ID del retiro es obligatorio");
			}	
			if(entity.getCuentas()==null || entity.getCuentas().equals("")){
				throw new Exception("No hay una cuenta asociada");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getCuentas().getCueNumero(), 30, 0)){
				throw new Exception("El tamaño del número de  cuenta no debe ser mayor a 30 digitos");
			}
					
			if(entity.getUsuarios()==null || entity.getUsuarios().equals("")){
				throw new Exception(" Debe existir usuario para realizar el retiro");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getUsuarios(),10, 0)){
				throw new Exception("El número de la cedula del usuario no debe superar los 10 digitos");
			}
			
			if(entity.getRetValor()==null||entity.getRetValor().compareTo(new BigDecimal(0))==0){
				throw new Exception("Debe haber un valor a retirar mayor a cero");
			}
			
			if(entity.getRetValor().intValue()<0) {
				throw new Exception ("El valor a retirar debe ser una cantidad positiva ");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale("" + entity.getRetValor(), 10, 2)){
				throw new Exception("El total de digitos en el valor a retirar debe ser como max 10 en la"
						+ "parte entera y 2 en la decimal");
			}				
			
			if(entity.getRetDescripcion().equals("") || entity.getRetDescripcion().equals("-1")){
				throw new Exception(" La descripcion del retiro es necesaria");
			}
			
			if(Utilities.checkWordAndCheckWithlength(entity.getRetDescripcion(), 50)==false){
				throw new Exception("La descripción  no debe exceder los 50 caracteres");
			}
			
			Cuentas cuentaRetiro= cuentalogic.getCuentasById(entity.getCuentas().getCueNumero());
			if(cuentaRetiro==null) {
				throw new Exception("La cuenta con el número " + entity.getCuentas().getCueNumero() + " no existe.");
			}
			
			if(!cuentaRetiro.getCueActiva().trim().equals("S")) {
				throw new Exception("La cuenta no está activa.");
			}
			
			if(cuentaRetiro.getCueSaldo().intValue()==0) {
				throw new Exception("Saldo insuficiente para realizar la transacción");
			}
			
			if(cuentaRetiro.getCueSaldo().intValue()<entity.getRetValor().intValue()) {
				throw new Exception("El valor a retirar excede el saldo actual de la cuenta");
			}
			
			Retiros retiroN=new Retiros();
			RetirosId retirosId = new RetirosId();
			
			retirosId.setRetCodigo(generarCodigoRetiro());
			retirosId.setCueNumero(entity.getCuentas().getCueNumero());
			
			retiroN.setId(retirosId);
			retiroN.setRetValor(entity.getRetValor());
			retiroN.setRetFecha(new Date());
			retiroN.setRetDescripcion(entity.getRetDescripcion());
			retiroN.setUsuarios(entity.getUsuarios());//puede fallar
			retiroN.setCuentas(cuentaRetiro);
			retirosDAO.save(retiroN);
			
			cuentaRetiro.setCueSaldo(cuentaRetiro.getCueSaldo().subtract(entity.getRetValor()));
			cuentalogic.updateCuentas(cuentaRetiro);
			
			log.info("guardo satisfactoriamente");
			
		} catch (Exception e) {
			log.error("SaveRetiros fallo", e);
			throw e;
			
		}

	}
	
	public void retiroCajero(String cueNumero, BigDecimal valor, long usuCedula) throws Exception{
		
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateRetiros(Retiros entity) throws Exception {
		
		try {
			log.info("Inicia updateRetiros");
			
			if(entity.getId()==null){
				throw new Exception("el Id del retiro es obligatorio");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getId(), 10, 0)== false){
				throw new Exception("El tamaÃ±o del id de retiro no debe ser mayor a 10 digitos");
			}
			
			Long codigo = entity.getId().getRetCodigo();
			if(getRetiroById(codigo)==null){
				
				throw new Exception("debe existir un retiro con Id "+ codigo);
				
			}
			
			if(entity.getRetFecha()==null){
				throw new Exception("es necesaria la fecha del retiro");
			}
			
			if(cuentasDAO.toString().isEmpty()){
				throw new Exception("es necesario ingresar una cuenta para hacer un retiro");
			}
			
			if(Utilities.checkNumberAndCheckWithPrecisionAndScale(""+ entity.getCuentas(), 30,0)==false){
				throw new Exception("el tamaÃ±o del numero de la cuenta no debe exceder a los 30 caracteres");
			}
			
			retirosDAO.update(entity);
			log.info("actualizo satisfactoriamente");
		} catch (Exception e) {
			log.error("updateRetiros fallo", e);
			throw e;
			
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteRetiros(Long codigo) throws Exception {
		try {
			
			log.info("Inicia deleteRetiros");
			
			if(codigo==0 || codigo==null){
				throw new Exception(" el codigo de retiros es obligatorio");
			}
			
			Retiros entity=getRetiroById(codigo);
			
			
			if(entity==null){
				throw new Exception("no existe un retiro con el codigo"+codigo);
			}
			
			retirosDAO.delete(entity);

			log.info("el retiro con codigo:"+ codigo+" se elimino sartisfactoriamente");
		} catch (Exception e) {
			
			log.error("deleteRetiros fallo", e);
			throw e;
		}
	}

	@TransactionAttribute
	public List<Retiros> getRetiros() throws Exception {
		
		return retirosDAO.findAll();
	}


	@TransactionAttribute
	public Retiros getRetiroById(Long codigo) throws Exception {
	
		Retiros retiros=null;
		
		try {
			
			if(codigo==null || codigo==0){
				throw new Exception("el codigo del retiro es obligatorio");
				
			}
			
			retiros=retirosDAO.findById(codigo);
			
		} catch (Exception e) {
			log.error("getRetiroById fallo", e);
			throw e;
		}
		return retiros;
	}

	
	private long generarCodigoRetiro() {
		String nano = "" + System.nanoTime();
		nano = nano.substring(nano.length() - 7, nano.length());

		return Long.parseLong(nano);
	}
	

}
