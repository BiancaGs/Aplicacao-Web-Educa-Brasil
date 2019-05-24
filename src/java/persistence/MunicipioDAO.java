
package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Municipio;

/**
 *
 * @author Pietro
 * @author Bianca
 * 
 */
public class MunicipioDAO {
   
    private Connection connection;

    public MunicipioDAO() throws DAOException {
        this.connection = ConnectionFactory.getConnection();
    }
    
    public List<Municipio> listar(String codigoUf) {
        
        List<Municipio> municipios = new ArrayList<>();
        
        try {
        
            String sql = "SELECT * FROM municipio m ";
            sql +=       "WHERE m.co_microrregiao IN (";
            sql +=          "SELECT mi.co_microrregiao FROM microrregiao mi ";
            sql +=          "WHERE mi.co_mesorregiao IN (";
            sql +=              "SELECT me.co_mesorregiao FROM mesorregiao me ";
            sql +=                  "WHERE me.co_uf = " + codigoUf + "))";

            PreparedStatement stmt = connection.prepareStatement(sql);
        
            ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                Municipio m = new Municipio();
                m.setCodigo(rs.getInt("co_municipio"));
                m.setNome(rs.getString("nome_municipio"));
                
                municipios.add(m);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return municipios;
        
    }
    
}
