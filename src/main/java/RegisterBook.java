import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.bd.LivroDAO;
import model.modelo.Usuario;

@ManagedBean(name="registerbook")
@RequestScoped
public class RegisterBook {
 
	private String isbn;
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String submit() {
		LivroDAO livrodao = new LivroDAO();
		Usuario user = Session.getUser();

		livrodao.cadastraLivroISBN(user, getIsbn());
		return "index";
	}
}