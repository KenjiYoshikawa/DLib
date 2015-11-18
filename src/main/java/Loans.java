import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import model.bd.EmprestimoDAO;
import model.bd.LivroDAO;
import model.modelo.Livro;

@RequestScoped
@ManagedBean
public class Loans {
	
	private List<Livro> books;
	private DataModel<Livro> booksModel;
	

    @PostConstruct
    public void init() {
    	LivroDAO dao = new LivroDAO();
		books = dao.buscaTodosLivros();
		setBooksModel(new ListDataModel<Livro>(books));
    }
	
	public String unloan()
	{
		//Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		//String param = parameterMap.get("parameter");
		//int id = Integer.parseInt(param);
		Livro livro = getBooksModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.pegaLivroEmprestado(Session.getUser(), livro);
		return "";
	}

	public DataModel<Livro> getBooksModel() {
		return booksModel;
	}

	public void setBooksModel(DataModel<Livro> booksModel) {
		this.booksModel = booksModel;
	}
}