/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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
@Table(name = "inquiry_users", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InquiryUsers.findAll", query = "SELECT i FROM InquiryUsers i"),
    @NamedQuery(name = "InquiryUsers.findById", query = "SELECT i FROM InquiryUsers i WHERE i.id = :id"),
    @NamedQuery(name = "InquiryUsers.findByNationalId", query = "SELECT i FROM InquiryUsers i WHERE i.nationalId = :nationalId"),
    @NamedQuery(name = "InquiryUsers.findByName", query = "SELECT i FROM InquiryUsers i WHERE i.name = :name"),
    @NamedQuery(name = "InquiryUsers.findByUsername", query = "SELECT i FROM InquiryUsers i WHERE i.username = :username"),
    @NamedQuery(name = "InquiryUsers.findByPassword", query = "SELECT i FROM InquiryUsers i WHERE i.password = :password"),
    @NamedQuery(name = "InquiryUsers.findByTrafficUnit", query = "SELECT i FROM InquiryUsers i WHERE i.trafficUnit = :trafficUnit"),
    @NamedQuery(name = "InquiryUsers.findByTrafficUnitCode", query = "SELECT i FROM InquiryUsers i WHERE i.trafficUnitCode = :trafficUnitCode")})
public class InquiryUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NATIONAL_ID")
    private String nationalId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "TRAFFIC_UNIT")
    private String trafficUnit;
    @Column(name = "TRAFFIC_UNIT_CODE")
    private String trafficUnitCode;

    public InquiryUsers() {
    }

    public InquiryUsers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTrafficUnit() {
        return trafficUnit;
    }

    public void setTrafficUnit(String trafficUnit) {
        this.trafficUnit = trafficUnit;
    }

    public String getTrafficUnitCode() {
        return trafficUnitCode;
    }

    public void setTrafficUnitCode(String trafficUnitCode) {
        this.trafficUnitCode = trafficUnitCode;
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
        if (!(object instanceof InquiryUsers)) {
            return false;
        }
        InquiryUsers other = (InquiryUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.InquiryUsers[ id=" + id + " ]";
    }
    
}
