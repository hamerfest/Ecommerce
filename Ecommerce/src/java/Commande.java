
import java.sql.Date;
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
public class Commande {
    private Integer id_commande;
    private String statut;
    private Float prix_total;
    private Date date_commande;
    private Date date_livraison;
    private String login;
    
}
