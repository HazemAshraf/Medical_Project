/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;

import com.google.gson.JsonObject;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.tomcat.util.codec.binary.Base64;
import java.awt.Image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author Hazem Ashraf
 */
@MultipartConfig(maxFileSize = 16177215)	// upload file's size up to 16MB
@WebServlet(urlPatterns = {"/biometric"})
public class biometric extends HttpServlet {

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
        String qry = "select * from `clients_data` where `MedicalCheckupID` = '" + nid + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            return true;
        }

        return false;
    }

    private String getEyeInspRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `MedicalCheckupID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("eyes_inspection_result");
        }
        //if(res == null) res = "";
        return res;
    }

    private String getInternInspRes(Connection Con, String tId) throws SQLException {
        String res = "";
        Statement stmt = Con.createStatement();
        String qry = "select * from `clients_data` where `MedicalCheckupID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("internal_inspection_result");
        }
        //if(res == null) res = "";
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
        String qry = "select * from `clients_data` where `MedicalCheckupID` = '" + tId + "'";
        ResultSet rs = stmt.executeQuery(qry);
        if (rs.next()) {
            res = rs.getString("inspection_status");
        }
        // if(res == null) res = "";
        return res;
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
        System.out.println("GET Response Code :: " + responseCode);
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
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

    }

    private File compressImage(String filePath, float compressionQuality) throws IOException {
   
      File input = new File(filePath);
      BufferedImage image = ImageIO.read(input);
      
      File compressedImageFile = File.createTempFile("tmp", ".jpg");;
      OutputStream os =new FileOutputStream(compressedImageFile);

      Iterator<ImageWriter>writers =  ImageIO.getImageWritersByFormatName("jpg");
      ImageWriter writer = (ImageWriter) writers.next();

      ImageOutputStream ios = ImageIO.createImageOutputStream(os);
      writer.setOutput(ios);

      ImageWriteParam param = writer.getDefaultWriteParam();
      
      param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      param.setCompressionQuality(compressionQuality);
      writer.write(null, new IIOImage(image, null, null), param);
      
      os.close();
      ios.close();
      writer.dispose();
      
      return compressedImageFile;
   }

    private static int sendPOST(String POST_URL, String POST_PARAMS, String TrackID) throws IOException {
        System.out.println("JSON is " + POST_PARAMS);
        FileWriter file = new FileWriter("E:\\Biometrics\\log_request.txt");
        file.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /API/oculist " + POST_PARAMS);
        file.close();

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
                System.out.println(response.toString());
                FileWriter file1 = new FileWriter("E:\\Biometrics\\log_response.txt");
                file1.write(new Timestamp(System.currentTimeMillis()).toString() + " POST /drvintegration_test/API/MedicalCheckup/NotifyResults " + response.toString());
                file1.close();
                if (response.toString().contains("200")) {
                    return 0;
                } else {
                    return 1;
                }

            }
        } else {
            return -1;
        }
    }

    
    
    public File resize(String inputImagePath, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        int currentHeight = inputImage.getHeight();
        int currentWidth = inputImage.getWidth();

        int scaledWidth ;

        scaledWidth = (scaledHeight * currentWidth) / currentHeight;

        System.out.println("currentHeight=" + currentHeight);
        System.out.println("currentWidth= " + currentWidth);
        System.out.println("scaledHeight=" + scaledHeight);
        System.out.println("scaledWidth=" + scaledWidth);


        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

       File newFile = File.createTempFile("tmp", ".jpg");
       
        // extracts extension of output file
//        String formatName = outputImagePath.substring(outputImagePath
//                .lastIndexOf(".") + 1);
//
//        File newFile = new File(outputImagePath);
        // writes to output file
        ImageIO.write(outputImage, "jpg", newFile);

        return newFile;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String nationalID = request.getParameter("nationalID");
//            boolean agnby = false;
//            if(!nationalID.matches("[0-9]+")){
//                System.out.println("agnbyyyyyyyyyyyyyyyyyyy");
//            agnby = true;
//            }
//            String FullName = request.getParameter("FullName");
//            ByteBuffer x = ByteBuffer.wrap(FullName.getBytes());
//
//            CharBuffer a = java.nio.charset.StandardCharsets.UTF_8.decode(x);
//            FullName = new String(FullName.getBytes(), StandardCharsets.UTF_8);
//            System.out.println("Full Name ---------- " + FullName + "aaaaaaaaa " + request.getParameter("FullName"));
//            System.out.println("biometric servlet : " + nationalID);
//            String passNo = request.getParameter("passportNo");
//            String theCountry = request.getParameter("passportIssueCountry");
//            

//            String issueCountryComposite = theCountry + passNo;
            String requestID = request.getParameter("requestID");
//            String ServiceType = request.getParameter("ServiceType");

            Part bioFile = request.getPart("bioFile");
            String photo64 = "";
            String bioPath = "";
            InputStream inputStream = null; // input stream of the upload file
            InputStream inputStream1 = null; // input stream of the upload file
            // obtains the upload file part in this multipart request
            Part filePart = request.getPart("bioFile");
            Part imgPart = request.getPart("imgFile");

            System.out.println("aaaaaaaaaaaa" + imgPart.getSize());
            if (imgPart != null) {
                // prints out some information for debugging
                System.out.println(imgPart.getName());
                System.out.println("imagesizeeeeeeeeeeeeee" + imgPart.getSize());

                System.out.println(imgPart.getContentType());
//                System.out.println(imgPart.getSubmittedFileName());

                // obtains input stream of the upload file
                inputStream = imgPart.getInputStream();
                System.out.println("innputstream of the image ; " + inputStream);
                byte[] imageBytes = new byte[(int) imgPart.getSize()];
                inputStream.read(imageBytes, 0, imageBytes.length);
                inputStream.close();
                //------------Compression----------------
                // File f = new File("C:\\Users\\User\\Desktop\\pp.jpg");
                File f = null;

                try {
                    // creates temporary file
                    f = File.createTempFile("tmp", ".jpg");
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(imageBytes);
                    // prints absolute path
                    System.out.println("File path: " + f.getAbsolutePath());

                    // creates temporary file
                } catch (Exception e) {
                    // if any error occurs
                    e.printStackTrace();
                }
                BufferedImage image = ImageIO.read(f);

     
//                File compressedImageFile = new File(f.getAbsolutePath());
              
                System.out.println("master file size : " + f.length());
               File compressedImageFile = resize(f.getAbsolutePath(),200);
               System.out.println("compressed file size : " + compressedImageFile.length());
//                
//                os.close();
//                ios.close();
//                writer.dispose();

                // get compressed as bas64
                FileInputStream fileInputStreamReader = new FileInputStream(compressedImageFile);
                byte[] bytes = new byte[(int) compressedImageFile.length()];
                
                fileInputStreamReader.read(bytes);
                photo64 = new String(Base64.encodeBase64(bytes), "UTF-8");
                fileInputStreamReader.close();
//                System.out.println("the compressed image base64 is : " + photo64);
//                 FileWriter file1 = new FileWriter("E:\\base_compressed.txt");
//                file1.write(photo64);
//                file1.close();

                //------------------ end of compression
//                photo64 = Base64.encodeBase64String(imageBytes);
//                System.out.println("photo 64 is " + photo64);
//                FileWriter file = new FileWriter("E:\\base.txt");
//                file.write(photo64);
//                file.close();
            }

            
           
//            
//            System.out.println(filePart);
//            if (filePart != null) {
//                // prints out some information for debugging
//                System.out.println(filePart.getName());
//                System.out.println(filePart.getSize());
//                System.out.println(filePart.getContentType());
////                System.out.println(filePart.getSubmittedFileName());
//                String savedPath = "";
//                // obtains input stream of the upload file
//                inputStream1 = filePart.getInputStream();
//                if (nationalID != null && !(nationalID.isEmpty())) {
//                    bioPath = "http://192.168.235.76:8080/biometric/" + nationalID + ".nist"; // to be accissble from outside 
//                    savedPath = "E:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\biometric\\" + nationalID + ".nist"; // saved locally
//
//                } else {
//                    bioPath = "http://192.168.235.76:8080/biometric/" + issueCountryComposite + ".nist";
//                    savedPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\webapps\\path\\to\\biometric\\" + issueCountryComposite + ".nist";
//                }
//                Path path = Paths.get(savedPath);
//                Files.deleteIfExists(path);
//                Files.copy(inputStream1, Paths.get(savedPath));
//                inputStream1.close();
//
//            }

            Connection Con = null;
            Statement stmt = null, stmt1 = null, stmt2 = null, stmt3 = null;

            getcon c = new getcon();
            Con = c.myconnection();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String bio_date = dtf.format(now);

            //    stmt = Con.createStatement();
            //    stmt2 = Con.createStatement();
            stmt3 = Con.createStatement();
            stmt = Con.createStatement();
            // check if photo already exists
//            System.out.println("national iddddddddddddddddd" + nationalID);

//              ResultSet rs1 = stmt2.executeQuery("select * from mi.clients_data where ( national_id = '" + nationalID + "' or nationality = '" + nationalID + "' ) and requestID = '"+requestID+"'");
//            if (rs1.first()) {
           int saved = stmt.executeUpdate("REPLACE INTO mi.clients_photos(requestID,national_id,photo) VALUES('" + requestID + "','" + nationalID + "' , '" + photo64 + "');");
           stmt.close();
           
            Random random = new Random();
            String medical_check_up_ID = Integer.toString(random.nextInt());
            //  System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+medical_check_up_ID);
            //  System.out.println("ana hena ya jhoneeeeeeeeeeeeeeeeeeeeee");
            // System.out.println(photo64);
            String sql = "update mi.clients_data set hasPhoto = 1 , MedicalCheckupID = '" + medical_check_up_ID + "' where requestID = '" + requestID + "';";
            int update = stmt3.executeUpdate(sql);
            if (update > 0) {
                out.println("<script type='text/javascript'>");

                out.println("alert('تم تعديل الصورة');");

                out.println("location='inquiry.jsp';");

                out.println("</script>");
//                stmt2.close();
                stmt3.close();
                Con.close();
                return;
            } else {
                out.println("<script type='text/javascript'>");

                out.println("alert('فشل في تعديل الصورة يرجى المحاولة مرة أخرى');");

                out.println("location='inquiry.jsp';");

                out.println("</script>");
                stmt3.close();
                Con.close();
                return;
            }

//        }
            // System.err.println(transID);
            // check if this client is already exists 
//            stmt1 = Con.createStatement();
//            ResultSet rs = stmt1.executeQuery("select * from mi.clients_data where national_id = '" + nationalID + "' or nationality = '" + theCountry + "' order by request_date desc");
//            if (rs.first()) {
//                Blob b = rs.getBlob("photo");
//                byte[] ba = b.getBytes(1, (int) b.length());
            // b.length() throws exception, no message, no cause
//              byte[] img64 = Base64.encode(ba);
//                photo64 = new String(ba);
// create medical check up ID
//            Random random = new Random();
//            String medical_check_up_ID = Integer.toString(random.nextInt());
//            System.out.println("Medical check up ID is : " + medical_check_up_ID);
//            System.out.println("imgge valueeeeeeeeeeee : " + photo64);
//            //check if agnby
//            String masryORagnby = "";
//            
//            if(agnby) masryORagnby = "nationality";
//            else masryORagnby = "national_id";
//            if (nationalID != null) {
//                System.out.println("egyptian king in biometric ");
//                System.out.println("national ID is " + nationalID);
//                // int updated = stmt.executeUpdate("update mi.clients_data set bioFile = '" + bioFile + "' , bioDate = '" + bio_date + "' ,  requestID = '" + requestID + "' , MedicalCheckupID = '" + medical_check_up_ID + "'  where national_id = '" + nationalID + "' ");
//                
//               
//                int updated = stmt.executeUpdate("insert into mi.clients_data (bioFile,bioDate,requestID,MedicalCheckupID,photo,"+masryORagnby+",Name) values ('" + bioFile + "' , '" + bio_date + "' , '" + requestID + "' , '" + medical_check_up_ID + "' , '" + photo64 + "' , '" + nationalID + "' , '" + FullName + "')");
//                      System.out.println("walaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + updated);                  
//                      
//                if (updated > 0) {
//                                      out.println("<script type='text/javascript'>");
//
//            out.println("alert('تم حفظ الصوره');");
//
//            out.println("location='inquiry.jsp';");
//
//            out.println("</script>");
//                     
//             return ;
//                }
//                else{
//        out.println("<script type='text/javascript'>");
//
//            out.println("alert('فشل في تعديل الصورة يرجى المحاولة مرة أخرى');");
//
//            out.println("location='inquiry.jsp';");
//
//            out.println("</script>");
//            return;
//                }
//            }
//            else if (passNo != null && theCountry != null) {
//                // sql = "update mi.clients_data set bioFile = "+bioFile+" where PassportNo = '"+passNo+"' and nationality = '"+theCountry+"' order by request_date desc";
//                int updated = stmt.executeUpdate("insert into mi.clients_data (bioFile,bioDate,requestID,MedicalCheckupID,photo,foreignComposite,Name) values ('" + bioFile + "' , '" + bio_date + "' , '" + requestID + "' , '" + medical_check_up_ID + "' , '" + photo64 + "' , '" + theCountry + passNo + "' , '" + FullName + "')");
//                if (!(updated > 0)) {
//                    out.println("can not update biometric");
//                }
//            }
//            stmt.close();
//            } else {
//                // insert new record with biometric
//
//                if ((passNo.isEmpty() || passNo == null) && (theCountry.isEmpty() || theCountry == null)) {
//                    int updated = stmt2.executeUpdate("insert into mi.clients_data (bioFile,national_id,bioDate,requestID) values ('" + bioFile + "' , '" + nationalID + "' , '" + bio_date + "' , '" + requestID + "')");
//                    if (!(updated > 0)) {
//                        out.println("can not insert biometric");
//                    }
//                } else if (nationalID.isEmpty() || nationalID == null) {
//                    // sql = "update mi.clients_data set bioFile = "+bioFile+" where PassportNo = '"+passNo+"' and nationality = '"+theCountry+"' order by request_date desc";
//                    int updated = stmt2.executeUpdate("insert into mi.clients_data (bioFile,PassportNo,nationality,,bioDate,foreignComposite,requestID) values ('" + bioFile + "' , '" + passNo + "' , '" + theCountry + "' , '" + bio_date + "' , '" + issueCountryComposite + "' , '" + requestID + "')");
//                    if (!(updated > 0)) {
//                        out.println("can not insert biometric");
//                    }
//                }
//
//            }
//            ResultSet rs = stmt1.executeQuery("select photo from mi.clients_data where national_id = '" + nationalID + "' or nationality = '" + nationalID + "' order by request_date desc");
//            if (rs.first()) {
//                Blob b = rs.getBlob("photo");
//                byte[] ba = b.getBytes(1, (int) b.length());
//                photo64 = new String(ba);
//                System.out.println("imgge valueeeeeeeeeeee : " + photo64);
//            }
//            stmt1.close();
            // check if this client has a biometric only not medical 
//            if (ServiceType.equals("2")) {
//                // send notification to 3s 
//                System.out.println("ServiceType.equals(\"2\"");
//                String json = "{\"header\": {\"version\": \"1.0\",\"category\": \"request\",\"service\": \" TIT_Medical_Results \",\"timestam\": \"03-09-2018 13:19\",\"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"},\"body\": {\"RequestID\": " + requestID + ",\"MedicalCheckupID\": \"\",\"MedicalCheckupDate\": \"\",\"MedicalCheckupResults\": 1,\"MedicalCheckupPhoto\": \"" + photo64 + "\",\"BloodGroup\": \"\",\"BioPath\": \"" + bioPath + "\",\"MedicalConditions\": []}}";
//
//                int res = sendPOST("http://192.168.235.50/drvintegration_test/API/MedicalCheckup/NotifyResults", json, "1");
//                if (res == 0) {
//                    System.out.println("0");
//
//                    out.println("<script type='text/javascript'>");
//
//                    out.println("alert('تم ارسال الصوره');");
//
//                    out.println("</script>");
//
//                } else if (res == 1) {
//                    out.println("there is error in inputs");
//                    System.out.println("1");
//                } else {
//                    out.println("otherwise error");
//                    System.out.println("unknown error");
//                }
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
