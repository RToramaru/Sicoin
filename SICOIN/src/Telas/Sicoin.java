/*
 * Tela principal do sistema.
 */
package Telas;

import Classes.HistoricoEntradaQueries;
import Classes.HistoricoSaidaQueries;
import Classes.NotaEntradaQueries;
import Classes.NotaHistoricoTabela;
import Classes.NotaEntradaTabelaQueries;
import Classes.NotaProduto;
import Classes.NotaSaidaQueries;
import Classes.NotaSaidaTabelaQueries;
import Classes.Produto;
import Classes.ProdutoQueries;
import Classes.ResultSetTableModelUsuario;
import Classes.Setor;
import Classes.SetorQueries;
import Classes.Usuario;
import Classes.UsuarioQueries;
import Classes.ValidaCNPJ;
import Core.MysqlConnect;
import Tutoriais.AdicionarProdutoNotaEntrada;
import Tutoriais.AdicionarProdutoNotaSaida;
import Tutoriais.AlterarProduto;
import Tutoriais.AlterarSenhaDemo;
import Tutoriais.AlterarSetor;
import Tutoriais.CadastrarProduto;
import Tutoriais.CadastrarSetor;
import Tutoriais.CadastrarUsuario;
import Tutoriais.ComoCadastrarNota;
import Tutoriais.ComoCadastrarNotaSaida;
import Tutoriais.ExcluirNotaEntrada;
import Tutoriais.ExcluirNotaSaida;
import Tutoriais.ExcluirProduto;
import Tutoriais.ExcluirSetor;
import Tutoriais.ExcluirUsuario;
import Tutoriais.GerarRelatoriosPorCodigo;
import Tutoriais.GerarRelatoriosPorFiltros;
import Tutoriais.LimparProduto;
import Tutoriais.LimparSetor;
import Tutoriais.LimparUsuarios;
import Tutoriais.PesquisarProduto;
import Tutoriais.PesquisarSetor;
import Tutoriais.PesquisarUsuario;
import Tutoriais.RemoverProdutoNotaEntrada;
import Tutoriais.RemoverProdutoNotaSaida;
import Tutoriais.VerHistoricoNotaEntrada;
import Tutoriais.VerHistoricoNotaSaida;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Rafael
 */
public class Sicoin extends javax.swing.JFrame {

    /**
     * Creates new form Sicoin
     */
    int i = 0;
    Connection con;
    List<String> descricao_produtos = new ArrayList<String>();
    private SetorQueries setorQueries;
    private ProdutoQueries produtoQueries;
    private Setor setor;
    private Produto produto;
    Color color = new Color(57, 33, 89);
    ArrayList<NotaHistoricoTabela> tabelaEntrada = new ArrayList();
    ArrayList<NotaHistoricoTabela> tabelaSaida = new ArrayList();
    float totalEntrada = 0;
    float totalSaida = 0;
    private String usuarioLogin;
    public int cod;
    ArrayList<NotaProduto> notaProduto = new ArrayList();
    ArrayList<NotaHistoricoTabela> notaTabelaHistorico = new ArrayList();
    ArrayList<Setor> listaSetores = new ArrayList();

    public Sicoin(int tela, String usuarioConta) throws SQLException {
        initComponents();
        pnlPaineis.setSelectedComponent(pnlInicial);
        
        if(tela == 2){
            lblUsuarios.setVisible(false);
            lblSetores.setVisible(false);
            
        }
        
        //Iniciando a tela cheia.
        setExtendedState(MAXIMIZED_BOTH);
        produtoQueries = new ProdutoQueries();
        // conexão com o banco de dados.
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SiCoInB", "root", "");
        //Inicializando Variáveis
        comboBoxComplete();

        setorQueries = new SetorQueries();
        setor = new Setor();
        produto = new Produto();
        tfCodigoProduto.setBackground(color);
        //Ocultando Campos
        lblQualProduto.setVisible(false);
        tfUnidadeOutro.setVisible(false);
        // Montando Tabelas
        montarTabelaProduto();
        montarTabelaSetor();
        montarTabelaNotaEntrada();
        cbbRelatorioNatOp.setVisible(false);
        cbRelatorioNatOp.setVisible(false);
        
        // Campos Relatório
        desabCamposRelatorioPorCod();
        
        // Filtros do Relatório
        desabilitarFiltrosRelatorio();
        
        
        usuarioLogin = usuarioConta;
        setIcon();


    }

    public Sicoin() throws SQLException {
        initComponents();
        pnlPaineis.setSelectedComponent(pnlInicial);
        //Iniciando a tela cheia.
        setExtendedState(MAXIMIZED_BOTH);
        produtoQueries = new ProdutoQueries();
        // conexão com o banco de dados.
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/SiCoInB", "root", "");
        //Inicializando Variáveis
        comboBoxComplete();

        setorQueries = new SetorQueries();
        setor = new Setor();
        produto = new Produto();
        tfCodigoProduto.setBackground(color);
        //Ocultando Campos
        lblQualProduto.setVisible(false);
        tfUnidadeOutro.setVisible(false);
        // Montando Tabelas
        montarTabelaProduto();
        montarTabelaSetor();
        montarTabelaNotaEntrada();
        cbbRelatorioNatOp.setVisible(false);
        cbRelatorioNatOp.setVisible(false);
        
        // Campos Relatório
        desabCamposRelatorioPorCod();
        
        // Filtros do Relatório
        desabilitarFiltrosRelatorio();
        
        setIcon();

    }
    
     private void setIcon() {
        setIconImage ( Toolkit . getDefaultToolkit (). getImage ( getClass (). getResource ( "/imagens/icone.png" )));

    }
    
    public void desabCamposRelatorioPorCod(){
        lblRelatorioTipoDaNota.setVisible(false);
        cbbRelatorioTipoDaNota.setVisible(false);
        lblRelatorioCodNota.setVisible(false);
        tfRelatorioCodNota.setVisible(false);
    }
    
    public void habCamposRelatorioPorCod(){
        lblRelatorioTipoDaNota.setVisible(true);
        cbbRelatorioTipoDaNota.setVisible(true);
        lblRelatorioCodNota.setVisible(true);
        tfRelatorioCodNota.setVisible(true);
    }
    
    public void habilitarFiltrosRelatorio(){
        pnlRelatorioFiltros.setEnabled(true);
        cbRelatorioSetor.setEnabled(true);
        cbRelatorioProduto.setEnabled(true);
        cbRelatorioData.setEnabled(true);
        cbRelatorioNatOp.setEnabled(true);
        cbbRelatorioProduto.setEnabled(true);
        cbbRelatorioSetor.setEnabled(true);
        cbbRelatorioNatOp.setEnabled(true);
        cbbRelatorioTipoNota.setEnabled(true);
        lblRelatorioTipoNota.setEnabled(true);
        lblRelatorioDe.setEnabled(true);
        lblRelatorioAte.setEnabled(true);
        ftRelatorioDe.setEnabled(true);
        ftRelatorioAte.setEnabled(true);
    }
    
    public void desabilitarFiltrosRelatorio(){
        pnlRelatorioFiltros.setEnabled(false);
        cbRelatorioSetor.setEnabled(false);
        cbRelatorioProduto.setEnabled(false);
        cbRelatorioData.setEnabled(false);
        cbRelatorioNatOp.setEnabled(false);
        cbbRelatorioProduto.setEnabled(false);
        cbbRelatorioSetor.setEnabled(false);
        cbbRelatorioNatOp.setEnabled(false);
        cbbRelatorioTipoNota.setEnabled(false);
        lblRelatorioTipoNota.setEnabled(false);
        lblRelatorioDe.setEnabled(false);
        lblRelatorioAte.setEnabled(false);
        ftRelatorioDe.setEnabled(false);
        ftRelatorioAte.setEnabled(false);
    }

    public void comboBoxComplete() {
        AutoCompleteDecorator.decorate(cbRegEntradaDescricao);
        AutoCompleteDecorator.decorate(cbRegSaidaDescricao);
        AutoCompleteDecorator.decorate(cbRegEntradaDest);
        AutoCompleteDecorator.decorate(cbRegEntradaOrigem);
        AutoCompleteDecorator.decorate(cbRegSaidaNatOp);
        AutoCompleteDecorator.decorate(cbRegSaidaSetor);
        AutoCompleteDecorator.decorate(cbUsuAcesso);
        AutoCompleteDecorator.decorate(cbbTipoSetor);
        AutoCompleteDecorator.decorate(cbbUnidadeProduto);
    }

    public void montarComboBoxRelatorioSetorSaida() throws SQLException{
         List<String> stringEntrada = new ArrayList<String>();
        String queryEntrada = "SELECT * FROM setor WHERE tipo = 'S' OR tipo = 'E/S'";
        PreparedStatement psEntrada = con.prepareStatement(queryEntrada);
        ResultSet rsEntrada = psEntrada.executeQuery();

        while (rsEntrada.next()) {
            cbbRelatorioSetor.addItem(rsEntrada.getString(2));
        }

        
    }
    
    public void montarComboBoxRelatorioSetorEntrada() throws SQLException{
        List<String> stringEntrada = new ArrayList<String>();
        String queryEntrada = "SELECT * FROM setor WHERE tipo = 'E' OR tipo = 'E/S'";
        PreparedStatement psEntrada = con.prepareStatement(queryEntrada);
        ResultSet rsEntrada = psEntrada.executeQuery();

        while (rsEntrada.next()) {
            cbbRelatorioSetor.addItem(rsEntrada.getString(2));
        }

    }
    
    public void montarComboBoxEntrada() throws SQLException {
        List<String> stringEntrada = new ArrayList<String>();
        String queryEntrada = "SELECT * FROM setor WHERE tipo = 'E' OR tipo = 'E/S'";
        PreparedStatement psEntrada = con.prepareStatement(queryEntrada);
        ResultSet rsEntrada = psEntrada.executeQuery();

        while (rsEntrada.next()) {
            cbRegEntradaOrigem.addItem(rsEntrada.getString(2));
        }

        List<String> stringSaida = new ArrayList<String>();
        String querySaida = "SELECT * FROM setor WHERE tipo = 'S' OR tipo = 'E/S'";

        PreparedStatement psSaida = con.prepareStatement(querySaida);
        ResultSet rsSaida = psSaida.executeQuery();

        while (rsSaida.next()) {
            cbRegEntradaDest.addItem(rsSaida.getString(2));
        }

        psEntrada.close();
        psSaida.close();
    }

    public void montarComboBoxSaida() throws SQLException {
        List<String> stringSaida = new ArrayList<String>();
        String querySaida = "SELECT * FROM setor WHERE tipo = 'S' OR tipo = 'E/S'";

        PreparedStatement psSaida = con.prepareStatement(querySaida);
        ResultSet rsSaida = psSaida.executeQuery();

        while (rsSaida.next()) {
            cbRegSaidaSetor.addItem(rsSaida.getString(2));
        }

        psSaida.close();
    }

    public void montarTabelaVisualizarHistoricoEntrada() {
        // Centralizando Dados da tabela Produtos
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblHistoricoEntradaProduto.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblHistoricoEntradaProduto.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }
        // Colocando Fonte do cabeçalho da tabela produtos em negrito.
        JTableHeader tableHeaderProdutos = tblHistoricoEntradaProduto.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblHistoricoEntradaProduto.getModel();
        modelo.setNumRows(0);
        for (NotaHistoricoTabela notaTabela1 : notaTabelaHistorico) {
            modelo.addRow(new Object[]{
                notaTabela1.getQuantidade(),
                notaTabela1.getUnidade(),
                notaTabela1.getDescricao(),
                notaTabela1.getValor(),
                notaTabela1.getValorTotal()
            });
        }

    }

    public void montarTabelaVisualizarHistoricoSaida() {
        // Centralizando Dados da tabela Produtos
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblHistoricoSaidaProduto.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblHistoricoSaidaProduto.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }
        // Colocando Fonte do cabeçalho da tabela produtos em negrito.
        JTableHeader tableHeaderProdutos = tblHistoricoSaidaProduto.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblHistoricoSaidaProduto.getModel();
        modelo.setNumRows(0);
        for (NotaHistoricoTabela notaTabela1 : notaTabelaHistorico) {
            modelo.addRow(new Object[]{
                notaTabela1.getQuantidade(),
                notaTabela1.getUnidade(),
                notaTabela1.getDescricao(),
                notaTabela1.getValor(),
                notaTabela1.getValorTotal()
            });
        }

    }

    public void montarTabelaHistoricoEntrada() {
        // Centralizando Dados da tabela Produtos
        for (int i = 0; i < 1; i++) {
            ((DefaultTableCellRenderer) tblHistoricoEntrada.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblHistoricoEntrada.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }
        // Colocando Fonte do cabeçalho da tabela produtos em negrito.
        JTableHeader tableHeaderProdutos = tblHistoricoEntrada.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblHistoricoEntrada.getModel();
        modelo.setNumRows(0);

        HistoricoEntradaQueries historico = new HistoricoEntradaQueries();
        List<String> cod_nota = new ArrayList<>();
        cod_nota = (ArrayList<String>) historico.getAllcodigos();
        for (int i = 0; i < cod_nota.size(); i++) {
            modelo.addRow(new Object[]{cod_nota.get(i)});
        }

    }

    public void montarTabelaHistoricoSaida() {
        // Centralizando Dados da tabela Produtos
        for (int i = 0; i < 1; i++) {
            ((DefaultTableCellRenderer) tblHistoricoSaida.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblHistoricoSaida.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }
        // Colocando Fonte do cabeçalho da tabela produtos em negrito.
        JTableHeader tableHeaderProdutos = tblHistoricoSaida.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblHistoricoSaida.getModel();
        modelo.setNumRows(0);

        HistoricoSaidaQueries historico = new HistoricoSaidaQueries();
        List<String> cod_nota = new ArrayList<>();
        cod_nota = (ArrayList<String>) historico.getAllcodigos();
        for (int i = 0; i < cod_nota.size(); i++) {
            modelo.addRow(new Object[]{cod_nota.get(i)});
        }

    }

    //Montar Tabela Produtos.
    public void montarTabelaProduto() {
        // Centralizando Dados da tabela Produtos
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblProdutos.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblProdutos.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }
        // Colocando Fonte do cabeçalho da tabela produtos em negrito.
        JTableHeader tableHeaderProdutos = tblProdutos.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblProdutos.getModel();
        modelo.setNumRows(0);
        for (Produto p : produtoQueries.getAllProdutos()) {
            modelo.addRow(new Object[]{
                p.getCodProd(),
                p.getDescricao(),
                p.getUnidade(),
                p.getValor(),
                p.getEstoque()
            });

        }
    }

    public void montarTabelaNotaEntrada() {
        // Centralizando Dados da tabela Entrada
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblNotaEntrada.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblNotaEntrada.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }

        // Colocando Fonte do cabeçalho da tabela entrada em negrito.
        JTableHeader tableHeaderProdutos = tblProdutos.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblNotaEntrada.getModel();
        modelo.setNumRows(0);
        for (NotaHistoricoTabela tabelaEntrada1 : tabelaEntrada) {
            modelo.addRow(new Object[]{
                tabelaEntrada1.getQuantidade(),
                tabelaEntrada1.getUnidade(),
                tabelaEntrada1.getDescricao(),
                tabelaEntrada1.getValor(),
                tabelaEntrada1.getValorTotal()
            });
        }
    }

    public void montarTabelaNotaSaida() {
        // Centralizando Dados da tabela Entrada
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblNotaSaida.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblProdutos = new DefaultTableCellRenderer();
            centerRendererTblProdutos.setHorizontalAlignment(JLabel.CENTER);
            tblNotaSaida.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblProdutos);
        }

        // Colocando Fonte do cabeçalho da tabela entrada em negrito.
        JTableHeader tableHeaderProdutos = tblProdutos.getTableHeader();
        tableHeaderProdutos.setFont(tableHeaderProdutos.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblNotaSaida.getModel();
        modelo.setNumRows(0);
        for (NotaHistoricoTabela tabelaSaida1 : tabelaSaida) {
            modelo.addRow(new Object[]{
                tabelaSaida1.getQuantidade(),
                tabelaSaida1.getUnidade(),
                tabelaSaida1.getDescricao(),
                tabelaSaida1.getValor(),
                tabelaSaida1.getValorTotal()
            });
        }
    }

    public java.util.Date converteData(String minhaData) {
        java.util.Date data = null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dataFormatada = formato.parse(minhaData);
            return dataFormatada;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return data;
    }

    public void verificaCampos() {
        tfCodigoProduto.getText();
    }

    public String converterFormatoData(String minhadata) {
        String part1 = minhadata.substring(0, 2);
        String parte2 = minhadata.substring(3, 5);
        String parte3 = minhadata.substring(6);
        String novadata = parte3 + "-" + parte2 + "-" + part1;
        return novadata;
    }

    // Frame Setores.
    public void montarTabelaSetor() {
        // Centralizando Dados da tabela Setor
        for (int i = 0; i <= 4; i++) {
            ((DefaultTableCellRenderer) tblSetores.getTableHeader()
                    .getDefaultRenderer()).setHorizontalAlignment((int) JLabel.CENTER_ALIGNMENT);

            DefaultTableCellRenderer centerRendererTblSetores = new DefaultTableCellRenderer();
            centerRendererTblSetores.setHorizontalAlignment(JLabel.CENTER);
            tblSetores.getColumnModel().getColumn(i).setCellRenderer(centerRendererTblSetores);
        }

        // Colocando Fonte do cabeçalho da tabela setor em negrito.
        JTableHeader tableHeaderSetores = tblSetores.getTableHeader();
        tableHeaderSetores.setFont(tableHeaderSetores.getFont().deriveFont(Font.BOLD));

        // Adicionando Dados do banco na tabela.
        DefaultTableModel modelo = (DefaultTableModel) tblSetores.getModel();
        modelo.setNumRows(0);
        for (Setor s : setorQueries.getAllSetores()) {
            modelo.addRow(new Object[]{
                s.getCodSetor(),
                s.getNomSetor(),
                s.getResponsavel(),
                s.getTipo(),
                s.getCnpj()
            });
        }
    }

    // Limpar Campos Produto
    public void limparCamposProduto() {
        tfCodigoProduto.setText("");
        tfDescricaoProduto.setText("");
        ftValorProduto.setText("");
        ftEstoqueProduto.setText("");
        cbbUnidadeProduto.setSelectedIndex(0);
    }

    //Limpar Campos Setor
    public void limparCamposSetor() {
        cbbTipoSetor.setSelectedIndex(0);
        tfCodigo.setText("");
        tfResponsavel.setText("");
        tfNome.setText("");
        ftCNPJSetor.setText("");
        Color color = new Color(255, 255, 255);
        ftCNPJSetor.setEnabled(true);
        ftCNPJSetor.setBackground(color);
    }

    public void limparCamposUsuario() {
        tfUsuNom.setText("");
        tfUsuCpf.setText("");
        tfUsuEnd.setText("");
        tfUsuFone.setText("");
        tfUsuLogin.setText("");
        pfUsuSenha.setText("");
    }

    public void pesquisaSetor() throws SQLException {
        if (!"".equals(tfCodigo.getText())) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM setor WHERE codSetorO = ? ");
            ps.setString(1, tfCodigo.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tfCodigo.setText(rs.getString("codSetorO"));
                tfResponsavel.setText(rs.getString("nomSetor"));
                tfNome.setText(rs.getString("responsavel"));
            }

            for (int i = 0; i <= tblSetores.getRowCount() - 1; i++) {
                if (tfCodigo.getText().equals("" + tblSetores.getModel().getValueAt(i, 0))) {
                    tblSetores.addRowSelectionInterval(i, i);
                }
            }

            ps.close();
            rs.close();
        }
        if (!"".equals(tfResponsavel.getText())) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM setor WHERE nomSetor = ? ");
            ps.setString(1, tfResponsavel.getText().trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tfCodigo.setText(rs.getString("codSetorO"));
                tfResponsavel.setText(rs.getString("nomSetor"));
                tfNome.setText(rs.getString("responsavel"));
            }

            for (int i = 0; i <= tblSetores.getRowCount() - 1; i++) {
                if (tfResponsavel.getText().equals("" + tblSetores.getModel().getValueAt(i, 0))) {
                    tblSetores.addRowSelectionInterval(i, i);
                }
            }

            ps.close();
            rs.close();
        } else {
            JOptionPane.showMessageDialog(null, "Insira o que quer pesquisar no campo que quer pesquisar.");
        }
    }
    
    /*
      Métodos para gerar relatórios
    */
    
    public void gerarRelatorio1(){
        HashMap params = new HashMap<>();

        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        
        params.put("produto",produto_selecionado); 
        params.put("filtro_produto",produto_selecionado);
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio1.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
                            
    }
    
    public void gerarRelatorio2(){
        HashMap params = new HashMap<>();

        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        
        params.put("setor",setor_selecionado); 
        params.put("filtro_setor",setor_selecionado);
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio2.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio3(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate)); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio3.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio4(){
        HashMap params = new HashMap<>();
  
        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        
        params.put("setor",setor_selecionado); 
        params.put("produto",produto_selecionado);
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio4.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio5(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
         String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("setor",setor_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio5.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gerarRelatorio6(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
         String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("produto",produto_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio6.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio7(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
         String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("produto",produto_selecionado); 
            params.put("setor",setor_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio7.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio8(){
        HashMap params = new HashMap<>();

        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        
        params.put("produto",produto_selecionado); 
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio8.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            
            JasperViewer view = new JasperViewer(print,false);
           
                 
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio9(){
        HashMap params = new HashMap<>();

        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        
        params.put("destino",setor_selecionado); 
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio9.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio10(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate)); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio10.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio11(){
        HashMap params = new HashMap<>();

        String natOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
        
        params.put("natureza_op",natOp_selecionado); 
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio11.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio12(){
        HashMap params = new HashMap<>();

        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        
        params.put("produto",produto_selecionado); 
        params.put("destino",setor_selecionado); 
        
        try {
             JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio12.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio13(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("destino",setor_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio13.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio14(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("produto",produto_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio14.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio15(){
        HashMap params = new HashMap<>();

        String natOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        params.put("natureza_op",natOp_selecionado); 
        params.put("destino",setor_selecionado);
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio15.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio16(){
        HashMap params = new HashMap<>();

        String natOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        params.put("natureza_op",natOp_selecionado); 
        params.put("produto",produto_selecionado);
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio16.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio17(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String naturezaOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("natureza_operacao",naturezaOp_selecionado); 
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio17.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio18(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String naturezaOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("natureza_operacao",naturezaOp_selecionado);
            params.put("destino",setor_selecionado);
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio18.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio19(){
         HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String naturezaOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("natureza_operacao",naturezaOp_selecionado);
            params.put("produto",produto_selecionado);
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
             JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio19.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio20(){
        HashMap params = new HashMap<>();

        String natOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
          String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
         
        params.put("natureza_operacao",natOp_selecionado); 
        params.put("destino",setor_selecionado);
        params.put("produto",produto_selecionado);
        
        try {
             JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio20.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio21(){
        HashMap params = new HashMap<>();

        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("produto",produto_selecionado); 
            params.put("destino",setor_selecionado);
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio21.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio22(){
        HashMap params = new HashMap<>();
     
        String de = ftRelatorioDe.getText();
        String ate = ftRelatorioAte.getText();
        String naturezaOp_selecionado = (String) cbbRelatorioNatOp.getSelectedItem();
         String produto_selecionado = (String) cbbRelatorioProduto.getSelectedItem();
        String setor_selecionado = (String) cbbRelatorioSetor.getSelectedItem();
        de = converterFormatoData(de);
        ate = converterFormatoData(ate);
        
        
        try { 
            params.put("de",new SimpleDateFormat("yyyy-MM-dd").parse(de));
            params.put("ate",new SimpleDateFormat("yyyy-MM-dd").parse(ate));            params.put("natureza_operacao",naturezaOp_selecionado);
            params.put("produto",produto_selecionado);
            params.put("destino",setor_selecionado);
        } catch (ParseException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
             JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio22.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio23(){
        HashMap params = new HashMap<>();
       
      
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio23.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio24(){
        HashMap params = new HashMap<>();
     
      
        
        try {
             JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio24.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void gerarRelatorio25(){
        HashMap params = new HashMap<>();
        int codigo = Integer.parseInt(tfRelatorioCodNota.getText());
        
        params.put("codigo",codigo);
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio25.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorio26(){
        HashMap params = new HashMap<>();
        int codigo = Integer.parseInt(tfRelatorioCodNota.getText());
        
        params.put("codigo",codigo);
        
        try {
              JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/relatorios/relatorio26.jasper"));
            JasperPrint print = JasperFillManager.fillReport(
                    jr, params, con);
            JasperViewer view = new JasperViewer(print,false);
            view.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogo = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        pnlBotoes = new javax.swing.JPanel();
        lblSetores = new javax.swing.JLabel();
        lblProdutos = new javax.swing.JLabel();
        lblUsuarios = new javax.swing.JLabel();
        lblInformacoes = new javax.swing.JLabel();
        lblSair = new javax.swing.JLabel();
        lblEntrada = new javax.swing.JLabel();
        lblSaida = new javax.swing.JLabel();
        lblRelatorios = new javax.swing.JLabel();
        lblContaAcesso = new javax.swing.JLabel();
        pnlPaineis = new javax.swing.JTabbedPane();
        pnlSetores = new javax.swing.JPanel();
        btnCadastrarSetor = new javax.swing.JButton();
        btnAlterarSetor = new javax.swing.JButton();
        btnExcluirSetor = new javax.swing.JButton();
        lblTituloSetores = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbbTipoSetor = new javax.swing.JComboBox<>();
        lblSetCod = new javax.swing.JLabel();
        lblSetNom = new javax.swing.JLabel();
        lblSetRes = new javax.swing.JLabel();
        lblSetCnpj = new javax.swing.JLabel();
        tfCodigo = new TxtFieldSomenteNum();
        tfNome = new javax.swing.JTextField();
        tfResponsavel = new javax.swing.JTextField();
        lblTipSet = new javax.swing.JLabel();
        ftCNPJSetor = new javax.swing.JFormattedTextField();
        tfPesquisaSetor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblSetores = new javax.swing.JTable();
        lblAjudaSetores = new javax.swing.JLabel();
        btnLimparSetor = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        pnlProdutos = new javax.swing.JPanel();
        lblTituloProdutos = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tfCodigoProduto = new TxtFieldSomenteNum();
        tfDescricaoProduto = new javax.swing.JTextField();
        lblProCod = new javax.swing.JLabel();
        lblProDes = new javax.swing.JLabel();
        lblProUni = new javax.swing.JLabel();
        lblProVal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbbUnidadeProduto = new javax.swing.JComboBox<>();
        lblQualProduto = new javax.swing.JLabel();
        tfUnidadeOutro = new javax.swing.JTextField();
        ftEstoqueProduto = new javax.swing.JFormattedTextField();
        ftValorProduto = new javax.swing.JFormattedTextField();
        btnCadastrarProduto = new javax.swing.JButton();
        btnAlterarProduto = new javax.swing.JButton();
        btnExcluirProduto = new javax.swing.JButton();
        btnLimparProduto = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        tfPesquisaProduto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblAjudaProdutos = new javax.swing.JLabel();
        pnlRelatorio = new javax.swing.JPanel();
        lblTituloRelatorio = new javax.swing.JLabel();
        pnlRelatorioFiltros = new javax.swing.JPanel();
        cbRelatorioData = new javax.swing.JCheckBox();
        ftRelatorioDe = new javax.swing.JFormattedTextField();
        lblRelatorioDe = new javax.swing.JLabel();
        lblRelatorioAte = new javax.swing.JLabel();
        ftRelatorioAte = new javax.swing.JFormattedTextField();
        cbRelatorioNatOp = new javax.swing.JCheckBox();
        cbbRelatorioNatOp = new javax.swing.JComboBox<>();
        cbRelatorioSetor = new javax.swing.JCheckBox();
        cbbRelatorioProduto = new javax.swing.JComboBox<>();
        lblRelatorioTipoNota = new javax.swing.JLabel();
        cbbRelatorioTipoNota = new javax.swing.JComboBox<>();
        cbRelatorioProduto = new javax.swing.JCheckBox();
        cbbRelatorioSetor = new javax.swing.JComboBox<>();
        btnRelatorioGerar = new javax.swing.JButton();
        lblGerarRelatorioPor = new javax.swing.JLabel();
        cbOpcaoRelatorio = new javax.swing.JComboBox<>();
        lblRelatorioCodNota = new javax.swing.JLabel();
        tfRelatorioCodNota = new TxtFieldSomenteNum();
        lblRelatorioTipoDaNota = new javax.swing.JLabel();
        cbbRelatorioTipoDaNota = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        pnlConta = new javax.swing.JPanel();
        lblContaDados = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        lblContaNome = new javax.swing.JLabel();
        tfContaNome = new javax.swing.JTextField();
        lblContaFone = new javax.swing.JLabel();
        tfContaFone = new javax.swing.JTextField();
        lblContaCpf = new javax.swing.JLabel();
        tfContaCpf = new javax.swing.JTextField();
        lblContaEnd = new javax.swing.JLabel();
        lblContaUser = new javax.swing.JLabel();
        tfContaEnd = new javax.swing.JTextField();
        tfCOntaUser = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        lblContaSenha = new javax.swing.JLabel();
        lblContaConfirmaSenha = new javax.swing.JLabel();
        pfContaSenha = new javax.swing.JPasswordField();
        pfContaConfirmaSenha = new javax.swing.JPasswordField();
        btnContaAlterar = new javax.swing.JButton();
        btnAjudaAltSenha = new javax.swing.JLabel();
        pnlInformacoes = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        PnlHistoricoSaida = new javax.swing.JPanel();
        lblHistoricoSaida = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblHistoricoSaida = new javax.swing.JTable();
        btnHistoricoSaidaExcluir = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblHistoricoSaidaProduto = new javax.swing.JTable();
        lblTotalHistoricoSaida = new javax.swing.JLabel();
        tfHistoricoSaidaTotal = new javax.swing.JTextField();
        lblHistoricoSaidaPesquisar = new javax.swing.JLabel();
        tfHistoricoSaidaPesquisar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        PnlHistoricoEntrada = new javax.swing.JPanel();
        lblHistoricoEntrada = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHistoricoEntrada = new javax.swing.JTable();
        btnHistoricoEntradaExcluir = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblHistoricoEntradaProduto = new javax.swing.JTable();
        lblTotalHistoricoEntrada = new javax.swing.JLabel();
        tfHistoricoEntradaTotal = new javax.swing.JTextField();
        lblHistoricoEntradaPesquisar = new javax.swing.JLabel();
        tfHistoricoEntradaPesquisar = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        pnlRegSaida = new javax.swing.JPanel();
        lblRegistroSaida = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblNotaSaida = new javax.swing.JTable();
        lblRegSaidaTotal = new javax.swing.JLabel();
        tfRegSaidaTotal = new javax.swing.JTextField();
        btnRegSaidaSalvar = new javax.swing.JButton();
        lblRegSaidaHistorico = new javax.swing.JLabel();
        pnlDadosDaNota = new javax.swing.JPanel();
        tfRegSaidaCod = new TxtFieldSomenteNum();
        lblCodigoNotaSaida = new javax.swing.JLabel();
        cbRegSaidaNatOp = new javax.swing.JComboBox<>();
        lblNatOpNotaSaida = new javax.swing.JLabel();
        cbRegSaidaSetor = new javax.swing.JComboBox<>();
        lblSetorNotaSaida = new javax.swing.JLabel();
        tfRegSaidaCnpj = new javax.swing.JFormattedTextField();
        lblCNPJNotaSaida = new javax.swing.JLabel();
        lblDataEmissaoNotaSaida = new javax.swing.JLabel();
        lblRegSaidaDescricao = new javax.swing.JLabel();
        cbRegSaidaDescricao = new javax.swing.JComboBox<>();
        lblRegSaidaQuant = new javax.swing.JLabel();
        tfRegSaidaQuant = new javax.swing.JFormattedTextField();
        btnRegSaidaAdicionar = new javax.swing.JButton();
        btnRegSaidaDeletar = new javax.swing.JButton();
        tfRegSaidaDataEm = new com.toedter.calendar.JDateChooser();
        lblProdutosNotaSaida = new javax.swing.JLabel();
        btnAjudaRegistroSaida = new javax.swing.JLabel();
        pnlAjudaProdutos = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        brnAjudaVoltar = new javax.swing.JButton();
        lblComoCadastrarProduto = new javax.swing.JLabel();
        lblAlterarProduto = new javax.swing.JLabel();
        lblExcluirProduto = new javax.swing.JLabel();
        lblLimparCampos = new javax.swing.JLabel();
        lblCadastrarProdutoAjuda = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblComoPesquisarProduto = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        verDemoCadProd = new javax.swing.JLabel();
        verDemoAltProd = new javax.swing.JLabel();
        verDemoExcProd = new javax.swing.JLabel();
        verDemoLimparCamposProd = new javax.swing.JLabel();
        verDemoPesqProd = new javax.swing.JLabel();
        pnlAjudaSetores = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        brnAjudaVoltar1 = new javax.swing.JButton();
        lblComoCadastrarSetor = new javax.swing.JLabel();
        lblAlterarSetor = new javax.swing.JLabel();
        lblExcluirSetor = new javax.swing.JLabel();
        lblLimparCamposSetor = new javax.swing.JLabel();
        lblCadastrarSetorAjuda = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblComoPesquisarSetor = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        verDemoCadSetor = new javax.swing.JLabel();
        verDemoAltSetor = new javax.swing.JLabel();
        verDemoExcSetor = new javax.swing.JLabel();
        verDemoLimparCamposSet = new javax.swing.JLabel();
        verDemoPesqSetor = new javax.swing.JLabel();
        pnlAjudaRegistroEntrada = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        brnAjudaVoltarRegEntrada = new javax.swing.JButton();
        lblComoCadastrarNotaEnt = new javax.swing.JLabel();
        lblAlddNotaEntrada = new javax.swing.JLabel();
        lblExcluirNotaEnt = new javax.swing.JLabel();
        lblAcessarHisNotEnt = new javax.swing.JLabel();
        lblCadastrarNotaEnt = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblComoPesquisarNotEnt = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        verDemoCadNotaEnt = new javax.swing.JLabel();
        verDemoAddNotaEnt = new javax.swing.JLabel();
        verDemoExcProdNotaEnt = new javax.swing.JLabel();
        verDemoAcessarHistorico = new javax.swing.JLabel();
        verDemoExcluirNota = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        pnlAjudaRegistroSaida = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        brnAjudaVoltarRegistroSaida = new javax.swing.JButton();
        lblComoCadastrarNotaSaida = new javax.swing.JLabel();
        lblAdicionarProdutoNotaS = new javax.swing.JLabel();
        lblExcluirProdutoNS = new javax.swing.JLabel();
        lblHistoricoNotasSaida = new javax.swing.JLabel();
        lblCadastrarNotaSaidaAjuda = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lblExcluirNotaSCadastrada = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        verDemoCadNotaSaida = new javax.swing.JLabel();
        verDemoAdcProdutoNS = new javax.swing.JLabel();
        verDemoExcProdutoNS = new javax.swing.JLabel();
        verDemoAcessarHistoricoNS = new javax.swing.JLabel();
        verDemoExcNotaS = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        pnlAjudaAlterarSenha = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        brnAjudaVoltarAltSenha = new javax.swing.JButton();
        lblComoAlterarSenha = new javax.swing.JLabel();
        lblAlterarSenhaAjuda = new javax.swing.JLabel();
        verDemoAltSenha = new javax.swing.JLabel();
        pnlAjudaUusuarios = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        brnAjudaVoltarRegSaida2 = new javax.swing.JButton();
        lblComoCadastrarUsuario = new javax.swing.JLabel();
        lblCadastrarUusuarioAjuda = new javax.swing.JLabel();
        verDemoCadUsuario = new javax.swing.JLabel();
        lblComoExcluirUusuario = new javax.swing.JLabel();
        lblExcluirUsuarioAjuda = new javax.swing.JLabel();
        verDemoExcUsuario = new javax.swing.JLabel();
        lblLimparCamposUsuarios = new javax.swing.JLabel();
        lbLimparUsAjuda = new javax.swing.JLabel();
        verDemoLimparUsuarios = new javax.swing.JLabel();
        lblComoPesquisarUsuario = new javax.swing.JLabel();
        lblPesquisarUsAjuda = new javax.swing.JLabel();
        verDemoPesqUs = new javax.swing.JLabel();
        pnlAjudaRelatorios = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        brnAjudaVoltarRelatorios = new javax.swing.JButton();
        lblFuncionamentoRelatorios = new javax.swing.JLabel();
        lblRelatoriosAjuda = new javax.swing.JLabel();
        verDemoRelatoriosFiltros = new javax.swing.JLabel();
        lblRelatoriosFiltros = new javax.swing.JLabel();
        lblRelatoriosFiltrosAjuda = new javax.swing.JLabel();
        lblComoGerarRelatorioCodigo = new javax.swing.JLabel();
        lblRelatorioCodigoAjuda = new javax.swing.JLabel();
        verDemoRelatorioCodigo = new javax.swing.JLabel();
        pnlRegEntrada = new javax.swing.JPanel();
        lblRegEntrada = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNotaEntrada = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        tfRegEntradaTotal = new javax.swing.JTextField();
        btnRegEntradaSalvar = new javax.swing.JButton();
        lblRegEntradaHistorico = new javax.swing.JLabel();
        lblProdutosNotaEntrada = new javax.swing.JLabel();
        pnlDadosNotaEntrada = new javax.swing.JPanel();
        lblRegEntradaDataEmissao = new javax.swing.JLabel();
        lblRegEntradaDataRecebimento = new javax.swing.JLabel();
        tfRegEntradaCod = new javax.swing.JFormattedTextField();
        lblRegEntradaCod = new javax.swing.JLabel();
        lblRegEntradaOrin = new javax.swing.JLabel();
        lblRegEntradaDest = new javax.swing.JLabel();
        cbRegEntradaDest = new javax.swing.JComboBox<>();
        cbRegEntradaOrigem = new javax.swing.JComboBox<>();
        lblRegEntradaDescricao = new javax.swing.JLabel();
        cbRegEntradaDescricao = new javax.swing.JComboBox<>();
        btnRegEntradaDeletar = new javax.swing.JButton();
        tfRegEntradaQuant = new javax.swing.JFormattedTextField();
        lblRegEntradaQuant = new javax.swing.JLabel();
        btnRegEntradaAdicionar = new javax.swing.JButton();
        tfRegEntradaDataEmissao = new com.toedter.calendar.JDateChooser();
        tfRegEntradaDataRecebimento = new com.toedter.calendar.JDateChooser();
        btnAjudaRegistroEntrada = new javax.swing.JLabel();
        pnlInicial = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        pnlUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnAddUsu = new javax.swing.JButton();
        btnUsuExc = new javax.swing.JButton();
        lblTituloUsuarios = new javax.swing.JLabel();
        tfPesquisaUsuario = new javax.swing.JTextField();
        lblPesquisar = new javax.swing.JLabel();
        btnLimparUsuario = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblUsuNom = new javax.swing.JLabel();
        lblUsuCpf = new javax.swing.JLabel();
        lblUsuEnd = new javax.swing.JLabel();
        tfUsuEnd = new javax.swing.JTextField();
        tfUsuCpf = new javax.swing.JFormattedTextField();
        tfUsuNom = new javax.swing.JTextField();
        lblUsuAcesso = new javax.swing.JLabel();
        cbUsuAcesso = new javax.swing.JComboBox<>();
        pfUsuSenha = new javax.swing.JPasswordField();
        lblUsuSenha = new javax.swing.JLabel();
        lblUsuUsuario = new javax.swing.JLabel();
        tfUsuLogin = new javax.swing.JTextField();
        lblUsuTel = new javax.swing.JLabel();
        tfUsuFone = new javax.swing.JFormattedTextField();
        lblAjudaUsuarios = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sicoin");
        setIconImages(null);

        pnlLogo.setBackground(new java.awt.Color(57, 33, 89));
        pnlLogo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logotipo.png"))); // NOI18N

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlBotoes.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlBotoes.setPreferredSize(new java.awt.Dimension(260, 570));

        lblSetores.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblSetores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSetores.setText("Setores");
        lblSetores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSetoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSetoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSetoresMouseExited(evt);
            }
        });

        lblProdutos.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblProdutos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProdutos.setText("Produtos");
        lblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblProdutosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblProdutosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblProdutosMouseExited(evt);
            }
        });

        lblUsuarios.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarios.setText("Usuários");
        lblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblUsuariosMouseExited(evt);
            }
        });

        lblInformacoes.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblInformacoes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformacoes.setText("Informações");
        lblInformacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInformacoesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInformacoesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInformacoesMouseExited(evt);
            }
        });

        lblSair.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblSair.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSair.setText("Sair");
        lblSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSairMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSairMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSairMouseExited(evt);
            }
        });

        lblEntrada.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblEntrada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEntrada.setText("Registro de Entrada ");
        lblEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEntradaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEntradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEntradaMouseExited(evt);
            }
        });

        lblSaida.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSaida.setText("    Registro de Saída");
        lblSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSaidaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSaidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSaidaMouseExited(evt);
            }
        });

        lblRelatorios.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblRelatorios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRelatorios.setText("Relatórios");
        lblRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRelatoriosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRelatoriosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRelatoriosMouseExited(evt);
            }
        });

        lblContaAcesso.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblContaAcesso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContaAcesso.setText("Conta");
        lblContaAcesso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblContaAcessoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblContaAcessoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblContaAcessoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlBotoesLayout = new javax.swing.GroupLayout(pnlBotoes);
        pnlBotoes.setLayout(pnlBotoesLayout);
        pnlBotoesLayout.setHorizontalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(lblContaAcesso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                        .addComponent(lblSetores, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProdutos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSair, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblInformacoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblRelatorios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlBotoesLayout.setVerticalGroup(
            pnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBotoesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblContaAcesso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblSaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblSetores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblProdutos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlPaineis.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        pnlPaineis.setToolTipText("");
        pnlPaineis.setEnabled(false);
        pnlPaineis.setFont(new java.awt.Font("Tahoma", 0, 1)); // NOI18N
        pnlPaineis.setPreferredSize(new java.awt.Dimension(950, 570));

        pnlSetores.setPreferredSize(new java.awt.Dimension(570, 950));

        btnCadastrarSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCadastrarSetor.setText("Cadastrar");
        btnCadastrarSetor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCadastrarSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarSetorActionPerformed(evt);
            }
        });

        btnAlterarSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAlterarSetor.setText("Alterar");
        btnAlterarSetor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAlterarSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSetorActionPerformed(evt);
            }
        });

        btnExcluirSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnExcluirSetor.setText("Excluir");
        btnExcluirSetor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcluirSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirSetorActionPerformed(evt);
            }
        });

        lblTituloSetores.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloSetores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloSetores.setText("Setores");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Setor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12))); // NOI18N

        cbbTipoSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbbTipoSetor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entrada", "Saída", "Entrada/Saída" }));

        lblSetCod.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetCod.setText("Código:");

        lblSetNom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetNom.setText("*Nome:");

        lblSetRes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetRes.setText("*Responsável:");

        lblSetCnpj.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetCnpj.setText("CNPJ:");

        tfCodigo.setEditable(false);
        tfCodigo.setBackground(new java.awt.Color(57, 33, 89));
        tfCodigo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCodigo.setForeground(new java.awt.Color(255, 255, 255));
        tfCodigo.setDisabledTextColor(new java.awt.Color(204, 204, 0));

        tfNome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNomeActionPerformed(evt);
            }
        });

        tfResponsavel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfResponsavelActionPerformed(evt);
            }
        });

        lblTipSet.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTipSet.setText("*Tipo:");

        try {
            ftCNPJSetor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###.####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftCNPJSetor.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        ftCNPJSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSetCod)
                    .addComponent(lblSetRes)
                    .addComponent(lblSetCnpj)
                    .addComponent(lblTipSet, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetNom))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(cbbTipoSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(20, 20, 20))
                        .addComponent(tfCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ftCNPJSetor, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                    .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNome))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblSetCod, lblSetNom, lblSetRes});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ftCNPJSetor, tfNome});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSetCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tfCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ftCNPJSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetCnpj))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSetNom)
                    .addComponent(tfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSetRes))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipSet, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTipoSetor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblSetCod, lblSetNom, lblSetRes});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftCNPJSetor, tfCodigo, tfNome, tfResponsavel});

        tfPesquisaSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfPesquisaSetor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPesquisaSetorKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Pesquisar (Nome):");

        tblSetores.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblSetores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Responsável", "Tipo", "CNPJ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSetores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSetoresMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblSetores);

        lblAjudaSetores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        lblAjudaSetores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAjudaSetoresMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAjudaSetoresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAjudaSetoresMouseExited(evt);
            }
        });

        btnLimparSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLimparSetor.setText("Limpar");
        btnLimparSetor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimparSetor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparSetorActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel29.setText("Setores cadastrados:");

        javax.swing.GroupLayout pnlSetoresLayout = new javax.swing.GroupLayout(pnlSetores);
        pnlSetores.setLayout(pnlSetoresLayout);
        pnlSetoresLayout.setHorizontalGroup(
            pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSetoresLayout.createSequentialGroup()
                .addGroup(pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlSetoresLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlSetoresLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSetoresLayout.createSequentialGroup()
                                .addComponent(btnCadastrarSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAlterarSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnExcluirSetor)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimparSetor))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                            .addGroup(pnlSetoresLayout.createSequentialGroup()
                                .addComponent(tfPesquisaSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                                .addComponent(lblAjudaSetores))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlSetoresLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
            .addGroup(pnlSetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(lblTituloSetores))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSetoresLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAlterarSetor, btnCadastrarSetor, btnExcluirSetor, btnLimparSetor});

        pnlSetoresLayout.setVerticalGroup(
            pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSetoresLayout.createSequentialGroup()
                .addComponent(lblTituloSetores, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExcluirSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterarSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrarSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pnlSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlSetoresLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(3, 3, 3)
                        .addComponent(tfPesquisaSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSetoresLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAjudaSetores)
                        .addGap(10, 10, 10))))
        );

        pnlSetoresLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAlterarSetor, btnCadastrarSetor, btnExcluirSetor, btnLimparSetor});

        pnlPaineis.addTab("", pnlSetores);

        pnlProdutos.setPreferredSize(new java.awt.Dimension(200, 400));

        lblTituloProdutos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloProdutos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloProdutos.setText("Produtos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do produto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        tfCodigoProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfCodigoProduto.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        tfCodigoProduto.setEnabled(false);
        tfCodigoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCodigoProdutoActionPerformed(evt);
            }
        });

        tfDescricaoProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblProCod.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblProCod.setText("Código:");

        lblProDes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblProDes.setText("Descrição:");

        lblProUni.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblProUni.setText("Unidade:");

        lblProVal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblProVal.setText("Valor:");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setText("Estoque:");

        cbbUnidadeProduto.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cbbUnidadeProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unid", "Kg", "Lt", "Pct", "Arroba", "Pé", "Band", "Ton", "Caix", "Outro" }));
        cbbUnidadeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbUnidadeProdutoActionPerformed(evt);
            }
        });

        lblQualProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblQualProduto.setText("Qual ?");

        tfUnidadeOutro.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        ftEstoqueProduto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.00"))));
        ftEstoqueProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ftEstoqueProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftEstoqueProdutoActionPerformed(evt);
            }
        });

        ftValorProduto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.00"))));
        ftValorProduto.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        ftValorProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ftValorProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftValorProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblProCod)
                                .addGap(59, 59, 59))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftEstoqueProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblProUni)
                                .addGap(51, 51, 51))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblProVal)
                                .addGap(73, 73, 73))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblProDes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbbUnidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(lblQualProduto)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfUnidadeOutro))
                            .addComponent(tfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ftValorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProCod)
                    .addComponent(tfCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ftEstoqueProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProVal)
                    .addComponent(ftValorProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProDes)
                    .addComponent(tfDescricaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblProUni)
                        .addComponent(cbbUnidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblQualProduto)
                    .addComponent(tfUnidadeOutro))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ftEstoqueProduto, ftValorProduto, tfCodigoProduto, tfDescricaoProduto});

        btnCadastrarProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnCadastrarProduto.setText("Cadastrar");
        btnCadastrarProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarProdutoActionPerformed(evt);
            }
        });

        btnAlterarProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAlterarProduto.setText("Alterar");
        btnAlterarProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAlterarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarProdutoActionPerformed(evt);
            }
        });

        btnExcluirProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnExcluirProduto.setText("Excluir");
        btnExcluirProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnExcluirProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirProdutoActionPerformed(evt);
            }
        });

        btnLimparProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLimparProduto.setText("Limpar");
        btnLimparProduto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimparProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparProdutoActionPerformed(evt);
            }
        });

        tblProdutos.setAutoCreateRowSorter(true);
        tblProdutos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Descrição", "Unidade", "Estoque", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProdutos.getTableHeader().setResizingAllowed(false);
        tblProdutos.getTableHeader().setReorderingAllowed(false);
        tblProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProdutosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProdutos);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Produtos cadastrados:");

        tfPesquisaProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfPesquisaProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPesquisaProdutoActionPerformed(evt);
            }
        });
        tfPesquisaProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPesquisaProdutoKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setText("Pesquisar (Descrição):");

        lblAjudaProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        lblAjudaProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAjudaProdutosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAjudaProdutosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAjudaProdutosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlProdutosLayout = new javax.swing.GroupLayout(pnlProdutos);
        pnlProdutos.setLayout(pnlProdutosLayout);
        pnlProdutosLayout.setHorizontalGroup(
            pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProdutosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProdutosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCadastrarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btnLimparProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(pnlProdutosLayout.createSequentialGroup()
                        .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlProdutosLayout.createSequentialGroup()
                                .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(lblTituloProdutos))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(pnlProdutosLayout.createSequentialGroup()
                        .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAjudaProdutos)
                        .addContainerGap())))
        );
        pnlProdutosLayout.setVerticalGroup(
            pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProdutosLayout.createSequentialGroup()
                .addComponent(lblTituloProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlProdutosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCadastrarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAlterarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExcluirProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimparProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPesquisaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlProdutosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAjudaProdutos)))
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlProdutos);

        lblTituloRelatorio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloRelatorio.setText("Relatórios");

        pnlRelatorioFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filtros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        pnlRelatorioFiltros.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        cbRelatorioData.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRelatorioData.setText("Data");

        try {
            ftRelatorioDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        lblRelatorioDe.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRelatorioDe.setText("De:");

        lblRelatorioAte.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRelatorioAte.setText("Até:");

        try {
            ftRelatorioAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        cbRelatorioNatOp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRelatorioNatOp.setText("Natureza da Operação");

        cbbRelatorioNatOp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbbRelatorioNatOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Remessa para consumo", "Venda", "Doação", "Transferência" }));

        cbRelatorioSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRelatorioSetor.setText("Setor");

        cbbRelatorioProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbbRelatorioProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblRelatorioTipoNota.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRelatorioTipoNota.setText("Tipo da(s) nota(s):");

        cbbRelatorioTipoNota.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbbRelatorioTipoNota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Entrada", "Saída" }));
        cbbRelatorioTipoNota.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbRelatorioTipoNotaItemStateChanged(evt);
            }
        });

        cbRelatorioProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRelatorioProduto.setText("Produto");

        cbbRelatorioSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlRelatorioFiltrosLayout = new javax.swing.GroupLayout(pnlRelatorioFiltros);
        pnlRelatorioFiltros.setLayout(pnlRelatorioFiltrosLayout);
        pnlRelatorioFiltrosLayout.setHorizontalGroup(
            pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRelatorioFiltrosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRelatorioTipoNota)
                    .addComponent(cbRelatorioSetor)
                    .addComponent(cbRelatorioProduto)
                    .addComponent(cbRelatorioData)
                    .addComponent(cbRelatorioNatOp))
                .addGap(31, 31, 31)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbRelatorioNatOp, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlRelatorioFiltrosLayout.createSequentialGroup()
                        .addComponent(lblRelatorioDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ftRelatorioDe, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(lblRelatorioAte)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ftRelatorioAte, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbbRelatorioTipoNota, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbbRelatorioSetor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbbRelatorioProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(308, Short.MAX_VALUE))
        );
        pnlRelatorioFiltrosLayout.setVerticalGroup(
            pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRelatorioFiltrosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRelatorioTipoNota)
                    .addComponent(cbbRelatorioTipoNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbbRelatorioSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbRelatorioSetor))
                .addGap(10, 10, 10)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRelatorioProduto)
                    .addComponent(cbbRelatorioProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRelatorioData)
                    .addComponent(lblRelatorioDe)
                    .addComponent(ftRelatorioDe, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRelatorioAte)
                    .addComponent(ftRelatorioAte, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlRelatorioFiltrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRelatorioNatOp)
                    .addComponent(cbbRelatorioNatOp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRelatorioFiltrosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbRelatorioNatOp, cbbRelatorioProduto});

        btnRelatorioGerar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        btnRelatorioGerar.setText("Gerar Relatório");
        btnRelatorioGerar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRelatorioGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelatorioGerarActionPerformed(evt);
            }
        });

        lblGerarRelatorioPor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblGerarRelatorioPor.setText("Gerar relatório por:");

        cbOpcaoRelatorio.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        cbOpcaoRelatorio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Filtro(s)", "Código da nota" }));
        cbOpcaoRelatorio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbOpcaoRelatorioItemStateChanged(evt);
            }
        });

        lblRelatorioCodNota.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        lblRelatorioCodNota.setText("Código da nota: ");

        lblRelatorioTipoDaNota.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        lblRelatorioTipoDaNota.setText("Tipo da nota:");

        cbbRelatorioTipoDaNota.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        cbbRelatorioTipoDaNota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Entrada", "Saída" }));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel34MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel34MouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlRelatorioLayout = new javax.swing.GroupLayout(pnlRelatorio);
        pnlRelatorio.setLayout(pnlRelatorioLayout);
        pnlRelatorioLayout.setHorizontalGroup(
            pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRelatorioLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRelatorioLayout.createSequentialGroup()
                        .addGroup(pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlRelatorioFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRelatorioLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnRelatorioGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)))
                        .addGap(10, 10, 10))
                    .addGroup(pnlRelatorioLayout.createSequentialGroup()
                        .addGroup(pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlRelatorioLayout.createSequentialGroup()
                                .addComponent(lblGerarRelatorioPor)
                                .addGap(36, 36, 36)
                                .addComponent(cbOpcaoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(lblRelatorioTipoDaNota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbbRelatorioTipoDaNota, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblRelatorioCodNota)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfRelatorioCodNota, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblTituloRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRelatorioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(10, 10, 10))
        );
        pnlRelatorioLayout.setVerticalGroup(
            pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRelatorioLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblTituloRelatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRelatorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRelatorioCodNota)
                    .addComponent(lblRelatorioTipoDaNota)
                    .addComponent(cbbRelatorioTipoDaNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbOpcaoRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGerarRelatorioPor)
                    .addComponent(tfRelatorioCodNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRelatorioFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRelatorioGerar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addContainerGap())
        );

        pnlPaineis.addTab("", pnlRelatorio);

        lblContaDados.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblContaDados.setText("Conta");

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da conta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 13))); // NOI18N

        lblContaNome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaNome.setText("Nome:");

        tfContaNome.setEditable(false);
        tfContaNome.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblContaFone.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaFone.setText("Telefone:");

        tfContaFone.setEditable(false);
        tfContaFone.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblContaCpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaCpf.setText("CPF:");

        tfContaCpf.setEditable(false);
        tfContaCpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblContaEnd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaEnd.setText("Endereço:");

        lblContaUser.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaUser.setText("Usuário:");

        tfContaEnd.setEditable(false);
        tfContaEnd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tfCOntaUser.setEditable(false);
        tfCOntaUser.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContaNome)
                    .addComponent(lblContaFone)
                    .addComponent(lblContaCpf))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfContaNome, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                    .addComponent(tfContaCpf)
                    .addComponent(tfContaFone))
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContaEnd)
                    .addComponent(lblContaUser))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfContaEnd)
                    .addComponent(tfCOntaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(tfContaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(tfContaFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfContaEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContaEnd))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContaUser)
                            .addComponent(tfCOntaUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(lblContaNome)
                        .addGap(13, 13, 13)
                        .addComponent(lblContaFone)))
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContaCpf)
                    .addComponent(tfContaCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Alterar senha", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 13))); // NOI18N

        lblContaSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaSenha.setText("Nova senha:");

        lblContaConfirmaSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblContaConfirmaSenha.setText("Confimar Senha:");

        pfContaSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        pfContaConfirmaSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        btnContaAlterar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnContaAlterar.setText("Alterar senha");
        btnContaAlterar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnContaAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContaAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btnContaAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addComponent(lblContaSenha)
                                .addGap(45, 45, 45)
                                .addComponent(pfContaSenha))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addComponent(lblContaConfirmaSenha)
                                .addGap(18, 18, 18)
                                .addComponent(pfContaConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(490, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContaSenha)
                    .addComponent(pfContaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContaConfirmaSenha)
                    .addComponent(pfContaConfirmaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnContaAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnAjudaAltSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        btnAjudaAltSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAjudaAltSenhaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAjudaAltSenhaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAjudaAltSenhaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlContaLayout = new javax.swing.GroupLayout(pnlConta);
        pnlConta.setLayout(pnlContaLayout);
        pnlContaLayout.setHorizontalGroup(
            pnlContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaLayout.createSequentialGroup()
                .addGroup(pnlContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAjudaAltSenha)))
                .addGap(10, 10, 10))
            .addGroup(pnlContaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContaLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(pnlContaLayout.createSequentialGroup()
                        .addComponent(lblContaDados)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlContaLayout.setVerticalGroup(
            pnlContaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblContaDados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAjudaAltSenha)
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlConta);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Informações");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Descrição:");

        jLabel11.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel11.setText("Sicoin: Sistema de controle de produção e distribuição de insumos ");

        jLabel13.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel13.setText("Sistema desenvolvido com o objetivo de suporte para gerenciar a distribuição de insumos que passam pela central de vendas ");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setText("Desenvolvedores:");

        jLabel15.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel15.setText("Rafael Almeida Soares");

        jLabel16.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel16.setText("Filipi Barbosa Alves");

        jLabel25.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel25.setText("Professor Orientador:");

        jLabel26.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel26.setText("Leonardo Humberto Guimarães Silva");

        javax.swing.GroupLayout pnlInformacoesLayout = new javax.swing.GroupLayout(pnlInformacoes);
        pnlInformacoes.setLayout(pnlInformacoesLayout);
        pnlInformacoesLayout.setHorizontalGroup(
            pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlInformacoesLayout.setVerticalGroup(
            pnlInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInformacoesLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(10, 10, 10)
                .addComponent(jLabel11)
                .addGap(10, 10, 10)
                .addComponent(jLabel13)
                .addGap(20, 20, 20)
                .addComponent(jLabel14)
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addGap(10, 10, 10)
                .addComponent(jLabel16)
                .addGap(20, 20, 20)
                .addComponent(jLabel25)
                .addGap(20, 20, 20)
                .addComponent(jLabel26)
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlInformacoes);

        lblHistoricoSaida.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHistoricoSaida.setText("Histórico de Notas de Saída");

        tblHistoricoSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblHistoricoSaida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistoricoSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoricoSaidaMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblHistoricoSaida);

        btnHistoricoSaidaExcluir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnHistoricoSaidaExcluir.setText("Excluir");
        btnHistoricoSaidaExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoSaidaExcluirActionPerformed(evt);
            }
        });

        tblHistoricoSaidaProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblHistoricoSaidaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantidade", "Unidade", "Descrição", "Valor Unidade", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tblHistoricoSaidaProduto);

        lblTotalHistoricoSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalHistoricoSaida.setText("Total:");

        tfHistoricoSaidaTotal.setEditable(false);
        tfHistoricoSaidaTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblHistoricoSaidaPesquisar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblHistoricoSaidaPesquisar.setText("Pesquisar:");

        tfHistoricoSaidaPesquisar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfHistoricoSaidaPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfHistoricoSaidaPesquisarKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setText("Voltar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlHistoricoSaidaLayout = new javax.swing.GroupLayout(PnlHistoricoSaida);
        PnlHistoricoSaida.setLayout(PnlHistoricoSaidaLayout);
        PnlHistoricoSaidaLayout.setHorizontalGroup(
            PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                        .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfHistoricoSaidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHistoricoSaidaPesquisar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                            .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHistoricoSaidaExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotalHistoricoSaida)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfHistoricoSaidaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblHistoricoSaida))
                .addGap(10, 10, 10))
        );
        PnlHistoricoSaidaLayout.setVerticalGroup(
            PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHistoricoSaida)
                .addGap(10, 10, 10)
                .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(lblHistoricoSaidaPesquisar))
                    .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfHistoricoSaidaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(btnHistoricoSaidaExcluir)))
                    .addGroup(PnlHistoricoSaidaLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(PnlHistoricoSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfHistoricoSaidaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotalHistoricoSaida))))
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("tab9", PnlHistoricoSaida);

        lblHistoricoEntrada.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblHistoricoEntrada.setText("Histórico de Notas de Entrada");

        tblHistoricoEntrada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblHistoricoEntrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHistoricoEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHistoricoEntradaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblHistoricoEntrada);

        btnHistoricoEntradaExcluir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnHistoricoEntradaExcluir.setText("Excluir");
        btnHistoricoEntradaExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoEntradaExcluirActionPerformed(evt);
            }
        });

        tblHistoricoEntradaProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblHistoricoEntradaProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantidade", "Unidade", "Descrição", "Valor Unidade", "Valor Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tblHistoricoEntradaProduto);

        lblTotalHistoricoEntrada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalHistoricoEntrada.setText("Total:");

        tfHistoricoEntradaTotal.setEditable(false);
        tfHistoricoEntradaTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblHistoricoEntradaPesquisar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblHistoricoEntradaPesquisar.setText("Pesquisar:");

        tfHistoricoEntradaPesquisar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfHistoricoEntradaPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfHistoricoEntradaPesquisarKeyPressed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setText("Voltar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PnlHistoricoEntradaLayout = new javax.swing.GroupLayout(PnlHistoricoEntrada);
        PnlHistoricoEntrada.setLayout(PnlHistoricoEntradaLayout);
        PnlHistoricoEntradaLayout.setHorizontalGroup(
            PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlHistoricoEntradaLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHistoricoEntrada)
                    .addGroup(PnlHistoricoEntradaLayout.createSequentialGroup()
                        .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfHistoricoEntradaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHistoricoEntradaPesquisar)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PnlHistoricoEntradaLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE))
                            .addGroup(PnlHistoricoEntradaLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHistoricoEntradaExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotalHistoricoEntrada)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfHistoricoEntradaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
        );
        PnlHistoricoEntradaLayout.setVerticalGroup(
            PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlHistoricoEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHistoricoEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(lblHistoricoEntradaPesquisar)
                .addGap(14, 14, 14)
                .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalHistoricoEntrada)
                        .addComponent(tfHistoricoEntradaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PnlHistoricoEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfHistoricoEntradaPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2)
                        .addComponent(btnHistoricoEntradaExcluir)))
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("tab9", PnlHistoricoEntrada);

        lblRegistroSaida.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRegistroSaida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRegistroSaida.setText("Registro de saída");

        tblNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblNotaSaida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantidade", "Unidade", "Descrição", "Valor Unidade", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNotaSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNotaSaidaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblNotaSaida);
        if (tblNotaSaida.getColumnModel().getColumnCount() > 0) {
            tblNotaSaida.getColumnModel().getColumn(0).setHeaderValue("Quantidade");
            tblNotaSaida.getColumnModel().getColumn(1).setHeaderValue("Unidade");
            tblNotaSaida.getColumnModel().getColumn(2).setHeaderValue("Descrição");
            tblNotaSaida.getColumnModel().getColumn(3).setHeaderValue("Valor Unidade");
            tblNotaSaida.getColumnModel().getColumn(4).setHeaderValue("Valor Total");
        }

        lblRegSaidaTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegSaidaTotal.setText("Total:");

        tfRegSaidaTotal.setEditable(false);
        tfRegSaidaTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        btnRegSaidaSalvar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegSaidaSalvar.setText("Cadastrar");
        btnRegSaidaSalvar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegSaidaSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegSaidaSalvarActionPerformed(evt);
            }
        });

        lblRegSaidaHistorico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegSaidaHistorico.setText("Histórico de registros de saída");
        lblRegSaidaHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegSaidaHistoricoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRegSaidaHistoricoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRegSaidaHistoricoMouseExited(evt);
            }
        });

        pnlDadosDaNota.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da nota", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 13))); // NOI18N

        tfRegSaidaCod.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfRegSaidaCod.setPreferredSize(new java.awt.Dimension(4, 20));

        lblCodigoNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCodigoNotaSaida.setText("* Código:");

        cbRegSaidaNatOp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRegSaidaNatOp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Remessa para consumo", "Venda", "Doação", "Transferência" }));

        lblNatOpNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNatOpNotaSaida.setText("* Natureza da operação:");

        cbRegSaidaSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblSetorNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSetorNotaSaida.setText("* Setor/Razão Social:");

        try {
            tfRegSaidaCnpj.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###.####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfRegSaidaCnpj.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblCNPJNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCNPJNotaSaida.setText("CNPJ:");

        lblDataEmissaoNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblDataEmissaoNotaSaida.setText("*Data da emissão:");

        lblRegSaidaDescricao.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegSaidaDescricao.setText("* Produto:");

        cbRegSaidaDescricao.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblRegSaidaQuant.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegSaidaQuant.setText("Quantidade:");

        tfRegSaidaQuant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.###"))));
        tfRegSaidaQuant.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        btnRegSaidaAdicionar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegSaidaAdicionar.setText("Adicionar");
        btnRegSaidaAdicionar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegSaidaAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegSaidaAdicionarActionPerformed(evt);
            }
        });

        btnRegSaidaDeletar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegSaidaDeletar.setText("Remover");
        btnRegSaidaDeletar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegSaidaDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegSaidaDeletarActionPerformed(evt);
            }
        });

        tfRegSaidaDataEm.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlDadosDaNotaLayout = new javax.swing.GroupLayout(pnlDadosDaNota);
        pnlDadosDaNota.setLayout(pnlDadosDaNotaLayout);
        pnlDadosDaNotaLayout.setHorizontalGroup(
            pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCNPJNotaSaida, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDadosDaNotaLayout.createSequentialGroup()
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigoNotaSaida)
                            .addComponent(lblNatOpNotaSaida)
                            .addComponent(lblSetorNotaSaida)
                            .addComponent(lblDataEmissaoNotaSaida)
                            .addComponent(lblRegSaidaDescricao))
                        .addGap(22, 22, 22)
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbRegSaidaNatOp, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfRegSaidaCod, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRegSaidaSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfRegSaidaCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfRegSaidaDataEm, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRegSaidaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(lblRegSaidaQuant)
                        .addGap(10, 10, 10)
                        .addComponent(tfRegSaidaQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnRegSaidaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnRegSaidaDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        pnlDadosDaNotaLayout.setVerticalGroup(
            pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosDaNotaLayout.createSequentialGroup()
                .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblRegSaidaQuant)
                                    .addComponent(tfRegSaidaQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRegSaidaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRegSaidaDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfRegSaidaCod, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCodigoNotaSaida))
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblNatOpNotaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(cbRegSaidaNatOp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10)
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbRegSaidaSetor, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSetorNotaSaida))
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblCNPJNotaSaida))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(tfRegSaidaCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(tfRegSaidaDataEm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDadosDaNotaLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lblDataEmissaoNotaSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(pnlDadosDaNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRegSaidaDescricao)
                            .addComponent(cbRegSaidaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        pnlDadosDaNotaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbRegSaidaDescricao, cbRegSaidaNatOp, cbRegSaidaSetor, tfRegSaidaCnpj, tfRegSaidaCod, tfRegSaidaDataEm});

        lblProdutosNotaSaida.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        lblProdutosNotaSaida.setText("Produtos da nota:");

        btnAjudaRegistroSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        btnAjudaRegistroSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroSaidaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroSaidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroSaidaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlRegSaidaLayout = new javax.swing.GroupLayout(pnlRegSaida);
        pnlRegSaida.setLayout(pnlRegSaidaLayout);
        pnlRegSaidaLayout.setHorizontalGroup(
            pnlRegSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlRegSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDadosDaNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                        .addComponent(lblRegSaidaHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjudaRegistroSaida))
                    .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                        .addComponent(lblProdutosNotaSaida)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                        .addComponent(lblRegSaidaTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfRegSaidaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegSaidaSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
            .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegistroSaida)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRegSaidaLayout.setVerticalGroup(
            pnlRegSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRegistroSaida)
                .addGap(3, 3, 3)
                .addComponent(pnlDadosDaNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProdutosNotaSaida)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlRegSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                        .addGroup(pnlRegSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblRegSaidaTotal)
                            .addComponent(tfRegSaidaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(lblRegSaidaHistorico))
                    .addGroup(pnlRegSaidaLayout.createSequentialGroup()
                        .addComponent(btnRegSaidaSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjudaRegistroSaida)))
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlRegSaida);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda Tela Produtos:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltar.setText("Voltar");
        brnAjudaVoltar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarActionPerformed(evt);
            }
        });

        lblComoCadastrarProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoCadastrarProduto.setText("• Como cadastrar um produto ?");

        lblAlterarProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAlterarProduto.setText("• Como alterar um produto cadastrado?");

        lblExcluirProduto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExcluirProduto.setText("• Como excluir um produto cadastrado?");

        lblLimparCampos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLimparCampos.setText("• Como limpar/desbloquear os campos ?");
        lblLimparCampos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLimparCamposMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLimparCamposMouseExited(evt);
            }
        });

        lblCadastrarProdutoAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCadastrarProdutoAjuda.setText("Para cadastrar um produto é necessário que seja preenchido corretamente todos os campos de texto e clicar no botão “Cadastrar”.");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("<html> Para realizar a alteração de um produto cadastrado, é necessário clicar na tabela sobre o produto que deseja alterar, feito isso, basta modificar as informações <br />  do produto e clicar no botão “Alterar” para que o produto seja alterado. </html>");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("<html> Para realizar a exclusão de um produto cadastrado, é necessário clicar na tabela sobre o produto que deseja alterar, feito isso, basta clicar no botão \"Excluir\" <br />  e posteiormente confirmar a exclusão. </html>");
        jLabel6.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("<html> Para limpar/desbloquear os campos, basta clicar no botão limpar. </html>");

        lblComoPesquisarProduto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblComoPesquisarProduto.setText("• Como pesquisar um produto cadastrado ?");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("<html> Para realizar uma pesquisa de um produto cadastrado, basta inserir a descrição dele no campo de texto “Pesquisar (Descrição)”. ( A medida que a descrição <br /> é inserida a pesquisa já vai sendo feita). </html>");

        verDemoCadProd.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoCadProd.setForeground(new java.awt.Color(57, 33, 89));
        verDemoCadProd.setText("Ver demonstração");
        verDemoCadProd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoCadProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoCadProdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoCadProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoCadProdMouseExited(evt);
            }
        });

        verDemoAltProd.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAltProd.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAltProd.setText("Ver demonstração");
        verDemoAltProd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAltProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAltProdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAltProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAltProdMouseExited(evt);
            }
        });

        verDemoExcProd.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcProd.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcProd.setText("Ver demonstração");
        verDemoExcProd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcProdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcProdMouseExited(evt);
            }
        });

        verDemoLimparCamposProd.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoLimparCamposProd.setForeground(new java.awt.Color(57, 33, 89));
        verDemoLimparCamposProd.setText("Ver demonstração");
        verDemoLimparCamposProd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoLimparCamposProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposProdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposProdMouseExited(evt);
            }
        });

        verDemoPesqProd.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoPesqProd.setForeground(new java.awt.Color(57, 33, 89));
        verDemoPesqProd.setText("Ver demonstração");
        verDemoPesqProd.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoPesqProd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoPesqProdMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoPesqProdMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoPesqProdMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExcluirProduto)
                    .addComponent(lblComoCadastrarProduto)
                    .addComponent(lblLimparCampos)
                    .addComponent(lblCadastrarProdutoAjuda)
                    .addComponent(lblAlterarProduto)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComoPesquisarProduto)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verDemoCadProd)
                    .addComponent(verDemoAltProd)
                    .addComponent(verDemoExcProd)
                    .addComponent(verDemoLimparCamposProd)
                    .addComponent(verDemoPesqProd)
                    .addComponent(brnAjudaVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblComoCadastrarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCadastrarProdutoAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoCadProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAlterarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAltProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblExcluirProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoExcProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLimparCampos)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoLimparCamposProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblComoPesquisarProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(verDemoPesqProd)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAjudaProdutosLayout = new javax.swing.GroupLayout(pnlAjudaProdutos);
        pnlAjudaProdutos.setLayout(pnlAjudaProdutosLayout);
        pnlAjudaProdutosLayout.setHorizontalGroup(
            pnlAjudaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAjudaProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAjudaProdutosLayout.setVerticalGroup(
            pnlAjudaProdutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaProdutosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlAjudaProdutos);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda Tela Setores:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltar1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltar1.setText("Voltar");
        brnAjudaVoltar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnAjudaVoltar1MouseClicked(evt);
            }
        });
        brnAjudaVoltar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltar1ActionPerformed(evt);
            }
        });

        lblComoCadastrarSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoCadastrarSetor.setText("• Como cadastrar um setor ?");

        lblAlterarSetor.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAlterarSetor.setText("• Como alterar um setor cadastrado?");

        lblExcluirSetor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExcluirSetor.setText("• Como excluir um setor cadastrado?");

        lblLimparCamposSetor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblLimparCamposSetor.setText("• Como limpar/desbloquear os campos ?");
        lblLimparCamposSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLimparCamposSetorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLimparCamposSetorMouseExited(evt);
            }
        });

        lblCadastrarSetorAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCadastrarSetorAjuda.setText("Para cadastrar um setor é necessário que seja preenchido corretamente todos os campos de texto obrigatórios e clicar no botão “Cadastrar”.");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("<html> Para realizar a alteração de um setor cadastrado, é necessário clicar na tabela sobre o setor que deseja alterar, feito isso, basta modificar as informações <br />  do setor e clicar no botão “Alterar” para que o produto seja alterado. </html>");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("<html> Para realizar a exclusão de um setor cadastrado, é necessário clicar na tabela sobre o setor que deseja alterar, feito isso, basta clicar no botão \"Excluir\" <br />  e posteiormente confirmar a exclusão. </html>");
        jLabel23.setToolTipText("");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("<html> Para limpar/desbloquear os campos, basta clicar no botão limpar. </html>");

        lblComoPesquisarSetor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblComoPesquisarSetor.setText("• Como pesquisar um setor cadastrado ?");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("<html> Para realizar uma pesquisa de um setor cadastrado, basta inserir a descrição dele no campo de texto “Pesquisar (Descrição)”. ( A medida que a descrição <br /> é inserida a pesquisa já vai sendo feita). </html>");

        verDemoCadSetor.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoCadSetor.setForeground(new java.awt.Color(57, 33, 89));
        verDemoCadSetor.setText("Ver demonstração");
        verDemoCadSetor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoCadSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoCadSetorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoCadSetorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoCadSetorMouseExited(evt);
            }
        });

        verDemoAltSetor.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAltSetor.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAltSetor.setText("Ver demonstração");
        verDemoAltSetor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAltSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAltSetorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAltSetorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAltSetorMouseExited(evt);
            }
        });

        verDemoExcSetor.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcSetor.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcSetor.setText("Ver demonstração");
        verDemoExcSetor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcSetorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcSetorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcSetorMouseExited(evt);
            }
        });

        verDemoLimparCamposSet.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoLimparCamposSet.setForeground(new java.awt.Color(57, 33, 89));
        verDemoLimparCamposSet.setText("Ver demonstração");
        verDemoLimparCamposSet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoLimparCamposSet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposSetMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposSetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoLimparCamposSetMouseExited(evt);
            }
        });

        verDemoPesqSetor.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoPesqSetor.setForeground(new java.awt.Color(57, 33, 89));
        verDemoPesqSetor.setText("Ver demonstração");
        verDemoPesqSetor.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoPesqSetor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoPesqSetorMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoPesqSetorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoPesqSetorMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExcluirSetor)
                    .addComponent(lblComoCadastrarSetor)
                    .addComponent(lblLimparCamposSetor)
                    .addComponent(brnAjudaVoltar1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCadastrarSetorAjuda)
                    .addComponent(lblAlterarSetor)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verDemoCadSetor)
                    .addComponent(verDemoAltSetor)
                    .addComponent(verDemoExcSetor)
                    .addComponent(verDemoLimparCamposSet)
                    .addComponent(lblComoPesquisarSetor)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verDemoPesqSetor))
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblComoCadastrarSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCadastrarSetorAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoCadSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAlterarSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAltSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblExcluirSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoExcSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblLimparCamposSetor)
                .addGap(18, 18, 18)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoLimparCamposSet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblComoPesquisarSetor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoPesqSetor)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAjudaSetoresLayout = new javax.swing.GroupLayout(pnlAjudaSetores);
        pnlAjudaSetores.setLayout(pnlAjudaSetoresLayout);
        pnlAjudaSetoresLayout.setHorizontalGroup(
            pnlAjudaSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAjudaSetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAjudaSetoresLayout.setVerticalGroup(
            pnlAjudaSetoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaSetoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pnlPaineis.addTab("", pnlAjudaSetores);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda tela Registro de Entrada:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltarRegEntrada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltarRegEntrada.setText("Voltar");
        brnAjudaVoltarRegEntrada.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltarRegEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarRegEntradaActionPerformed(evt);
            }
        });

        lblComoCadastrarNotaEnt.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoCadastrarNotaEnt.setText("• Como cadastrar uma nota de entrada ?");

        lblAlddNotaEntrada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAlddNotaEntrada.setText("• Como adicionar  produto em uma nota de entrada ?");

        lblExcluirNotaEnt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExcluirNotaEnt.setText("• Como excluir um produto adicionado em uma notada de entrada ?");

        lblAcessarHisNotEnt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAcessarHisNotEnt.setText("• Como acessar histórico das notas cadastradas ?");
        lblAcessarHisNotEnt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAcessarHisNotEntMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAcessarHisNotEntMouseExited(evt);
            }
        });

        lblCadastrarNotaEnt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCadastrarNotaEnt.setText("Para cadastrar uma nota de entrada é necessário que seja preenchido corretamente todos os campos  obrigatórios e clicar no botão “Cadastrar”.");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel30.setText("<html> Para adicionar um produto na nota de entrada, é necessário preencher todas as informações obrigatórias anteriormente, feito isso,  basta selecionar <br /> o produto e clicar em adicionar. </html>");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Para remover um produto adicionado em uma nota, basta clicar na tabela sobre o produto que deseja remover da nota e posteriormente clicar em remover.");
        jLabel31.setToolTipText("");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("Para acessar o histórico das notas de entrada cadastradas no sistema, basta clicar em \"histórico de notas de entrada\"");

        lblComoPesquisarNotEnt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblComoPesquisarNotEnt.setText("• Como excluir uma nota cadastrada ?");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        verDemoCadNotaEnt.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoCadNotaEnt.setForeground(new java.awt.Color(57, 33, 89));
        verDemoCadNotaEnt.setText("Ver demonstração");
        verDemoCadNotaEnt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoCadNotaEnt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoCadNotaEntMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoCadNotaEntMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoCadNotaEntMouseExited(evt);
            }
        });

        verDemoAddNotaEnt.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAddNotaEnt.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAddNotaEnt.setText("Ver demonstração");
        verDemoAddNotaEnt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAddNotaEnt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAddNotaEntMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAddNotaEntMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAddNotaEntMouseExited(evt);
            }
        });

        verDemoExcProdNotaEnt.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcProdNotaEnt.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcProdNotaEnt.setText("Ver demonstração");
        verDemoExcProdNotaEnt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcProdNotaEnt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcProdNotaEntMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcProdNotaEntMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcProdNotaEntMouseExited(evt);
            }
        });

        verDemoAcessarHistorico.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAcessarHistorico.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAcessarHistorico.setText("Ver demonstração");
        verDemoAcessarHistorico.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAcessarHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoMouseExited(evt);
            }
        });

        verDemoExcluirNota.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcluirNota.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcluirNota.setText("Ver demonstração");
        verDemoExcluirNota.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcluirNota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcluirNotaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcluirNotaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcluirNotaMouseExited(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel39.setText("Para excluir uma nota, basta acessar o histórico, selecionar a nota que deseja excluir e clicar em \"excluir\".");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExcluirNotaEnt)
                    .addComponent(lblComoCadastrarNotaEnt)
                    .addComponent(lblAcessarHisNotEnt)
                    .addComponent(brnAjudaVoltarRegEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCadastrarNotaEnt)
                    .addComponent(lblAlddNotaEntrada)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32)
                    .addComponent(lblComoPesquisarNotEnt)
                    .addComponent(jLabel33)
                    .addComponent(verDemoCadNotaEnt)
                    .addComponent(verDemoAddNotaEnt)
                    .addComponent(verDemoExcProdNotaEnt)
                    .addComponent(verDemoAcessarHistorico)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(verDemoExcluirNota))
                    .addComponent(jLabel39))
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblComoCadastrarNotaEnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCadastrarNotaEnt)
                .addGap(10, 10, 10)
                .addComponent(verDemoCadNotaEnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAlddNotaEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAddNotaEnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblExcluirNotaEnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoExcProdNotaEnt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAcessarHisNotEnt)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAcessarHistorico)
                .addGap(18, 18, 18)
                .addComponent(lblComoPesquisarNotEnt)
                .addGap(18, 18, 18)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoExcluirNota)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltarRegEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel33)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAjudaRegistroEntradaLayout = new javax.swing.GroupLayout(pnlAjudaRegistroEntrada);
        pnlAjudaRegistroEntrada.setLayout(pnlAjudaRegistroEntradaLayout);
        pnlAjudaRegistroEntradaLayout.setHorizontalGroup(
            pnlAjudaRegistroEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAjudaRegistroEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAjudaRegistroEntradaLayout.setVerticalGroup(
            pnlAjudaRegistroEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaRegistroEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlAjudaRegistroEntrada);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda tela Registro de Saída:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltarRegistroSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltarRegistroSaida.setText("Voltar");
        brnAjudaVoltarRegistroSaida.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltarRegistroSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnAjudaVoltarRegistroSaidaMouseClicked(evt);
            }
        });
        brnAjudaVoltarRegistroSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarRegistroSaidaActionPerformed(evt);
            }
        });

        lblComoCadastrarNotaSaida.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoCadastrarNotaSaida.setText("• Como cadastrar uma nota de saída ?");

        lblAdicionarProdutoNotaS.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAdicionarProdutoNotaS.setText("• Como adicionar  produto em uma nota de saída ?");

        lblExcluirProdutoNS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExcluirProdutoNS.setText("• Como excluir um produto adicionado em uma notada de saída ?");

        lblHistoricoNotasSaida.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHistoricoNotasSaida.setText("• Como acessar histórico das notas cadastradas ?");
        lblHistoricoNotasSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHistoricoNotasSaidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblHistoricoNotasSaidaMouseExited(evt);
            }
        });

        lblCadastrarNotaSaidaAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCadastrarNotaSaidaAjuda.setText("Para cadastrar uma nota de saída é necessário que seja preenchido corretamente todos os campos  obrigatórios e clicar no botão “Cadastrar”.");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel35.setText("<html> Para adicionar um produto na nota de saída, é necessário preencher todas as informações obrigatórias anteriormente, feito isso,  basta selecionar <br /> o produto e clicar em adicionar. </html>");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Para remover um produto adicionado em uma nota, basta clicar na tabela sobre o produto que deseja remover da nota e posteriormente clicar em remover.");
        jLabel36.setToolTipText("");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("Para acessar o histórico das notas de saída cadastradas no sistema, basta clicar em \"histórico de registros de saída\"");

        lblExcluirNotaSCadastrada.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblExcluirNotaSCadastrada.setText("• Como excluir uma nota cadastrada ?");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        verDemoCadNotaSaida.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoCadNotaSaida.setForeground(new java.awt.Color(57, 33, 89));
        verDemoCadNotaSaida.setText("Ver demonstração");
        verDemoCadNotaSaida.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoCadNotaSaida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoCadNotaSaidaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoCadNotaSaidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoCadNotaSaidaMouseExited(evt);
            }
        });

        verDemoAdcProdutoNS.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAdcProdutoNS.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAdcProdutoNS.setText("Ver demonstração");
        verDemoAdcProdutoNS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAdcProdutoNS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAdcProdutoNSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAdcProdutoNSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAdcProdutoNSMouseExited(evt);
            }
        });

        verDemoExcProdutoNS.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcProdutoNS.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcProdutoNS.setText("Ver demonstração");
        verDemoExcProdutoNS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcProdutoNS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcProdutoNSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcProdutoNSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcProdutoNSMouseExited(evt);
            }
        });

        verDemoAcessarHistoricoNS.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAcessarHistoricoNS.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAcessarHistoricoNS.setText("Ver demonstração");
        verDemoAcessarHistoricoNS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAcessarHistoricoNS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoNSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoNSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAcessarHistoricoNSMouseExited(evt);
            }
        });

        verDemoExcNotaS.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcNotaS.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcNotaS.setText("Ver demonstração");
        verDemoExcNotaS.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcNotaS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcNotaSMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcNotaSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcNotaSMouseExited(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel40.setText("Para excluir uma nota, basta acessar o histórico, selecionar a nota que deseja excluir e clicar em \"excluir\".");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExcluirProdutoNS)
                    .addComponent(lblComoCadastrarNotaSaida)
                    .addComponent(lblHistoricoNotasSaida)
                    .addComponent(brnAjudaVoltarRegistroSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCadastrarNotaSaidaAjuda)
                    .addComponent(lblAdicionarProdutoNotaS)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37)
                    .addComponent(lblExcluirNotaSCadastrada)
                    .addComponent(jLabel38)
                    .addComponent(verDemoCadNotaSaida)
                    .addComponent(verDemoAdcProdutoNS)
                    .addComponent(verDemoExcProdutoNS)
                    .addComponent(verDemoAcessarHistoricoNS)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(verDemoExcNotaS))
                    .addComponent(jLabel40))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblComoCadastrarNotaSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCadastrarNotaSaidaAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoCadNotaSaida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAdicionarProdutoNotaS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAdcProdutoNS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblExcluirProdutoNS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoExcProdutoNS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblHistoricoNotasSaida)
                .addGap(18, 18, 18)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoAcessarHistoricoNS)
                .addGap(18, 18, 18)
                .addComponent(lblExcluirNotaSCadastrada)
                .addGap(18, 18, 18)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(verDemoExcNotaS)
                .addGap(0, 0, 0)
                .addComponent(jLabel38)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltarRegistroSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlAjudaRegistroSaidaLayout = new javax.swing.GroupLayout(pnlAjudaRegistroSaida);
        pnlAjudaRegistroSaida.setLayout(pnlAjudaRegistroSaidaLayout);
        pnlAjudaRegistroSaidaLayout.setHorizontalGroup(
            pnlAjudaRegistroSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAjudaRegistroSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAjudaRegistroSaidaLayout.setVerticalGroup(
            pnlAjudaRegistroSaidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaRegistroSaidaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPaineis.addTab("", pnlAjudaRegistroSaida);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda tela Conta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltarAltSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltarAltSenha.setText("Voltar");
        brnAjudaVoltarAltSenha.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltarAltSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnAjudaVoltarAltSenhaMouseClicked(evt);
            }
        });
        brnAjudaVoltarAltSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarAltSenhaActionPerformed(evt);
            }
        });

        lblComoAlterarSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoAlterarSenha.setText("• Como alterar senha ?");

        lblAlterarSenhaAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblAlterarSenhaAjuda.setText("Para alterar a senha da conta basta inserir e confirmar a nova senha, feito isso, basta clicar no botão \"Alterar senha\".");

        verDemoAltSenha.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoAltSenha.setForeground(new java.awt.Color(57, 33, 89));
        verDemoAltSenha.setText("Ver demonstração");
        verDemoAltSenha.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoAltSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoAltSenhaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoAltSenhaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoAltSenhaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComoAlterarSenha)
                    .addComponent(lblAlterarSenhaAjuda)
                    .addComponent(verDemoAltSenha)))
            .addComponent(brnAjudaVoltarAltSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComoAlterarSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblAlterarSenhaAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoAltSenha)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltarAltSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pnlAjudaAlterarSenhaLayout = new javax.swing.GroupLayout(pnlAjudaAlterarSenha);
        pnlAjudaAlterarSenha.setLayout(pnlAjudaAlterarSenhaLayout);
        pnlAjudaAlterarSenhaLayout.setHorizontalGroup(
            pnlAjudaAlterarSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAjudaAlterarSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlAjudaAlterarSenhaLayout.setVerticalGroup(
            pnlAjudaAlterarSenhaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaAlterarSenhaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPaineis.addTab("", pnlAjudaAlterarSenha);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda tela Usuários:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltarRegSaida2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltarRegSaida2.setText("Voltar");
        brnAjudaVoltarRegSaida2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltarRegSaida2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnAjudaVoltarRegSaida2MouseClicked(evt);
            }
        });
        brnAjudaVoltarRegSaida2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarRegSaida2ActionPerformed(evt);
            }
        });

        lblComoCadastrarUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoCadastrarUsuario.setText("• Como cadastrar usuário?");

        lblCadastrarUusuarioAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCadastrarUusuarioAjuda.setText("Para cadastrar um usuário, é necessário que seja preenchido corretamente todos os campos e posteriormente clicar em cadastrar");

        verDemoCadUsuario.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoCadUsuario.setForeground(new java.awt.Color(57, 33, 89));
        verDemoCadUsuario.setText("Ver demonstração");
        verDemoCadUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoCadUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoCadUsuarioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoCadUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoCadUsuarioMouseExited(evt);
            }
        });

        lblComoExcluirUusuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoExcluirUusuario.setText("• Como excluir usuário?");

        lblExcluirUsuarioAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblExcluirUsuarioAjuda.setText("Para excluir um usuário, é necessário selecionar na tabela o usuário que deseja excluir e posteriormente clicar no botão \"excluir\".");

        verDemoExcUsuario.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoExcUsuario.setForeground(new java.awt.Color(57, 33, 89));
        verDemoExcUsuario.setText("Ver demonstração");
        verDemoExcUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoExcUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoExcUsuarioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoExcUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoExcUsuarioMouseExited(evt);
            }
        });

        lblLimparCamposUsuarios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblLimparCamposUsuarios.setText("• Como limpar/desbloquear campos ?");

        lbLimparUsAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbLimparUsAjuda.setText("Para limpar/desbloquear os campos basta clicar no botão \"Limpar\". ");

        verDemoLimparUsuarios.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoLimparUsuarios.setForeground(new java.awt.Color(57, 33, 89));
        verDemoLimparUsuarios.setText("Ver demonstração");
        verDemoLimparUsuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoLimparUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoLimparUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoLimparUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoLimparUsuariosMouseExited(evt);
            }
        });

        lblComoPesquisarUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoPesquisarUsuario.setText("• Como pesquisar um  usuário cadastrado ?");

        lblPesquisarUsAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPesquisarUsAjuda.setText("Para realizar uma pesquisa de um usuário cadastrado, basta inserir o nome dele no campo de texto \"Pesquisar (Nome)\". (A medida que o nome vai sendo inserido a pesquisa já vai sendo feita ");

        verDemoPesqUs.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoPesqUs.setForeground(new java.awt.Color(57, 33, 89));
        verDemoPesqUs.setText("Ver demonstração");
        verDemoPesqUs.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoPesqUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoPesqUsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoPesqUsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoPesqUsMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComoCadastrarUsuario)
                    .addComponent(brnAjudaVoltarRegSaida2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCadastrarUusuarioAjuda)
                    .addComponent(verDemoCadUsuario)
                    .addComponent(lblComoExcluirUusuario)
                    .addComponent(lblExcluirUsuarioAjuda)
                    .addComponent(verDemoExcUsuario)
                    .addComponent(lblLimparCamposUsuarios)
                    .addComponent(lbLimparUsAjuda)
                    .addComponent(verDemoLimparUsuarios)
                    .addComponent(lblComoPesquisarUsuario)
                    .addComponent(lblPesquisarUsAjuda)
                    .addComponent(verDemoPesqUs))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComoCadastrarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCadastrarUusuarioAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoCadUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblComoExcluirUusuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblExcluirUsuarioAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoExcUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLimparCamposUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLimparUsAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoLimparUsuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblComoPesquisarUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPesquisarUsAjuda)
                .addGap(10, 10, 10)
                .addComponent(verDemoPesqUs)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltarRegSaida2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pnlAjudaUusuariosLayout = new javax.swing.GroupLayout(pnlAjudaUusuarios);
        pnlAjudaUusuarios.setLayout(pnlAjudaUusuariosLayout);
        pnlAjudaUusuariosLayout.setHorizontalGroup(
            pnlAjudaUusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaUusuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        pnlAjudaUusuariosLayout.setVerticalGroup(
            pnlAjudaUusuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaUusuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlPaineis.addTab("", pnlAjudaUusuarios);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ajuda tela Relatórios:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        brnAjudaVoltarRelatorios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        brnAjudaVoltarRelatorios.setText("Voltar");
        brnAjudaVoltarRelatorios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        brnAjudaVoltarRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                brnAjudaVoltarRelatoriosMouseClicked(evt);
            }
        });
        brnAjudaVoltarRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brnAjudaVoltarRelatoriosActionPerformed(evt);
            }
        });

        lblFuncionamentoRelatorios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblFuncionamentoRelatorios.setText("• Funcionamento da tela relatórios:");

        lblRelatoriosAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRelatoriosAjuda.setText("Os relatórios podem ser gerados de duas maneiras distintas, por filtros específicos ou com base em uma nota específica. ");

        verDemoRelatoriosFiltros.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoRelatoriosFiltros.setForeground(new java.awt.Color(57, 33, 89));
        verDemoRelatoriosFiltros.setText("Ver demonstração");
        verDemoRelatoriosFiltros.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoRelatoriosFiltros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoRelatoriosFiltrosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoRelatoriosFiltrosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoRelatoriosFiltrosMouseExited(evt);
            }
        });

        lblRelatoriosFiltros.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRelatoriosFiltros.setText("• Como gerar relatório por filtros específicos ?");

        lblRelatoriosFiltrosAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRelatoriosFiltrosAjuda.setText("<html> Para gerar o relatório por filtros específicos basta selecionar \"Gerar relatórios por filtro(s)\", posteriormente selecionar o tipo da(s) nota(s), e escolher  <br /> os filtros que desejar, por fim, basta clicar em \"Gerar Relatório\".  </html>");

        lblComoGerarRelatorioCodigo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblComoGerarRelatorioCodigo.setText("• Como gerar relatório por código da nota ?");

        lblRelatorioCodigoAjuda.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblRelatorioCodigoAjuda.setText("<html> Para gerar o relatório com base em uma nota específica basta selecionar \"Gerar relatórios por código da nota\", posteriormente informar <br /> o tipo da nota, bem como o seu código, por fim, basta clicar em \"Gerar Relatório\". </html>");

        verDemoRelatorioCodigo.setFont(new java.awt.Font("Dialog", 3, 14)); // NOI18N
        verDemoRelatorioCodigo.setForeground(new java.awt.Color(57, 33, 89));
        verDemoRelatorioCodigo.setText("Ver demonstração");
        verDemoRelatorioCodigo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        verDemoRelatorioCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verDemoRelatorioCodigoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                verDemoRelatorioCodigoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                verDemoRelatorioCodigoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFuncionamentoRelatorios)
                    .addComponent(brnAjudaVoltarRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRelatoriosAjuda)
                    .addComponent(verDemoRelatoriosFiltros)
                    .addComponent(lblRelatoriosFiltros)
                    .addComponent(lblRelatoriosFiltrosAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(verDemoRelatorioCodigo)
                    .addComponent(lblComoGerarRelatorioCodigo)
                    .addComponent(lblRelatorioCodigoAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFuncionamentoRelatorios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRelatoriosAjuda)
                .addGap(18, 18, 18)
                .addComponent(lblRelatoriosFiltros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRelatoriosFiltrosAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoRelatoriosFiltros)
                .addGap(18, 18, 18)
                .addComponent(lblComoGerarRelatorioCodigo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRelatorioCodigoAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(verDemoRelatorioCodigo)
                .addGap(15, 15, 15)
                .addComponent(brnAjudaVoltarRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pnlAjudaRelatoriosLayout = new javax.swing.GroupLayout(pnlAjudaRelatorios);
        pnlAjudaRelatorios.setLayout(pnlAjudaRelatoriosLayout);
        pnlAjudaRelatoriosLayout.setHorizontalGroup(
            pnlAjudaRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaRelatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAjudaRelatoriosLayout.setVerticalGroup(
            pnlAjudaRelatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAjudaRelatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pnlPaineis.addTab("", pnlAjudaRelatorios);

        lblRegEntrada.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRegEntrada.setText("Registro de entrada");

        tblNotaEntrada.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblNotaEntrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantidade", "Unidade", "Descrição", "Valor Unidade", "Valor Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNotaEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNotaEntradaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNotaEntrada);
        if (tblNotaEntrada.getColumnModel().getColumnCount() > 0) {
            tblNotaEntrada.getColumnModel().getColumn(0).setHeaderValue("Quantidade");
            tblNotaEntrada.getColumnModel().getColumn(1).setHeaderValue("Unidade");
            tblNotaEntrada.getColumnModel().getColumn(2).setHeaderValue("Descrição");
            tblNotaEntrada.getColumnModel().getColumn(3).setHeaderValue("Valor Unidade");
            tblNotaEntrada.getColumnModel().getColumn(4).setHeaderValue("Valor Total");
        }

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setText("Total:");

        tfRegEntradaTotal.setEditable(false);
        tfRegEntradaTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        btnRegEntradaSalvar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegEntradaSalvar.setText("Cadastrar");
        btnRegEntradaSalvar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegEntradaSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegEntradaSalvarActionPerformed(evt);
            }
        });

        lblRegEntradaHistorico.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaHistorico.setText("Histórico de registros de entrada");
        lblRegEntradaHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegEntradaHistoricoMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRegEntradaHistoricoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRegEntradaHistoricoMouseExited(evt);
            }
        });

        lblProdutosNotaEntrada.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        lblProdutosNotaEntrada.setText("Produtos da nota:");

        pnlDadosNotaEntrada.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados da nota:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 13))); // NOI18N

        lblRegEntradaDataEmissao.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaDataEmissao.setText("* Data de emissão:");

        lblRegEntradaDataRecebimento.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaDataRecebimento.setText("* Data de recebimento:");

        tfRegEntradaCod.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("##########"))));
        tfRegEntradaCod.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblRegEntradaCod.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaCod.setText("* Código:");

        lblRegEntradaOrin.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaOrin.setText("* Setor de Origem:");

        lblRegEntradaDest.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaDest.setText(" Setor de Destino:");

        cbRegEntradaDest.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRegEntradaDest.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        cbRegEntradaOrigem.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbRegEntradaOrigem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        lblRegEntradaDescricao.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaDescricao.setText("* Produto:");

        btnRegEntradaDeletar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegEntradaDeletar.setText("Remover");
        btnRegEntradaDeletar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegEntradaDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegEntradaDeletarActionPerformed(evt);
            }
        });

        tfRegEntradaQuant.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.###"))));
        tfRegEntradaQuant.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblRegEntradaQuant.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblRegEntradaQuant.setText("Quantidade:");

        btnRegEntradaAdicionar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnRegEntradaAdicionar.setText("Adicionar");
        btnRegEntradaAdicionar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegEntradaAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegEntradaAdicionarActionPerformed(evt);
            }
        });

        tfRegEntradaDataEmissao.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tfRegEntradaDataRecebimento.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout pnlDadosNotaEntradaLayout = new javax.swing.GroupLayout(pnlDadosNotaEntrada);
        pnlDadosNotaEntrada.setLayout(pnlDadosNotaEntradaLayout);
        pnlDadosNotaEntradaLayout.setHorizontalGroup(
            pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosNotaEntradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosNotaEntradaLayout.createSequentialGroup()
                        .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRegEntradaCod)
                            .addComponent(lblRegEntradaDescricao)
                            .addComponent(lblRegEntradaOrin)
                            .addComponent(lblRegEntradaDest)
                            .addComponent(lblRegEntradaDataEmissao))
                        .addGap(54, 54, 54)
                        .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbRegEntradaOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbRegEntradaDest, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfRegEntradaDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDadosNotaEntradaLayout.createSequentialGroup()
                                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbRegEntradaDescricao, 0, 212, Short.MAX_VALUE)
                                    .addComponent(tfRegEntradaCod))
                                .addGap(15, 15, 15)
                                .addComponent(lblRegEntradaQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(tfRegEntradaQuant, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnRegEntradaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnRegEntradaDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlDadosNotaEntradaLayout.createSequentialGroup()
                        .addComponent(lblRegEntradaDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(tfRegEntradaDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnlDadosNotaEntradaLayout.setVerticalGroup(
            pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDadosNotaEntradaLayout.createSequentialGroup()
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegEntradaCod)
                    .addComponent(tfRegEntradaCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegEntradaOrin)
                    .addComponent(cbRegEntradaDest, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRegEntradaOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRegEntradaDest))
                .addGap(10, 10, 10)
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRegEntradaDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfRegEntradaDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblRegEntradaDataRecebimento)
                    .addComponent(tfRegEntradaDataRecebimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblRegEntradaQuant)
                        .addComponent(tfRegEntradaQuant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRegEntradaAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRegEntradaDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDadosNotaEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblRegEntradaDescricao)
                        .addComponent(cbRegEntradaDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );

        pnlDadosNotaEntradaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbRegEntradaDescricao, cbRegEntradaDest, cbRegEntradaOrigem, tfRegEntradaCod, tfRegEntradaDataRecebimento});

        btnAjudaRegistroEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        btnAjudaRegistroEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroEntradaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroEntradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAjudaRegistroEntradaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlRegEntradaLayout = new javax.swing.GroupLayout(pnlRegEntrada);
        pnlRegEntrada.setLayout(pnlRegEntradaLayout);
        pnlRegEntradaLayout.setHorizontalGroup(
            pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                        .addComponent(lblRegEntradaHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 605, Short.MAX_VALUE)
                        .addComponent(btnAjudaRegistroEntrada))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 925, Short.MAX_VALUE)
                    .addComponent(pnlDadosNotaEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                        .addGroup(pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblProdutosNotaEntrada)
                            .addComponent(lblRegEntrada))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfRegEntradaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegEntradaSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        pnlRegEntradaLayout.setVerticalGroup(
            pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                .addComponent(lblRegEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDadosNotaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProdutosNotaEntrada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfRegEntradaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal)))
                    .addGroup(pnlRegEntradaLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnRegEntradaSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(pnlRegEntradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegEntradaLayout.createSequentialGroup()
                        .addComponent(lblRegEntradaHistorico)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegEntradaLayout.createSequentialGroup()
                        .addComponent(btnAjudaRegistroEntrada)
                        .addGap(10, 10, 10))))
        );

        pnlPaineis.addTab("", pnlRegEntrada);

        jLabel24.setFont(new java.awt.Font("Comic Sans MS", 0, 48)); // NOI18N
        jLabel24.setText("Central de Vendas");

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/if_inicial.png"))); // NOI18N

        jLabel41.setBackground(new java.awt.Color(0, 0, 0));
        jLabel41.setFont(new java.awt.Font("Linux Biolinum G", 0, 70)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("Central de vendas");
        jLabel41.setToolTipText("");

        javax.swing.GroupLayout pnlInicialLayout = new javax.swing.GroupLayout(pnlInicial);
        pnlInicial.setLayout(pnlInicialLayout);
        pnlInicialLayout.setHorizontalGroup(
            pnlInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInicialLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInicialLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addGap(574, 574, 574))
            .addGroup(pnlInicialLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlInicialLayout.setVerticalGroup(
            pnlInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addContainerGap())
        );

        pnlPaineis.addTab("", pnlInicial);

        tblUsuarios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "CPF", "Endereço", "Telefone", "Usuario", "Acesso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        btnAddUsu.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddUsu.setText("Cadastrar");
        btnAddUsu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAddUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddUsuActionPerformed(evt);
            }
        });

        btnUsuExc.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUsuExc.setText("Excluir");
        btnUsuExc.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuExcActionPerformed(evt);
            }
        });

        lblTituloUsuarios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTituloUsuarios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloUsuarios.setText("Gerenciamento de Usuários");

        tfPesquisaUsuario.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tfPesquisaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPesquisaUsuarioKeyPressed(evt);
            }
        });

        lblPesquisar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPesquisar.setText("Pesquisar (Nome)");

        btnLimparUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnLimparUsuario.setText("Limpar");
        btnLimparUsuario.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimparUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparUsuarioActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setText("Usuários cadastrados:");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações do usuário", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N

        lblUsuNom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuNom.setText("*Nome:");

        lblUsuCpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuCpf.setText("*CPF:");

        lblUsuEnd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuEnd.setText("*Endereço:");

        tfUsuEnd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        try {
            tfUsuCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfUsuCpf.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        tfUsuNom.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblUsuAcesso.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuAcesso.setText("*Acesso:");

        cbUsuAcesso.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cbUsuAcesso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuário" }));

        pfUsuSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblUsuSenha.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuSenha.setText("*Senha:");

        lblUsuUsuario.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuUsuario.setText("*Usuário:");

        tfUsuLogin.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        lblUsuTel.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUsuTel.setText("*Telefone:");

        try {
            tfUsuFone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        tfUsuFone.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(lblUsuNom)
                            .addGap(70, 70, 70))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(lblUsuCpf)
                            .addGap(82, 82, 82)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuEnd)
                            .addComponent(lblUsuTel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfUsuNom)
                    .addComponent(tfUsuCpf)
                    .addComponent(tfUsuEnd)
                    .addComponent(tfUsuFone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUsuUsuario)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblUsuSenha)
                        .addComponent(lblUsuAcesso)))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(tfUsuLogin, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cbUsuAcesso, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pfUsuSenha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUsuUsuario))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuAcesso)
                            .addComponent(cbUsuAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuSenha)
                            .addComponent(pfUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblUsuTel)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(tfUsuNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfUsuCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUsuCpf))
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tfUsuEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUsuEnd)))
                                .addComponent(lblUsuNom))
                            .addGap(10, 10, 10)
                            .addComponent(tfUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbUsuAcesso, pfUsuSenha, tfUsuCpf, tfUsuEnd, tfUsuFone, tfUsuLogin, tfUsuNom});

        lblAjudaUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ajuda.png"))); // NOI18N
        lblAjudaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAjudaUsuariosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAjudaUsuariosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAjudaUsuariosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlUsuariosLayout = new javax.swing.GroupLayout(pnlUsuarios);
        pnlUsuarios.setLayout(pnlUsuariosLayout);
        pnlUsuariosLayout.setHorizontalGroup(
            pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuariosLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuariosLayout.createSequentialGroup()
                        .addGroup(pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlUsuariosLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(btnAddUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnUsuExc, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLimparUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlUsuariosLayout.createSequentialGroup()
                                .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))
                    .addGroup(pnlUsuariosLayout.createSequentialGroup()
                        .addComponent(lblTituloUsuarios)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlUsuariosLayout.createSequentialGroup()
                        .addComponent(tfPesquisaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAjudaUsuarios)
                        .addContainerGap())))
        );
        pnlUsuariosLayout.setVerticalGroup(
            pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuariosLayout.createSequentialGroup()
                .addComponent(lblTituloUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addGroup(pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddUsu, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUsuExc, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLimparUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuariosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(lblPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(tfPesquisaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlUsuariosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addComponent(lblAjudaUsuarios)
                        .addGap(10, 10, 10))))
        );

        pnlPaineis.addTab("", pnlUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPaineis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPaineis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntradaMouseClicked
        // Notas

        pnlPaineis.setSelectedComponent(pnlRegEntrada);
        try {
            cbRegEntradaDest.removeAllItems();
            cbRegEntradaOrigem.removeAllItems();
            cbRegEntradaDest.addItem("");
            cbRegEntradaOrigem.addItem("");
            montarComboBoxEntrada();
        } catch (SQLException ex) {

        }
        cbRegEntradaDescricao.removeAllItems();
        cbRegEntradaDescricao.addItem("");
        descricao_produtos = (ArrayList<String>) produtoQueries.getAllProdutosDescricao();
        for (int j = 0; j < descricao_produtos.size(); j++) {
            cbRegEntradaDescricao.addItem(descricao_produtos.get(j));
        }
        tfRegEntradaCod.setText("");
        totalEntrada = 0;
        tfRegEntradaTotal.setText("");
        tabelaEntrada.clear();
        montarTabelaNotaEntrada();
        btnRegEntradaDeletar.setVisible(false);

    }//GEN-LAST:event_lblEntradaMouseClicked

    private void lblSetoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSetoresMouseClicked
        // Setores
        pnlPaineis.setSelectedComponent(pnlSetores);
    }//GEN-LAST:event_lblSetoresMouseClicked

    private void lblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProdutosMouseClicked
        // abrir tela Produtos
        pnlPaineis.setSelectedComponent(pnlProdutos);
        montarTabelaProduto();
    }//GEN-LAST:event_lblProdutosMouseClicked

    private void lblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseClicked
        // Funcionários
        pnlPaineis.setSelectedComponent(pnlUsuarios);
        try {
            tblUsuarios.setModel(new ResultSetTableModelUsuario("select nomUser,cpf,endereco,fone,login,tipo from usuario"));

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }//GEN-LAST:event_lblUsuariosMouseClicked

    private void btnCadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarProdutoActionPerformed
        if (tfDescricaoProduto.getText().isEmpty()
                || ftValorProduto.getText().isEmpty()
                || ftEstoqueProduto.getText().isEmpty() || (cbbUnidadeProduto.getSelectedItem() == "Outro") && tfUnidadeOutro.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, ""
                    + "Por favor, preencha todos os campos obrigatórios", "Campo(s) Vazio(s) ",
                    JOptionPane.WARNING_MESSAGE);

        } else {
            
            descricao_produtos = produtoQueries.getAllProdutosDescricao();
            produto.setDescricao(tfDescricaoProduto.getText());
            produto.setEstoque(Float.parseFloat(ftEstoqueProduto.getText().replaceAll(",", ".")));
            produto.setValor(Float.parseFloat(ftValorProduto.getText().replaceAll(",", ".")));
            
            for (String descricao_produto : descricao_produtos) {
                if(descricao_produto.equals(tfDescricaoProduto.getText())){
                    JOptionPane.showMessageDialog(null, "Produto já foi cadastrado anteriormente!", "Produto existente",JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (cbbUnidadeProduto.getSelectedItem() == "Unid") {
                produto.setUnidade("Unid");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Duz") {
                produto.setUnidade("Duz");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Kg") {
                produto.setUnidade("Kg");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Lt") {
                produto.setUnidade("Lt");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Pct") {
                produto.setUnidade("Pct");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Arroba") {
                produto.setUnidade("Arroba");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Pé") {
                produto.setUnidade("Pé");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Band") {
                produto.setUnidade("Band");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Ton") {
                produto.setUnidade("Ton");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Caix") {
                produto.setUnidade("Caix");
            } else {
                produto.setUnidade(tfUnidadeOutro.getText());
            }

            int result = produtoQueries.addProduto(
                    produto.getDescricao(), produto.getUnidade(),
                    produto.getValor(), produto.getEstoque());
            if (result == 1) {
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                montarTabelaProduto();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }

        limparCamposProduto();

    }//GEN-LAST:event_btnCadastrarProdutoActionPerformed

    private void lblRelatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRelatoriosMouseClicked

        pnlPaineis.setSelectedComponent(pnlRelatorio);
         cbbRelatorioProduto.removeAllItems();
        descricao_produtos = (ArrayList<String>) produtoQueries.getAllProdutosDescricao();
        cbbRelatorioProduto.addItem("");
        for (int j = 0; j < descricao_produtos.size(); j++) {
            cbbRelatorioProduto.addItem(descricao_produtos.get(j));
        }

    }//GEN-LAST:event_lblRelatoriosMouseClicked

    private void lblSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaidaMouseClicked
        // TODO add your handling code here:
        try {
            cbRegSaidaSetor.removeAllItems();
            cbRegSaidaSetor.addItem("");
            montarComboBoxSaida();
        } catch (SQLException ex) {

        }

        pnlPaineis.setSelectedComponent(pnlRegSaida);
        cbRegSaidaDescricao.removeAllItems();
        cbRegSaidaDescricao.addItem("");
        descricao_produtos = (ArrayList<String>) produtoQueries.getAllProdutosDescricao();
        for (int j = 0; j < descricao_produtos.size(); j++) {
            cbRegSaidaDescricao.addItem(descricao_produtos.get(j));
        }
        btnRegSaidaDeletar.setVisible(false);
        tfRegSaidaCod.setText("");
        tfRegSaidaCnpj.setText("");
        totalSaida = 0;
        tfRegSaidaTotal.setText("");
        tabelaSaida.clear();
        montarTabelaNotaSaida();
    }//GEN-LAST:event_lblSaidaMouseClicked

    private void lblSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSairMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_lblSairMouseClicked

    private void btnExcluirProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirProdutoActionPerformed

        if (tfCodigoProduto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É preciso selecionar na tabela o produto que deseja excluir","Erro ao excluir produto",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o produto");
            if ( confirma == 1 || confirma == 2){
                return ;
            }
            

        int codigo = Integer.parseInt(tfCodigoProduto.getText());
        int result = produtoQueries.deleteProduto(codigo);
        if (result == 1) {
            ImageIcon icon = new ImageIcon("src/imagens/excluido.png");
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
           
        } else if(result == -1){
            
        } else{
            JOptionPane.showMessageDialog(this, "Produto Inexistente");
        }

        montarTabelaProduto();
        limparCamposProduto();


    }//GEN-LAST:event_btnExcluirProdutoActionPerformed

    private void btnAlterarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarProdutoActionPerformed
        if (tfDescricaoProduto.getText().isEmpty()
                || ftValorProduto.getText().isEmpty()
                || ftEstoqueProduto.getText().isEmpty() || (cbbUnidadeProduto.getSelectedItem() == "Outro") && tfUnidadeOutro.getText().isEmpty()) {
            if (tfCodigoProduto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É preciso selecionar na tabela o produto que deseja alterar","Erro ao alterar produto",JOptionPane.WARNING_MESSAGE);
            return;
        } 
            
          

        } else {
            
            int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar o produto");
        if (confirma !=0) return; 
            
            produto.setCodProduto(Integer.parseInt(tfCodigoProduto.getText()));
            produto.setDescricao(tfDescricaoProduto.getText());
            produto.setEstoque(Float.parseFloat(ftValorProduto.getText().replaceAll(",", ".")));
            produto.setValor(Float.parseFloat(ftEstoqueProduto.getText().replaceAll(",", ".")));
            if (cbbUnidadeProduto.getSelectedItem() == "Unid") {
                produto.setUnidade("Unid");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Duz") {
                produto.setUnidade("Duz");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Kg") {
                produto.setUnidade("Kg");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Lt") {
                produto.setUnidade("Lt");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Pct") {
                produto.setUnidade("Pct");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Arroba") {
                produto.setUnidade("Arroba");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Pé") {
                produto.setUnidade("Pé");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Band") {
                produto.setUnidade("Band");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Ton") {
                produto.setUnidade("Ton");
            } else if (cbbUnidadeProduto.getSelectedItem() == "Caix") {
                produto.setUnidade("Caix");
            } else {
                produto.setUnidade(tfUnidadeOutro.getText());
            }

            int result = produtoQueries.updateProduto(produto.getCodProd(),
                    produto.getDescricao(), produto.getUnidade(),
                    produto.getValor(), produto.getEstoque());

            if (result == 1) {
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Produto alterado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                montarTabelaProduto();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao alterar produto",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_btnAlterarProdutoActionPerformed

    private void lblInformacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInformacoesMouseClicked
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(pnlInformacoes);
    }//GEN-LAST:event_lblInformacoesMouseClicked

    private void tfNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNomeActionPerformed

    private void btnExcluirSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirSetorActionPerformed

        if (tfCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "É preciso selecionar na tabela o produto que deseja excluir","Erro ao excluir produto",JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o setor ?");
            if ( confirma == 1 || confirma == 2){
                return ;
            }

        int codigo = Integer.parseInt(tfCodigo.getText());
        int result = setorQueries.deleteSetor(codigo);
        if (result == 1) {
            ImageIcon icon = new ImageIcon("src/imagens/excluido.png");
                JOptionPane.showMessageDialog(null, "Setor excluído com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
        } else if(result == -1){
            
        } else{
            JOptionPane.showMessageDialog(this, "Setor Inexistente");
        }

        montarTabelaSetor();
        limparCamposSetor();
    }//GEN-LAST:event_btnExcluirSetorActionPerformed

    private void btnAlterarSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarSetorActionPerformed
        if (tfCodigo.getText().isEmpty() || tfResponsavel.getText().isEmpty()
                || tfNome.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, ""
                    + "Campo(s) Vazio(s)", "Erro ",
                    JOptionPane.WARNING_MESSAGE);
            
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar o setor ?");
            if ( confirma == 1 || confirma == 2){
                return ;
            }
            
            
        } else {
            setor.setCodSetor(Integer.parseInt(tfCodigo.getText()));
            setor.setNomSetor(tfNome.getText());
            setor.setResponsavel(tfResponsavel.getText());
            setor.setCnpj(ftCNPJSetor.getText());
            if (cbbTipoSetor.getSelectedItem() == "Entrada") {
                setor.setTipo("E");
            } else if (cbbTipoSetor.getSelectedItem() == "Saiída") {
                setor.setTipo("S");;
            } else {
                setor.setTipo("E/S");
            }

            int result = setorQueries.updateSetor(setor.getCodSetor(),
                    setor.getNomSetor(), setor.getResponsavel(),
                    setor.getTipo(), setor.getCnpj());
            if (result == 1) {
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Setor alterado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                montarTabelaSetor();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao alterar setor",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnAlterarSetorActionPerformed

    private void btnCadastrarSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarSetorActionPerformed

        if (tfResponsavel.getText().isEmpty()
                || tfNome.getText().isEmpty()) {

            
            JOptionPane.showMessageDialog(null, ""
                    + "Por favor, preencha todos os campos obrigatórios", "Campo(s) Vazio(s) ",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            setor.setCnpj(ftCNPJSetor.getText());
            String cnpj = "";
            cnpj = setor.getCnpj();
            cnpj = cnpj.replace(".", "");
            cnpj = cnpj.replace("-", "");
            if (ftCNPJSetor.getText().trim().length() == 14) {

            } else {

                if (!ValidaCNPJ.isCNPJ(cnpj)) {
                    JOptionPane.showMessageDialog(null, ""
                            + "CNPJ Inválido.", "Erro ao cadastrar setor ",
                            JOptionPane.WARNING_MESSAGE);
                    ftCNPJSetor.setText("");
                    return;
                }
            }

            setor.setNomSetor(tfNome.getText());
            setor.setResponsavel(tfResponsavel.getText());

            if (cbbTipoSetor.getSelectedItem() == "Entrada") {
                setor.setTipo("E");
            } else if (cbbTipoSetor.getSelectedItem() == "Saída") {
                setor.setTipo("S");
            } else {
                setor.setTipo("E/S");
            }

            int result = setorQueries.addSetor(
                    setor.getNomSetor(), setor.getResponsavel(),
                    setor.getTipo(), cnpj);
            if (result == 1) {
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Setor cadastrado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                
                montarTabelaSetor();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar setor",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        limparCamposSetor();
    }//GEN-LAST:event_btnCadastrarSetorActionPerformed

    private void tfResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfResponsavelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfResponsavelActionPerformed

    private void tfPesquisaSetorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaSetorKeyPressed
        try {
            String qry = "Select * from setor where nomSetor"
                    + " LIKE'" + tfPesquisaSetor.getText() + "%'";

            Connection conn = MysqlConnect.connectDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            DefaultTableModel modelo = (DefaultTableModel) tblSetores.getModel();
            modelo.setNumRows(0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tfPesquisaSetorKeyPressed

    private void tfCodigoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCodigoProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCodigoProdutoActionPerformed

    private void cbbUnidadeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbUnidadeProdutoActionPerformed
        if (cbbUnidadeProduto.getSelectedItem() == "Outro") {
            lblQualProduto.setVisible(true);
            tfUnidadeOutro.setVisible(true);
        } else {
            lblQualProduto.setVisible(false);
            tfUnidadeOutro.setVisible(false);
        }
    }//GEN-LAST:event_cbbUnidadeProdutoActionPerformed

    private void btnLimparProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparProdutoActionPerformed
        tfCodigoProduto.setText("");
        tfDescricaoProduto.setText("");
        ftValorProduto.setText("");
        ftEstoqueProduto.setText("");
        cbbUnidadeProduto.setSelectedIndex(0);
        Color color = new Color(255, 255, 255);
        ftEstoqueProduto.setEnabled(true);
        ftEstoqueProduto.setBackground(color);
    }//GEN-LAST:event_btnLimparProdutoActionPerformed

    private void btnLimparSetorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparSetorActionPerformed
        limparCamposSetor();
    }//GEN-LAST:event_btnLimparSetorActionPerformed

    private void ftEstoqueProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftEstoqueProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftEstoqueProdutoActionPerformed

    private void btnAddUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddUsuActionPerformed
        // Botão adicionar na tabela Funcionários
        UsuarioQueries user_query = new UsuarioQueries();
        if (tfUsuNom.getText().isEmpty() || tfUsuCpf.getText().trim().length() != 14 || tfUsuEnd.getText().isEmpty()
                || tfUsuFone.getText().trim().length() != 14 || tfUsuLogin.getText().isEmpty() || pfUsuSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","Campo(s) vazio(s)",JOptionPane.WARNING_MESSAGE);
        } else {

            String nomUser = tfUsuNom.getText();
            String cpf = tfUsuCpf.getText();
            String endereco = tfUsuEnd.getText();
            String fone = tfUsuFone.getText();
            String login = tfUsuLogin.getText();
            String senha = pfUsuSenha.getText();
            String tipo = cbUsuAcesso.getItemAt(cbUsuAcesso.getSelectedIndex());
            int result = user_query.addUsuario(nomUser, cpf, endereco, fone, login, senha, tipo);
             ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
            
            limparCamposUsuario();
        }

        try {
            tblUsuarios.setModel(new ResultSetTableModelUsuario("select nomUser,cpf,endereco,fone,login,tipo from usuario;"));

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
    }//GEN-LAST:event_btnAddUsuActionPerformed

    private void btnUsuExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuExcActionPerformed
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o usuário");
            if ( confirma == 1 || confirma == 2){
                return ;
            }
            
        UsuarioQueries user_query = new UsuarioQueries();
        String loginUser = tfUsuLogin.getText();
        int result = user_query.DeleteUsuario(loginUser);
        try {
            tblUsuarios.setModel(new ResultSetTableModelUsuario("select nomUser,cpf,endereco,fone,login,tipo from usuario"));
            ImageIcon icon = new ImageIcon("src/imagens/excluido.png");
                JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, sqle);
        }
        limparCamposUsuario();
    }//GEN-LAST:event_btnUsuExcActionPerformed

    private void tfPesquisaUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaUsuarioKeyPressed
        // TODO add your handling code here:
        String qry = "Select nomUser,cpf,endereco,fone,login,tipo from usuario where nomUser"
                + " LIKE'" + tfPesquisaUsuario.getText() + "%'";
        try {
            tblUsuarios.setModel(new ResultSetTableModelUsuario(qry));
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_tfPesquisaUsuarioKeyPressed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // TODO add your handling code here:

        tfUsuNom.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString());
        tfUsuCpf.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 1).toString());
        tfUsuEnd.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 2).toString());
        tfUsuFone.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 3).toString());
        tfUsuLogin.setText(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 4).toString());

        if ("Administrador".equalsIgnoreCase(tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 5).toString())) {
            cbUsuAcesso.setSelectedItem("Administrador");
        } else {
            cbUsuAcesso.setSelectedItem("Usuário");
        }

        /*
        tfUsuNom.setBackground(color);
        tfUsuEnd.setBackground(color);
        tfUsuFone.setBackground(color);
        tfUsuLogin.setBackground(color);
        tfUsuCpf.setBackground(color);
        pfUsuSenha.setBackground(color);
        cbUsuAcesso.setBackground(color);
        */

        tfUsuNom.enable(false);
        tfUsuEnd.enable(false);
        tfUsuFone.enable(false);
        tfUsuLogin.enable(false);
        tfUsuCpf.enable(false);
        pfUsuSenha.enable(false);
        
        cbUsuAcesso.setEnabled(false);
        
       
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void lblSetoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSetoresMouseEntered
        // TODO add your handling code here:
        lblSetores.setOpaque(true);
        lblSetores.setBackground(color);
        lblSetores.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblSetoresMouseEntered

    private void lblEntradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntradaMouseEntered
        // TODO add your handling code here:
        lblEntrada.setOpaque(true);
        lblEntrada.setBackground(color);
        lblEntrada.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblEntradaMouseEntered

    private void lblEntradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntradaMouseExited
        // TODO add your handling code here:
        lblEntrada.setOpaque(false);
        lblEntrada.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblEntrada.setForeground(cinza);

    }//GEN-LAST:event_lblEntradaMouseExited

    private void lblSaidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaidaMouseEntered
        // TODO add your handling code here:
        lblSaida.setOpaque(true);
        lblSaida.setBackground(color);
        lblSaida.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblSaidaMouseEntered

    private void lblSaidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSaidaMouseExited
        // TODO add your handling code here:
        lblSaida.setOpaque(false);
        lblSaida.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblSaida.setForeground(cinza);
    }//GEN-LAST:event_lblSaidaMouseExited

    private void lblSetoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSetoresMouseExited
        // TODO add your handling code here:
        lblSetores.setOpaque(false);
        lblSetores.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblSetores.setForeground(cinza);
    }//GEN-LAST:event_lblSetoresMouseExited

    private void lblProdutosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProdutosMouseEntered
        // TODO add your handling code here:
        lblProdutos.setOpaque(true);
        lblProdutos.setBackground(color);
        lblProdutos.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblProdutosMouseEntered

    private void lblProdutosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblProdutosMouseExited
        // TODO add your handling code here:
        lblProdutos.setOpaque(false);
        lblProdutos.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblProdutos.setForeground(cinza);
    }//GEN-LAST:event_lblProdutosMouseExited

    private void lblUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseEntered
        // TODO add your handling code here:
        lblUsuarios.setOpaque(true);
        lblUsuarios.setBackground(color);
        lblUsuarios.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblUsuariosMouseEntered

    private void lblUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUsuariosMouseExited
        // TODO add your handling code here:
        lblUsuarios.setOpaque(false);
        lblUsuarios.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblUsuarios.setForeground(cinza);
    }//GEN-LAST:event_lblUsuariosMouseExited

    private void lblRelatoriosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRelatoriosMouseEntered
        // TODO add your handling code here:
        lblRelatorios.setOpaque(true);
        lblRelatorios.setBackground(color);
        lblRelatorios.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblRelatoriosMouseEntered

    private void lblRelatoriosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRelatoriosMouseExited
        // TODO add your handling code here:
        lblRelatorios.setOpaque(false);
        lblRelatorios.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblRelatorios.setForeground(cinza);

    }//GEN-LAST:event_lblRelatoriosMouseExited

    private void lblInformacoesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInformacoesMouseEntered
        // TODO add your handling code here:
        lblInformacoes.setOpaque(true);
        lblInformacoes.setBackground(color);
        lblInformacoes.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblInformacoesMouseEntered

    private void lblInformacoesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInformacoesMouseExited
        // TODO add your handling code here:
        lblInformacoes.setOpaque(false);
        lblInformacoes.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblInformacoes.setForeground(cinza);

    }//GEN-LAST:event_lblInformacoesMouseExited

    private void lblSairMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSairMouseEntered
        // TODO add your handling code here:
        lblSair.setOpaque(true);
        lblSair.setBackground(color);
        lblSair.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblSairMouseEntered

    private void lblSairMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSairMouseExited
        // TODO add your handling code here:
        lblSair.setOpaque(false);
        lblSair.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblSair.setForeground(cinza);
    }//GEN-LAST:event_lblSairMouseExited

    private void btnLimparUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparUsuarioActionPerformed
        Color color = new Color(255, 255, 255);
        tfUsuNom.setBackground(color);
        tfUsuEnd.setBackground(color);
        tfUsuFone.setBackground(color);
        tfUsuLogin.setBackground(color);
        tfUsuCpf.setBackground(color);
        pfUsuSenha.setBackground(color);
        cbUsuAcesso.setBackground(color);

        tfUsuNom.setText("");
        tfUsuEnd.setText("");
        tfUsuFone.setText("");
        tfUsuLogin.setText("");
        tfUsuCpf.setText("");
        pfUsuSenha.setText("");

        tfUsuNom.enable(true);
        tfUsuEnd.enable(true);
        tfUsuFone.enable(true);
        tfUsuLogin.enable(true);
        btnAddUsu.enable(true);
        tfUsuCpf.enable(true);
        pfUsuSenha.enable(true);
        cbUsuAcesso.setEnabled(true);


    }//GEN-LAST:event_btnLimparUsuarioActionPerformed

    private void ftValorProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftValorProdutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftValorProdutoActionPerformed

    private void btnRegEntradaAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegEntradaAdicionarActionPerformed
        // TODO add your handling code here:
        if (tfRegEntradaCod.getText().isEmpty() || tfRegEntradaQuant.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos antes de adicionar um produto","Campo(s) vazio(s)",JOptionPane.WARNING_MESSAGE);
        } else {

            int cod = Integer.parseInt(tfRegEntradaCod.getText());
            String descricao = cbRegEntradaDescricao.getItemAt(cbRegEntradaDescricao.getSelectedIndex());
            float quantidade = Float.parseFloat(tfRegEntradaQuant.getText());

            NotaEntradaTabelaQueries tabela = new NotaEntradaTabelaQueries();
            Produto prod;
            prod = tabela.selectTabelaCompleta(descricao);
            NotaHistoricoTabela nota = new NotaHistoricoTabela(quantidade, prod.getUnidade(), descricao, prod.getValor(), prod.getValor() * quantidade);
            for (NotaHistoricoTabela tabelaEntrada1 : tabelaEntrada) {
                if(tabelaEntrada1.getDescricao().equals(nota.getDescricao())){
                    JOptionPane.showMessageDialog(null, "Item já existente na tabela!","Produto já foi adicionado na nota",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                    
            }
            tabelaEntrada.add(nota);
            montarTabelaNotaEntrada();
            totalEntrada = 0;
            for (NotaHistoricoTabela tabelaEntrada1 : tabelaEntrada) {
                totalEntrada += tabelaEntrada1.getValorTotal();
            }
            tfRegEntradaTotal.setText(Float.toString(totalEntrada));

        }

        tfRegEntradaQuant.setText("");

    }//GEN-LAST:event_btnRegEntradaAdicionarActionPerformed

    private void btnRegEntradaSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegEntradaSalvarActionPerformed
        // TODO add your handling code here:
        Statement statement;
        try {
            statement = con.createStatement();
            statement.executeUpdate("START TRANSACTION;");
            try {
                listaSetores = (ArrayList<Setor>) setorQueries.getAllSetores();
                int cod = Integer.parseInt(tfRegEntradaCod.getText());
                String setorOrigem = cbRegEntradaOrigem.getItemAt(cbRegEntradaOrigem.getSelectedIndex());
                String setorDestino = cbRegEntradaDest.getItemAt(cbRegEntradaDest.getSelectedIndex());
                NotaEntradaQueries nota = new NotaEntradaQueries();
                int codSetor = 0;
                for (Setor listaSetore : listaSetores) {
                    if(listaSetore.getNomSetor().equals(setorOrigem)){
                        codSetor = listaSetore.getCodSetor();
                    }
                }
                java.util.Date dateEmissao = tfRegEntradaDataEmissao.getDate();
                java.util.Date dateRecebimento = tfRegEntradaDataRecebimento.getDate();
                java.sql.Date dateEmissaosql = new java.sql.Date(dateEmissao.getTime());
                java.sql.Date dateRecebimentosql = new java.sql.Date(dateRecebimento.getTime());
                int result = nota.addNotaEntrada(cod, setorOrigem, setorDestino, totalEntrada, dateEmissaosql, dateRecebimentosql,codSetor,usuarioLogin);
                if(result == -1){
                    return;
                }
                totalEntrada = 0;
                NotaEntradaTabelaQueries tabela = new NotaEntradaTabelaQueries();
                totalEntrada = 0;
                for (NotaHistoricoTabela tabelaEntrada1 : tabelaEntrada) {
                    totalEntrada += tabelaEntrada1.getValorTotal();
                }
                Produto prod;
                for (int j = 0; j < tabelaEntrada.size(); j++) {
                    prod = tabela.selectTabelaCompleta(tabelaEntrada.get(j).getDescricao());
                    int result2 = tabela.addTabelaEntrada(tabelaEntrada.get(j).getQuantidade(), cod, prod.getCodProd(), totalEntrada);
                    ProdutoQueries prodQuery = new ProdutoQueries();
                    prodQuery.updateProduto(prod.getCodProd(), prod.getDescricao(), prod.getUnidade(), prod.getValor(), (tabelaEntrada.get(j).getQuantidade() + prod.getEstoque()));
                }
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Nota cadastrada com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                tfRegEntradaCod.setText("");
                statement.executeUpdate("COMMIT;");
            } catch (SQLException ex) {

                try {
                    statement.executeUpdate("ROLLBACK;");
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Erro ao realizar transação");
                }

            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","Campo(s) vazio(s)",JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {

        }
        totalEntrada = 0;
        tfRegEntradaTotal.setText("");
        tabelaEntrada.clear();
        montarTabelaNotaEntrada();

    }//GEN-LAST:event_btnRegEntradaSalvarActionPerformed

    private void lblContaAcessoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContaAcessoMouseClicked
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(pnlConta);

        UsuarioQueries user_query = new UsuarioQueries();
        String usuario = usuarioLogin;
        Usuario user;
        user = user_query.selectUsuarioLogin(usuarioLogin);
        tfContaNome.setText(user.getNomUser());
        tfContaCpf.setText(user.getCpf());
        tfContaEnd.setText(user.getEndereco());
        tfContaFone.setText(user.getFone());
        tfCOntaUser.setText(user.getLogin());
    }//GEN-LAST:event_lblContaAcessoMouseClicked

    private void btnContaAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContaAlterarActionPerformed
        // TODO add your handling code here:
        if (pfContaSenha.getText().equals(pfContaConfirmaSenha.getText())) {
            UsuarioQueries user_query = new UsuarioQueries();
            String usuario = usuarioLogin;
            int result = user_query.UpdateUsuarioSenha(pfContaSenha.getText(), usuarioLogin);
              ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Conta alterado com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
        } else {
            JOptionPane.showMessageDialog(null, "A senha deve ser igual nos dois campos","Erro",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnContaAlterarActionPerformed

    private void lblContaAcessoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContaAcessoMouseEntered
        // TODO add your handling code here:
        lblContaAcesso.setOpaque(true);
        lblContaAcesso.setBackground(color);
        lblContaAcesso.setForeground(Color.WHITE);

    }//GEN-LAST:event_lblContaAcessoMouseEntered

    private void lblContaAcessoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblContaAcessoMouseExited
        // TODO add your handling code here:
        lblContaAcesso.setOpaque(false);
        lblContaAcesso.setBackground(Color.WHITE);
        Color cinza = new Color(51, 51, 55);
        lblContaAcesso.setForeground(cinza);
    }//GEN-LAST:event_lblContaAcessoMouseExited

    private void tblNotaEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNotaEntradaMouseClicked
        // TODO add your handling code here:
        cbRegEntradaDescricao.setSelectedItem(tblNotaEntrada.getValueAt(tblNotaEntrada.getSelectedRow(), 2).toString());
        tfRegEntradaQuant.setText(tblNotaEntrada.getValueAt(tblNotaEntrada.getSelectedRow(), 0).toString());
        btnRegEntradaDeletar.setVisible(true);

    }//GEN-LAST:event_tblNotaEntradaMouseClicked

    private void btnRegEntradaDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegEntradaDeletarActionPerformed
        // TODO add your handling code here:
        tabelaEntrada.remove(tblNotaEntrada.getSelectedRow());
        totalEntrada = 0;
        for (int i = 0; i < tabelaEntrada.size(); i++) {
            totalEntrada += tabelaEntrada.get(i).getValorTotal();
        }
        tfRegEntradaTotal.setText(Float.toString(totalEntrada));
        montarTabelaNotaEntrada();
        tfRegEntradaQuant.setText("");
        btnRegEntradaDeletar.setVisible(false);
    }//GEN-LAST:event_btnRegEntradaDeletarActionPerformed

    private void tfPesquisaProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaProdutoKeyPressed

        try {
            String qry = "Select * from produto where descricao"
                    + " LIKE'" + tfPesquisaProduto.getText() + "%'";

            Connection conn = MysqlConnect.connectDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            DefaultTableModel modelo = (DefaultTableModel) tblProdutos.getModel();
            modelo.setNumRows(0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getFloat(4),
                    rs.getFloat(5)
                });
            }

            //montarTabelaProduto();
        } catch (SQLException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tfPesquisaProdutoKeyPressed

    private void tfPesquisaProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPesquisaProdutoActionPerformed

    }//GEN-LAST:event_tfPesquisaProdutoActionPerformed

    private void tblProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProdutosMouseClicked
        // Clicar tabela produtos
        tfCodigoProduto.setText("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 0));
        tfDescricaoProduto.setText("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 1));
        ftEstoqueProduto.setText("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 3).toString().replace(".", ","));
        ftValorProduto.setText("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 4).toString().replace(".", ","));

        if ("Unid".equals(tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Unid");
        } else if (tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2) == "Duz") {
            cbbUnidadeProduto.setSelectedItem("Duz");
        } else if ("Kg".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Kg");
        } else if (tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2) == "Lt") {
            cbbUnidadeProduto.setSelectedItem("Lt");
        } else if ("Pct".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Pct");
        } else if ("Arroba".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Arroba");
        } else if ("Pé".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Pé");
        } else if ("Band".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Band");
        } else if ("Ton".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Ton");
        } else if ("Caix".equals("" + tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 2))) {
            cbbUnidadeProduto.setSelectedItem("Caix");
        } else {
            cbbUnidadeProduto.setSelectedItem("Outro");
        }

        ftEstoqueProduto.setEnabled(false);
        ftEstoqueProduto.setBackground(color);
    }//GEN-LAST:event_tblProdutosMouseClicked

    private void tblNotaSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNotaSaidaMouseClicked
        // TODO add your handling code here:
        cbRegSaidaDescricao.setSelectedItem(tblNotaSaida.getValueAt(tblNotaSaida.getSelectedRow(), 2).toString());
        tfRegSaidaQuant.setText(tblNotaSaida.getValueAt(tblNotaSaida.getSelectedRow(), 0).toString());
        btnRegSaidaDeletar.setVisible(true);
    }//GEN-LAST:event_tblNotaSaidaMouseClicked

    private void btnRegSaidaAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegSaidaAdicionarActionPerformed
        if (tfRegSaidaCod.getText().isEmpty() || tfRegSaidaCnpj.getText().isEmpty() || tfRegSaidaQuant.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","Campo(s) vazio(s)",JOptionPane.WARNING_MESSAGE);
        } else {
            int cod = Integer.parseInt(tfRegSaidaCod.getText());
            String natureza = cbRegSaidaNatOp.getItemAt(cbRegSaidaNatOp.getSelectedIndex());
            String razao = cbRegSaidaSetor.getItemAt(cbRegSaidaSetor.getSelectedIndex());
            String cnpj = tfRegSaidaCnpj.getText();
            float quantidade = Float.parseFloat(tfRegSaidaQuant.getText());
            java.util.Date dataEmissao = tfRegSaidaDataEm.getDate();
            java.sql.Date dataEmissaosql = new java.sql.Date(dataEmissao.getTime());
            String descricao = cbRegSaidaDescricao.getItemAt(cbRegSaidaDescricao.getSelectedIndex());
            NotaEntradaTabelaQueries tabela = new NotaEntradaTabelaQueries();
            Produto prod;
            prod = tabela.selectTabelaCompleta(descricao);
            if ((prod.getEstoque() - quantidade) >= 0) {
                NotaHistoricoTabela nota = new NotaHistoricoTabela(quantidade, prod.getUnidade(), descricao, prod.getValor(), prod.getValor() * quantidade);
                for (NotaHistoricoTabela tabelaSaida1 : tabelaSaida) {
                    if(tabelaSaida1.getDescricao().equals(nota.getDescricao())){
                        JOptionPane.showMessageDialog(null, "Item já existente na tabela!","Item cadastrado",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
                tabelaSaida.add(nota);
                montarTabelaNotaSaida();
                totalSaida = 0;
                for (NotaHistoricoTabela tabelaEntrada1 : tabelaSaida) {
                    totalSaida += tabelaEntrada1.getValorTotal();
                }
                tfRegSaidaTotal.setText(Float.toString(totalSaida));
                tfRegSaidaQuant.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Quantidade inexistente no estoque","Valor não permitido",JOptionPane.WARNING_MESSAGE);
                tfRegSaidaQuant.setText("");
            }
        }
    }//GEN-LAST:event_btnRegSaidaAdicionarActionPerformed

    private void btnRegSaidaDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegSaidaDeletarActionPerformed
        // TODO add your handling code here:
        tabelaSaida.remove(tblNotaSaida.getSelectedRow());
        totalSaida = 0;
        for (int i = 0; i < tabelaSaida.size(); i++) {
            totalSaida += tabelaSaida.get(i).getValorTotal();
        }
        tfRegSaidaTotal.setText(Float.toString(totalSaida));
        montarTabelaNotaSaida();
        tfRegSaidaQuant.setText("");
        btnRegEntradaDeletar.setVisible(false);
    }//GEN-LAST:event_btnRegSaidaDeletarActionPerformed

    private void btnRegSaidaSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegSaidaSalvarActionPerformed
        // TODO add your handling code here:
        Statement statement;
        try {
            statement = con.createStatement();
            statement.executeUpdate("START TRANSACTION;");
            try {
                
                int codSaida = Integer.parseInt(tfRegSaidaCod.getText());
                String natureza = cbRegSaidaNatOp.getItemAt(cbRegSaidaNatOp.getSelectedIndex());
                String razao = cbRegSaidaSetor.getItemAt(cbRegSaidaSetor.getSelectedIndex());
                String cnpj = tfRegSaidaCnpj.getText();
                
                int codSetor = 0;
                java.util.Date dataEmissao = tfRegSaidaDataEm.getDate();
                java.sql.Date dataEmissaosql = new java.sql.Date(dataEmissao.getTime());
                String destino = cbRegSaidaSetor.getItemAt(cbRegSaidaSetor.getSelectedIndex());
                              
                NotaSaidaQueries notaSaida = new NotaSaidaQueries();
                listaSetores = (ArrayList<Setor>) setorQueries.getAllSetores();
                for (Setor listaSetore : listaSetores) {
                    if(listaSetore.getNomSetor().equals(destino)){
                        codSetor = listaSetore.getCodSetor();
                    }
                }
                int result = notaSaida.addNotaSaida(codSaida, natureza, razao, cnpj, dataEmissaosql, totalSaida,codSetor,usuarioLogin);
                if(result == -1){
                    return;
                }
                totalSaida = 0;
                NotaSaidaTabelaQueries tabela = new NotaSaidaTabelaQueries();
                Produto prod;
                
                totalSaida = 0;
                for (NotaHistoricoTabela tabelaEntrada1 : tabelaSaida) {
                    totalSaida += tabelaEntrada1.getValorTotal();
                }
                for (int j = 0; j < tabelaSaida.size(); j++) {

                    prod = tabela.selectTabelaCompleta(tabelaSaida.get(j).getDescricao());
                    int result2 = tabela.addTabelaSaida(tabelaSaida.get(j).getQuantidade(), codSaida, prod.getCodProd(), totalSaida);
                    ProdutoQueries prodQuery = new ProdutoQueries();
                    prodQuery.updateProduto(prod.getCodProd(), prod.getDescricao(), prod.getUnidade(), prod.getValor(),
                            (prod.getEstoque() - tabelaSaida.get(j).getQuantidade()));
                }
                ImageIcon icon = new ImageIcon("src/imagens/cadastrado.png");
                JOptionPane.showMessageDialog(null, "Nota cadastrada com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
                tfRegSaidaCod.setText("");
                tfRegSaidaCnpj.setText("");
                statement.executeUpdate("COMMIT;");
            } catch (SQLException ex) {
                try {
                    statement.executeUpdate("ROLLBACK;");
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null, "Erro ao realizar transação");
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!","Campo(s) vazio(s)" ,JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
        }
        totalSaida = 0;
        tfRegSaidaTotal.setText("");
        tabelaSaida.clear();
        montarTabelaNotaSaida();
    }//GEN-LAST:event_btnRegSaidaSalvarActionPerformed

    private void tblSetoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSetoresMouseClicked
        // TODO add your handling code here:
        tfCodigo.setText("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 0));
        tfResponsavel.setText("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 2));
        tfNome.setText("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 1));
        if ("E".equals("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 3))) {
            cbbTipoSetor.setSelectedItem("Entrada");
        } else if ("S".equals("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 3))) {
            cbbTipoSetor.setSelectedItem("Saída");
        } else {
            cbbTipoSetor.setSelectedItem("Entrada/Saída");
        }

        ftCNPJSetor.setText("" + tblSetores.getValueAt(tblSetores.getSelectedRow(), 4));

        ftCNPJSetor.setEnabled(false);
        ftCNPJSetor.setBackground(color);
    }//GEN-LAST:event_tblSetoresMouseClicked

    private void lblRegEntradaHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegEntradaHistoricoMouseClicked
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(PnlHistoricoEntrada);
        montarTabelaHistoricoEntrada();
    }//GEN-LAST:event_lblRegEntradaHistoricoMouseClicked

    private void tblHistoricoEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoricoEntradaMouseClicked
        // TODO add your handling code here:
        cod = Integer.parseInt(tblHistoricoEntrada.getValueAt(tblHistoricoEntrada.getSelectedRow(), 0).toString());
        btnHistoricoEntradaExcluir.setVisible(true);

        ProdutoQueries prodQuery = new ProdutoQueries();
        HistoricoEntradaQueries historico = new HistoricoEntradaQueries();
        NotaHistoricoTabela nota;

        try {
            notaProduto = (ArrayList<NotaProduto>) historico.getAllNotasProduto(cod);

            for (int j = 0; j < notaProduto.size(); j++) {
                nota = historico.getProdutoTabela(notaProduto.get(j).getCodNotaEntrada(), notaProduto.get(j).getCodProdutoEntrada());
                notaTabelaHistorico.add(nota);
            }
        } catch (SQLException ex) {

        }
        tblHistoricoEntradaProduto.setVisible(true);
        btnHistoricoEntradaExcluir.setVisible(true);
        float valorTotal = 0;
        for (int i = 0; i < notaTabelaHistorico.size(); i++) {
            valorTotal = valorTotal + notaTabelaHistorico.get(i).getValorTotal();
        }
        tfHistoricoEntradaTotal.setText(Float.toString(valorTotal));
        montarTabelaVisualizarHistoricoEntrada();
        notaProduto.clear();
        notaTabelaHistorico.clear();
    }//GEN-LAST:event_tblHistoricoEntradaMouseClicked

    private void btnHistoricoEntradaExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoEntradaExcluirActionPerformed
        // TODO add your handling code here:

        ProdutoQueries prodQuery = new ProdutoQueries();
        HistoricoEntradaQueries historico = new HistoricoEntradaQueries();
        Statement statement;
        Produto prod;
        try {
            statement = con.createStatement();
            statement.executeUpdate("START TRANSACTION;");

            notaProduto = (ArrayList<NotaProduto>) historico.getAllNotasProduto(cod);
            for (int i = 0; i < notaProduto.size(); i++) {
                prod = historico.getAllProdutoCompleto(notaProduto.get(i).getCodProdutoEntrada());
                prodQuery.updateProduto(prod.getCodProd(), prod.getDescricao(), prod.getUnidade(), prod.getValor(),
                        prod.getEstoque() - notaProduto.get(i).getQuantidade());
            }

            historico.deletaNota(cod);
            historico.deletaNotaFInal(cod);

            ImageIcon icon = new ImageIcon("src/imagens/excluido.png");
               JOptionPane.showMessageDialog(null, "Nota excluída com sucesso", "", JOptionPane.PLAIN_MESSAGE, icon);
            statement.executeUpdate("COMMIT;");
        } catch (SQLException ex) {
            try {
                statement = con.createStatement();
                statement.executeUpdate("ROLLBACK;");
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Erro ao realizar transação");
            }

        }
        btnHistoricoEntradaExcluir.setVisible(false);
        tblHistoricoEntradaProduto.setVisible(false);
        tfHistoricoEntradaTotal.setText("0");
        notaProduto.clear();
        montarTabelaHistoricoEntrada();
        montarTabelaVisualizarHistoricoEntrada();
    }//GEN-LAST:event_btnHistoricoEntradaExcluirActionPerformed

    private void tfHistoricoEntradaPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHistoricoEntradaPesquisarKeyPressed
        // TODO add your handling code here:
        String qry = "SELECT codNotaEntrada FROM notaProdutoEntrada WHERE codNotaEntrada"
                + " LIKE'" + tfHistoricoEntradaPesquisar.getText() + "%'";

        try {
            Connection conn = MysqlConnect.connectDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            DefaultTableModel modelo = (DefaultTableModel) tblHistoricoEntrada.getModel();
            modelo.setNumRows(0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt(1)
                });
            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_tfHistoricoEntradaPesquisarKeyPressed

    private void tblHistoricoSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHistoricoSaidaMouseClicked
        // TODO add your handling code here:
        cod = Integer.parseInt(tblHistoricoSaida.getValueAt(tblHistoricoSaida.getSelectedRow(), 0).toString());
        btnHistoricoSaidaExcluir.setVisible(true);
        ProdutoQueries prodQuery = new ProdutoQueries();
        HistoricoSaidaQueries historico = new HistoricoSaidaQueries();
        NotaHistoricoTabela nota;

        try {
            notaProduto = (ArrayList<NotaProduto>) historico.getAllNotasProduto(cod);
            for (int j = 0; j < notaProduto.size(); j++) {
                nota = historico.getProdutoTabela(notaProduto.get(j).getCodNotaEntrada(), notaProduto.get(j).getCodProdutoEntrada());
                notaTabelaHistorico.add(nota);
            }
        } catch (SQLException ex) {

        }
        tblHistoricoSaidaProduto.setVisible(true);
        btnHistoricoSaidaExcluir.setVisible(true);
        float valorTotal = 0;
        for (int i = 0; i < notaTabelaHistorico.size(); i++) {
            valorTotal = valorTotal + notaTabelaHistorico.get(i).getValorTotal();
        }
        tfHistoricoSaidaTotal.setText(Float.toString(valorTotal));
        montarTabelaVisualizarHistoricoSaida();
        notaProduto.clear();
        notaTabelaHistorico.clear();
    }//GEN-LAST:event_tblHistoricoSaidaMouseClicked

    private void btnHistoricoSaidaExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoSaidaExcluirActionPerformed
        // TODO add your handling code here:
        ProdutoQueries prodQuery = new ProdutoQueries();
        HistoricoSaidaQueries historico = new HistoricoSaidaQueries();
        Statement statement;
        Produto prod;
        try {
            statement = con.createStatement();
            statement.executeUpdate("START TRANSACTION;");

            notaProduto = (ArrayList<NotaProduto>) historico.getAllNotasProduto(cod);
            for (int i = 0; i < notaProduto.size(); i++) {
                prod = historico.getAllProdutoCompleto(notaProduto.get(i).getCodProdutoEntrada());
                prodQuery.updateProduto(prod.getCodProd(), prod.getDescricao(), prod.getUnidade(), prod.getValor(), prod.getEstoque() + notaProduto.get(i).getQuantidade());
            }

            historico.deletaNota(cod);
            historico.deletaNotaFInal(cod);

            JOptionPane.showMessageDialog(this, "Registro excluido");
            statement.executeUpdate("COMMIT;");
        } catch (SQLException ex) {
            try {
                statement = con.createStatement();
                statement.executeUpdate("ROLLBACK;");
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, "Erro ao realizar transação");
            }

        }
        btnHistoricoSaidaExcluir.setVisible(false);
        tblHistoricoSaidaProduto.setVisible(false);
        tfHistoricoSaidaTotal.setText("0");
        notaProduto.clear();
        montarTabelaHistoricoSaida();
        montarTabelaVisualizarHistoricoSaida();
    }//GEN-LAST:event_btnHistoricoSaidaExcluirActionPerformed

    private void tfHistoricoSaidaPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHistoricoSaidaPesquisarKeyPressed
        // TODO add your handling code here:
        String qry = "SELECT codNotaSaida FROM notaProdutoSaida WHERE codNotaSaida"
                + " LIKE'" + tfHistoricoSaidaPesquisar.getText() + "%'";

        try {
            Connection conn = MysqlConnect.connectDB();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(qry);

            DefaultTableModel modelo = (DefaultTableModel) tblHistoricoSaida.getModel();
            modelo.setNumRows(0);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt(1)
                });
            }
        } catch (SQLException ex) {

        }
    }//GEN-LAST:event_tfHistoricoSaidaPesquisarKeyPressed

    private void lblRegSaidaHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegSaidaHistoricoMouseClicked
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(PnlHistoricoSaida);
        montarTabelaHistoricoSaida();
    }//GEN-LAST:event_lblRegSaidaHistoricoMouseClicked

    private void lblRegEntradaHistoricoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegEntradaHistoricoMouseEntered
        // TODO add your handling code here:
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_lblRegEntradaHistoricoMouseEntered

    private void lblRegEntradaHistoricoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegEntradaHistoricoMouseExited
        // TODO add your handling code here:
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_lblRegEntradaHistoricoMouseExited

    private void lblRegSaidaHistoricoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegSaidaHistoricoMouseEntered
        // TODO add your handling code here:
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_lblRegSaidaHistoricoMouseEntered

    private void lblRegSaidaHistoricoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegSaidaHistoricoMouseExited
        // TODO add your handling code here:
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_lblRegSaidaHistoricoMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(pnlRegSaida);
        tfRegSaidaCod.setText("");
        tfRegSaidaCnpj.setText("");
        totalSaida = 0;
        tfRegSaidaTotal.setText("");
        tabelaSaida.clear();
        montarTabelaNotaSaida();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pnlPaineis.setSelectedComponent(pnlRegEntrada);
        tfRegEntradaCod.setText("");
        totalEntrada = 0;
        tfRegEntradaTotal.setText("");
        tabelaEntrada.clear();
        montarTabelaNotaEntrada();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnRelatorioGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRelatorioGerarActionPerformed
        if (cbOpcaoRelatorio.getSelectedIndex() == 1){
            
                if(cbbRelatorioTipoNota.getSelectedIndex() == 1){
                    if (cbRelatorioSetor.isSelected() && 
                         !cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()){
                        
                        gerarRelatorio2();
                        
                    } else if(!cbRelatorioSetor.isSelected() && 
                            cbRelatorioProduto.isSelected()
                            && !cbRelatorioData.isSelected()){
                        
                        gerarRelatorio1();
                        
                    } else if (!cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                            && cbRelatorioData.isSelected()){
                        
                        gerarRelatorio3();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                            && !cbRelatorioData.isSelected()){
                        
                        gerarRelatorio4();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                            && cbRelatorioData.isSelected()){
                        
                        gerarRelatorio5();
                    } else if (!cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                            && cbRelatorioData.isSelected()){
                        
                        gerarRelatorio6();        
                    } else if (cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                            && cbRelatorioData.isSelected()){
                        
                        gerarRelatorio7();
                        
                    } else {
                        gerarRelatorio23();
                    }
                    

                } else if (cbbRelatorioTipoNota.getSelectedIndex() == 2){
                    if(!cbRelatorioSetor.isSelected() && 
                         cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                            && !cbRelatorioNatOp.isSelected()){
                        gerarRelatorio8();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                         !cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && !cbRelatorioNatOp.isSelected() ){
                        
                        gerarRelatorio9();
                    } else if(!cbRelatorioSetor.isSelected() && 
                         !cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && !cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio10();
                        
                    } else if (!cbRelatorioSetor.isSelected() && 
                         !cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio11();
                        
                    } else if(cbRelatorioSetor.isSelected() && 
                         cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio12();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()) {
                        
                        gerarRelatorio13();
                    } else if (!cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && !cbRelatorioNatOp.isSelected())  {
                        
                        gerarRelatorio14();
                    } else if(cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio15();
                        
                    } else if (!cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio16();
                        
                    } else if (!cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        gerarRelatorio17();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          !cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio18();
                        
                    } else if (!cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio19();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && !cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio20();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && !cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio21();
                        
                    } else if (cbRelatorioSetor.isSelected() && 
                          cbRelatorioProduto.isSelected()
                          && cbRelatorioData.isSelected()
                          && cbRelatorioNatOp.isSelected()){
                        
                        gerarRelatorio22();
                    } else {
                        gerarRelatorio24();
                    }

                } else {
                    
                    JOptionPane.showMessageDialog(null,"Por favor, selecione o tipo da nota para gerar o relatório", "Tipo da nota não informada", JOptionPane.ERROR_MESSAGE);

                }
            
        } else if (cbOpcaoRelatorio.getSelectedIndex() == 2){
            if (cbbRelatorioTipoDaNota.getSelectedIndex() == 0
                 || tfRelatorioCodNota.getText().isEmpty() ){
                JOptionPane.showMessageDialog(null,"Por favor, preencha os campos para gerar o relatório", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
                
            } else {
                if (cbbRelatorioTipoDaNota.getSelectedIndex() == 1){
                    gerarRelatorio25();
                } else {
                    gerarRelatorio26();
                }
            }
            
            
        } else {
            JOptionPane.showMessageDialog(null,"Por favor, selecione a forma que deseja gerar o relatório", "Opção de relatório não reconhecida", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }//GEN-LAST:event_btnRelatorioGerarActionPerformed

    private void cbbRelatorioTipoNotaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbRelatorioTipoNotaItemStateChanged

        int index = cbbRelatorioTipoNota.getSelectedIndex();
        if (index == 1){
            try {
                cbbRelatorioNatOp.setVisible(false);
                cbRelatorioNatOp.setVisible(false);
                cbbRelatorioSetor.removeAllItems();
                montarComboBoxRelatorioSetorEntrada();
            } catch (SQLException ex) {
                Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (index == 2){
            try {
                cbbRelatorioNatOp.setVisible(true);
                cbRelatorioNatOp.setVisible(true);
                cbbRelatorioSetor.removeAllItems();
                montarComboBoxRelatorioSetorSaida();
            } catch (SQLException ex) {
                Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            cbbRelatorioNatOp.setVisible(false);
            cbRelatorioNatOp.setVisible(false);
            cbbRelatorioSetor.removeAllItems();
        }
    }//GEN-LAST:event_cbbRelatorioTipoNotaItemStateChanged

    private void cbOpcaoRelatorioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbOpcaoRelatorioItemStateChanged
        int indice = cbOpcaoRelatorio.getSelectedIndex();
        if (indice == 1){
            habilitarFiltrosRelatorio();
            desabCamposRelatorioPorCod();
        } else if (indice == 2){
            desabilitarFiltrosRelatorio();
            habCamposRelatorioPorCod();
        } else {
            desabilitarFiltrosRelatorio();
            desabCamposRelatorioPorCod();
        }
        
    }//GEN-LAST:event_cbOpcaoRelatorioItemStateChanged

    private void lblAjudaProdutosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaProdutosMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_lblAjudaProdutosMouseEntered

    private void lblAjudaProdutosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaProdutosMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_lblAjudaProdutosMouseExited

    private void lblAjudaProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaProdutosMouseClicked
        pnlPaineis.setSelectedComponent(pnlAjudaProdutos);
    }//GEN-LAST:event_lblAjudaProdutosMouseClicked

    private void brnAjudaVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarActionPerformed
        pnlPaineis.setSelectedComponent(pnlProdutos);
    }//GEN-LAST:event_brnAjudaVoltarActionPerformed

    private void lblLimparCamposMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimparCamposMouseEntered
        
    }//GEN-LAST:event_lblLimparCamposMouseEntered

    private void lblLimparCamposMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimparCamposMouseExited
        
    }//GEN-LAST:event_lblLimparCamposMouseExited

    private void verDemoCadProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadProdMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoCadProdMouseEntered

    private void verDemoCadProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadProdMouseExited
         this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoCadProdMouseExited

    private void verDemoAltProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltProdMouseClicked
       AlterarProduto ap = new AlterarProduto();
       ap.setVisible(true);
    }//GEN-LAST:event_verDemoAltProdMouseClicked

    private void verDemoAltProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltProdMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAltProdMouseEntered

    private void verDemoExcProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcProdMouseEntered

    private void verDemoPesqProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqProdMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoPesqProdMouseEntered

    private void verDemoPesqProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqProdMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoPesqProdMouseExited

    private void verDemoLimparCamposProdMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposProdMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoLimparCamposProdMouseEntered

    private void verDemoLimparCamposProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposProdMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoLimparCamposProdMouseExited

    private void verDemoAltProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltProdMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAltProdMouseExited

    private void lblAjudaSetoresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaSetoresMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_lblAjudaSetoresMouseEntered

    private void lblAjudaSetoresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaSetoresMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_lblAjudaSetoresMouseExited

    private void verDemoExcProdMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdMouseExited
       this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcProdMouseExited

    private void verDemoCadProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadProdMouseClicked
        CadastrarProduto cd = new CadastrarProduto();
        cd.setVisible(true);
    }//GEN-LAST:event_verDemoCadProdMouseClicked

    private void verDemoExcProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdMouseClicked
        ExcluirProduto ep = new ExcluirProduto();
        ep.setVisible(true);
    }//GEN-LAST:event_verDemoExcProdMouseClicked

    private void verDemoLimparCamposProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposProdMouseClicked
        LimparProduto lp = new LimparProduto();
        lp.setVisible(true);
    }//GEN-LAST:event_verDemoLimparCamposProdMouseClicked

    private void verDemoPesqProdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqProdMouseClicked
       PesquisarProduto pp = new PesquisarProduto();
       pp.setVisible(true);
    }//GEN-LAST:event_verDemoPesqProdMouseClicked

    private void brnAjudaVoltar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brnAjudaVoltar1ActionPerformed

    private void lblLimparCamposSetorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimparCamposSetorMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLimparCamposSetorMouseEntered

    private void lblLimparCamposSetorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimparCamposSetorMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblLimparCamposSetorMouseExited

    private void verDemoCadSetorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadSetorMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoCadSetorMouseEntered

    private void verDemoCadSetorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadSetorMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoCadSetorMouseExited

    private void verDemoAltSetorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSetorMouseClicked
        AlterarSetor as = new AlterarSetor();
        as.setVisible(true);
    }//GEN-LAST:event_verDemoAltSetorMouseClicked

    private void verDemoAltSetorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSetorMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAltSetorMouseEntered

    private void verDemoAltSetorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSetorMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAltSetorMouseExited

    private void verDemoExcSetorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcSetorMouseClicked
        ExcluirSetor es = new ExcluirSetor();
        es.setVisible(true);
    }//GEN-LAST:event_verDemoExcSetorMouseClicked

    private void verDemoExcSetorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcSetorMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcSetorMouseEntered

    private void verDemoExcSetorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcSetorMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcSetorMouseExited

    private void verDemoLimparCamposSetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposSetMouseClicked
        LimparSetor ls = new LimparSetor();
        ls.setVisible(true);
    }//GEN-LAST:event_verDemoLimparCamposSetMouseClicked

    private void verDemoLimparCamposSetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposSetMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoLimparCamposSetMouseEntered

    private void verDemoLimparCamposSetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparCamposSetMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoLimparCamposSetMouseExited

    private void verDemoPesqSetorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqSetorMouseClicked
       PesquisarSetor ps = new PesquisarSetor();
       ps.setVisible(true);
    }//GEN-LAST:event_verDemoPesqSetorMouseClicked

    private void verDemoPesqSetorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqSetorMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoPesqSetorMouseEntered

    private void verDemoPesqSetorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqSetorMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoPesqSetorMouseExited

    private void lblAjudaSetoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaSetoresMouseClicked
        pnlPaineis.setSelectedComponent(pnlAjudaSetores);
    }//GEN-LAST:event_lblAjudaSetoresMouseClicked

    private void brnAjudaVoltar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnAjudaVoltar1MouseClicked
        pnlPaineis.setSelectedComponent(pnlSetores);
    }//GEN-LAST:event_brnAjudaVoltar1MouseClicked

    private void verDemoCadSetorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadSetorMouseClicked
        CadastrarSetor cs = new CadastrarSetor();
        cs.setVisible(true);
    }//GEN-LAST:event_verDemoCadSetorMouseClicked

    private void btnAjudaRegistroEntradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroEntradaMouseEntered
       this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_btnAjudaRegistroEntradaMouseEntered

    private void btnAjudaRegistroEntradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroEntradaMouseExited
       this.setCursor(Cursor.DEFAULT_CURSOR);

    }//GEN-LAST:event_btnAjudaRegistroEntradaMouseExited

    private void brnAjudaVoltarRegEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRegEntradaActionPerformed
        pnlPaineis.setSelectedComponent(pnlRegEntrada);
    }//GEN-LAST:event_brnAjudaVoltarRegEntradaActionPerformed

    private void lblAcessarHisNotEntMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAcessarHisNotEntMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAcessarHisNotEntMouseEntered

    private void lblAcessarHisNotEntMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAcessarHisNotEntMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAcessarHisNotEntMouseExited

    private void verDemoCadNotaEntMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaEntMouseClicked
        ComoCadastrarNota cdn = new ComoCadastrarNota();
        cdn.setVisible(true);
    }//GEN-LAST:event_verDemoCadNotaEntMouseClicked

    private void verDemoCadNotaEntMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaEntMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoCadNotaEntMouseEntered

    private void verDemoCadNotaEntMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaEntMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoCadNotaEntMouseExited

    private void verDemoAddNotaEntMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAddNotaEntMouseClicked
        AdicionarProdutoNotaEntrada apne = new AdicionarProdutoNotaEntrada();
        apne.setVisible(true);
    }//GEN-LAST:event_verDemoAddNotaEntMouseClicked

    private void verDemoAddNotaEntMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAddNotaEntMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAddNotaEntMouseEntered

    private void verDemoAddNotaEntMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAddNotaEntMouseExited
       this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAddNotaEntMouseExited

    private void verDemoExcProdNotaEntMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdNotaEntMouseClicked
        RemoverProdutoNotaEntrada rpne = new RemoverProdutoNotaEntrada();
        rpne.setVisible(true);
    }//GEN-LAST:event_verDemoExcProdNotaEntMouseClicked

    private void verDemoExcProdNotaEntMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdNotaEntMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcProdNotaEntMouseEntered

    private void verDemoExcProdNotaEntMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdNotaEntMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcProdNotaEntMouseExited

    private void verDemoAcessarHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoMouseClicked
         VerHistoricoNotaEntrada vhne = new VerHistoricoNotaEntrada();
         vhne.setVisible(true);
         
    }//GEN-LAST:event_verDemoAcessarHistoricoMouseClicked

    private void verDemoAcessarHistoricoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAcessarHistoricoMouseEntered

    private void verDemoAcessarHistoricoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAcessarHistoricoMouseExited

    private void verDemoExcluirNotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcluirNotaMouseClicked
        ExcluirNotaEntrada ene = new ExcluirNotaEntrada();
        ene.setVisible(true);
    }//GEN-LAST:event_verDemoExcluirNotaMouseClicked

    private void verDemoExcluirNotaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcluirNotaMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcluirNotaMouseEntered

    private void verDemoExcluirNotaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcluirNotaMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcluirNotaMouseExited

    private void btnAjudaRegistroEntradaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroEntradaMouseClicked
       pnlPaineis.setSelectedComponent(pnlAjudaRegistroEntrada);
    }//GEN-LAST:event_btnAjudaRegistroEntradaMouseClicked

    private void brnAjudaVoltarRegistroSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRegistroSaidaActionPerformed
        pnlPaineis.setSelectedComponent(pnlRegSaida);
    }//GEN-LAST:event_brnAjudaVoltarRegistroSaidaActionPerformed

    private void lblHistoricoNotasSaidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoricoNotasSaidaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHistoricoNotasSaidaMouseEntered

    private void lblHistoricoNotasSaidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHistoricoNotasSaidaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHistoricoNotasSaidaMouseExited

    private void verDemoCadNotaSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaSaidaMouseClicked
        ComoCadastrarNotaSaida cns = new ComoCadastrarNotaSaida();
        cns.setVisible(true);
    }//GEN-LAST:event_verDemoCadNotaSaidaMouseClicked

    private void verDemoCadNotaSaidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaSaidaMouseEntered
       this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoCadNotaSaidaMouseEntered

    private void verDemoCadNotaSaidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadNotaSaidaMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoCadNotaSaidaMouseExited

    private void verDemoAdcProdutoNSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAdcProdutoNSMouseClicked
        AdicionarProdutoNotaSaida apns = new AdicionarProdutoNotaSaida();
        apns.setVisible(true);
    }//GEN-LAST:event_verDemoAdcProdutoNSMouseClicked

    private void verDemoAdcProdutoNSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAdcProdutoNSMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAdcProdutoNSMouseEntered

    private void verDemoAdcProdutoNSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAdcProdutoNSMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAdcProdutoNSMouseExited

    private void verDemoExcProdutoNSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdutoNSMouseClicked
        RemoverProdutoNotaSaida rpns = new RemoverProdutoNotaSaida();
        rpns.setVisible(true);
    }//GEN-LAST:event_verDemoExcProdutoNSMouseClicked

    private void verDemoExcProdutoNSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdutoNSMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcProdutoNSMouseEntered

    private void verDemoExcProdutoNSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcProdutoNSMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcProdutoNSMouseExited

    private void verDemoAcessarHistoricoNSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoNSMouseClicked
        VerHistoricoNotaSaida vhns = new VerHistoricoNotaSaida();
        vhns.setVisible(true);
    }//GEN-LAST:event_verDemoAcessarHistoricoNSMouseClicked

    private void verDemoAcessarHistoricoNSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoNSMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAcessarHistoricoNSMouseEntered

    private void verDemoAcessarHistoricoNSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAcessarHistoricoNSMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAcessarHistoricoNSMouseExited

    private void verDemoExcNotaSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcNotaSMouseClicked
        ExcluirNotaSaida ens = new ExcluirNotaSaida();
        ens.setVisible(true);
    }//GEN-LAST:event_verDemoExcNotaSMouseClicked

    private void verDemoExcNotaSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcNotaSMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcNotaSMouseEntered

    private void verDemoExcNotaSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcNotaSMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcNotaSMouseExited

    private void btnAjudaRegistroSaidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroSaidaMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_btnAjudaRegistroSaidaMouseEntered

    private void btnAjudaRegistroSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroSaidaMouseClicked
        pnlPaineis.setSelectedComponent(pnlAjudaRegistroSaida);
    }//GEN-LAST:event_btnAjudaRegistroSaidaMouseClicked

    private void btnAjudaRegistroSaidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaRegistroSaidaMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_btnAjudaRegistroSaidaMouseExited

    private void brnAjudaVoltarRegistroSaidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRegistroSaidaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_brnAjudaVoltarRegistroSaidaMouseClicked

    private void btnAjudaAltSenhaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaAltSenhaMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_btnAjudaAltSenhaMouseEntered

    private void btnAjudaAltSenhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaAltSenhaMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_btnAjudaAltSenhaMouseExited

    private void brnAjudaVoltarAltSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnAjudaVoltarAltSenhaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_brnAjudaVoltarAltSenhaMouseClicked

    private void brnAjudaVoltarAltSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarAltSenhaActionPerformed
        pnlPaineis.setSelectedComponent(pnlConta);
    }//GEN-LAST:event_brnAjudaVoltarAltSenhaActionPerformed

    private void verDemoAltSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSenhaMouseClicked
        AlterarSenhaDemo as = new AlterarSenhaDemo();
        as.setVisible(true);
    }//GEN-LAST:event_verDemoAltSenhaMouseClicked

    private void verDemoAltSenhaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSenhaMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoAltSenhaMouseEntered

    private void verDemoAltSenhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoAltSenhaMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoAltSenhaMouseExited

    private void btnAjudaAltSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAjudaAltSenhaMouseClicked
       pnlPaineis.setSelectedComponent(pnlAjudaAlterarSenha);
    }//GEN-LAST:event_btnAjudaAltSenhaMouseClicked

    private void brnAjudaVoltarRegSaida2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRegSaida2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_brnAjudaVoltarRegSaida2MouseClicked

    private void brnAjudaVoltarRegSaida2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRegSaida2ActionPerformed
        pnlPaineis.setSelectedComponent(pnlUsuarios);
    }//GEN-LAST:event_brnAjudaVoltarRegSaida2ActionPerformed

    private void verDemoCadUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadUsuarioMouseClicked
       CadastrarUsuario cu = new CadastrarUsuario();
       cu.setVisible(true);
    }//GEN-LAST:event_verDemoCadUsuarioMouseClicked

    private void verDemoCadUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadUsuarioMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoCadUsuarioMouseEntered

    private void verDemoCadUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoCadUsuarioMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoCadUsuarioMouseExited

    private void verDemoExcUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcUsuarioMouseClicked
        ExcluirUsuario eu = new ExcluirUsuario();
        eu.setVisible(true);
    }//GEN-LAST:event_verDemoExcUsuarioMouseClicked

    private void verDemoExcUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcUsuarioMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoExcUsuarioMouseEntered

    private void verDemoExcUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoExcUsuarioMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoExcUsuarioMouseExited

    private void verDemoLimparUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparUsuariosMouseClicked
        LimparUsuarios lu = new LimparUsuarios();
        lu.setVisible(true);
    }//GEN-LAST:event_verDemoLimparUsuariosMouseClicked

    private void verDemoLimparUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparUsuariosMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoLimparUsuariosMouseEntered

    private void verDemoLimparUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoLimparUsuariosMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoLimparUsuariosMouseExited

    private void verDemoPesqUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqUsMouseClicked
        PesquisarUsuario pu = new PesquisarUsuario();
        pu.setVisible(true);
    }//GEN-LAST:event_verDemoPesqUsMouseClicked

    private void verDemoPesqUsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqUsMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoPesqUsMouseEntered

    private void verDemoPesqUsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoPesqUsMouseExited
       this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoPesqUsMouseExited

    private void brnAjudaVoltarRelatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRelatoriosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_brnAjudaVoltarRelatoriosMouseClicked

    private void brnAjudaVoltarRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brnAjudaVoltarRelatoriosActionPerformed
        pnlPaineis.setSelectedComponent(pnlUsuarios);
    }//GEN-LAST:event_brnAjudaVoltarRelatoriosActionPerformed

    private void verDemoRelatoriosFiltrosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatoriosFiltrosMouseClicked
        GerarRelatoriosPorFiltros grf = new GerarRelatoriosPorFiltros ();
        grf.setVisible(true);
    }//GEN-LAST:event_verDemoRelatoriosFiltrosMouseClicked

    private void verDemoRelatoriosFiltrosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatoriosFiltrosMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoRelatoriosFiltrosMouseEntered

    private void verDemoRelatoriosFiltrosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatoriosFiltrosMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoRelatoriosFiltrosMouseExited

    private void verDemoRelatorioCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatorioCodigoMouseClicked
        GerarRelatoriosPorCodigo grc = new GerarRelatoriosPorCodigo ();
        grc.setVisible(true);
    }//GEN-LAST:event_verDemoRelatorioCodigoMouseClicked

    private void verDemoRelatorioCodigoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatorioCodigoMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_verDemoRelatorioCodigoMouseEntered

    private void verDemoRelatorioCodigoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verDemoRelatorioCodigoMouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_verDemoRelatorioCodigoMouseExited

    private void jLabel34MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_jLabel34MouseEntered

    private void jLabel34MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseExited
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_jLabel34MouseExited

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        pnlPaineis.setSelectedComponent(pnlAjudaRelatorios);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void lblAjudaUsuariosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaUsuariosMouseEntered
        this.setCursor(Cursor.HAND_CURSOR);
    }//GEN-LAST:event_lblAjudaUsuariosMouseEntered

    private void lblAjudaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaUsuariosMouseClicked
        pnlPaineis.setSelectedComponent(pnlAjudaUusuarios);
    }//GEN-LAST:event_lblAjudaUsuariosMouseClicked

    private void lblAjudaUsuariosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAjudaUsuariosMouseExited
       this.setCursor(Cursor.DEFAULT_CURSOR);
    }//GEN-LAST:event_lblAjudaUsuariosMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sicoin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sicoin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sicoin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sicoin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Sicoin().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Sicoin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlHistoricoEntrada;
    private javax.swing.JPanel PnlHistoricoSaida;
    private javax.swing.JButton brnAjudaVoltar;
    private javax.swing.JButton brnAjudaVoltar1;
    private javax.swing.JButton brnAjudaVoltarAltSenha;
    private javax.swing.JButton brnAjudaVoltarRegEntrada;
    private javax.swing.JButton brnAjudaVoltarRegSaida2;
    private javax.swing.JButton brnAjudaVoltarRegistroSaida;
    private javax.swing.JButton brnAjudaVoltarRelatorios;
    private javax.swing.JButton btnAddUsu;
    private javax.swing.JLabel btnAjudaAltSenha;
    private javax.swing.JLabel btnAjudaRegistroEntrada;
    private javax.swing.JLabel btnAjudaRegistroSaida;
    private javax.swing.JButton btnAlterarProduto;
    private javax.swing.JButton btnAlterarSetor;
    private javax.swing.JButton btnCadastrarProduto;
    private javax.swing.JButton btnCadastrarSetor;
    private javax.swing.JButton btnContaAlterar;
    private javax.swing.JButton btnExcluirProduto;
    private javax.swing.JButton btnExcluirSetor;
    private javax.swing.JButton btnHistoricoEntradaExcluir;
    private javax.swing.JButton btnHistoricoSaidaExcluir;
    private javax.swing.JButton btnLimparProduto;
    private javax.swing.JButton btnLimparSetor;
    private javax.swing.JButton btnLimparUsuario;
    private javax.swing.JButton btnRegEntradaAdicionar;
    private javax.swing.JButton btnRegEntradaDeletar;
    private javax.swing.JButton btnRegEntradaSalvar;
    private javax.swing.JButton btnRegSaidaAdicionar;
    private javax.swing.JButton btnRegSaidaDeletar;
    private javax.swing.JButton btnRegSaidaSalvar;
    private javax.swing.JButton btnRelatorioGerar;
    private javax.swing.JButton btnUsuExc;
    private javax.swing.JComboBox<String> cbOpcaoRelatorio;
    private javax.swing.JComboBox<String> cbRegEntradaDescricao;
    private javax.swing.JComboBox<String> cbRegEntradaDest;
    private javax.swing.JComboBox<String> cbRegEntradaOrigem;
    private javax.swing.JComboBox<String> cbRegSaidaDescricao;
    private javax.swing.JComboBox<String> cbRegSaidaNatOp;
    private javax.swing.JComboBox<String> cbRegSaidaSetor;
    private javax.swing.JCheckBox cbRelatorioData;
    private javax.swing.JCheckBox cbRelatorioNatOp;
    private javax.swing.JCheckBox cbRelatorioProduto;
    private javax.swing.JCheckBox cbRelatorioSetor;
    private javax.swing.JComboBox<String> cbUsuAcesso;
    private javax.swing.JComboBox<String> cbbRelatorioNatOp;
    private javax.swing.JComboBox<String> cbbRelatorioProduto;
    private javax.swing.JComboBox<String> cbbRelatorioSetor;
    private javax.swing.JComboBox<String> cbbRelatorioTipoDaNota;
    private javax.swing.JComboBox<String> cbbRelatorioTipoNota;
    private javax.swing.JComboBox<String> cbbTipoSetor;
    private javax.swing.JComboBox<String> cbbUnidadeProduto;
    private javax.swing.JFormattedTextField ftCNPJSetor;
    private javax.swing.JFormattedTextField ftEstoqueProduto;
    private javax.swing.JFormattedTextField ftRelatorioAte;
    private javax.swing.JFormattedTextField ftRelatorioDe;
    private javax.swing.JFormattedTextField ftValorProduto;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbLimparUsAjuda;
    private javax.swing.JLabel lblAcessarHisNotEnt;
    private javax.swing.JLabel lblAdicionarProdutoNotaS;
    private javax.swing.JLabel lblAjudaProdutos;
    private javax.swing.JLabel lblAjudaSetores;
    private javax.swing.JLabel lblAjudaUsuarios;
    private javax.swing.JLabel lblAlddNotaEntrada;
    private javax.swing.JLabel lblAlterarProduto;
    private javax.swing.JLabel lblAlterarSenhaAjuda;
    private javax.swing.JLabel lblAlterarSetor;
    private javax.swing.JLabel lblCNPJNotaSaida;
    private javax.swing.JLabel lblCadastrarNotaEnt;
    private javax.swing.JLabel lblCadastrarNotaSaidaAjuda;
    private javax.swing.JLabel lblCadastrarProdutoAjuda;
    private javax.swing.JLabel lblCadastrarSetorAjuda;
    private javax.swing.JLabel lblCadastrarUusuarioAjuda;
    private javax.swing.JLabel lblCodigoNotaSaida;
    private javax.swing.JLabel lblComoAlterarSenha;
    private javax.swing.JLabel lblComoCadastrarNotaEnt;
    private javax.swing.JLabel lblComoCadastrarNotaSaida;
    private javax.swing.JLabel lblComoCadastrarProduto;
    private javax.swing.JLabel lblComoCadastrarSetor;
    private javax.swing.JLabel lblComoCadastrarUsuario;
    private javax.swing.JLabel lblComoExcluirUusuario;
    private javax.swing.JLabel lblComoGerarRelatorioCodigo;
    private javax.swing.JLabel lblComoPesquisarNotEnt;
    private javax.swing.JLabel lblComoPesquisarProduto;
    private javax.swing.JLabel lblComoPesquisarSetor;
    private javax.swing.JLabel lblComoPesquisarUsuario;
    private javax.swing.JLabel lblContaAcesso;
    private javax.swing.JLabel lblContaConfirmaSenha;
    private javax.swing.JLabel lblContaCpf;
    private javax.swing.JLabel lblContaDados;
    private javax.swing.JLabel lblContaEnd;
    private javax.swing.JLabel lblContaFone;
    private javax.swing.JLabel lblContaNome;
    private javax.swing.JLabel lblContaSenha;
    private javax.swing.JLabel lblContaUser;
    private javax.swing.JLabel lblDataEmissaoNotaSaida;
    private javax.swing.JLabel lblEntrada;
    private javax.swing.JLabel lblExcluirNotaEnt;
    private javax.swing.JLabel lblExcluirNotaSCadastrada;
    private javax.swing.JLabel lblExcluirProduto;
    private javax.swing.JLabel lblExcluirProdutoNS;
    private javax.swing.JLabel lblExcluirSetor;
    private javax.swing.JLabel lblExcluirUsuarioAjuda;
    private javax.swing.JLabel lblFuncionamentoRelatorios;
    private javax.swing.JLabel lblGerarRelatorioPor;
    private javax.swing.JLabel lblHistoricoEntrada;
    private javax.swing.JLabel lblHistoricoEntradaPesquisar;
    private javax.swing.JLabel lblHistoricoNotasSaida;
    private javax.swing.JLabel lblHistoricoSaida;
    private javax.swing.JLabel lblHistoricoSaidaPesquisar;
    private javax.swing.JLabel lblInformacoes;
    private javax.swing.JLabel lblLimparCampos;
    private javax.swing.JLabel lblLimparCamposSetor;
    private javax.swing.JLabel lblLimparCamposUsuarios;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNatOpNotaSaida;
    private javax.swing.JLabel lblPesquisar;
    private javax.swing.JLabel lblPesquisarUsAjuda;
    private javax.swing.JLabel lblProCod;
    private javax.swing.JLabel lblProDes;
    private javax.swing.JLabel lblProUni;
    private javax.swing.JLabel lblProVal;
    private javax.swing.JLabel lblProdutos;
    private javax.swing.JLabel lblProdutosNotaEntrada;
    private javax.swing.JLabel lblProdutosNotaSaida;
    private javax.swing.JLabel lblQualProduto;
    private javax.swing.JLabel lblRegEntrada;
    private javax.swing.JLabel lblRegEntradaCod;
    private javax.swing.JLabel lblRegEntradaDataEmissao;
    private javax.swing.JLabel lblRegEntradaDataRecebimento;
    private javax.swing.JLabel lblRegEntradaDescricao;
    private javax.swing.JLabel lblRegEntradaDest;
    private javax.swing.JLabel lblRegEntradaHistorico;
    private javax.swing.JLabel lblRegEntradaOrin;
    private javax.swing.JLabel lblRegEntradaQuant;
    private javax.swing.JLabel lblRegSaidaDescricao;
    private javax.swing.JLabel lblRegSaidaHistorico;
    private javax.swing.JLabel lblRegSaidaQuant;
    private javax.swing.JLabel lblRegSaidaTotal;
    private javax.swing.JLabel lblRegistroSaida;
    private javax.swing.JLabel lblRelatorioAte;
    private javax.swing.JLabel lblRelatorioCodNota;
    private javax.swing.JLabel lblRelatorioCodigoAjuda;
    private javax.swing.JLabel lblRelatorioDe;
    private javax.swing.JLabel lblRelatorioTipoDaNota;
    private javax.swing.JLabel lblRelatorioTipoNota;
    private javax.swing.JLabel lblRelatorios;
    private javax.swing.JLabel lblRelatoriosAjuda;
    private javax.swing.JLabel lblRelatoriosFiltros;
    private javax.swing.JLabel lblRelatoriosFiltrosAjuda;
    private javax.swing.JLabel lblSaida;
    private javax.swing.JLabel lblSair;
    private javax.swing.JLabel lblSetCnpj;
    private javax.swing.JLabel lblSetCod;
    private javax.swing.JLabel lblSetNom;
    private javax.swing.JLabel lblSetRes;
    private javax.swing.JLabel lblSetorNotaSaida;
    private javax.swing.JLabel lblSetores;
    private javax.swing.JLabel lblTipSet;
    private javax.swing.JLabel lblTituloProdutos;
    private javax.swing.JLabel lblTituloRelatorio;
    private javax.swing.JLabel lblTituloSetores;
    private javax.swing.JLabel lblTituloUsuarios;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTotalHistoricoEntrada;
    private javax.swing.JLabel lblTotalHistoricoSaida;
    private javax.swing.JLabel lblUsuAcesso;
    private javax.swing.JLabel lblUsuCpf;
    private javax.swing.JLabel lblUsuEnd;
    private javax.swing.JLabel lblUsuNom;
    private javax.swing.JLabel lblUsuSenha;
    private javax.swing.JLabel lblUsuTel;
    private javax.swing.JLabel lblUsuUsuario;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JPasswordField pfContaConfirmaSenha;
    private javax.swing.JPasswordField pfContaSenha;
    private javax.swing.JPasswordField pfUsuSenha;
    private javax.swing.JPanel pnlAjudaAlterarSenha;
    private javax.swing.JPanel pnlAjudaProdutos;
    private javax.swing.JPanel pnlAjudaRegistroEntrada;
    private javax.swing.JPanel pnlAjudaRegistroSaida;
    private javax.swing.JPanel pnlAjudaRelatorios;
    private javax.swing.JPanel pnlAjudaSetores;
    private javax.swing.JPanel pnlAjudaUusuarios;
    private javax.swing.JPanel pnlBotoes;
    private javax.swing.JPanel pnlConta;
    private javax.swing.JPanel pnlDadosDaNota;
    private javax.swing.JPanel pnlDadosNotaEntrada;
    private javax.swing.JPanel pnlInformacoes;
    private javax.swing.JPanel pnlInicial;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JTabbedPane pnlPaineis;
    private javax.swing.JPanel pnlProdutos;
    private javax.swing.JPanel pnlRegEntrada;
    private javax.swing.JPanel pnlRegSaida;
    private javax.swing.JPanel pnlRelatorio;
    private javax.swing.JPanel pnlRelatorioFiltros;
    private javax.swing.JPanel pnlSetores;
    private javax.swing.JPanel pnlUsuarios;
    private javax.swing.JTable tblHistoricoEntrada;
    private javax.swing.JTable tblHistoricoEntradaProduto;
    private javax.swing.JTable tblHistoricoSaida;
    private javax.swing.JTable tblHistoricoSaidaProduto;
    private javax.swing.JTable tblNotaEntrada;
    private javax.swing.JTable tblNotaSaida;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTable tblSetores;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField tfCOntaUser;
    private javax.swing.JTextField tfCodigo;
    private javax.swing.JTextField tfCodigoProduto;
    private javax.swing.JTextField tfContaCpf;
    private javax.swing.JTextField tfContaEnd;
    private javax.swing.JTextField tfContaFone;
    private javax.swing.JTextField tfContaNome;
    private javax.swing.JTextField tfDescricaoProduto;
    private javax.swing.JTextField tfHistoricoEntradaPesquisar;
    private javax.swing.JTextField tfHistoricoEntradaTotal;
    private javax.swing.JTextField tfHistoricoSaidaPesquisar;
    private javax.swing.JTextField tfHistoricoSaidaTotal;
    private javax.swing.JTextField tfNome;
    private javax.swing.JTextField tfPesquisaProduto;
    private javax.swing.JTextField tfPesquisaSetor;
    private javax.swing.JTextField tfPesquisaUsuario;
    private javax.swing.JFormattedTextField tfRegEntradaCod;
    private com.toedter.calendar.JDateChooser tfRegEntradaDataEmissao;
    private com.toedter.calendar.JDateChooser tfRegEntradaDataRecebimento;
    private javax.swing.JFormattedTextField tfRegEntradaQuant;
    private javax.swing.JTextField tfRegEntradaTotal;
    private javax.swing.JFormattedTextField tfRegSaidaCnpj;
    private javax.swing.JTextField tfRegSaidaCod;
    private com.toedter.calendar.JDateChooser tfRegSaidaDataEm;
    private javax.swing.JFormattedTextField tfRegSaidaQuant;
    private javax.swing.JTextField tfRegSaidaTotal;
    private javax.swing.JTextField tfRelatorioCodNota;
    private javax.swing.JTextField tfResponsavel;
    private javax.swing.JTextField tfUnidadeOutro;
    private javax.swing.JFormattedTextField tfUsuCpf;
    private javax.swing.JTextField tfUsuEnd;
    private javax.swing.JFormattedTextField tfUsuFone;
    private javax.swing.JTextField tfUsuLogin;
    private javax.swing.JTextField tfUsuNom;
    private javax.swing.JLabel verDemoAcessarHistorico;
    private javax.swing.JLabel verDemoAcessarHistoricoNS;
    private javax.swing.JLabel verDemoAdcProdutoNS;
    private javax.swing.JLabel verDemoAddNotaEnt;
    private javax.swing.JLabel verDemoAltProd;
    private javax.swing.JLabel verDemoAltSenha;
    private javax.swing.JLabel verDemoAltSetor;
    private javax.swing.JLabel verDemoCadNotaEnt;
    private javax.swing.JLabel verDemoCadNotaSaida;
    private javax.swing.JLabel verDemoCadProd;
    private javax.swing.JLabel verDemoCadSetor;
    private javax.swing.JLabel verDemoCadUsuario;
    private javax.swing.JLabel verDemoExcNotaS;
    private javax.swing.JLabel verDemoExcProd;
    private javax.swing.JLabel verDemoExcProdNotaEnt;
    private javax.swing.JLabel verDemoExcProdutoNS;
    private javax.swing.JLabel verDemoExcSetor;
    private javax.swing.JLabel verDemoExcUsuario;
    private javax.swing.JLabel verDemoExcluirNota;
    private javax.swing.JLabel verDemoLimparCamposProd;
    private javax.swing.JLabel verDemoLimparCamposSet;
    private javax.swing.JLabel verDemoLimparUsuarios;
    private javax.swing.JLabel verDemoPesqProd;
    private javax.swing.JLabel verDemoPesqSetor;
    private javax.swing.JLabel verDemoPesqUs;
    private javax.swing.JLabel verDemoRelatorioCodigo;
    private javax.swing.JLabel verDemoRelatoriosFiltros;
    // End of variables declaration//GEN-END:variables
}
