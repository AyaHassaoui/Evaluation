package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestEx3 {
    public static void main(String[] args) {

        List<Femme> femmes = new ArrayList<>();
        List<Homme> hommes = new ArrayList<>();

        // 1) créer 10 femmes et 5 hommes
        try(Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = s.beginTransaction();

            for (int i = 1; i <= 10; i++) {
                Femme f = new Femme("FEMME"+i, "P"+i, "06"+i, "Adresse "+i,
                        LocalDate.of(1980+i%5, 1+i%12, 1+i%25));
                s.persist(f);
                femmes.add(f);
            }

            for (int i = 1; i <= 5; i++) {
                Homme h = new Homme("HOMME"+i, "P"+i, "07"+i, "Ville "+i,
                        LocalDate.of(1970+i%5, 2+i%12, 2+i%25));
                s.persist(h);
                hommes.add(h);
            }

            tx.commit();
        }

        MariageService ms = new MariageService();
        FemmeService fs = new FemmeService();
        HommeService hs = new HommeService();

        // 2) créer mariages (exemples)
        ms.create(new Mariage(hommes.get(0), femmes.get(0), LocalDate.of(1989,9,3), LocalDate.of(1990,9,3), 0));
        ms.create(new Mariage(hommes.get(0), femmes.get(1), LocalDate.of(1990,9,3), null, 4));
        ms.create(new Mariage(hommes.get(0), femmes.get(2), LocalDate.of(1995,9,3), null, 2));
        ms.create(new Mariage(hommes.get(0), femmes.get(3), LocalDate.of(2000,11,4), null, 3));

        // Femme mariée 2 fois
        ms.create(new Mariage(hommes.get(1), femmes.get(5), LocalDate.of(2010,1,1), LocalDate.of(2012,1,1), 1));
        ms.create(new Mariage(hommes.get(2), femmes.get(5), LocalDate.of(2013,1,1), null, 2));

        // ✅ Afficher liste femmes
        System.out.println("\n--- Liste des femmes ---");
        fs.getAll().forEach(System.out::println);

        // ✅ Femme la plus âgée
        System.out.println("\n--- Femme la plus âgée ---");
        System.out.println(fs.femmePlusAgee());

        // ✅ Afficher épouses d’un homme donné
        System.out.println("\n--- Epouses de HOMME1 entre 1988 et 2001 ---");
        hs.epousesEntreDeuxDates(hommes.get(0).getId(),
                LocalDate.of(1988,1,1),
                LocalDate.of(2001,12,31)).forEach(System.out::println);

        // ✅ Nombre d’enfants d’une femme entre deux dates (native query)
        System.out.println("\n--- Nombre enfants de FEMME6 entre 2009 et 2020 ---");
        System.out.println(fs.nombreEnfantsEntreDeuxDates(femmes.get(5).getId(),
                LocalDate.of(2009,1,1),
                LocalDate.of(2020,1,1)));

        // ✅ Femmes mariées deux fois ou plus
        System.out.println("\n--- Femmes mariées >= 2 fois ---");
        fs.femmesMarieesDeuxFoisOuPlus().forEach(System.out::println);

        // ✅ Hommes mariés à 4 femmes entre deux dates (Criteria)
        System.out.println("\n--- Nombre hommes mariés à 4 femmes entre 1988 et 2005 ---");
        System.out.println(ms.nbHommesMarieA4FemmesEntre(
                LocalDate.of(1988,1,1),
                LocalDate.of(2005,12,31)
        ));

        // ✅ Mariages d’un homme avec détails
        System.out.println("\n--- Détails mariages HOMME1 ---");
        hs.afficherMariagesHomme(hommes.get(0).getId());
    }
}
