package ma.projet.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="femme")
public class Femme extends Personne {
    public Femme() {}

    public Femme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
}
