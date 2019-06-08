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
@Table(name = "general_cafe", catalog = "hackaton", schema = "")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "GeneralCafe.findAll", query = "SELECT g FROM GeneralCafe g"),
    @NamedQuery(name = "GeneralCafe.findByIdUnion", query = "SELECT g FROM GeneralCafe g WHERE g.idUnion = :idUnion"),
    @NamedQuery(name = "GeneralCafe.findByTimeFirst", query = "SELECT g FROM GeneralCafe g WHERE g.timeFirst = :timeFirst"),
    @NamedQuery(name = "GeneralCafe.findByTimeLast", query = "SELECT g FROM GeneralCafe g WHERE g.timeLast = :timeLast"),
    @NamedQuery(name = "GeneralCafe.findByChekFlag", query = "SELECT g FROM GeneralCafe g WHERE g.chekFlag = :chekFlag"),
    @NamedQuery(name = "GeneralCafe.findByDateCreate", query = "SELECT g FROM GeneralCafe g WHERE g.dateCreate = :dateCreate"),
    @NamedQuery(name = "GeneralCafe.findByDateEnd", query = "SELECT g FROM GeneralCafe g WHERE g.dateEnd = :dateEnd"),
    @NamedQuery(name = "GeneralCafe.findByHouse", query = "SELECT g FROM GeneralCafe g WHERE g.house = :house"),
    @NamedQuery(name = "GeneralCafe.findByApartment", query = "SELECT g FROM GeneralCafe g WHERE g.apartment = :apartment"),
    @NamedQuery(name = "GeneralCafe.findByObject", query = "SELECT g FROM GeneralCafe g WHERE g.object = :object"),
    @NamedQuery(name = "GeneralCafe.findByKadastr", query = "SELECT g FROM GeneralCafe g WHERE g.kadastr = :kadastr"),
    @NamedQuery(name = "GeneralCafe.findByOkato", query = "SELECT g FROM GeneralCafe g WHERE g.okato = :okato"),
    @NamedQuery(name = "GeneralCafe.findBySquare", query = "SELECT g FROM GeneralCafe g WHERE g.square = :square")
})
public class GeneralCafe implements Serializable {

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

    public GeneralCafe()
    {
    }

    public GeneralCafe(Integer idUnion)
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
        if (!(object instanceof GeneralCafe))
        {
            return false;
        }
        GeneralCafe other = (GeneralCafe) object;
        if ((this.idUnion == null && other.idUnion != null) || (this.idUnion != null && !this.idUnion.equals(other.idUnion)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "administration.entity.GeneralCafe[ idUnion=" + idUnion + " ]";
    }
    
}
