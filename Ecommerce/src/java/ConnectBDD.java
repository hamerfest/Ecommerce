
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class ConnectBDD {

    private Connection myConnexion;
    private Statement myStatement;
    private final String url = "jdbc:mysql://localhost/ecommerce";
    private final String user = "root";
    private final String pwd = "";
    private PreparedStatement preparedStatement;
    private ResultSet resultat = null;

    public ConnectBDD() throws InstantiationException, IllegalAccessException {
        if (testDriver()) {
            try {
                myConnexion = DriverManager.getConnection(url, user, pwd);
                myStatement = myConnexion.createStatement();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la connexion :" + ex.getMessage() + "\n");
            }
        } else {
            System.out.println("Echec de la connexion");
        }

    }

    public boolean testDriver() throws InstantiationException, IllegalAccessException {
        //   Chargement du driver JDBC pour MySQL */
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver n'a pas été trouvé dans le classpath: " + e.getMessage() + ". Le driver n'a pas été chargé.\n");
            return false;
        }
    }

    public ResultSet getResultat() {
        return resultat;
    }

    public Connection getMyConnexion() {
        return myConnexion;
    }

    public Statement getMyStatement() {
        return myStatement;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public PreparedStatement preparePreparedStatement(String requete, String[] values) {
        try {
            PreparedStatement sqlrequete = myConnexion.prepareStatement(requete);
            for (int i = 0; i < values.length; i++) {
                sqlrequete.setString(i + 1, values[i]);
            }
            this.preparedStatement = sqlrequete;
            return sqlrequete;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la préparation de la requête " + requete);
        }

        return null;
    }

    public void executeRequete(String requete, String[] values) throws SQLException {
        String typeRequete = requete.substring(0,6);
        System.out.println("Type de requete :"+typeRequete);
        // TODO executeUpdate ==> retourne le résultat
        //executeQuery==> Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement or an SQL statement that returns nothing, such as an SQL DDL statement.
        int size = 0;
        if ("SELECT".equals(typeRequete.toUpperCase())) {
            System.out.println("Traitement requete SELECT");
            try {
                this.resultat = myStatement.executeQuery(requete);
                if (resultat != null) {
                    resultat.last();
                    size = resultat.getRow();
                    resultat.beforeFirst();
                }
                System.out.println("Requete executée : selection de " + size + " résultats");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'execution de la requete : " + e.getMessage());
            }
        } else { /*UPDATE ou INSERT*/
            System.out.println("Traitement requete UPDATE or INSERT");
            PreparedStatement sqlrequete = preparePreparedStatement(requete, values);
            try {
                size = preparedStatement.executeUpdate();
                System.out.println("Requete executée : " + size + " action(s)");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'execution de la requete : " + e.getMessage());
                System.out.println(sqlrequete);
            }
        }
    }

    public void closeConnect() {
        if (resultat != null) {
            try {
                resultat.close();
            } catch (SQLException ignore) {
            }
        }
        if (myStatement != null) {
            try {
                myStatement.close();
            } catch (SQLException ignore) {
            }
        }
        if (myConnexion != null) {
            try {
                myConnexion.close();
            } catch (SQLException ignore) {
                System.out.println("Erreur a la fermeture de la connexion : " + ignore.getMessage());
            }
        }
    }
}
