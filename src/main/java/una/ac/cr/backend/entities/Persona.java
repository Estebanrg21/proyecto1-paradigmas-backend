package una.ac.cr.backend.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "persona")
public class Persona{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id_persona")
    private BigInteger id;
    @Column(unique = true)
    private String identificacion;
    private String nombre;

    @OneToMany(targetEntity = Matricula.class, mappedBy = "persona")
    private List<Matricula> matriculas;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
