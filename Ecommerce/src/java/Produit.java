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
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class Produit implements Serializable {

    private Integer id_produit;
    private String nom_produit;
    private String categorie;
    private float prix_unitaire;
    private String description;
    private int quantite;
    private Personne fournisseur;
    private String image;
    private String login;
    private Personne myFournisseur;
    private Integer quantiteSelected =0;

    public Personne getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Personne fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Integer getQuantiteSelected() {
        return quantiteSelected;
    }

    public void setQuantiteSelected(Integer quantiteSelected) {
        this.quantiteSelected = quantiteSelected;
    }

    public Personne getMyFournisseur() {
        return myFournisseur;
    }

    public void setMyFournisseur(Personne myFournisseur) {
        this.myFournisseur = myFournisseur;
    }
    

    public String getLogin() {
        return login;
    }

    public void setLogin(String fournisseur) {
        this.login = fournisseur;
    }

    /*
     Save Produit into dataBase
     */
    public void addProduit(String newClassLogin) throws InstantiationException, IllegalAccessException, SQLException {
        System.out.println(newClassLogin);
        if (!testBDDNom(newClassLogin)) { /* Nouveau produit par le fournisseur */
            String[] paramInsert;
            String requeteInsert;
            /*traitement description*/
            if (description == null || description.isEmpty() || "".equals(description)){
                
                String[] param = {this.nom_produit,this.categorie,Float.toString(this.prix_unitaire), Integer.toString(this.quantite), newClassLogin};
                requeteInsert = "INSERT INTO ecommerce.produit"
                    + "(id_produit,nom_produit,categorie,prix_unitaire,description,quantite,login)"
                    + "VALUES (NULL,?,?,?,NULL,?,?)";
                paramInsert=param;
            }else {
                 String[] param = {this.nom_produit,this.categorie, Float.toString(this.prix_unitaire),description, Integer.toString(this.quantite), newClassLogin};
                 requeteInsert = "INSERT INTO ecommerce.produit"
                    + "(id_produit,nom_produit,categorie,prix_unitaire,description,quantite,login)"
                    + "VALUES (NULL,?,?,?,?,?,?)";
                 paramInsert=param;
            }
            
            ConnectBDD b = new ConnectBDD();
            try {
                b.executeRequete(requeteInsert, paramInsert);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Enregistrement Réussie"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : Enregistrement Echouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
            b.closeConnect();
        } else {
            
            FacesContext.getCurrentInstance().addMessage("msg",new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Ce produit existe déjà"));
            System.out.println("Erreur nom en doublon");
            FacesContext.getCurrentInstance().addMessage("nom",new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Veuillez en inscrire un autre produit"));
        
        }
    }

    public boolean testBDDNom(String newClassLogin) throws InstantiationException, SQLException, IllegalAccessException {
        String requeteTest = "SELECT count(*) FROM  ecommerce.produit WHERE nom_produit='"+nom_produit+"' AND categorie='"+categorie+"' AND login='"+newClassLogin+"'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requeteTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) >= 1);
        }
        c.closeConnect();
        return false;
    }

    public List<Produit> getListSaucisson() throws SQLException, InstantiationException, IllegalAccessException {
        return getListProduit("Saucisson");
    }

    public List<Produit> getListPate() throws SQLException, InstantiationException, IllegalAccessException {
        return getListProduit("Pâté");
    }

    public List<Produit> getListFromage() throws SQLException, InstantiationException, IllegalAccessException {
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
        String requeteSelect = "SELECT * FROM  ecommerce.produit INNER JOIN ecommerce.personne on personne.login=produit.login where produit.categorie='" + categorie + "'";
        ConnectBDD b = new ConnectBDD();
        List<Produit> list = new ArrayList<>();
        b.executeRequete(requeteSelect, param);
        ResultSet res = b.getResultat();
        while (res.next()) {
            Produit prod = new Produit();
            prod.setId_produit(res.getInt("id_produit"));
            prod.setCategorie(res.getString("categorie"));
            prod.setDescription(res.getString("description"));
            prod.setNom_produit(res.getString("nom_produit"));
            prod.setPrix_unitaire(res.getFloat("prix_unitaire"));
            prod.setQuantite(res.getInt("quantite"));
            prod.setImage("resources/"+res.getString("image"));
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
            prod.setMyFournisseur(pers);
            prod.setLogin(pers.getLogin());
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
