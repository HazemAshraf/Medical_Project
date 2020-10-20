/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.aman.medical.db.getcon;
import com.mysql.cj.protocol.Resultset;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
  
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
  
 import sun.misc.BASE64Decoder;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/ImageServlet"})
public class ImageServlet extends HttpServlet {

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
            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//       
//        }
System.out.println("Image Servleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeet");

            try
             {
             StringBuffer buffer = new StringBuffer();
             Reader reader = request.getReader();
             int current;

             while((current = reader.read()) >= 0)
             buffer.append((char) current);   

             String data = new String(buffer);
             String rqID = data.substring(0,data.indexOf("d"));
             data = data.substring(data.indexOf(",") + 1);
                System.out.println("PNG image data on Base64: " + data);
              String sql = "";
              
               
                
                
              System.out.println("request id data is:" + rqID);
                
                 
            
            if( rqID.matches("^[0-9]+$") ) {
                System.out.println("egyptian client");
                   sql =  "update mi.clients_data set photo = '"+data+"' where national_id = '"+rqID+"'";
            }
             else {
               
                System.out.println("foreign client");
                 sql =  "update mi.clients_data set photo = '"+data+"' where foreignComposite = '"+rqID+"'";
            }

          

//             FileOutputStream output = new FileOutputStream(new File("/E:/" + 
//             new Random().nextInt(100000) + ".png"));
//                          output.write(new BASE64Decoder().decodeBuffer(data));
//             output.flush();
//             output.close();

 Connection Con = null;
        Statement stmt = null,stmt1 = null,stmt2=null;
        try{
             getcon c = new getcon();
                
                 Con = c.myconnection();
                stmt1 = Con.createStatement();
                 stmt = Con.createStatement();
                 stmt2 = Con.createStatement();
                //check if this client is already exists 
                ResultSet rs = stmt1.executeQuery("select * from mi.clients_data where national_id = '"+rqID+"' or foreignComposite = '"+rqID+"'");
                if(rs.first()){
                
                
                int saved = stmt.executeUpdate(sql);
                if (saved < 1){
                    try (PrintWriter out = response.getWriter()) {out.write("can not update citizen photo");}catch(Exception e){}
                }
                }
                else{
                    
                 if( rqID.matches("^[0-9]+$") ) {
                System.out.println("egyptian client");
                   int updated =  stmt2.executeUpdate("insert into mi.clients_data (photo,national_id) values ('"+data+"' , '"+rqID+"')");                
                if (!(updated > 0)) {
                     try (PrintWriter out = response.getWriter()) {out.write("can not save citizen photo");}catch(Exception e){}
                }
            }
             else {
               
                System.out.println("foreign client");
                        int updated =  stmt2.executeUpdate("insert into mi.clients_data (photo,foreignComposite) values ('"+data+"' , '"+rqID+"')");                
                if (!(updated > 0)) {
                     try (PrintWriter out = response.getWriter()) {out.write("can not save citizen photo");}catch(Exception e){}
                }
            }
                }
                
                
        }catch(SQLException ex){
              Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
              System.err.println(ex.toString());
            }
            finally{
            try {
                Con.close();
                stmt.close();
                stmt1.close();
                stmt2.close();
               
            }
            catch(SQLException e){
                
            }
        }
//             output.write(new BASE64Decoder().decodeBuffer(data));
//             output.flush();
//             output.close();
             }
             catch (Exception e)
             {
             e.printStackTrace();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
