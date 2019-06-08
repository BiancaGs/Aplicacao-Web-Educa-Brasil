/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javafx.util.Pair;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Pietro
 * @author Bianca
 */
public class AtualizarFiltros extends HttpServlet {

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
    
        if ( request.getParameter("atualizarFiltros") != null && request.getParameter("atualizarFiltros").equals("sim") ) {
            
            System.out.println(request.getParameter("filtrosSituacao"));
            System.out.println(request.getParameter("filtrosDepAdm"));
            System.out.println(request.getParameter("filtrosOfertas"));
            
            JSONObject jsonSituacao = new JSONObject(request.getParameter("filtrosSituacao"));
            Boolean emAtividade = (Boolean) jsonSituacao.get("emAtividade");
            Boolean paralisada = (Boolean) jsonSituacao.get("paralisada");
            Boolean extinta = (Boolean) jsonSituacao.get("extinta");
            System.out.println("emAtividade: " + emAtividade);
            System.out.println("paralisada: " + paralisada);
            System.out.println("extinta: " + extinta);

            JSONObject jsonDepAdm = new JSONObject(request.getParameter("filtrosDepAdm"));
            Boolean federal = (Boolean) jsonDepAdm.get("federal");
            Boolean estadual = (Boolean) jsonDepAdm.get("estadual");
            Boolean municipal = (Boolean) jsonDepAdm.get("municipal");
            Boolean privada = (Boolean) jsonDepAdm.get("privada");
            System.out.println("federal: " + federal);
            System.out.println("estadual: " + estadual);
            System.out.println("municipal: " + municipal);
            System.out.println("privada: " + privada);

            JSONObject jsonOfertas = new JSONObject(request.getParameter("filtrosOfertas"));
            Boolean b = (Boolean) jsonOfertas.get("b");
            Boolean c = (Boolean) jsonOfertas.get("c");
            Boolean pe = (Boolean) jsonOfertas.get("pe");
            Boolean efi = (Boolean) jsonOfertas.get("efi");
            Boolean efii = (Boolean) jsonOfertas.get("efii");
            Boolean emn = (Boolean) jsonOfertas.get("emn");
            Boolean emi = (Boolean) jsonOfertas.get("emi");
            System.out.println("b: " + b);
            System.out.println("c: " + c);
            System.out.println("pe: " + pe);
            System.out.println("efi: " + efi);
            System.out.println("efii: " + efii);
            System.out.println("emn: " + emn);
            System.out.println("emi: " + emi);
            
            System.out.println("--------------------------------");

            
            // =======================================================
            // Atualiza a Sessão
            // =======================================================

            HttpSession s = request.getSession(true);
                    
            List<Pair<String, Boolean>> filtrosSituacao = (List<Pair<String, Boolean>>) s.getAttribute("filtros_situacao");
            filtrosSituacao.set(0, new Pair("Em atividade", emAtividade));
            filtrosSituacao.set(1, new Pair("Paralisada", paralisada));
            filtrosSituacao.set(2, new Pair("Extinta", extinta));
            s.setAttribute("filtros_situacao", filtrosSituacao);

            List<Pair<String, Boolean>> filtrosDep = (List<Pair<String, Boolean>>) s.getAttribute("filtros_dependencia_adm");
            filtrosDep.set(0, new Pair("Federal", federal));
            filtrosDep.set(1, new Pair("Estadual", estadual));
            filtrosDep.set(2, new Pair("Municipal", municipal));
            filtrosDep.set(3, new Pair("Privada", privada));
            s.setAttribute("filtros_dependencia_adm", filtrosDep);

            List<Pair<String, Boolean>> filtrosOfertas = (List<Pair<String, Boolean>>) s.getAttribute("filtros_ofertas");
            filtrosOfertas.set(0, new Pair("B", b));
            filtrosOfertas.set(1, new Pair("C", c));
            filtrosOfertas.set(2, new Pair("PE", pe));
            filtrosOfertas.set(3, new Pair("EFI", efi));
            filtrosOfertas.set(4, new Pair("EFII", efii));
            filtrosOfertas.set(5, new Pair("EMN", emn));
            filtrosOfertas.set(6, new Pair("EMI", emi));
            s.setAttribute("filtros_ofertas", filtrosOfertas);      
            
            //!DELETAR Enumerar sessão
            Enumeration enS = s.getAttributeNames();
            while (enS.hasMoreElements()) {
                Object obj = enS.nextElement();
                String nome = (String) obj;
                System.out.println("Sessao eh '" + nome + "'");
                if ( nome.equals("filtros_situacao") || nome.equals("filtros_dependencia_adm") || nome.equals("filtros_ofertas") ) {
                    List<Pair<String, Boolean>> lista = new ArrayList<>();
                    lista = (List<Pair<String, Boolean>>) s.getAttribute(nome);
                    for (int i = 0; i < lista.size(); i++) {
                        System.out.println(lista.get(i).getKey() + " - " + lista.get(i).getValue());
                    }
                }
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
