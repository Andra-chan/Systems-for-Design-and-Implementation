package project.repository.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Flight;
import project.model.User;
import project.repository.IUserRepository;
import project.repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDbRepository implements IUserRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public UserDbRepository(Properties props) {
        logger.info("Initializing UserDbRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public User save(User elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Users(first_name, last_name, email, pass) values (?, ?, ?,?)")) {

            preparedStatement.setString(1, elem.getFirstName());
            preparedStatement.setString(2, elem.getLastName());
            preparedStatement.setString(3, elem.getEmail());
            preparedStatement.setString(4, elem.getPassword());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public User findOne(Integer id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        String sql = "SELECT id, first_name, last_name, email, pass from Users WHERE id = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User toReturn = new User(resultSet.getString("first_name"), resultSet.getString("last_name"),
                            resultSet.getString("email"), resultSet.getString("pass"));
                    toReturn.setId(resultSet.getInt("id"));
                    return toReturn;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public User findOneLogin(String email, String password) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        String sql = "SELECT * from Users where email = ? and pass = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User toReturn = new User(resultSet.getString("first_name"), resultSet.getString("last_name"),
                            resultSet.getString("email"), resultSet.getString("pass"));
                    toReturn.setId(resultSet.getInt("id"));
                    return toReturn;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return null;
    }


    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Users")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(firstName, lastName, "", "");
                    user.setId(id);
                    users.add(user);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return users;
    }

    @Override
    public User delete(Integer id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public List<Flight> findBySearch(String searchDestination, LocalDateTime searchDateTime) {
        return null;
    }

    @Override
    public List<Flight> findBySearchAirport(String searchDestination, String searchAirport) {
        return null;
    }
}
