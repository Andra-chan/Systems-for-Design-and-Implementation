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
	public class UserDbRepository : IUserDbRepository<int, User>
	{
		private static readonly ILog log = LogManager.GetLogger("UserDbRepository");

		IDictionary<String, string> props;
		public UserDbRepository(IDictionary<String, string> props)
		{
			log.Info("Creating UserDbRepository ");
			this.props = props;
		}

		public User findOne(int id)
		{
			log.InfoFormat("Entering findOne with value {0}", id);
			IDbConnection con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, first_name, last_name, email from Users where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);

				using (var dataR = comm.ExecuteReader())
				{
					if (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String first_name = dataR.GetString(1);
						String last_name = dataR.GetString(2);
						String email = dataR.GetString(3);


						User user = new User(first_name, last_name, email, "");
						user.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", user);
						return user;
					}
				}
			}
			log.InfoFormat("Exiting findOne with value {0}", null);
			return null;
		}

		public IList<User> findAll()
		{
			IDbConnection con = DBUtils.getConnection(props);
			IList<User> usersR = new List<User>();
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select id, first_name, last_name, email from Users";

				using (var dataR = comm.ExecuteReader())
				{
					while (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String first_name = dataR.GetString(1);
						String last_name = dataR.GetString(2);
						String email = dataR.GetString(3);


						User user = new User(first_name, last_name, email, "");
						user.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", user);

						usersR.Add(user);
					}
				}
			}

			return usersR;
		}
		public void save(User entity)
		{
			var con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "insert into Users(first_name,last_name, email, pass)  values (@first_name, @last_name, @email, @pass)";
				var paramName = comm.CreateParameter();
				paramName.ParameterName = "@first_name";
				paramName.Value = entity.FirstName;
				comm.Parameters.Add(paramName);

				var paramLastName = comm.CreateParameter();
				paramLastName.ParameterName = "@last_name";
				paramLastName.Value = entity.LastName;
				comm.Parameters.Add(paramLastName);

				var paramEmail = comm.CreateParameter();
				paramEmail.ParameterName = "@email";
				paramEmail.Value = entity.Email;
				comm.Parameters.Add(paramEmail);

				var paramPass = comm.CreateParameter();
				paramPass.ParameterName = "@pass";
				paramPass.Value = entity.Password;
				comm.Parameters.Add(paramPass);


				var result = comm.ExecuteNonQuery();
				if (result == 0)
					throw new RepositoryException("No user added!");
			}

		}
		public void delete(int id)
		{
			IDbConnection con = DBUtils.getConnection(props);
			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "delete from Users where id=@id";
				IDbDataParameter paramId = comm.CreateParameter();
				paramId.ParameterName = "@id";
				paramId.Value = id;
				comm.Parameters.Add(paramId);
				var dataR = comm.ExecuteNonQuery();
				if (dataR == 0)
					throw new RepositoryException("No user deleted!");
			}
		}

		public User findOneLogin(String email, String password)
		{
			log.InfoFormat("Entering findOne with value {0}", email);
			IDbConnection con = DBUtils.getConnection(props);

			using (var comm = con.CreateCommand())
			{
				comm.CommandText = "select * from Users where email=@email and pass = @password";
				IDbDataParameter paramEmail = comm.CreateParameter();
				paramEmail.ParameterName = "@email";
				paramEmail.Value = email;
				comm.Parameters.Add(paramEmail);

				IDbDataParameter paramPass = comm.CreateParameter();
				paramPass.ParameterName = "@password";
				paramPass.Value = password;
				comm.Parameters.Add(paramPass);

				using (var dataR = comm.ExecuteReader())
				{
					if (dataR.Read())
					{
						int idV = dataR.GetInt32(0);
						String first_name = dataR.GetString(1);
						String last_name = dataR.GetString(2);
						String emails = dataR.GetString(3);
						String pass = dataR.GetString(4);


						User user = new User(first_name, last_name, emails, pass);
						user.Id = idV;
						log.InfoFormat("Exiting findOne with value {0}", user);
						return user;
					}
				}
			}
			log.InfoFormat("Exiting findOne with value {0}", null);
			return null;
		}

	}
}

