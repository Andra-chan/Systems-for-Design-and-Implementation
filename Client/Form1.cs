using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    public partial class Form1 : Form
    {
        private MainPage mainPage;
        public Form1(MainPage mainPage)
        {
            InitializeComponent();
            this.mainPage = mainPage;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (emailTextBox.Text == "" || passwordTextBox.Text == "")
            {
                MessageBox.Show("Campul de email sau parola nu pot fi goale!");
                return;
            }

            try
            {
                mainPage.login(emailTextBox.Text, passwordTextBox.Text);
                mainPage.Text = "Main page window for" + emailTextBox.Text;
                mainPage.Show();
                mainPage.initData(mainPage.GetAllFlights());
                this.Hide();
            }
            catch (Exception ex)
            {
                emailTextBox.Clear();
                passwordTextBox.Clear();
                MessageBox.Show(this, "Login Error" + ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {
        }
    }
}
