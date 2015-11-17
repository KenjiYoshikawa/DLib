
/* Adaptado de: http://www.journaldev.com/7252/jsf-authentication-login-logout-database-example */

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.modelo.Usuario;

public class Session {

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static Usuario getUser() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null)
			return (Usuario) session.getAttribute("usuario");
		else
			return null;
	}

	public static void setUser(Usuario user) {
		HttpSession session = getSession();
		session.setAttribute("usuario", user);
	}
}
