
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Djo
 */
public class TestConnectBDD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
        ConnectBDD b= new ConnectBDD(); 
        String requete = "SELECT * FROM PERSONNE";
        b.executeRequete(requete, null);
        ResultSet resultat = b.getResultat();
        while(resultat.next()){
        System.out.println("id ="+resultat.getInt("id_personne")+" nom : "+resultat.getString("nom")+" fonction :"+resultat.getString("fonction"));
        }
    }
    
}
