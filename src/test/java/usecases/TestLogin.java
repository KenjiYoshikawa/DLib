package usecases;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import projeto.bd.UsuarioDAO;
import projeto.modelo.Usuario;

public class TestLogin {
	
	private static UsuarioDAO userdao;
	
	static final private String userteste = "testelogin@teste.com";
	static final private String passteste = "teste123";
	static final private String diffpassteste = "teste1234";
	static final private String shortpassteste = "teste12";
	
	@BeforeClass
	public static void setUpClass() {
		userdao = new UsuarioDAO();
	}
	
	@Test
	public void testLoginSuccess() {
		Usuario user = new Usuario(userteste,passteste);
		
		/* Registrando novo usuario */
		assertTrue(userdao.cadastraUsuario(user));
		
		/* Logando como novo usuario */
		assertTrue(userdao.autenticaLogin(user));
		
		/* Removendo usuario teste */
		assertTrue(userdao.removeUsuario(user));
	}
	
	@Test
	public void testLoginUnexistantUser() {
		Usuario user = new Usuario(userteste,passteste);
		
		/* logando como usuario nao existente */
		assertFalse(userdao.autenticaLogin(user));
	}

	@Test
	public void testLoginIncorrectPassword() {
		Usuario user = new Usuario(userteste,passteste);
		Usuario userincpass = new Usuario(userteste,diffpassteste);
		
		/* Registrando novo usuario */
		assertTrue(userdao.cadastraUsuario(user));
		
		/* Logando como usuario com senha errada */
		assertFalse(userdao.autenticaLogin(userincpass));
		
		/* Removendo usuario teste */
		assertTrue(userdao.removeUsuario(user));
	}
	
	@Test
	public void testRegisterShortPassword() {
		Usuario user = new Usuario(userteste,shortpassteste);;
		
		/* Cadastrando usuario com senha curta demais */
		assertFalse(userdao.cadastraUsuario(user));
	}
}
