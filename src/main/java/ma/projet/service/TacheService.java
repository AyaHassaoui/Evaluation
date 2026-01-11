package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.time.LocalDate;
import java.util.List;

public class TacheService {

    public List<Tache> prixSup1000(){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createNamedQuery("Tache.prixSup1000",Tache.class).list();
        }
    }

    public List<Tache> tachesEntre(LocalDate d1, LocalDate d2){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
              select et.tache from EmployeTache et
              where et.dateDebutReelle between :d1 and :d2
            """,Tache.class).setParameter("d1",d1).setParameter("d2",d2).list();
        }
    }
}
