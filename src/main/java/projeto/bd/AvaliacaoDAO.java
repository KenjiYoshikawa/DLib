package projeto.bd;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import projeto.bd.FabricaDeConexao;
import projeto.modelo.Avaliacao;

public class AvaliacaoDAO {
	private Connection c;

	public AvaliacaoDAO() {
		c = FabricaDeConexao.obterInstancia().obterConexao();
	}

	public List<Avaliacao> buscaAvaliacoes(String user) {
		List<Avaliacao> avals = new ArrayList<Avaliacao>();

		String sts = "SELECT id, email_u1, email_u2, escala, descricao FROM Avalia where email_u2=?";

		PreparedStatement busca = null;
		try {
			busca = c.prepareStatement(sts);
			busca.setString(1, user);

			ResultSet rs = busca.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int nota = rs.getInt("escala");
				String email1 = rs.getString("email_u1");
				String email2 = rs.getString("email_u2");
				String descricao = rs.getString("descricao");
				Avaliacao aval = new Avaliacao(id, nota, email1, email2, descricao);
				avals.add(aval);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (busca != null) {
					busca.close();
				}
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		}

		return avals;
	}

	public void adicionaAvaliacao(Avaliacao aval) {
		String sts = "INSERT INTO Avalia(id, email_u1, email_u2, escala, descricao) VALUES (null, ?, ?, ?, ?)";

		PreparedStatement cadastra = null;
		try {
			c.setAutoCommit(false);
			cadastra = c.prepareStatement(sts);

			cadastra.setString(1, aval.getEmail1());
			cadastra.setString(2, aval.getEmail2());
			cadastra.setInt(3, aval.getNota());
			cadastra.setString(4, aval.getDescricao());

			cadastra.executeUpdate();
			c.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (c != null) {
				try {
					System.err.println("Executando rollback");
					c.rollback();
				} catch (SQLException excep) {
					excep.printStackTrace();
				}
			}
		} finally {
			try {
				if (cadastra != null) {
					cadastra.close();
				}
				c.setAutoCommit(true);
			} catch (SQLException excep) {
				excep.printStackTrace();
			}
		}
	}
}
