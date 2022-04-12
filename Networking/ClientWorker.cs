using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Services;

namespace Networking
{
    public class ClientWorker : IObserver
    {
        private IServices server;
        private TcpClient connection;

        private NetworkStream stream;
        private IFormatter formatter;
        private volatile bool connected;

        public ClientWorker(IServices server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    object request = formatter.Deserialize(stream);
                    object response = handleRequest((Request)request);
                    if (response != null)
                    {
                        sendResponse((Response)response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }

            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }

        private Response handleRequest(Request request)
        {
            Response response = null;
            if (request is LoginRequest)
            {
                Console.WriteLine("Login request ...");
                LoginRequest logReq = (LoginRequest)request;
                User user = logReq.User;
                try
                {
                    lock (server)
                    {
                        server.login(user.Email, user.Password, this);
                    }

                    return new OkResponse();
                }
                catch (ServiceException e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is LogoutRequest)
            {
                Console.WriteLine("Logout request");
                LogoutRequest logReq = (LogoutRequest)request;
                User user = logReq.User;
                try
                {
                    lock (server)
                    {
                        server.logout(user, this);
                    }

                    connected = false;
                    return new OkResponse();
                }
                catch (ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetAllFlightsRequest)
            {
                Console.WriteLine("Get all flights request");
                try
                {
                    IList<Flight> flights;
                    lock (server)
                    {
                        flights = server.getAllFlights();
                    }

                    return new GetAllFlightsResponse(flights);
                }
                catch (ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is GetFlightsByAirportRequest)
            {
                Console.WriteLine("Get flights by airport request");
                GetFlightsByAirportRequest airportRequest = (GetFlightsByAirportRequest)request;
                String searchDestination = airportRequest.Destination;
                String airport = airportRequest.Airport;
                try
                {
                    IList<Flight> flights;
                    lock (server)
                    {
                        flights = server.getFlightBySearchAirport(searchDestination, airport);
                    }

                    return new GetFlightsByAirportResponse(flights);
                }
                catch (ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            if (request is BuyTicketsRequest)
            {
                Console.WriteLine("Buy tickets request");
                BuyTicketsRequest buyRequest = (BuyTicketsRequest)request;
                TicketDTO ticket = buyRequest.TicketDto;
                try
                {
                    int idFlight = ticket.FlightId;
                    String name = ticket.Name;
                    String tourists = ticket.Tourists;
                    String address = ticket.Address;
                    int seats = ticket.Seats;

                    server.buyTicket(idFlight, name, tourists, address, seats);
                    return new OkResponse();
                }
                catch (ServiceException e)
                {
                    return new ErrorResponse(e.Message);
                }
            }

            return response;
        }

        private void sendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            formatter.Serialize(stream, response);
            stream.Flush();
        }

        public void soldTicketsUpdate(IList<Flight> flights)
        {
            Console.WriteLine("Announce sold tickets");
            try
            {
                sendResponse(new SoldTicketsResponse(flights));
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
	}
}