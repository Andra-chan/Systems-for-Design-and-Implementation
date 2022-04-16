package project.network.dto;

import project.model.Ticket;
import project.model.TicketDTO;
import project.model.User;

public class DTOUtils {
    public static Ticket getFromDTO(TicketDTO ticketDTO){
        Integer id=ticketDTO.getFlightId();
        String name=ticketDTO.getName();
        String tourists = ticketDTO.getTourists();
        String address = ticketDTO.getAddress();
        Integer seats = ticketDTO.getSeats();
        return new Ticket(id, name, tourists, address, seats);

    }
}
