package model.bd;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import model.bd.FabricaDeConexao;
import model.modelo.Usuario;
import model.modelo.Livro;
import model.modelo.Emprestimo;

public class EmprestimoDAO {
    private Connection c;

    public EmprestimoDAO() {
        c = FabricaDeConexao.obterInstancia().obterConexao();
    }

    public enum EmpSearchLimit { todos, abertos, fechados };

    public List<Emprestimo> buscaEmprestimos (Usuario u, EmpSearchLimit l) {
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        String sts;
        if (l == EmpSearchLimit.todos ) {
            sts = "SELECT idLivro, dataIni, dataFim, fechado FROM LivroFisico WHERE email_u = ?";
        } else if (l == EmpSearchLimit.abertos) {
            sts = "SELECT idLivro, dataIni, dataFim, fechado FROM LivroFisico WHERE email_u = ? AND fechado = false";
        } else {
            sts = "SELECT idLivro, dataIni, dataFim, fechado FROM LivroFisico WHERE email_u = ? AND fechado = true";
        }

        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);

            busca.setString(1, u.getEmail());

            ResultSet rs = busca.executeQuery();
            while (rs.next()) {
                int idLivro       = rs.getInt("idLivro");
                Timestamp dataIni = rs.getTimestamp("dataIni");
                Timestamp dataFim = rs.getTimestamp("dataFim");
                boolean fechado   = rs.getBoolean("fechado");

                Emprestimo emp = new Emprestimo(u.getEmail(), idLivro, dataIni, dataFim, fechado);
                emprestimos.add(emp);
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

        return emprestimos;
    }

    public Emprestimo pegaLivroEmprestado (Usuario u, Livro livro) {
        String sts1 = "UPDATE LivroFisico SET emprestado = 'true' WHERE id = ?";
        String sts2 = "INSERT INTO ObtemEmprestimo(email_u, idLivro, dataIni, dataFim, fechado) VALUES (?, ?, CURRENT_TIMESTAMP, (SELECT date('now','start of day','+7 day')), 'false')";

        PreparedStatement atualizaLivro = null;
        PreparedStatement insereEmprestimo = null;

        Emprestimo emp = null;

        try {
            c.setAutoCommit(false);
            atualizaLivro = c.prepareStatement(sts1);
            insereEmprestimo = c.prepareStatement(sts2, Statement.RETURN_GENERATED_KEYS);

            atualizaLivro.setInt(1, livro.getId());

            insereEmprestimo.setString(1, u.getEmail());
            insereEmprestimo.setInt(2, livro.getId());

            atualizaLivro.executeUpdate();
            insereEmprestimo.executeUpdate();
            
            ResultSet rs = insereEmprestimo.getGeneratedKeys();
            if (rs != null && rs.next()) {
                //Timestamp dataIni = rs.getTimestamp("dataIni");
                //Timestamp dataFim = rs.getTimestamp("dataFim");

                emp = new Emprestimo(u.getEmail(), livro.getId(), null, null, false);
            }
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
                if (atualizaLivro != null) { atualizaLivro.close(); }
                if (insereEmprestimo != null) { insereEmprestimo.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }

        return emp;
    }

    public void fechaEmprestimo (Emprestimo emp) {
        String sts1 = "UPDATE LivroFisico SET emprestado = false WHERE id = ?";
        String sts2 = "UPDATE ObterEmprestimo SET fechado = true WHERE email = ? AND id = ?";

        PreparedStatement atualizaLivro = null;
        PreparedStatement terminaEmprestimo = null;

        try {
            c.setAutoCommit(false);
            atualizaLivro = c.prepareStatement(sts1);
            terminaEmprestimo = c.prepareStatement(sts2);

            atualizaLivro.setInt(1, emp.getIdLivro());

            terminaEmprestimo.setString(1, emp.getEmailUsuario());
            terminaEmprestimo.setInt(2, emp.getIdLivro());

            atualizaLivro.executeUpdate();
            terminaEmprestimo.executeUpdate();
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
                if (atualizaLivro != null) { atualizaLivro.close(); }
                if (terminaEmprestimo != null) { terminaEmprestimo.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
    }
}
