using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Model;
using Services;

namespace Client
{
    public partial class MainPage : Form, IObserver
    {
        private readonly IServices service;
        private User currentUser;

        public MainPage(IServices service)
        {
            this.service = service;
            InitializeComponent();
            currentUser = null;
        }

        public void initData(IList<Flight> tickets)
        {
            dataGridView1.Rows.Clear();
            foreach (Flight f in tickets)
            {
                var index = dataGridView1.Rows.Add();
                dataGridView1.Rows[index].Cells["destination"].Value = f.Destination;
                dataGridView1.Rows[index].Cells["idFlight"].Value = f.Id;
                dataGridView1.Rows[index].Cells["date"].Value = f.Date_Time.Date;
                dataGridView1.Rows[index].Cells["airport"].Value = f.Airport;
                dataGridView1.Rows[index].Cells["totalSeats"].Value = f.TotalSeats;
                dataGridView1.Rows[index].Cells["remainingSeats"].Value = f.RemainingSeats;
                if (f.RemainingSeats == 0)
                    dataGridView1.Rows[index].DefaultCellStyle.BackColor = ColorTranslator.FromHtml("#BF2A36");
            }
        }

        private void updateTable(DataGridView table, IList<Flight> flights)
        {
            table.Rows.Clear();
            foreach (Flight f in flights)
            {
                var index = table.Rows.Add();
                dataGridView1.Rows[index].Cells["destination"].Value = f.Destination;
                dataGridView1.Rows[index].Cells["idFlight"].Value = f.Id;
                dataGridView1.Rows[index].Cells["date"].Value = f.Date_Time.Date;
                dataGridView1.Rows[index].Cells["airport"].Value = f.Airport;
                dataGridView1.Rows[index].Cells["totalSeats"].Value = f.TotalSeats;
                dataGridView1.Rows[index].Cells["remainingSeats"].Value = f.RemainingSeats;
                if (f.RemainingSeats == 0)
                    dataGridView1.Rows[index].DefaultCellStyle.BackColor = ColorTranslator.FromHtml("#BF2A36");
            }
        }

        public delegate void UpdateCallback(DataGridView table, IList<Flight> data);

        private void search_Click(object sender, EventArgs e)
        {
            Console.WriteLine("requesting flights for date {0}", date);
            IList<Flight> flights = service.getFlightBySearchAirport(destinationBox.Text, airportBox.Text);
            if (flights.Count == 0)
            {
                MessageBox.Show("Nu s-a gasit niciun zbor pentru ce ati introdus!");
                return;
            }
            SearchResult searchResult = new SearchResult(flights);
            searchResult.ShowDialog();
        }

        private void buy_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count == 0)
            {
                MessageBox.Show("Nu ati selectat zborul!");
                return;
            }
            else
            {
                if (clientBox.Text == "")
                {
                    MessageBox.Show("Nu ati introdus numele!");
                    return;
                }
                else
                {
                    int index = dataGridView1.SelectedRows[0].Index;
                    if ((int)dataGridView1.Rows[index].Cells["remainingSeats"].Value == 0)
                    {
                        MessageBox.Show("Nu se mai pot cumpara bilete pentru acest zbor!");
                        return;
                    }
                    else
                    {
                        Console.WriteLine("Requesting buy...");
                        service.buyTicket(Int32.Parse(dataGridView1.Rows[index].Cells["idFlight"].Value.ToString()),
                            clientBox.Text, touristsBox.Text, addressBox.Text, Int32.Parse(numericUpDown1.Value.ToString()));
                        dataGridView1.ClearSelection();
                    }
                }
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (dataGridView1.Rows[e.RowIndex].Cells["remainingSeats"].Value == null ||
                Int32.Parse(dataGridView1.Rows[e.RowIndex].Cells["remainingSeats"].Value.ToString()) == 0)
            {
                numericUpDown1.Maximum = 0;
                numericUpDown1.Minimum = 0;
                return;
            }

            numericUpDown1.Minimum = 1;
            numericUpDown1.Maximum = (int)dataGridView1.Rows[e.RowIndex].Cells["remainingSeats"].Value;
        }

        private void logOut_Click_1(object sender, EventArgs e)
        {
            Console.WriteLine(currentUser.Email + " logging out");
            service.logout(currentUser, this);
            currentUser = null;
            Application.Exit();
        }

        public void login(String email, String password)
        {
            service.login(email, password, this);
            Console.WriteLine("Login succeeded...");
            currentUser = new User("FirstName", "LastName", email, password);
            Console.WriteLine("Current user {0}", email);
        }

        public IList<Flight> GetAllFlights()
        {
            Console.WriteLine("Requesting flights...");
            return service.getAllFlights();
        }

        public void soldTicketsUpdate(IList<Flight> flights)
        {
            System.Console.WriteLine("Notifying window...");
            dataGridView1.Invoke(new UpdateCallback(this.updateTable), new object[]{this.dataGridView1, flights});
        }
    }
}
