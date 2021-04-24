/*
 *  Classe para execução das queries e updates do banco de dados.
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
 * @author Rafael
 */
public class HistoricoSaidaQueries {

    private Connection connection;
    private PreparedStatement selectAllCodigos;
    private PreparedStatement deleteNota;
    private PreparedStatement deleteNotaFinal;
    private PreparedStatement selectProdutoHistorico;
    private PreparedStatement selectProdutoCompelto;
    private PreparedStatement selectTabelaVisualizar;

    public HistoricoSaidaQueries() {
        try {
            connection = MysqlConnect.connectDB();
            selectAllCodigos = connection.prepareStatement("SELECT cod FROM notasaida");
            deleteNota = connection.prepareStatement("DELETE from notaprodutosaida "
                    + "WHERE codNotaSaida = ?");
            deleteNotaFinal = connection.prepareStatement("DELETE from notasaida WHERE cod = ?");
            selectProdutoHistorico = connection.prepareStatement("SELECT *from notaprodutosaida WHERE codNotaSaida = ?");
            selectProdutoCompelto = connection.prepareStatement("SELECT *FROM produto WHERE codProd = ?");
            selectTabelaVisualizar = connection.prepareStatement("SELECT quantidade,unidade,descricao,valor,valor*quantidade "
                    + "FROM notaprodutosaida,produto WHERE codProdutoSaida = codProd AND codNotaSaida = ? AND codProd = ?");
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoSaidaQueries.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List< String> getAllcodigos() {
        List< String> results = null;

        try (ResultSet resultSet = selectAllCodigos.executeQuery()) {
            // executeQuery returns ResultSet containing matching entries
            results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(new String(
                        resultSet.getString("cod")));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
        return results;
    }

    public int deletaNota(int cod) throws SQLException {
        int result;
        deleteNota.setInt(1, cod);
        result = deleteNota.executeUpdate();
        return result;
    }

    public int deletaNotaFInal(int cod) throws SQLException {
        int result;
        deleteNotaFinal.setInt(1, cod);
        result = deleteNotaFinal.executeUpdate();
        return result;
    }

    public List< NotaProduto> getAllNotasProduto(int cod) throws SQLException {
        selectProdutoHistorico.setInt(1, cod);
        List< NotaProduto> results = null;

        try (ResultSet resultSet = selectProdutoHistorico.executeQuery()) {
            results = new ArrayList<>();

            while (resultSet.next()) {

                results.add(new NotaProduto(
                        resultSet.getFloat("quantidade"),
                        resultSet.getInt("codNotaSaida"),
                        resultSet.getInt("codProdutoSaida")
                ));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }

        return results;
    }

    public Produto getAllProdutoCompleto(int cod) throws SQLException {
        selectProdutoCompelto.setInt(1, cod);

        try (ResultSet resultSet = selectProdutoCompelto.executeQuery()) {

            if (resultSet.next()) {

                return new Produto(
                        resultSet.getInt("codProd"),
                        resultSet.getString("descricao"),
                        resultSet.getString("unidade"),
                        resultSet.getFloat("valor"),
                        resultSet.getFloat("estoque"));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }

        return new Produto();
    }

    public NotaHistoricoTabela getProdutoTabela(int codNota,int codProduto) throws SQLException {
        selectTabelaVisualizar.setInt(1, codNota);
        selectTabelaVisualizar.setInt(2, codProduto);

        try (ResultSet resultSet = selectTabelaVisualizar.executeQuery()) {

            if (resultSet.next()) {

                return new NotaHistoricoTabela(
                        resultSet.getInt("quantidade"),
                        resultSet.getString("unidade"),
                        resultSet.getString("descricao"),
                        resultSet.getFloat("valor"),
                        resultSet.getFloat("valor*quantidade"));
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }

        return new NotaHistoricoTabela();
    }

    public void close() {
        try {
            connection.close();
            selectAllCodigos.close();
            deleteNota.close();
            deleteNotaFinal.close();
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }
}
