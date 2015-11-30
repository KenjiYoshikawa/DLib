package model.modelo;

import java.sql.*;

public class Emprestimo {
    private String email_u;
    private String emailDono;
    private String isbn;
    private int idLivro;
    private Timestamp dataIni;
    private Timestamp dataFim;
    private String status;

    public Emprestimo (String email_u, int idLivro, Timestamp dataIni, Timestamp dataFim, String status) {
        this.setEmail_u(email_u);
        this.idLivro = idLivro;
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.status = status;
    }
    
    public String getISBN() {
		return isbn;
	}

    public String getEmailUsuario () {
        return getEmail_u();
    }

    public int getIdLivro() {
        return idLivro;
    }
    
    public Timestamp getDataIni () {
        return dataIni;
    }

    public Timestamp getDataFim () {
        return dataFim;
    }

    public String getStatus () {
        return status;
    }

	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

	public String getEmailDono() {
		return emailDono;
	}

	public void setEmailDono(String emailDono) {
		this.emailDono = emailDono;
	}

	public String getEmail_u() {
		return email_u;
	}

	public void setEmail_u(String email_u) {
		this.email_u = email_u;
	}
}
