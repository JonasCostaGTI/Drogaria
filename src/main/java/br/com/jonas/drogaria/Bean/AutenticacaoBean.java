package br.com.jonas.drogaria.Bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.jonas.drogaria.dao.UsuarioDAO;
import br.com.jonas.drogaria.domain.Pessoa;
import br.com.jonas.drogaria.domain.Usuario;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	
	private Usuario usuario;
	private Usuario usuarioLogado;
	
	

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	@PostConstruct
	public void iniciar(){
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}
	
	public void autenticar(){
		try {
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
		    usuarioLogado = usuarioDAO.autenticar(usuario.getSenha(), usuario.getPessoa().getCpf());
		
		    
		    if (usuarioLogado == null) {
				Messages.addGlobalError("Usuario ou Senha Invalidos");
				return;
			}
		    
			Faces.redirect("./pages/principal.xhtml");
		} catch (IOException e) {
			Messages.addGlobalError("Ocorreu algum erro ao tentar redirecionar");
			e.printStackTrace();
		}
	}

}
