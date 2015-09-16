import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Bibi
 */
public class connexionBDD {
    
    private String nomToto;
    private String nomTata;
    
    
    
    public void test_bdd(){
        String messages ="";
        
        try {
            messages=messages+"Chargement du driver...\n";
            Class.forName( "com.mysql.jdbc.Driver" );
            messages=messages+"Driver chargé !\n";
        } catch ( ClassNotFoundException e ) {
            messages=messages+"Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! \n"
                    + e.getMessage() +"\n";
        }
        
        String url = "localhost";
        String utilisateur = "root";
        String motDePasse = "OST";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            messages=messages+"Connexion à la base de données...\n";
            connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
            messages=messages+"Connexion réussie !\n";
            
            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();
            messages=messages+"Objet requête créé !\n";

            /* --> Exécution d'une requête de lecture */
                /* BIEN SUR, ON PEUT METTRE NOS VARIABLES DANS LA REQUETE (+) */
                resultat = statement.executeQuery( "SELECT id FROM personne;" );
                messages=messages+"Requête \"SELECT id FROM personne;\" effectuée !\n";
            
 
                /* Récupération des données du résultat de la requête de lecture */
                while ( resultat.next() ) {
                    int idPersonne = resultat.getInt( "id" );
                    /* Formatage des données pour affichage*/
                    messages=messages+"Données retournées par la requête : id = " + idPersonne + ".\n";
                }
                
            /* --> OU Exécution d'une requête de modification */
                /* Exécution d'une requête d'écriture */
                int statut = statement.executeUpdate( "INSERT INTO personne (login,nom,prenom,adresse,cdp,ville,) VALUES ('jmarc@mail.fr', MD5('lavieestbelle78'), 'jean-marc', NOW());" );

                /* Formatage pour affichage dans la JSP finale. */
                messages=messages+"Résultat de la requête d'insertion : " + statut + ".\n";
                /* statut : 1 = OK / 0 = ERROR */
                
            /* --> OU Exemple de requête préparée */
                /* ? remplacé plus tard ;) */
                /* Création de l'objet gérant les requêtes préparées */
                    PreparedStatement preparedStatement = connexion.prepareStatement( "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES(?, MD5(?), ?, NOW());" );
                    messages=messages+"Requête préparée créée !\n";

                /* Récupération des paramètres d'URL saisis par l'utilisateur */
                String paramEmail = "bite@youhou.com";
                String paramMotDePasse = "beute";
                String paramNom = "Charlot";

                /*
                 * Remplissage des paramètres de la requête grâce aux méthodes
                 * setXXX() mises à disposition par l'objet PreparedStatement.
                 * Remplacement des 
                 */
                preparedStatement.setString( 1, paramEmail );
                preparedStatement.setString( 2, paramMotDePasse );
                preparedStatement.setString( 3, paramNom );

                /* Exécution de la requête */
                statut = preparedStatement.executeUpdate();

                /* Formatage pour affichage dans la JSP finale. */
                messages=messages+"Résultat de la requête d'insertion préparée : " + statut + ".\n";
                
            /* FIN DIFFERENT EXEMPLE */
               
        } catch ( SQLException e ) {
            messages=messages+"Erreur lors de la connexion : "
                + e.getMessage()+"\n";
        } finally {
            messages=messages+"Fermeture de l'objet ResultSet.\n";
            if ( resultat != null ) {
                try {
                    resultat.close();
                } catch ( SQLException ignore ) {
                }
            }
            messages=messages+"Fermeture de l'objet Statement.\n";
            if ( statement != null ) {
                try {
                    statement.close();
                } catch ( SQLException ignore ) {
                }
            }
            messages=messages+"Fermeture de l'objet Connection.\n";
            if ( connexion != null ) {
                try {
                    connexion.close();
                } catch ( SQLException ignore ) {
                }
            }
        }
        System.out.println(messages);
    }
}