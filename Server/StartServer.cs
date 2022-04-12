using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Persistence.database.repositoryInterfaces;
using Persistence.database;
using Services;
using Networking;
using System.Threading;
using System.Net.Sockets;
using Model;

namespace Server
{
    static class StartServer
    {
        static void Main(string[] args)
        {
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("FLIGHTMANAGEMENTDB"));
 
            IFlightDbRepository<int, Flight> flightRepo = new FlightDbRepository(props);
            ITicketDbRepository<int, Ticket> ticketRepo = new TicketDbRepository(props);
            IUserDbRepository<int, User> userRepo = new UserDbRepository(props);
            IServices serviceImpl = new ServerImplementation(flightRepo, ticketRepo, userRepo);

            MyConcurrentServer server = new MyConcurrentServer("127.0.0.1", 55555, serviceImpl);
            server.Start();
            Console.WriteLine("Server awaiting connections on port 55555");
            Console.ReadLine();
        }

        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = System.Configuration.ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }

    public class MyConcurrentServer : ServerUtils.ConcurrentServer
    {
        private IServices server;
        private ClientWorker worker;

        public MyConcurrentServer(string host, int port, IServices server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("MyConcurrentServer...");
        }

        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
