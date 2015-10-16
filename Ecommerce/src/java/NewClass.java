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
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import org.primefaces.context.RequestContext;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
@ManagedBean
public class NewClass implements Serializable {

    private Integer id_personne;
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

    public void savePersonne() throws InstantiationException, IllegalAccessException, SQLException {
        if (!testBDDLogin()) {
            String[] paramInsert = {this.login, this.mdp, this.nom, this.prenom, this.adresse, this.cdp, this.ville, "CLIENT"};
            String requeteInsert = "INSERT INTO ecommerce.personne"
                    + "(id_personne,login,mdp,nom, prenom,adresse,cdp, ville,fonction)"
                    + "VALUES (NULL,?,?,?,?,?,?,?,?)";
            ConnectBDD b = new ConnectBDD();
            try {
                b.executeRequete(requeteInsert, paramInsert);
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sauvegarde Réussie"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "System Error : Sauvegarde Echouée"));
                System.out.println("Erreur lors de la sauvegarde");
                System.out.println(e.getMessage());
            }
            b.closeConnect();
        } else {
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Le login choisi est déjà utilisé"));
            System.out.println("Erreur login en doublon");
            FacesContext.getCurrentInstance().addMessage("login", new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Veuillez en inscrire un autre login"));
        }
    }

    public List<Personne> getListPersonne() throws SQLException, InstantiationException, IllegalAccessException {
        String[] param = null;
        String requeteSelect = "SELECT * FROM  ecommerce.personne";
        ConnectBDD b = new ConnectBDD();
        List<Personne> list = new ArrayList<>();
        b.executeRequete(requeteSelect, param);
        ResultSet res = b.getResultat();
        while (res.next()) {
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
            list.add(pers);
        }
        b.closeConnect();
        return list;
    }

    public Integer getId_personne() {
        return id_personne;
    }

    public void setId_personne(Integer id_personne) {
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

    /* test if login exist in BDD
     true ==> exist else false*/
    public boolean testBDDLogin() throws InstantiationException, IllegalAccessException, SQLException {
        String requeteTest = ""
                + "SELECT count(*) FROM  ecommerce.personne WHERE login='" + login + "'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requeteTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) >= 1);
        }
        return false;
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

    public NewClass getPersonne() throws SQLException, InstantiationException, IllegalAccessException {
        String[] param = null;
        String requeteSelect = "SELECT * FROM  ecommerce.personne WHERE login='" + login + "' AND mdp='" + mdp + "'";
        ConnectBDD b = new ConnectBDD();
        b.executeRequete(requeteSelect, param);
        ResultSet res = b.getResultat();
        NewClass pers = new NewClass();
        while (res.next()) {
            pers.setId_personne(1);
            pers.setLogin(res.getString("login"));
            pers.setMdp(res.getString("mdp"));
            pers.setNom(res.getString("nom"));
            pers.setPrenom(res.getString("prenom"));
            pers.setAdresse(res.getString("adresse"));
            pers.setCdp(res.getString("cdp"));
            pers.setVille(res.getString("ville"));
            pers.setFonction(res.getString("fonction"));
        }
        b.closeConnect();
        return pers;
    }

    public boolean testConnexion() throws InstantiationException, IllegalAccessException, SQLException {
        String requeteTest = ""
                + "SELECT count(*) FROM  ecommerce.personne WHERE login='" + login + "' AND mdp='" + mdp + "'";
        ConnectBDD c = new ConnectBDD();
        c.executeRequete(requeteTest, null);
        ResultSet res = c.getResultat();
        while (res.next()) {
            return (res.getInt(1) == 1);
        }

        return false;
    }

    public void connexion() throws SQLException, InstantiationException, IllegalAccessException {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;

        if (getPersonne() != null) {
            nom = getPersonne().getNom();
            prenom = getPersonne().getPrenom();
            fonction = getPersonne().getFonction();
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", getPersonne().getLogin());
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }

        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }

    public void Modif(String login) throws SQLException, InstantiationException, IllegalAccessException {
        FacesMessage message = null;
        String[] param = {mdp, login};
        String requeteSelect = "UPDATE personne SET mdp=? WHERE login =?";
        ConnectBDD b = new ConnectBDD();
        b.executeRequete(requeteSelect, param);
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Félicitations !", "Votre mot de passe a été modifié:"+ mdp);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void deco() {
        this.nom = "";
        this.prenom = "";
        this.fonction = "";
        this.login = "";
        this.mdp = "";
    }

}
