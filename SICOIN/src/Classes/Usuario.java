/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author Rafael
 */
public class Usuario {
    private String nomUser;
    private String cpf;
    private String endereco;
    private String fone;
    private String login;
    private String senha;
    private String tipo;

    public Usuario(){
        
    }
    public Usuario(String nom_User, String cpf_User, String endereco_User, String fone_User, String login_User, String senha_User, String tipo_User) {
        this.nomUser = nom_User;
        this.cpf = cpf_User;
        this.endereco = endereco_User;
        this.fone = fone_User;
        this.login = login_User;
        this.senha = senha_User;
        this.tipo = tipo_User;
    }

    public Usuario(String nom_User, String cpf_User, String endereco_User, String fone_User, String login_User, String tipo_User) {
        this.nomUser = nom_User;
        this.cpf = cpf_User;
        this.endereco = endereco_User;
        this.fone = fone_User;
        this.login = login_User;
        this.tipo = tipo_User;
    }


    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
}
