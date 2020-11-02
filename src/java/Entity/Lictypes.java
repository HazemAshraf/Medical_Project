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
@Table(name = "lictypes", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lictypes.findAll", query = "SELECT l FROM Lictypes l"),
    @NamedQuery(name = "Lictypes.findByLookUpID", query = "SELECT l FROM Lictypes l WHERE l.lookUpID = :lookUpID"),
    @NamedQuery(name = "Lictypes.findByDescription", query = "SELECT l FROM Lictypes l WHERE l.description = :description")})
public class Lictypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "lookUp_ID")
    private String lookUpID;
    @Column(name = "description")
    private String description;
    

    public Lictypes() {
    }

    public Lictypes(String lookUpID) {
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
        if (!(object instanceof Lictypes)) {
            return false;
        }
        Lictypes other = (Lictypes) object;
        if ((this.lookUpID == null && other.lookUpID != null) || (this.lookUpID != null && !this.lookUpID.equals(other.lookUpID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Lictypes[ lookUpID=" + lookUpID + " ]";
    }

   

}
