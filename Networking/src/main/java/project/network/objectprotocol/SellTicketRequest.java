package project.network.objectprotocol;

import project.model.TicketDTO;

public class SellTicketRequest implements Request{
    private TicketDTO ticketDTO;

    public SellTicketRequest(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }

    public void setTicketDTO(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }
}
