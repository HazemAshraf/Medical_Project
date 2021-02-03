/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Entity.ClientsData;
import Entity.Paymentnotify;
import Entity.Trafficunits;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/NextClient"})
public class NextClient extends HttpServlet {
    private Paymentnotify payment;
    private List<Trafficunits> trafficUnitsList;
    private String trafficUnit;
    private List<String> trafficUnitNames = new ArrayList<>();
    private Boolean flag = false;

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
   
             Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

            EntityManager entityManager = factory.createEntityManager();
            String a=request.getSession().getAttribute("TRAFFIC_UNIT_NAME").toString();
             this.setTrafficUnit(a);
        this.setPayment(null);
        if (this.getFlag()) {
            int queue = Integer.parseInt(this.getPayment().getQueueNumber()) + 1;
            System.out.println("traffic    " + this.getPayment().getTrafficUnit());
            List<Paymentnotify> pList = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class)
                    .setParameter("queueNumber", String.valueOf(queue))
                    .setParameter("trafficUnit", this.getPayment().getTrafficUnit()).getResultList();
            if (pList.size() < 1) {
                this.setFlag(true);
            } else {
                this.setFlag(false);

            }
        }
        List<Paymentnotify> paymentList = new ArrayList<>();
        if (this.getTrafficUnit() == null) {
            paymentList = entityManager.createNamedQuery("Paymentnotify.findAll", Paymentnotify.class).getResultList();
        } else {

            paymentList = entityManager.createNamedQuery("Paymentnotify.findByTrafficUnit", Paymentnotify.class).setParameter("trafficUnit", this.getTrafficUnit()).getResultList();
            // this.setFlag(!this.getFlag());
        }
        List<ClientsData> ClientsList = entityManager.createNamedQuery("ClientsData.findAll", ClientsData.class).getResultList();
        this.setTrafficUnitsList(entityManager.createNamedQuery("Trafficunits.findAll", Trafficunits.class).getResultList());
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa " + this.getTrafficUnit());
        ClientsData c = new ClientsData();
        List cc = new ArrayList();
        for (int i = 0; i < paymentList.size(); i++) {

            cc = entityManager.createNamedQuery("ClientsData.findByRequestIDAndTrafficUnit", ClientsData.class).setParameter("requestID", paymentList.get(i).getRequestID()).
                    setParameter("trafficUnit", this.getTrafficUnit())
                    .getResultList();
            System.out.println("sizeeeeeeeeee " + cc.size());
            if (cc.size() < 1) {
                System.out.println("listssssssssssssssss " + paymentList.get(i));
                if (i < 1) {
                    this.setPayment(paymentList.get(i));
                    break;
                } else {
                    this.setPayment(paymentList.get(i - 1));
                    break;

                }
            }

        }
        //   System.out.println("+++++++++++++++++ "+this.getPayment().getNationalID());

        System.out.println("+++++++++++++++++ " + this.getPayment());
        if (this.getPayment() == null) {
            this.setPayment(paymentList.get(paymentList.size()-1));
        }
        
       int nextClientQueue = Integer.parseInt(this.getPayment().getQueueNumber())+1;
       String trafficUnit = a;
       String date = this.getPayment().getDate();
       request.getSession().setAttribute("NEXT_CLIENT", nextClientQueue);
       request.getSession().setAttribute("CURR_DATE", date);
        
        this.getTrafficUnitsList().stream().forEach(i -> {

            this.getTrafficUnitNames().add(i.getDescription());
        });
        this.setTrafficUnit(null);
       
       
            
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

    public Paymentnotify getPayment() {
        return payment;
    }

    public void setPayment(Paymentnotify payment) {
        this.payment = payment;
    }

    public List<Trafficunits> getTrafficUnitsList() {
        return trafficUnitsList;
    }

    public void setTrafficUnitsList(List<Trafficunits> trafficUnitsList) {
        this.trafficUnitsList = trafficUnitsList;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }

    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public List<String> getTrafficUnitNames() {
        return trafficUnitNames;
    }

    public void setTrafficUnitNames(List<String> trafficUnitNames) {
        this.trafficUnitNames = trafficUnitNames;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
