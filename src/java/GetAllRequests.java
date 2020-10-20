/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/MedicalCheckup/GetAllRequest"})
public class GetAllRequests extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String json = "{\n" +
" \"body\": {\n" +
" \"error_Message\": 0,\n" +
" \"requests\": [\n" +
" {\n" +
" \"requestID\": 699,\n" +
" \"requestFees\": 10.0,\n" +
"\"fName\" : \"عبد الفتاح\",\n" +
"\"mName\" : \"محمد\",\n" +
"\"lName\" : \"\",\n" +
"\"exName\" : \"\",\n" +
"\"nationalID\": \"789\",\n" +
"\"passportNo\": \"\",\n" +
" \"passportIssueCountry\": \"\",\n" +
" \"licenseType\": \"7686\",\n" +
" \"trafficUnit\": \"454\",\n" +
" \"birthDate\": \"1998-8-15\",\n" +
" \"queueNumber\": \"123\",\n" +
" \"serviceType\": \"3\"\n" +
" },\n" +
" {\n" +
" \"requestID\": 324234,\n" +
" \"requestFees\": 10.0,\n" +
"\"fName\" : \"حازم\",\n" +
"\"mName\" : \"أشرف\",\n" +
"\"lName\" : \"\",\n" +
"\"exName\" : \"\",\n" +
"\"nationalID\": \"456456\",\n" +
"\"passportNo\": \"\",\n" +
" \"passportIssueCountry\": \"\",\n" +
" \"licenseType\": \"7686\",\n" +
" \"trafficUnit\": \"454\",\n" +
" \"birthDate\": \"1998-8-15\",\n" +
" \"queueNumber\": \"124\",\n" +
" \"serviceType\": \"3\"\n" +
" }\n" +
" ]\n" +
" },\n" +
" \"header\": {\n" +
" \"version\": \"1.0\",\n" +
" \"category\": \"response\",\n" +
" \"service\": \"TIT_Medical_Inquiry\",\n" +
" \"timestamp\": \"19-12-2019 18:24\",\n" +
" \"tid\": \"594f2c57-e0d6-4311-87ffac491c4337dd\"\n" +
" }\n" +
"}";
 out.write(json);
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
        processRequest(request, response);
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
