import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import model.bd.EmprestimoDAO;
import model.bd.EmprestimoDAO.EmpSearchLimit;
import model.modelo.Emprestimo;

@RequestScoped
@ManagedBean
public class Requests {
	
	private List<Emprestimo> requests;
	private DataModel<Emprestimo> requestsModel;
	

    @PostConstruct
    public void init() {
    	EmprestimoDAO dao = new EmprestimoDAO();
    	requests = dao.buscaEmprestimos(Session.getUser(), EmpSearchLimit.pedidoEmp);
		setRequestsModel(new ListDataModel<Emprestimo>(requests));
    }
	
	public String accept()
	{
		Emprestimo loan = getRequestsModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.aceitaEmprestimo(loan);
		return "requests?faces-redirect=true";
	}
	
	public String reject()
	{
		Emprestimo loan = getRequestsModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.terminaEmprestimo(loan);
		return "requests?faces-redirect=true";
	}

	public DataModel<Emprestimo> getRequestsModel() {
		return requestsModel;
	}

	public void setRequestsModel(DataModel<Emprestimo> requestsModel) {
		this.requestsModel = requestsModel;
	}
}