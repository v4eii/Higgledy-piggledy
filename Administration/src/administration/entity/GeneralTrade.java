/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author мвидео
 */
@Entity
@Table(name = "general_trade", catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "GeneralTrade.findAll", query = "SELECT g FROM GeneralTrade g"),
    @NamedQuery(name = "GeneralTrade.findByIdUnion", query = "SELECT g FROM GeneralTrade g WHERE g.idUnion = :idUnion"),
    @NamedQuery(name = "GeneralTrade.findByTimeFirst", query = "SELECT g FROM GeneralTrade g WHERE g.timeFirst = :timeFirst"),
    @NamedQuery(name = "GeneralTrade.findByTimeLast", query = "SELECT g FROM GeneralTrade g WHERE g.timeLast = :timeLast"),
    @NamedQuery(name = "GeneralTrade.findByChekFlag", query = "SELECT g FROM GeneralTrade g WHERE g.chekFlag = :chekFlag"),
    @NamedQuery(name = "GeneralTrade.findByDateCreate", query = "SELECT g FROM GeneralTrade g WHERE g.dateCreate = :dateCreate"),
    @NamedQuery(name = "GeneralTrade.findByDateEnd", query = "SELECT g FROM GeneralTrade g WHERE g.dateEnd = :dateEnd"),
    @NamedQuery(name = "GeneralTrade.findByHouse", query = "SELECT g FROM GeneralTrade g WHERE g.house = :house"),
    @NamedQuery(name = "GeneralTrade.findByApartment", query = "SELECT g FROM GeneralTrade g WHERE g.apartment = :apartment")
})
public class GeneralTrade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_union")
    private Integer idUnion;
    @Column(name = "time_first")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeFirst;
    @Column(name = "time_last")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLast;
    @Column(name = "chek_flag")
    private String chekFlag;
    @Column(name = "date_create")
    @Temporal(TemporalType.DATE)
    private Date dateCreate;
    @Column(name = "date_end")
    @Temporal(TemporalType.DATE)
    private Date dateEnd;
    private String house;
    private String apartment;
    @JoinColumn(name = "id_st", referencedColumnName = "id_st")
    @ManyToOne(optional = false)
    private Statement idSt;
    @JoinColumn(name = "id_adr", referencedColumnName = "id_adr")
    @ManyToOne(optional = false)
    private Street idAdr;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Users idUser;
    @JoinColumn(name = "id_spec", referencedColumnName = "id_spec")
    @ManyToOne(optional = false)
    private Specialization idSpec;

    public GeneralTrade()
    {
    }

    public GeneralTrade(Integer idUnion)
    {
        this.idUnion = idUnion;
    }

    public Integer getIdUnion()
    {
        return idUnion;
    }

    public void setIdUnion(Integer idUnion)
    {
        this.idUnion = idUnion;
    }

    public Date getTimeFirst()
    {
        return timeFirst;
    }

    public void setTimeFirst(Date timeFirst)
    {
        this.timeFirst = timeFirst;
    }

    public Date getTimeLast()
    {
        return timeLast;
    }

    public void setTimeLast(Date timeLast)
    {
        this.timeLast = timeLast;
    }

    public String getChekFlag()
    {
        return chekFlag;
    }

    public void setChekFlag(String chekFlag)
    {
        this.chekFlag = chekFlag;
    }

    public Date getDateCreate()
    {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate)
    {
        this.dateCreate = dateCreate;
    }

    public Date getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd)
    {
        this.dateEnd = dateEnd;
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

    public Statement getIdSt()
    {
        return idSt;
    }

    public void setIdSt(Statement idSt)
    {
        this.idSt = idSt;
    }

    public Street getIdAdr()
    {
        return idAdr;
    }

    public void setIdAdr(Street idAdr)
    {
        this.idAdr = idAdr;
    }

    public Users getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Users idUser)
    {
        this.idUser = idUser;
    }

    public Specialization getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(Specialization idSpec)
    {
        this.idSpec = idSpec;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idUnion != null ? idUnion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneralTrade))
        {
            return false;
        }
        GeneralTrade other = (GeneralTrade) object;
        if ((this.idUnion == null && other.idUnion != null) || (this.idUnion != null && !this.idUnion.equals(other.idUnion)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.GeneralTrade[ idUnion=" + idUnion + " ]";
    }
    
}
