/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdk.nashorn.api.scripting.JSObject;

/**
 *
 * @author win7
 */
@WebServlet(urlPatterns = {"/API/MedicalCheckup/NotifyResults/old"})
public class GetMedicalResult extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
         response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {

      String tid = request.getParameter("tid");
      
       Connection Con = null;
             Statement stmt = null ;
                    
              getcon c = new getcon(); 
              JsonObject obj = new JsonObject();
             // JSONObject obj = new JSONObject();
            try {
                
                Con = c.myconnection();
                stmt = Con.createStatement();
                
                ResultSet rs = stmt.executeQuery("select * from mi.medical_exam where transaction_id = '"+tid+"'");
                if(rs.first()){
                    if(rs.getString("inspection_status").equals("C")){ // 
                obj.addProperty("exam_result", "passed");
                obj.addProperty("national_id", rs.getString("national_id"));
                    }
                    else if (rs.getString("inspection_status").equals("W")){
                    obj.addProperty("exam_result", "wait");
                    obj.addProperty("national_id", rs.getString("national_id"));
                    }
                    else{
                    obj.addProperty("exam_result", "faild");
                    obj.addProperty("national_id", rs.getString("national_id"));
                    }
                }
                
                 out.write(obj.toString());  
                
            } catch (SQLException ex) {
                Logger.getLogger(GetMedicalResult.class.getName()).log(Level.SEVERE, null, ex);
            }
      

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetMedicalResult.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetMedicalResult.class.getName()).log(Level.SEVERE, null, ex);
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
    }// </editor-fold>

}
