/*
 * Classe Produto.
 */
package Classes;

/**
 *
 * @author Filipi
 */
public class Produto {
    private int codProd;
    private String descricao;
    private String unidade;
    private float estoque;
    private float valor;

    public Produto(int codProd, String descricao, String unidade, float estoque, float valor) {
        this.codProd = codProd;
        this.descricao = descricao;
        this.unidade = unidade;
        this.valor = valor;
        this.estoque = estoque;
    }
    
    public Produto (){
        
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProduto(int codProd) {
        this.codProd = codProd;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getEstoque() {
        return estoque;
    }

    public void setEstoque(float estoque) {
        this.estoque = estoque;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    
    
    
}
