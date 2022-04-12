using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client
{
	public class MainPageEventArgs : EventArgs
	{
		private readonly Object data;

		public MainPageEventArgs(object data)
		{
			this.data = data;
		}

		public object Data
		{
			get { return data; }
		}
	}
}
