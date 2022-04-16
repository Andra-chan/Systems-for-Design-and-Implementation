package clientfx.clientfx.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;


public class MainPageController {
    ObservableList<Flight> modelFlights = FXCollections.observableArrayList();

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

    @FXML
    public void initialize() {
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

    public void onLogoutButtonClick() {
        App.changeSceneToLogin(service);
    }

    /**
     * Initialize the application.service
     */
    public void setService(Service service) {
        this.service = service;
        var flightList = getFlights();
        modelFlights.setAll(flightList);
    }
}
