
package model;

/**
 *
 * @author Pietro
 * @author Bianca
 */
public class Municipio {
    
    private Integer codigo;
    private String nome;
    private Integer codigoMicrorregiao;
    
    // ======================================
    // Get e Set
    // ======================================

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCodigoMicrorregiao() {
        return codigoMicrorregiao;
    }

    public void setCodigoMicrorregiao(Integer codigoMicrorregiao) {
        this.codigoMicrorregiao = codigoMicrorregiao;
    }    
    
}
