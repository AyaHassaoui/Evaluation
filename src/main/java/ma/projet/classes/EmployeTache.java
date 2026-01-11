package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="employe_tache")
public class EmployeTache {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    @ManyToOne @JoinColumn(name="employe_id")
    private Employe employe;

    @ManyToOne @JoinColumn(name="tache_id")
    private Tache tache;

    public EmployeTache(){}
    public EmployeTache(Employe e, Tache t, LocalDate d1, LocalDate d2){
        this.employe=e; this.tache=t; this.dateDebutReelle=d1; this.dateFinReelle=d2;
    }
}
