package ma.projet.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="homme")
public class Homme extends Personne {
    public Homme() {}

    public Homme(String nom, String prenom, String telephone, String adresse, java.time.LocalDate dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }
}
