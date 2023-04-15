package br.com.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//cria tabela automatico no banco de dados
@Entity
public class Pessoa implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id //identificaa que é uma primary key
	
	@GeneratedValue(strategy = GenerationType.AUTO) //gera primary key automatico do banco de dados
	private Long id;
	
	private String nome;
	
	private String sobrenome;
	
	private Integer idade;
	
	private String sexo;
 
	private String[] frameworks; 
	
	private Boolean ativo;
	
	private String senha;
	
	private String login;
	
	private String perfilUser;
	
	public String getPerfilUser() {
		return perfilUser;
	}
	public void setPerfilUser(String perfilUser) {
		this.perfilUser = perfilUser;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Boolean getAtivo() { //sempre que estiver usando hibernate e jpa usar sempre os objetos nunca os tipos primitivos
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public String[] getFrameworks() {
		return frameworks;
	}
	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getSexo() {
		return sexo;
	}

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	//sempre criar um construtor vazio é um padrao java
	public Pessoa() {
	
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
