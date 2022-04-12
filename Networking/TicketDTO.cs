using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Networking
{
    [Serializable]
    public class TicketDTO
    {
        public int Id { get; set; }

        public int FlightId { get; set; }
        public string Name { get; set; }
        public string Tourists { get; set; }
        public string Address { get; set; }
        public int Seats { get; set; }

        public TicketDTO(int flightId, string name, string tourists, string address, int seats)
        {
            this.FlightId = flightId;
            this.Name = name;
            this.Tourists = tourists;
            this.Address = address;
            this.Seats = seats;
        }
    }
}
