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
@WebServlet(urlPatterns = {"/GetClient"})
public class GetClient extends HttpServlet {

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
        //   request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            System.out.println("hereeeeeeeeeeeeeeee");
            String nid = request.getParameter("nid");
            String rid = request.getParameter("rid");
            System.out.println(rid);
            System.out.println(nid);
            Connection Con = null;
            Statement stmt = null, stmt1 = null;
            //  String sql = "select mi.paymentnotify.ApplicantName , mi.paymentnotify.NationalID , mi.paymentnotify.PassportIssueCountry , mi.paymentnotify.PassportNo , mi.paymentnotify.LicenseType , mi.paymentnotify.TrafficUnit , mi.paymentnotify.TotalAmount , mi.paymentnotify.requestID , mi.paymentnotify.queueNumber from mi.paymentnotify  where  mi.paymentnotify.Date = date(now()) and  mi.paymentnotify.TrafficUnit = '"+ request.getSession().getAttribute("TRAFFIC_UNIT").toString() + "' order by mi.paymentnotify.queueNumber;";
            String sql = "select mi.clients_data.eyes_inspection_result , mi.clients_data.internal_inspection_result  , mi.clients_data.Name , mi.clients_data.national_id , mi.clients_data.PassportIssueCountry , mi.clients_data.PassportNo , mi.clients_data.LicenseType , mi.clients_data.TrafficUnit , mi.clients_data.TotalAmount , mi.clients_data.requestID , mi.clients_data.queue from mi.clients_data  where  mi.clients_data.TrafficUnit = '" + request.getSession().getAttribute("TRAFFIC_UNIT").toString() + "' and ( mi.clients_data.requestID  = '" + rid + "' or mi.clients_data.national_id = '" + nid + "' or  mi.clients_data.PassportNo = '" + nid + "' );";

            getcon c = new getcon();

            Con = c.myconnection();

            stmt = Con.createStatement();

            //  stmt1 = Con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            String identity = "";
            String identity_show = "";
            boolean hasPhoto = false;
            String photoAction = "";
            if (rs.first()) {

                if (rs.getString("national_id") != null) {
                    identity = rs.getString("national_id");
                    identity_show = rs.getString("national_id");
                    System.out.println(identity);
                } else {
                    identity_show = rs.getString("PassportNo");
                    identity = rs.getString("PassportIssueCountry") + rs.getString("PassportNo");
                }
//                String requestID = rs.getString("requestID");
//                ResultSet rs1 = stmt1.executeQuery("select * from mi.clients_data where mi.clients_data.requestID = '" + requestID + "';");
//               
                String internInsp = "";
                String ocuInsp = "";
                String status = "";

              stmt1 = Con.createStatement();
              String photoSQL = "select * from mi.clients_photos where requestID = '"+rid+"' or mi.clients_photos.national_id = '" + nid + "'";
            ResultSet rs1 = stmt1.executeQuery(photoSQL);
             photoAction = "إلتقاط الصورة";
                if (rs1.first()) {
                if (rs1.getBlob("photo") != null) {
                    photoAction = "تعديل الصورة";
                }
            }
                stmt1.close();
                internInsp = rs.getString("internal_inspection_result");
                ocuInsp = rs.getString("eyes_inspection_result");
                //                } 

                if (internInsp == null) {
                    internInsp = "";
                }
                if (ocuInsp == null) {
                    ocuInsp = "";
                }
                if (internInsp.equals("acc") && ocuInsp.equals("acc")) {
                    status = "لائق باطنة و نظر";
                } else if (internInsp.equals("acc") && ocuInsp.equals("nacc")) {
                    status = "لائق باطنة و غير لائق نظر";
                } else if (internInsp.equals("nacc") && ocuInsp.equals("acc")) {
                    status = "لائق نظر و غير لائق باطنة";
                } else if (internInsp.isEmpty() && ocuInsp.equals("acc")) {
                    status = "لائق نظر و في مرحلة الباطنة";
                } else if (internInsp.equals("acc") && ocuInsp.isEmpty()) {
                    status = "لائق باطنة و في مرحلة النظر";
                } else if (internInsp.equals("nacc") && ocuInsp.isEmpty()) {
                    status = "غير لائق باطنة و في مرجلة النظر";
                } else if (internInsp.isEmpty() && ocuInsp.equals("nacc")) {
                    status = "غير لائق نظر و في مرحلة الباطنة";
                } else if (internInsp.isEmpty() && ocuInsp.isEmpty()) {
                    status = "لم يبدأ كشف باطنة أو نظر";
                } else if (internInsp.equals("nacc") && ocuInsp.equals("nacc")) {
                    status = "غير لائق باطنة و غير لائق نظر";
                }
                System.out.println(rs.getString("requestID"));
                System.out.println(identity_show);
                String json = "{\"status\": \"" + status + "\",\"queueNumber\": \"" + rs.getString("queue") + "\",\"identity_show\": \"" + identity_show + "\",\"TotalAmount\": \"" + rs.getString("TotalAmount") + "\",\"TrafficUnit\": \"" + rs.getString("TrafficUnit") + "\",\"LicenseType\": \"" + rs.getString("LicenseType") + "\",\"requestID\": \"" + rs.getString("requestID") + "\",\"identity\": \"" + identity + "\" , \"photoAction\": \"" + photoAction + "\" , \"ApplicantName\": \"" + rs.getString("Name") + "\"}";

                out.print(json);
            } else {
                String notExist = "لا يوجد";
                String json = "{\"status\": \"" + notExist + "\",\"queueNumber\": \"" + notExist + "\",\"identity_show\": \"" + notExist + "\",\"TotalAmount\": \"" + notExist + "\",\"TrafficUnit\": \"" + notExist + "\",\"LicenseType\": \"" + notExist + "\",\"requestID\": \"" + notExist + "\",\"identity\": \"" + notExist + "\" , \"photoAction\": \"" + notExist + "\" , \"ApplicantName\": \"" + notExist + "\"}";
                out.print(json);
            }
            stmt.close();
            //stmt1.close();
            Con.close();
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
            Logger.getLogger(GetClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetClient.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GetClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetClient.class.getName()).log(Level.SEVERE, null, ex);
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
