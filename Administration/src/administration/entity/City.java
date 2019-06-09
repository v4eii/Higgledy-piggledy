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
 * @author мвидео
 */
@Entity
@Table(catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "City.findAll", query = "SELECT c FROM City c"),
    @NamedQuery(name = "City.findByIdCity", query = "SELECT c FROM City c WHERE c.idCity = :idCity"),
    @NamedQuery(name = "City.findByCity", query = "SELECT c FROM City c WHERE c.city = :city")
})
public class City implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_city")
    private Integer idCity;
    private String city;
    @JoinColumn(name = "id_reg", referencedColumnName = "id_reg")
    @ManyToOne(optional = false)
    private Region idReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCity")
    private Collection<Street> streetCollection;

    public City()
    {
    }

    public City(Integer idCity)
    {
        this.idCity = idCity;
    }

    public Integer getIdCity()
    {
        return idCity;
    }

    public void setIdCity(Integer idCity)
    {
        this.idCity = idCity;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Region getIdReg()
    {
        return idReg;
    }

    public void setIdReg(Region idReg)
    {
        this.idReg = idReg;
    }

    @XmlTransient
    public Collection<Street> getStreetCollection()
    {
        return streetCollection;
    }

    public void setStreetCollection(Collection<Street> streetCollection)
    {
        this.streetCollection = streetCollection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idCity != null ? idCity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof City))
        {
            return false;
        }
        City other = (City) object;
        if ((this.idCity == null && other.idCity != null) || (this.idCity != null && !this.idCity.equals(other.idCity)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.City[ idCity=" + idCity + " ]";
    }
    
}
