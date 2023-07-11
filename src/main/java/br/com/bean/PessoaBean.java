package br.com.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidades.Cidades;
import br.com.entidades.Estados;
import br.com.entidades.Pessoa;
import br.com.jpaUtil.JPAUtil;
import br.com.repository.IDaoPessoa;


@RequestScoped
@Named(value = "pessoaBean")
public class PessoaBean implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private Pessoa pessoa;
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	
	@Inject
	private IDaoPessoa iDaoPessoa;
	
	private List<SelectItem> estados;
	
	private List<SelectItem> cidades;
	
	@Inject
	private JPAUtil jpaUtil;
	
	private Part arquivofoto;
	
	public String salvar()throws IOException{
		/*Processar imagem*/		
		if(arquivofoto != null) {	
		
		byte[] imagemByte = getByte(arquivofoto.getInputStream());
		pessoa.setFotoIconBase64Original(imagemByte);/* salva imagem original*/
		
		/*transforma em buffimage*/
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
		
		/*pegar o tipo da imagem*/
		int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
		
		int largura = 200;
		int altura = 200;
		/*criar a miniatura*/
		BufferedImage resizedImage = new BufferedImage(altura, largura, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(bufferedImage,0 ,0 , largura,altura,null);
		g.dispose();
		
		/*Escrever novamente a imagem em tamanho menor*/
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String extensao = arquivofoto.getContentType().split("\\/")[1];/* imagem png*/
		ImageIO.write(resizedImage, extensao, baos);
		
		String miniImagem = "data:" + arquivofoto.getContentType()+";base64," +
		                   DatatypeConverter.printBase64Binary(baos.toByteArray());
		
		
 /*Processar imagem*/  
		pessoa.setFotoIconBase64(miniImagem);
		pessoa.setExtensao(extensao);
		}
		
		 daoGeneric.merge(pessoa);
		//carregarpessoas();
		mostrarMsg("Cadastrado com sucesso");
		novo();
		return "";	 //pra ficar na mesma pagina usa-se return"" ou null
	}
	
	private void mostrarMsg(String msg) {
	   FacesContext context = FacesContext.getCurrentInstance();
	   FacesMessage message = new FacesMessage(msg);
	   context.addMessage(null, message);
	   {		
		}
	}
	
	public String novo() {
		pessoa = new Pessoa();
		return"";
	}
	
	public String limpar() {
		pessoa = new Pessoa();
		return"";
	}
	
	public String remove() {
			daoGeneric.deletePorId(pessoa);
			pessoa = new Pessoa();
			carregarpessoas();
			mostrarMsg("Removido com sucesso!");
			
			return"";	
	}
    
	@PostConstruct
	public void carregarpessoas() {
			pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	public void pesquisaCep(AjaxBehaviorEvent Event) {
		
		try {
			URL url = new URL("https://viacep.com.br/ws/"+pessoa.getCep()+"/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder(); 
			
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setCep(gsonAux.getCep());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade());
			pessoa.setUf(gsonAux.getUf());
			pessoa.setUnidade(gsonAux.getUnidade());
			pessoa.setIbge(gsonAux.getIbge());
			pessoa.setGia(gsonAux.getGia());
				
			System.out.println(gsonAux);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			mostrarMsg("Erro ao consultar cep");
		}
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
	
	public String deslogar(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)
				context.getCurrentInstance().
		getExternalContext().getRequest();
		
		httpServletRequest.getSession().invalidate();
		
		return "index.jsf";
	
	}
	
	public String logar() {
		
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaUser != null) {//achou o usuario
			
			// adicionar o usuario na sessão usuarioLogado
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			
			//HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			//HttpSession session = req.getSession();
			//session.setAttribute("usuarioLogado ", pessoaUser);
			
		return"index2.xhtml";	
		
		}else {
		FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage("Usuario não encontrado"));
		
			
		}
		return "index.xhtml";
	}
	
	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		 Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get
				 ("usuarioLogado");
		 
		 return pessoaUser.getPerfilUser().contentEquals(acesso);
		 
		 
	}
	public List<SelectItem> getEstados() {
		List<SelectItem> estados = new ArrayList<SelectItem>();
		estados = iDaoPessoa.listaEstados();
		return estados;
	}

	@SuppressWarnings("unchecked")
	public void carregaCidades(AjaxBehaviorEvent event) {
		Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();
		
					if (estado != null){
						pessoa.setEstados(estado);
						
						List<Cidades> cidades = jpaUtil.getEntityManager()
								       .createQuery("from Cidades where estados.id = "
						               + estado.getId()).getResultList();
						
						List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();	
						
						for (Cidades cidade : cidades) {
							selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
						}
						    setCidades(selectItemsCidade);
					}}
	
   public String editar() {
	  if (pessoa.getCidades() != null) {
		  Estados estado = pessoa.getCidades().getEstados();
		  pessoa.setEstados(estado);
		  
		  @SuppressWarnings("unchecked")
		List<Cidades> cidades = jpaUtil.getEntityManager()
			       .createQuery("from Cidades where estados.id = "
	               + estado.getId()).getResultList();
	
	List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();	
	
	for (Cidades cidade : cidades) {
		selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
	}
	    setCidades(selectItemsCidade);
   }
	  return "";
   }
	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}
	
	public List<SelectItem> getCidades() {
		return cidades;
	}
	
	public Part getArquivofoto() {
		return arquivofoto;
	}
	
	public void setArquivofoto(Part arquivofoto) {
		this.arquivofoto = arquivofoto;
	}
	
	
	/* Método que converte inputstream em array bytes*/
	
	private byte[] getByte (InputStream is) throws IOException{
		
         int len;
         int size = 1024;
         byte[] buf = null;
         if (is instanceof ByteArrayInputStream) {
        	 size = is.available();
        	 buf = new byte[size];
        	 len = is.read(buf, 0, size);
         }else{
        	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        	buf = new byte [size];
        	while ((len = is.read(buf, 0, size)) != -1) {
        		bos.write(buf, 0, len);
        	}
        	buf = bos.toByteArray();
         }
		return buf;
		
		}
	
	public void download() throws IOException {
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String fileDownloadId = params.get("fileDownloadId");
		
		Pessoa pessoa = daoGeneric.consultar(Pessoa.class, fileDownloadId);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		response.addHeader("Content-Disposition", "attachment; filename=downloand." + pessoa.getExtensao());
		response.setContentType("application/octet-stream");
		response.setContentLength(pessoa.getFotoIconBase64Original().length);
		response.getOutputStream().write(pessoa.getFotoIconBase64Original());
		response.getOutputStream().flush();
		FacesContext.getCurrentInstance().responseComplete();
	}
	}
	
	


