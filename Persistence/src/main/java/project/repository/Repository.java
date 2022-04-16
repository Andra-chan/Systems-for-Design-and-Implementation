package project.repository;



import project.model.Entity;
import project.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository Interface for CRUD operations
 *
 * @param <ID> a type E must have an attribute of type ID
 * @param <E>  type of Entity saved in the application.repository
 */

public interface Repository<ID, E extends Entity<ID>> {

    /**
     * Returns entity with the given id
     *
     * @param id specific attribute of an entity
     * @return an entity with the specified id, null otherwise
     * @throws IllegalAccessException if id is null
     */
    E findOne(ID id);

    /**
     * @return all entities
     */
    Iterable<E> findAll();

    /**
     * Validates and saves an entity into the application.repository
     *
     * @param entity to be saved
     * @return the entity if it already exists, null otherwise
     * @throws RepositoryException                       if the given entity is null
     * @throws app.domain.validators.ValidationException if the entity is not valid
     */
    E save(E entity);

    /**
     * Removes an entity from the application.repository
     *
     * @param id entity to be removed at the specified id
     * @return the entity that was removed, null otherwise
     * @throws RepositoryException if the id is null
     */
    E delete(ID id);

    /**
     * Updates an entity
     *
     * @param entity to be updated
     * @return null if the entity has been updated, otherwise it returns the entity(e.g. id does not exist)
     * @throws RepositoryException                       if the entity does not exist
     * @throws app.domain.validators.ValidationException if the given entity is not valid
     */
    E update(E entity);

    E findOneLogin(String email, String password);

    List<Flight> findBySearch(String searchDestination, LocalDateTime searchDateTime);

    List<Flight> findBySearchAirport(String searchDestination, String searchAirport);
}

