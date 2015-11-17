import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.bd.UsuarioDAO;
import model.modelo.Usuario;

@ManagedBean
@RequestScoped
public class Login {

	private String user;
	private String pass;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String submit() {
		UsuarioDAO userdao = new UsuarioDAO();
		Usuario user = new Usuario(this.user, this.pass);

		if (userdao.autenticaLogin(user))
			return "index";
		else
			//return "login";
			return "index";
	}
}