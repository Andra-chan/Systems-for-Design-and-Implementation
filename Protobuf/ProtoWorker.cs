using System;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using Services;
using Project.Network.Protobuffprotocol;
using Google.Protobuf;
using System.Collections.Generic;

namespace Protobuf
{
	public class ProtoWorker : IObserver
	{
		private IServices server;
		private TcpClient connection;

		private NetworkStream stream;
		private volatile bool connected;

		public ProtoWorker(IServices server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{

				stream = connection.GetStream();
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

					Request request = Request.Parser.ParseDelimitedFrom(stream);
					Response response = handleRequest(request);
					if (response != null)
					{
						sendResponse(response);
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
			Request.Types.Type reqType = request.Type;
			switch (reqType)
			{
				case Request.Types.Type.Login:
					{
						Console.WriteLine("Login request ...");
						String email = request.Email;
						String password = request.Password;
						try
						{
							int id = server.login(email, password, this);
							return ProtoUtils.createLoginResponse(id);
						}
						catch (ServiceException e)
						{
							connected = false;
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case Request.Types.Type.Logout:
					{
						Console.WriteLine("Logout request");
						Model.User user = ProtoUtils.getUser(request);
						try
						{
							server.logout(user, this);
							connected = false;
							return ProtoUtils.createOkResponse();

						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
				case Request.Types.Type.GetAllFlights:
					{
						Console.WriteLine("Get all flights request...");
						try
						{
							IList<Model.Flight> flights = server.getAllFlights();
							return ProtoUtils.createAllFlightsResponse(flights);
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case Request.Types.Type.SearchByAirport:
					{
						Console.WriteLine("Search request");
						String destination = null;
						String airport = null;
						if (request.Airport.Length > 0)
							airport = request.Airport;
						if (request.Destination.Length > 0)
							destination = request.Destination;
						try
						{
							IList<Model.Flight> flights = server.getFlightBySearchAirport(destination, airport);
							return ProtoUtils.createFlightResponse(flights);

						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}

				case Request.Types.Type.SellTicket:
					{
						Console.WriteLine("Sell ticket request");
						Model.Ticket ticket = ProtoUtils.getTicket(request.Ticket);
						try
						{
							server.buyTicket(ticket.FlightId, ticket.Name, ticket.Tourists, ticket.Address, ticket.Seats);
							return ProtoUtils.createOkResponse();
						}
						catch (ServiceException e)
						{
							return ProtoUtils.createErrorResponse(e.Message);
						}
					}
			}

			return response;
		}

		private void sendResponse(Response response)
		{
			Console.WriteLine("Sending response " + response);
			lock (stream)
			{
				response.WriteDelimitedTo(stream);
				stream.Flush();
			}

		}

		public void ticketsSold(IList<Model.Flight> flights)
		{
			Console.WriteLine("Sending sold ticket response");
			sendResponse(ProtoUtils.createTicketSoldResponse(flights));
		}
	}
}
