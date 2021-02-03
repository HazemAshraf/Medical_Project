/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.PassportIssueCountry;
import Entity.Paymentnotify;
import Entity.SchoolPaymentnotify;
import Entity.VehicleRequirmentsPaymentConfirm;
import Entity.Vehicleinspection;
import com.aman.medical.db.getcon;
import com.aman.medical.db.getconMedicalServer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
@WebServlet(name = "VehicleRequirmentsConfirm", urlPatterns = {"/VehicleInsp/Inquiry"})
public class VehicleRequirmentsConfirm extends HttpServlet {

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
            throws ServletException, IOException, JRException, ParseException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String applicationType = "";
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            //  inputStream = new FileInputStream("C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\conf\\config.properties");
            inputStream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\conf\\config.properties");

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            applicationType = prop.getProperty("applicationType");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }

        if (applicationType.equals("MEDICAL")) {
            PrintWriter out = response.getWriter();
            JsonObject error = new JsonObject();
            error.addProperty("ERROR", "you are trying to pay on medical applciation");
            out.write(error.toString());
            return;
        }

        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

        EntityManager entityManager = factory.createEntityManager();
        // response.setContentType("text/html;charset=UTF-8");
        // try (PrintWriter out = response.getWriter()) {
        String requestID = request.getParameter("requestNo");

        if (requestID == null || requestID.isEmpty()) {
            PrintWriter out = response.getWriter();
            JsonObject error = new JsonObject();
            error.addProperty("statusCode", "400");
            error.addProperty("Message", "invalid requestID");
            out.write(error.toString());
            return;
        }

        try {
            List<Vehicleinspection> listp = entityManager.createNamedQuery("Vehicleinspection.findByRequestID", Vehicleinspection.class).setParameter("requestID", requestID).getResultList();
            PrintWriter out = response.getWriter();
            JsonObject respMsg = new JsonObject();
            if (listp == null || listp.isEmpty()) {
                respMsg.addProperty("statusCode", "400");
                respMsg.addProperty("Message", "this request ID is not found");
            } else if (listp.get(0).getTotalAmount() == null) {
                respMsg.addProperty("statusCode", "300");
                respMsg.addProperty("Message", "this transaction is not paid");
            } else {
                respMsg.addProperty("statusCode", "200");
                respMsg.addProperty("Message", "this transaction is paid");
                respMsg.addProperty("traffic_unit", listp.get(0).getTrafficUnit());
                respMsg.addProperty("license_type", listp.get(0).getLicenseType());
                respMsg.addProperty("request_no", listp.get(0).getRequestID());
                respMsg.addProperty("applicant_name", listp.get(0).getApplicantName());
                respMsg.addProperty("national_id", listp.get(0).getNationalID());
                respMsg.addProperty("passport_no", listp.get(0).getPassportNo());
                respMsg.addProperty("passport_country", listp.get(0).getPassportIssueCountry());
                respMsg.addProperty("shape_code", listp.get(0).getShapeCode());
                respMsg.addProperty("shape_description", listp.get(0).getShapeDescription());
                respMsg.addProperty("require_vehicle_supplies", listp.get(0).getRequireVehicleSupplies());
                respMsg.addProperty("inspection_services", listp.get(0).getInspectionServices());
                respMsg.addProperty("chassis_no", listp.get(0).getChassisNo());
                respMsg.addProperty("plate_no", listp.get(0).getPlateNo());
            }

            out.write(respMsg.toString());
        } catch (Exception e) {
            System.out.println("catch Exception block -> " + e.toString());
            entityManager.getTransaction().commit();
            PrintWriter out = response.getWriter();
            JsonObject respMsg = new JsonObject();
            respMsg.addProperty("Message", "something went wrong the server says : " + e.toString());
            response.setStatus(500);
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
        } catch (JRException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
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
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(paymentNotification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
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
