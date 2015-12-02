package usecases;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import projeto.bd.LivroDAO;
import projeto.bd.UsuarioDAO;
import projeto.bd.LivroDAO.BookSearchLimit;
import projeto.modelo.Livro;
import projeto.modelo.Usuario;

public class TestRegisterBook {

	private static UsuarioDAO userdao;
	private static LivroDAO livrodao;
	
	private static Usuario user;
	
	static final private String userteste = "testelogin@teste.com";
	static final private String passteste = "teste123";
	static final private String isbnteste = "TESTE12345678";
	
	@BeforeClass
	public static void setUpClass() {
		userdao = new UsuarioDAO();
		livrodao = new LivroDAO();
		
		user = new Usuario(userteste,passteste);
		
		userdao.cadastraUsuario(user);
	}
	
	@AfterClass
	public static void tearDownClass() {
		userdao.removeUsuario(user);
	}
	
	@Test
	public void testRegistraLivro() {
		Livro livro = null;
		assertTrue(livrodao.cadastraLivroISBN(user, isbnteste));
		
		boolean success = false;
		for (Livro itLivro : livrodao.buscaLivrosDono(user, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
				livro = itLivro;
				success = true;
			}
		}
		assertTrue(success);
		
		livrodao.removeLivro(livro);
	}

}
