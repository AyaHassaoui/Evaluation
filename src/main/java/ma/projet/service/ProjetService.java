package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class ProjetService {

    public List<Tache> tachesProjet(int id){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("from Tache t where t.projet.id=:id",
                    Tache.class).setParameter("id",id).list();
        }
    }

    public List<Object[]> tachesReellesProjet(int id){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
             select t.id,t.nom,et.dateDebutReelle,et.dateFinReelle
             from EmployeTache et join et.tache t
             where t.projet.id=:id
            """,Object[].class).setParameter("id",id).list();
        }
    }
}
