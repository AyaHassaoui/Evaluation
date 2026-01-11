package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.remove(s.contains(o) ? o : s.merge(o));
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit getById(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Produit.class, id);
        }
    }

    @Override
    public List<Produit> getAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Produit", Produit.class).list();
        }
    }

    // 1) Produits par catégorie
    public List<Produit> findByCategorie(int categorieId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Produit p where p.categorie.id = :id", Produit.class)
                    .setParameter("id", categorieId)
                    .list();
        }
    }

    // 2) Produits commandés entre deux dates (join)
    public List<Object[]> produitsCommandesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select c.id, c.date, p.reference, p.prix, l.quantite
                from LigneCommandeProduit l
                join l.commande c
                join l.produit p
                where c.date between :d1 and :d2
                order by c.id
            """;
            return s.createQuery(hql, Object[].class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        }
    }

    // 3) Produits d'une commande donnée (affichage)
    public List<Object[]> produitsDeCommande(int commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            String hql = """
                select p.reference, p.prix, l.quantite
                from LigneCommandeProduit l
                join l.commande c
                join l.produit p
                where c.id = :id
            """;
            return s.createQuery(hql, Object[].class)
                    .setParameter("id", commandeId)
                    .list();
        }
    }

    // 4) prix > 100 via NamedQuery
    public List<Produit> prixSup100() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.prixSup100", Produit.class).list();
        }
    }
}
