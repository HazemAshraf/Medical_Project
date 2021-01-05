/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "passport_issue_country", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PassportIssueCountry.findAll", query = "SELECT p FROM PassportIssueCountry p"),
    @NamedQuery(name = "PassportIssueCountry.findByLookUpID", query = "SELECT p FROM PassportIssueCountry p WHERE p.lookUpID = :lookUpID"),
    @NamedQuery(name = "PassportIssueCountry.findByDescription", query = "SELECT p FROM PassportIssueCountry p WHERE p.description = :description")})
public class PassportIssueCountry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "lookUp_ID")
    private String lookUpID;
    @Column(name = "description")
    private String description;
   

    public PassportIssueCountry() {
    }

    public PassportIssueCountry(String lookUpID) {
        this.lookUpID = lookUpID;
    }

    public String getLookUpID() {
        return lookUpID;
    }

    public void setLookUpID(String lookUpID) {
        this.lookUpID = lookUpID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lookUpID != null ? lookUpID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PassportIssueCountry)) {
            return false;
        }
        PassportIssueCountry other = (PassportIssueCountry) object;
        if ((this.lookUpID == null && other.lookUpID != null) || (this.lookUpID != null && !this.lookUpID.equals(other.lookUpID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.PassportIssueCountry[ lookUpID=" + lookUpID + " ]";
    }

  
    
}
