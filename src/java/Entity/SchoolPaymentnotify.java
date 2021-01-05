/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "schoolpaymentnotify", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SchoolPaymentnotify.findAll", query = "SELECT p FROM SchoolPaymentnotify p"),
    @NamedQuery(name = "SchoolPaymentnotify.findById", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.id = :id"),
    @NamedQuery(name = "SchoolPaymentnotify.findByRequestID", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.requestID = :requestID"),
    @NamedQuery(name = "SchoolPaymentnotify.findByNationalID", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.nationalID = :nationalID"),
    @NamedQuery(name = "SchoolPaymentnotify.findByPassportNo", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.passportNo = :passportNo"),
    @NamedQuery(name = "SchoolPaymentnotify.findByPassportIssueCountry", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.passportIssueCountry = :passportIssueCountry"),
    @NamedQuery(name = "SchoolPaymentnotify.findByTrafficUnit", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.trafficUnit = :trafficUnit"),
    @NamedQuery(name = "SchoolPaymentnotify.findByAmanUserID", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.amanUserID = :amanUserID"),
    @NamedQuery(name = "SchoolPaymentnotify.findByPaymentMethod", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.paymentMethod = :paymentMethod"),
    @NamedQuery(name = "SchoolPaymentnotify.findByServiceID", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.serviceID = :serviceID"),
    @NamedQuery(name = "SchoolPaymentnotify.findByTimeStamp", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.timeStamp = :timeStamp"),
    @NamedQuery(name = "SchoolPaymentnotify.findByApplicantName", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.applicantName = :applicantName"),
    @NamedQuery(name = "SchoolPaymentnotify.findByApplicantMobile", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.applicantMobile = :applicantMobile"),
    @NamedQuery(name = "SchoolPaymentnotify.findByTotalAmount", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.totalAmount = :totalAmount"),
    @NamedQuery(name = "SchoolPaymentnotify.findByPayedElements", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.payedElements = :payedElements"),
    @NamedQuery(name = "SchoolPaymentnotify.findByLicenseType", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.licenseType = :licenseType"),
    @NamedQuery(name = "SchoolPaymentnotify.findByDateOfBirth", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.dateOfBirth = :dateOfBirth"),
    @NamedQuery(name = "SchoolPaymentnotify.findByPaymentNumber", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.paymentNumber = :paymentNumber"),
    @NamedQuery(name = "SchoolPaymentnotify.findByQueueNumber", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.queueNumber = :queueNumber"),
    @NamedQuery(name = "SchoolPaymentnotify.findByDate", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.date = :date"),
    @NamedQuery(name = "SchoolPaymentnotify.findByQueueNumberUsed", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.queueNumberUsed = :queueNumberUsed"),
    @NamedQuery(name = "SchoolPaymentnotify.findByMedicaltotalamount", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.medicaltotalamount = :medicaltotalamount"),
    @NamedQuery(name = "SchoolPaymentnotify.findByType", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.type = :type"),
    @NamedQuery(name = "SchoolPaymentnotify.findByDateAndTraffiUnit", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.date = :date AND p.trafficUnit = :trafficUnit"),
    @NamedQuery(name = "SchoolPaymentnotify.findByStatusCode", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.statusCode = :statusCode"),
    @NamedQuery(name = "SchoolPaymentnotify.findByEusername", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.eusername = :eusername"),
    @NamedQuery(name = "SchoolPaymentnotify.findByEpassword", query = "SELECT p FROM SchoolPaymentnotify p WHERE p.epassword = :epassword")})
public class SchoolPaymentnotify implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "requestID")
    private String requestID;
    @Column(name = "NationalID")
    private String nationalID;
    @Column(name = "PassportNo")
    private String passportNo;
    @Column(name = "PassportIssueCountry")
    private String passportIssueCountry;
    @Column(name = "TrafficUnit")
    private String trafficUnit;
    @Column(name = "AmanUserID")
    private String amanUserID;
    @Column(name = "PaymentMethod")
    private String paymentMethod;
    @Column(name = "ServiceID")
    private String serviceID;
    @Column(name = "TimeStamp")
    private String timeStamp;
    @Column(name = "ApplicantName")
    private String applicantName;
    @Column(name = "ApplicantMobile")
    private String applicantMobile;
    @Column(name = "TotalAmount")
    private String totalAmount;
    @Column(name = "PayedElements")
    private String payedElements;
    @Column(name = "LicenseType")
    private String licenseType;
    @Column(name = "DateOfBirth")
    private String dateOfBirth;
    @Basic(optional = false)
    @Column(name = "paymentNumber")
    private String paymentNumber;
    @Basic(optional = false)
    @Column(name = "queueNumber")
    private String queueNumber;
    @Column(name = "Date")
    private String date;
    @Column(name = "queueNumberUsed")
    private String queueNumberUsed;
    @Column(name = "Medical_total_amount")
    private String medicaltotalamount;
    @Column(name = "Type")
    private String type;
    @Column(name = "StatusCode")
    private String statusCode;
    @Column(name = "Eusername")
    private String eusername;
    @Column(name = "Epassword")
    private String epassword;

    public SchoolPaymentnotify() {
    }

    public SchoolPaymentnotify(Integer id) {
        this.id = id;
    }

    public SchoolPaymentnotify(Integer id, String requestID, String paymentNumber, String queueNumber) {
        this.id = id;
        this.requestID = requestID;
        this.paymentNumber = paymentNumber;
        this.queueNumber = queueNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getNationalID() {
        return nationalID;
    }
    @JsonProperty("NationalID")
    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPassportNo() {
        return passportNo;
    }

    @JsonProperty("PassportNo")
    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportIssueCountry() {
        return passportIssueCountry;
    }
    @JsonProperty("PassportIssueCountry")
    public void setPassportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }
    @JsonProperty("TrafficUnit")
    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public String getAmanUserID() {
        return amanUserID;
    }
    @JsonProperty("AmanUserID")
    public void setAmanUserID(String amanUserID) {
        this.amanUserID = amanUserID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    @JsonProperty("PaymentMethod")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getServiceID() {
        return serviceID;
    }
    @JsonProperty("ServiceID")
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    @JsonProperty("TimeStamp")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getApplicantName() {
        return applicantName;
    }
    @JsonProperty("ApplicantName")
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantMobile() {
        return applicantMobile;
    }
    @JsonProperty("ApplicantMobile")
    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    public String getTotalAmount() {
        return totalAmount;
    }
    @JsonProperty("TotalAmount")
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayedElements() {
        return payedElements;
    }
    @JsonProperty("PayedElements")
    public void setPayedElements(String payedElements) {
        this.payedElements = payedElements;
    }

    public String getLicenseType() {
        return licenseType;
    }
    @JsonProperty("LicenseType")
    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    @JsonProperty("DateOfBirth")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public String getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(String queueNumber) {
        this.queueNumber = queueNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQueueNumberUsed() {
        return queueNumberUsed;
    }

    public void setQueueNumberUsed(String queueNumberUsed) {
        this.queueNumberUsed = queueNumberUsed;
    }

    public String getMedicaltotalamount() {
        return medicaltotalamount;
    }

    public void setMedicaltotalamount(String medicaltotalamount) {
        this.medicaltotalamount = medicaltotalamount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getEusername() {
        return eusername;
    }

    public void setEusername(String eusername) {
        this.eusername = eusername;
    }

    public String getEpassword() {
        return epassword;
    }

    public void setEpassword(String epassword) {
        this.epassword = epassword;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestID != null ? requestID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolPaymentnotify)) {
            return false;
        }
        SchoolPaymentnotify other = (SchoolPaymentnotify) object;
        if ((this.requestID == null && other.requestID != null) || (this.requestID != null && !this.requestID.equals(other.requestID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.SchoolPaymentnotify[ requestID=" + requestID + " ]";
    }



   

   

   


}