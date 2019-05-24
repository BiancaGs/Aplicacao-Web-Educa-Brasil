
package model;

import java.time.LocalDate;

/**
 * @author Pietro
 * @author Bianca
 */
public class Escola {
    
    // =================================
    // Referência aos outros Beans
    // =================================

    private EscolaOfertas eo;
    private EscolaDependenciasGerais edg;
    private EscolaDependenciasFisicas edf;

    
    // =================================
    // Atributos
    // =================================
    
    private Integer coEscola;
    private String nomeEscola;
    
    private Integer situacaoFuncionamento;
    // (1) Em Atividade / (2) Extinta / (3) Paralisada
    
    private LocalDate inicioAnoLetivo;
    private LocalDate terminoAnoLetivo;
    
    private Integer coDistrito;
    
    private Integer localizacao;
    // (1) Rural / (2) Urbana

    private Integer dependenciaAdm;
    // (1) Federal / (2) Estadual / (3) Municipal / (4) Privada
    
    private Boolean regulamentada;
    private Integer qtdSalasExistentes;
    private Integer qtdSalasUtilizadas;
    private Integer qtdFuncionarios;
    
    
    
    
}