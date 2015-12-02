package projeto;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import projeto.bd.AvaliacaoDAO;
import projeto.modelo.Avaliacao;

@RequestScoped
@ManagedBean
public class UserReviews {
	
	private List<Avaliacao> reviews;
	private DataModel<Avaliacao> reviewsModel;
	private String username;

	@PostConstruct
	public void init() {
		AvaliacaoDAO dao = new AvaliacaoDAO();
		reviews = dao.buscaAvaliacoes(Session.getUsername());
		setReviewsModel(new ListDataModel<Avaliacao>(reviews));
	}

	public DataModel<Avaliacao> getReviewsModel() {
		return reviewsModel;
	}

	public void setReviewsModel(DataModel<Avaliacao> reviewsModel) {
		this.reviewsModel = reviewsModel;
	}

	public String getUsername() {
		this.username = Session.getUsername();
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
		Session.setUsername(username);
	}
}