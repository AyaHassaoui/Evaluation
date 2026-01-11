package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;

public class HommeService {

    // 1) épouses d’un homme entre 2 dates
    public List<Femme> epousesEntreDeuxDates(int hommeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                    select m.femme
                    from Mariage m
                    where m.homme.id = :id
                    and m.dateDebut between :d1 and :d2
                    """, Femme.class)
                    .setParameter("id", hommeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    // 2) afficher mariages d’un homme avec détails (en cours / échoués)
    public void afficherMariagesHomme(int hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Homme h = s.get(Homme.class, hommeId);
            if (h == null) {
                System.out.println("Homme introuvable !");
                return;
            }

            System.out.println("\nNom : " + h.getNom() + " " + h.getPrenom());

            List<Mariage> enCours = s.createQuery("""
                    from Mariage m
                    where m.homme.id = :id and m.dateFin is null
                    order by m.dateDebut
                    """, Mariage.class).setParameter("id", hommeId).list();

            List<Mariage> echoues = s.createQuery("""
                    from Mariage m
                    where m.homme.id = :id and m.dateFin is not null
                    order by m.dateDebut
                    """, Mariage.class).setParameter("id", hommeId).list();

            System.out.println("\nMariages En Cours :");
            if (enCours.isEmpty()) System.out.println("Aucun");
            int i = 1;
            for (Mariage m : enCours) {
                System.out.println(i++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                        + "   Date Début : " + m.getDateDebut()
                        + "    Nbr Enfants : " + m.getNbrEnfant());
            }

            System.out.println("\nMariages échoués :");
            if (echoues.isEmpty()) System.out.println("Aucun");
            i = 1;
            for (Mariage m : echoues) {
                System.out.println(i++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                        + "   Date Début : " + m.getDateDebut()
                        + "   Date Fin : " + m.getDateFin()
                        + "    Nbr Enfants : " + m.getNbrEnfant());
            }
        }
    }
}
