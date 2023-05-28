package br.com.converter;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.persistence.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.jpaUtil.JPAUtil;

@FacesConverter(forClass = Estados.class)
public class EstadoConverter implements javax.faces.convert.Converter, Serializable{

	@Override /*retorna o objeto inteiro*/
	public Object getAsObject(FacesContext context, UIComponent component,
			String codigoEstado) {
	
		EntityManager entityManager =JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Estados estados = (Estados) entityManager.
				find(Estados.class, Long.parseLong(codigoEstado));
		
		return estados;
	}

	@Override /*retorna apenas o codigo em String*/
	public String getAsString(FacesContext context, UIComponent component,
			Object estado) {
		
		return ((Estados) estado).getId().toString();
	}
	
}
	