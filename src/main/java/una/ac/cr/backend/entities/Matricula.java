package una.ac.cr.backend.entities;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "matricula")
public class Matricula {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id_matricula")
    private BigInteger id;

    @ManyToOne(targetEntity = Persona.class)
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona")
    private Persona persona;

    @ManyToOne(targetEntity = Materia.class)
    @JoinColumn(name = "materia_id", referencedColumnName = "id_materia")
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
    public String toString() {
        return "Matricula{" +
                "id=" + id +
                ", persona=" + persona +
                ", materia=" + materia +
                '}';
    }
}
