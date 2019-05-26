
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
    
    public List<Escola> listar(String codigoMunicipio) {
        
        List<Escola> escolas = new ArrayList<>();
        
        try {
        
            String sql = "SELECT e.co_escola, e.nome_escola FROM escola e ";
            sql +=       "WHERE e.co_distrito IN (";
            sql +=          "SELECT d.co_distrito FROM distrito d ";
            sql +=          "WHERE d.co_municipio = " + codigoMunicipio + ")";
            sql +=       "ORDER BY e.nome_escola";

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
    
    public List<Escola> listarPorEstado(String codigoEstado) {
        
        List<Escola> escolas = new ArrayList<>();
        
        try {
        
            String sql =    "SELECT  e.co_escola, e.nome_escola, e.situacao_funcionamento, e.dependencia_adm, e.bercario, e.creche, e.pre_escola, e.ens_fundamental_anos_iniciais, e.ens_fundamental_anos_finais, e.ens_medio_normal, e.ens_medio_integrado FROM escola e ";
            sql +=              "WHERE e.co_distrito IN (";
            sql +=              "SELECT d.co_distrito ";
            sql +=              "FROM distrito d ";
            sql +=              "WHERE d.co_municipio IN (";
            sql +=                  "SELECT m.co_municipio ";
            sql +=                  "FROM municipio m ";
            sql +=                  "WHERE m.co_microrregiao IN (";
            sql +=                      "SELECT mi.co_microrregiao ";
            sql +=                      "FROM microrregiao mi ";
            sql +=                      "WHERE mi.co_mesorregiao IN (";
            sql +=                          "SELECT me.co_mesorregiao ";
            sql +=                          "FROM mesorregiao me ";
            sql +=                          "WHERE me.co_uf = " + codigoEstado;
            sql +=                      ")";
            sql +=                  ")";
            sql +=              ")";
            sql +=          ") ";
            sql +=          "ORDER BY e.qtd_funcionarios;";
            
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
        
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return escolas;
        
    }
    
}
