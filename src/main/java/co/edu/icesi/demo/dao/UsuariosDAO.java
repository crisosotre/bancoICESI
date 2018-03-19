package co.edu.icesi.demo.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import co.edu.icesi.banco.modelo.Usuarios;

@Stateless
public class UsuariosDAO implements IUsuariosDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Usuarios entity) {
	    entityManager.persist(entity);
	}

	@Override
	public void update(Usuarios entity) {
		entityManager.merge(entity);
	}

	@Override
	public void delete(Usuarios entity) {
		entityManager.remove(entity);
	}

	@Override
	public Usuarios findById(Long usuCedula) {
		return entityManager.find(Usuarios.class, usuCedula);
	}

	@Override
	public List<Usuarios> findAll() {
		String jpql = "SELECT usu FROM Usuarios usu";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public List<Usuarios> findByProperty(String propertyName, Object value) {

		String jpql = "SELECT usu FROM Usuarios usu WHERE usu."+propertyName+"='"+value+"'";
		return entityManager.createQuery(jpql).getResultList();
		
	}

	@Override
	public Usuarios getUsuario(String usuLogin) {
		// TODO Auto-generated method stub
		
		String jpql = "SELECT usu FROM Usuarios usu WHERE usu.usuLogin = :login";
		return (Usuarios) entityManager.createQuery(jpql).setParameter("login", usuLogin).getSingleResult();
	
		
	}

}
