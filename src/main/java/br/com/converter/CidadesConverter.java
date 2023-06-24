package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import br.com.entidades.Cidades;
import javassist.SerialVersionUID;

@SuppressWarnings("serial")
@FacesConverter(forClass = Cidades.class, value = "ciadadeConverter")
public class CidadesConverter implements javax.faces.convert.Converter, Serializable{
	
	private static final long SerialVersionUID = 7942337638899772351L;

	@Override /*retorna o objeto inteiro*/
	public Object getAsObject(FacesContext context, UIComponent component,
			String codigoCidade) {
	
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		
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
	