package project.network.objectprotocol;

import project.model.TicketDTO;

public class SellTicketResponse implements Response{
    private TicketDTO ticketDTO;

    public SellTicketResponse(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }

    public void setTicketDTO(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }
}
