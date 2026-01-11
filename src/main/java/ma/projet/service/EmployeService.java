package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class EmployeService {

    public List<Tache> tachesParEmploye(int id){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("""
              select et.tache from EmployeTache et
              where et.employe.id=:id
            """,Tache.class).setParameter("id",id).list();
        }
    }

    public List<Projet> projetsParEmploye(int id){
        try(Session s=HibernateUtil.getSessionFactory().openSession()){
            return s.createQuery("from Projet p where p.chefProjet.id=:id",
                    Projet.class).setParameter("id",id).list();
        }
    }
}
