package una.ac.cr.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id_materia")
    private BigInteger id;

    private String descripcion;

    @ManyToOne(targetEntity = Periodo.class)
    @JoinColumn(name = "periodo_id", referencedColumnName = "id_periodo")
    @JsonIgnoreProperties({"materias"})
    private Periodo periodo;

    @OneToMany(targetEntity = Matricula.class, mappedBy = "materia")
    private List<Matricula> matriculas;
    private Integer cupos;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }
}
