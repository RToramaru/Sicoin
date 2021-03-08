/*
 * Classe Setor.
 */
package Classes;

/**
 *
 * @author Filipi
 */
public class Setor {
    public int codSetor;
    public String nomSetor;
    public String responsavel;
    public String tipo;
    public String cnpj;

    public Setor(int codSetor, String nomSetor, String responsavel, String tipo, String cnpj) {
       this.codSetor = codSetor;
       this.nomSetor = nomSetor;
       this.responsavel = responsavel;
       this.tipo = tipo;
       this.cnpj = cnpj;
    }
    
    public Setor(){
        
    }

    public int getCodSetor() {
        return codSetor;
    }

    public void setCodSetor(int codSetor) {
        this.codSetor = codSetor;
    }

    public String getNomSetor() {
        return nomSetor;
    }

    public void setNomSetor(String nomSetor) {
        this.nomSetor = nomSetor;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

   
   
    
    
    
}
