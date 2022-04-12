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

namespace Client
{
    public partial class SearchResult : Form
    {
        public SearchResult(IList<Flight> flights)
        {
            InitializeComponent();
            foreach (Flight f in flights)
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

        private void back_Click(object sender, EventArgs e)
        {
            this.Hide();
            this.Dispose();
        }
    }
}
