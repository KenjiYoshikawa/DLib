import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import model.bd.UsuarioDAO;
import model.modelo.Usuario;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
 
    private static final long serialVersionUID = 1L;

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

	public String login() {
		UsuarioDAO userdao = new UsuarioDAO();
		Usuario user = new Usuario(this.user, this.pass);

		if (userdao.autenticaLogin(user)) {
			Session.setUser(user);
			return "index";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Senha ou usuario incorretos", null));
			return "login";
			//return "index";
		}
	}

	public String logout() {
		Session.setUser(null);
		Session.getSession().invalidate();
		return "login";
	}
}