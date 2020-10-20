/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
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
@WebServlet(urlPatterns = {"/retry"})
public class faild extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static int sendPOST(String POST_URL, String POST_PARAMS, String requestID) throws IOException, SQLException, ClassNotFoundException {

        Connection Con = null;
        Statement stmt = null;

        getcon c = new getcon();

//        System.out.println("JSON is " + POST_PARAMS);
//        FileWriter file = new FileWriter("E:\\Biometrics\\log_request.txt");
//        file.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /API/oculist " + POST_PARAMS);
//        file.close();
        System.out.println(POST_URL);
        System.out.println(POST_PARAMS);
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //set request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
//        con.setRequestProperty("Accept-Charset", "UTF-8");
//        con.setRequestProperty("Content-Type", "application/json");
        // For POST only - START
//		con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = POST_PARAMS.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

//                String txt = "\n" + new Timestamp(System.currentTimeMillis()).toString() + " POST /drvintegration_test/API/MedicalCheckup/NotifyResults " + response.toString();
////                      
//                Files.write(Paths.get("E:\\Biometrics\\log_response.txt"), txt.getBytes(), StandardOpenOption.APPEND);
                if (response.toString().contains("\"error_Message\":200")) {
                    //write to database that this succes notified request
                    Con = c.myconnection();
                    stmt = Con.createStatement();
                    int updated = stmt.executeUpdate("insert into mi.log_success_retry (response,requestID) values ('" + response.toString() + "' , '" + requestID + "')");
                    int updated1 = stmt.executeUpdate("update clients_data set notified = 1 where requestID = '" + requestID + "'");

                    stmt.close();
                    Con.close();
                    return 0;
                } else {
                    //write to database that this faild notified request
                    Con = c.myconnection();
                    stmt = Con.createStatement();
                    int updated = stmt.executeUpdate("insert into mi.log_faild_retry (response,requestID) values ('" + response.toString() + "' , '" + requestID + "')");
                    stmt.close();
                    Con.close();
                    return 1;
                }

            }
        } else {
            //write to database that this faild notified request
            Con = c.myconnection();
            stmt = Con.createStatement();
            int updated = stmt.executeUpdate("insert into mi.log_faild_retry (response,requestID) values ('NO JSON COME FROM OTHER SIDE' , '" + requestID + "')");
            stmt.close();
            Con.close();
            return -1;
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, JRException, InterruptedException, SQLException {
        Connection Con = null;
        Statement stmt = null, stmt1 = null, stmt5 = null;
        try {
            response.setContentType("text/html;charset=UTF-8");

            JsonObject obj = new JsonObject();
            String applicationType = "";
            String IP = "";
            String API_CTX = "";
            InputStream inputStream = null;
            try {
                Properties prop = new Properties();
                String propFileName = "config.properties";

                //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                inputStream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\conf\\config.properties");
                // inputStream = new FileInputStream("C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\conf\\config.properties");
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }

                // get the property value and print it out
                applicationType = prop.getProperty("applicationType");
                IP = prop.getProperty("called_ip");
                API_CTX = prop.getProperty("api_ctx");

//            System.out.println("ip running : " + IP + " and the api context : " + API_CTX);
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
//            inputStream.close();
            }

            if (applicationType.equals("MEDICALPAYMENT") || applicationType.equals("INSPECTIONPAYMENT")) {
                PrintWriter out = response.getWriter();
                obj.addProperty("ERROR", "you are trying to retry sending medical to 3S on payment applciation");
                out.write(obj.toString());
                return;
            }

            getcon c = new getcon();

            Con = c.myconnection();

            String sql = "select mi.clients_photos.photo , mi.clients_data.requestID , mi.clients_data.medical_conditions , mi.clients_data.eyes_exam_date , mi.clients_data.inspection_status , mi.clients_data.MedicalCheckupID , mi.clients_data.request_date , mi.clients_data.blood_group from mi.clients_data , mi.clients_photos where mi.clients_data.requestID = mi.clients_photos.requestID and (notified = 0 or notified = -1) and inspection_status != 'W' and inspection_status is not null";
            stmt = Con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String photo64 = "";
            List<String> rIDs = new ArrayList<String>();
            List<String> FaildrIDs = new ArrayList<String>();
            int medicalRes = 1;
            stmt5 = Con.createStatement();
            while (rs.next()) {
                //fetch this request ID 
                Blob b = rs.getBlob("photo");
                byte[] ba = b.getBytes(1, (int) b.length());
                photo64 = new String(ba);

                if (rs.getString("inspection_status").equals("N")) {
                    medicalRes = 2;
                } else if (rs.getString("inspection_status").equals("C")) {
                    medicalRes = 1;
                }
                         //      {\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID                 + ",\"MedicalCheckupID\": \"" + transID                          + "\",\"MedicalCheckupDate\": \"" + eye_request_date               + "\",\"MedicalCheckupResults\":                  2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": []}} 
               
                         String finalMedicalCond = "[]";
                         if(rs.getString("medical_conditions") != null)
                         {
                         if(!rs.getString("medical_conditions").equals("null") && !rs.getString("medical_conditions").isEmpty())
                         {
                            finalMedicalCond = rs.getString("medical_conditions");
                         
                         }
                         }
                         
                         String json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + rs.getString("requestID") + ",\"MedicalCheckupID\": \"" + rs.getString("MedicalCheckupID") + "\",\"MedicalCheckupDate\": \"" + rs.getString("eyes_exam_date") + "\",\"MedicalCheckupResults\": " + medicalRes + ",\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + rs.getString("blood_group") + "\",\"BioPath\": \"\",\"MedicalConditions\": "+finalMedicalCond+"}}";
                         String log = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + rs.getString("requestID") + ",\"MedicalCheckupID\": \"" + rs.getString("MedicalCheckupID") + "\",\"MedicalCheckupDate\": \"" + rs.getString("eyes_exam_date") + "\",\"MedicalCheckupResults\": " + medicalRes + ",\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + rs.getString("blood_group") + "\",\"BioPath\": \"\",\"MedicalConditions\": "+finalMedicalCond+"}}";

                         
                int updated = stmt5.executeUpdate("insert into mi.log_success_request (request,requestID) values ('" + log + "' , '" + rs.getString("requestID") + "')");

                Thread.sleep(3000);
                //sendPOST("http://" + IP + "/" + API_CTX + "/API/MedicalCheckup/NotifyResults", json, requestID);

                int res = sendPOST("http://" + IP + "/" + API_CTX + "/API/MedicalCheckup/NotifyResults", json, rs.getString("requestID"));
                //  int res = sendPOST("http://localhost:8997/drvintegration_test/API/MedicalCheckup/NotifyResults", json, rs.getString("requestID"));
                if (res == 0) {
                    rIDs.add(rs.getString("requestID"));
                }
                if (res != 0) {
                    FaildrIDs.add(rs.getString("requestID"));
                }
//                        int count = 0;
//                        while(res == 1 || res == -1){
//                            if(count == 4) break;
//                            count++;
//                          res = sendPOST("http://192.168.235.50/drvintegration/API/MedicalCheckup/NotifyResults", json, rs.getString("requestID"));
//                        }
            }

            PrintWriter out = response.getWriter();
            JsonObject obj1 = new JsonObject();
            obj1.addProperty("Notified_Request_IDs", rIDs.toString());
            obj1.addProperty("faild Notified_Request_IDs", FaildrIDs.toString());
            out.println(obj1.toString());
//            out.println("requestIDs has notified are -> " + );
//            out.println("requestIDs has faild notified are -> " + FaildrIDs.toString());

        } catch (SQLException ex) {
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.toString());
        } finally {
            stmt5.close();
            stmt.close();
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
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ConfirmData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(faild.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(faild.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (InterruptedException ex) {
            Logger.getLogger(faild.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(faild.class.getName()).log(Level.SEVERE, null, ex);
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
