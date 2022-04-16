package project.repository;



import project.model.Flight;
import project.model.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface ITicketRepository extends Repository<Integer, Ticket> {
    /**
     * Returns entity with the given id
     *
     * @param id specific attribute of an entity
     * @return an entity with the specified id, null otherwise
     * @throws IllegalAccessException if id is null
     */
    Ticket findOne(Integer id);

    /**
     * @return all entities
     */
    Iterable<Ticket> findAll();

    /**
     * Validates and saves an entity into the application.repository
     *
     * @param entity to be saved
     * @return the entity if it already exists, null otherwise
     * @throws RepositoryException                       if the given entity is null
     * @throws app.domain.validators.ValidationException if the entity is not valid
     */
    Ticket save(Ticket entity);

    /**
     * Removes an entity from the application.repository
     *
     * @param id entity to be removed at the specified id
     * @return the entity that was removed, null otherwise
     * @throws RepositoryException if the id is null
     */
    Ticket delete(Integer id);

    /**
     * Updates an entity
     *
     * @param entity to be updated
     * @return null if the entity has been updated, otherwise it returns the entity(e.g. id does not exist)
     * @throws RepositoryException                       if the entity does not exist
     * @throws app.domain.validators.ValidationException if the given entity is not valid
     */
    Ticket update(Ticket entity);

    Ticket findOneLogin(String email, String password);

    List<Flight> findBySearch(String searchDestination, LocalDateTime searchDateTime);

    List<Flight> findBySearchAirport(String searchDestination, String searchAirport);
}
