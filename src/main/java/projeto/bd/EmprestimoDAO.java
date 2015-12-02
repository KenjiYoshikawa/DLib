package projeto.bd;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import projeto.bd.FabricaDeConexao;
import projeto.modelo.Usuario;
import projeto.modelo.Livro;
import projeto.modelo.Emprestimo;

public class EmprestimoDAO {
    private Connection c;

    public EmprestimoDAO() {
        c = FabricaDeConexao.obterInstancia().obterConexao();
    }

    public enum EmpSearchLimit {  emprestado, pedidoEmp, pedidoDev };

    public List<Emprestimo> buscaEmprestimos (Usuario u, EmpSearchLimit l) {
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        String sts;
        if (l == EmpSearchLimit.pedidoEmp) {
            sts = "SELECT e.email_u, e.idLivro, e.dataFim, e.status, l.isbn, l.emailDono, e.dataIni from LivroFisico as l, ObtemEmprestimo as e where e.idLivro=l.id AND e.status='pedido_emp' AND l.emailDono=?";
        } else if (l == EmpSearchLimit.pedidoDev) {
            sts = "SELECT e.email_u, e.idLivro, e.dataFim, e.status, l.isbn, l.emailDono, e.dataIni from LivroFisico as l, ObtemEmprestimo as e where e.idLivro=l.id AND e.status='pedido_dev' AND l.emailDono=?";
        } else {
        	sts = "SELECT e.email_u, e.idLivro, e.dataFim, e.status, l.isbn, l.emailDono, e.dataIni from LivroFisico as l, ObtemEmprestimo as e where e.idLivro=l.id AND e.status='emprestado' AND e.email_u=?";
        }

        PreparedStatement busca = null;
        try {
            busca = c.prepareStatement(sts);

            busca.setString(1, u.getEmail());

            ResultSet rs = busca.executeQuery();
            while (rs.next()) {
                int idLivro       = rs.getInt("idLivro");
                //Timestamp dataIni = rs.getTimestamp("dataIni");
                //Timestamp dataFim = rs.getTimestamp("dataFim");
                Timestamp dataIni = null;
                Timestamp dataFim = null;
                String status   = rs.getString("status");
                String emailDono  = rs.getString("emailDono");
                String email_u = rs.getString("email_u");
                String isbn  = rs.getString("isbn");

                Emprestimo emp = new Emprestimo(email_u, idLivro, dataIni, dataFim, status);
                emp.setEmailDono(emailDono);
                emp.setISBN(isbn);
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
        String sts2 = "INSERT INTO ObtemEmprestimo(email_u, idLivro, dataIni, dataFim, status) VALUES (?, ?, CURRENT_TIMESTAMP, (SELECT date('now','start of day','+7 day')), 'pedido_emp')";

        PreparedStatement atualizaLivro = null;
        PreparedStatement insereEmprestimo = null;
        Statement getNewEntry = null; 

        Emprestimo emp = null;

        try {
            c.setAutoCommit(false);
            atualizaLivro = c.prepareStatement(sts1);
            insereEmprestimo = c.prepareStatement(sts2, Statement.RETURN_GENERATED_KEYS);
            getNewEntry = c.createStatement();

            atualizaLivro.setInt(1, livro.getId());

            insereEmprestimo.setString(1, u.getEmail());
            insereEmprestimo.setInt(2, livro.getId());

            atualizaLivro.executeUpdate();
            insereEmprestimo.executeUpdate();
            
            ResultSet rs = getNewEntry.executeQuery("SELECT last_insert_rowid()");
            if (rs != null && rs.next()) {
                //Timestamp dataIni = rs.getTimestamp("dataIni");
                //Timestamp dataFim = rs.getTimestamp("dataFim");

                emp = new Emprestimo(u.getEmail(), livro.getId(), null, null, "pedido_emp");
            }
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
        String sts = "UPDATE ObtemEmprestimo SET status = 'pedido_dev' WHERE email_u = ? AND idLivro = ?";

        PreparedStatement terminaEmprestimo = null;

        try {
            c.setAutoCommit(false);
            terminaEmprestimo = c.prepareStatement(sts);
            terminaEmprestimo.setString(1, emp.getEmailUsuario());
            terminaEmprestimo.setInt(2, emp.getIdLivro());
            terminaEmprestimo.executeUpdate();
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
                if (terminaEmprestimo != null) { terminaEmprestimo.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
    }
    
    public void aceitaEmprestimo (Emprestimo emp) {
        String sts = "UPDATE ObtemEmprestimo SET status = 'emprestado' WHERE email_u = ? AND idLivro = ?";

        PreparedStatement terminaEmprestimo = null;

        try {
            c.setAutoCommit(false);
            terminaEmprestimo = c.prepareStatement(sts);
            terminaEmprestimo.setString(1, emp.getEmailUsuario());
            terminaEmprestimo.setInt(2, emp.getIdLivro());
            terminaEmprestimo.executeUpdate();
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
                if (terminaEmprestimo != null) { terminaEmprestimo.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
    }
    
    public void terminaEmprestimo (Emprestimo emp) {
        String sts1 = "UPDATE LivroFisico SET emprestado = 'false' WHERE id = ?";
        String sts2 = "DELETE FROM ObtemEmprestimo WHERE email_u = ? AND idLivro = ?";

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
                if (atualizaLivro != null) { atualizaLivro.close(); }
                if (terminaEmprestimo != null) { terminaEmprestimo.close(); }
                c.setAutoCommit(true);
            } catch (SQLException excep) {
                excep.printStackTrace();
            }
        }
    }
}
