/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
//import com.google.gson.JsonObject;
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

/**
 *
 * @author win7
 */
@WebServlet(urlPatterns = {"/MedicalCheckup/InquiryRequest"})
public class InquiryRequest extends HttpServlet {

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
        //response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
       
        try (PrintWriter out = response.getWriter()) {

            String citizenName = "";
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getcon.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {

                getcon c = new getcon();
                System.out.println("hereeeee");
                Connection Con = c.myconnection();

                boolean flag = false;
                String NationalID = request.getParameter("NationalID");
                String PassportNo = request.getParameter("PassportNo");
                String PassportIssueCountry = request.getParameter("PassportIssueCountry");
                System.out.println("hasdfasgfhasfgah "+PassportIssueCountry);
                System.out.println("adsdasfsdfsdfsdfsdfdsfsf" + NationalID);
                String sql = "";
                if( NationalID != null){
                sql = "select * from mi.inquired_data where NationalID = '" + NationalID + "'";
                }
                else if( PassportNo != null  &&  PassportIssueCountry != null ){
                 System.out.println("asasasasas "+PassportNo);     
                    sql = "select * from mi.inquired_data where PassportNo = '" + PassportNo + "' and PassportIssueCountry = '"+PassportIssueCountry+"'";
                }
                else{
                out.write("please check paraemeters");
                }

                Statement stmt = Con.createStatement();
                //SELECT `TRAFFIC_UNIT` , `NATIONAL_ID` , `USERNAME` ,  CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR)  FROM `users` WHERE CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR) = 'admin'  and `USERNAME` = 'hazem';
                ResultSet RS = stmt.executeQuery(sql);

//ResultSet RS =stmt.executeQuery("SELECT * FROM `mi`.`users` WHERE CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR) = '"+password+"'  and `USERNAME` = '"+email+"';");
                JsonObject obj = new JsonObject();

                if (RS.first()) {

//                   //response.sendRedirect("courses.jsp");
                    obj.addProperty("FName", RS.getString("FName"));
                    obj.addProperty("MName", RS.getString("MName"));
                    obj.addProperty("LName", RS.getString("LName"));
                   // obj.addProperty("MName", RS.getString("MName"));
                    obj.addProperty("ExName", RS.getString("ExName"));
                    obj.addProperty("NationalID", RS.getString("NationalID"));
                     obj.addProperty("PassportNo", RS.getString("PassportNo"));
                            
                    obj.addProperty("PassportIssueCountry", RS.getString("PassportIssueCountry"));
                    obj.addProperty("RequestFees", RS.getDouble("requestFees"));
                    obj.addProperty("LicenseType", RS.getString("LicenseType"));
                    obj.addProperty("TrafficUnit", RS.getString("TrafficUnit"));
                    obj.addProperty("BirthDate", String.valueOf(RS.getDate("BirthDate")));
                    
                     obj.addProperty("RequestID", RS.getString("requestID"));
                     obj.addProperty("queue", RS.getString("queue"));
                    

                    obj.addProperty("Error_Message", "0");

                    // citizenName = RS.getString("name");
//                        if(type.equalsIgnoreCase("oculist")){
//                               RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("oculist.jsp");
//                          requestDispatcher.forward(request, response);
//                        }else{
//                            RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("internist.jsp");
//                          requestDispatcher.forward(request, response);
//                        
//                        }
//                                                       RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("test.jsp");
//                          requestDispatcher.forward(request, response);
//                               RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/test.jsp");
//                               dispatcher.forward(request, response);
                } else {
                  
                    obj.addProperty("Error_Message", "1");
                    // out.write("ادخل الرقم القومي الصحيح");
                    //response.sendRedirect("index.html"); 
                }
                System.out.println("JSON obj is : " + obj.toString());
                out.write(obj.toString());
                // out.write(citizenName);

            } catch (SQLException ex) {
                Logger.getLogger(InquiryRequest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InquiryRequest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(InquiryRequest.class.getName()).log(Level.SEVERE, null, ex);
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
