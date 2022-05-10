using System.Collections.Generic;
using Model;

namespace Services
{
    public interface IObserver
    {
        void ticketsSold(IList<Flight> flights);
    }
}