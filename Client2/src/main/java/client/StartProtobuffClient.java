package client;

import client.gui.LoginController;
import client.gui.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.model.User;
import project.network.protobuffprotocol.ProtoProxy;
import project.services.IServices;

import java.io.IOException;
import java.util.Properties;


// DE SCHIMBAT IN CONTROLLERS APELUL DE LA CHANGE SCENES
public class StartProtobuffClient extends Application {
    public static Stage currentStage;
    private static int defaultPort =55555;
    private static String defaultServer="localhost";

    public static void changeSceneToMainWindow(User user, IServices service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProtobuffClient.class.getClassLoader().getResource("mainPage.fxml"));
            AnchorPane root = fxmlLoader.load();
            StartProtobuffClient.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            StartProtobuffClient.currentStage.setMinHeight(root.getPrefHeight());
            StartProtobuffClient.currentStage.setMinWidth(root.getPrefWidth());
            StartProtobuffClient.currentStage.setResizable(false);

            MainPageController controller = fxmlLoader.getController();
            controller.setService(user, service);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
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
     **/

    public static void changeSceneToLogin(IServices service) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProtobuffClient.class.getClassLoader().getResource("login.fxml"));
            BorderPane root = fxmlLoader.load();
            StartProtobuffClient.currentStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
            StartProtobuffClient.currentStage.setMinHeight(root.getPrefHeight());
            StartProtobuffClient.currentStage.setMinWidth(root.getPrefWidth());
            StartProtobuffClient.currentStage.setResizable(false);
            StartProtobuffClient.currentStage.setMaximized(false);

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
            clientProps.load(StartObjectClient.class.getResourceAsStream("/projectclient.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find projectclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("server.host",defaultServer);
        int serverPort=defaultPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch(NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);

        IServices server=new ProtoProxy(serverIP, serverPort);
        StartProtobuffClient.currentStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        BorderPane root = loginLoader.load();

        LoginController loginController = loginLoader.getController();
        loginController.setService(server);

        FXMLLoader mainPageLoader = new FXMLLoader(StartProtobuffClient.class.getClassLoader().getResource("mainPage.fxml"));
        AnchorPane rootMain = mainPageLoader.load();

        MainPageController mainPageController =
                mainPageLoader.getController();
        mainPageController.setServer(server);

        loginController.setController(mainPageController);


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