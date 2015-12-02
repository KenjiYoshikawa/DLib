package projeto.bd;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import projeto.bd.FabricaDeConexao;
import projeto.modelo.Usuario;
import projeto.modelo.Livro;

public class LivroDAO {
    private Connection c;

    public LivroDAO() {
        c = FabricaDeConexao.obterInstancia().obterConexao();
    }

    public enum BookSearchLimit { todos, emprestados, nemprestados };
    
    public List<Livro> buscaTodosLivros (String email) {
        List<Livro> livros = new ArrayList<Livro>();

        String sts = "SELECT id, isbn, emailDono FROM LivroFisico where emprestado='false' and emailDono!= ?";
        //SELECT fisico.isbn, abstrato.titulo FROM LivroFisico as fisico, LivroAbstrato as abstrato where fisico.isbn = abstrato.isbn
        
        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);
            busca.setString(1, email);

            ResultSet rs = busca.executeQuery();
            while (rs.next()) {
            	int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String dono = rs.getString("emailDono");
                Livro livro = new Livro(id, dono, isbn, false);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (busca != null) { busca.close(); }
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return livros;
    }
    
    public boolean removeLivro (Livro livro) {
        String sts = "DELETE FROM LivroFisico where id= ?";
        
        boolean success = false;
        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);
            busca.setInt(1, livro.getId());
            busca.execute();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (busca != null) { busca.close(); }
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return success;
    }
    
    public List<Livro> buscaLivrosISBN (String isbn, BookSearchLimit l) {
        List<Livro> livros = new ArrayList<Livro>();

        String sts;
        if (l == BookSearchLimit.todos ) {
            sts = "SELECT id, emailDono, emprestado FROM LivroFisico WHERE isbn = ?";
        } else if (l == BookSearchLimit.emprestados) {
            sts = "SELECT id, emailDono, emprestado FROM LivroFisico WHERE isbn = ? AND emprestado = 'true'";
        } else {
            sts = "SELECT id, emailDono, emprestado FROM LivroFisico WHERE isbn = ? AND emprestado = 'false'";
        }

        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);

            busca.setString(1, isbn);

            ResultSet rs = busca.executeQuery();
            while (rs.next()) {
                int id             = rs.getInt("id");
                String emailDono   = rs.getString("emailDono");
                boolean emprestado = rs.getBoolean("emprestado");

                Livro livro = new Livro(id, emailDono, isbn, emprestado);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (busca != null) { busca.close(); }
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return livros;
    }

    public List<Livro> buscaLivrosDono (Usuario u, BookSearchLimit l) {
        List<Livro> livros = new ArrayList<Livro>();

        String sts;
        if (l == BookSearchLimit.todos ) {
            sts = "SELECT id, isbn, emprestado FROM LivroFisico WHERE emailDono = ?";
        } else if (l == BookSearchLimit.emprestados) {
            sts = "SELECT id, isbn, emprestado FROM LivroFisico WHERE emailDono = ? AND emprestado = 'true'";
        } else {
            sts = "SELECT id, isbn, emprestado FROM LivroFisico WHERE emailDono = ? AND emprestado = 'false'";
        }

        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);

            busca.setString(1, u.getEmail());

            ResultSet rs = busca.executeQuery();
            while (rs.next()) {
                int id             = rs.getInt("id");
                String isbn        = rs.getString("isbn");
                boolean emprestado = rs.getBoolean("emprestado");

                Livro livro = new Livro(id, u.getEmail(), isbn, emprestado);
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (busca != null) { busca.close(); }
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return livros;
    }

    public boolean cadastraLivroISBN (Usuario u, String isbn) {
        String sts = "INSERT INTO LivroFisico(id, emailDono, isbn, emprestado) VALUES (null, ?, ?, 'false')";

        boolean success = false;
        PreparedStatement cadastra = null;
        try {
            c.setAutoCommit(false);
            cadastra = c.prepareStatement(sts);

            cadastra.setString(1, u.getEmail());
            cadastra.setString(2, isbn);

            cadastra.executeUpdate();
            c.commit();
            success = true;
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
        return success;
    }
}
