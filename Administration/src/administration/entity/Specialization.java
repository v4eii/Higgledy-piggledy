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
    @NamedQuery(name = "Specialization.findAll", query = "SELECT s FROM Specialization s"),
    @NamedQuery(name = "Specialization.findByIdSpec", query = "SELECT s FROM Specialization s WHERE s.idSpec = :idSpec"),
    @NamedQuery(name = "Specialization.findBySpec", query = "SELECT s FROM Specialization s WHERE s.spec = :spec")
})
public class Specialization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_spec")
    private Integer idSpec;
    private String spec;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSpec")
    private Collection<GeneralTrade> generalTradeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSpec")
    private Collection<GeneralCafe> generalCafeCollection;

    public Specialization()
    {
    }

    public Specialization(Integer idSpec)
    {
        this.idSpec = idSpec;
    }

    public Integer getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(Integer idSpec)
    {
        this.idSpec = idSpec;
    }

    public String getSpec()
    {
        return spec;
    }

    public void setSpec(String spec)
    {
        this.spec = spec;
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
        hash += (idSpec != null ? idSpec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialization))
        {
            return false;
        }
        Specialization other = (Specialization) object;
        if ((this.idSpec == null && other.idSpec != null) || (this.idSpec != null && !this.idSpec.equals(other.idSpec)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.Specialization[ idSpec=" + idSpec + " ]";
    }
    
}
