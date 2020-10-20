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
@WebServlet(name = "reprint", urlPatterns = {"/reprint"})
public class reprint extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
        JsonReader json = new JsonReader(reader);

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(json);
        rcvd = (JsonObject) root;
        // HashMap params = new HashMap();
        // PrintWriter out = response.getWriter();
        System.out.println("REQUEST ID " +rcvd.get("requestID"));
        String requestId=rcvd.get("requestID").toString().substring(1,rcvd.get("requestID").toString().length()-1);
        System.out.println(requestId);

        List<Vehicleinspection> list = entityManager.createNamedQuery("Vehicleinspection.findByRequestID", Vehicleinspection.class).setParameter("requestID", requestId).
                getResultList();
        if (list.size() > 0) {
            Vehicleinspection obj = list.get(0);

            params.put("receiptType", "ايصال بدل فاقد");
            params.put("ApplicantName", obj.getApplicantName());
            params.put("TimeStamp", obj.getTimeStamp());
            params.put("requestID", obj.getRequestID());
             params.put("TrafficUnit", obj.getTrafficUnit());
            params.put("paymentNumber", obj.getPaymentNumber());
            params.put("QueueNumverNumber", obj.getQueueNumber());
            params.put("totalAmount", obj.getTotalAmount());

            String fileName = "vehicle_insp_reciept_" + obj.getRequestID();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("viPolicy2_3_vehicle_reciept.jasper");
            // System.out.println("input stream " + is.read() + "   " + is.toString());
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
            //  JasperReport jasperReport = JasperCompileManager.compileReport("testPDF.jrxml");

//                    System.out.println("aaaaaaaaaaaaaaaaa");
//                JasperReport report = JasperCompileManager.compileReport("C:/User/user/Desktop/testPDF.jrxml");
//                    System.out.println("bbbbbbbbbbbbbbb");
//                JasperPrint print = JasperFillManager.fillReport(report,null);
//                    System.out.println("cccccccccc");
//                JasperExportManager.exportReportToPdfFile(print,"C:/User/user/Desktop/Test.pdf");
//                    System.out.println("ddddddddddddddddddd");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
             Random random = new Random();
              String receiptPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\receipt\\" + fileName + "_" + random.nextInt()+".pdf";
          // String receiptPath = "C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\webapps\\path\\to\\receipt\\" + fileName + "_" + random.nextInt() + ".pdf";
            JasperExportManager.exportReportToPdfFile(print, receiptPath);
            
            
                            String IP = "";
            if(request.getRemoteAddr().toString().contains("192.168.235.50")){
            IP = "192.168.235.76";
            }
            else{
            IP = "192.168.250.138";
            }
            String receiptPathResp = "http://"+IP+":8080/receipt/" + fileName + "_" + random.nextInt() + ".pdf";
            
         
            // byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
            // OutputStream outStream = response.getOutputStream();

//                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//                    response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".pdf");
//                    response.setHeader("Content-Stream", byteStream.toString());
            // response.setContentType("application/pdf");
            // response.setContentLength(byteStream.length);
            // out.print("asdasdasdas");
            obj1.addProperty("receiptURL", receiptPathResp);
            out.write(obj1.toString());

        } else {
            obj1.addProperty("message", "this Request Id is not available");
            out.write(obj1.toString());
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
