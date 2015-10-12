/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class Personne implements Serializable{
    private String id_personne;
    private String nom;
    private String prenom;
    private String adresse;
    private String cdp;
    private String ville;
    private String login;
    private String mdp;
    private String fonction;
    
    /*
     Save Personne into dataBase
    Par defaut fonction = "Client"
     */
    public void savePersonne() throws InstantiationException, IllegalAccessException {
        String[] paramInsert ={this.login,this.mdp,this.nom,this.prenom,this.adresse,this.cdp,this.ville,"CLIENT"};
        String requeteInsert = "INSERT INTO ecommerce.personne"
                    + "(id_personne,login,mdp,nom, prenom,adresse,cdp, ville, fonction)"
                    + "VALUES (NULL,?,?,?,?,?,?,?,?)";
        ConnectBDD b = new ConnectBDD();
        try {
            b.executeRequete(requeteInsert,paramInsert);
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
            System.out.println(e.getMessage());
        }
        b.closeConnect();

    }
    public List<Personne> getListPersonne() throws SQLException, InstantiationException, IllegalAccessException {
        String[] param = null;
        String requeteSelect = "SELECT * FROM  ecommerce.personne";
        ConnectBDD b =new ConnectBDD();
        List<Personne> list = new ArrayList<Personne>();
        b.executeRequete(requeteSelect,param);
        ResultSet res = b.getResultat();
        while (res.next()){
            Personne pers = new Personne();
            pers.setNom(res.getString("id_personne"));
            pers.setLogin(res.getString("login"));
            pers.setMdp(res.getString("mdp"));
            pers.setNom(res.getString("nom"));
            pers.setPrenom(res.getString("prenom"));
            pers.setAdresse(res.getString("adresse"));
            pers.setCdp(res.getString("cdp"));
            pers.setVille(res.getString("ville"));
            pers.setFonction(res.getString("fonction"));
            list.add(pers);
        }
        return list;
    }

    public String getId_personne() {
        return id_personne;
    }

    public void setId_personne(String id_personne) {
        this.id_personne = id_personne;
    }
    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCdp() {
        return cdp;
    }

    public String getVille() {
        return ville;
    }

    public String getLogin() {
        return login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCdp(String cdp) {
        this.cdp = cdp;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }    
}
