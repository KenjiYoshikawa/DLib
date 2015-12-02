package projeto.modelo;

public class Usuario {
    private String email;
    private String nome;
    private String senha;
    private String endereco;

    private boolean insertValid;

    final static private int MAX_EMAIL_LENGTH = 50;
    final static private int MIN_PASSW_LENGTH = 8;
    final static private int MAX_PASSW_LENGTH = 30;
    final static private int MAX_NAME_LENGTH  = 30;
    final static private int MAX_ADDR_LENGTH  = 50;

    public Usuario (String email, String senha) {
        this.insertValid = true;

        /* OBS: Senha entre MIN e MAX_PASSW_LENGTH caracteres */
        if (senha.length() >= MIN_PASSW_LENGTH &&
            senha.length() <= MAX_PASSW_LENGTH    ) {
            this.senha = senha;
        } else {
            this.insertValid = false;
        }
        
        /* OBS: Email é chave primária, não pode ter comprimento 0
         *      e deve ter no máximo MAX_EMAIL_LENGTH */
        if (email.length() > 0 && email.length() <= MAX_EMAIL_LENGTH) {
            this.email = email;
        } else {
            this.insertValid = false;
        }
    }

    public String getEmail () {
        return email;
    }

    public String getSenha () {
        return senha;
    }

    public String getNome () {
        return nome;
    }

    public String getEndereco () {
        return endereco;
    }

    public boolean isInsertValid() {
        return insertValid;
    }

    public boolean setNome (String nome) {
        if (nome.length() <= MAX_NAME_LENGTH) {
            this.nome = nome;
            return true;
        } else {
            return false;
        }
    }

    public boolean setEndereco (String endereco) {
        if (endereco.length() <= MAX_ADDR_LENGTH) {
            this.endereco = endereco;
            return true;
        } else {
            return false;
        }
    }
}
