/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.google.common.base.Strings;
import com.aman.medical.db.getcon;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

/**
 *
 * @author win7
 */
@WebServlet(urlPatterns = {"/login"})
public class login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public String getTrafficUnitName(Connection con, String code) throws SQLException {
        String tuName = "";
        Statement stmt = con.createStatement();
        String sql = "select description from mi.trafficunits where lookUp_ID = '" + code + "';";
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.first()) {
            tuName = rs.getString("description");
        }
        return tuName;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String applicationType = "";
                    InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            //inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
             inputStream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\conf\\config.properties");
         //   inputStream = new FileInputStream("C:\\Users\\User\\Desktop\\apache-tomcat-8.5.5\\conf\\config.properties");
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            applicationType = prop.getProperty("applicationType");

//            System.out.println("ip running : " + IP + " and the api context : " + API_CTX);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
//            inputStream.close();
        }
            
          
            	if (applicationType.equals("MEDICALPAYMENT") || applicationType.equals("INSPECTIONPAYMENT")){
		out.println("<script type='text/javascript'>");
		out.println("alert(' You are trying to login to payment application !');");
		out.println("location='index.jsp';");
		out.println("</script>");
                return;
	}
            
            
            
            
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(getcon.class.getName()).log(Level.SEVERE, null, ex);
            }
            getcon c = new getcon();
            System.out.println("hereeeee");
            Connection Con = c.myconnection();
            Statement stmt = null;
            try {

                boolean flag = false;
                String email = request.getParameter("mail");
                String password = request.getParameter("pass");
                String theUnit = request.getParameter("theUnit");

//                if ("وحدة مرور مدينة نصر".equals(theUnit)) {
//                    System.out.println("walaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                }
                String type = "";
                System.out.println("user name : " + email);
                System.out.println("type : " + type);
                String sql = "";
//                if (type.equals("inquiry")) {
                sql = "select * from mi.inquiry_users where USERNAME = '" + email + "' and PASSWORD = '" + password + "'";
//                } else if (type.equals("internist")) {
                //  sql = "select * from mi.internist_users where USERNAME = '" + email + "' and PASSWORD = '" + password + "'";
//                } //                        else if(type.equals("biometric")){
                //                       sql = "select * from mi.biometric_users where USERNAME = '" + email + "' and PASSWORD = '"+password+"'";
                //                       }
//                else {
                //  sql = "select * from mi.oculist_users where USERNAME = '" + email + "' and PASSWORD = '" + password + "'";
//                }
                stmt = Con.createStatement();
                //SELECT `TRAFFIC_UNIT` , `NATIONAL_ID` , `USERNAME` ,  CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR)  FROM `users` WHERE CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR) = 'admin'  and `USERNAME` = 'hazem';
                ResultSet RS = stmt.executeQuery(sql);

//ResultSet RS =stmt.executeQuery("SELECT * FROM `mi`.`users` WHERE CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR) = '"+password+"'  and `USERNAME` = '"+email+"';");
                if (RS.first()) {
                    type = "inquiry";
//                                 //response.sendRedirect("courses.jsp");
                    HttpSession session = request.getSession(true);
//                                // session.setMaxInactiveInterval(10); ems7ly el session b3d 10 sawany
                    if (session.isNew() == false) { //el session d adema ?
                        session.invalidate();
                        session = request.getSession(true);

                    }
                    String nationalId = RS.getString("NATIONAL_ID");

                    String userName = RS.getString("USERNAME");
                    String name = RS.getString("NAME");
//                    String trafficUnit = RS.getString("TRAFFIC_UNIT");
//                    String trafficUnitCode = RS.getString("TRAFFIC_UNIT_CODE");

                    session.setAttribute("USERNAME", userName);
                    session.setAttribute("NAME", name);
                    session.setAttribute("TRAFFIC_UNIT", theUnit);
                    session.setAttribute("TRAFFIC_UNIT_CODE", theUnit);
                    session.setAttribute("TRAFFIC_UNIT_NAME", theUnit);
                    session.setAttribute("NATIONAL_ID", nationalId);
                    session.setAttribute("TYPE", type);
                    session.setAttribute("SESSION_ID", session.getId());
//                        if(type.equalsIgnoreCase("oculist")){
//                               RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("oculist.jsp");
//                          requestDispatcher.forward(request, response);
//                        }else{
//                            RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("internist.jsp");
//                          requestDispatcher.forward(request, response);
//                        
//                        }
//                                                       RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("test.jsp");
//                          requestDispatcher.forward(request, response);
//                               RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/test.jsp");
//                               dispatcher.forward(request, response);
                    System.out.println("type name " + type);
                    RequestDispatcher requestDispatcher;
                    requestDispatcher = request.getRequestDispatcher("inquiry.jsp");
                    requestDispatcher.forward(request, response);

                } else {
                    sql = "select * from mi.internist_users where USERNAME = '" + email + "' and PASSWORD = '" + password + "'";
                    RS = stmt.executeQuery(sql);
                    if (RS.first()) {
                        type = "internist";
//                                 //response.sendRedirect("courses.jsp");
                        HttpSession session = request.getSession(true);
//                                // session.setMaxInactiveInterval(10); ems7ly el session b3d 10 sawany
                        if (session.isNew() == false) { //el session d adema ?
                            session.invalidate();
                            session = request.getSession(true);

                        }
                        String nationalId = RS.getString("NATIONAL_ID");

                        String userName = RS.getString("USERNAME");
                        String name = RS.getString("NAME");
                        String trafficUnit = RS.getString("TRAFFIC_UNIT");
                        String trafficUnitCode = RS.getString("TRAFFIC_UNIT_CODE");

                        session.setAttribute("USERNAME", userName);
                        session.setAttribute("NAME", name);
                        session.setAttribute("TRAFFIC_UNIT", theUnit);
                        session.setAttribute("TRAFFIC_UNIT_CODE", theUnit);
                        session.setAttribute("TRAFFIC_UNIT_NAME", theUnit);
                        session.setAttribute("NATIONAL_ID", nationalId);
                        session.setAttribute("TYPE", type);
                        session.setAttribute("SESSION_ID", session.getId());
//                        if(type.equalsIgnoreCase("oculist")){
//                               RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("oculist.jsp");
//                          requestDispatcher.forward(request, response);
//                        }else{
//                            RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("internist.jsp");
//                          requestDispatcher.forward(request, response);
//                        
//                        }
//                                                       RequestDispatcher requestDispatcher;
//                         requestDispatcher = request.getRequestDispatcher("test.jsp");
//                          requestDispatcher.forward(request, response);
//                               RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/test.jsp");
//                               dispatcher.forward(request, response);
                        System.out.println("type name " + type);
                        RequestDispatcher requestDispatcher;
                        requestDispatcher = request.getRequestDispatcher("internist.jsp");
                        requestDispatcher.forward(request, response);

                    } else {
                        sql = "select * from mi.oculist_users where USERNAME = '" + email + "' and PASSWORD = '" + password + "'";
                        RS = stmt.executeQuery(sql);

//ResultSet RS =stmt.executeQuery("SELECT * FROM `mi`.`users` WHERE CAST(AES_DECRYPT(`PASSWORD`, 'secret') AS CHAR) = '"+password+"'  and `USERNAME` = '"+email+"';");
                        if (RS.first()) {
                            type = "oculist";
//                                 //response.sendRedirect("courses.jsp");
                            HttpSession session = request.getSession(true);
//                                // session.setMaxInactiveInterval(10); ems7ly el session b3d 10 sawany
                            if (session.isNew() == false) { //el session d adema ?
                                session.invalidate();
                                session = request.getSession(true);

                            }
                            String nationalId = RS.getString("NATIONAL_ID");

                            String userName = RS.getString("USERNAME");
                            String name = RS.getString("NAME");
                            String trafficUnit = RS.getString("TRAFFIC_UNIT");
                            String trafficUnitCode = RS.getString("TRAFFIC_UNIT_CODE");

                            session.setAttribute("USERNAME", userName);
                            session.setAttribute("NAME", name);
                            session.setAttribute("TRAFFIC_UNIT", theUnit);
                            session.setAttribute("TRAFFIC_UNIT_CODE", theUnit);
                            session.setAttribute("TRAFFIC_UNIT_NAME", theUnit);
                            session.setAttribute("NATIONAL_ID", nationalId);
                            session.setAttribute("TYPE", type);
                            session.setAttribute("SESSION_ID", session.getId());

                            System.out.println("type name " + type);
                            RequestDispatcher requestDispatcher;
                            requestDispatcher = request.getRequestDispatcher("oculist.jsp");
                            requestDispatcher.forward(request, response);

                        } else {
                            out.println("<script type='text/javascript'>");

                            out.println("alert(' Wrong Email or Password !');");

                            out.println("location='index.jsp';");

                            out.println("</script>");

                        }

                    }

                    //response.sendRedirect("index.html"); 
                }

            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                stmt.close();
                Con.close();
            }

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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
