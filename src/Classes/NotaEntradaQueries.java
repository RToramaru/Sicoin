/*
 *  Classe para execução das queries e updates do banco de dados.
 */
package Classes;

import Core.MysqlConnect;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class NotaEntradaQueries {
    private Connection connection;
    private PreparedStatement insertNotaEntrada;

    public NotaEntradaQueries() {
        try {
            
            connection = MysqlConnect.connectDB();
            
            insertNotaEntrada = connection.prepareStatement("INSERT INTO notaentrada "
                    + "(cod,setorOrigem,setorDestino,valorTotal,DataEmissao,dataRecebimento,codSetor,responsavel) VALUES(?,?,?,?,?,?,?,?)");
                  
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            System.exit(1);
        }
    }
    
    public int addNotaEntrada(int cod, String setorOrigem, String setorDestino,float valorTotal,
            java.sql.Date DataEmissao,java.sql.Date dataRecebimento, int codSetor,String responsavel){
        int result = 0;
        try{
            insertNotaEntrada.setInt(1, cod);
            insertNotaEntrada.setString(2, setorOrigem);
            insertNotaEntrada.setString(3, setorDestino);
            insertNotaEntrada.setFloat(4, valorTotal);
            insertNotaEntrada.setDate(5, DataEmissao);
            insertNotaEntrada.setDate(6, dataRecebimento);
            insertNotaEntrada.setInt(7, codSetor);
            insertNotaEntrada.setString(8, responsavel);
            
            result = insertNotaEntrada.executeUpdate();
        } catch(SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Falha ao inserir, código ja utilizado!","Código inválido",JOptionPane.WARNING_MESSAGE);
            return -1;
            
        }
        return result;
    }
    
    
}
