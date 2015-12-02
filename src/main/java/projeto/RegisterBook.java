package projeto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import projeto.bd.LivroDAO;
import projeto.modelo.Usuario;

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
		return "mybooks";
	}
}