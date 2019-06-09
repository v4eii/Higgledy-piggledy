/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author мвидео
 */
@Entity
@Table(catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r"),
    @NamedQuery(name = "Region.findByIdReg", query = "SELECT r FROM Region r WHERE r.idReg = :idReg"),
    @NamedQuery(name = "Region.findByRegion", query = "SELECT r FROM Region r WHERE r.region = :region")
})
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reg")
    private Integer idReg;
    private String region;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idReg")
    private Collection<City> cityCollection;

    public Region()
    {
    }

    public Region(Integer idReg)
    {
        this.idReg = idReg;
    }

    public Integer getIdReg()
    {
        return idReg;
    }

    public void setIdReg(Integer idReg)
    {
        this.idReg = idReg;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    @XmlTransient
    public Collection<City> getCityCollection()
    {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection)
    {
        this.cityCollection = cityCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idReg != null ? idReg.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region))
        {
            return false;
        }
        Region other = (Region) object;
        if ((this.idReg == null && other.idReg != null) || (this.idReg != null && !this.idReg.equals(other.idReg)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.Region[ idReg=" + idReg + " ]";
    }
    
}
