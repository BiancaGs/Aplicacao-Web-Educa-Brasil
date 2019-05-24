
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
    
}
