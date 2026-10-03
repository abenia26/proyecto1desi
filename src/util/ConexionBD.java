package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase para conectarse a la base de datos MySQL. Los datos de conexión se
 * leen del archivo {@code .env} del proyecto.
 *
 * @author Hector Abenia
 */
public class ConexionBD {

    /**
     * Abre una conexión nueva con la base de datos usando las variables
     * {@code DB_URL}, {@code DB_USER} y {@code DB_PASS} del archivo
     * {@code .env}.
     *
     * @return la conexión abierta con la base de datos
     * @throws SQLException si no se puede conectar con la base de datos
     */
    public static Connection conectar() throws SQLException {
        Dotenv env = Dotenv.load();
        String url = env.get("DB_URL");
        String user = env.get("DB_USER");
        String pass = env.get("DB_PASS");

        return DriverManager.getConnection(url, user, pass);
    }
}