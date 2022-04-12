
using System.ComponentModel;

namespace Client
{
	partial class SearchResult
	{
		/// <summary>
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
			this.back = new System.Windows.Forms.Button();
			this.idFlight = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.destination = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.date = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.airport = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.totalSeats = new System.Windows.Forms.DataGridViewTextBoxColumn();
			this.remainingSeats = new System.Windows.Forms.DataGridViewTextBoxColumn();
			((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
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
			this.dataGridView1.Location = new System.Drawing.Point(12, 10);
			this.dataGridView1.Margin = new System.Windows.Forms.Padding(3, 1, 3, 1);
			this.dataGridView1.Name = "dataGridView1";
			this.dataGridView1.RowHeadersWidth = 51;
			this.dataGridView1.RowTemplate.Height = 24;
			this.dataGridView1.Size = new System.Drawing.Size(773, 316);
			this.dataGridView1.TabIndex = 0;
			// 
			// back
			// 
			this.back.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(178)))), ((int)(((byte)(231)))), ((int)(((byte)(232)))));
			this.back.Location = new System.Drawing.Point(657, 336);
			this.back.Margin = new System.Windows.Forms.Padding(3, 1, 3, 1);
			this.back.Name = "back";
			this.back.Size = new System.Drawing.Size(128, 31);
			this.back.TabIndex = 1;
			this.back.Text = "Close";
			this.back.UseVisualStyleBackColor = false;
			this.back.Click += new System.EventHandler(this.back_Click);
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
			// SearchResult
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(48)))), ((int)(((byte)(77)))), ((int)(((byte)(99)))));
			this.ClientSize = new System.Drawing.Size(817, 379);
			this.Controls.Add(this.back);
			this.Controls.Add(this.dataGridView1);
			this.Margin = new System.Windows.Forms.Padding(3, 4, 3, 4);
			this.Name = "SearchResult";
			this.Text = "SearchResult";
			((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
			this.ResumeLayout(false);

		}

		#endregion

		private System.Windows.Forms.DataGridView dataGridView1;
		private System.Windows.Forms.Button back;
		private System.Windows.Forms.DataGridViewTextBoxColumn idFlight;
		private System.Windows.Forms.DataGridViewTextBoxColumn destination;
		private System.Windows.Forms.DataGridViewTextBoxColumn date;
		private System.Windows.Forms.DataGridViewTextBoxColumn airport;
		private System.Windows.Forms.DataGridViewTextBoxColumn totalSeats;
		private System.Windows.Forms.DataGridViewTextBoxColumn remainingSeats;
	}
}