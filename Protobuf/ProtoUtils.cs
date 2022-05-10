using System;
using System.Collections.Generic;
using System.Text;
using Flight = Model.Flight;
using Ticket = Model.Ticket;
using User = Model.User;
using proto = Project.Network.Protobuffprotocol;
using Project.Network.Protobuffprotocol;

namespace Protobuf
{
    public class ProtoUtils
    {
        public static String getError(proto.Response response)
        {
            String errorMessage = response.Error;
            return errorMessage;
        }

        public static Request createLoginRequest(String email, String password)
        {
            Request request = new proto.Request { Type = proto.Request.Types.Type.Login, Email = email, Password = password };
            return request;
        }

        public static proto.Request createLogoutRequest(User user)
        {
            proto.User acc = new proto.User { FirstName = user.FirstName, LastName = user.LastName, Email = user.Email,  Password = user.Password };
            Request request = new proto.Request { Type = proto.Request.Types.Type.Logout, User = acc };
            return request;
        }

        public static User getUser(proto.Response response)
        {
            User user = new User(response.User.FirstName, response.User.LastName, response.User.Email, response.User.Password);
            //user.Id = response.User.Id;
            return user;
        }

        public static User getUser(proto.Request request)
        {
            User user = new User(request.User.FirstName, request.User.LastName, request.User.Email, request.User.Password);
            return user;
        }

        public static proto.Response createOkResponse()
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.Ok };
            return response;
        }

        public static proto.Response createErrorResponse(String message)
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.Error };
            return response;
        }

        public static proto.Response createLoginResponse(int id)
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.Login, Id = id };

            return response;
        }

        public static IList<Flight> getFlights(proto.Response response)
        {
            Flight[] flights = new Flight[response.Flights.Count];
            for (int i = 0; i < response.Flights.Count; i++)
            {
                proto.Flight flight = response.Flights[i];
                Flight flight1 = new Flight(flight.Destination, flight.Airport, DateTime.Parse(flight.Date), (int) flight.TotalSeats,(int) flight.RemainingSeats);
                flight1.Id = flight.Id;
                flights[i] = flight1;
            }

            return flights;
        }

        public static proto.Request createFlightRequest(String destination, String airport)
        {
            proto.Request request = new proto.Request { Type = proto.Request.Types.Type.SearchByAirport, Destination = destination, Airport = airport };
            return request;
        }
        public static proto.Request createFlightRequest()
        {
            proto.Request request = new proto.Request { Type = proto.Request.Types.Type.SearchByAirport };
            return request;
        }

        public static proto.Request createAllFlightsRequest()
        {
            proto.Request request = new proto.Request { Type = proto.Request.Types.Type.GetAllFlights };
            return request;
        }

        public static proto.Response createFlightResponse(IList<Flight> flights)
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.SearchByAirport };
            foreach (var flight in flights)
            {
                proto.Flight flight1 = new proto.Flight
                {            
                    Destination = flight.Destination,
                    Airport = flight.Airport,
                    Date = flight.Date_Time.ToString("yyyy-MM-dd hh:mm:ss"),
                    TotalSeats = flight.TotalSeats,
                    RemainingSeats = flight.RemainingSeats
                };
                flight1.Id = flight.Id;
                response.Flights.Add(flight1);
            }

            return response;
        }

        public static proto.Response createAllFlightsResponse(IList<Flight> flights)
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.GetAllFlights };
            foreach (var flight in flights)
            {
                proto.Flight flight1 = new proto.Flight
                {
                    Destination = flight.Destination,
                    Airport = flight.Airport,
                    Date = flight.Date_Time.ToString("yyyy-MM-dd hh:mm:ss"),
                    TotalSeats = flight.TotalSeats,
                    RemainingSeats = flight.RemainingSeats
                };
                flight1.Id = flight.Id;
                response.Flights.Add(flight1);
            }

            return response;
        }

        public static proto.Request createSellTicketRequest(Ticket ticket)
        {
            proto.Ticket ticket1 = new proto.Ticket
            { FlightId = ticket.FlightId, Name = ticket.Name, Tourists = ticket.Tourists, Address = ticket.Address, Seats = ticket.Seats };
            proto.Request request = new Request { Type = Request.Types.Type.SellTicket, Ticket = ticket1 };
            return request;
        }

        public static Ticket getTicket(proto.Ticket ticket)
        {
            Ticket ticket1 = new Ticket(ticket.FlightId, ticket.Name, ticket.Tourists, ticket.Address, ticket.Seats);
            return ticket1;
        }

        public static proto.Response createTicketSoldResponse(IList<Flight> flights)
        {
            proto.Response response = new proto.Response { Type = proto.Response.Types.Type.SellTicket };
            foreach (var flight in flights)
            {
                proto.Flight flight1 = new proto.Flight
                {
                    Destination = flight.Destination,
                    Airport = flight.Airport,
                    Date = flight.Date_Time.ToString("yyyy-MM-dd hh:mm:ss"),
                    TotalSeats = flight.TotalSeats,
                    RemainingSeats = flight.RemainingSeats
                };
                flight1.Id = flight.Id;
                response.Flights.Add(flight1);
            }

            return response;
        }
    }
}