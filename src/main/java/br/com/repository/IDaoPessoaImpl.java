package br.com.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Estados;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa {
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
	try {
	pessoa = (Pessoa) entityManager
		.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'")
		.getSingleResult();
	
   } catch (Exception e) {/*tratamento se nao encontrar usuario com login e senha*/
	}	
		
		entityTransaction.commit();
		
		return pessoa;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SelectItem> listaEstados() {
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		List<Estados> estados = new ArrayList<Estados>();
		
		List<SelectItem> selectItems = new ArrayList<SelectItem>();
				
		estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		return selectItems;
	}

	@Override
	public List<Pessoa> relatorioPessoa(String nome, String dataIni, String dataFim) {
		
List<Pessoa> lancamentos = new ArrayList<Pessoa>();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select 1 from pessoa 1 ");
		
		if (dataIni == null && dataFim == null && nome != null && !nome.isEmpty()) {
			sql.append(" where 1.nome like  '%").append(nome.trim().toUpperCase()).append("%'");
			
		}else if (nome == null || (nome != null && nome.isEmpty())
		   && dataIni != null && dataFim == null) {
			
			 String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			 sql.append(" where 1.dataNascimento >= '").append(dataIniString).append("'");
			  
	    }
		else if (nome == null || (nome != null && nome.isEmpty())
				&& dataIni == null && dataFim != null) {
			
		     String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		     sql.append(" where 1.dataNascimento <= '").append(dataIniString).append("'");
		     
		}else if (nome == null || (nome != null && nome.isEmpty())
				&& dataIni != null && dataFim != null) {
		    
		     sql.append(" where 1.dataNascimento >= '").append(dataIni).append("' ");
		     sql.append(" and 1.dataNascimento <= '").append(dataFim).append("' ");
	   }
	   else if (nome != null && !nome.isEmpty()
			   && dataIni != null && dataFim != null) {
		   
		     
		   String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
		   String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
		    
		   sql.append(" where 1.dataNascimento >= '").append(dataIniString).append("' ");
		   sql.append(" and 1.dataNascimento <= '").append(datafimString).append("' ");
		   sql.append(" and upper(1.nome) like '%").append(nome.trim().toUpperCase()).append("%'");   
	   }
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		     

		return null;
	}

}
