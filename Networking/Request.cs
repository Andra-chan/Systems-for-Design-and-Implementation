using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;

namespace Networking
{
    public interface Request
    {
    }

    [Serializable]
    public class LoginRequest : Request
    {
        private User user;

        public LoginRequest(User user)
        {
            this.user = user;
        }

        public virtual User User
        {
            get { return user; }
        }
    }

    [Serializable]
    public class LogoutRequest : Request
    {
        private User user;

        public LogoutRequest(User user)
        {
            this.user = user;
        }

        public virtual User User
        {
            get { return user; }
        }
    }

    [Serializable]
    public class GetAllFlightsRequest : Request
    {
        public GetAllFlightsRequest()
        {
        }
    }

    [Serializable]
    public class GetFlightsByAirportRequest : Request
    {     
        private String destination;
        private String airport;

        public GetFlightsByAirportRequest(String destination, String airport)
        {
            this.destination = destination;
            this.airport = airport;
        }

        public virtual String Destination
        {
            get { return destination; }
        }
        public virtual String Airport
        {
            get { return airport; }
        }

    }

    [Serializable]
    public class BuyTicketsRequest : Request
    {
        private TicketDTO ticket;

        public BuyTicketsRequest(TicketDTO ticket)
        {
            this.ticket = ticket;
        }

        public virtual TicketDTO TicketDto
        {
            get { return ticket; }
        }
    }

}
