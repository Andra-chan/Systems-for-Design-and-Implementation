package project.repository.file;



import project.model.Flight;
import project.model.validators.Validator;

import java.time.LocalDateTime;
import java.util.List;

public class FlightFile extends AbstractFileRepository<Integer, Flight> {

    public FlightFile(String fileName, Validator<Flight> validator) {
        super(validator, fileName);
    }

    /**
     * extract entity override for flight  - template method design pattern
     * creates an entity of type Flight having a specified list of @code attributes
     *
     * @param attributes
     * @return an entity of type Flight
     */
    @Override
    public Flight extractEntity(List<String> attributes) {
        Flight flight = new Flight(attributes.get(1), attributes.get(2), LocalDateTime.parse(attributes.get(3)), Integer.parseInt(attributes.get(4)), Integer.parseInt(attributes.get(5)));
        flight.setId(Integer.parseInt(attributes.get(0)));

        return flight;
    }

    @Override
    protected String createEntityAsString(Flight entity) {
        return entity.getId() + ";" + entity.getDestination() + ";" + entity.getAirport() + ";" + entity.getDateTime() + ";" + entity.getTotalSeats() + ";" + entity.getRemainingSeats();
    }

}
