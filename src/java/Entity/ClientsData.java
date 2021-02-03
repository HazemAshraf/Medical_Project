/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AhmedElsayed
 */
@Entity
@Table(name = "clients_data", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientsData.findAll", query = "SELECT c FROM ClientsData c")
    , @NamedQuery(name = "ClientsData.findById", query = "SELECT c FROM ClientsData c WHERE c.id = :id")
    , @NamedQuery(name = "ClientsData.findByRequestID", query = "SELECT c FROM ClientsData c WHERE c.requestID = :requestID")
    , @NamedQuery(name = "ClientsData.findByNationalID", query = "SELECT c FROM ClientsData c WHERE c.nationalID = :nationalID")
    , @NamedQuery(name = "ClientsData.findByName", query = "SELECT c FROM ClientsData c WHERE c.name = :name")
    , @NamedQuery(name = "ClientsData.findByMName", query = "SELECT c FROM ClientsData c WHERE c.mName = :mName")
    , @NamedQuery(name = "ClientsData.findByLName", query = "SELECT c FROM ClientsData c WHERE c.lName = :lName")
    , @NamedQuery(name = "ClientsData.findByExName", query = "SELECT c FROM ClientsData c WHERE c.exName = :exName")
    , @NamedQuery(name = "ClientsData.findByLicenseType", query = "SELECT c FROM ClientsData c WHERE c.licenseType = :licenseType")
    , @NamedQuery(name = "ClientsData.findByTrafficUnit", query = "SELECT c FROM ClientsData c WHERE c.trafficUnit = :trafficUnit")
    , @NamedQuery(name = "ClientsData.findByBirthDate", query = "SELECT c FROM ClientsData c WHERE c.birthDate = :birthDate")
    , @NamedQuery(name = "ClientsData.findByRequestFees", query = "SELECT c FROM ClientsData c WHERE c.requestFees = :requestFees")
    , @NamedQuery(name = "ClientsData.findByPassportNo", query = "SELECT c FROM ClientsData c WHERE c.passportNo = :passportNo")
    , @NamedQuery(name = "ClientsData.findByPassportIssueCountry", query = "SELECT c FROM ClientsData c WHERE c.passportIssueCountry = :passportIssueCountry")
    , @NamedQuery(name = "ClientsData.findByEyesInspectionResult", query = "SELECT c FROM ClientsData c WHERE c.eyesInspectionResult = :eyesInspectionResult")
    , @NamedQuery(name = "ClientsData.findByInternalInspectionResult", query = "SELECT c FROM ClientsData c WHERE c.internalInspectionResult = :internalInspectionResult")
    , @NamedQuery(name = "ClientsData.findByInspectionStatus", query = "SELECT c FROM ClientsData c WHERE c.inspectionStatus = :inspectionStatus")
    , @NamedQuery(name = "ClientsData.findByNationalId", query = "SELECT c FROM ClientsData c WHERE c.nationalId = :nationalId")
    , @NamedQuery(name = "ClientsData.findByMedicalCheckupID", query = "SELECT c FROM ClientsData c WHERE c.medicalCheckupID = :medicalCheckupID")
    , @NamedQuery(name = "ClientsData.findByEyesExamDate", query = "SELECT c FROM ClientsData c WHERE c.eyesExamDate = :eyesExamDate")
    , @NamedQuery(name = "ClientsData.findByInternalInspectionDate", query = "SELECT c FROM ClientsData c WHERE c.internalInspectionDate = :internalInspectionDate")
    , @NamedQuery(name = "ClientsData.findByRequestDate", query = "SELECT c FROM ClientsData c WHERE c.requestDate = :requestDate")
    , @NamedQuery(name = "ClientsData.findByNationality", query = "SELECT c FROM ClientsData c WHERE c.nationality = :nationality")
    , @NamedQuery(name = "ClientsData.findByRightEyeDegree", query = "SELECT c FROM ClientsData c WHERE c.rightEyeDegree = :rightEyeDegree")
    , @NamedQuery(name = "ClientsData.findByLeftEyeDegree", query = "SELECT c FROM ClientsData c WHERE c.leftEyeDegree = :leftEyeDegree")
    , @NamedQuery(name = "ClientsData.findByForeignComposite", query = "SELECT c FROM ClientsData c WHERE c.foreignComposite = :foreignComposite")
    , @NamedQuery(name = "ClientsData.findByQueue", query = "SELECT c FROM ClientsData c WHERE c.queue = :queue")
    , @NamedQuery(name = "ClientsData.findByBioDate", query = "SELECT c FROM ClientsData c WHERE c.bioDate = :bioDate")
    , @NamedQuery(name = "ClientsData.findByServiceType", query = "SELECT c FROM ClientsData c WHERE c.serviceType = :serviceType")
    , @NamedQuery(name = "ClientsData.findByRequestIDAndTrafficUnit", query = "SELECT c FROM ClientsData c WHERE c.requestID = :requestID AND c.trafficUnit = :trafficUnit")
})
public class ClientsData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "requestID")
    private String requestID;
    @Column(name = "NationalID")
    private String nationalID;
    @Column(name = "Name")
    private String name;
    @Column(name = "MName")
    private String mName;
    @Column(name = "LName")
    private String lName;
    @Column(name = "ExName")
    private String exName;
    @Column(name = "LicenseType")
    private String licenseType;
    @Column(name = "TrafficUnit")
    private String trafficUnit;
    @Column(name = "BirthDate")
    private String birthDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "requestFees")
    private Double requestFees;
    @Column(name = "PassportNo")
    private String passportNo;
    @Column(name = "PassportIssueCountry")
    private String passportIssueCountry;
    @Column(name = "eyes_inspection_result")
    private String eyesInspectionResult;
    @Column(name = "internal_inspection_result")
    private String internalInspectionResult;
    @Column(name = "inspection_status")
    private String inspectionStatus;
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "MedicalCheckupID")
    private String medicalCheckupID;
    @Column(name = "eyes_exam_date")
    private String eyesExamDate;
    @Column(name = "internal_inspection_date")
    private String internalInspectionDate;
    @Column(name = "request_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "right_eye_degree")
    private String rightEyeDegree;
    @Column(name = "left_eye_degree")
    private String leftEyeDegree;
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @Column(name = "foreignComposite")
    private String foreignComposite;
    @Column(name = "queue")
    private String queue;
    @Lob
    @Column(name = "bioFile")
    private byte[] bioFile;
    @Column(name = "bioDate")
    private String bioDate;
    @Column(name = "serviceType")
    private String serviceType;

    public ClientsData() {
    }

    public ClientsData(Integer id) {
        this.id = id;
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

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }

    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Double getRequestFees() {
        return requestFees;
    }

    public void setRequestFees(Double requestFees) {
        this.requestFees = requestFees;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportIssueCountry() {
        return passportIssueCountry;
    }

    public void setPassportIssueCountry(String passportIssueCountry) {
        this.passportIssueCountry = passportIssueCountry;
    }

    public String getEyesInspectionResult() {
        return eyesInspectionResult;
    }

    public void setEyesInspectionResult(String eyesInspectionResult) {
        this.eyesInspectionResult = eyesInspectionResult;
    }

    public String getInternalInspectionResult() {
        return internalInspectionResult;
    }

    public void setInternalInspectionResult(String internalInspectionResult) {
        this.internalInspectionResult = internalInspectionResult;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getMedicalCheckupID() {
        return medicalCheckupID;
    }

    public void setMedicalCheckupID(String medicalCheckupID) {
        this.medicalCheckupID = medicalCheckupID;
    }

    public String getEyesExamDate() {
        return eyesExamDate;
    }

    public void setEyesExamDate(String eyesExamDate) {
        this.eyesExamDate = eyesExamDate;
    }

    public String getInternalInspectionDate() {
        return internalInspectionDate;
    }

    public void setInternalInspectionDate(String internalInspectionDate) {
        this.internalInspectionDate = internalInspectionDate;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getRightEyeDegree() {
        return rightEyeDegree;
    }

    public void setRightEyeDegree(String rightEyeDegree) {
        this.rightEyeDegree = rightEyeDegree;
    }

    public String getLeftEyeDegree() {
        return leftEyeDegree;
    }

    public void setLeftEyeDegree(String leftEyeDegree) {
        this.leftEyeDegree = leftEyeDegree;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getForeignComposite() {
        return foreignComposite;
    }

    public void setForeignComposite(String foreignComposite) {
        this.foreignComposite = foreignComposite;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public byte[] getBioFile() {
        return bioFile;
    }

    public void setBioFile(byte[] bioFile) {
        this.bioFile = bioFile;
    }

    public String getBioDate() {
        return bioDate;
    }

    public void setBioDate(String bioDate) {
        this.bioDate = bioDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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
        if (!(object instanceof ClientsData)) {
            return false;
        }
        ClientsData other = (ClientsData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ClientsData[ id=" + id + " ]";
    }
    
}
