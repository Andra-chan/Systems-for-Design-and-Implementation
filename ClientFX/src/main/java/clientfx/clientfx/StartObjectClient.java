package clientfx.clientfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient extends Application {
    public static Stage currentStage;
    private static int defaultPort =55555;
    private static String defaultServer="localhost";
    public static void main(String[] args) {
        launch(args);
    }
    private void initView(Stage primaryStage, IServices server) throws IOException {
        FXMLLoader loginViewLoader = new FXMLLoader();
        loginViewLoader.setLocation(getClass().getResource("views/loginView.fxml"));
        AnchorPane anchorPane = loginViewLoader.load();
        primaryStage.setScene(new Scene(anchorPane));

        Controller controller= loginViewLoader.getController();
        controller.setServer(server);
    }

    public static void changeSceneToMainWindow(IServices service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartObjectClient.class.getResource("mainPage.fxml"));
            AnchorPane root = fxmlLoader.load();
            StartObjectClient.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            StartObjectClient.currentStage.setMinHeight(root.getPrefHeight());
            StartObjectClient.currentStage.setMinWidth(root.getPrefWidth());
            StartObjectClient.currentStage.setResizable(false);

            MainPageController controller = fxmlLoader.getController();
            controller.setService(service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeSceneToRegister(IServices service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartObjectClient.class.getResource("register.fxml"));
            AnchorPane root = fxmlLoader.load();
            StartObjectClient.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            StartObjectClient.currentStage.setMinHeight(root.getPrefHeight());
            StartObjectClient.currentStage.setMinWidth(root.getPrefWidth());
            StartObjectClient.currentStage.setResizable(false);

            RegisterController controller = fxmlLoader.getController();
            controller.setService(service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeSceneToLogin(IServices service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartObjectClient.class.getResource("login.fxml"));
            BorderPane root = fxmlLoader.load();
            StartObjectClient.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            StartObjectClient.currentStage.setMinHeight(root.getPrefHeight());
            StartObjectClient.currentStage.setMinWidth(root.getPrefWidth());
            StartObjectClient.currentStage.setResizable(false);
            StartObjectClient.currentStage.setMaximized(false);

            LoginController controller = fxmlLoader.getController();
            controller.setService(service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("server.host",defaultServer);
        int serverPort= defaultPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+ defaultPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        IServices server=new ServicesObjectProxy(serverIP, serverPort);

        StartObjectClient.currentStage = stage;

        FlightDbRepository flightDbRepository = new FlightDbRepository(props);
        UserDbRepository userDbRepository = new UserDbRepository(props);
        TicketDbRepository ticketDbRepository = new TicketDbRepository(props);

        Service service = new Service(flightDbRepository, ticketDbRepository, userDbRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        // HBox root = fxmlLoader.load();
        BorderPane root = fxmlLoader.load();
        // ApplicationController application.controller = fxmlLoader.getController();

        LoginController controller = fxmlLoader.getController();
        controller.setService(service);

        // application.controller.setService(serviceNetwork);

        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        stage.setTitle("OverSky");
        stage.setScene(scene);
        stage.setMinHeight(root.getPrefHeight());
        stage.setMinWidth(root.getPrefWidth());
        stage.setMaxHeight(root.getPrefHeight());
        stage.setMaxWidth(root.getPrefWidth());
        stage.setResizable(false);
        stage.show();
    }
}
