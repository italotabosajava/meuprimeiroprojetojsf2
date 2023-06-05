package br.com.converter;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.persistence.Converter;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.jpaUtil.JPAUtil;

@FacesConverter(forClass = Cidades.class, value = "ciadadeConverter")
public class CidadesConverter implements javax.faces.convert.Converter, Serializable{

	@Override /*retorna o objeto inteiro*/
	public Object getAsObject(FacesContext context, UIComponent component,
			String codigoCidade) {
	
		EntityManager entityManager =JPAUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		Cidades cidade = (Cidades) entityManager.
				find(Cidades.class, Long.parseLong(codigoCidade));
		
		return cidade;
	}

	@Override /*retorna apenas o codigo em String*/
	public String getAsString(FacesContext context, UIComponent component,
			Object cidade) {
		
		if (cidade == null){
			return null;
		}
		
		if (cidade instanceof Cidades) {
		   return ((Cidades) cidade).getId().toString();
		}else{
	         return cidade.toString();
	}
	}
}
	