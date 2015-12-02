package projeto;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@RequestScoped
@ManagedBean(name="topbar")
public class TopBar {
	private String username;

	@PostConstruct
	public void init() {
	}

	public String view() {
		username = Session.getUser().getEmail();
		Session.setUsername(username);
		return "user_reviews";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}