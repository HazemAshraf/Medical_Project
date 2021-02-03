/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Entity.Paymentnotify;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.Math.ceil;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author AhmedElsayed
 */
@WebServlet(name = "testt", urlPatterns = {"/testt"})
public class testt extends HttpServlet {

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
            throws ServletException, IOException {
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

        EntityManager entityManager = factory.createEntityManager();
       // response.setContentType("text/html;charset=UTF-8");
       // try (PrintWriter out = response.getWriter()) {
            JsonObject rcvd;
            JsonObject obj1 = new JsonObject();

            try {
                InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
                JsonReader json = new JsonReader(reader);

                JsonParser parser = new JsonParser();
                JsonElement root = parser.parse(json);
                rcvd = (JsonObject) root;

                System.out.println("aaaaaaaaaaaaaaaaaaa : " + rcvd.toString());
                try {

                    ObjectMapper mapper = new ObjectMapper();
                    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

                    Paymentnotify obj = mapper.readValue(rcvd.toString(), Paymentnotify.class);
                    Random random = new Random();
                    
                    obj.setPaymentNumber(Integer.toString(random.nextInt()));
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

                    obj1.addProperty("requestID", obj.getRequestID());
                    obj1.addProperty("Confirmed", "true");
                    obj1.addProperty("TimeStamp", dateFormat.format(date));

                    obj1.addProperty("paymentNumber", obj.getPaymentNumber());

                    
                    entityManager.getTransaction().begin();
                    entityManager.persist(obj);
                    entityManager.getTransaction().commit();
                    
                     HashMap params = new HashMap();
                     params.put("plate", "Ziad Moustafa");
   
                
//                
                String fileName = "policy_reciept";
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                InputStream is = classloader.getResourceAsStream("testPDF.jasper");
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
                
                byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
                OutputStream outStream = response.getOutputStream();
                
              
                    response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
                    response.setHeader("Content-Disposition","attachment; filename=" + fileName + ".pdf");
                    response.setContentType("application/pdf");
                
                
                response.setContentLength(byteStream.length);
                // out.print("asdasdasdas");
                try {
                   // System.out.println("bytestream is " + byteStream.length +"     "  +  byteStream.toString());
                    outStream.write(byteStream);        //,0,byteStream.length);
               } catch(Exception e2){
                    response.setStatus(500);
                }  
                    
                   // out.write(obj1.toString());

                } catch (JsonGenerationException e) {
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                }
                catch (Exception ex) {
                    //sendReponse(response, 400, "", "Invalid format.");
                    return;
                }

            } catch (NoResultException nre) {
                response.setStatus(401);
                PrintWriter pw = response.getWriter();
                pw.write("Something went Wrong");
                obj1.addProperty("Confirmed", "true");

            }
        //}
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
        processRequest(request, response);
    }

    public EntityManager EntityConnection(EntityManager entityManager) {
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/demo?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("paymentAPIPU", properties);

        entityManager = factory.createEntityManager();
        return entityManager;
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
        processRequest(request, response);
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
