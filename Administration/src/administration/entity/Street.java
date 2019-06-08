/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity;

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
 * @author мвидео
 */
@Entity
@Table(catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Street.findAll", query = "SELECT s FROM Street s"),
    @NamedQuery(name = "Street.findByIdAdr", query = "SELECT s FROM Street s WHERE s.idAdr = :idAdr"),
    @NamedQuery(name = "Street.findByStreet", query = "SELECT s FROM Street s WHERE s.street = :street"),
    @NamedQuery(name = "Street.findByIndex", query = "SELECT s FROM Street s WHERE s.index = :index"),
    @NamedQuery(name = "Street.findByIdCity", query = "SELECT s FROM Street s WHERE s.idCity = :idCity"),
    @NamedQuery(name = "Street.findByIdReg", query = "SELECT s FROM Street s WHERE s.idReg = :idReg")
})
public class Street implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adr")
    private Integer idAdr;
    private String street;
    private String index;
    @Basic(optional = false)
    @Column(name = "id_city")
    private int idCity;
    @Basic(optional = false)
    @Column(name = "id_reg")
    private int idReg;

    public Street()
    {
    }

    public Street(Integer idAdr)
    {
        this.idAdr = idAdr;
    }

    public Street(Integer idAdr, int idCity, int idReg)
    {
        this.idAdr = idAdr;
        this.idCity = idCity;
        this.idReg = idReg;
    }

    public Integer getIdAdr()
    {
        return idAdr;
    }

    public void setIdAdr(Integer idAdr)
    {
        this.idAdr = idAdr;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public int getIdCity()
    {
        return idCity;
    }

    public void setIdCity(int idCity)
    {
        this.idCity = idCity;
    }

    public int getIdReg()
    {
        return idReg;
    }

    public void setIdReg(int idReg)
    {
        this.idReg = idReg;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idAdr != null ? idAdr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Street))
        {
            return false;
        }
        Street other = (Street) object;
        if ((this.idAdr == null && other.idAdr != null) || (this.idAdr != null && !this.idAdr.equals(other.idAdr)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.Street[ idAdr=" + idAdr + " ]";
    }
    
}
