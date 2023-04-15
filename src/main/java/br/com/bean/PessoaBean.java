package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;


@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	
	public String salvar() {
		pessoa = daoGeneric.merge(pessoa);
		carregarpessoas();
		return"";	 //pra ficar na mesma pagina usa-se return"" ou null
	}
	public String novo() {
		pessoa = new Pessoa();
		return"";
	}
	
	public String remove() {
			daoGeneric.deletePorId(pessoa);
			pessoa = new Pessoa();
			carregarpessoas();
			return"";	
	}
    
	@PostConstruct
	public void carregarpessoas() {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	public String logar() {
		
		System.out.println(pessoa.getLogin() + " - " + pessoa.getSenha());
		
		return "index.jsf";
	}
	
	public void testegit() {
		
	}
}
