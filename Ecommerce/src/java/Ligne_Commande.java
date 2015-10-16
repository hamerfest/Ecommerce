
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Djo
 */
@Named
@ManagedBean
public class Ligne_Commande {

    private Integer id_lg_commande;
    private Integer quantite;
    private Integer id_commande;
    private Integer id_produit;

    public void addLgCommande(String newClassLogin, Integer id_produit, Integer quantiteSelected) throws InstantiationException, IllegalAccessException, SQLException {
        System.out.println("Parametre"+newClassLogin+" "+id_produit+" "+quantiteSelected);
        if (commandeExist(newClassLogin)) {
            id_commande = idCommandExist(newClassLogin);
            id_lg_commande = idLinesameLigne(id_produit);
            this.id_produit = id_produit;
            this.quantite = quantitePredSameLine();
            /*UPDATE de la ligne */
            String requeteMajLg = "UPDATE ecommerce.ligne_commande SET quantite="+Integer.toString(quantite + quantiteSelected)+
                    " WHERE id_lg_commande="+Integer.toString(id_lg_commande)+" and id_commande="+Integer.toString(id_commande)+
                    " and id_produit="+Integer.toString(id_produit);
            ConnectBDD b = new ConnectBDD();
            try {
                b.executeRequete(requeteMajLg, null);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Mise à jour Réussie"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : Mise à jour Echouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
            majPrixCommande(id_commande);
        }
        else {
            /*création nouvelle commande */
            System.out.println("Creation commande");
            
            String requeteInsert = "INSERT INTO ecommerce.commande"
                    + "(id_commande,statut,prix_total,date_commande,date_livraison,login)"
                    + "VALUES (NULL,'progress',0,NOW(),NULL,?)";
            String[] paramInsert ={newClassLogin};          
            ConnectBDD b = new ConnectBDD();
            try {
                b.executeRequete(requeteInsert, paramInsert);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Nouvelle Commande ajoutée"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : Enregistrement Echouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
            id_commande = idCommandExist(newClassLogin);
            System.out.println("Récupération de l'id commande :"+Integer.toString(id_commande));
            System.out.println("quantite selectionne :"+quantiteSelected);
            /*création nouvelle ligne de commande*/
            String requeteInsertLg = "INSERT INTO ecommerce.ligne_commande"
                    + "(id_lg_commande,quantite,id_commande,id_produit)"
                    + "VALUES (NULL,"+Integer.toString(id_lg_commande)+","+Integer.toString(quantiteSelected)+","+Integer.toString(id_commande)+","+Integer.toString(id_produit);
            System.out.println("creation ligne :"+requeteInsertLg);
            try {
                System.out.println("Insertion Ligne");
                b.executeRequete(requeteInsertLg, null);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Produit ajouté au panier"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : ajout ligne  Echouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
            
        }
    }
    public void majPrixCommande(Integer id_commande) throws InstantiationException, SQLException, IllegalAccessException{
        
        String requeteMajCommande = "UPDATE ecommerce.commande SET prix_total="+Float.toString(nouveauPrixTotal(id_commande))+" WHERE id_commande="+Integer.toString(id_commande);
        ConnectBDD c = new ConnectBDD();
         try {
                c.executeRequete(requeteMajCommande, null);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Mise à jour du prix total du panier"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : Mise à jour du panier échouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
        c.closeConnect();
    }
    
    public float nouveauPrixTotal(Integer id_commande) throws InstantiationException, SQLException, IllegalAccessException{
        String requeteTest = "SELECT ligne_commande.quantite,produit.prix_unitaire FROM  ecommerce.ligne_commande Inner"
                +"join ecommerce.produit on ligne_commande.id_produit= produit.id_produit WHERE ligne_commande.id_commande='"+id_commande+"'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requeteTest, null);
        ResultSet res = c.getResultat();
        Float prix_final=0f;
        while (res.next()) {
            prix_final =prix_final+res.getInt(1)*res.getInt(2);
        }
        c.closeConnect();
        return prix_final;
    }
    public boolean commandeExist(String login) throws InstantiationException, IllegalAccessException, SQLException {
        String requestTest = "SELECT count(*) FROM ecommerce.commande WHERE login='" + login + "'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) >= 1);
        }
        c.closeConnect();
        return false;
    }

    public Integer idCommandExist(String login) throws InstantiationException, IllegalAccessException, SQLException {
        String requestTest = "SELECT id_commande FROM ecommerce.commande WHERE login='" + login + "' AND statut='progress'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1));
        }
        c.closeConnect();
        return -1;
    }

    public boolean sameLigne(Integer id_prod) throws InstantiationException, IllegalAccessException, SQLException {
        String requestTest = "SELECT count(*) FROM ecommerce.ligne_commande WHERE id_produit=" + id_prod + " AND id_commande=" + id_commande ;
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) >= 1);
        }
        c.closeConnect();
        return false;
    }

    public Integer idLinesameLigne(Integer id_prod) throws InstantiationException, IllegalAccessException, SQLException {
        String requestTest = "SELECT id_ligne_commande FROM ecommerce.ligne_commande WHERE id_produit=" + id_prod + " AND id_commande=" + id_commande ;
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1));
        }
        c.closeConnect();
        return -1;
    }

    public Integer quantitePredSameLine() throws InstantiationException, IllegalAccessException, SQLException {
        String requestTest = "SELECT quantite FROM ecommerce.ligne_commande WHERE id_produit='" + id_produit + "' AND id_commande='" + id_commande + "'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1));
        }
        c.closeConnect();
        return -1;
    }

}
