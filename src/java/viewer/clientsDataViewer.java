/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Viewer;

import Entity.ClientsData;
import Entity.Paymentnotify;
import Entity.Trafficunits;
import com.mysql.cj.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AhmedElsayed
 */
@ManagedBean
@SessionScoped

public class clientsDataViewer implements Serializable {

    private EntityManager entityManager = null;
    private Paymentnotify payment;
    private List<Trafficunits> trafficUnitsList;
    private String trafficUnit;
    private List<String> trafficUnitNames = new ArrayList<>();
    private Boolean flag = false;
    private Boolean next = false;
    private int nextClientQueue;
    private Boolean disabled = false;
    private Paymentnotify nextPayment;

    // private static final String PERSISTENCE_UNIT_NAME = "WebApplication1PU";
    public EntityManager EntityConnection(EntityManager entityManager) {
        Map properties = new HashMap();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/mi?zeroDateTimeBehavior=convertToNull&characterEncoding=UTF8&jdbcCompliantTruncation=false&serverTimezone=UTC");
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Medical_InspPU", properties);

        entityManager = factory.createEntityManager();
        return entityManager;
    }

    public void nextClient() {

//  if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT").toString() != null){   
//
//    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT");
//     System.out.println("nextttttttttttttttttttttt "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT").toString());}
    }

    public void setNextClient() {

        String a = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("TRAFFIC_UNIT_NAME").toString();

        this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);
        //    System.out.println("nextttttttttt " + nextClientQueue);
        this.setNext(true);
        List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
        if (list.size() < 1) {
            this.setDisabled(true);
        } else {
            this.setDisabled(false);
        }
    }

    public void checkDisabled() {
        String a = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("TRAFFIC_UNIT_NAME").toString();

        this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);
        //  System.out.println("nextttttttttt " + nextClientQueue);
        this.setNext(true);
        List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
        if (list.size() < 1) {
            this.setDisabled(true);
        } else {
            this.setDisabled(false);
        }

    }

    public void info() {
        //this.setFlag(false);
        entityManager = this.EntityConnection(entityManager);
        String a = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("TRAFFIC_UNIT_NAME").toString();

        //  System.out.println("traficcccccccccccccccccccc " + a);
        this.setTrafficUnit(a);
        //  System.out.println("heloooooooooooooooooooooo");
        this.setPayment(null);
        System.out.println("next paymentttttttt  " + this.getNextPayment());
        if (this.getNext() == true) {
            this.setNextPayment(null);
            List<Paymentnotify> l = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                    setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
            if (l.size() < 1) {
                this.setDisabled(true);
            } else {
                this.setPayment(entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                        setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getSingleResult());
                this.setNextPayment(this.getPayment());
            }
            this.setNext(false);

        } else {
            Object o = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT");
            String next = "";
            Map<String, Object> map = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//        map.entrySet().stream().forEach(i -> {
//            System.out.println("keyyyyyyyyyyy " + i.getKey());
//            System.out.println("valueeeeeeeeeee " + i.getValue());
//
//            if (i.getKey().equals("NEXT_CLIENT") && !(i.getValue().equals("new"))) {
//                if (!(i.getValue().equals("new"))) {
//                    String value = i.getValue().toString();
//                    System.out.println("eltalyyyyyyyyyyyyyyyyyyyy abl aw b3d sf7t el dor  " + value);
//                    i.setValue("new");
//                } else if (i.getKey().equals("NEXT_CLIENT") && (i.getValue().equals("new"))) {
//                    System.out.println("sf7t el dor b3d el talyyyyyyy");
//
//                }
//
//            } else {
//
//                System.out.println("sf7t el dor abl mados el taly");
//            }
//        });

            // System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb  "+b);
            //  StringUtils.isNullOrEmpty();
            //        if(!StringUtils.isNullOrEmpty(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT").toString())){
//             System.out.println("nextttttttttttttttttttttt "+FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("NEXT_CLIENT").toString());
//
//        }
            //System.out.println("asdasdas "+o.toString());
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
            ClientsData c = new ClientsData();
            List cc = new ArrayList();
            for (int i = 0; i < paymentList.size(); i++) {

                cc = entityManager.createNamedQuery("ClientsData.findByRequestIDAndTrafficUnit", ClientsData.class).setParameter("requestID", paymentList.get(i).getRequestID()).
                        setParameter("trafficUnit", this.getTrafficUnit())
                        .getResultList();
                if (this.getNextPayment() == null) {
                    if (cc.size() < 1) {
                        if (i < 1) {
                            this.setPayment(paymentList.get(i));
                            this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);

                            List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                                    setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
                            if (list.size() < 1) {
                                this.setDisabled(true);
                            } else {
                                this.setDisabled(false);
                            }

                            break;
                        } else {
                            this.setPayment(paymentList.get(i - 1));
                            this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);

                            List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                                    setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
                            if (list.size() < 1) {
                                this.setDisabled(true);
                            } else {
                                this.setDisabled(false);
                            }

                            break;

                        }
                    }
                } else if (this.getNextPayment() != null) {
                    List<ClientsData> c1 = entityManager.createNamedQuery("ClientsData.findByRequestIDAndTrafficUnit", ClientsData.class).setParameter("requestID", this.getNextPayment().getRequestID()).
                            setParameter("trafficUnit", this.getTrafficUnit())
                            .getResultList();
                    if (c1.size() > 0) {

                        this.setPayment(this.getNextPayment());
                        this.setNextPayment(null);
                        this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);

                        List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                                setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
                        if (list.size() < 1) {
                            this.setDisabled(true);
                        } else {
                            this.setDisabled(false);
                        }

                        break;
                    }

                }

            }
            //   System.out.println("+++++++++++++++++ "+this.getPayment().getNationalID());

            if (this.getPayment() == null) {
                if (this.getNextPayment() == null) {
                    System.out.println("hereeeeeeeeeeeee");
                    this.setPayment(paymentList.get(paymentList.size() - 1));
                } else {
                    System.out.println("net paymenttttt condition");
                    this.setPayment(this.getNextPayment());
                }
            }
            this.setNextClientQueue(Integer.parseInt(this.getPayment().getQueueNumber()) + 1);

            List<Paymentnotify> list = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class).
                    setParameter("queueNumber", String.valueOf(this.getNextClientQueue())).setParameter("trafficUnit", a).getResultList();
            if (list.size() < 1) {
                this.setDisabled(true);
            } else {
                this.setDisabled(false);
            }

            this.getTrafficUnitsList().stream().forEach(i -> {

                this.getTrafficUnitNames().add(i.getDescription());
            });
            this.setTrafficUnit(null);
            //this.setFlag(false);
        }

    }

    public void increment() {

        // System.out.println("aaaaaaaaaaaaaaaaa " + this.getPayment().getQueueNumber() + 1);
        this.getPayment().setQueueNumber("");
        int queue = Integer.parseInt(this.getPayment().getQueueNumber()) + 1;
        this.setTrafficUnit("وحدة مرور مدينة نصر");

        System.out.println("traffic    " + this.getPayment().getTrafficUnit());
        List<Paymentnotify> pList = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class)
                .setParameter("queueNumber", String.valueOf(queue))
                .setParameter("trafficUnit", this.getPayment().getTrafficUnit()).getResultList();
        if (pList.size() < 1) {
            this.setFlag(true);
        } else {
            this.setFlag(false);
            Paymentnotify p = entityManager.createNamedQuery("Paymentnotify.findByQueueAndTraffiUnit", Paymentnotify.class)
                    .setParameter("queueNumber", String.valueOf(queue))
                    .setParameter("trafficUnit", this.getPayment().getTrafficUnit()).getSingleResult();
            this.setPayment(p);
            System.out.println("hbsdhsbfhsf " + p.getQueueNumber());

        }

    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public int getNextClientQueue() {
        return nextClientQueue;
    }

    public void setNextClientQueue(int nextClientQueue) {
        this.nextClientQueue = nextClientQueue;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Paymentnotify getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(Paymentnotify nextPayment) {
        this.nextPayment = nextPayment;
    }


}
