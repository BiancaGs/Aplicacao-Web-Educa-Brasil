
package persistence;

import model.Estado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pietro
 * @author Bianca
 * 
 */
public class EstadoDAO {
   
    private Connection connection;

    public EstadoDAO() throws DAOException {
        this.connection = ConnectionFactory.getConnection();
    }
    
    public List<Estado> listar() {
        
        List<Estado> estados = new ArrayList<>();
        
        try {
        
            String sql = "SELECT * FROM uf ORDER BY nome_uf";
            PreparedStatement stmt = connection.prepareStatement(sql);
        
            ResultSet rs = stmt.executeQuery();
        
            while (rs.next()) {
                Estado e = new Estado();
                e.setCodigo(rs.getInt("co_uf"));
                e.setNome(rs.getString("nome_uf"));
                e.setSigla(rs.getString("sigla_uf"));
                
                System.out.println(rs.getString("nome_uf"));
                
                estados.add(e);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(EstadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return estados;
        
    }
    
}
