/*
 *Classe NotaProduto.
 */
package Classes;

/**
 *
 * @author Rafael
 */
public class NotaProduto {
    float quantidade;
    int codNotaEntrada;
    int codProdutoEntrada;

    public NotaProduto(float quantidade, int codNotaEntrada, int codProdutoEntrada) {
        this.quantidade = quantidade;
        this.codNotaEntrada = codNotaEntrada;
        this.codProdutoEntrada = codProdutoEntrada;
    }

    public NotaProduto() {
    }

    
    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public int getCodNotaEntrada() {
        return codNotaEntrada;
    }

    public void setCodNotaEntrada(int codNotaEntrada) {
        this.codNotaEntrada = codNotaEntrada;
    }

    public int getCodProdutoEntrada() {
        return codProdutoEntrada;
    }

    public void setCodProdutoEntrada(int codProdutoEntrada) {
        this.codProdutoEntrada = codProdutoEntrada;
    }
    
    
}
