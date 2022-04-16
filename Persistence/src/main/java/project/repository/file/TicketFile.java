package project.repository.file;




import project.model.Ticket;
import project.model.validators.Validator;

import java.util.List;

public class TicketFile extends AbstractFileRepository<Integer, Ticket> {

    public TicketFile(String fileName, Validator<Ticket> validator) {
        super(validator, fileName);
    }

    /**
     * extract entity override for ticket  - template method design pattern
     * creates an entity of type Ticket having a specified list of @code attributes
     *
     * @param attributes
     * @return an entity of type Ticket
     */
    @Override
    public Ticket extractEntity(List<String> attributes) {
        Ticket ticket = new Ticket(Integer.parseInt(attributes.get(1)), attributes.get(2), attributes.get(3), attributes.get(4), Integer.parseInt(attributes.get(5)));
        ticket.setId(Integer.parseInt(attributes.get(0)));

        return ticket;
    }

    @Override
    protected String createEntityAsString(Ticket entity) {
        return entity.getId() + ";" + entity.getName() + ";" + entity.getAddress() + ";" + entity.getSeats();
    }
}
