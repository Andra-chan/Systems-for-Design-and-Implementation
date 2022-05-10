using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using Model;
using Persistence.database.repositoryInterfaces;

namespace Persistence.database
{
	public class FlightDbRepository : IFlightDbRepository<int, Flight>
	{
		private static readonly ILog log = LogManager.GetLogger("FlightDbRepository");

		IDictionary<String, string> props;
		public FlightDbRepository(IDictionary<String, string> props)
		{
			log.Info("Creating FlightDbRepository ");
			this.props = props;
		}

		public Flight findOne(int id)
		{
			log.InfoFormat("Entering findOne with value {0}", id);
			IDbConnection con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, destination, airport, date_time, total_seats, remaining_seats from Flights where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);

				using (var dataR = comm.ExecuteReader())
				{
					if (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String destination = dataR.GetString(1);
						String airport = dataR.GetString(2);
						DateTime date_time = dataR.GetDateTime(3);
						int total_seats = dataR.GetInt32(4);
						int remaining_seats = dataR.GetInt32(5);

						Flight flight = new Flight(destination, airport, date_time, total_seats, remaining_seats);
						flight.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", flight);
						return flight;
					}
				}
			}
			log.InfoFormat("Exiting findOne with value {0}", null);
			return null;
		}

		public IList<Flight> findAll()
		{
			IDbConnection con = DBUtils.getConnection(props);
			IList<Flight> flightsR = new List<Flight>();
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, destination, airport, date_time, total_seats, remaining_seats from Flights";

				using (var dataR = comm.ExecuteReader())
				{
					while (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String destination = dataR.GetString(1);
						String airport = dataR.GetString(2);
						DateTime date_time = dataR.GetDateTime(3);
						int total_seats = dataR.GetInt32(4);
						int remaining_seats = dataR.GetInt32(5);

						Flight flight = new Flight(destination, airport, date_time, total_seats, remaining_seats);
						flight.Id = idV;
						log.InfoFormat("Exiting findAll with value {0}", flight);
						flightsR.Add(flight);
					}
				}
			}

			return flightsR;
		}
		public void save(Flight entity)
		{
			var con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "insert into Flights(destination, airport, date_time, total_seats, remaining_seats) values (@destination, @airport, @date_time, @total_seats, @remaining_seats)";


				var paramDest = comm.CreateParameter();
				paramDest.ParameterName = "@destination";
				paramDest.Value = entity.Destination;
				comm.Parameters.Add(paramDest);

				var paramAir = comm.CreateParameter();
				paramAir.ParameterName = "@airport";
				paramAir.Value = entity.Airport;
				comm.Parameters.Add(paramAir);

				var paramDate = comm.CreateParameter();
				paramDate.ParameterName = "@date_time";
				paramDate.Value = entity.Date_Time;
				comm.Parameters.Add(paramDate);

				var paramTotalSeats = comm.CreateParameter();
				paramTotalSeats.ParameterName = "@total_seats";
				paramTotalSeats.Value = entity.TotalSeats;
				comm.Parameters.Add(paramTotalSeats);

				var paramRemainingSeats = comm.CreateParameter();
				paramRemainingSeats.ParameterName = "@remaining_seats";
				paramRemainingSeats.Value = entity.RemainingSeats;
				comm.Parameters.Add(paramRemainingSeats);

				var result = comm.ExecuteNonQuery();
				if (result == 0)
					throw new RepositoryException("No flight added!");
			}

		}
		public void delete(int id)
		{
			IDbConnection con = DBUtils.getConnection(props);
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "delete from Flights where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);
				var dataR = comm.ExecuteNonQuery();
				if (dataR == 0)
					throw new RepositoryException("No flight deleted!");
			}
		}

		public void update(Flight entity)
		{
			IDbConnection con = DBUtils.getConnection(props);
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "update Flights set destination = @destination, airport = @airport, date_time = @date_time, total_seats = @total_seats, remaining_seats = @remaining_seats where id = @id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = entity.Id;
				comm.Parameters.Add(paramId);

				var paramDest = comm.CreateParameter();
				paramDest.ParameterName = "@destination";
				paramDest.Value = entity.Destination;
				comm.Parameters.Add(paramDest);

				var paramAir = comm.CreateParameter();
				paramAir.ParameterName = "@airport";
				paramAir.Value = entity.Airport;
				comm.Parameters.Add(paramAir);

				var paramDate = comm.CreateParameter();
				paramDate.ParameterName = "@date_time";
				paramDate.Value = entity.Date_Time;
				comm.Parameters.Add(paramDate);

				var paramTotalSeats = comm.CreateParameter();
				paramTotalSeats.ParameterName = "@total_seats";
				paramTotalSeats.Value = entity.TotalSeats;
				comm.Parameters.Add(paramTotalSeats);

				var paramRemainingSeats = comm.CreateParameter();
				paramRemainingSeats.ParameterName = "@remaining_seats";
				paramRemainingSeats.Value = entity.RemainingSeats;
				comm.Parameters.Add(paramRemainingSeats);

				var result = comm.ExecuteNonQuery();
				if (result == 0)
					throw new RepositoryException("No flight updated!");
			}

		}

		public IList<Flight> findBySearch(String searchDestination, DateTime searchDateTime)
		{
			log.InfoFormat("Entering findOne with value {0}", searchDestination);
			IDbConnection con = DBUtils.getConnection(props);
			IList<Flight> flightsR = new List<Flight>();
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select * from Flights where destination = @destination and date_time = @date_time";
				IDbDataParameter paramDestination = comm.CreateParameter();
				paramDestination.ParameterName = "@destination";
				paramDestination.Value = searchDestination;
				comm.Parameters.Add(paramDestination);

				IDbDataParameter paramDate = comm.CreateParameter();
				paramDate.ParameterName = "@date_time";
				paramDate.Value = searchDateTime;
				comm.Parameters.Add(paramDate);

				using (var dataR = comm.ExecuteReader())
				{
					if (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String destination = dataR.GetString(1);
						String airport = dataR.GetString(2);
						DateTime date_time = dataR.GetDateTime(3);
						int total_seats = dataR.GetInt32(4);
						int remaining_seats = dataR.GetInt32(5);

						Flight flight = new Flight(destination, airport, date_time, total_seats, remaining_seats);
						flight.Id = idV;
						log.InfoFormat("Exiting findAll with value {0}", flight);
						flightsR.Add(flight);
					}
				}
			}
			return flightsR;
		}

		public IList<Flight> findBySearchAirport(String searchDestination, String searchAirport)
		{
			log.InfoFormat("Entering findOne with value {0}", searchDestination);
			IDbConnection con = DBUtils.getConnection(props);
			IList<Flight> flightsR = new List<Flight>();
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select * from Flights where destination = @destination and airport = @airport";
				IDbDataParameter paramDestination = comm.CreateParameter();
				paramDestination.ParameterName = "@destination";
				paramDestination.Value = searchDestination;
				comm.Parameters.Add(paramDestination);

				IDbDataParameter paramAirport = comm.CreateParameter();
				paramAirport.ParameterName = "@airport";
				paramAirport.Value = searchAirport;
				comm.Parameters.Add(paramAirport);

				using (var dataR = comm.ExecuteReader())
				{
					while (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String destination = dataR.GetString(1);
						String airport = dataR.GetString(2);
						DateTime date_time = dataR.GetDateTime(3);
						int total_seats = dataR.GetInt32(4);
						int remaining_seats = dataR.GetInt32(5);

						Flight flight = new Flight(destination, airport, date_time, total_seats, remaining_seats);
						flight.Id = idV;
						log.InfoFormat("Exiting findAll with value {0}", flight);
						flightsR.Add(flight);
					}
				}
			}
			return flightsR;

		}

	}
}

