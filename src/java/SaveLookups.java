/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.Bloodgroup;
import Entity.InquiryUsers;

import Entity.Lictypes;
import Entity.Medicalconditions;
import Entity.PassportIssueCountry;
import Entity.Trafficunits;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/Lookups/SaveLookups"})
public class SaveLookups extends HttpServlet {

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
            throws ServletException, IOException, JSONException {
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

        EntityManager entityManager = factory.createEntityManager();
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {

            JsonObject rcvd;
            JsonObject obj1 = new JsonObject();

            InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF-8");
            JsonReader json = new JsonReader(reader);

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(json);
            rcvd = (JsonObject) root;

            String myJSON = rcvd.toString();
            // split this lookups and get each object of jsons
            JSONObject jsonObject = new JSONObject(myJSON);
            List<Lictypes> licList = new ArrayList<>();
            List<Trafficunits> trfficList = new ArrayList<>();
            List<Bloodgroup> bloodList = new ArrayList<>();
            List<Medicalconditions> medicalList = new ArrayList<>();
            List<PassportIssueCountry> passportList = new ArrayList<>();

            JSONArray jsonArray = new JSONArray();
            //simply put obj into jsonArray
            jsonArray.put(jsonObject);

            for (int i = 0; i < jsonArray.length(); i++) {

                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("licTypes").length(); j++) {

                    Lictypes lic = new Lictypes();
                    lic.setDescription(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("licTypes").getJSONObject(j).getString("description"));
                    lic.setLookUpID(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("licTypes").getJSONObject(j).getString("lookUp_ID"));
                    licList.add(lic);

                }
                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("trafficUnits").length(); j++) {

                    Trafficunits traffic = new Trafficunits();
                    traffic.setDescription(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("trafficUnits").getJSONObject(j).getString("description"));
                    traffic.setLookUpID(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("trafficUnits").getJSONObject(j).getString("lookUp_ID"));
                    entityManager.persist(traffic);

                    trfficList.add(traffic);

                }
                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("medicalConditions").length(); j++) {
                    Medicalconditions medical = new Medicalconditions();
                    medical.setDescription(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("medicalConditions").getJSONObject(j).getString("description"));
                    medical.setLookUpID(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("medicalConditions").getJSONObject(j).getString("lookUp_ID"));

                    medicalList.add(medical);
                }
                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("bloodGroup").length(); j++) {
                    Bloodgroup blood = new Bloodgroup();
                    blood.setDescription(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("bloodGroup").getJSONObject(j).getString("description"));
                    blood.setLookUpID(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("bloodGroup").getJSONObject(j).getString("lookUp_ID"));

                    bloodList.add(blood);
                }
                for (int j = 0; j < jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("passportIssueCountry").length(); j++) {
                    PassportIssueCountry passport = new PassportIssueCountry();
                    passport.setDescription(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("passportIssueCountry").getJSONObject(j).getString("description"));
                    passport.setLookUpID(jsonArray.getJSONObject(i).getJSONObject("body").getJSONArray("passportIssueCountry").getJSONObject(j).getString("lookUp_ID"));

                    passportList.add(passport);
                }
            }

            licList.stream().forEach(i
                    -> {
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();
            });
            medicalList.stream().forEach(i
                    -> {
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();
            });
            bloodList.stream().forEach(i
                    -> {
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();
            });
            trfficList.stream().forEach(i
                    -> {
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();
            });
            passportList.stream().forEach(i
                    -> {
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();
            });

            List<InquiryUsers> usersList = entityManager.createNamedQuery("InquiryUsers.findAll", InquiryUsers.class).getResultList();

            usersList.stream().forEach(i -> {

                Trafficunits traff = entityManager.createNamedQuery("Trafficunits.findByDescription", Trafficunits.class).setParameter("description", i.getTrafficUnit()).getSingleResult();
                i.setTrafficUnitCode(traff.getLookUpID());
                entityManager.getTransaction().begin();
                entityManager.persist(i);
                entityManager.getTransaction().commit();

            });
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
        } catch (JSONException ex) {
            Logger.getLogger(SaveLookups.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JSONException ex) {
            Logger.getLogger(SaveLookups.class.getName()).log(Level.SEVERE, null, ex);
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
