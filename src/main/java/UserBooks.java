import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import model.bd.LivroDAO;
import model.bd.LivroDAO.BookSearchLimit;
import model.modelo.Livro;

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

	public DataModel<Livro> getBooksModel() {
		return booksModel;
	}

	public void setBooksModel(DataModel<Livro> booksModel) {
		this.booksModel = booksModel;
	}
}