/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.aman.medical.db.getcon;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/getName"})
public class getName extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String nid = request.getParameter("NationalID");
            String CountryID = request.getParameter("theCountry");
            String passNo = request.getParameter("passNo");

//            System.out.println("niddddddddddddddddd" + nid);
//            System.out.println("CountryIDDDDDDDDDDD" + CountryID);
//            System.out.println("passNoooooooooooooo" + passNo);
            String name = "";
            List<String> requestIds = new ArrayList<String>();
            getcon c = new getcon();
          //  System.out.println("getNameeeeeeeeeeeeeeeee");
            Connection Con = c.myconnection();
            Statement stmt = Con.createStatement();
//               boolean agnby = false;
//               String agnbyOrMasry = "";
//            if(!nid.matches("[0-9]+")){
//                agnbyOrMasry = "nationality";
//                System.out.println("agnbyyyyyyyyyyyyyyyyyyy");
//            agnby = true;
//            }
//            else{
//             agnbyOrMasry = "NationalID";
//            }
            String qry = "";
            if (nid != null) {
                 System.out.println("masry query to get name and request Ids");
                qry = "select * from `clients_data` where `national_id` = '" + nid + "'";
            } else if (CountryID != null && passNo != null) {
                System.out.println("agnaby query to get name and request Ids");
                qry = "select * from `clients_data` where `PassportIssueCountry` = '" + CountryID + "' and PassportNo = '"+passNo+"'";
            }
          
        //    String qry = "select * from `paymentnotify` where 1";
            ResultSet rs = stmt.executeQuery(qry);
            System.out.println(rs.toString());
//            System.out.println(rs.getMetaData());
            while (rs.next()) {
                name = rs.getString("Name");
                System.out.println(".add - > " + rs.getString("requestID"));
                requestIds.add(rs.getString("requestID"));
            }
             System.out.println(name);
             System.out.println(requestIds.toString());
             String requests = requestIds.toString();
             stmt.close();
             Con.close();
            out.print("{\"name\":\""+name+"\",\"requestIDs\":"+requests+"}");
           
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
            Logger.getLogger(getName.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(getName.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(getName.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(getName.class.getName()).log(Level.SEVERE, null, ex);
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
