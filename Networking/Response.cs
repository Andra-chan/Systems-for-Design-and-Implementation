using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Model;

namespace Networking
{
    public interface Response
    {
    }

    [Serializable]
    public class OkResponse : Response
    {
    }

    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public virtual string Message
        {
            get { return message; }
        }
    }

    [Serializable]
    public class GetAllFlightsResponse : Response
    {
        private IList<Flight> flights;

        public GetAllFlightsResponse(IList<Flight> flights)
        {
            this.flights = flights;
        }

        public virtual IList<Flight> Flights
        {
            get { return flights; }
        }
    }

    [Serializable]
    public class GetFlightsByAirportResponse : Response
    {
        private IList<Flight> flights;

        public GetFlightsByAirportResponse(IList<Flight> flights)
        {
            this.flights = flights;
        }

        public virtual IList<Flight> Flights
        {
            get { return flights; }
        }
    }

    public interface UpdateResponse : Response
    {
    }

    [Serializable]
    public class SoldTicketsResponse : UpdateResponse
    {
        private IList<Flight> flights;

        public SoldTicketsResponse(IList<Flight> flights)
        {
            this.flights = flights;
        }

        public virtual IList<Flight> Flights
        {
            get { return flights; }
        }
    }


}
