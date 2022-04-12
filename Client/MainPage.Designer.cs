
using System.ComponentModel;

namespace Client
{
	partial class MainPage
	{
		// <summary>
		/// Required designer variable.
		/// </summary>
		private IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}

			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.dataGridView1 = new System.Windows.Forms.DataGridView();
			this.idFlight = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.destination = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.date = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.airport = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.totalSeats = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.remainingSeats = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.label1 = new System.Windows.Forms.Label();
			this.label2 = new System.Windows.Forms.Label();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.clientBox = new System.Windows.Forms.TextBox();
			this.search = new System.Windows.Forms.Button();
			this.buy = new System.Windows.Forms.Button();
			this.label5 = new System.Windows.Forms.Label();
			this.numericUpDown1 = new System.Windows.Forms.NumericUpDown();
			this.logOut = new System.Windows.Forms.Button();
			this.label6 = new System.Windows.Forms.Label();
			this.destinationBox = new System.Windows.Forms.TextBox();
			this.airportBox = new System.Windows.Forms.TextBox();
			this.label7 = new System.Windows.Forms.Label();
			this.label8 = new System.Windows.Forms.Label();
			this.addressBox = new System.Windows.Forms.TextBox();
			this.touristsBox = new System.Windows.Forms.TextBox();
			((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
			((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).BeginInit();
			this.SuspendLayout();
			// 
			// dataGridView1
			// 
			this.dataGridView1.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(185)))), ((int)(((byte)(170)))));
			this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
			this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.idFlight,
            this.destination,
            this.date,
            this.airport,
            this.totalSeats,
            this.remainingSeats});
			this.dataGridView1.Location = new System.Drawing.Point(11, 11);
			this.dataGridView1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.dataGridView1.Name = "dataGridView1";
			this.dataGridView1.RowHeadersWidth = 51;
			this.dataGridView1.RowTemplate.Height = 24;
			this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
			this.dataGridView1.Size = new System.Drawing.Size(535, 464);
			this.dataGridView1.TabIndex = 1;
			this.dataGridView1.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridView1_CellClick);
			// 
			// idFlight
			// 
			this.idFlight.HeaderText = "ID";
			this.idFlight.MinimumWidth = 6;
			this.idFlight.Name = "idFlight";
			this.idFlight.Width = 35;
			// 
			// destination
			// 
			this.destination.HeaderText = "Destination";
			this.destination.MinimumWidth = 6;
			this.destination.Name = "destination";
			this.destination.Width = 125;
			// 
			// date
			// 
			this.date.HeaderText = "Date";
			this.date.MinimumWidth = 6;
			this.date.Name = "date";
			this.date.Width = 125;
			// 
			// airport
			// 
			this.airport.HeaderText = "Airport";
			this.airport.MinimumWidth = 6;
			this.airport.Name = "airport";
			this.airport.Width = 125;
			// 
			// totalSeats
			// 
			this.totalSeats.HeaderText = "Total Seats";
			this.totalSeats.MinimumWidth = 6;
			this.totalSeats.Name = "totalSeats";
			this.totalSeats.Width = 125;
			// 
			// remainingSeats
			// 
			this.remainingSeats.HeaderText = "Remaining Seats";
			this.remainingSeats.MinimumWidth = 6;
			this.remainingSeats.Name = "remainingSeats";
			this.remainingSeats.Width = 125;
			// 
			// label1
			// 
			this.label1.Font = new System.Drawing.Font("Segoe UI", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(185)))), ((int)(((byte)(170)))));
			this.label1.Location = new System.Drawing.Point(688, 18);
			this.label1.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(152, 29);
			this.label1.TabIndex = 2;
			this.label1.Text = "Search Flights";
			this.label1.TextAlign = System.Drawing.ContentAlignment.TopCenter;
			// 
			// label2
			// 
			this.label2.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label2.ForeColor = System.Drawing.Color.White;
			this.label2.Location = new System.Drawing.Point(640, 63);
			this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(82, 24);
			this.label2.TabIndex = 3;
			this.label2.Text = "Destination:";
			// 
			// label3
			// 
			this.label3.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label3.ForeColor = System.Drawing.Color.White;
			this.label3.Location = new System.Drawing.Point(670, 253);
			this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(53, 20);
			this.label3.TabIndex = 4;
			this.label3.Text = "Client:";
			// 
			// label4
			// 
			this.label4.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label4.ForeColor = System.Drawing.Color.White;
			this.label4.Location = new System.Drawing.Point(641, 359);
			this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(89, 24);
			this.label4.TabIndex = 5;
			this.label4.Text = "No. of seats:";
			// 
			// clientBox
			// 
			this.clientBox.Location = new System.Drawing.Point(736, 253);
			this.clientBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.clientBox.Name = "clientBox";
			this.clientBox.Size = new System.Drawing.Size(132, 20);
			this.clientBox.TabIndex = 6;
			// 
			// search
			// 
			this.search.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(231)))), ((int)(((byte)(232)))));
			this.search.Location = new System.Drawing.Point(728, 142);
			this.search.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.search.Name = "search";
			this.search.Size = new System.Drawing.Size(72, 24);
			this.search.TabIndex = 7;
			this.search.Text = "Search";
			this.search.UseVisualStyleBackColor = false;
			this.search.Click += new System.EventHandler(this.search_Click);
			// 
			// buy
			// 
			this.buy.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(231)))), ((int)(((byte)(232)))));
			this.buy.Location = new System.Drawing.Point(728, 402);
			this.buy.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.buy.Name = "buy";
			this.buy.Size = new System.Drawing.Size(76, 24);
			this.buy.TabIndex = 8;
			this.buy.Text = "Buy Ticket";
			this.buy.UseVisualStyleBackColor = false;
			this.buy.Click += new System.EventHandler(this.buy_Click);
			// 
			// label5
			// 
			this.label5.Font = new System.Drawing.Font("Segoe UI", 13.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label5.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(185)))), ((int)(((byte)(170)))));
			this.label5.Location = new System.Drawing.Point(704, 199);
			this.label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(122, 30);
			this.label5.TabIndex = 9;
			this.label5.Text = "Buy Passes";
			this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
			// 
			// numericUpDown1
			// 
			this.numericUpDown1.Location = new System.Drawing.Point(736, 359);
			this.numericUpDown1.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.numericUpDown1.Name = "numericUpDown1";
			this.numericUpDown1.Size = new System.Drawing.Size(132, 20);
			this.numericUpDown1.TabIndex = 11;
			// 
			// logOut
			// 
			this.logOut.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(231)))), ((int)(((byte)(232)))));
			this.logOut.Location = new System.Drawing.Point(791, 458);
			this.logOut.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.logOut.Name = "logOut";
			this.logOut.Size = new System.Drawing.Size(76, 25);
			this.logOut.TabIndex = 12;
			this.logOut.Text = "Logout";
			this.logOut.UseVisualStyleBackColor = false;
			this.logOut.Click += new System.EventHandler(this.logOut_Click_1);
			// 
			// label6
			// 
			this.label6.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label6.ForeColor = System.Drawing.Color.White;
			this.label6.Location = new System.Drawing.Point(669, 99);
			this.label6.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label6.Name = "label6";
			this.label6.Size = new System.Drawing.Size(61, 24);
			this.label6.TabIndex = 13;
			this.label6.Text = "Airport:";
			// 
			// destinationBox
			// 
			this.destinationBox.Location = new System.Drawing.Point(734, 63);
			this.destinationBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.destinationBox.Name = "destinationBox";
			this.destinationBox.Size = new System.Drawing.Size(132, 20);
			this.destinationBox.TabIndex = 14;
			// 
			// airportBox
			// 
			this.airportBox.Location = new System.Drawing.Point(734, 100);
			this.airportBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.airportBox.Name = "airportBox";
			this.airportBox.Size = new System.Drawing.Size(132, 20);
			this.airportBox.TabIndex = 15;
			// 
			// label7
			// 
			this.label7.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label7.ForeColor = System.Drawing.Color.White;
			this.label7.Location = new System.Drawing.Point(661, 325);
			this.label7.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label7.Name = "label7";
			this.label7.Size = new System.Drawing.Size(62, 20);
			this.label7.TabIndex = 16;
			this.label7.Text = "Address:";
			// 
			// label8
			// 
			this.label8.Font = new System.Drawing.Font("Segoe UI", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
			this.label8.ForeColor = System.Drawing.Color.White;
			this.label8.Location = new System.Drawing.Point(661, 289);
			this.label8.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
			this.label8.Name = "label8";
			this.label8.Size = new System.Drawing.Size(62, 20);
			this.label8.TabIndex = 17;
			this.label8.Text = "Tourists:";
			// 
			// addressBox
			// 
			this.addressBox.Location = new System.Drawing.Point(736, 325);
			this.addressBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.addressBox.Name = "addressBox";
			this.addressBox.Size = new System.Drawing.Size(132, 20);
			this.addressBox.TabIndex = 18;
			// 
			// touristsBox
			// 
			this.touristsBox.Location = new System.Drawing.Point(736, 289);
			this.touristsBox.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.touristsBox.Name = "touristsBox";
			this.touristsBox.Size = new System.Drawing.Size(132, 20);
			this.touristsBox.TabIndex = 19;
			// 
			// MainPage
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(48)))), ((int)(((byte)(77)))), ((int)(((byte)(99)))));
			this.ClientSize = new System.Drawing.Size(898, 490);
			this.Controls.Add(this.touristsBox);
			this.Controls.Add(this.addressBox);
			this.Controls.Add(this.label8);
			this.Controls.Add(this.label7);
			this.Controls.Add(this.airportBox);
			this.Controls.Add(this.destinationBox);
			this.Controls.Add(this.label6);
			this.Controls.Add(this.logOut);
			this.Controls.Add(this.numericUpDown1);
			this.Controls.Add(this.label5);
			this.Controls.Add(this.buy);
			this.Controls.Add(this.search);
			this.Controls.Add(this.clientBox);
			this.Controls.Add(this.label4);
			this.Controls.Add(this.label3);
			this.Controls.Add(this.label2);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.dataGridView1);
			this.Location = new System.Drawing.Point(15, 15);
			this.Margin = new System.Windows.Forms.Padding(2, 2, 2, 2);
			this.Name = "MainPage";
			this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
			((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
			((System.ComponentModel.ISupportInitialize)(this.numericUpDown1)).EndInit();
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		private System.Windows.Forms.TextBox addressBox;
		private System.Windows.Forms.TextBox touristsBox;

		private System.Windows.Forms.Label label7;
		private System.Windows.Forms.Label label8;

		private System.Windows.Forms.TextBox destinationBox;
		private System.Windows.Forms.TextBox airportBox;

		private System.Windows.Forms.Label label6;

		#endregion

		private System.Windows.Forms.Button logOut;
		private System.Windows.Forms.DataGridView dataGridView1;
		private System.Windows.Forms.NumericUpDown numericUpDown1;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.Button buy;
		private System.Windows.Forms.Button search;
		private System.Windows.Forms.TextBox clientBox;
		private System.Windows.Forms.DataGridViewTextBoxColumn idFlight;
		private System.Windows.Forms.DataGridViewTextBoxColumn destination;
		private System.Windows.Forms.DataGridViewTextBoxColumn date;
		private System.Windows.Forms.DataGridViewTextBoxColumn airport;
		private System.Windows.Forms.DataGridViewTextBoxColumn totalSeats;
		private System.Windows.Forms.DataGridViewTextBoxColumn remainingSeats;
	}
}