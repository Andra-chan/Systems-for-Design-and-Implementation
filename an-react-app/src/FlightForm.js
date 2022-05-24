
import React from  'react';
class FlightForm extends React.Component{

    constructor(props) {
        super(props);
        this.state = {destination: '', airport:'', dateTime:'', totalSeats:'', remainingSeats:''};

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }
    handleIdChange=(event) =>{
        this.setState({id: event.target.value});
    }

    handleDestinationChange=(event) =>{
        this.setState({destination: event.target.value});
    }

    handleAirportChange=(event) =>{
        this.setState({airport: event.target.value});
    }
    handleDateTimeChange=(event) =>{
        this.setState({dateTime: event.target.value});
    }
    handleTotalSeatsChange=(event) =>{
        this.setState({totalSeats: event.target.value});
    }

    handleRemainingSeatsChange=(event) =>{
        this.setState({remainingSeats: event.target.value});
    }
    handleSubmit =(event) =>{
        let flight={destination:this.state.destination,
            airport:this.state.airport,
            dateTime:this.state.dateTime,
            totalSeats:this.state.totalSeats,
            remainingSeats:this.state.remainingSeats
        }
        console.log('A flight was submitted: ');
        console.log(flight);
            this.props.addFunc(flight);
        event.preventDefault();
    }

    handleUpdate =(event) =>{
        let id = this.state.id;
        let flight={destination:this.state.destination,
            airport:this.state.airport,
            dateTime:this.state.dateTime,
            totalSeats:this.state.totalSeats,
            remainingSeats:this.state.remainingSeats
        }
        console.log('A flight was submitted: ');
        console.log(flight);
        console.log('Id: ');
        console.log(id);
        this.props.updateFunc(id, flight);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Id:
                    <input type="text" value={this.props.id} onChange={this.handleIdChange} />
                </label><br/>
                <label>
                    Destination:
                    <input type="text" value={this.state.destination} onChange={this.handleDestinationChange} />
                </label><br/>
                <label>
                    Airport:
                    <input type="text" value={this.state.airport} onChange={this.handleAirportChange} />
                </label><br/>
                <label>
                    DateTime:
                    <input type="text" value={this.state.dateTime} onChange={this.handleDateTimeChange} />
                </label><br/>
                <label>
                    Total Seats:
                    <input type="text" value={this.state.totalSeats} onChange={this.handleTotalSeatsChange} />
                </label><br/>
                <label>
                    Remaining Seats:
                    <input type="text" value={this.state.remainingSeats} onChange={this.handleRemainingSeatsChange} />
                </label><br/>

                <input type="submit" onClick={this.handleSubmit} value="Add flight" />
                <input type="submit" onClick={this.handleUpdate} value="Update flight" />

            </form>
        );
    }
}
export default FlightForm;