package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;
import java.util.List;

public class TestEx1 {
    public static void main(String[] args) {

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService coms = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // 1) Catégories
        Categorie c1 = new Categorie("C1", "PC");
        Categorie c2 = new Categorie("C2", "Accessoires");
        cs.create(c1);
        cs.create(c2);

        // 2) Produits
        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c1);
        Produit p3 = new Produit("EE85", 200, c2);
        Produit p4 = new Produit("AA11", 80, c2);
        ps.create(p1); ps.create(p2); ps.create(p3); ps.create(p4);

        // 3) Commandes
        Commande cmd1 = new Commande(LocalDate.of(2013, 3, 14));
        Commande cmd2 = new Commande(LocalDate.of(2013, 3, 20));
        coms.create(cmd1);
        coms.create(cmd2);

        // 4) Lignes de commande
        lcs.create(new LigneCommandeProduit(cmd1, p1, 7));
        lcs.create(new LigneCommandeProduit(cmd1, p2, 14));
        lcs.create(new LigneCommandeProduit(cmd1, p3, 5));

        lcs.create(new LigneCommandeProduit(cmd2, p4, 9));
        lcs.create(new LigneCommandeProduit(cmd2, p1, 2));

        // ✅ Test: produits par catégorie
        System.out.println("\n--- Produits de la catégorie PC ---");
        ps.findByCategorie(c1.getId()).forEach(System.out::println);

        // ✅ Test: produits commandés entre 2 dates
        System.out.println("\n--- Produits commandés entre 2013-03-13 et 2013-03-15 ---");
        List<Object[]> res = ps.produitsCommandesEntre(LocalDate.of(2013,3,13), LocalDate.of(2013,3,15));
        for (Object[] row : res) {
            System.out.println("Commande=" + row[0] + " date=" + row[1] + " ref=" + row[2] + " prix=" + row[3] + " qte=" + row[4]);
        }

        // ✅ Test: produits d'une commande donnée (format demandé)
        System.out.println("\n==============================");
        System.out.println("Commande : " + cmd1.getId() + "     Date : " + cmd1.getDate());
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");

        List<Object[]> rows = ps.produitsDeCommande(cmd1.getId());
        for (Object[] r : rows) {
            System.out.printf("%-10s %-6sDH %-5s%n", r[0], r[1], r[2]);
        }

        // ✅ Test: prix > 100 (NamedQuery)
        System.out.println("\n--- Produits prix > 100 ---");
        ps.prixSup100().forEach(System.out::println);
    }
}
