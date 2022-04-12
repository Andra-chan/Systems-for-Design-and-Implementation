using System.Collections.Generic;
using Model;

namespace Services
{
    public interface IObserver
    {
        void soldTicketsUpdate(IList<Flight> flights);
    }
}