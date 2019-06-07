/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.ConnectionFactory;
import persistence.DAOException;

/**
 *
 * @author pietr
 */
public class RecuperarEstatisticas extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if ( request.getParameter("recuperarEstatisticas") != null && request.getParameter("recuperarEstatisticas").equals("sim") ) {
            
            try {
                
                String sql = "SELECT " +
                    "GROUPING(e.bercario) g_bercario, GROUPING(e.creche) g_creche, GROUPING(e.pre_escola) g_pe, GROUPING(e.ens_fundamental_anos_iniciais) g_efi, " +
                    "GROUPING(e.ens_fundamental_anos_finais) g_efii, GROUPING(e.ens_medio_normal) g_emn, " +
                    "GROUPING(e.ens_medio_integrado) g_emi, GROUPING(e.situacao_funcionamento) g_situacao, " +
                    "GROUPING(e.dependencia_adm) g_dep, GROUPING(e.localizacao) g_localizacao, " +
                    "e.bercario, e.creche, e.pre_escola, e.ens_fundamental_anos_iniciais, e.ens_fundamental_anos_finais, e.ens_medio_normal, " +
                    "e.ens_medio_integrado,e.situacao_funcionamento, e.dependencia_adm, e.localizacao, count(e.co_escola) as qtd_escolas " +
                "FROM escola e " +
                "WHERE e.co_distrito IN ( " +
                    "SELECT d.co_distrito " +
                    "FROM distrito d " +
                    "WHERE d.co_municipio IN ( " +
                        "SELECT m.co_municipio " +
                        "FROM municipio m " +
                        "WHERE m.co_microrregiao IN ( " +
                            "SELECT mi.co_microrregiao " +
                            "FROM microrregiao mi " +
                            "WHERE mi.co_mesorregiao IN ( " +
                                "SELECT me.co_mesorregiao " +
                                "FROM mesorregiao me " +
                                "WHERE me.co_uf IN ( " +
                                    "SELECT u.co_uf " +
                                    "FROM uf u " +
                                    "WHERE u.co_regiao = 3 " +
                                ") " +
                            ") " +
                        ") " +
                    ") " +
                ") " +
                "GROUP BY GROUPING SETS ( " +
                    "(e.situacao_funcionamento), " +
                    "(e.dependencia_adm), " +
                    "(e.localizacao), " +
                    "(e.bercario), " +
                    "(e.creche), " +
                    "(e.pre_escola), " +
                    "(e.ens_fundamental_anos_iniciais), " +
                    "(e.ens_fundamental_anos_finais), " +
                    "(e.ens_medio_normal), " +
                    "(e.ens_medio_integrado), " +
                    "()" +
                ");";
                
                PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                Integer nEscolas = null;

                // Dependência
                Integer nFederal = null;
                Integer nEstadual = null;
                Integer nMunicipal = null;
                Integer nPrivada = null;

                // Situação
                Integer nAtividade = null;
                Integer nParalisada = null;
                Integer nExtinta = null;

                // Localização
                Integer nRural = null;
                Integer nUrbana = null;

                // Ofertas
                Integer nB = null;
                Integer nC = null;
                Integer nPE = null;
                Integer nEFI = null;
                Integer nEFII = null;
                Integer nEMN = null;
                Integer nEMI = null;

                
                while (rs.next()) {                    
                    
                    // Dependência
                    if (rs.getInt("g_dep") == 0) {
                        switch (rs.getString("dependencia_adm")) {
                            case "Federal":
                                nFederal = rs.getInt("qtd_escolas");
                                break;
                            case "Estadual":
                                nEstadual = rs.getInt("qtd_escolas");
                                break;
                            case "Municipal":
                                nMunicipal = rs.getInt("qtd_escolas");
                                break;
                            case "Privada":
                                nPrivada = rs.getInt("qtd_escolas");
                                break;
                        }
                        continue;
                    }
                    
                    // Situação
                    if (rs.getInt("g_situacao") == 0) {
                        switch (rs.getString("situacao_funcionamento")) {
                            case "Em atividade":
                                nAtividade = rs.getInt("qtd_escolas");
                                break;
                            case "Paralisada":
                                nParalisada = rs.getInt("qtd_escolas");
                                break;
                            case "Extinta":
                                nExtinta = rs.getInt("qtd_escolas");
                                break;
                        }
                        continue;
                    }
                    
                    // Localização
                    if (rs.getInt("g_localizacao") == 0) {
                        switch (rs.getString("localizacao")) {
                            case "Urbana":
                                nUrbana = rs.getInt("qtd_escolas");
                                break;
                            case "Rural":
                                nRural = rs.getInt("qtd_escolas");
                                break;
                        }
                        continue;
                    }
                    
                    // Ofertas
                    if (rs.getBoolean("bercario") == true) {
                        nB = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("creche") == true) {
                        nC = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("pre_escola") == true) {
                        nPE = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("ens_fundamental_anos_iniciais") == true) {
                        nEFI = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("ens_fundamental_anos_finais") == true) {
                        nEFII = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("ens_medio_normal") == true) {
                        nEMN = rs.getInt("qtd_escolas");
                    }
                    if (rs.getBoolean("ens_medio_integrado") == true) {
                        nEMI = rs.getInt("qtd_escolas");
                    }
                    
                    
                }
                
                nEscolas = nRural + nUrbana;


                // =======================================
                // Cria o JSON
                // =======================================

                String json = "";

                json += "{";
                json += "\"Federal\":" + nFederal.toString() + ",";
                json += "\"Estadual\":" + nEstadual.toString() + ",";
                json += "\"Municipal\":" + nMunicipal.toString() + ",";
                json += "\"Privada\":" + nPrivada.toString() + ",";
                json += "\"Atividade\":" + nAtividade.toString() + ",";
                json += "\"Paralisada\":" + nParalisada.toString() + ",";
                json += "\"Extinta\":" + nExtinta.toString() + ",";
                json += "\"Urbana\":" + nUrbana.toString() + ",";
                json += "\"Rural\":" + nRural.toString() + ",";
                json += "\"B\":" + nB.toString() + ",";
                json += "\"C\":" + nC.toString() + ",";
                json += "\"PE\":" + nPE.toString() + ",";
                json += "\"EFI\":" + nEFI.toString() + ",";
                json += "\"EFII\":" + nEFII.toString() + ",";
                json += "\"EMN\":" + nEMN.toString() + ",";
                json += "\"EMI\":" + nEMI.toString() + "";
                json += "}";
                
                
                PrintWriter writer = response.getWriter();
                writer.print(json);
                writer.close();
                
                
            } catch (DAOException | SQLException ex) {
                Logger.getLogger(RecuperarEstatisticas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
