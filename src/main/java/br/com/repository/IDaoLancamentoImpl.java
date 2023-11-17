package br.com.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento {

	@Inject
	private EntityManager entityManager;
	
	@Inject
	public IDaoLancamentoImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Lancamento> consultarLimit10(Long codUser) {
	   List<Lancamento> lista = null;
	   
	   EntityTransaction transaction = entityManager.getTransaction();
	   transaction.begin();
	   
	   lista = entityManager.createQuery(" from Lancamento where usuario.id = " + codUser +
			   "order by id desc ").setMaxResults(10).getResultList();
	   
	   transaction.commit();
	   
	
		return lista;
	}
	
	@Override
	public List<Lancamento> consultar(Long codUser) {
	   List<Lancamento> lista = null;
	   
	   EntityTransaction transaction = entityManager.getTransaction();
	   transaction.begin();
	   
	   lista = entityManager.createQuery(" from Lancamento where usuario.id = " + codUser).getResultList();
	   
	   transaction.commit();
	   
		return lista;
	}
	
	@Override
	public List<Lancamento> relatorioLancamento(String numNota, String dataIni, String dataFim){
		 
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select 1 from Lancamento 1 ");
		
		if (dataIni == null && dataFim == null && numNota != null && !numNota.isEmpty()) {
			sql.append(" where 1.numeroNotaFiscal = '").append(numNota.trim()).append("'");
			
		}else if (numNota == null || (numNota != null && numNota.isEmpty())
		   && dataIni != null && dataFim == null) {
			
			 String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			 sql.append(" where 1.dataIni <= '").append(dataIniString).append("'");
			  
	   }else if (numNota == null || (numNota != null && numNota.isEmpty())
				&& dataIni != null && dataFim != null) {
			
		     String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
		     String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		    
		     sql.append(" where 1.dataIni >= '").append(dataIniString).append("' ");
		     sql.append(" and 1.dataFin <= '").append(datafimString).append("' ");
	   }
	   else if (numNota != null && !numNota.isEmpty()
			   && dataIni != null && dataFim != null) {
		   
		     
		   String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
		   String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		    
		   sql.append(" where 1.dataIni >= '").append(dataIniString).append("' ");
		   sql.append(" and 1.dataFin <= '").append(datafimString).append("' ");
		   sql.append(" and 1.numeroNotaFiscal = '").append(numNota.trim()).append("'");   
	   }
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		     
		return lancamentos;
		}
	}
	
