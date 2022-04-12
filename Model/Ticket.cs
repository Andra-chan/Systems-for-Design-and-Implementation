using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    [Serializable]
    public class Ticket : HasId<int>
    {
        public int Id { get; set; }

        public int FlightId { get; set; }
        public string Name { get; set; }
        public string Tourists { get; set; }
        public string Address { get; set; }
        public int Seats { get; set; }

        public Ticket(int flightId, string name, string tourists, string address, int seats)
        {
            this.FlightId = flightId;
            this.Name = name;
            this.Tourists = tourists;
            this.Address = address;
            this.Seats = seats;
        }

        protected bool Equals(Ticket other)
        {
            return Id == other.Id || (Name == other.Name && Tourists.Equals(other.Tourists));
        }

        public override int GetHashCode()
        {
            unchecked
            {
                var hashCode = Id != 0 ? Id.GetHashCode() : 0;
                hashCode = (hashCode * 397) ^ (Name != null ? Name.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ Tourists.GetHashCode();
                return hashCode;
            }
        }

        public override string ToString()
        {
            return Name + " " + Tourists + " " + Address + " " + Seats;
        }
    }
}
