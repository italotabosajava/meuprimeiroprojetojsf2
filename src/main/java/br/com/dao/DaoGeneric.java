package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import br.com.jpaUtil.JPAUtil;


    @Named                          //o <E> identifica que vai receber qualquer tipo de classe
    public class DaoGeneric<E> {

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private JPAUtil jpaUtil;
    	
	public void salvar (E entidade) {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.persist(entidade);
		
		entityTransaction.commit();
		
	}
	// O merge vai salvar no banco e retornar os dados na tela
	public E merge (E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E retorno = entityManager.merge(entidade);
		
		entityTransaction.commit();
		//entityManager.close();
		
	    return retorno;
	}
	
	public void deletePorId (E entidade) {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Object id = jpaUtil.getPrimaryKey(entidade);
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " 	+ id).executeUpdate();
		
		entityTransaction.commit();
		
	
}
	//metodo para criacao de data teble
	public List<E> getListEntity(Class<E> entidade){
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
	
		entityTransaction.commit();
		return retorno;
	}

			public E consultar (Class<E> entidade, String codigo) {
				EntityTransaction entityTransaction = entityManager.getTransaction();
				entityTransaction.begin();
				
				E objeto = (E) entityManager.find(entidade, Long.parseLong(codigo));
			    entityTransaction.commit();
			    return objeto;
}       
		}

		

