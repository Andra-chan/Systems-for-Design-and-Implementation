import React from 'react';
//import ReactDOM from 'react-dom';
import {createRoot} from 'react-dom/client';
import './index1.css';

import FlightApp from "./FlightApp";

const container=document.getElementById('root');
const root=createRoot(container);
root.render( <FlightApp/>);

/*ReactDOM.render(
  <div>
 <FlightApp/>
  </div>,
  document.getElementById('root')
);*/
