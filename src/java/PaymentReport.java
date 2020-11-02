/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Paymentnotify;
import Entity.Vehicleinspection;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author AhmedElsayed
 */
@WebServlet(urlPatterns = {"/PaymentReport"})
public class PaymentReport extends HttpServlet {

    private static String[] columns = {"id", "RequestID", "passportNo", "passportIssueCountry", "NationalID", "TrafficUnit", "AmanUserID",
        "PaymentMethod", "serviceID", "TimeStamp", "ApplicantName", "ApplicantMobile", "TotalAmount", "PayedElements", "LicenseType", "DateOfBirth",
        "paymentNumber", "QueueNumber", "Date", "QueueNumberUsed", "Medical_Total_Amount", "type", "statusCode"};
    private static List<Paymentnotify> vehicleList = new ArrayList<>();

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
            throws ServletException, IOException, JRException, ParseException, IOException, InvalidFormatException {
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
        System.out.println("dateeeeeeeeeeeeeeeeee  : " + request.getParameter("date"));
        // list of requestID in the same dayy
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        CreationHelper createHelper = workbook.getCreationHelper();

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String strDate = dateFormat.format(date);
        String output = strDate.substring(0, 10);
        System.out.println("date " + output);
        List<Paymentnotify> listofPayment = entityManager.createNamedQuery("Paymentnotify.findByDateAndStatusCode", Paymentnotify.class)
                .setParameter("date", request.getParameter("date")).setParameter("statusCode", "200 OK")
                .getResultList();
        System.out.println("aaaaaaaaaaaaaaaaaaa " + listofPayment);
        Sheet sheet = workbook.createSheet("Vehicle Insepection for " + strDate);
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create Other rows and cells with Vehicle data
        int rowNum = 1;
        for (Paymentnotify payment : listofPayment) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(payment.getId());
            row.createCell(1).setCellValue(payment.getRequestID());
            row.createCell(2).setCellValue(payment.getPassportNo());
            row.createCell(3).setCellValue(payment.getPassportIssueCountry());
            row.createCell(4).setCellValue(payment.getNationalID());
            row.createCell(5).setCellValue(payment.getTrafficUnit());
            row.createCell(6).setCellValue(payment.getAmanUserID());
            row.createCell(7).setCellValue(payment.getPaymentMethod());
            row.createCell(8).setCellValue(payment.getServiceID());
            row.createCell(9).setCellValue(payment.getTimeStamp());
            row.createCell(10).setCellValue(payment.getApplicantName());
            row.createCell(11).setCellValue(payment.getApplicantMobile());
            row.createCell(12).setCellValue(payment.getTotalAmount());
            row.createCell(13).setCellValue(payment.getPayedElements());
            row.createCell(14).setCellValue(payment.getLicenseType());
            row.createCell(15).setCellValue(payment.getDateOfBirth());
            row.createCell(16).setCellValue(payment.getPaymentNumber());
            row.createCell(17).setCellValue(payment.getQueueNumber());
            Cell dateCell = row.createCell(18);
            dateCell.setCellValue(payment.getDate());
            dateCell.setCellStyle(dateCellStyle);
            row.createCell(19).setCellValue(payment.getQueueNumberUsed());

        //    row.createCell(20).setCellValue(payment.getMedicalTotalAmount());
         row.createCell(20).setCellValue("");
            row.createCell(21).setCellValue(payment.getType());

            row.createCell(22).setCellValue(payment.getStatusCode());

        }
        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream("E:\\MedicalReport_" + request.getParameter("date") + ".xlsx");
        workbook.write(fileOut);
        //ByteArrayOutputStream
        File file = new File("E:\\MedicalReport_" + request.getParameter("date") + ".xlsx");

        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            //       Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();

        byte[] byteStream = bytes;

        fileOut.close();
        // Closing the workbook
        workbook.close();

        OutputStream outStream = response.getOutputStream();

        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" + "MedicalReport_" + request.getParameter("date") + ".xlsx");
        response.setContentType("application/pdf");

        response.setContentLength(byteStream.length);

        outStream.write(byteStream);

        //  String reportPathResp = "E:\\MedicalReport_" + request.getParameter("date") + ".xlsx";
//        JsonObject obj1 = new JsonObject();
//
//        PrintWriter out = response.getWriter();
//        obj1.addProperty("reportURL", reportPathResp);
//        out.write(obj1.toString());
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
            Logger.getLogger(VehicleReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VehicleReport.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VehicleReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VehicleReport.class.getName()).log(Level.SEVERE, null, ex);
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
