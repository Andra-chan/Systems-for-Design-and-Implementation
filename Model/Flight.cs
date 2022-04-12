using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    [Serializable]
    public class Flight : HasId<int>
    {
        public int Id { get; set; }
        public string Destination { get; set; }
        public string Airport { get; set; }
        public DateTime Date_Time { get; set; }
        public int TotalSeats { get; set; }
        public int RemainingSeats { get; set; }

        public Flight(string destination, string airport, DateTime dateTime, int totalSeats, int remainingSeats)
        {
            this.Destination = destination;
            this.Airport = airport;
            this.Date_Time = dateTime;
            this.TotalSeats = totalSeats;
            this.RemainingSeats = remainingSeats;
        }
        protected bool Equals(Flight other)
        {
            return Id == other.Id || (Destination == other.Destination && Date_Time.Equals(other.Date_Time));
        }

        public override int GetHashCode()
        {
            unchecked
            {
#pragma warning disable CS0472 // The result of the expression is always the same since a value of this type is never equal to 'null'
				var hashCode = Id != null ? Id.GetHashCode() : 0;
#pragma warning restore CS0472 // The result of the expression is always the same since a value of this type is never equal to 'null'
				hashCode = (hashCode * 397) ^ (Destination != null ? Destination.GetHashCode() : 0);
                hashCode = (hashCode * 397) ^ Date_Time.GetHashCode();
                return hashCode;
            }
        }

        public override string ToString()
        {
            return Destination + " " + Airport + " " + Date_Time + " " + TotalSeats;
        }

    }
}

