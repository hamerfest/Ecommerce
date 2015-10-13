/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Djo
 */
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class Produit implements Serializable {

    private Integer id_produit;
    private String nom_produit;
    private String categorie;
    private float prix_unitaire;
    private String description;
    private int quantite;
    private Personne fournisseur;

    public Personne getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Personne fournisseur) {
        this.fournisseur = fournisseur;
    }

    /*
     Save Personne into dataBase
     Par defaut fonction = "Client"
     */
    public void savePersonne() throws InstantiationException, IllegalAccessException, SQLException {

        String[] paramInsert = {this.nom_produit, Float.toString(this.prix_unitaire), this.description, Integer.toString(this.quantite), this.fournisseur.getLogin()};
        String requeteInsert = "INSERT INTO ecommerce.produit"
                + "(id_produit,nom_produit,categorie,prix_unitaire,description,quantite,login)"
                + "VALUES (NULL,?,?,?,?,?,?,?,?)";
        ConnectBDD b = new ConnectBDD();
        try {
            b.executeRequete(requeteInsert, paramInsert);
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
            System.out.println(e.getMessage());
        }
        b.closeConnect();
    }
    
    public List<Produit> getListSaucisson() throws SQLException, InstantiationException, IllegalAccessException{
        return getListProduit("Saucisson");
    }
    
    public List<Produit> getListPate() throws SQLException, InstantiationException, IllegalAccessException{
        return getListProduit("Pâté");
    }
    public List<Produit> getListFromage() throws SQLException, InstantiationException, IllegalAccessException{
        return getListProduit("Fromage");
    }
    /**
     *
     * @param categorie Saucisson , Pâté ou Fromage
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    
    
    public List<Produit> getListProduit(String categorie) throws SQLException, InstantiationException, IllegalAccessException {
        String[] param = null;
        String requeteSelect = "SELECT * FROM  ecommerce.produit INNER JOIN ecommerce.personne on personne.login=produit.login where produit.categorie='"+categorie+"'";
        ConnectBDD b =new ConnectBDD();
        List<Produit> list = new ArrayList<>();
        b.executeRequete(requeteSelect,param);
        ResultSet res = b.getResultat();
        while (res.next()){
            Produit prod = new Produit();
            prod.setId_produit(res.getInt("id_produit"));
            prod.setCategorie(res.getString("categorie"));
            prod.setDescription(res.getString("description"));
            prod.setNom_produit(res.getString("nom_produit"));
            prod.setPrix_unitaire(res.getFloat("prix_unitaire"));
            prod.setQuantite(res.getInt("quantite"));
            /*Personne*/
            Personne pers = new Personne();
            pers.setId_personne(res.getInt("id_personne"));
            pers.setLogin(res.getString("login"));
            pers.setMdp(res.getString("mdp"));
            pers.setNom(res.getString("nom"));
            pers.setPrenom(res.getString("prenom"));
            pers.setAdresse(res.getString("adresse"));
            pers.setCdp(res.getString("cdp"));
            pers.setVille(res.getString("ville"));
            pers.setFonction(res.getString("fonction"));
            prod.setFournisseur(pers);
            list.add(prod);
        }
        b.closeConnect();
        return list;
    }
    

    /**
     * Get the value of quantite
     *
     * @return the value of quantite
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Set the value of quantite
     *
     * @param quantite new value of quantite
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of prix_unitaire
     *
     * @return the value of prix_unitaire
     */
    public float getPrix_unitaire() {
        return prix_unitaire;
    }

    /**
     * Set the value of prix_unitaire
     *
     * @param prix_unitaire new value of prix_unitaire
     */
    public void setPrix_unitaire(float prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    /**
     * Get the value of categorie
     *
     * @return the value of categorie
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Set the value of categorie
     *
     * @param categorie new value of categorie
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Get the value of nom_produit
     *
     * @return the value of nom_produit
     */
    public String getNom_produit() {
        return nom_produit;
    }

    /**
     * Set the value of nom_produit
     *
     * @param nom_produit new value of nom_produit
     */
    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    /**
     * Get the value of id_produit
     *
     * @return the value of id_produit
     */
    public Integer getId_produit() {
        return id_produit;
    }

    /**
     * Set the value of id_produit
     *
     * @param id_produit new value of id_produit
     */
    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }

}
