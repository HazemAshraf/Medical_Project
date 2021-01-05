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
@Table(name = "vehicle_requirments_payment_confirm", catalog = "mi", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findAll", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v"),
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findById", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v WHERE v.id = :id"),
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findByPaymentNo", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v WHERE v.paymentNo = :paymentNo"),
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findByConfirmMsg", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v WHERE v.confirmMsg = :confirmMsg"),
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findByStatusCode", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v WHERE v.statusCode = :statusCode"),
    @NamedQuery(name = "VehicleRequirmentsPaymentConfirm.findByIssued", query = "SELECT v FROM VehicleRequirmentsPaymentConfirm v WHERE v.issued = :issued")})
public class VehicleRequirmentsPaymentConfirm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "paymentNo")
    private String paymentNo;
    @Column(name = "confirmMsg")
    private String confirmMsg;
    @Column(name = "statusCode")
    private String statusCode;
    @Column(name = "issued")
    private String issued;

    public VehicleRequirmentsPaymentConfirm() {
    }

    public VehicleRequirmentsPaymentConfirm(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getConfirmMsg() {
        return confirmMsg;
    }

    public void setConfirmMsg(String confirmMsg) {
        this.confirmMsg = confirmMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
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
        if (!(object instanceof VehicleRequirmentsPaymentConfirm)) {
            return false;
        }
        VehicleRequirmentsPaymentConfirm other = (VehicleRequirmentsPaymentConfirm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.VehicleRequirmentsPaymentConfirm[ id=" + id + " ]";
    }
    
}
