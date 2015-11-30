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
public class Loans {
	
	private List<Emprestimo> loans;
	private DataModel<Emprestimo> loansModel;
	

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
}