/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration.entity;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "General2.findAll", query = "SELECT g FROM General2 g"),
    @NamedQuery(name = "General2.findByIdUnion", query = "SELECT g FROM General2 g WHERE g.idUnion = :idUnion"),
    @NamedQuery(name = "General2.findByTimeFirst", query = "SELECT g FROM General2 g WHERE g.timeFirst = :timeFirst"),
    @NamedQuery(name = "General2.findByTimeLast", query = "SELECT g FROM General2 g WHERE g.timeLast = :timeLast"),
    @NamedQuery(name = "General2.findByChekFlag", query = "SELECT g FROM General2 g WHERE g.chekFlag = :chekFlag"),
    @NamedQuery(name = "General2.findByDateCreate", query = "SELECT g FROM General2 g WHERE g.dateCreate = :dateCreate"),
    @NamedQuery(name = "General2.findByDateEnd", query = "SELECT g FROM General2 g WHERE g.dateEnd = :dateEnd"),
    @NamedQuery(name = "General2.findByHouse", query = "SELECT g FROM General2 g WHERE g.house = :house"),
    @NamedQuery(name = "General2.findByApartment", query = "SELECT g FROM General2 g WHERE g.apartment = :apartment"),
    @NamedQuery(name = "General2.findByObject", query = "SELECT g FROM General2 g WHERE g.object = :object"),
    @NamedQuery(name = "General2.findByKadastr", query = "SELECT g FROM General2 g WHERE g.kadastr = :kadastr"),
    @NamedQuery(name = "General2.findByOkato", query = "SELECT g FROM General2 g WHERE g.okato = :okato"),
    @NamedQuery(name = "General2.findBySquare", query = "SELECT g FROM General2 g WHERE g.square = :square"),
    @NamedQuery(name = "General2.findByIdAdr", query = "SELECT g FROM General2 g WHERE g.idAdr = :idAdr"),
    @NamedQuery(name = "General2.findByIdCity", query = "SELECT g FROM General2 g WHERE g.idCity = :idCity"),
    @NamedQuery(name = "General2.findByIdReg", query = "SELECT g FROM General2 g WHERE g.idReg = :idReg")
})
public class General2 implements Serializable {

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
    private String dateEnd;
    private String house;
    private BigInteger apartment;
    private String object;
    private String kadastr;
    private String okato;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float square;
    @Basic(optional = false)
    @Column(name = "id_adr")
    private int idAdr;
    @Basic(optional = false)
    @Column(name = "id_city")
    private int idCity;
    @Basic(optional = false)
    @Column(name = "id_reg")
    private int idReg;
    @JoinColumn(name = "id_st", referencedColumnName = "id_st")
    @ManyToOne(optional = false)
    private Statement idSt;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Users idUser;
    @JoinColumn(name = "id_spec", referencedColumnName = "id_spec")
    @ManyToOne(optional = false)
    private Specialization idSpec;

    public General2()
    {
    }

    public General2(Integer idUnion)
    {
        this.idUnion = idUnion;
    }

    public General2(Integer idUnion, int idAdr, int idCity, int idReg)
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

    public String getDateEnd()
    {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd)
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

    public BigInteger getApartment()
    {
        return apartment;
    }

    public void setApartment(BigInteger apartment)
    {
        this.apartment = apartment;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject(String object)
    {
        this.object = object;
    }

    public String getKadastr()
    {
        return kadastr;
    }

    public void setKadastr(String kadastr)
    {
        this.kadastr = kadastr;
    }

    public String getOkato()
    {
        return okato;
    }

    public void setOkato(String okato)
    {
        this.okato = okato;
    }

    public Float getSquare()
    {
        return square;
    }

    public void setSquare(Float square)
    {
        this.square = square;
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

    public Statement getIdSt()
    {
        return idSt;
    }

    public void setIdSt(Statement idSt)
    {
        this.idSt = idSt;
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
        if (!(object instanceof General2))
        {
            return false;
        }
        General2 other = (General2) object;
        if ((this.idUnion == null && other.idUnion != null) || (this.idUnion != null && !this.idUnion.equals(other.idUnion)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.General2[ idUnion=" + idUnion + " ]";
    }
    
}
