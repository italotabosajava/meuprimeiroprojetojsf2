package br.com.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Cidades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	private String nome;
	
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Estados estados;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public Estados getEstados() {
	return estados;
}

public void setEstados(Estados estados) {
	this.estados = estados;
}

@Override
public String toString() {
	return "Cidades [id=" + id + "]";
}
}

	
	
	