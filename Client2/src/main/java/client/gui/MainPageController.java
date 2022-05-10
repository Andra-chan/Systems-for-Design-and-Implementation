package client.gui;


import client.StartObjectClient;
import client.StartProtobuffClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import project.model.Flight;
import project.model.FlightDTO;
import project.model.TicketDTO;
import project.model.User;
import project.services.IObserver;
import project.services.IServices;
import project.services.ServiceException;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;


public class MainPageController implements Initializable, IObserver {
    ObservableList<Flight> modelFlights = FXCollections.observableArrayList();
    ObservableList<TicketDTO> modelTickets = FXCollections.observableArrayList();

    @FXML
    Label warningLabel;
    @FXML
    Button logoutButton;
    @FXML
    TextField textFieldDestination;
    @FXML
    TextField textFieldAirport;
    @FXML
    TextField textFieldDate;
    @FXML
    TextField textFieldClient;
    @FXML
    TextField textFieldTourists;
    @FXML
    TextField textFieldAddress;
    @FXML
    TextField textFieldNoSeats;
    @FXML
    Button searchButton;
    @FXML
    Button buyTicketsButton;
    @FXML
    TableColumn<Flight, String> destinationColumn;
    @FXML
    TableColumn<Flight, String> airportColumn;
    @FXML
    TableColumn<Flight, LocalDateTime> dateColumn;
    @FXML
    TableColumn<Flight, Integer> totalSeatsColumn;
    @FXML
    TableColumn<Flight, Integer> remainingSeatsColumn;
    @FXML
    TableView<Flight> flightsList;

    private IServices service;
    private User user;

    public void setServer(IServices service){
        this.service=service;
    }

    public void setUser(User user){
        this.user=user;
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb)  {
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        airportColumn.setCellValueFactory(new PropertyValueFactory<>("airport"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        totalSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        remainingSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("remainingSeats"));
        flightsList.setPlaceholder(new Label("Start adding flights to see more!"));
        flightsList.setItems(modelFlights);
    }

    public void onBuyTicketsButtonClick() {
        Flight selectedFlight = flightsList.getSelectionModel().getSelectedItem();
        String name = textFieldClient.getText();
        String tourists = textFieldTourists.getText();
        String address = textFieldAddress.getText();
        Integer seats = Integer.valueOf(textFieldNoSeats.getText());
        service.buyTicket(selectedFlight.getId(), name, tourists, address, seats);
        //ticketsSold(selectedFlight, seats);
    }

    public void onSearchButtonClick() {
        String destination = textFieldDestination.getText();
        String airport = textFieldAirport.getText();
        //LocalDateTime date = LocalDateTime.parse(textFieldDate.getText());
        var list = service.getFlightBySearchAirport(destination, airport);
        //var listDate = service.getFlightBySearch(destination, date);
        for (Flight f : list)
            if (f.getRemainingSeats() <= 0)
                list.remove(f);
        modelFlights.setAll(list);
    }

    public List<Flight> getFlights() {
        return service.getAllFlights();
    }

    @Override
    public void ticketsSold(List<Flight> flights) throws ServiceException {
        modelFlights.setAll(flights);
        //flightsList.setItems(modelFlights);
        /*
        var flightList = getFlights();
        modelFlights.setAll(flightList);
        for(int index=0;index<modelFlights.size();index++){
            Flight newFlight=modelFlights.get(index);
            if(newFlight.getId().intValue()== flight.getId()){
                newFlight.setRemainingSeats(newFlight.getRemainingSeats() - seats);
                modelFlights.set(index, newFlight);
                flightsList.setItems(modelFlights);
            }
        }

         */
    }

    public void onLogoutButtonClick() {
        try {
            service.logout(user,this);
            StartProtobuffClient.changeSceneToLogin(service);
            //StartObjectClient.changeSceneToLogin(service);
        } catch (ServiceException e) {
            warningLabel.setText(e.getMessage());
        }
    }

    /**
    public void onLogoutButtonClick() {
        StartObjectClient.changeSceneToLogin(service);
    }
    **/

    /**
     * Initialize the application.service
     */
    public void setService(User user, IServices service) {
        this.user = user;
        this.service = service;
        var flightList = getFlights();
        modelFlights.setAll(flightList);
    }
}
