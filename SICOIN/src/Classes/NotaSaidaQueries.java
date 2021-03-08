/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Core.MysqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Filipi
 */
public class NotaSaidaQueries {
    private Connection connection;
    private PreparedStatement insertNotaSaida;
    private PreparedStatement deleteNotaSaida;
    
    public NotaSaidaQueries(){
        try {
            // Efetuando Conexão com o banco
            connection = MysqlConnect.connectDB();
            
            // Preparando comandos;
            insertNotaSaida = connection.prepareStatement("INSERT INTO notasaida "
                    + "(cod,naturezaOperacao,destino,cnpj,dataEmissao,valorTotal,codSetor,responsavel) "
                    + "VALUES (?,?,?,?,?,?,?,?)");
            
                      
            
        } catch (SQLException ex) {
            Logger.getLogger(NotaSaidaQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int addNotaSaida(int cod,String naturezaOperacao,String destino,String cnpj,
            java.sql.Date dataEmissao,float valorTotal,int codSetor,String responsavel){
        int result = 0;
        try{
            insertNotaSaida.setInt(1, cod);
            insertNotaSaida.setString(2, naturezaOperacao);
            insertNotaSaida.setString(3, destino);
            insertNotaSaida.setString(4, cnpj);
            insertNotaSaida.setDate(5, dataEmissao);
            insertNotaSaida.setFloat(6, valorTotal);
            insertNotaSaida.setInt(7, codSetor);
            insertNotaSaida.setString(8, responsavel);
            result = insertNotaSaida.executeUpdate();
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Falha ao inserir, código ja utilizado!","Código inválido",JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return result;
    }
    

    
    
    public void close() {
        try {
            connection.close();
            insertNotaSaida.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
    
}
