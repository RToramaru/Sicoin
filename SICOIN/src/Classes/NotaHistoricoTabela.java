/*
 * Classe NotaHistoricoTabela.
 */
package Classes;

/**
 *
 * @author Rafael
 */
public class NotaHistoricoTabela {
    float quantidade;
    String unidade;
    String descricao;
    float valor;
    float ValorTotal;

    public NotaHistoricoTabela() {
    }

    
    public NotaHistoricoTabela(float quantidade, String unidade, String descricao, float valor, float ValorTotal) {
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.descricao = descricao;
        this.valor = valor;
        this.ValorTotal = ValorTotal;
    }

    
    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(float ValorTotal) {
        this.ValorTotal = ValorTotal;
    }
    
    
}
