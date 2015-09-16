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

    public void test_bdd() {
        String messages = "";

        try {
            messages = messages + "Chargement du driver...\n";
            Class.forName("com.mysql.jdbc.Driver");
            messages = messages + "Driver chargé !\n";
        } catch (ClassNotFoundException e) {
            messages = messages + "Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! \n"
                    + e.getMessage() + "\n";
        }

        String url = "localhost";
        String utilisateur = "root";
        String motDePasse = "OST";
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;
        try {
            messages = messages + "Connexion à la base de données...\n";
            connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            messages = messages + "Connexion réussie !\n";

            /* Création de l'objet gérant les requêtes */
            statement = connexion.createStatement();
            messages = messages + "Objet requête créé !\n";

            /* --> Exécution d'une requête de lecture */
            /* BIEN SUR, ON PEUT METTRE NOS VARIABLES DANS LA REQUETE (+) */
            resultat = statement.executeQuery("SELECT id FROM personne;");
            messages = messages + "Requête \"SELECT id FROM personne;\" effectuée !\n";

            /* Récupération des données du résultat de la requête de lecture */
            while (resultat.next()) {
                int idPersonne = resultat.getInt("id");
                /* Formatage des données pour affichage*/
                messages = messages + "Données retournées par la requête : id = " + idPersonne + ".\n";
            }

            /* --> OU Exécution d'une requête de modification */
            /* Exécution d'une requête d'écriture */
            int statut = statement.executeUpdate("INSERT INTO personne (login,mdp,nom,prenom,adresse,cdp,ville,fonction) VALUES ('admin','admin','admin','admin','mon adresse de admin','86000','youpi','administrateur');");

            /* Formatage pour affichage dans la JSP finale. */
            messages = messages + "Résultat de la requête d'insertion : " + statut + ".\n";
            /* statut : 1 = OK / 0 = ERROR */

            /* --> OU Exemple de requête préparée */
            /* ? remplacé plus tard ;) */
            /* Création de l'objet gérant les requêtes préparées */
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO personne (login,mdp,nom,prenom,adresse,cdp,ville,fonction) VALUES(?,?,?,?,?,?,?,?);");
            messages = messages + "Requête préparée créée !\n";

            /* Récupération des paramètres d'URL saisis par l'utilisateur */
            String paramLogin = "toto";
            String paramMDP = "toto";
            String paramNom = "Charlot";
            String paramPrenom = "Charlot";
            String paramAdresse = "Mon palace";
            String paramCDP = "86001";
            String paramVille = "Mon bled";
            String paramFonction = "comptable";

            /*
             * Remplissage des paramètres de la requête grâce aux méthodes
             * setXXX() mises à disposition par l'objet PreparedStatement.
             * Remplacement des 
             */
            preparedStatement.setString(1, paramLogin);
            preparedStatement.setString(2, paramMDP);
            preparedStatement.setString(3, paramNom);
            preparedStatement.setString(4, paramPrenom);
            preparedStatement.setString(5, paramAdresse);
            preparedStatement.setString(6, paramCDP);
            preparedStatement.setString(7, paramVille);
            preparedStatement.setString(8, paramFonction);

            /* Exécution de la requête */
            statut = preparedStatement.executeUpdate();

            /* Formatage pour affichage dans la JSP finale. */
            messages = messages + "Résultat de la requête d'insertion préparée : " + statut + ".\n";

            /* FIN DIFFERENT EXEMPLE */
        } catch (SQLException e) {
            messages = messages + "Erreur lors de la connexion : "
                    + e.getMessage() + "\n";
        } finally {
            messages = messages + "Fermeture de l'objet ResultSet.\n";
            if (resultat != null) {
                try {
                    resultat.close();
                } catch (SQLException ignore) {
                }
            }
            messages = messages + "Fermeture de l'objet Statement.\n";
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignore) {
                }
            }
            messages = messages + "Fermeture de l'objet Connection.\n";
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException ignore) {
                }
            }
        }
        System.out.println(messages);
    }
}
