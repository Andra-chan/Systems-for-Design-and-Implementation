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
	public class TicketDbRepository : ITicketDbRepository<int, Ticket>
	{
		private static readonly ILog log = LogManager.GetLogger("TicketDbRepository");

		IDictionary<String, string> props;
		public TicketDbRepository(IDictionary<String, string> props)
		{
			log.Info("Creating FlightDbRepository ");
			this.props = props;
		}

		public Ticket findOne(int id)
		{
			log.InfoFormat("Entering findOne with value {0}", id);
			IDbConnection con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, flight_id, name, tourists, address, seats from Tickets where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);

				using (var dataR = comm.ExecuteReader())
				{
					if (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						int idF = dataR.GetInt32(1);
						String name = dataR.GetString(2);
						String tourists = dataR.GetString(3);
						String address = dataR.GetString(4);
						int seats = dataR.GetInt32(5);

						Ticket ticket = new Ticket(idF, name, tourists, address, seats);
						ticket.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", ticket);
						return ticket;
					}
				}
			}
			log.InfoFormat("Exiting findOne with value {0}", null);
			return null;
		}

		public IList<Ticket> findAll()
		{
			IDbConnection con = DBUtils.getConnection(props);
			IList<Ticket> ticketsR = new List<Ticket>();
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, flight_id, name, tourists, address, seats from Tickets";

				using (var dataR = comm.ExecuteReader())
				{
					while (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						int idF = dataR.GetInt32(1);
						String name = dataR.GetString(2);
						String tourists = dataR.GetString(3);
						String address = dataR.GetString(4);
						int seats = dataR.GetInt32(5);

						Ticket ticket = new Ticket(idF, name, tourists, address, seats);
						ticket.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", ticket);

						ticketsR.Add(ticket);
					}
				}
			}

			return ticketsR;
		}
		public void save(Ticket entity)
		{
			var con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "insert into Tickets(flight_id, name, tourists, address, seats)  values (@flight_id, @name, @tourists, @address, @seats)";
				var paramIdF = comm.CreateParameter();
				paramIdF.ParameterName = "@flight_id";
				paramIdF.Value = entity.FlightId;
				comm.Parameters.Add(paramIdF);

				var paramName = comm.CreateParameter();
				paramName.ParameterName = "@name";
				paramName.Value = entity.Name;
				comm.Parameters.Add(paramName);

				var paramTour = comm.CreateParameter();
				paramTour.ParameterName = "@tourists";
				paramTour.Value = entity.Tourists;
				comm.Parameters.Add(paramTour);

				var paramAddr = comm.CreateParameter();
				paramAddr.ParameterName = "@address";
				paramAddr.Value = entity.Address;
				comm.Parameters.Add(paramAddr);

				var paramSeats = comm.CreateParameter();
				paramSeats.ParameterName = "@seats";
				paramSeats.Value = entity.Seats;
				comm.Parameters.Add(paramSeats);

				var result = comm.ExecuteNonQuery();
				if (result == 0)
					throw new RepositoryException("No ticket added!");
			}

		}
		public void delete(int id)
		{
			IDbConnection con = DBUtils.getConnection(props);
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "delete from Tickets where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);
				var dataR = comm.ExecuteNonQuery();
				if (dataR == 0)
					throw new RepositoryException("No ticket deleted!");
			}
		}

	}
}


