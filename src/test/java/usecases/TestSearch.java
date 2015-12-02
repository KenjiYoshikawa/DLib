package usecases;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import projeto.bd.EmprestimoDAO;
import projeto.bd.LivroDAO;
import projeto.bd.UsuarioDAO;
import projeto.bd.LivroDAO.BookSearchLimit;
import projeto.modelo.Emprestimo;
import projeto.modelo.Livro;
import projeto.modelo.Usuario;

public class TestSearch {
	
	private static UsuarioDAO userdao;
	private static LivroDAO livrodao;
	private static EmprestimoDAO loandao;
	
	private static Usuario userA;
	private static Usuario userB;
	private static Livro livro;
	
	static final private String userAteste = "testeloginA@teste.com";
	static final private String userBteste = "testeloginB@teste.com";
	static final private String passteste = "teste123";
	static final private String isbnteste = "TESTE12345678";
	
	@BeforeClass
	public static void setUpClass() {
		userdao = new UsuarioDAO();
		loandao = new EmprestimoDAO();
		livrodao = new LivroDAO();
		
		userA = new Usuario(userAteste,passteste);
		userB = new Usuario(userBteste,passteste);
		
		userdao.cadastraUsuario(userA);
		userdao.cadastraUsuario(userB);
		livrodao.cadastraLivroISBN(userA, isbnteste);
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
				livro = itLivro;
				System.err.println("asdf");
			}
		}
	}
	
	@AfterClass
	public static void tearDownClass() {
		userdao.removeUsuario(userA);
		userdao.removeUsuario(userB);
        livrodao.removeLivro(livro);
	}
	
	@Test
	public void testSearchBook() {
		boolean found = false;
		for (Livro itLivro : livrodao.buscaLivrosISBN(isbnteste, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
                found = true;
                break;
			}
        }
		if (!found) fail("Livro nao aparece na lista");
	}
	
	@Test
	public void testSearchLoanedBook() {
		Emprestimo loan = loandao.pegaLivroEmprestado(userB, livro);
		loandao.aceitaEmprestimo(loan);
		
		for (Livro itLivro : livrodao.buscaLivrosISBN(isbnteste, BookSearchLimit.nemprestados)) {
			if (itLivro.getId() == livro.getId()) {
				fail("Livro emprestado ainda aparece na lista dos nao emprestados");
			}
        }
		
		loandao.fechaEmprestimo(loan);
		loandao.terminaEmprestimo(loan);
	}
}
