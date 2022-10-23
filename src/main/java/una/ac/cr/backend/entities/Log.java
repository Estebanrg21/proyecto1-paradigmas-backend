package una.ac.cr.backend.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_log")
    private BigInteger id;
    private String metodo;
    @Column( columnDefinition = "DATE")
    private Date fecha;

    private String entidad;

    public Log(String metodo, Date fecha, String entidad) {
        this.metodo = metodo;
        this.fecha = fecha;
        this.entidad = entidad;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }
}
