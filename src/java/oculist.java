/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author win7
 */
@WebServlet(urlPatterns = {"/oculist"})
public class oculist extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private boolean checkDuplicate(Connection con, String nid) throws SQLException {
        Statement stmt = con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + nid + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            return true;
        }

        return false;
    }

    private String getEyeInspRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("eyes_inspection_result");
        }
        //if(res == null) res = "";
        stmt.close();
        return res;
    }

    private String getInternInspRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("internal_inspection_result");
        }
        //if(res == null) res = "";
        stmt.close();
        return res;
    }

    private String getRightRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("right_eye_degree");
        }
        return res;
    }

    private String getLeftRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("left_eye_degree");
        }
        return res;
    }

    private String InspRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("inspection_status");
        }
        // if(res == null) res = "";
        stmt.close();
        return res;
    }

    private boolean BioInsp(Connection Con, String tId) throws SQLException {
        Blob res = null;
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getBlob("bioFile");
        }
        if (res == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean ImageInsp(Connection Con, String tId) throws SQLException {
        Blob res = null;
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `requestID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getBlob("photo");
        }
        if (res == null) {
            return false;
        } else {
            return true;
        }
    }

    private static int sendPOST(String POST_URL, String POST_PARAMS, String requestID) throws IOException, SQLException, ClassNotFoundException {

        Connection Con = null;
        Statement stmt = null;

        getcon c = new getcon();
//        

//        System.out.println("JSON is " + POST_PARAMS);
//        FileWriter file = new FileWriter("E:\\Biometrics\\log_request.txt");
//        file.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /API/oculist " + POST_PARAMS);
//        file.close();
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
//                System.out.println(response.toString());
//                FileWriter file1 = new FileWriter("E:\\Biometrics\\log_response.txt");
//                file1.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /drvintegration/API/MedicalCheckup/NotifyResults " + response.toString());
//                file1.close();
//                String txt = "\n" + new Timestamp(System.currentTimeMillis()).toString() + " POST /drvintegration_test/API/MedicalCheckup/NotifyResults " + response.toString();
////                      
//                Files.write(Paths.get("E:\\Biometrics\\log_response.txt"), txt.getBytes(), StandardOpenOption.APPEND);
                if (response.toString().contains("\"error_Message\":200")) {
                    //write to database that this succes notified request
                    Con = c.myconnection();
                    stmt = Con.createStatement();
                    int updated = stmt.executeUpdate("insert into mi.log_success (response,requestID) values ('" + response.toString() + "' , '" + requestID + "')");
                    int updated1 = stmt.executeUpdate("update mi.clients_data set notified = 1 where requestID = '"+requestID+"'");
                   
                    stmt.close();
                    Con.close();
                    return 0;
                } else {
                    int codeReturn =1;
                    if(response.toString().contains("\"errorCode\":-4")) codeReturn = -4;
                    //write to database that this faild notified request
                    Con = c.myconnection();
                    stmt = Con.createStatement();
                    int updated = stmt.executeUpdate("insert into mi.log_faild (response,requestID) values ('" + response.toString() + "' , '" + requestID + "')");
                     int updated1 = stmt.executeUpdate("update mi.clients_data set notified = 0 where requestID = '"+requestID+"'");
                    stmt.close();
                    Con.close();
                    return codeReturn;
                }

            }
        } else {
            //write to database that this faild notified request
            Con = c.myconnection();
            stmt = Con.createStatement();
            int updated = stmt.executeUpdate("insert into mi.log_faild (response,requestID) values ('NO JSON COME FROM OTHER SIDE' , '" + requestID + "')");
             int updated1 = stmt.executeUpdate("update mi.clients_data set notified = -1 where requestID = '"+requestID+"'");
            stmt.close();
            Con.close();
            return -1;
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // load ip 
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
            IP = prop.getProperty("called_ip");
            API_CTX = prop.getProperty("api_ctx");

//            System.out.println("ip running : " + IP + " and the api context : " + API_CTX);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
//            inputStream.close();
        }

        try (PrintWriter out = response.getWriter()) {

            String nationalID = request.getParameter("transID");
            String requestID_UI = request.getParameter("requestIDs");
            System.out.println("requestID from UI  :  " + requestID_UI);
            String passNo = request.getParameter("passNo");
            String theCountry = request.getParameter("theCountry");
            String Reye = request.getParameter("Reye");
            String Leye = request.getParameter("Leye");
            String result = request.getParameter("result");
            String blood_group = request.getParameter("blood_group");

            String bioPath = "";

            if (nationalID != null && !(nationalID.isEmpty())) {
                bioPath = "http://192.168.235.76:8080/biometric/" + nationalID + ".pdf"; // to be accissble from outside 

            } else {
                bioPath = "http://192.168.235.76:8080/biometric/" + theCountry + passNo + ".pdf";
            }
//                    if(nationalID != null && !(nationalID.isEmpty())){
//                  bioPath = "http:192.168.235.76\\\\API\\\\Biometrics\\\\" + nationalID + ".pdf";
//                    }
//                    else{
//                     bioPath = "http:192.168.235.76\\\\API\\\\Biometrics\\\\" + theCountry+passNo + ".pdf";
//                    }
            String[] medical_conditions = request.getParameterValues("medical_conditions");
            String medical_conditions_str = null;
            if (medical_conditions != null) {
                StringBuffer medical_conditions_sb = new StringBuffer();
                medical_conditions_sb.append("[");
                for (int i = 0; i < medical_conditions.length; i++) {
                    if (i != medical_conditions.length - 1) {
                        ;
                        medical_conditions_sb.append("{\"Condition\":\"" + medical_conditions[i] + "\",\"Condition_ExInfo\":\"\"}").append(",");
                    } else {
                        medical_conditions_sb.append("{\"Condition\":\"" + medical_conditions[i] + "\",\"Condition_ExInfo\":\"\"}");
                    }
                }
                medical_conditions_sb.append("]");
                medical_conditions_str = medical_conditions_sb.toString();
                //  System.out.println(medical_conditions_str);
            }
//             else{
//             medical_conditions_str = "[]";
//             }

            String transID = "";

            Connection Con = null;
            Statement stmt = null, stmt1 = null;

            getcon c = new getcon();
            Con = c.myconnection();
            stmt = Con.createStatement();
            stmt1 = Con.createStatement();
            String sql = "";

            sql = "select requestID,MedicalCheckupID,request_date,nationality,national_id,PassportNo,eyes_inspection_result,internal_inspection_result,blood_group,hasPhoto from mi.clients_data where requestID = '" + requestID_UI + "'";

            ResultSet rs = stmt.executeQuery(sql);
            String nationality = "", national_id = "", passport_no = "", passport_expiryDT = "", country = "", client_nameA = "",
                    client_nameE = "", requestID = "", gender = "", request_status = "", request_date = "", license_type = "", transaction_id = "", eye_request_date = "";

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            eye_request_date = dtf.format(now);
            String photo64 = "";
            String eyeInspRes = "";
            String internInspRes = "";
            if (rs.first()) {
                //get request information
                requestID = rs.getString("requestID");
                //   System.out.println("requestID in oculist is : " + requestID);
                transID = rs.getString("MedicalCheckupID");

                request_date = String.valueOf(rs.getTimestamp("request_date"));
                blood_group = rs.getString("blood_group");
                // transaction_id = rs.getString("transaction_id");
                int hasPhoto = rs.getInt("hasPhoto");
                //  System.out.println("photo byte : "+b.getBytes(1, (int) b.length()).toString());
                if (hasPhoto != 1) {
                    out.println("<script type='text/javascript'>");

                    out.println("alert('تأكد من إلتقاط صورة');");
                    out.println("location='oculist.jsp';");
                    //request.getRequestDispatcher("Batna/n.jsp").forward(request, response);
                    // out.println("location='Batna/n.jsp';");
                    // out.println ("window.location.href = 'Batna/n.jsp'");
                    out.println("</script>");
                    stmt.close();
                    Con.close();
                    // response.sendRedirect(request.getContextPath() + "/Batna/n.jsp");
                    return;
                }

                //get personal information
                nationality = rs.getString("nationality");
                national_id = rs.getString("national_id");
                if (national_id == null) {
                    national_id = "";
                }
                passport_no = rs.getString("PassportNo");
                if (passport_no == null) {
                    passport_no = "";
                }
//              passport_expiryDT = rs.getString("passport_expiryDT");
//              if(passport_expiryDT == null) passport_expiryDT = "";
                country = rs.getString("nationality");
                if (country == null) {
                    country = "";
                }
                // get transaction id 
                eyeInspRes = rs.getString("eyes_inspection_result");
                internInspRes = rs.getString("internal_inspection_result");
            }
            // if(!BioInsp(Con, requestID) || !ImageInsp(Con, requestID)){
//                       if(!BioInsp(Con, requestID)){
//                    out.println ("<script type='text/javascript'>");
//
//             out.println ("alert('تأكد من اجراء فحص الفيش');");
//              out.println("location='internist.jsp';");
//           //request.getRequestDispatcher("Batna/n.jsp").forward(request, response);
//          // out.println("location='Batna/n.jsp';");
//             // out.println ("window.location.href = 'Batna/n.jsp'");
//             out.println ("</script>");
//
//            // response.sendRedirect(request.getContextPath() + "/Batna/n.jsp");
//             return;
//              }

            // if(checkDuplicate(Con , transID )){
            //   String eyeInspRes = getEyeInspRes(Con, requestID);
            //  String internInspRes = getInternInspRes(Con, requestID);
            //  String InspRes = InspRes(Con, requestID);
//                if(!(eyeInspRes.equals(result))){
            stmt = Con.createStatement();
            boolean intNotAcc = false;
            if (internInspRes == null) {
                internInspRes = "";
            }
            if (eyeInspRes == null) {
                eyeInspRes = "";
            }
            if (internInspRes.equals("nacc")) {
                intNotAcc = true;

//                     JsonObject json = new JsonObject();
//                     json.addProperty("RequestID", requestID);
//                            json.addProperty("MedicalCheckupID", transID);
//                            json.addProperty("MedicalCheckupDate", eye_request_date);
//                            json.addProperty("MedicalCheckupResults", "faild");
//                            json.addProperty("MedicalCheckupPhoto", "");
//                            json.addProperty("MedicalConditions", "faild");
//                                 int res = sendPOST("http://localhost:8080/API/MedicalCheckup/NotifyResults", json.toString() , "1");
//                         if(res == 0){System.out.println("0");}
//                        else if(res == 1){System.out.println("1");}
//                        else System.out.println("unknown error");
                out.println("<script type='text/javascript'>");

                out.println("alert(' الرجاء اعادة كشف الباطنة');");
                out.println("location='oculist.jsp';");
                //response.sendRedirect(request.getContextPath() + "../Nazar/index.jsp");
                //out.println("location='../Nazar/index.jsp';");

                out.println("</script>");
                stmt.close();
                Con.close();
                return;
            }
            if (result.equals("nacc")) {
                stmt.executeUpdate("update `clients_data` set `eyes_inspection_result` = '" + result + "' , `inspection_status` = 'N' , `eyes_exam_date` = '" + eye_request_date + "' , `right_eye_degree` = '" + Reye + "' , `left_eye_degree` = '" + Leye + "' where `requestID` ='" + requestID + "'");
                if (intNotAcc) {
                    sql = "select photo from mi.clients_photos where requestID = '" + requestID_UI + "'";
                    ResultSet rs1 = stmt1.executeQuery(sql);
                    if (rs1.first()) {
                        Blob b = rs1.getBlob("photo");
                        byte[] ba = b.getBytes(1, (int) b.length());
                        photo64 = new String(ba);
                    }
                    stmt1.close();
                    String json = "";
                     String final_medical_cond = "[]";
                    if (medical_conditions_str != null) {
                    final_medical_cond = medical_conditions_str;
                    }
             //       if (medical_conditions_str != null) {
                        //json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": "+requestID+",\"MedicalCheckupID\": \""+transID+"\",\"MedicalCheckupDate\": \""+eye_request_date+"\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"\",\"BioPath\": \""+bioPath+"\",\"MedicalConditions\": "+medical_conditions_str+"}}";

                        json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";
                  //  } else {
                        //json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": "+requestID+",\"MedicalCheckupID\": \""+transID+"\",\"MedicalCheckupDate\": \""+eye_request_date+"\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"\",\"BioPath\": \""+bioPath+"\",\"MedicalConditions\": []}}";

                    //    json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": []}}";

                  //  }

                    String jsonRequest = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": "+final_medical_cond+"}}";

                    Statement stmt5 = null;

                    stmt5 = Con.createStatement();
                    int updated = stmt5.executeUpdate("insert into mi.log_success_request (request,requestID) values ('" + jsonRequest + "' , '" + requestID + "')");
                    stmt5.close();
                    stmt.close();
                    Con.close();
                    int res = sendPOST("http://" + IP + "/" + API_CTX + "/API/MedicalCheckup/NotifyResults", json, requestID);

                    //   int res = sendPOST("/API/MedicalCheckup/NotifyResults", json , "1");
                    if (res == 0) {
                        // System.out.println("0");

                        out.println("<script type='text/javascript'>");

                        out.println("alert('تم ارسال الفحص');");
                        out.println("location='oculist.jsp';");
                        out.println("</script>");

                        return;

                    } else if (res == 1) {
                        out.println("there is error in inputs");
                        //  System.out.println("1");
                        out.println("<script type='text/javascript'>");

                        out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                        out.println("location='oculist.jsp';");
                        out.println("</script>");

                        return;
                    }
                    else if (res == -4){
                         out.println("there is error in inputs");
                        //  System.out.println("1");
                        out.println("<script type='text/javascript'>");

                        out.println("alert('  برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص حيث انه تم الرد من قبل علي هذه المعاملة');");
                        out.println("location='oculist.jsp';");
                        out.println("</script>");

                        return;
                    }
                    else {
                        out.println("otherwise error");
                        //  System.out.println("unknown error");
                        out.println("<script type='text/javascript'>");

                        out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                        out.println("location='oculist.jsp';");
                        out.println("</script>");

                        return;
                    }
                }
            } else {
                if (internInspRes.isEmpty()) {
                    stmt.executeUpdate("update `clients_data` set `medical_conditions` = '" + medical_conditions_str + "' , `eyes_inspection_result` = '" + result + "' , `inspection_status` = 'W' , `eyes_exam_date` = '" + eye_request_date + "' , `right_eye_degree` = '" + Reye + "' , `left_eye_degree` = '" + Leye + "' where `requestID` ='" + requestID + "'");
                } else {
                    if ((internInspRes.equals("acc") && result.equals("acc")) || (internInspRes.equals("sacc") && result.equals("acc")) || (internInspRes.equals("acc") && result.equals("sacc")) || (internInspRes.equals("sacc") && result.equals("sacc"))) {
                        stmt.executeUpdate("update `clients_data` set `medical_conditions` = '" + medical_conditions_str + "' , `eyes_inspection_result` = '" + result + "' , `inspection_status` = 'C' , `eyes_exam_date` = '" + eye_request_date + "' , `right_eye_degree` = '" + Reye + "' , `left_eye_degree` = '" + Leye + "' where `requestID` ='" + requestID + "'");
                        sql = "select photo from mi.clients_photos where requestID = '" + requestID_UI + "'";
                        ResultSet rs1 = stmt1.executeQuery(sql);
                        if (rs1.first()) {
                            Blob b = rs1.getBlob("photo");
                            byte[] ba = b.getBytes(1, (int) b.length());
                            photo64 = new String(ba);
                        }
                        stmt1.close();
                        String json = "";
                         String final_medical_cond = "[]";
                    if (medical_conditions_str != null) {
                    final_medical_cond = medical_conditions_str;
                    }
                     //   System.out.println(photo64);
                      //  if (medical_conditions_str != null) {
                            //    json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": "+requestID+",\"MedicalCheckupID\": \""+transID+"\",\"MedicalCheckupDate\": \""+eye_request_date+"\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \""+photo64+"\",\"BloodGroup\": \"\",\"BioPath\": \""+bioPath+"\",\"MedicalConditions\": "+medical_conditions_str+"}}";

                            json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";

                      //  } else {
                            //  json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": "+requestID+",\"MedicalCheckupID\": \""+transID+"\",\"MedicalCheckupDate\": \""+eye_request_date+"\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \""+photo64+"\",\"BloodGroup\": \"\",\"BioPath\": \""+bioPath+"\",\"MedicalConditions\": []}}";

                       //     json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": []}}";

                      //  }
//                        String medicalCond = "";
//                        if (medical_conditions_str != null) {
//                            medicalCond = medical_conditions_str;
//                        }
                        String jsonRequest = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + eye_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";

                        Statement stmt5 = null;

                        stmt5 = Con.createStatement();
                        int updated = stmt5.executeUpdate("insert into mi.log_success_request (request,requestID) values ('" + jsonRequest + "' , '" + requestID + "')");
                        stmt5.close();
                        stmt.close();
                        Con.close();
                        int res = sendPOST("http://" + IP + "/" + API_CTX + "/API/MedicalCheckup/NotifyResults", json, requestID);

                        //   int res = sendPOST("/API/MedicalCheckup/NotifyResults", json , "1");
                        if (res == 0) {
                            //   System.out.println("0");
                            //   System.out.println("200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('تم إرسال الكشف الي نظام التراخيص');");
                            out.println("location='oculist.jsp';");
                            out.println("</script>");

                            return;

                        } else if (res == 1) {
                            out.println("there is error in inputs");
                            //    System.out.println("1");
                            //     System.out.println("Error -> NOT 200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                            out.println("location='oculist.jsp';");
                            out.println("</script>");

                            return;
                        }
                         else if (res == -4){
                         out.println("there is error in inputs");
                        //  System.out.println("1");
                        out.println("<script type='text/javascript'>");

                        out.println("alert('  برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص حيث انه تم الرد من قبل علي هذه المعاملة');");
                        out.println("location='oculist.jsp';");
                        out.println("</script>");

                        return;
                    }
                         else {
                            out.println("otherwise error");
                            //   System.out.println("not 1 or 0 response error message code");
                            //   System.out.println("Error -> NOT 200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                            out.println("location='oculist.jsp';");
                            out.println("</script>");

                            return;
                        }
                    }

                }
            }

            out.println("<script type='text/javascript'>");

            out.println("alert(' تم الكشف');");
            out.println("location='oculist.jsp';");
            // out.println("location='Batna/n.jsp';");

            out.println("</script>");
            stmt.close();
            Con.close();
            return;

//                }
//                else{
//                
//                                out.println ("<script type='text/javascript'>");
//          
//             out.println ("alert(' هذا الكشف يوجد مسبقا');");
//            out.println("location='oculist.jsp';");
//           //out.println("location='Batna/n.jsp';");
//              
//             out.println ("</script>");
//             
//                }
//            }
//            
//            else{
////                //insert
////             stmt = Con.createStatement();
////             stmt.executeUpdate("INSERT INTO `clients_data` "
////     + "( `request_no` , `request_date` , `national_id` , `passport_no` , `country` , `passport_expiryDT` , `eyes_exam_date` , `eyes_inspection_result` , `inspection_status` , `transaction_id` , `right_eye_degree` , `left_ eye _degree` ) " 
////     + "VALUES ('"+request_no+"', '"+request_date+"', '"+national_id+"', '"+passport_no+"', '"+country+"', '"+passport_expiryDT+"', '" +eye_request_date+"', '"+result+"',  'W' , '"+transID+"', '"+Reye+"', '"+Leye+"')");
////              
//             out.println ("<script type='text/javascript'>");
//          
//             out.println ("alert('تأكد من خطوة استعلام الباينات');");
//           
//         //  out.println("location='Batna/n.jsp';");
//              
//             out.println ("</script>");
//                 
//            }
            //out.println(request.getParameter("result"));
//             String nationaId = request.getParameter("nid");
//             String name = request.getParameter("name");
//          // Inspection params
//            String rightEye = request.getParameter("reye");
//            String leftEye = request.getParameter("leye");
//            String result = request.getParameter("result");
//            String trafficUnit  = request.getSession().getAttribute("TRAFFIC_UNIT").toString();
//            out.print(out);
//            /////////////////////////
//            Statement stmt = null;    
//       
//                 getcon c = new getcon();
//            
//            Connection Con = c.myconnection();
//            
//            if(checkDuplicate(Con , nationaId )){
//           
//                //first get internal_inspection 
//                String eyeInspRes = getEyeInspRes(Con , nationaId);
//                String internInspRes = getInternInspRes(Con , nationaId);
//                String rightInspRes = getRightRes(Con , nationaId);
//                 String leftInspRes = getRightRes(Con , nationaId);
//                String InspRes = InspRes(Con , nationaId);
//                //if(eyeInspRes.isEmpty()) out.println("eyes insp res cant be catched");
//                // update and check eyes_inspection if acc and this.result acc also so set Inspection_status to C
//                if(eyeInspRes == null || rightInspRes == null || leftInspRes == null){
//                eyeInspRes = "";
//                rightInspRes = "";
//                leftInspRes = "";
//                } 
//                if(!(eyeInspRes.equals(result)) || !(rightInspRes.equals(rightEye)) || !(leftInspRes.equals(leftEye))){
//                 stmt = Con.createStatement();
//                 if(result.equals("nacc")){
//                 stmt.executeUpdate("update `clients_data` set `RIGHT_EYE` = '"+rightEye+"' , `LEFT_EYE` = '"+leftEye+"' , `EYES_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'N' where `NATIONAL_ID` = '"+nationaId+"'");
//                 }
//                 else{
//                     if(internInspRes == null){
//                stmt.executeUpdate("update `clients_data` set `RIGHT_EYE` = '"+rightEye+"' , `LEFT_EYE` = '"+leftEye+"' , `EYES_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'W' where `NATIONAL_ID` = '"+nationaId+"'");
//                     }
//                     else{
//                      if(internInspRes.equals("acc") && result.equals("acc")){
//                stmt.executeUpdate("update `clients_data` set `RIGHT_EYE` = '"+rightEye+"' , `LEFT_EYE` = '"+leftEye+"' , `EYES_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'C' where `NATIONAL_ID` = '"+nationaId+"'");
//                      }
//                      
//                     }
//                
//                 }
//                 out.println ("<script type='text/javascript'>");
//          
//             out.println ("alert(' تم الكشف');");
//           
//          out.println("location='oculist.jsp';");
//              
//             out.println ("</script>");
//                }
//                else{
//                
//                                out.println ("<script type='text/javascript'>");
//          
//             out.println ("alert(' هذا الفحص يوجد مسبقا');");
//           
//          out.println("location='oculist.jsp';");
//              
//             out.println ("</script>");
//             
//                }
//            }
//            else{
//                //insert
//             stmt = Con.createStatement();
//             stmt.executeUpdate("INSERT INTO `clients_data` ( `NATIONAL_ID` , `NAME` , `TRAFFIC_UNIT` , `RIGHT_EYE` , `LEFT_EYE` , `EYES_INSPECTION` , `INSPECTION_STATUS`) " + "VALUES ('"+nationaId+"', '"+name+"', '"+trafficUnit+"', '"+rightEye+"', '"+leftEye+"', '" +result+"', 'W')");
//              out.println ("<script type='text/javascript'>");
//          
//             out.println ("alert('تم تسجيل نتيجة الفحص');");
//           
//           out.println("location='oculist.jsp';");
//              
//             out.println ("</script>");
//                 
//            }
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
            Logger.getLogger(oculist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(oculist.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(oculist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(oculist.class.getName()).log(Level.SEVERE, null, ex);
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
