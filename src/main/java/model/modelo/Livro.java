package model.modelo;

public class Livro {
    private int id;
    private String emailDono;
    private String titulo;
    private String descricao;
    private String isbn;
    private boolean emprestado;

    public Livro(int id, String emailDono, String isbn, boolean emprestado) {
        this.id = id;
        this.emailDono = emailDono;
        this.isbn = isbn;
        this.emprestado = emprestado;
    }
    
    public int getId() {
        return id;
    }

    public String getEmailDono () {
        return emailDono;
    }
    
    public String getISBN () {
        return isbn;
    }

    public boolean getEmprestado () {
        return emprestado;
    }

    public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String título) {
		this.titulo = título;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
