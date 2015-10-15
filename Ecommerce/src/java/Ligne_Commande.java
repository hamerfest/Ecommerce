
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Djo
 */
@ManagedBean
public class Ligne_Commande {

    private Integer id_lg_commande;
    private Integer quantite;
    private Integer id_commande;
    private Integer id_produit;

    public void addLgCommande(String newClassLogin, Integer id_produit, Integer quantité) throws InstantiationException, IllegalAccessException, SQLException {
        if (commandeExist(newClassLogin)){
            id_commande = idCommandExist(newClassLogin);
            id_lg_commande =numsameLigne(id_produit);
            /*UPDATE de la ligne */
            
            
        }
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
        String requestTest = "SELECT id_commande FROM ecommerce.commande WHERE login='" + login + "' AND statut=progress";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1));
        }
        c.closeConnect();
        return -1;
    }
    
    public boolean sameLigne(Integer id_prod) throws InstantiationException, IllegalAccessException, SQLException{
        String requestTest = "SELECT count(*) FROM ecommerce.lg_commande WHERE id_produit='" + id_prod + "' AND id_commande='"+id_commande+"'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requestTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) >= 1);
        }
        c.closeConnect();
        return false;
    }
    
     public Integer numsameLigne(Integer id_prod) throws InstantiationException, IllegalAccessException, SQLException{
        String requestTest = "SELECT id_lg_commande FROM ecommerce.lg_commande WHERE id_produit='" + id_prod + "' AND id_commande='"+id_commande+"'";
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
