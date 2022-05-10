package project.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class JdbcUtils {

    private static final Logger logger = LogManager.getLogger();
    private Properties jdbcProps;
    private Connection instance = null;

    public JdbcUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection getNewConnection() {
        logger.traceEntry();
        //String driver = jdbcProps.getProperty("project.jdbc.driver");
        String url = jdbcProps.getProperty("project.jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");
        logger.info("trying to connect to database ... {}", url);
        logger.info("user: {}", user);
        logger.info("pass: {}", pass);
        Connection con = null;

        try {
            //Class.forName(driver);
            //con = DriverManager.getConnection(url, user, pass);

            if (user != null && pass != null)
                con = DriverManager.getConnection(url, user, pass);
            else
                con = DriverManager.getConnection(url);

        }/* catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error getting driver " + e);
        }*/
        catch (SQLException e){
            System.out.println("Error getting connection " + e);
        }
        return con;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
