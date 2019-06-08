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
@Table(catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "General.findAll", query = "SELECT g FROM General g"),
    @NamedQuery(name = "General.findByIdUnion", query = "SELECT g FROM General g WHERE g.idUnion = :idUnion"),
    @NamedQuery(name = "General.findByTimeFirst", query = "SELECT g FROM General g WHERE g.timeFirst = :timeFirst"),
    @NamedQuery(name = "General.findByTimeLast", query = "SELECT g FROM General g WHERE g.timeLast = :timeLast"),
    @NamedQuery(name = "General.findByChekFlag", query = "SELECT g FROM General g WHERE g.chekFlag = :chekFlag"),
    @NamedQuery(name = "General.findByDateCreate", query = "SELECT g FROM General g WHERE g.dateCreate = :dateCreate"),
    @NamedQuery(name = "General.findByDateEnd", query = "SELECT g FROM General g WHERE g.dateEnd = :dateEnd"),
    @NamedQuery(name = "General.findByHouse", query = "SELECT g FROM General g WHERE g.house = :house"),
    @NamedQuery(name = "General.findByApartment", query = "SELECT g FROM General g WHERE g.apartment = :apartment"),
    @NamedQuery(name = "General.findByIdAdr", query = "SELECT g FROM General g WHERE g.idAdr = :idAdr"),
    @NamedQuery(name = "General.findByIdCity", query = "SELECT g FROM General g WHERE g.idCity = :idCity"),
    @NamedQuery(name = "General.findByIdReg", query = "SELECT g FROM General g WHERE g.idReg = :idReg")
})
public class General implements Serializable {

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
    @Basic(optional = false)
    @Column(name = "id_adr")
    private int idAdr;
    @Basic(optional = false)
    @Column(name = "id_city")
    private int idCity;
    @Basic(optional = false)
    @Column(name = "id_reg")
    private int idReg;
    @JoinColumn(name = "id_spec", referencedColumnName = "id_spec")
    @ManyToOne(optional = false)
    private Specialization idSpec;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Users idUser;
    @JoinColumn(name = "id_st", referencedColumnName = "id_st")
    @ManyToOne(optional = false)
    private Statement idSt;

    public General()
    {
    }

    public General(Integer idUnion)
    {
        this.idUnion = idUnion;
    }

    public General(Integer idUnion, int idAdr, int idCity, int idReg)
    {
        this.idUnion = idUnion;
        this.idAdr = idAdr;
        this.idCity = idCity;
        this.idReg = idReg;
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

    public int getIdAdr()
    {
        return idAdr;
    }

    public void setIdAdr(int idAdr)
    {
        this.idAdr = idAdr;
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

    public Specialization getIdSpec()
    {
        return idSpec;
    }

    public void setIdSpec(Specialization idSpec)
    {
        this.idSpec = idSpec;
    }

    public Users getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Users idUser)
    {
        this.idUser = idUser;
    }

    public Statement getIdSt()
    {
        return idSt;
    }

    public void setIdSt(Statement idSt)
    {
        this.idSt = idSt;
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
        if (!(object instanceof General))
        {
            return false;
        }
        General other = (General) object;
        if ((this.idUnion == null && other.idUnion != null) || (this.idUnion != null && !this.idUnion.equals(other.idUnion)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.General[ idUnion=" + idUnion + " ]";
    }
    
}
