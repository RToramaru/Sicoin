/*
 * Classe nota de entrada.
 */
package Classes;

/**
 *
 * @author Rafael
 */
public class NotaEntrada {
    int cod;
    String setorOrigem;
    String setorDestino;
    float valorTotal;
    String dataEmissao;
    String dataRecebimento;

    public NotaEntrada(int cod, String setorOrigem, String setorDestino, float valorTotal, String dataEmissao, String dataRecebimento) {
        this.cod = cod;
        this.setorOrigem = setorOrigem;
        this.setorDestino = setorDestino;
        this.valorTotal = valorTotal;
        this.dataEmissao = dataEmissao;
        this.dataRecebimento = dataRecebimento;
    }

    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getSetorOrigem() {
        return setorOrigem;
    }

    public void setSetorOrigem(String setorOrigem) {
        this.setorOrigem = setorOrigem;
    }

    public String getSetorDestino() {
        return setorDestino;
    }

    public void setSetorDestino(String setorDestino) {
        this.setorDestino = setorDestino;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(String dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }
    
}
