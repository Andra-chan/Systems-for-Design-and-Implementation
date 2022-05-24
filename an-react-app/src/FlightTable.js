
import React from  'react';
import './FlightApp.css'

class FlightRow extends React.Component{

    handleDelete=(event)=>{
        console.log('delete button pentru '+this.props.flight.id);
        this.props.deleteFunc(this.props.flight.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.flight.id}</td>
                <td>{this.props.flight.destination}</td>
                <td><button  onClick={this.handleDelete}>Delete</button></td>
            </tr>
        );
    }
}

class FlightTable extends React.Component {
    render() {
        let rows = [];
        let functieStergere=this.props.deleteFunc;
        this.props.flights.forEach(function(flight) {

            rows.push(<FlightRow flight={flight}  key={flight.id} deleteFunc={functieStergere} />);
        });
        return (<div className="FlightTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>Destination</th>
                        <th>Airport</th>

                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default FlightTable;