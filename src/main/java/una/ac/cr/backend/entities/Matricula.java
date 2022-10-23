package una.ac.cr.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "matricula")
public class Matricula extends CommonEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id_matricula")
    private BigInteger id;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona")
    @JsonIgnoreProperties({"matriculas"})
    private Persona persona;

    @ManyToOne(targetEntity = Materia.class)
    @JoinColumn(name = "materia_id", referencedColumnName = "id_materia")
    @JsonIgnoreProperties({"matriculas"})
    private Materia materia;


    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    protected boolean makeComparison(CommonEntity entity) {
        Matricula newMatricula = (Matricula) entity;
        return this.materia.getId().equals(newMatricula.materia.getId()) &&
                this.persona.getId().equals(newMatricula.persona.getId());
    }
}
