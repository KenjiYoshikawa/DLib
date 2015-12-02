package projeto;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import projeto.bd.EmprestimoDAO;
import projeto.bd.EmprestimoDAO.EmpSearchLimit;
import projeto.modelo.Emprestimo;

@ViewScoped
@ManagedBean
public class Loans {
	
	private List<Emprestimo> loans;
	private DataModel<Emprestimo> loansModel;
	private String username;
	

    @PostConstruct
    public void init() {
    	EmprestimoDAO dao = new EmprestimoDAO();
    	loans = dao.buscaEmprestimos(Session.getUser(), EmpSearchLimit.emprestado);
		setLoansModel(new ListDataModel<Emprestimo>(loans));
    }
	
	public String unloan()
	{
		Emprestimo loan = getLoansModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.fechaEmprestimo(loan);
		return "myloans?faces-redirect=true";
	}

	public DataModel<Emprestimo> getLoansModel() {
		return loansModel;
	}

	public void setLoansModel(DataModel<Emprestimo> loansModel) {
		this.loansModel = loansModel;
	}
	
	public String viewUser()
	{
		Emprestimo loan = getLoansModel().getRowData();
		username = loan.getEmailDono();
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