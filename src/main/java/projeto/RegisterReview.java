package projeto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import projeto.bd.AvaliacaoDAO;
import projeto.modelo.Avaliacao;
import projeto.modelo.Usuario;

@ManagedBean(name="registerreview")
@RequestScoped
public class RegisterReview {

	private int nota;
	private String comment;

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public String submit() {
		AvaliacaoDAO dao = new AvaliacaoDAO();
		Usuario user = Session.getUser();
		Avaliacao aval = new Avaliacao(0, getNota(), user.getEmail(), Session.getUsername(), getComment());
		dao.adicionaAvaliacao(aval);
		return "myrequests";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}