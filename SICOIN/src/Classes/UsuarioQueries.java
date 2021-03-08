/*
 * Classe para queries e updates.
 */
package Classes;

import Core.MysqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class UsuarioQueries {

    private Connection connection;
    private PreparedStatement insertUsuario;
    private PreparedStatement selectUsuario;
    private PreparedStatement selectUsuarioLogin;
    private PreparedStatement selectUsuarioCompleto;
    private PreparedStatement updateUsuarioSenha;
    private PreparedStatement updateUsuario;
    private PreparedStatement deleteUsuario;
    private PreparedStatement selectAllUsuarios;
    public UsuarioQueries() {
        try {
            
            connection = MysqlConnect.connectDB();
            insertUsuario = connection.prepareStatement("INSERT INTO usuario " + 
                    "(nomUser,cpf,endereco,fone,login,senha,tipo) VALUES(?, ?, ?, ?, ?, ?, ?)");
            selectUsuario = connection.prepareStatement("SELECT * FROM usuario" + 
                    " WHERE login = ? AND senha = ?");
            selectUsuarioCompleto = connection.prepareStatement("SELECT * FROM usuario" + 
                    " WHERE cpf = ? AND login = ?");
            updateUsuarioSenha = connection.prepareStatement("UPDATE Usuario "
                    + "SET senha = ? WHERE login = ?");
            updateUsuario = connection.prepareStatement("UPDATE Usuario "
                    + "SET codUser = ?, NomUser = ?, cpf = ?, endereco = ? fone = ?, login = ?, senha = ?, tipo = ? WHERE codUser = ?");
            deleteUsuario = connection.prepareStatement("DELETE FROM usuario "
                    + "WHERE login = ?");           
            selectAllUsuarios = connection.prepareStatement("SELECT "
                    + "nomUser,cpf,endereco,fone,login,senha,tipo FROM usuario");
            selectUsuarioLogin = connection.prepareStatement("SELECT * FROM usuario" + 
                    " WHERE login = ?");
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            System.exit(1);
        }
    }
    public List <Usuario> getAllUsuario(){
        List <Usuario> results = null;
        
        try (ResultSet resultSet = selectAllUsuarios.executeQuery())
        {
            results = new ArrayList<  >();        
            while (resultSet.next())
            {
                results.add(new Usuario(
                    resultSet.getString("nomUser"),
                    resultSet.getString("cpf"),
                    resultSet.getString("endereco"),
                    resultSet.getString("fone"),
                    resultSet.getString("login"),
                    resultSet.getString("senha"),
                    resultSet.getString("tipo")));
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        } 
      
      return results;
    }
    
    public int addUsuario(String nomUser, String cpf, String endereco, String fone, String login, String senha, String tipo){
        int result = 0;
        try{
            insertUsuario.setString(1, nomUser);
            insertUsuario.setString(2, cpf);
            insertUsuario.setString(3, endereco);
            insertUsuario.setString(4, fone);
            insertUsuario.setString(5, login);
            insertUsuario.setString(6, senha);
            insertUsuario.setString(7, tipo);
            result = insertUsuario.executeUpdate();
        } catch(SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Usuario indisponível, login já cadastrado!","Login inválido",JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return result;
    }

    public Usuario selectUsuario(String usuario,String senha){
        try {
            selectUsuario.setString(1, usuario);
            selectUsuario.setString(2, senha);                               
            ResultSet rs = selectUsuario.executeQuery();
            if(rs.next()){
                return new Usuario(rs.getString("nomUser"),rs.getString("cpf"), rs.getString("endereco"), rs.getString("fone"), rs.getString("login"), rs.getString("senha"),rs.getString("tipo"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new Usuario();
        }      
        return new Usuario();
    }
    
    public void selectUsuarioCompleto(String cpf, String login,String senha){
        try {
            selectUsuarioCompleto.setString(1, cpf); 
            selectUsuarioCompleto.setString(2, login);
            ResultSet rs = selectUsuarioCompleto.executeQuery();
            if(rs.next()){
                int result = UpdateUsuarioSenha(senha,login);
            } else{
                JOptionPane.showMessageDialog(null, "Error ao alterar a senha\nDados não informados corretamente");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }      
    }
    
    public int UpdateUsuarioSenha(String senha,String login){
        int result = 0;
        try {
            updateUsuarioSenha.setString(1, senha);
            updateUsuarioSenha.setString(2, login);
            result = updateUsuarioSenha.executeUpdate();
         
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;
    }
    
    public int UpdateUsuario(String nomUser, String cpf, String endereco, String fone, String login,String senha,String tipo){
        int result = 0;
        try {
            updateUsuario.setString(1, nomUser);
            updateUsuario.setString(2, cpf); 
            updateUsuario.setString(3, endereco); 
            updateUsuario.setString(4, fone); 
            updateUsuario.setString(5, login);
            updateUsuario.setString(6, senha);
            updateUsuario.setString(7, tipo);
            result = updateUsuario.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;     
    }
    
    public int DeleteUsuario(String login){
        int result = 0;
        try{
            deleteUsuario.setString(1, login);
            result = deleteUsuario.executeUpdate();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;
    }
    public void close() {
        try {
            connection.close();
            insertUsuario.close();
            selectUsuario.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
    public Usuario selectUsuarioLogin(String usuario){
        try {
            selectUsuarioLogin.setString(1, usuario);                               
            ResultSet rs = selectUsuarioLogin.executeQuery();
            if(rs.next()){
                return new Usuario(rs.getString("nomUser"),rs.getString("cpf"), rs.getString("endereco"), rs.getString("fone"), rs.getString("login"), rs.getString("senha"),rs.getString("tipo"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new Usuario();
        }      
        return new Usuario();
    }
}
