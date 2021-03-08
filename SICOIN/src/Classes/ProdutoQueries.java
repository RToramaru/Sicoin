/*
 * Classe para execução das queries e updates do banco de dados.
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
public class ProdutoQueries {
    private Connection connection;
    private PreparedStatement insertProduto;
    private PreparedStatement deleteProduto;
    private PreparedStatement updateProduto;
    private PreparedStatement selectAllProdutos;
    private PreparedStatement selectProdutos;
    private PreparedStatement selectAllProdutosDescricao;
    
    public ProdutoQueries(){
        try {
            
            // Efetuando Conexão com o banco
            connection = MysqlConnect.connectDB();
            
            // Preparando comandos;
            
            selectAllProdutos = connection.prepareStatement("SELECT * FROM produto");
            
            selectAllProdutosDescricao = connection.prepareStatement("SELECT descricao FROM produto");
                      
            insertProduto = connection.prepareStatement("INSERT INTO produto"
                    + " (descricao,unidade,valor,estoque) VALUES"
                    + " (?,?,?,?)");
            
            deleteProduto = connection.prepareStatement("DELETE FROM produto"
                    + " WHERE codProd = ?");
           
            selectProdutos = connection.prepareStatement("SELECT *FROM produto WHERE codProd = ?");
            
            
        } catch (SQLException ex) {
            
            Logger.getLogger(ProdutoQueries.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
     public List< Produto > getAllProdutos()
    {
        List< Produto > results = null;
      
        try (ResultSet resultSet = selectAllProdutos.executeQuery())
        {
            // executeQuery returns ResultSet containing matching entries
            results = new ArrayList<  >();
         
            while (resultSet.next())
            {
                results.add(new Produto(
                    resultSet.getInt("codProd"),
                    resultSet.getString("descricao"),
                    resultSet.getString("unidade"),
                    resultSet.getFloat("valor"),
                    resultSet.getFloat("estoque")));
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        } 
      
      return results;
    } 
     
     public List< String > getAllProdutosDescricao()
    {
        List< String> results = null;
      
        try (ResultSet resultSet = selectAllProdutosDescricao.executeQuery())
        {
            // executeQuery returns ResultSet containing matching entries
            results = new ArrayList<  >();
         
            while (resultSet.next())
            {
                results.add(new String(
                   
                    resultSet.getString("descricao")));
            } 
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        } 
      
      return results;
    } 
    
    // Método para adicionar produto no banco de dados.
    
    public int addProduto(String descricao,String unidade,float valor,float estoque){
        int result = 0;
        
        try{
            
            
            insertProduto.setString(1, descricao);
            insertProduto.setString(2, unidade);
            insertProduto.setFloat(3, valor);
            insertProduto.setFloat(4, estoque);
            
            result = insertProduto.executeUpdate();
            
            
        }catch(SQLException sqle){
            Logger.getLogger(SetorQueries.class.getName())
                    .log(Level.SEVERE, null, sqle); 
        }
        
        return result;
        
    }
    
    // Método para alterar produto do banco de dados
    
    public int updateProduto(int codProd,String descricao,String unidade,float valor,float estoque){
        int result = 0;
       
        try {
            
            updateProduto = connection.prepareStatement("UPDATE produto SET"
                    + " descricao = ?,unidade = ?, valor = ?,"
                    + " estoque = ? WHERE codProd = "+codProd+"");
            updateProduto.setString(1,descricao);
            updateProduto.setString(2, unidade);
            updateProduto.setFloat(3, valor);
            updateProduto.setFloat(4, estoque);
            
            result = updateProduto.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    
    // Método para deletar produto do banco de dados.
    
    public int deleteProduto(int codProduto){
        int result = 0;
        
        try {
            
            deleteProduto.setInt(1, codProduto);
            result = deleteProduto.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha ao excluir, produto ja foi utilizado!","Produto utlizado",JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        
        return result;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Produto selectionProdutoId(int cod) throws SQLException{
        int result;
        
        selectProdutos.setInt(1, cod);
        ResultSet rs = selectProdutos.executeQuery();
        if(rs.next()){
                return new Produto(rs.getInt("codProd"),rs.getString("descricao"), rs.getString("unidade"),
                        rs.getFloat("valor"), rs.getFloat("estoque"));
            }
        return new Produto();
    }
    
    // Método para encerrar conexão com o banco de dados.
    
    public void close() {
        try {
            connection.close();
            insertProduto.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
    
}
