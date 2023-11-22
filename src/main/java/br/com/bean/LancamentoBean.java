package br.com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.dao.DaoGeneric;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.entidades.Usuario;
import br.com.repository.IDaoLancamento;
import br.com.repository.IDaoLancamentoImpl;

@RequestScoped
@Named(value="lancamentoBean")
public class LancamentoBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	@Inject
	private Lancamento lancamento;
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	
	@Inject
	private IDaoLancamento daoLancamento; 
	
	public String salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		 
		 lancamento.setUsuario(pessoaUser);
		 lancamento = daoGeneric.merge(lancamento);
		 
		 carregarlancamentos();
		
		 FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Salvo com sucesso"));
		 
		 return""; 
		 }
	    @PostConstruct
		private void carregarlancamentos() {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
			lancamentos = daoLancamento.consultarLimit10(pessoaUser.getId());	
			
		}
           
		public String novo() {
			lancamento = new Lancamento();
		return "";	
		
		}
		public String remover() {
			
			daoGeneric.deletePorId(lancamento);
			lancamento = new Lancamento();
			carregarlancamentos();
			FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Excluido com sucesso"));
			return "";
		}
		
		public String editar() {
			if (lancamento != null) {
				return""; 
			}
			return"";
		}		
	
	
		
	public Lancamento getLancamento() {
		return lancamento;
	}
	
	
	
	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}
	public DaoGeneric<Lancamento> getDaoGeneric() {
		return daoGeneric;
	}
	public void setDaoGeneric(DaoGeneric<Lancamento> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	
}
