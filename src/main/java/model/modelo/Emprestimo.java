package model.modelo;

import java.sql.*;

public class Emprestimo {
    private String email_u;
    private String emailDono;
    private String isbn;
    private int idLivro;
    private Timestamp dataIni;
    private Timestamp dataFim;
    private boolean fechado;

    public Emprestimo (String email_u, int idLivro, Timestamp dataIni, Timestamp dataFim, boolean fechado) {
        this.email_u = email_u;
        this.idLivro = idLivro;
        this.dataIni = dataIni;
        this.dataFim = dataFim;
        this.fechado = fechado;
    }
    
    public String getISBN() {
		return isbn;
	}

    public String getEmailUsuario () {
        return email_u;
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

    public boolean estaFechado () {
        return fechado;
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
}
