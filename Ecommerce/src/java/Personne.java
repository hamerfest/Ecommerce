/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
import javax.faces.bean.ManagedBean;
 
@ManagedBean
public class Personne {
    private String nom;
    private String prenom;
    private String adresse;
    private String cdp;
    private String ville;
    private String login;
    private String mdp; 

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

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    
}
