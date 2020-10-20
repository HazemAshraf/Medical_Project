/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.PassportIssueCountry;
import Entity.Paymentnotify;
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
@WebServlet(name = "paymentNotification", urlPatterns = {"/paymentNotification"})
public class paymentNotification extends HttpServlet {

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
        //response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
        /* TODO output your page here. You may use following sample code. */
//                   JasperReport report = JasperCompileManager.compileReport("C:/User/user/Desktop/testPDF.jrxml");
//                JasperPrint print = JasperFillManager.fillReport(report,null);
//                JasperExportManager.exportReportToPdfFile(print,"C:/User/user/Desktop/Test.pdf");

        // load ip 
        String loadedIp = "";
        String loadedNattingIp = "";
        String schoolFeesNew = "";
        String schoolFeesOld = "";
        String bloodFees = "";
        String medicalFees = "";
        String totalAmountSchoolNew = "";
        String totalAmountSchoolOld = "";
        String totalAmountNormal = "";
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
            loadedIp = prop.getProperty("app_ip");
            loadedNattingIp = prop.getProperty("natting_ip");
            schoolFeesNew = prop.getProperty("school_fees_new");
            schoolFeesOld = prop.getProperty("school_fees_old");
            bloodFees = prop.getProperty("blood_fees");
            medicalFees = prop.getProperty("medical_fees");
            totalAmountSchoolNew = prop.getProperty("total_amount_school_new");
            totalAmountSchoolOld = prop.getProperty("total_amount_school_old");
            totalAmountNormal = prop.getProperty("total_amount_normal");
            applicationType = prop.getProperty("applicationType");

            System.out.println("ip running : " + loadedIp + " and the api context : " + loadedNattingIp);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        
        if (applicationType.equals("MEDICAL")){
		PrintWriter out = response.getWriter();
                JsonObject error =  new JsonObject();
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
        JsonObject rcvd;
        JsonObject obj1 = new JsonObject();

        InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
        JsonReader json = new JsonReader(reader);

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(json);
        rcvd = (JsonObject) root;
        HashMap params = new HashMap();
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + rcvd.toString());
        System.out.println("hi boysssssssss  : " + rcvd.get("ServiceID"));

        String a = rcvd.get("ServiceID").getAsString();
        String PayedElements = rcvd.get("PayedElements").getAsString();
        String PassportIssueCountryCode = "";
        if (rcvd.get("PassportIssueCountry") != null) {
            PassportIssueCountryCode = rcvd.get("PassportIssueCountry").getAsString();
        }

        Connection Con = null, Con1 = null;
        Statement stmt, stmt1 = null, stmt2 = null;
        getcon myCon = new getcon();

        if (a == null) {
            PrintWriter out = response.getWriter();
            JsonObject error =  new JsonObject();
            error.addProperty("Error Message", "invalid serviceID");
            out.write(error.toString());
            return;
        }

        if (a.equals("TIT_Medical_payment")) {
            Paymentnotify obj = mapper.readValue(rcvd.toString(), Paymentnotify.class);
            System.out.println("objjjjjjjjj : " + obj.getNationalID() + "  " + obj.getRequestID() + "  ");
            try {

                String jasperName = "";
                System.out.println("aaaaaaaaaaaaa");

                if (!PassportIssueCountryCode.equals("")) {
                    List<PassportIssueCountry> passportIssue = entityManager.createNamedQuery("PassportIssueCountry.findByDescription", PassportIssueCountry.class).setParameter("description", PassportIssueCountryCode).getResultList();
                    if (passportIssue.size() > 0) {
                        obj.setPassportIssueCountry(passportIssue.get(0).getLookUpID());
                    }
                }

                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + obj + "                " + obj.getTotalAmount());
                Random random = new Random();
                obj.setPaymentNumber(Integer.toString(random.nextInt()));
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println("aaaaaaaaaaaaa " + obj);

                System.out.println("bbbbbbbbb " + obj.getTimeStamp());

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
                    System.out.println("aaaaaaaaaaaaaaaaaaaa list  " + l);
                    System.out.println("sadfasfafs " + l.get(0));
                    int value = l.get(0) + 1;
                    System.out.println("queeeeeeeee " + value);
                    obj.setQueueNumber(Integer.toString(value));
                }

                Date d = dateFormat.parse(obj.getTimeStamp());
                System.out.println("dddddddddddddddddddddd " + d);
                //Paymentnotify p = new Paymentnotify();
                List<Paymentnotify> listp = entityManager.createNamedQuery("Paymentnotify.findByRequestID", Paymentnotify.class).setParameter("requestID", obj.getRequestID()).getResultList();
                // System.out.println("aaaaaaaaaaaaaaaaaaaaa "+p);
                 String finalMedicalFees = "0";
                String finalBloodFees = "0";
                String username = "";
                String password = "";
                if (listp.size() > 0) {
                    username = listp.get(0).getEusername();
                    password = listp.get(0).getEpassword();
                     params.put("totalAmount", totalAmountNormal); // 650
                    params.put("medicalFees", finalMedicalFees); // 200
                    params.put("bloodFees", finalBloodFees); // 85
                    if (obj.getPayedElements().contains("Medical")) {
                        finalMedicalFees = medicalFees;
                        finalBloodFees = bloodFees;
                        params.put("medicalFees", finalMedicalFees); // 200
                        params.put("bloodFees", finalBloodFees); // 85
                    }
                    jasperName = "viPolicy2_3_qoute_reciept.jasper";
                    obj.setTotalAmount(String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(totalAmountNormal))); // 200+85

                    //check school rules....
                    stmt = null;

                    Con = myCon.myconnection();
                    if (Con != null) {
                        System.out.println("Database Connection to the localhost done successfully");
                    }
                    stmt = Con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from mi.traffic_units_schools where 1");
                    while (rs.next()) {
                        if (obj.getTrafficUnit().contains(rs.getString("name"))) {
                            // Renew (old)
                            jasperName = "viPolicy2_3_qoute_reciept_school_renew.jasper";
                            totalAmountSchoolOld = String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(schoolFeesOld));
                            params.put("totalAmount", totalAmountSchoolOld); // 1685
                            params.put("medicalFees", finalMedicalFees); // 200
                            params.put("bloodFees", finalBloodFees); // 85
                            params.put("schoolFees", schoolFeesOld); // 1400
                            obj.setTotalAmount(totalAmountSchoolOld); // 1400+200+85

                            if (obj.getPayedElements().contains("E-Exam")) { // new
                                jasperName = "viPolicy2_3_qoute_reciept_school_new.jasper";
                                totalAmountSchoolNew = String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(schoolFeesNew));
                                params.put("totalAmount", totalAmountSchoolNew); // 2085
                                params.put("medicalFees", finalMedicalFees); // 200
                                params.put("bloodFees", finalBloodFees); // 85
                                params.put("schoolFees", schoolFeesNew); // 1800
                                obj.setTotalAmount(totalAmountSchoolNew); // 1800+200+85
                            }

                            break;
                        }
                    }

                    stmt.close();

                    obj.setStatusCode("200 OK");
                    obj.setQueueNumber(listp.get(0).getQueueNumber());

                    //insert new record with requestID at clients data on 192.168.235.76
                    //check if exist first
                    
                   
                    getcon getcon = new getcon();
              
                    getconMedicalServer getcon1 = new getconMedicalServer();
                
                    
                    
                     if(applicationType.equals("ALL")){
                    Con1 = getcon.myconnection();
                     }
                     else{
                     Con1 = getcon1.myconnection();
                     }
                    if (Con1 != null) {
                        System.out.println("Database connection to server 76 done successfully");
                    }

                    stmt2 = Con1.createStatement();
                    ResultSet rs1 = stmt2.executeQuery("select Name from `mi`.`clients_data` where requestID = '" + obj.getRequestID() + "'");
                    if (!rs1.first()) {
                        stmt1 = Con1.createStatement();
                        String foreignComposite = obj.getPassportIssueCountry() + obj.getPassportNo();
                        stmt1.executeUpdate("INSERT INTO `mi`.`clients_data` (`requestID`,`Name`,`LicenseType`,`TrafficUnit`,`PassportNo`,`PassportIssueCountry`,`national_id`,`nationality`,`queue`,`TotalAmount`) VALUES ('" + obj.getRequestID() + "' , '" + obj.getApplicantName() + "' , '" + obj.getLicenseType() + "' , '" + obj.getTrafficUnit() + "' , '" + obj.getPassportNo() + "' , '" + obj.getPassportIssueCountry() + "' , '" + obj.getNationalID() + "' , '" + foreignComposite + "' , '" + obj.getQueueNumber() + "' , '" + obj.getTotalAmount() + "');");
                        stmt1.close();
                    }
                    stmt2.close();
                    //////////

                    obj1.addProperty("RequestID", listp.get(0).getRequestID());
                    obj1.addProperty("Confirmed", "true");
                    obj1.addProperty("TimeStamp", listp.get(0).getTimeStamp());

                    obj1.addProperty("PaymentNumber", listp.get(0).getPaymentNumber());
                    obj1.addProperty("queueNumber", listp.get(0).getQueueNumber());

                    //  obj1.addProperty("Error Message", "This request ID was used before");
                } else {

                   params.put("totalAmount", totalAmountNormal); // 650
                    params.put("medicalFees", finalMedicalFees); // 200
                    params.put("bloodFees", finalBloodFees); // 85
                    if (obj.getPayedElements().contains("Medical")) {
                        finalMedicalFees = medicalFees;
                        finalBloodFees = bloodFees;
                        params.put("medicalFees", finalMedicalFees); // 200
                        params.put("bloodFees", finalBloodFees); // 85
                    }
                    jasperName = "viPolicy2_3_qoute_reciept.jasper";
                    obj.setTotalAmount(String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(totalAmountNormal))); // 200+85

                    //check school rules....
                    stmt = null;
                    Con = myCon.myconnection();
                    if (Con != null) {
                        System.out.println("Database Connection to the localhost server done successfully");
                    }
                    stmt = Con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from mi.traffic_units_schools where 1");
                    while (rs.next()) {
                         if (obj.getTrafficUnit().contains(rs.getString("name"))) {
                            // Renew (old)
                            jasperName = "viPolicy2_3_qoute_reciept_school_renew.jasper";
                            totalAmountSchoolOld = String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(schoolFeesOld));
                            params.put("totalAmount", totalAmountSchoolOld); // 1685
                            params.put("medicalFees", finalMedicalFees); // 200
                            params.put("bloodFees", finalBloodFees); // 85
                            params.put("schoolFees", schoolFeesOld); // 1400
                            obj.setTotalAmount(totalAmountSchoolOld); // 1400+200+85

                            if (obj.getPayedElements().contains("E-Exam")) { // new
                                jasperName = "viPolicy2_3_qoute_reciept_school_new.jasper";
                                totalAmountSchoolNew = String.valueOf(Integer.parseInt(finalMedicalFees) + Integer.parseInt(finalBloodFees) + Integer.parseInt(schoolFeesNew));
                                params.put("totalAmount", totalAmountSchoolNew); // 2085
                                params.put("medicalFees", finalMedicalFees); // 200
                                params.put("bloodFees", finalBloodFees); // 85
                                params.put("schoolFees", schoolFeesNew); // 1800
                                obj.setTotalAmount(totalAmountSchoolNew); // 1800+200+85
                            }

                            break;
                        }
                    }

                    stmt.close();

                    //generate username and password
                    // get course name 
                    String courseName = "";
                    stmt = null;
                    stmt = Con.createStatement();
                    ResultSet rs3 = stmt.executeQuery("select courseName from lictypes where description = '" + obj.getLicenseType() + "'");
                    if (rs3.first()) {
                        courseName = rs3.getString("courseName");
                    }
                    stmt.close();
                    //
                    ElearningAccountGenerator EPG = new ElearningAccountGenerator();
                    ElearningAccount EA = EPG.generateElearningAccount(obj.getRequestID(), courseName);
                    username = EA.getUsername();
                    password = EA.getPassword();
                    obj.setEpassword(password);
                    /////////
                    obj.setEusername(username);
                    obj.setStatusCode("200 OK");
                    entityManager.getTransaction().begin();
                    entityManager.persist(obj);
                    entityManager.getTransaction().commit();

                    //insert new record with requestID at clients data on 192.168.235.76
                     getcon getcon = new getcon();
              
                    getconMedicalServer getcon1 = new getconMedicalServer();
                
                    
                    
                     if(applicationType.equals("ALL")){
                    Con1 = getcon.myconnection();
                     }
                     else{
                     Con1 = getcon1.myconnection();
                     }
                    if (Con1 != null) {
                        System.out.println("Database Connection to server 76 done successfully");
                    }
                    stmt1 = Con1.createStatement();
                    String foreignComposite = obj.getPassportIssueCountry() + obj.getPassportNo();
                    stmt1.executeUpdate("INSERT INTO `mi`.`clients_data` (`requestID`,`Name`,`LicenseType`,`TrafficUnit`,`PassportNo`,`PassportIssueCountry`,`national_id`,`nationality`,`queue`,`TotalAmount`) VALUES ('" + obj.getRequestID() + "' , '" + obj.getApplicantName() + "' , '" + obj.getLicenseType() + "' , '" + obj.getTrafficUnit() + "' , '" + obj.getPassportNo() + "' , '" + obj.getPassportIssueCountry() + "' , '" + obj.getNationalID() + "' , '" + foreignComposite + "' , '" + obj.getQueueNumber() + "' , '" + obj.getTotalAmount() + "');");

                    stmt1.close();
                    //////////

                    obj1.addProperty("RequestID", obj.getRequestID());
                    obj1.addProperty("Confirmed", "true");
                    obj1.addProperty("TimeStamp", obj.getTimeStamp());

                    obj1.addProperty("PaymentNumber", obj.getPaymentNumber());
                    obj1.addProperty("queueNumber", obj.getQueueNumber());
                }

                params.put("receiptType", "خدمـات أمــان للمـرور");
                params.put("ApplicantName", obj.getApplicantName());
                params.put("TimeStamp", obj.getTimeStamp());
                params.put("requestID", obj.getRequestID());
                params.put("TrafficUnit", obj.getTrafficUnit());
                params.put("paymentNumber", obj.getPaymentNumber());
                params.put("QueueNumverNumber", obj.getQueueNumber());
                //generate username and password
                System.out.println("username generated is : " + username);
                System.out.println("password generated is : " + password);
                params.put("username", username);
                params.put("password", password);

                /////////////////////////
                String fileName = "medical_reciept_" + obj.getRequestID();
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream is = classloader.getResourceAsStream(jasperName);
//                //  InputStream is = classloader.getResourceAsStream("viPolicy2_3_qoute_reciept.jasper");
//                // System.out.println("input stream " + is.read() + "   " + is.toString());
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
//                //  JasperReport jasperReport = JasperCompileManager.compileReport("testPDF.jrxml");

////                    System.out.println("aaaaaaaaaaaaaaaaa");
////                JasperReport report = JasperCompileManager.compileReport("C:/User/user/Desktop/testPDF.jrxml");
////                    System.out.println("bbbbbbbbbbbbbbb");
////                JasperPrint print = JasperFillManager.fillReport(report,null);
////                    System.out.println("cccccccccc");
////                JasperExportManager.exportReportToPdfFile(print,"C:/User/user/Desktop/Test.pdf");
////                    System.out.println("ddddddddddddddddddd");
                JasperPrint print = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
                String receiptPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\receipt\\" + fileName + ".pdf";
               //  String receiptPath = "C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\webapps\\path\\to\\receipt\\" + fileName + ".pdf";
                JasperExportManager.exportReportToPdfFile(print, receiptPath);

                String IP = "";
//                // System.out.println("IP of client is : "+request.getRemoteAddr() + " " +request.getRemoteHost());
                String TU = obj.getTrafficUnit();
                IP = loadedIp;

                stmt = null;

                stmt = Con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from mi.flybox_medical where 1");
                while (rs.next()) {
                    if (TU.contains(rs.getString("TrafficUnit"))) {
                        IP = loadedNattingIp;
                        break;
                    }
                }

                stmt.close();

////                if (request.getRemoteAddr().toString().contains("192.168.235.55") || request.getRemoteAddr().toString().contains("192.168.235.51")) {
////                    IP = "192.168.235.76";
////                } else {
////                    IP = "192.168.250.138";
////                }
                String receiptPathResp = "http://" + IP + ":8080/receipt/" + fileName + ".pdf";

//                // byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
//                // OutputStream outStream = response.getOutputStream();
////                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
////                    response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".pdf");
////                    response.setHeader("Content-Stream", byteStream.toString());
//                // response.setContentType("application/pdf");
//                // response.setContentLength(byteStream.length);
//                // out.print("asdasdasdas");
                PrintWriter out = response.getWriter();
                obj1.addProperty("receiptURL", receiptPathResp);
                Con.close();
                Con1.close();
                out.write(obj1.toString());

            } catch (Exception e) {
                System.out.println("catch Exception block -> " + e.toString());
                obj.setStatusCode(e.toString());
                entityManager.getTransaction().begin();
                entityManager.persist(obj);
                entityManager.getTransaction().commit();

                //insert new record with requestID at clients data on 192.168.235.76
//                    getconMedicalServer getcon = new getconMedicalServer();
//                    Con1 = getcon.myconnection();
//                    stmt1 = Con1.createStatement();
//                    String foreignComposite = obj.getPassportIssueCountry() + obj.getPassportNo();
//                    stmt1.executeUpdate("INSERT INTO `mi`.`clients_data` (`requestID`,`Name`,`LicenseType`,`TrafficUnit`,`PassportNo`,`PassportIssueCountry`,`national_id`,`nationality`,`queue`,`TotalAmount`) VALUES ('" + obj.getRequestID() + "' , '" + obj.getApplicantName() + "' , '" + obj.getLicenseType() + "' , '" + obj.getTrafficUnit() + "' , '" + obj.getPassportNo() + "' , '" + obj.getPassportIssueCountry() + "' , '" + obj.getNationalID() + "' , '" + foreignComposite + "' , '" + obj.getQueueNumber() + "' , '" + obj.getTotalAmount() + "');");
//                   stmt1.close();
//                    Con1.close();
                ////////// 
                response.setStatus(500);
            }
        } else if (a.equals("TIT_Vehicle_Inspection_payment")) {
            
            Vehicleinspection VehicleObj = mapper.readValue(rcvd.toString(), Vehicleinspection.class);
            VehicleObj.setTotalAmount(rcvd.get("TotalAmount").getAsString());
            VehicleObj.setCancel(0);
            VehicleObj.setUsedcancel(0);
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + VehicleObj + "                " + VehicleObj.getTotalAmount());
            Random random = new Random();
            VehicleObj.setPaymentNumber(Integer.toString(random.nextInt()));
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            String output = VehicleObj.getTimeStamp().substring(0, 10);

            VehicleObj.setDate(output);
            List<Vehicleinspection> payNotifyByDate = entityManager.createNamedQuery("Vehicleinspection.findByDateAndTraffiUnit", Vehicleinspection.class).setParameter("date", VehicleObj.getDate()).
                    setParameter("trafficUnit", VehicleObj.getTrafficUnit()).getResultList();
            if (payNotifyByDate.size() < 1) {
                VehicleObj.setQueueNumber("1");
            } else {
                List<Integer> l = new ArrayList<>();
                payNotifyByDate.stream().forEach(i -> {
                    l.add(Integer.parseInt(i.getQueueNumber()));
                });
                Collections.sort(l, Collections.reverseOrder());
                System.out.println("aaaaaaaaaaaaaaaaaaaa list  " + l);
                System.out.println("sadfasfafs " + l.get(0));
                int value = l.get(0) + 1;
                System.out.println("queeeeeeeee " + value);
                VehicleObj.setQueueNumber(Integer.toString(value));
            }

            Date d = dateFormat.parse(VehicleObj.getTimeStamp());
            System.out.println("dddddddddddddddddddddd " + d);

            List<Vehicleinspection> listp = entityManager.createNamedQuery("Vehicleinspection.findByRequestID", Vehicleinspection.class).setParameter("requestID", VehicleObj.getRequestID()).getResultList();

            try {
                if (listp.size() > 0) {

                    obj1.addProperty("RequestID", VehicleObj.getRequestID());
                    obj1.addProperty("Confirmed", "true");
                    obj1.addProperty("TimeStamp", VehicleObj.getTimeStamp());

                    obj1.addProperty("PaymentNumber", VehicleObj.getPaymentNumber());
                    obj1.addProperty("queueNumber", VehicleObj.getQueueNumber());

                } else {
                    VehicleObj.setStatusCode("200 OK");
                    entityManager.getTransaction().begin();
                    entityManager.persist(VehicleObj);
                    entityManager.getTransaction().commit();

                    obj1.addProperty("RequestID", VehicleObj.getRequestID());
                    obj1.addProperty("Confirmed", "true");
                    obj1.addProperty("TimeStamp", VehicleObj.getTimeStamp());

                    obj1.addProperty("PaymentNumber", VehicleObj.getPaymentNumber());
                    obj1.addProperty("queueNumber", VehicleObj.getQueueNumber());
                }

                params.put("receiptType", "خدمة الفحص الفني للمركبات");
                params.put("ApplicantName", VehicleObj.getApplicantName());
                params.put("TimeStamp", VehicleObj.getTimeStamp());
                params.put("requestID", VehicleObj.getRequestID());
                params.put("paymentNumber", VehicleObj.getPaymentNumber());
                params.put("TrafficUnit", VehicleObj.getTrafficUnit());
                params.put("totalAmount", VehicleObj.getTotalAmount());
                String fileName = "vehicle_insp_reciept_" + VehicleObj.getRequestID();
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream is = classloader.getResourceAsStream("viPolicy2_3_vehicle_reciept.jasper");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);

                JasperPrint print = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
                String receiptPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\receipt\\" + fileName + ".pdf";
                // String receiptPath = "C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\webapps\\path\\to\\receipt\\" + fileName + ".pdf";
                JasperExportManager.exportReportToPdfFile(print, receiptPath);
                String IP = "";

                String TU = VehicleObj.getTrafficUnit();
                IP = loadedIp;

                stmt1 = null;
                try {
                    Con = myCon.myconnection();
                    stmt1 = Con.createStatement();
                    ResultSet rs = stmt1.executeQuery("select * from mi.flybox_inspection where 1");
                    while (rs.next()) {
                        if (TU.contains(rs.getString("TrafficUnit"))) {
                            IP = loadedNattingIp;
                            break;
                        }
                    }
                } catch (Exception e) {
                    //
                } finally {
                    stmt1.close();
                    Con.close();
                }

                String receiptPathResp = "http://" + IP + ":8080/receipt/" + fileName + ".pdf";

                PrintWriter out = response.getWriter();
                obj1.addProperty("receiptURL", receiptPathResp);
                out.write(obj1.toString());
            } catch (Exception e) {
                VehicleObj.setStatusCode("500 Internal server error");
                entityManager.getTransaction().begin();
                entityManager.persist(VehicleObj);
                entityManager.getTransaction().commit();
                response.setStatus(500);
            }
        } else {
            PrintWriter out = response.getWriter();
            JsonObject error =  new JsonObject();
            error.addProperty("Error Message", "invalid serviceID");
            out.write(error.toString());
            return;
        }

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
