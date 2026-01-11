package ma.projet.classes;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "employe")
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "chefProjet")
    private List<Projet> projets;

    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> employeTaches;

    public Employe() {}
    public Employe(String nom, String prenom, String tel) {
        this.nom = nom; this.prenom = prenom; this.telephone = tel;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    @Override
    public String toString() {
        return id+" - "+nom+" "+prenom;
    }
}
