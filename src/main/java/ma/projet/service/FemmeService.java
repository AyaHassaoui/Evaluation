package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class FemmeService {

    // Native named query : nombre d’enfants entre 2 dates
    public long nombreEnfantsEntreDeuxDates(int femmeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Object result = s.createNamedQuery("Femme.nbEnfantsEntre")
                    .setParameter(1, femmeId)
                    .setParameter(2, d1)
                    .setParameter(3, d2)
                    .getSingleResult();
            return ((Number) result).longValue();
        }
    }

    // NamedQuery : femmes mariées au moins 2 fois
    public List<Femme> femmesMarieesDeuxFoisOuPlus() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieeDeuxFoisOuPlus", Femme.class).list();
        }
    }

    // Femme la plus âgée
    public Femme femmePlusAgee() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    public List<Femme> getAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Femme", Femme.class).list();
        }
    }
}
