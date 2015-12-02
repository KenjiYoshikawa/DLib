package projeto;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import projeto.bd.EmprestimoDAO;
import projeto.bd.LivroDAO;
import projeto.modelo.Livro;

@ViewScoped
@ManagedBean
public class Books {
	
	private List<Livro> books;
	private DataModel<Livro> booksModel;
	private String username;
	

    @PostConstruct
    public void init() {
    	LivroDAO dao = new LivroDAO();
		books = dao.buscaTodosLivros(Session.getUser().getEmail());
		setBooksModel(new ListDataModel<Livro>(books));
    }
	
	public String loan()
	{
		Livro livro = getBooksModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.pegaLivroEmprestado(Session.getUser(), livro);
		return "books?faces-redirect=true";
	}

	public DataModel<Livro> getBooksModel() {
		return booksModel;
	}

	public void setBooksModel(DataModel<Livro> booksModel) {
		this.booksModel = booksModel;
	}
	
	public String viewUser()
	{
		Livro livro = getBooksModel().getRowData();
		username = livro.getEmailDono();
		Session.setUsername(username);
		return "user_reviews";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}