package project.repository.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Flight;
import project.repository.IFlightRepository;
import project.repository.JdbcUtils;
import project.repository.RepositoryException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class FlightDbRepository implements IFlightRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public FlightDbRepository(Properties props) {
        logger.info("Initializing FlightDbRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Flight save(Flight elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Flights(destination, airport, date_time, total_seats, remaining_seats) values (?,?,?,?,?)")) {
            preparedStatement.setString(1, elem.getDestination());
            preparedStatement.setString(2, elem.getAirport());
            preparedStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(elem.getDateTime())));
            preparedStatement.setInt(4, elem.getTotalSeats());
            preparedStatement.setInt(5, elem.getRemainingSeats());
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
    public Flight update(Flight elem) {
        logger.traceEntry("updating task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("update Flights set destination = ?, airport = ?, date_time = ?, total_seats = ?, remaining_seats = ? where id = ?")) {
            preparedStatement.setString(1, elem.getDestination());
            preparedStatement.setString(2, elem.getAirport());
            preparedStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(elem.getDateTime())));
            preparedStatement.setInt(4, elem.getTotalSeats());
            preparedStatement.setInt(5, elem.getRemainingSeats());
            preparedStatement.setInt(6, elem.getId());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return elem;
    }

    @Override
    public Flight delete(Integer id) {
        if (id == null)
            throw new RepositoryException("ID must not be null!");

        logger.traceEntry("deleting task {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("delete from Flights where id = ?")) {
            preparedStatement.setInt(1, id);
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
    public Flight findOne(Integer id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Flights where id=?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String destination = resultSet.getString("destination");
                    String airport = resultSet.getString("airport");
                    LocalDateTime date_time = Timestamp.valueOf(resultSet.getString("date_time")).toLocalDateTime();
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer remainingSeats = resultSet.getInt("remaining_seats");
                    Flight flight = new Flight(destination, airport, date_time, totalSeats, remainingSeats);
                    flight.setId(id);
                    return flight;
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
    public Iterable<Flight> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Flights")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String destination = resultSet.getString("destination");
                    String airport = resultSet.getString("airport");
                    LocalDateTime date_time = Timestamp.valueOf(resultSet.getString("date_time")).toLocalDateTime();
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer remainingSeats = resultSet.getInt("remaining_seats");
                    Flight flight = new Flight(destination, airport, date_time, totalSeats, remainingSeats);
                    flight.setId(id);
                    flights.add(flight);
                }
            }

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return flights;
    }

    @Override
    public List<Flight> findBySearch(String searchDestination, LocalDateTime searchDateTime) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Flights where destination = ? and date_time = ?")) {
            preparedStatement.setString(1, searchDestination);
            preparedStatement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(searchDateTime)));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String destination = resultSet.getString("destination");
                    String airport = resultSet.getString("airport");
                    LocalDateTime date_time = Timestamp.valueOf(resultSet.getString("date_time")).toLocalDateTime();
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer remainingSeats = resultSet.getInt("remaining_seats");
                    Flight flight = new Flight(destination, airport, date_time, totalSeats, remainingSeats);
                    flight.setId(id);
                    flights.add(flight);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return flights;
    }

    @Override
    public List<Flight> findBySearchAirport(String searchDestination, String searchAirport) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Flight> flights = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Flights where destination = ? and airport = ?")) {
            preparedStatement.setString(1, searchDestination);
            preparedStatement.setString(2, searchAirport);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String destination = resultSet.getString("destination");
                    String airport = resultSet.getString("airport");
                    LocalDateTime date_time = Timestamp.valueOf(resultSet.getString("date_time")).toLocalDateTime();
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer remainingSeats = resultSet.getInt("remaining_seats");
                    Flight flight = new Flight(destination, airport, date_time, totalSeats, remainingSeats);
                    flight.setId(id);
                    flights.add(flight);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return flights;
    }

    @Override
    public Flight findOneLogin(String email, String password) {
        return null;
    }
}
