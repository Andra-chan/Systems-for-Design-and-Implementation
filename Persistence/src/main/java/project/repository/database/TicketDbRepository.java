package project.repository.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Flight;
import project.model.Ticket;
import project.repository.ITicketRepository;
import project.repository.JdbcUtils;
import project.repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketDbRepository implements ITicketRepository {

    private static final Logger logger = LogManager.getLogger();
    private JdbcUtils dbUtils;

    public TicketDbRepository(Properties props) {
        logger.info("Initializing TicketDbRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public Ticket save(Ticket elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Tickets(flight_id, name, tourists, address, seats) values (?,?,?,?,?)")) {

            preparedStatement.setInt(1, elem.getFlightId());
            preparedStatement.setString(2, elem.getName());
            preparedStatement.setString(3, elem.getTourists());
            preparedStatement.setString(4, elem.getAddress());
            preparedStatement.setInt(5, elem.getSeats());

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
    public Ticket update(Ticket elem) {
        logger.traceEntry("updating task {}", elem);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("update Tickets set flight_id = ?, name = ?, toursits = ?, address = ?, seats = ? where id = ?")) {

            preparedStatement.setInt(1, elem.getFlightId());
            preparedStatement.setString(2, elem.getName());
            preparedStatement.setString(3, elem.getTourists());
            preparedStatement.setString(4, elem.getAddress());
            preparedStatement.setInt(5, elem.getSeats());
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
    public Ticket delete(Integer id) {
        if (id == null)
            throw new RepositoryException("ID must not be null!");

        logger.traceEntry("deleting task {}", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("delete from Tickets where id = ?")) {
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
    public Ticket findOne(Integer id) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Tickets where id=?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Integer flightId = resultSet.getInt("flight_id");
                    String name = resultSet.getString("name");
                    String tourists = resultSet.getString("tourists");
                    String address = resultSet.getString("address");
                    Integer seats = resultSet.getInt("seats");

                    Ticket ticket = new Ticket(flightId, name, tourists, address, seats);
                    ticket.setId(id);
                    return ticket;
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
    public Iterable<Ticket> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Tickets")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    Integer id = resultSet.getInt("id");
                    Integer flightId = resultSet.getInt("flight_id");
                    String name = resultSet.getString("name");
                    String tourists = resultSet.getString("tourists");
                    String address = resultSet.getString("address");
                    Integer seats = resultSet.getInt("seats");

                    Ticket ticket = new Ticket(flightId, name, tourists, address, seats);
                    ticket.setId(id);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
        return tickets;
    }

    @Override
    public Ticket findOneLogin(String email, String password) {
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