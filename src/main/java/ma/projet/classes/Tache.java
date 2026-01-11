package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="tache")
@NamedQuery(name="Tache.prixSup1000",
        query="from Tache t where t.prix > 1000")
public class Tache {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private double prix;

    @ManyToOne
    @JoinColumn(name="projet_id")
    private Projet projet;

    @OneToMany(mappedBy="tache")
    private List<EmployeTache> employeTaches;

    public Tache(){}
    public Tache(String nom, LocalDate dd, LocalDate df, double prix, Projet p){
        this.nom=nom; this.dateDebut=dd; this.dateFin=df; this.prix=prix; this.projet=p;
    }

    public int getId(){return id;}
    public String getNom(){return nom;}
}
