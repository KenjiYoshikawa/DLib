package model.bd;

/* Exemplo adaptado de:  http://www.caelum.com.br/apostila-java-web/bancos-de-dados-e-jdbc/  */

import java.sql.*;

public class FabricaDeConexao {

	public static final String NOME_DRIVER = "org.sqlite.JDBC";
	public static final String URL_BD = "jdbc:sqlite:/home/user/desktop/projeto/livraria.bd";
	public static final String USUARIO_BD = "";
	public static final String SENHA_BD = "";

	private static FabricaDeConexao fabricaDeConexao = null;

    /**
     * O construtor busca pelo driver no CLASSPATH.
     */
	private FabricaDeConexao() {
        // Tenta encontrar o driver do JDBC
		try {
			Class.forName(NOME_DRIVER);
		} catch (ClassNotFoundException e) {
            System.out.println("ERRO: Inclua o driver do postgre no seu classpath!");
			e.printStackTrace();
		}
	}

    /**
     * Esta função retorna uma conexão com o banco de dados da livraria.
     *
     * @return Uma conexão com o banco de dados.
     */
	public Connection obterConexao() {
		try {
			return DriverManager.getConnection(URL_BD, USUARIO_BD, SENHA_BD);
		} catch (SQLException e) {
			// A SQLException é "encapsulada" em uma RuntimeException
			// para desacoplar o código da API de JDBC
			throw new RuntimeException(e);
		}
	}
	
    /**
     * Obtem uma instância da fábrica de conexão.
     *
     * A fábrica de conexão é um singleton, ou seja, não é possível instanciar
     * ela mais de uma vez durante a execução do programa.
     *
     * @return Uma instância da fabrica de conexão.
     */
	public static FabricaDeConexao obterInstancia() {
		// A fábrica é um singleton
		if (fabricaDeConexao == null) {
			fabricaDeConexao = new FabricaDeConexao();
		}
		return fabricaDeConexao;
	}

}
