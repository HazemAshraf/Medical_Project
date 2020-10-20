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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
 * @author Hazem Ashraf
 */
@WebServlet(urlPatterns = {"/internist"})
public class internist extends HttpServlet {

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

    private String getBloodRes(Connection Con, String nationaId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `NATIONAL_ID` = '" + nationaId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("BLOOD");
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

    private static void sendGET(String GET_URL, String TrackID) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //set request header
        con.setRequestMethod("GET");
        con.setRequestProperty("version", "1.0");
        con.setRequestProperty("category", "Request");
        con.setRequestProperty("Service", "TIT_Medical_Results");
        con.setRequestProperty("Timestamp", String.valueOf(System.currentTimeMillis()));
        con.setRequestProperty("TrackID", TrackID);

        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
        } else {

        }

    }

    private static int sendPOST(String POST_URL, String POST_PARAMS, String requestID) throws IOException, SQLException, ClassNotFoundException {

        Connection Con = null;
        Statement stmt = null;

        getcon c = new getcon();

        //  System.out.println("JSON is " + POST_PARAMS);
//        FileWriter file = new FileWriter("E:\\Biometrics\\log_request.txt");
//        file.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /API/internist " + POST_PARAMS);
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
                   int codeReturn = 1;
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
        try (PrintWriter out = response.getWriter()) {

            String nationalID = request.getParameter("transID");
            String requestID_UI = request.getParameter("requestIDs");
            //  System.out.println("requestID from UI  :  " + requestID_UI);
            String passNo = request.getParameter("passNo");
            String theCountry = request.getParameter("theCountry");
            if (nationalID == null && requestID_UI == null && passNo == null && theCountry == null) {
                return;
            }

            // load ip 
            String IP = "";
            String API_CTX = "";
            InputStream inputStream = null;
            try {
                Properties prop = new Properties();
                String propFileName = "config.properties";

                //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                inputStream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\conf\\config.properties");
                //  inputStream = new FileInputStream("C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\conf\\config.properties");
                if (inputStream != null) {
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }

                // get the property value and print it out
                IP = prop.getProperty("called_ip");
                API_CTX = prop.getProperty("api_ctx");

                //   System.out.println("ip running : " + IP + " and the api context : " + API_CTX);
            } catch (Exception e) {
                //  System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }

            //  System.out.println("a7aaaaaaaaaaaaaaaaaaaaaaaaaaaaa -> " + nationalID + " " + theCountry + " " + passNo);
            String result = request.getParameter("result");

            String blood_group = request.getParameter("blood_group");
            String bioPath = "";
            if (nationalID != null && !(nationalID.isEmpty())) {
                bioPath = "http://192.168.235.76:8080/biometric/" + nationalID + ".pdf"; // to be accissble from outside 

            } else {
                bioPath = "http://192.168.235.76:8080/biometric/" + theCountry + passNo + ".pdf";
            }
//            if (nationalID != null && !(nationalID.isEmpty())) {
//                bioPath = "http:192.168.235.76\\\\API\\\\Biometrics\\\\" + nationalID + ".pdf";
//            } else {
//                bioPath = "http:192.168.235.76\\\\API\\\\Biometrics\\\\" + theCountry + passNo + ".pdf";
//            }

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
//            else{
//            medical_conditions_str = "[]";
//            }

//             System.out.println("the medical condiotns are : " + medical_conditions[0]);
            String transID = "";

            Connection Con = null;
            Statement stmt = null, stmt1 = null, stmt2 = null;

            getcon c = new getcon();
            Con = c.myconnection();

            stmt = Con.createStatement();
            stmt1 = Con.createStatement();
            // System.err.println(transID);
            String sql = "";
            //  System.out.println("Composite key is = > " + theCountry + passNo);
            sql = "select requestID,MedicalCheckupID,request_date,nationality,national_id,PassportNo,eyes_inspection_result,internal_inspection_result,hasPhoto from mi.clients_data where requestID = '" + requestID_UI + "'";

            ResultSet rs = stmt.executeQuery(sql);
            String nationality = "", national_id = "", passport_no = "", request_date = "", country = "", requestID = "", internal_request_date = "";

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            internal_request_date = dtf.format(now);
            String photo64 = "";
            String eyeInspRes = "";
            String internInspRes = "";
            if (rs.first()) {
                //get request information
                requestID = rs.getString("requestID");
                //  System.out.println("");
                //  System.out.println("requestID in internist is : " + requestID);
                transID = rs.getString("MedicalCheckupID");
                //request_no = rs.getInt("requestID");
                request_date = String.valueOf(rs.getTimestamp("request_date"));
                // transaction_id = rs.getString("transaction_id");
                int hasPhoto = rs.getInt("hasPhoto");
                //  System.out.println("photo byte : "+b.getBytes(1, (int) b.length()).toString());
                if (hasPhoto != 1) {
                    out.println("<script type='text/javascript'>");

                    out.println("alert('تأكد من إلتقاط الصورة');");
                    out.println("location='internist.jsp';");
                    //request.getRequestDispatcher("Batna/n.jsp").forward(request, response);
                    // out.println("location='Batna/n.jsp';");
                    // out.println ("window.location.href = 'Batna/n.jsp'");
                    out.println("</script>");

                    // response.sendRedirect(request.getContextPath() + "/Batna/n.jsp");
                    stmt.close();
                    Con.close();

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

            // if (!BioInsp(Con, requestID) || !ImageInsp(Con, requestID)) {
//            if (!BioInsp(Con, requestID)) {
//                out.println("<script type='text/javascript'>");
//
//                out.println("alert('تأكد من اجراء فحص الفيش');");
//                out.println("location='internist.jsp';");
//                //request.getRequestDispatcher("Batna/n.jsp").forward(request, response);
//                // out.println("location='Batna/n.jsp';");
//                // out.println ("window.location.href = 'Batna/n.jsp'");
//                out.println("</script>");
//
//                // response.sendRedirect(request.getContextPath() + "/Batna/n.jsp");
//                return;
//            }
            // if(checkDuplicate(Con , transID )){
            //  System.out.println("check duplicate");
            // String eyeInspRes = getEyeInspRes(Con, requestID);
            //   System.out.println(eyeInspRes);
            //  String internInspRes = getInternInspRes(Con, requestID);
//            String InspRes = InspRes(Con, requestID);
            if (internInspRes == null) {
                internInspRes = "";
            }
            //  System.out.println("a7a tany  " + requestID + "a7a " + internInspRes + "moft7a" + result);
//            if (!(internInspRes.equals(result))) {
            //System.err.println("if(!(internInspRes.equals(result)))");
            stmt = Con.createStatement();
            if (eyeInspRes == null) {
                eyeInspRes = "";
            }
            boolean eyeNotAcc = false;
            if (eyeInspRes.equals("nacc")) {
                eyeNotAcc = true;
//                    stmt.executeUpdate("update `clients_data` set `blood_group` = '"+blood_group+"' , `internal_inspection_result` = '" + result + "' , `internal_inspection_date` = '" + internal_request_date + "' where `requestID` ='" + requestID + "'");

                //response.sendRedirect(request.getContextPath() + "../Batna/n.jsp");
//                     JsonObject json = new JsonObject();
//                     json.addProperty("RequestID", request_no);
//                            json.addProperty("MedicalCheckupID", transID);
//                            json.addProperty("MedicalCheckupDate", internal_request_date);
//                            json.addProperty("MedicalCheckupResults", "faild");
//                            json.addProperty("MedicalCheckupPhoto", "");
//                            json.addProperty("MedicalConditions", "faild");
//                                 int res = sendPOST("http://localhost:8080/API/MedicalCheckup/NotifyResults", json.toString() , "1");
//                         if(res == 0){System.out.println("0");}
//                        else if(res == 1){System.out.println("1");}
//                        else System.out.println("unknown error");
                out.println("<script type='text/javascript'>");

                out.println("alert(' الرجاء اعادة امتحان النظر');");
                out.println("location='internist.jsp';");
                out.println("</script>");
                stmt.close();
                Con.close();

                return;
            }

            if (result.equals("nacc")) {
                stmt.executeUpdate("update `clients_data` set `blood_group` = '" + blood_group + "' , `internal_inspection_result` = '" + result + "' , `inspection_status` = 'N' , `internal_inspection_date` = '" + internal_request_date + "' where `requestID` ='" + requestID + "'");
                if (eyeNotAcc) {
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
//                    if (medical_conditions_str != null) {
                        // json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"\",\"BioPath\": \"" + bioPath + "\",\"MedicalConditions\": " + medical_conditions_str + "}}";

                        json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";
//                    } else {
                        //json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"\",\"BioPath\": \"" + bioPath + "\",\"MedicalConditions\": []}}";

                     //   json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": []}}";

//                    }
                    String jsonRequest = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 2,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": "+final_medical_cond+"}}";

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

                        out.println("alert('تم ارسال الفحص');");
                        out.println("location='internist.jsp';");
                        out.println("</script>");

                        return;
                    } else if (res == 1) {
                        out.println("there is error in inputs");
                        //  System.out.println("1");
                        //  System.out.println("Error -> NOT 200 OK response");
                        out.println("<script type='text/javascript'>");

                        out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                        out.println("location='internist.jsp';");
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
                        out.println("location='internist.jsp';");
                        out.println("</script>");

                        return;
                    }
                }
            } else {
                if (eyeInspRes.isEmpty()) {
                    stmt.executeUpdate("update `clients_data` set `medical_conditions` = '" + medical_conditions_str + "' , `blood_group` = '" + blood_group + "' , `internal_inspection_result` = '" + result + "' , `inspection_status` = 'W' , `internal_inspection_date` = '" + internal_request_date + "' where `requestID` ='" + requestID + "'");
                } else {
                    if ((eyeInspRes.equals("acc") && result.equals("acc")) || (eyeInspRes.equals("sacc") && result.equals("acc")) || ((eyeInspRes.equals("acc") && result.equals("sacc"))) || ((eyeInspRes.equals("sacc") && result.equals("sacc")))) {
                        stmt.executeUpdate("update `clients_data` set `medical_conditions` = '" + medical_conditions_str + "' , `blood_group` = '" + blood_group + "' , `internal_inspection_result` = '" + result + "' , `inspection_status` = 'C' , `internal_inspection_date` = '" + internal_request_date + "' where `requestID` ='" + requestID + "'");
                        //
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
                     //   if (medical_conditions_str != null) {
                            //json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"\",\"BioPath\": \"" + bioPath + "\",\"MedicalConditions\": " + medical_conditions_str + "}}";

                            json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";
                       // } else {
                            // json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"\",\"BioPath\": \"" + bioPath + "\",\"MedicalConditions\": []}}";

                          //  json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": []}}";

                       // }
//                        String medicalCond = "";
//                        if (medical_conditions_str != null) {
//                            medicalCond = medical_conditions_str;
//                        }
                        String jsonRequest = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestamp\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"" + transID + "\",\"MedicalCheckupDate\": \"" + internal_request_date + "\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"\",\"BloodGroup\": \"" + blood_group + "\",\"BioPath\": \"\",\"MedicalConditions\": " + final_medical_cond + "}}";

                        Statement stmt6 = null;

                        stmt6 = Con.createStatement();
                        int updated = stmt6.executeUpdate("insert into mi.log_success_request (request,requestID) values ('" + jsonRequest + "' , '" + requestID + "')");
                        stmt6.close();
                        stmt.close();
                        Con.close();

                        int res = sendPOST("http://" + IP + "/" + API_CTX + "/API/MedicalCheckup/NotifyResults", json, requestID);

                        //   int res = sendPOST("/API/MedicalCheckup/NotifyResults", json , "1");
                        if (res == 0) {
                            //      System.out.println("0");
                            //     System.out.println("200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('تم إرسال الكشف الي نظام التراخيص');");
                            out.println("location='internist.jsp';");
                            out.println("</script>");

                            return;

                        } else if (res == 1) {
                            out.println("there is error in inputs");
                            //    System.out.println("1");
                            //   System.out.println("Error -> NOT 200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                            out.println("location='internist.jsp';");
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
                            //      System.out.println("not 1 or 0 response error message code");
                            //     System.out.println("Error -> NOT 200 OK response");
                            out.println("<script type='text/javascript'>");

                            out.println("alert('برجاء التواصل مع نظام التراخيص لعدم ارسال الفحص');");
                            out.println("location='internist.jsp';");
                            out.println("</script>");

                            return;
                        }
                    }

                }
            }

            out.println("<script type='text/javascript'>");

            out.println("alert(' تم الكشف');");

            out.println("location='internist.jsp';");

            out.println("</script>");

            stmt.close();
            Con.close();
            return;

//            } else {
//
//                out.println("<script type='text/javascript'>");
//
//                out.println("alert(' هذا الكشف يوجد مسبقا');");
//
//                out.println("location='internist.jsp';");
//
//                out.println("</script>");
//
//            }
//            }
//
//            else{
////                //insert
////             stmt = Con.createStatement();
////             stmt.executeUpdate("INSERT INTO `clients_data` "
////     + "( `request_no` , `request_date` , `national_id` , `passport_no` , `country` , `passport_expiryDT` , `internal_inspection_date` , `internal_inspection_result` , `inspection_status` , `transaction_id` ) "
////     + "VALUES ('"+request_no+"', '"+request_date+"', '"+national_id+"', '"+passport_no+"', '"+country+"', '"+passport_expiryDT+"', '" +internal_request_date+"', '"+result+"',  'W' , '"+transID+"')");
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
            // Person info params
//            String nationaId = request.getParameter("nid");
//             String name = request.getParameter("name");
//          // Inspection params
//            String blood = request.getParameter("blood");
//            String result = request.getParameter("result");
//            String trafficUnit  = request.getSession().getAttribute("TRAFFIC_UNIT").toString();
//           // int saved = 0;
//            Statement stmt = null;
//
//                 getcon c = new getcon();
//
//            Connection Con = c.myconnection();
//
//            if(checkDuplicate(Con , nationaId )){
//
//                //first get eyes_inspection
//                String eyeInspRes = getEyeInspRes(Con , nationaId);
//                String internInspRes = getInternInspRes(Con , nationaId);
//                String bloodInspRes = getBloodRes(Con , nationaId);
//                String InspRes = InspRes(Con , nationaId);
//                //if(eyeInspRes.isEmpty()) out.println("eyes insp res cant be catched");
//                // update and check eyes_inspection if acc and this.result acc also so set Inspection_status to C
//                if(internInspRes == null || bloodInspRes == null){
//                internInspRes = "";
//                bloodInspRes = "";
//                }
//                if(!(internInspRes.equals(result)) || !(bloodInspRes.equals(blood))){
//                 stmt = Con.createStatement();
//                 if(result.equals("nacc")){
//                 stmt.executeUpdate("update `medical_exam` set `BLOOD` = '"+blood+"' , `INTERNAL_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'N' where `NATIONAL_ID` ='"+nationaId+"'");
//                 }
//                 else{
//                     if(eyeInspRes == null){
//                     stmt.executeUpdate("update `medical_exam` set `BLOOD` = '"+blood+"' , `INTERNAL_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'W' where `NATIONAL_ID` ='"+nationaId+"'");
//                     }
//                     else{
//                      if(eyeInspRes.equals("acc") && result.equals("acc")){
//                       stmt.executeUpdate("update `medical_exam` set `BLOOD` = '"+blood+"' , `INTERNAL_INSPECTION` = '"+result+"' , `INSPECTION_STATUS` = 'C' where `NATIONAL_ID` ='"+nationaId+"'");
//                      }
//
//                     }
//
//                 }
//                 out.println ("<script type='text/javascript'>");
//
//             out.println ("alert(' تم الكشف');");
//
//           out.println("location='internist.jsp';");
//
//             out.println ("</script>");
//                }
//                else{
//
//                                out.println ("<script type='text/javascript'>");
//
//             out.println ("alert(' هذا الفحص يوجد مسبقا');");
//
//           out.println("location='internist.jsp';");
//
//             out.println ("</script>");
//
//                }
//            }
//            else{
//                //insert
//             stmt = Con.createStatement();
//             stmt.executeUpdate("INSERT INTO `medical_exam` ( `NATIONAL_ID` , `NAME` , `TRAFFIC_UNIT` , `BLOOD` , `INTERNAL_INSPECTION` , `INSPECTION_STATUS`) " + "VALUES ('"+nationaId+"', '"+name+"', '"+trafficUnit+"', '"+blood+"', '"+result+"', 'W')");
//              out.println ("<script type='text/javascript'>");
//
//             out.println ("alert('تم تسجيل نتيجة الفحص');");
//
//           out.println("location='internist.jsp';");
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
            Logger.getLogger(internist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(internist.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(internist.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(internist.class.getName()).log(Level.SEVERE, null, ex);
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
