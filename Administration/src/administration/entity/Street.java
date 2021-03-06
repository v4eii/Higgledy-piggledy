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
 * @author ������
 */
@Entity
@Table(catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "Street.findAll", query = "SELECT s FROM Street s"),
    @NamedQuery(name = "Street.findByIdAdr", query = "SELECT s FROM Street s WHERE s.idAdr = :idAdr"),
    @NamedQuery(name = "Street.findByStreet", query = "SELECT s FROM Street s WHERE s.street = :street"),
    @NamedQuery(name = "Street.findByPochta", query = "SELECT s FROM Street s WHERE s.pochta = :pochta")
})
public class Street implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_adr")
    private Integer idAdr;
    private String street;
    private String pochta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdr")
    private Collection<GeneralTrade> generalTradeCollection;
    @JoinColumn(name = "id_city", referencedColumnName = "id_city")
    @ManyToOne(optional = false)
    private City idCity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdr")
    private Collection<Statement> statementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAdr")
    private Collection<GeneralCafe> generalCafeCollection;

    public Street()
    {
    }

    public Street(Integer idAdr)
    {
        this.idAdr = idAdr;
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

    public String getPochta()
    {
        return pochta;
    }

    public void setPochta(String pochta)
    {
        this.pochta = pochta;
    }

    @XmlTransient
    public Collection<GeneralTrade> getGeneralTradeCollection()
    {
        return generalTradeCollection;
    }

    public void setGeneralTradeCollection(Collection<GeneralTrade> generalTradeCollection)
    {
        this.generalTradeCollection = generalTradeCollection;
    }

    public City getIdCity()
    {
        return idCity;
    }

    public void setIdCity(City idCity)
    {
        this.idCity = idCity;
    }

    @XmlTransient
    public Collection<Statement> getStatementCollection()
    {
        return statementCollection;
    }

    public void setStatementCollection(Collection<Statement> statementCollection)
    {
        this.statementCollection = statementCollection;
    }

    @XmlTransient
    public Collection<GeneralCafe> getGeneralCafeCollection()
    {
        return generalCafeCollection;
    }

    public void setGeneralCafeCollection(Collection<GeneralCafe> generalCafeCollection)
    {
        this.generalCafeCollection = generalCafeCollection;
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
