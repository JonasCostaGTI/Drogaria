package br.com.jonas.drogaria.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.jonas.drogaria.dao.CidadeDAO;
import br.com.jonas.drogaria.dao.EstadoDAO;
import br.com.jonas.drogaria.dao.PessoaDAO;
import br.com.jonas.drogaria.domain.Cidade;
import br.com.jonas.drogaria.domain.Estado;
import br.com.jonas.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {

	private Estado estado;
	private Pessoa pessoa;
	private List<Pessoa> pessoas;
	private List<Cidade> cidades;
	private List<Estado> estados;
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public void novo() {
		

		try {
			pessoa = new Pessoa();
			estado = new Estado();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
			
			cidades = new ArrayList<Cidade>();
			
		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar cidades");
			e.printStackTrace();
		}

	}

	// listar
	@PostConstruct
	public void listar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar");
			e.printStackTrace();
		}
	}

	// excluir
	public void excluir(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);

			pessoas = pessoaDAO.listar("nome");

			Messages.addGlobalInfo("Pessoa removida com sucesso");
		} catch (RuntimeException e) {
			Messages.addFlashGlobalError("Nao foi possivel remover a Pessoa");
			e.printStackTrace();
		}
	}

	// salvar
	public void salvar() {

		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);

			novo();

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar();

			pessoas = pessoaDAO.listar("nome");
			Messages.addGlobalInfo("Salvo com sucesso");
		} catch (RuntimeException e) {
			Messages.addGlobalError("Nao foi possivel salvar");
			e.printStackTrace();
		}
	}

	// editar
	public void editar(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			estado = pessoa.getCidada().getEstado();
			
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao tentar listar os Cidades");
			e.printStackTrace();
		}

	}
	
	//popular
	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			}else{
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException e) {
			Messages.addGlobalError("O correu um erro ao tentar filtrar as cidades");
			e.printStackTrace();
		}
	}

}








