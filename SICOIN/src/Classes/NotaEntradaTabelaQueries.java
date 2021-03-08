/*
 *  Classe para execução das queries e updates do banco de dados..
 */
package Classes;

import Core.MysqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class NotaEntradaTabelaQueries {

    private Connection connection;
    private PreparedStatement insertTabela;
    private PreparedStatement selectTabela;
    private PreparedStatement deleteTabela;

    public NotaEntradaTabelaQueries() {
        try {
            
            connection = MysqlConnect.connectDB();
            
            insertTabela = connection.prepareStatement("INSERT INTO notaprodutoentrada "
                    + "(quantidade,codNotaEntrada,codProdutoEntrada,precoNotaEntrada) VALUES(?,?,?,?)");
            
            selectTabela = connection.prepareStatement("SELECT * FROM produto WHERE descricao = ?");
            
            deleteTabela = connection.prepareStatement("DELETE FROM notaprodutoentrada "
                    + "WHERE codNotaEntrada = ? AND descricao = ? AND quantidade = ?");
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
            System.exit(1);
        }
    }

    
    public int addTabelaEntrada(float quantidade,int codNotaEntrada, int codProdutoEntrada, float precoNotaEntrada){
        int result = 0;
        try{
            insertTabela.setFloat(1, quantidade);
            insertTabela.setInt(2, codNotaEntrada);
            insertTabela.setInt(3, codProdutoEntrada);
            insertTabela.setFloat(4, precoNotaEntrada);
            
            result = insertTabela.executeUpdate();
        } catch(SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Falha ao inserir" + sqle.getMessage());
        }
        return result;
    }

    public Produto selectTabelaCompleta(String descricao){
        try {
            selectTabela.setString(1, descricao);                              
            ResultSet rs = selectTabela.executeQuery();
            if(rs.next()){
                return new Produto(rs.getInt("codProd"), rs.getString("descricao"), rs.getString("unidade"),rs.getFloat("estoque") , rs.getFloat("valor"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return new Produto();
        }      
        return new Produto();
    }
    
    public int deleteNotaTabela(int codNotaEntrada,String descricao,float quantidade) throws SQLException{
        int result = 0;
        deleteTabela.setInt(1, codNotaEntrada);
        deleteTabela.setString(2, descricao);
        deleteTabela.setFloat(3, quantidade);
        result = deleteTabela.executeUpdate();
        return result;       
    }
    
    public void close() {
        try {
            connection.close();
            insertTabela.close();
            selectTabela.close();
            deleteTabela.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
}
