package model.bd;

import java.sql.*;

import model.bd.FabricaDeConexao;
import model.modelo.Usuario;

public class UsuarioDAO {
    private Connection c;

    public UsuarioDAO() {
        c = FabricaDeConexao.obterInstancia().obterConexao();
    }

    public boolean autenticaLogin (Usuario u) {
        String sts = "SELECT email, senha FROM usuario WHERE email = ? and senha = ?";

        PreparedStatement autentica = null;
        try {
            autentica = c.prepareStatement(sts);

            autentica.setString(1, u.getEmail());
            autentica.setString(2, u.getSenha());

            ResultSet rs = autentica.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (autentica != null) { autentica.close(); }
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return false;
    }

    public void cadastraUsuario (Usuario u) {
        String sts = "INSERT INTO usuario(email, nome, senha, endereco) VALUES (?,?,?,?)";

        if (!u.isInsertValid()) {
            System.err.println("Objeto não pode ser inserido (invalido)");
            return;
        }

        PreparedStatement cadastra = null;
        try {
            c.setAutoCommit(false);
            cadastra = c.prepareStatement(sts);

            cadastra.setString(1, u.getEmail());
            cadastra.setString(2, u.getNome());
            cadastra.setString(3, u.getSenha());
            cadastra.setString(4, u.getEndereco());

            cadastra.executeUpdate();
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
                if (cadastra != null) { cadastra.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
    }
}
