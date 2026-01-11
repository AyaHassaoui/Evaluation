package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="projet")
public class Projet {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne
    @JoinColumn(name="chef_projet")
    private Employe chefProjet;

    @OneToMany(mappedBy="projet")
    private List<Tache> taches;

    public Projet(){}
    public Projet(String nom, LocalDate dd, LocalDate df, Employe e){
        this.nom=nom; this.dateDebut=dd; this.dateFin=df; this.chefProjet=e;
    }

    public int getId(){return id;}
    public String getNom(){return nom;}
    public LocalDate getDateDebut(){return dateDebut;}
}
