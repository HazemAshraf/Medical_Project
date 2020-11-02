/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author win7
 */
@WebServlet(urlPatterns = {"/confirm_data"})
public class ConfirmData extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, JRException {
        Connection Con = null;
        Statement stmt = null , stmt1 = null , stmt2=null;
        try {
                response.setContentType("text/html;charset=UTF-8");
                
                String nid = request.getParameter("nid");
                String passId = request.getParameter("passId");
                String cId = request.getParameter("cId");
                String citizenFees = request.getParameter("citizenFees");
                String citizenName = request.getParameter("citizenName");
                  String citizenBD = request.getParameter("citizenBD");
                String citizenLicense = request.getParameter("citizenLicense");
                  String citizenTU = request.getParameter("citizenTU");
                String citizenReqNo = request.getParameter("citizenReqNo");
                String foreignComp = request.getParameter("foreignComp");
                String queue = request.getParameter("queue");
//                String citizenPhoto = request.getParameter("citizenPhoto");
//                System.out.println("photo url is ====== " + citizenPhoto);
              //  String seller = request.getSession().getAttribute("NAME").toString();
                String Nationality = "" ;
                if (cId.isEmpty() || cId == null) {
                Nationality = "Egy";
               
                }
                else{
                Nationality = cId;
                
                }
                String sql = "INSERT INTO `mi`.`clients_data`\n" +
                        
                        "(`nationality`,\n" +
                        "`national_id`,\n" +
                        "`Name`,\n" +
                        "`BirthDate`,\n" +
                        "`LicenseType`,\n" +
                         "`TrafficUnit`,\n" +
                         "`requestID`,\n" +
                        
                        "`PassportNo`,\n" +
                        "`foreignComposite`,\n" +
                        "`queue`,\n" +
                        "`requestFees`)\n" +
                        
                       
                        "VALUES\n" +
                        "('"+Nationality+"',\n" +
                        "'"+nid+"',\n" +
                        "'"+citizenName+"',\n" +
                        "'"+citizenBD+"',\n" +
                        "'"+citizenLicense+"',\n" +
                         "'"+citizenTU+"',\n" +
                         "'"+citizenReqNo+"',\n" +
                        "'"+passId+"',\n" +
                                 "'"+foreignComp+"',\n" +
                         "'"+queue+"',\n" +
                        "'"+citizenFees+"');";
                    
                
                getcon c = new getcon();
                
                 Con = c.myconnection();
                
                
                 stmt = Con.createStatement();
                int saved = stmt.executeUpdate(sql,stmt.RETURN_GENERATED_KEYS);
                if (saved < 1){
                    // response
                }
                int id;
                long id1;
//                HashMap params = new HashMap();
//                params.put("owner_id", nid);
//                params.put("owner_name", owner_name);
//                params.put("seller", seller);
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
               id = generatedKeys.getInt(1);
                System.out.println("generated key int : " +  id);
            }
            else {
                throw new SQLException("No ID obtained.");
            }
        }
                
                stmt1 = Con.createStatement();
                 ResultSet rs1 = stmt1.executeQuery("select request_date from mi.clients_data where id = '" + id + "'");
                String transDate = "";
                while(rs1.next()) {
                 transDate = String.valueOf(rs1.getTimestamp("request_date"));
               }
               if(transDate.isEmpty()){
               throw new SQLException("can not fint the date of this transaction.");
               }
               String TransNum = transDate ;
               //  String complexString = "3ifhuq023hjk@jka$ksoap";
          String TransNum1 = TransNum.replaceAll("\\D", ""); // numric date 
          // get this format 20191121155304012
          String finalTransFormat = TransNum1.substring(2, 6) + String.valueOf(id);
            System.out.println("final transaction id is : " +  finalTransFormat);
          //update record with the transaction_id
          stmt2 = Con.createStatement();
         int update =  stmt2.executeUpdate("update mi.clients_data set MedicalCheckupID = '"+ finalTransFormat +"' where id = '" + id +"'");
          if(update < 1){
          throw new SQLException("can not update a record with new transaction ID");
          }
         PrintWriter out = response.getWriter();
          out.println ("done");
          
            
          
//          
//                     params.put("trans_id", finalTransFormat);
//                     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
//                     LocalDateTime now = LocalDateTime.now();  
   
//                 params.put("trans_date",dtf.format(now));
//                
//                
//                String fileName = "policy_reciept";
//                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//                InputStream is = classloader.getResourceAsStream("viPolicy2_3_qoute_reciept.jasper");
//                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
//                // JasperReport jasperReport = JasperCompileManager.compileReport("testPDF.jrxml");
//                
//                byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
//                OutputStream outStream = response.getOutputStream();
//                
//                if (true){
//                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//                    response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".pdf");
//                    response.setContentType("application/pdf");
//                }
//                
//                response.setContentLength(byteStream.length);
//                // out.print("asdasdasdas");
//                try {
//                    
//                    outStream.write(byteStream);        //,0,byteStream.length);
//                } catch(Exception e2){
//                    response.setStatus(500);
//                }
//                
            } catch(SQLException ex){
              Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
              System.err.println(ex.toString());
            }
            finally{
            try {
                Con.close();
                stmt.close();
               
            } catch (SQLException ex) {
                Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.toString());
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
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
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
