/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Paymentnotify;
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
@WebServlet(name = "manualQueueNumber", urlPatterns = {"/manualQueueNumber"})
public class manualQueueNumber extends HttpServlet {

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
        HashMap params = new HashMap();
        JsonObject rcvd;
        JsonObject obj1 = new JsonObject();

        InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
        JsonReader json = new JsonReader(reader);

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(json);
        rcvd = (JsonObject) root;
        Random random = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println(formatter.format(date));
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa" + rcvd.get("requestID").getAsString());
        // list of requestID in the same dayy
        List<Paymentnotify> listofPayment = entityManager.createNamedQuery("Paymentnotify.findByDateAndRequestID", Paymentnotify.class).setParameter("requestID", rcvd.get("requestID").getAsString()).
                setParameter("date", formatter.format(date).substring(0, 10)).getResultList();

        Paymentnotify p = listofPayment.get(listofPayment.size() - 1);

        System.out.println("aaaaaaa" + p.getQueueNumber());
        System.out.println("+++++++++++++++++++" + listofPayment.size());

       

        PrintWriter out = response.getWriter();
        Paymentnotify obj = new Paymentnotify();
        if (listofPayment.size() >= 3) {
            System.out.println("more than 3 or equal");

            obj1.addProperty("Error", "you can't generate more than 3 queue numbers in the same day your last queue number today was : " + p.getQueueNumber());
            out.write(obj1.toString());

        } else {

            p.setQueueNumberUsed("not used");

            obj.setRequestID(p.getRequestID());
            obj.setTotalAmount(p.getTotalAmount());
            obj.setTrafficUnit(p.getTrafficUnit());
            obj.setAmanUserID(p.getAmanUserID());
            obj.setApplicantMobile(p.getApplicantMobile());
            obj.setApplicantName(p.getApplicantName());
            obj.setDateOfBirth(p.getDateOfBirth());
            obj.setLicenseType(p.getLicenseType());
            if(rcvd.get("NationalID")!=null)
            obj.setNationalID(rcvd.get("NationalID").getAsString());
           else if(rcvd.get("PassportIssueCountry") != null && rcvd.get("PasspornNo") != null){
            obj.setPassportIssueCountry(rcvd.get("PassportIssueCountry").getAsString());
            obj.setPassportNo(rcvd.get("PasspornNo").getAsString());
           }
            obj.setPayedElements(p.getPayedElements());
            obj.setPaymentMethod(p.getPaymentMethod());
            obj.setPaymentNumber(Integer.toString(random.nextInt()));
            obj.setServiceID(p.getServiceID());
            obj.setTimeStamp(formatter.format(date));
            obj.setQueueNumberUsed("used");
            String output = obj.getTimeStamp().substring(0, 10);
            obj.setDate(output);

            List<Paymentnotify> payNotifyByDate = entityManager.createNamedQuery("Paymentnotify.findByDateAndTraffiUnit", Paymentnotify.class).setParameter("date", obj.getDate()).
                    setParameter("trafficUnit", obj.getTrafficUnit()).getResultList();
            if (payNotifyByDate.size() < 1) {
                obj.setQueueNumber("1");
            } else {
                List<Integer> l = new ArrayList<>();
                payNotifyByDate.stream().forEach(i -> {
                    l.add(Integer.parseInt(i.getQueueNumber()));
                });
                Collections.sort(l, Collections.reverseOrder());
                System.out.println("aaaaaaaaaaaaaaaaaaaa list  "+l);
                System.out.println("sadfasfafs "+l.get(0));
                int value = l.get(0) + 1;
                System.out.println("queeeeeeeee "+value);
                obj.setQueueNumber(Integer.toString(value));
            }

            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
            obj1.addProperty("requestID", obj.getRequestID());
            obj1.addProperty("queueNumber", obj.getQueueNumber());
           
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            params.put("TimeStamp", dateFormat.format(date));
            params.put("requestID", obj.getRequestID());

            params.put("QueueNumverNumber", obj.getQueueNumber());

            String fileName = "manual_queue_number_" + obj.getRequestID();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("viPolicy2_3_qoute_reciept_manual.jasper");
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
            String receiptPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\receipt\\" + fileName + ".pdf";
            JasperExportManager.exportReportToPdfFile(print, receiptPath);
            String receiptPathResp = "http://192.168.235.76:8080/receipt/" + fileName + ".pdf";
            // byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
            // OutputStream outStream = response.getOutputStream();

//                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
//                    response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".pdf");
//                    response.setHeader("Content-Stream", byteStream.toString());
            // response.setContentType("application/pdf");
            // response.setContentLength(byteStream.length);
            // out.print("asdasdasdas");
            obj1.addProperty("queueRecieptURL", receiptPathResp);

            out.write(obj1.toString());
        }
        try {
            // System.out.println("bytestream is " + byteStream.length +"     "  +  byteStream.toString());
            //  outStream.write(byteStream);        //,0,byteStream.length);
        } catch (Exception e2) {
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
