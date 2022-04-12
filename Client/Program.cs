using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Client;
using Networking;
using Services;

namespace Client
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            IServices server = new ServerProxy("127.0.0.1", 55555);
            MainPage ticketing = new MainPage(server);
            Application.Run(new Form1(ticketing));
        }
    }
}
