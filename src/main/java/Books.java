import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.bd.LivroDAO;
import model.modelo.Livro;

@RequestScoped
@ManagedBean
public class Books {

	public List<Livro> getAllBooks() {
		LivroDAO dao = new LivroDAO();
		return dao.buscaTodosLivros();
	}
}