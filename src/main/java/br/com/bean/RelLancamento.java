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
import br.com.repository.IDaoLancamento;

@RequestScoped
@Named(value = "relLancamento")
public class RelLancamento implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String dataIni;
	private String dataFim;
	
	private String numNota;

	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	@Inject
	private IDaoLancamento daoLancamento; 
	
	@Inject
	private DaoGeneric<Lancamento> daoGeneric;
	

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

	public String getNumNota() {
		return numNota;
	}

	public void setNumNota(String numNota) {
		this.numNota = numNota;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
	public void buscarLancamento() {
		if (dataIni == null && dataFim == null && numNota == null) {
		lancamentos = daoGeneric.getListEntity(Lancamento.class);
		}else{
			lancamentos = daoLancamento.relatorioLancamento(numNota, dataIni, dataFim);
		}
	}

}
