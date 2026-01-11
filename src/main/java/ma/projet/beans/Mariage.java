package ma.projet.beans;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="mariage")

@NamedQuery(
        name = "Femme.marieeDeuxFoisOuPlus",
        query = "select m.femme from Mariage m group by m.femme having count(m.id) >= 2"
)

@NamedNativeQuery(
        name = "Femme.nbEnfantsEntre",
        query = "SELECT COALESCE(SUM(nbr_enfant),0) FROM mariage " +
                "WHERE femme_id = ?1 AND date_debut BETWEEN ?2 AND ?3",
        resultClass = Long.class
)
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="date_debut")
    private LocalDate dateDebut;

    @Column(name="date_fin")
    private LocalDate dateFin; // nullable

    @Column(name="nbr_enfant")
    private int nbrEnfant;

    @ManyToOne
    @JoinColumn(name="homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name="femme_id")
    private Femme femme;

    public Mariage() {}

    public Mariage(Homme homme, Femme femme, LocalDate dateDebut, LocalDate dateFin, int nbrEnfant) {
        this.homme = homme;
        this.femme = femme;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrEnfant = nbrEnfant;
    }

    public int getId() { return id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public Homme getHomme() { return homme; }
    public Femme getFemme() { return femme; }

    @Override
    public String toString() {
        return "Mariage{id=" + id + ", H=" + homme.getNom() + ", F=" + femme.getNom() +
                ", debut=" + dateDebut + ", fin=" + dateFin + ", enfants=" + nbrEnfant + "}";
    }
}
