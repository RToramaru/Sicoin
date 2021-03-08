/*
 * Classe para execução das Queries e updates do banco de dados.
 */
package Classes;

import Core.MysqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filipi
 */
public class SetorQueries {
    private Connection connection;
    private PreparedStatement insertSetor;
    private PreparedStatement deleteSetor;
    private PreparedStatement updateSetor;
    private PreparedStatement selectAllSetores;
    
    public SetorQueries(){
        
        try {
            // Efetuando Conexão com o banco.
            
            connection = MysqlConnect.connectDB();
            
            // Preparando comandos.
            
            insertSetor = connection.prepareStatement("INSERT INTO setor"
                    + " ( nomSetor,responsavel,tipo,cnpj)"
                    + "VALUES (?,?,?,?)");
            
            deleteSetor = connection.prepareStatement("DELETE FROM setor"
                    + " WHERE codSetor = ?");
            
         
            selectAllSetores = connection.prepareStatement("SELECT * FROM setor");
            
        } catch (SQLException ex) {
            Logger.getLogger(SetorQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Método para selecionar todos os setores do banco de dados.
    
    public List <Setor> getAllSetores(){
        List <Setor> results = null;
        
        try (ResultSet resultSet = selectAllSetores.executeQuery())
        {
            // executeQuery returns ResultSet containing matching entries
            results = new ArrayList<  >();
         
            while (resultSet.next())
            {
                results.add(new Setor(
                    resultSet.getInt("codSetor"),
                    resultSet.getString("nomSetor"),
                    resultSet.getString("responsavel"),
                    resultSet.getString("tipo"),
                    resultSet.getString("cnpj")));
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        } 
      
      return results;
    }
    
    
        // Método para adicionar setor no banco de dados.
    
    public int addSetor(String nomSetor,
            String responsavel, String tipo, String cnpj ){
        int result = 0;
        
        try {
            
            insertSetor.setString(1, nomSetor);
            insertSetor.setString(2, responsavel);
            insertSetor.setString(3, tipo);
            insertSetor.setString(4, cnpj);
            
            result = insertSetor.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(SetorQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    // Método para alterar setor do banco de dados.
    
    public int updateSetor(int codSetor,String nomSetor,
            String responsavel, String tipo, String cnpj){
        int result = 0;
        
        try {
            
              updateSetor = connection.prepareStatement("UPDATE setor SET"
                    + " nomSetor = ?,responsavel = ?,tipo = ?,cnpj = ? WHERE codSetor = "+codSetor+"");
            
            updateSetor.setString(1, nomSetor);
            updateSetor.setString(2, responsavel);
            updateSetor.setString(3, String.valueOf(tipo));
            updateSetor.setString(4, cnpj);
            
            result = updateSetor.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(SetorQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    // Método para deletar setor do banco de dados.
    
    public int deleteSetor(int codSetor){
        
        int result = 0;
        
        try {
            deleteSetor.setInt(1, codSetor);
            result = deleteSetor.executeUpdate();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Falha ao excluir, setor já foi utilizado em alguma nota!","Erro",JOptionPane.WARNING_MESSAGE);
             return -1;
        }
        
        return result;
    }
    
    // Método para encerrar conexão com o banco de dados.
    
    public void close() {
        try {
            connection.close();
            insertSetor.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }

    
   
}
