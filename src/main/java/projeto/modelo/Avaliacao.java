package projeto.modelo;

public class Avaliacao {
    private int id;
    private int nota;
    private String email1;
    private String email2;
    private String descricao;

    public Avaliacao(int id, int nota, String email1, String email2, String descricao) {
        this.setId(id);
        this.setNota(nota);
        this.setEmail1(email1);
        this.setEmail2(email2);
        this.setDescricao(descricao);
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
