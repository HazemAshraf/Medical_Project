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
 * @author AhmedElsayed
 */
@Entity
@Table(name = "vehicleinspection", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicleinspection.findAll", query = "SELECT v FROM Vehicleinspection v")
    , @NamedQuery(name = "Vehicleinspection.findById", query = "SELECT v FROM Vehicleinspection v WHERE v.id = :id")
    , @NamedQuery(name = "Vehicleinspection.findByRequestID", query = "SELECT v FROM Vehicleinspection v WHERE v.requestID = :requestID")
    , @NamedQuery(name = "Vehicleinspection.findByNationalID", query = "SELECT v FROM Vehicleinspection v WHERE v.nationalID = :nationalID")
    , @NamedQuery(name = "Vehicleinspection.findByPassportNo", query = "SELECT v FROM Vehicleinspection v WHERE v.passportNo = :passportNo")
    , @NamedQuery(name = "Vehicleinspection.findByPassportIssueCountry", query = "SELECT v FROM Vehicleinspection v WHERE v.passportIssueCountry = :passportIssueCountry")
    , @NamedQuery(name = "Vehicleinspection.findByTrafficUnit", query = "SELECT v FROM Vehicleinspection v WHERE v.trafficUnit = :trafficUnit")
    , @NamedQuery(name = "Vehicleinspection.findByAmanUserID", query = "SELECT v FROM Vehicleinspection v WHERE v.amanUserID = :amanUserID")
    , @NamedQuery(name = "Vehicleinspection.findByPaymentMethod", query = "SELECT v FROM Vehicleinspection v WHERE v.paymentMethod = :paymentMethod")
    , @NamedQuery(name = "Vehicleinspection.findByServiceID", query = "SELECT v FROM Vehicleinspection v WHERE v.serviceID = :serviceID")
    , @NamedQuery(name = "Vehicleinspection.findByTimeStamp", query = "SELECT v FROM Vehicleinspection v WHERE v.timeStamp = :timeStamp")
    , @NamedQuery(name = "Vehicleinspection.findByApplicantName", query = "SELECT v FROM Vehicleinspection v WHERE v.applicantName = :applicantName")
    , @NamedQuery(name = "Vehicleinspection.findByApplicantMobile", query = "SELECT v FROM Vehicleinspection v WHERE v.applicantMobile = :applicantMobile")
    , @NamedQuery(name = "Vehicleinspection.findByTotalAmount", query = "SELECT v FROM Vehicleinspection v WHERE v.totalAmount = :totalAmount")
    , @NamedQuery(name = "Vehicleinspection.findByPayedElements", query = "SELECT v FROM Vehicleinspection v WHERE v.payedElements = :payedElements")
    , @NamedQuery(name = "Vehicleinspection.findByLicenseType", query = "SELECT v FROM Vehicleinspection v WHERE v.licenseType = :licenseType")
    , @NamedQuery(name = "Vehicleinspection.findByDateOfBirth", query = "SELECT v FROM Vehicleinspection v WHERE v.dateOfBirth = :dateOfBirth")
    , @NamedQuery(name = "Vehicleinspection.findByPaymentNumber", query = "SELECT v FROM Vehicleinspection v WHERE v.paymentNumber = :paymentNumber")
    , @NamedQuery(name = "Vehicleinspection.findByQueueNumber", query = "SELECT v FROM Vehicleinspection v WHERE v.queueNumber = :queueNumber")
    , @NamedQuery(name = "Vehicleinspection.findByDate", query = "SELECT v FROM Vehicleinspection v WHERE v.date = :date")
    , @NamedQuery(name = "Vehicleinspection.findByQueueNumberUsed", query = "SELECT v FROM Vehicleinspection v WHERE v.queueNumberUsed = :queueNumberUsed")
    , @NamedQuery(name = "Vehicleinspection.findByCancel", query = "SELECT v FROM Vehicleinspection v WHERE v.cancel = :cancel")
    , @NamedQuery(name = "Vehicleinspection.findByUsedcancel", query = "SELECT v FROM Vehicleinspection v WHERE v.usedcancel = :usedcancel")
    , @NamedQuery(name = "Vehicleinspection.findByStatusCode", query = "SELECT v FROM Vehicleinspection v WHERE v.statusCode = :statusCode")
    , @NamedQuery(name = "Vehicleinspection.findByChassisNumber", query = "SELECT v FROM Vehicleinspection v WHERE v.chassisNumber = :chassisNumber")
    ,@NamedQuery(name = "Vehicleinspection.findByDateAndTraffiUnit", query = "SELECT v FROM Vehicleinspection v WHERE v.date = :date AND v.trafficUnit = :trafficUnit")
    ,@NamedQuery(name = "Vehicleinspection.findByDateAndRequestID", query = "SELECT v FROM Vehicleinspection v WHERE v.date = :date AND v.requestID = :requestID")
    , @NamedQuery(name = "Vehicleinspection.findByDateAndStatusCode", query = "SELECT v FROM Vehicleinspection v WHERE v.date = :date AND v.statusCode = :statusCode")
    
        , @NamedQuery(name = "Vehicleinspection.findByVehicleinspectionamount", query = "SELECT v FROM Vehicleinspection v WHERE v.vehicleinspectionamount = :vehicleinspectionamount")})
public class Vehicleinspection implements Serializable {

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
    @Column(name = "Vehicle_inspection_amount")
    private String vehicleinspectionamount;
    @Column(name = "Type")
    private String type;
    @Column(name = "cancel")
    private Integer cancel;
    @Column(name = "usedcancel")
    private Integer usedcancel;
    @Column(name = "StatusCode")
    private String statusCode;
    @Column(name = "ChassisNumber")
    private String chassisNumber;

    public Vehicleinspection() {
    }

    public Vehicleinspection(Integer id) {
        this.id = id;
    }

    public Vehicleinspection(Integer id, String requestID, String paymentNumber, String queueNumber) {
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

    public String getVehicleinspectionamount() {
        return vehicleinspectionamount;
    }

    @JsonProperty("Vehicle_inspection_amount")
    public void setVehicleinspectionamount(String vehicleinspectionamount) {
        this.vehicleinspectionamount = vehicleinspectionamount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicleinspection)) {
            return false;
        }
        Vehicleinspection other = (Vehicleinspection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Vehicleinspection[ id=" + id + " ]";
    }

    public String getType() {
        return type;
    }

    @JsonProperty("Type")

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Integer getUsedcancel() {
        return usedcancel;
    }

    public void setUsedcancel(Integer usedcancel) {
        this.usedcancel = usedcancel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

}
