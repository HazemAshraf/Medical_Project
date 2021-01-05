/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Paymentnotify;
import Entity.Vehicleinspection;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author User
 */
@WebServlet(name = "UsedCancellation", urlPatterns = {"/UsedCancellation"})
public class UsedCancellation extends HttpServlet {

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
            throws ServletException, IOException, JRException, ParseException {
        //response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
        /* TODO output your page here. You may use following sample code. */
//                   JasperReport report = JasperCompileManager.compileReport("C:/User/user/Desktop/testPDF.jrxml");
//                JasperPrint print = JasperFillManager.fillReport(report,null);
//                JasperExportManager.exportReportToPdfFile(print,"C:/User/user/Desktop/Test.pdf");
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

        EntityManager entityManager = factory.createEntityManager();
        // response.setContentType("text/html;charset=UTF-8");
        // try (PrintWriter out = response.getWriter()) {
        JsonObject obj1 = new JsonObject();
        HashMap params = new HashMap();
        JsonObject rcvd;
        // JsonObject obj1 = new JsonObject();

        InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
        JsonReader json = new JsonReader(reader);

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(json);
        rcvd = (JsonObject) root;
        // HashMap params = new HashMap();
        PrintWriter out = response.getWriter();
        System.out.println("REQUEST ID " +rcvd.get("requestID"));
        String requestId=rcvd.get("requestID").toString().substring(1,rcvd.get("requestID").toString().length()-1);
        System.out.println(requestId);

        List<Vehicleinspection> list = entityManager.createNamedQuery("Vehicleinspection.findByRequestID", Vehicleinspection.class).setParameter("requestID", requestId).
                getResultList();
        System.out.println("sizeeeeeeeeeee " + list.size());
        if (list.size() > 0) {
            Vehicleinspection vObj = list.get(0);
            if (vObj.getCancel().equals(0)) {
                obj1.addProperty("message", "this request has not been cancelled ");
                out.print(obj1.toString());

            } else if (vObj.getCancel().equals(1) && vObj.getUsedcancel().equals(0)) {
                obj1.addProperty("useCancellation", "true");
                vObj.setUsedcancel(1);
                entityManager.getTransaction().begin();
                entityManager.persist(vObj);
                entityManager.getTransaction().commit();
                obj1.addProperty("message", "You are verified to use it cancellation  free ");
                out.print(obj1.toString());

            } else if (vObj.getCancel().equals(1) && vObj.getUsedcancel().equals(1)) {
                obj1.addProperty("useCancellation", "false");

                obj1.addProperty("message", "You already used this cancellation  free");
                out.print(obj1.toString());

            }

        } else {
            obj1.addProperty("message", "request id is not available");
            out.print(obj1.toString());
        }

//                
//                try {
//                   // System.out.println("bytestream is " + byteStream.length +"     "  +  byteStream.toString());
//                    outStream.write(byteStream);        //,0,byteStream.length);
//               } catch(Exception e2){
//                    response.setStatus(500);
//                }
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
        } catch (JRException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JRException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
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
