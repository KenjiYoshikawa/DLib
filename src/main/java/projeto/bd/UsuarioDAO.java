package projeto.bd;

import java.sql.*;

import projeto.bd.FabricaDeConexao;
import projeto.modelo.Usuario;

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

    public boolean cadastraUsuario (Usuario u) {
        String sts = "INSERT INTO usuario(email, nome, senha, endereco) VALUES (?,?,?,?)";

        if (!u.isInsertValid()) {
            System.err.println("Objeto não pode ser inserido (invalido)");
            return false;
        }

        boolean success = true;
        PreparedStatement cadastra = null;
        try {
            c.setAutoCommit(false);
            cadastra = c.prepareStatement(sts);

            cadastra.setString(1, u.getEmail());
            cadastra.setString(2, u.getNome());
            cadastra.setString(3, u.getSenha());
            cadastra.setString(4, u.getEndereco());

            cadastra.executeUpdate();
            c.commit();
        } catch (SQLException e) {
        	success = false;
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
        
        return success;
    }

    public boolean removeUsuario (Usuario u) {
        String sts = "DELETE FROM usuario WHERE email = ?";

        boolean success = true;
        PreparedStatement deleta = null;
        try {
            c.setAutoCommit(false);
            deleta = c.prepareStatement(sts);

            deleta.setString(1, u.getEmail());

            deleta.executeUpdate();
            c.commit();
        } catch (SQLException e) {
        	success = false;
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
                if (deleta != null) { deleta.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
        
        return success;
    }
    
}
