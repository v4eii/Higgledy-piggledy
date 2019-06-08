/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Statement.findAll", query = "SELECT s FROM Statement s"),
    @NamedQuery(name = "Statement.findByIdSt", query = "SELECT s FROM Statement s WHERE s.idSt = :idSt"),
    @NamedQuery(name = "Statement.findByOrg", query = "SELECT s FROM Statement s WHERE s.org = :org"),
    @NamedQuery(name = "Statement.findByOgrn", query = "SELECT s FROM Statement s WHERE s.ogrn = :ogrn"),
    @NamedQuery(name = "Statement.findByDateOgrn", query = "SELECT s FROM Statement s WHERE s.dateOgrn = :dateOgrn"),
    @NamedQuery(name = "Statement.findByInn", query = "SELECT s FROM Statement s WHERE s.inn = :inn"),
    @NamedQuery(name = "Statement.findByDateINN", query = "SELECT s FROM Statement s WHERE s.dateINN = :dateINN"),
    @NamedQuery(name = "Statement.findByContact", query = "SELECT s FROM Statement s WHERE s.contact = :contact"),
    @NamedQuery(name = "Statement.findByHouse", query = "SELECT s FROM Statement s WHERE s.house = :house"),
    @NamedQuery(name = "Statement.findByApartment", query = "SELECT s FROM Statement s WHERE s.apartment = :apartment"),
    @NamedQuery(name = "Statement.findByIdAdr", query = "SELECT s FROM Statement s WHERE s.idAdr = :idAdr"),
    @NamedQuery(name = "Statement.findByIdCity", query = "SELECT s FROM Statement s WHERE s.idCity = :idCity"),
    @NamedQuery(name = "Statement.findByIdReg", query = "SELECT s FROM Statement s WHERE s.idReg = :idReg")
})
public class Statement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_st")
    private Integer idSt;
    private String org;
    private String ogrn;
    @Column(name = "date_ogrn")
    @Temporal(TemporalType.DATE)
    private Date dateOgrn;
    private String inn;
    @Column(name = "date_INN")
    @Temporal(TemporalType.DATE)
    private Date dateINN;
    private String contact;
    private String house;
    private String apartment;
    @Basic(optional = false)
    @Column(name = "id_adr")
    private int idAdr;
    @Column(name = "id_city")
    private Integer idCity;
    @Column(name = "id_reg")
    private Integer idReg;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSt")
    private Collection<General> generalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSt")
    private Collection<General2> general2Collection;

    public Statement()
    {
    }

    public Statement(Integer idSt)
    {
        this.idSt = idSt;
    }

    public Statement(Integer idSt, int idAdr)
    {
        this.idSt = idSt;
        this.idAdr = idAdr;
    }

    public Integer getIdSt()
    {
        return idSt;
    }

    public void setIdSt(Integer idSt)
    {
        this.idSt = idSt;
    }

    public String getOrg()
    {
        return org;
    }

    public void setOrg(String org)
    {
        this.org = org;
    }

    public String getOgrn()
    {
        return ogrn;
    }

    public void setOgrn(String ogrn)
    {
        this.ogrn = ogrn;
    }

    public Date getDateOgrn()
    {
        return dateOgrn;
    }

    public void setDateOgrn(Date dateOgrn)
    {
        this.dateOgrn = dateOgrn;
    }

    public String getInn()
    {
        return inn;
    }

    public void setInn(String inn)
    {
        this.inn = inn;
    }

    public Date getDateINN()
    {
        return dateINN;
    }

    public void setDateINN(Date dateINN)
    {
        this.dateINN = dateINN;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getHouse()
    {
        return house;
    }

    public void setHouse(String house)
    {
        this.house = house;
    }

    public String getApartment()
    {
        return apartment;
    }

    public void setApartment(String apartment)
    {
        this.apartment = apartment;
    }

    public int getIdAdr()
    {
        return idAdr;
    }

    public void setIdAdr(int idAdr)
    {
        this.idAdr = idAdr;
    }

    public Integer getIdCity()
    {
        return idCity;
    }

    public void setIdCity(Integer idCity)
    {
        this.idCity = idCity;
    }

    public Integer getIdReg()
    {
        return idReg;
    }

    public void setIdReg(Integer idReg)
    {
        this.idReg = idReg;
    }

    @XmlTransient
    public Collection<General> getGeneralCollection()
    {
        return generalCollection;
    }

    public void setGeneralCollection(Collection<General> generalCollection)
    {
        this.generalCollection = generalCollection;
    }

    @XmlTransient
    public Collection<General2> getGeneral2Collection()
    {
        return general2Collection;
    }

    public void setGeneral2Collection(Collection<General2> general2Collection)
    {
        this.general2Collection = general2Collection;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idSt != null ? idSt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statement))
        {
            return false;
        }
        Statement other = (Statement) object;
        if ((this.idSt == null && other.idSt != null) || (this.idSt != null && !this.idSt.equals(other.idSt)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.Statement[ idSt=" + idSt + " ]";
    }
    
}
