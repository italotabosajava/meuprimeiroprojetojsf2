package br.com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.dao.DaoGeneric;
import br.com.entidades.Usuario;

@ViewScoped
@ManagedBean (name = "usuarioBean")
public class UsuarioBean {

	private Usuario usuario = new Usuario();
	private DaoGeneric<Usuario>  daoGeneric = new DaoGeneric<Usuario>();
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	
	public String salvar() {
		usuario = daoGeneric.merge(usuario);
		carregarUsuarios();
		//novo();
		return"";	//pra ficar na mesma pagina usa-se return"" ou null
	}
	
	public String novo() {
		usuario = new Usuario();
		return"";
	}
	public boolean resposta ;
	
	public String remove() {
			daoGeneric.deletePorId(usuario);
			usuario = new Usuario();
			carregarUsuarios();
			return"";
		}
	
	public String editar() {
		if (usuario == null) {
			return""; 
		}
		return"";
	}
 	
	@PostConstruct
	public void carregarUsuarios(){
		usuarios = daoGeneric.getListEntity(Usuario.class);	
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public DaoGeneric<Usuario> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Usuario> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}	
	



