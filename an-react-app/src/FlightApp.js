
import React from  'react';
import FlightTable from './FlightTable';
import './FlightApp.css'
import FlightForm from "./FlightForm";
import {GetFlights, DeleteFlight, AddFlight, UpdateFlight} from './utils/rest-calls'


class FlightApp extends React.Component{
    constructor(props){
        super(props);
        this.state={flights:[{"id":"id1","destination":"destination1", "airport":"airport1", "dateTime":"2022-05-17 17:10:47","totalSeats":2, "remainingSeats": 2}],
            deleteFunc:this.deleteFunc.bind(this),
            addFunc:this.addFunc.bind(this),
            updateFunc:this.updateFunc.bind(this),
        }
        console.log('FlightApp constructor')
    }

    addFunc(flight){
        console.log('inside add Func '+flight);
        AddFlight(flight)
            .then(res=> GetFlights())
            .then(flights=>this.setState({flights}))
            .catch(error=>console.log('eroare add ',error));
    }


    deleteFunc(flight){
        console.log('inside deleteFunc '+flight);
        DeleteFlight(flight)
            .then(res=> GetFlights())
            .then(flights=>this.setState({flights}))
            .catch(error=>console.log('eroare delete', error));
    }

    updateFunc(id, flight){
        console.log('inside updateFunc ' + flight);
        console.log('id: ' + id)
        UpdateFlight(id, flight)
            .then(res=> GetFlights())
            .then(flights=>this.setState({flights}))
            .catch(error=>console.log('eroare update ',error));
    }


    componentDidMount(){
        console.log('inside componentDidMount')
        GetFlights().then(flights=>this.setState({flights}));
    }

    render(){
        return(
            <div className="FlightApp">
                <h1>Flight Management</h1>
                <FlightForm addFunc={this.state.addFunc} updateFunc={this.state.updateFunc}/>
                <br/>
                <br/>
                <FlightTable flights={this.state.flights} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default FlightApp;