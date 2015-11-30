import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
	private List<Emprestimo> returns;
	private DataModel<Emprestimo> returnsModel;
	

    @PostConstruct
    public void init() {
    	EmprestimoDAO dao = new EmprestimoDAO();
    	requests = dao.buscaEmprestimos(Session.getUser(), EmpSearchLimit.pedidoEmp);
		setRequestsModel(new ListDataModel<Emprestimo>(requests));
    	returns = dao.buscaEmprestimos(Session.getUser(), EmpSearchLimit.pedidoDev);
		setReturnsModel(new ListDataModel<Emprestimo>(returns));
    }
	
	public String accept()
	{
		Emprestimo loan = getRequestsModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.aceitaEmprestimo(loan);
		refresh();
		return "requests?faces-redirect=true";
	}
	
	public String reject()
	{
		Emprestimo loan = getRequestsModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.terminaEmprestimo(loan);
		refresh();
		return "requests?faces-redirect=true";
	}

	public DataModel<Emprestimo> getRequestsModel() {
		return requestsModel;
	}

	public void setRequestsModel(DataModel<Emprestimo> requestsModel) {
		this.requestsModel = requestsModel;
	}
	
	public String confirm()
	{
		Emprestimo loan = getReturnsModel().getRowData();
		EmprestimoDAO dao = new EmprestimoDAO();
		dao.terminaEmprestimo(loan);
		refresh();
		return "requests?faces-redirect=true";
	}

	public void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		javax.faces.application.ViewHandler handler = context.getApplication().getViewHandler();
		UIViewRoot root = handler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
	}
	public DataModel<Emprestimo> getReturnsModel() {
		return returnsModel;
	}

	public void setReturnsModel(DataModel<Emprestimo> returnsModel) {
		this.returnsModel = returnsModel;
	}
}