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

public class TestLivroDAO {
	private static UsuarioDAO userdao;
	private static LivroDAO livrodao;
	private static EmprestimoDAO loandao;
	
	private static Usuario userA;
	private static Usuario userB;
	
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
	}
	
	@AfterClass
	public static void tearDownClass() {
		userdao.removeUsuario(userA);
		userdao.removeUsuario(userB);
	}
	
	@Test
	public void testCadastraERemoveLivroISBN() {
		Livro livro = null;
		
		assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
				livro = itLivro;
			}
		}
		assertTrue(livrodao.removeLivro(livro));
	}
	
	@Test
	public void testBuscaLivrosISBNTodos() {
		Livro livro1 = null;
		Livro livro2 = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
				if (livro1 == null) {
					livro1 = itLivro;				
				} else {
					livro2 = itLivro;				
				}
			}
		}
	    
		Emprestimo loan = loandao.pegaLivroEmprestado(userB, livro1);
		loandao.aceitaEmprestimo(loan);
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosISBN(isbnteste, BookSearchLimit.todos)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		
		assertEquals(2, matches);
		
		loandao.fechaEmprestimo(loan);
		loandao.terminaEmprestimo(loan);
		assertTrue(livrodao.removeLivro(livro1));
		assertTrue(livrodao.removeLivro(livro2));
	}

	@Test
	public void testBuscaLivrosISBNEmprestados() {
		Livro livro = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
			    livro = itLivro;				
			}
		}
	    
		Emprestimo loan = loandao.pegaLivroEmprestado(userB, livro);
		loandao.aceitaEmprestimo(loan);
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosISBN(isbnteste, BookSearchLimit.emprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		
		assertEquals(1, matches);
		
		loandao.fechaEmprestimo(loan);
		loandao.terminaEmprestimo(loan);
		assertTrue(livrodao.removeLivro(livro));
	}
	
	@Test
	public void testBuscaLivrosISBNNaoEmprestados() {
		Livro livro = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
			    livro = itLivro;				
			}
		}
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosISBN(isbnteste, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		assertEquals(1, matches);
		
		assertTrue(livrodao.removeLivro(livro));
	}
	
	@Test
	public void testBuscaLivrosUsuarioTodos() {
		Livro livro1 = null;
		Livro livro2 = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
				if (livro1 == null) {
					livro1 = itLivro;				
				} else {
					livro2 = itLivro;				
				}
			}
		}
	    
		Emprestimo loan = loandao.pegaLivroEmprestado(userB, livro1);
		loandao.aceitaEmprestimo(loan);
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.todos)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		
		assertEquals(2, matches);
		
		loandao.fechaEmprestimo(loan);
		loandao.terminaEmprestimo(loan);
		assertTrue(livrodao.removeLivro(livro1));
		assertTrue(livrodao.removeLivro(livro2));
	}

	@Test
	public void testBuscaLivrosUsuarioEmprestados() {
		Livro livro = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
			    livro = itLivro;				
			}
		}
	    
		Emprestimo loan = loandao.pegaLivroEmprestado(userB, livro);
		loandao.aceitaEmprestimo(loan);
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.emprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		
		assertEquals(1, matches);
		
		loandao.fechaEmprestimo(loan);
		loandao.terminaEmprestimo(loan);
		assertTrue(livrodao.removeLivro(livro));
	}
	
	@Test
	public void testBuscaLivrosUsuarioNaoEmprestados() {
		Livro livro = null;
		
	    assertTrue(livrodao.cadastraLivroISBN(userA,isbnteste));
	    
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) {
			    livro = itLivro;				
			}
		}
		
		int matches = 0;
		for (Livro itLivro : livrodao.buscaLivrosDono(userA, BookSearchLimit.nemprestados)) {
			if (itLivro.getISBN().equals(isbnteste)) matches++;
        }
		assertEquals(1, matches);
		
		assertTrue(livrodao.removeLivro(livro));
	}
}
