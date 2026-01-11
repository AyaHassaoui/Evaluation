package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;

public class TestEx2 {
    public static void main(String[] args) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        Employe e1=new Employe("SAFI","SAID","0600");
        s.persist(e1);

        Projet p1=new Projet("Gestion de stock",
                LocalDate.of(2013,1,14),null,e1);
        s.persist(p1);

        Tache t1=new Tache("Analyse",null,null,1500,p1);
        Tache t2=new Tache("Conception",null,null,1200,p1);
        s.persist(t1); s.persist(t2);

        EmployeTache et1=new EmployeTache(e1,t1,
                LocalDate.of(2013,2,10),LocalDate.of(2013,2,20));
        s.persist(et1);

        tx.commit(); s.close();

        ProjetService ps=new ProjetService();
        System.out.println("\nTaches réelles du projet :");
        for(Object[] o:ps.tachesReellesProjet(p1.getId())){
            System.out.println(o[0]+" "+o[1]+" "+o[2]+" "+o[3]);
        }
    }
}
