package projeto;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import projeto.Session;
import projeto.bd.UsuarioDAO;
import projeto.modelo.Usuario;

@ManagedBean
@SessionScoped
public class Register implements Serializable {
	
    private static final long serialVersionUID = 1L;

	private String user;
	private String pass;
	private String confirmPass;

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


	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

	public String submit() {
		if (!this.pass.equals(this.confirmPass)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "As senhas digitadas diferem!", null));
			return "register";
		}
		
		if (this.pass.length() < 8) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "A senha precisa ter no minimo 8 caracteres", null));
			return "register";
		}
		
		UsuarioDAO userdao = new UsuarioDAO();
		Usuario user = new Usuario(this.user, this.pass);

		if (!userdao.cadastraUsuario(user)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario nao pode ser cadastrado, erro desconhecido.", null));
			return "register";
		};
		Session.setUser(user);
	    return "index";
	}
}