/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Estado;
import persistence.DAOException;
import persistence.EstadoDAO;

/**
 *
 * @author Pietro
 * @author Bianca
 */
public class RecuperarEstados extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

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
    
        Estado e = new Estado();
        String retorno = "<option></option>";
        request.setCharacterEncoding("UTF-8");
        
        try {
            
            EstadoDAO edao = new EstadoDAO();
            List<Estado> estados = edao.listar();
            
            for (int i = 0; i < estados.size(); i++) {
                String option = "<option value='" + estados.get(i).getCodigo() + "'>" + estados.get(i).getNome() + "</option>";
                retorno += option;
            }
            
        } catch (DAOException ex) {
            Logger.getLogger(RecuperarEstados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PrintWriter writer = response.getWriter();
        writer.print(retorno);
        writer.close();
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Este servlet tem o objetivo de recuperar os Estados disponíveis no Banco de Dados";
    }

}
