package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Escola;
import model.EscolaDependenciasFisicas;
import model.EscolaDependenciasGerais;
import model.EscolaOfertas;

/**
 *
 * @author Pietro
 * @author Bianca
 */
public class EscolaDAO {

    private Connection connection;

    public EscolaDAO() throws DAOException {
        this.connection = ConnectionFactory.getConnection();
    }

    /**
     * Recupera a lista de escolas de um dado Município
     * 
     * @param String codigoMunicipio    : código do município
     * 
     * @return uma List<Escola> com as escolas recuperadas do banco
     */
    public List<Escola> listar(String codigoMunicipio) {

        List<Escola> escolas = new ArrayList<>();

        try {

            String sql = "SELECT e.co_escola, e.nome_escola FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito FROM distrito d ";
            sql += "WHERE d.co_municipio = " + codigoMunicipio + ")";
            sql += "ORDER BY e.nome_escola";

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Escola e = new Escola();
                e.setCodigo(rs.getInt("co_escola"));
                e.setNome(rs.getString("nome_escola"));

                escolas.add(e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return escolas;

    }


    /**
     * Recupera o JSON para utilização da DataTable das Escolas
     * 
     * @param String codigoEstado       : código do Estado
     * @param String campoBusca         : campo digitado na busca
     * @param String limit              : número de itens por página
     * @param String offset             : "deslocamento" para o SELECT
     * @param String draw               
     * @param String qtdEscolasEstado   : quantidade total de escolas, fornecida pela sessão
     * 
     * @return uma String contendo o JSON
     * 
     */
    public String listarPorEstadoJSON(String codigoEstado, String campoBusca, String limit, String offset, String draw, String qtdEscolasEstado, String orderColumn, String orderDirection) {

        List<Escola> escolas = new ArrayList<>();
        String json = "";

        try {
            
            /**
             * COM LIMIT
             */

            String sql = "SELECT  e.co_escola, e.nome_escola, e.situacao_funcionamento, e.dependencia_adm, ";
            sql += "e.bercario, e.creche, e.pre_escola, e.ens_fundamental_anos_iniciais, ";
            sql += "e.ens_fundamental_anos_finais, e.ens_medio_normal, e.ens_medio_integrado ";
            sql += "FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distritos"+codigoEstado+" d ";
            sql += ")";
            
            // =======================================================
            // Caso o usuário tenha digitado algo no campo de busca
            // =======================================================

            if (!campoBusca.isEmpty()) {
                sql += "AND e.nome_escola LIKE '%"+campoBusca.toUpperCase()+"%' ";
            }
            
            // =======================================================
            // Ordenação
            // =======================================================
            
            switch (orderColumn) {
                case "0":
                    sql += "ORDER BY e.co_escola "+orderDirection+" ";
                    break;
                case "1":
                    sql += "ORDER BY e.nome_escola "+orderDirection+" ";
                    break;
                case "2":
                    sql += "ORDER BY e.situacao_funcionamento "+orderDirection+" ";
                    break;
                case "3":
                    sql += "ORDER BY e.dependencia_adm "+orderDirection+" ";
                    break;
                case "4":
                    sql += "ORDER BY ";
                    sql += "CASE ";
                    sql += "WHEN (e.bercario IS true AND e.creche IS true AND e.pre_escola IS true) THEN 1 ";
                    sql += "ELSE 2 ";
                    sql += "END "+orderDirection+" ";
                    break;
                default:
                    sql += "ORDER BY e.qtd_funcionarios ";
                    break;
            }
            
            // =======================================================
            // LIMIT e OFFSET para paginação
            // =======================================================
            
            sql += "LIMIT "+limit+" OFFSET "+offset;

            System.out.println(sql);
            

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Escola e = new Escola();
                EscolaOfertas eo = new EscolaOfertas();

                e.setCodigo(rs.getInt("co_escola"));
                e.setNome(rs.getString("nome_escola"));
                e.setSituacaoFuncionamento(rs.getString("situacao_funcionamento"));
                e.setDependenciaAdm(rs.getString("dependencia_adm"));

                eo.setBercario(rs.getBoolean("bercario"));
                eo.setCreche(rs.getBoolean("creche"));
                eo.setPreEscola(rs.getBoolean("pre_escola"));
                eo.setEFI(rs.getBoolean("ens_fundamental_anos_iniciais"));
                eo.setEFII(rs.getBoolean("ens_fundamental_anos_finais"));
                eo.setEMN(rs.getBoolean("ens_medio_normal"));
                eo.setEMI(rs.getBoolean("ens_medio_integrado"));

                e.setEo(eo);

                escolas.add(e);
            }


            /**
             * SEM LIMIT (para contar os elementos)
             */
            
            sql = "SELECT count(e.co_escola) AS total ";
            sql += "FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distritos"+codigoEstado+" d ";
            sql += ")";
            
            // =======================================================
            // Caso o usuário tenha digitado algo no campo de busca
            // =======================================================

            if (!campoBusca.isEmpty()) {
                sql += "AND e.nome_escola LIKE '%"+campoBusca.toUpperCase()+"%' ";
            }
            
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            rs.next();
            
            Integer qtdSemLimit = rs.getInt("total");

            
            /**
             * CRIA O JSON
             */
            
            String data = "";
            
            if (!escolas.isEmpty()) {
                
                int total = escolas.size();
                int i = 1;
                
                for (Escola escola : escolas) {
                    
                    data += "["+
                            "\""+ escola.getCodigo() +"\", "+
                            "\""+ escola.getNome() +"\", "+
                            "\""+ escola.getSituacaoFuncionamento() +"\", "+
                            "\""+ escola.getDependenciaAdm() +"\","+
                            "["+ 
                            (escola.getEo().getBercario() == true ? "\"B\"," : "\"\",")+
                            (escola.getEo().getCreche() == true ? "\"C\"," : "\"\",")+
                            (escola.getEo().getPreEscola() == true ? "\"PE\"," : "\"\",")+
                            (escola.getEo().getEFI() == true ? "\"EFI\"," : "\"\",")+
                            (escola.getEo().getEFII() == true ? "\"EFII\"," : "\"\",")+
                            (escola.getEo().getEMN() == true ? "\"EMN\"," : "\"\",")+
                            (escola.getEo().getEMI() == true ? "\"EMI\"" : "\"\"")+
                        "]]";
                    if (i < total)
                        data += ",";
                    
                    i++;
                }
                
            }
            
            json = "{"+
                "\"draw\": " + draw + ","+
                "\"recordsTotal\": " + qtdEscolasEstado + ","+     // o total é o valor de 'qtd_escolas_estado' na sessão
                "\"recordsFiltered\": " + qtdSemLimit.toString() + ","+
                "\"data\": ["+
                    data +
                "]"+ // fechamento do data
            "}"; // fechamento do json
            

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return json;

    }

    /**
     * Recupera o JSON para utilização da DataTable das Escolas
     * 
     * @param String codigoMunicipio        : código do Municipio
     * @param String codigoEstado           : código do Estado
     * @param String campoBusca             : campo digitado na busca
     * @param String limit                  : número de itens por página
     * @param String offset                 : "deslocamento" para o SELECT
     * @param String draw               
     * @param String qtdEscolasMunicipio    : quantidade total de escolas, fornecida pela sessão
     * 
     * @return uma String contendo o JSON
     * 
     */
    public String listaPorMunicipioJSON(String codigoMunicipio, String codigoEstado, String campoBusca, String limit, String offset, String draw, String qtdEscolasMunicipio, String orderColumn, String orderDirection) {

        List<Escola> escolas = new ArrayList<>();
        String json = "";

        try {
            
            /**
             * COM LIMIT
             */

            String sql = "SELECT  e.co_escola, e.nome_escola, e.situacao_funcionamento, "; 
            sql += "e.dependencia_adm, e.bercario, e.creche, e.pre_escola, ";
            sql += "e.ens_fundamental_anos_iniciais, e.ens_fundamental_anos_finais, ";
            sql += "e.ens_medio_normal, e.ens_medio_integrado ";
            sql += "FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distritos"+codigoEstado+" d ";
            sql += "WHERE d.co_municipio = "+codigoMunicipio+" ";
            sql += ")";


            // =======================================================
            // Caso o usuário tenha digitado algo no campo de busca
            // =======================================================

            if (!campoBusca.isEmpty()) {
                sql += "AND e.nome_escola LIKE '%"+campoBusca.toUpperCase()+"%' ";
            }
            
            // =======================================================
            // Ordenação
            // =======================================================
            
            switch (orderColumn) {
                case "0":
                    sql += "ORDER BY e.co_escola "+orderDirection+" ";
                    break;
                case "1":
                    sql += "ORDER BY e.nome_escola "+orderDirection+" ";
                    break;
                case "2":
                    sql += "ORDER BY e.situacao_funcionamento "+orderDirection+" ";
                    break;
                case "3":
                    sql += "ORDER BY e.dependencia_adm "+orderDirection+" ";
                    break;
                case "4":
                    sql += "ORDER BY ";
                    sql += "CASE ";
                    sql += "WHEN (e.bercario IS true AND e.creche IS true AND e.pre_escola IS true) THEN 1 ";
                    sql += "ELSE 2 ";
                    sql += "END "+orderDirection+" ";
                    break;
                default:
                    sql += "ORDER BY e.qtd_funcionarios ";
                    break;
            }
            
            // =======================================================
            // LIMIT e OFFSET para paginação
            // =======================================================
            
            sql += "LIMIT "+limit+" OFFSET "+offset;
            

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Escola e = new Escola();
                EscolaOfertas eo = new EscolaOfertas();

                e.setCodigo(rs.getInt("co_escola"));
                e.setNome(rs.getString("nome_escola"));
                e.setSituacaoFuncionamento(rs.getString("situacao_funcionamento"));
                e.setDependenciaAdm(rs.getString("dependencia_adm"));

                eo.setBercario(rs.getBoolean("bercario"));
                eo.setCreche(rs.getBoolean("creche"));
                eo.setPreEscola(rs.getBoolean("pre_escola"));
                eo.setEFI(rs.getBoolean("ens_fundamental_anos_iniciais"));
                eo.setEFII(rs.getBoolean("ens_fundamental_anos_finais"));
                eo.setEMN(rs.getBoolean("ens_medio_normal"));
                eo.setEMI(rs.getBoolean("ens_medio_integrado"));

                e.setEo(eo);

                escolas.add(e);
            }


            /**
             * SEM LIMIT (para contar os elementos)
             */
            
            sql = "SELECT count(e.co_escola) AS total ";
            sql += "FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distritos"+codigoEstado+" d ";
            sql += "WHERE d.co_municipio = "+codigoMunicipio+" ";
            sql += ")";

            
            // =======================================================
            // Caso o usuário tenha digitado algo no campo de busca
            // =======================================================

            if (!campoBusca.isEmpty()) {
                sql += "AND e.nome_escola LIKE '%"+campoBusca.toUpperCase()+"%' ";
            }
            
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();
            rs.next();
            
            Integer qtdSemLimit = rs.getInt("total");

            
            /**
             * CRIA O JSON
             */
            
            String data = "";
            
            if (!escolas.isEmpty()) {
                
                int total = escolas.size();
                int i = 1;
                
                for (Escola escola : escolas) {
                    
                    data += "["+
                            "\""+ escola.getCodigo() +"\", "+
                            "\""+ escola.getNome() +"\", "+
                            "\""+ escola.getSituacaoFuncionamento() +"\", "+
                            "\""+ escola.getDependenciaAdm() +"\","+
                            "["+ 
                            (escola.getEo().getBercario() == true ? "\"B\"," : "\"\",")+
                            (escola.getEo().getCreche() == true ? "\"C\"," : "\"\",")+
                            (escola.getEo().getPreEscola() == true ? "\"PE\"," : "\"\",")+
                            (escola.getEo().getEFI() == true ? "\"EFI\"," : "\"\",")+
                            (escola.getEo().getEFII() == true ? "\"EFII\"," : "\"\",")+
                            (escola.getEo().getEMN() == true ? "\"EMN\"," : "\"\",")+
                            (escola.getEo().getEMI() == true ? "\"EMI\"" : "\"\"")+
                        "]]";
                    if (i < total)
                        data += ",";
                    
                    i++;
                }
                
            }
            
            json = "{"+
                "\"draw\": " + draw + ","+
                "\"recordsTotal\": " + qtdEscolasMunicipio + ","+     // o total é o valor de 'qtd_escolas_estado' na sessão
                "\"recordsFiltered\": " + qtdSemLimit.toString() + ","+
                "\"data\": ["+
                    data +
                "]"+ // fechamento do data
            "}"; // fechamento do json
            

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return json;

    }
    
    /**
     * Recupera a quantidade de escolas em um Estado
     * 
     * @param String codigoEstado   : código do estado
     * 
     * @return um Integer com o número de escolas
     */
    public Integer contarEscolasEstado(String codigoEstado) {
        
        Integer total = 0;
        
        try {

            String sql = "SELECT count(e.co_escola) AS total FROM escola e ";
            sql += "WHERE e.co_distrito IN (";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distrito d ";
            sql += "WHERE d.co_municipio IN (";
            sql += "SELECT m.co_municipio ";
            sql += "FROM municipio m ";
            sql += "WHERE m.co_microrregiao IN (";
            sql += "SELECT mi.co_microrregiao ";
            sql += "FROM microrregiao mi ";
            sql += "WHERE mi.co_mesorregiao IN (";
            sql += "SELECT me.co_mesorregiao ";
            sql += "FROM mesorregiao me ";
            sql += "WHERE me.co_uf = " + codigoEstado;
            sql += ")";
            sql += ")";
            sql += ")";
            sql += "); ";

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            total = rs.getInt("total");

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }

    
    /**
     * Recupera a quantidade de escolas em um Municipio
     * 
     * @param String codigoMunicipio   : código do Municipio
     * 
     * @return um Integer com o número de escolas
     */
    public Integer contarEscolasMunicipio(String codigoMunicipio) {
        
        Integer total = 0;
        
        try {

            String sql = "SELECT count(e.co_escola) as total ";
            sql += "FROM escola e ";
            sql += "WHERE e.co_distrito IN ( ";
            sql += "SELECT d.co_distrito ";
            sql += "FROM distrito d ";
            sql += "WHERE d.co_municipio = " + codigoMunicipio;
            sql += ")";

            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            total = rs.getInt("total");

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return total;
    }

    
    /**
     * Recupera uma escola específica a partir de seu código
     * 
     * @param String codigoEscola   : código da Escola
     * 
     * @return uma Escola 
     */
    public Escola recuperarEscola(String codigoEscola) {
        
        Escola e = new Escola();
        EscolaDependenciasFisicas edf = new EscolaDependenciasFisicas();
        EscolaDependenciasGerais edg = new EscolaDependenciasGerais();
        EscolaOfertas eo = new EscolaOfertas();
                
        try {

            String sql = "SELECT * FROM escola WHERE co_escola = " + codigoEscola;
           
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            
            System.out.println("hi");
            
            if (rs.next()) {
                
                // Informações Gerais
                e.setCodigo(rs.getInt("co_escola"));
                e.setNome(rs.getString("nome_escola"));
                e.setSituacaoFuncionamento(rs.getString("situacao_funcionamento"));
                e.setInicioAnoLetivo(rs.getString("inicio_ano_letivo"));
                e.setTerminoAnoLetivo(rs.getString("termino_ano_letivo"));
                e.setCodigoDistrito(rs.getInt("co_distrito"));
                e.setLocalizacao(rs.getString("localizacao"));
                e.setDependenciaAdm(rs.getString("dependencia_adm"));
                e.setRegulamentada(rs.getBoolean("regulamentada"));
                e.setQtdSalasExistentes(rs.getInt("qtd_salas_existentes"));
                e.setQtdSalasUtilizadas(rs.getInt("qtd_salas_utilizadas"));
                e.setQtdFuncionarios(rs.getInt("qtd_funcionarios"));
                
                // Dependências Físcias
                edf.setSalaDiretoria(rs.getBoolean("sala_diretoria"));
                edf.setSalaProfessor(rs.getBoolean("sala_professor"));
                edf.setLaboratorioInformatica(rs.getBoolean("laboratorio_informatica"));
                edf.setLaboratorioCiencias(rs.getBoolean("laboratorio_ciencias"));
                edf.setQuadraEsportes(rs.getBoolean("quadra_esportes"));
                edf.setCozinha(rs.getBoolean("cozinha"));
                edf.setBiblioteca(rs.getBoolean("biblioteca"));
                edf.setSalaLeitura(rs.getBoolean("sala_leitura"));
                edf.setParqueInfantil(rs.getBoolean("parque_infantil"));
                edf.setSecretaria(rs.getBoolean("secretaria"));
                edf.setRefeitorio(rs.getBoolean("refeitorio"));
                edf.setAuditorio(rs.getBoolean("auditorio"));
                
                // Dependencias Gerais
                
                edg.setAguaFiltrada(rs.getBoolean("agua_filtrada"));
                edg.setEsgoto(rs.getBoolean("esgoto"));
                edg.setColetaLixo(rs.getBoolean("coleta_de_lixo"));
                edg.setRecliclagem(rs.getBoolean("reciclagem"));
                edg.setAcessibilidade(rs.getBoolean("acessibilidade_deficiencia"));
                edg.setAlimentacao(rs.getBoolean("alimentacao"));
                edg.setAlojamentoAlunos(rs.getBoolean("alojamento_alunos"));
                edg.setAlojamentoProfessores(rs.getBoolean("alojamento_professores"));
                edg.setAreaVerde(rs.getBoolean("area_verde"));
                edg.setInternet(rs.getBoolean("internet"));
                
                // Ofertas de Matrícula
                
                eo.setBercario(rs.getBoolean("bercario"));
                eo.setCreche(rs.getBoolean("creche"));
                eo.setPreEscola(rs.getBoolean("pre_escola"));
                eo.setEFI(rs.getBoolean("ens_fundamental_anos_iniciais"));
                eo.setEFII(rs.getBoolean("ens_fundamental_anos_finais"));
                eo.setEMN(rs.getBoolean("ens_medio_normal"));
                eo.setEMI(rs.getBoolean("ens_medio_integrado"));
                
            }
            
            e.setEdf(edf);
            e.setEdg(edg);
            e.setEo(eo);
            

        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return e;
    }
    
}


