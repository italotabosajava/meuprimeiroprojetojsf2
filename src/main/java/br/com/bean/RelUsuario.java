package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@RequestScoped
@Named(value = "relUsuario")
public class RelUsuario implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
		
	private String dataIni;
	private String dataFim;
	
	private String nome;
	
	private List<Pessoa> pessoas = new ArrayList<Pessoa>(); 
	
	
	@Inject
	private IDaoPessoa iDaoPessoa;
	
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	public String getDataIni() {
		return dataIni;
	}


	public void setDataIni(String dataIni) {
		this.dataIni = dataIni;
	}


	public String getDataFim() {
		return dataFim;
	}


	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}


	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	
public void relPessoa() {
		
		if (dataIni == null && dataFim == null && nome == null) {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
			}else{
				pessoas = iDaoPessoa.relatorioPessoa(nome, dataIni, dataFim);
			}
}
		
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}


	public IDaoPessoa getiDaoPessoa() {
		return iDaoPessoa;
	}


	public void setiDaoPessoa(IDaoPessoa iDaoPessoa) {
		this.iDaoPessoa = iDaoPessoa;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	}


