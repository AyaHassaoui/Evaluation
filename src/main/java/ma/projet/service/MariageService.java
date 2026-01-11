package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

public class MariageService {

    // CRUD simple
    public void create(Mariage m) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            s.beginTransaction();
            s.persist(m);
            s.getTransaction().commit();
        }
    }

    // Criteria API : nombre d’hommes mariés à 4 femmes entre deux dates
    public long nbHommesMarieA4FemmesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> m = cq.from(Mariage.class);

            // select count(distinct homme.id)
            cq.select(cb.countDistinct(m.get("homme").get("id")));

            // subquery: hommes avec count(distinct femme) = 4
            Subquery<Integer> sub = cq.subquery(Integer.class);
            Root<Mariage> m2 = sub.from(Mariage.class);
            sub.select(m2.get("homme").get("id"));

            sub.where(cb.between(m2.get("dateDebut"), d1, d2));
            sub.groupBy(m2.get("homme").get("id"));
            sub.having(cb.equal(cb.countDistinct(m2.get("femme").get("id")), 4));

            cq.where(m.get("homme").get("id").in(sub));

            return s.createQuery(cq).getSingleResult();
        }
    }

    public List<Mariage> getAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Mariage", Mariage.class).list();
        }
    }
}
