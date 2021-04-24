/*
 * Classe nota saida.
 */
package Classes;

/**
 *
 * @author Filipi
 */
public class NotaSaida {
    
   private int cod;
   private String naturezaOperacao;
   private String destino;
   private String cnpj;
   private String dataEmissao;
   private String valorTotal;

    public NotaSaida() {
        
    }

    public NotaSaida(int cod, String naturezaOperacao, String destino, String cnpj, String dataEmissao, String valorTotal) {
        this.cod = cod;
        this.naturezaOperacao = naturezaOperacao;
        this.destino = destino;
        this.cnpj = cnpj;
        this.dataEmissao = dataEmissao;
        this.valorTotal = valorTotal;
    }
   
   

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(String naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
   
   
    
}
