package projeto;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import projeto.bd.LivroDAO;
import projeto.bd.LivroDAO.BookSearchLimit;
import projeto.modelo.Livro;

@RequestScoped
@ManagedBean
public class UserBooks {
	
	private List<Livro> books;
	private DataModel<Livro> booksModel;
	

    @PostConstruct
    public void init() {
    	LivroDAO dao = new LivroDAO();
		books = dao.buscaLivrosDono(Session.getUser(), BookSearchLimit.todos);
		setBooksModel(new ListDataModel<Livro>(books));
    }
    
    public String remove()
	{
		Livro livro = getBooksModel().getRowData();
		LivroDAO dao = new LivroDAO();
		dao.removeLivro(livro);
		return "mybooks?faces-redirect=true";
	}

	public DataModel<Livro> getBooksModel() {
		return booksModel;
	}

	public void setBooksModel(DataModel<Livro> booksModel) {
		this.booksModel = booksModel;
	}
}