package clientfx.clientfx;

import clientfx.clientfx.gui.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/projectserver.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find projectserver.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService server = new ChatServicesRpcProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginW.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);




        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("ChatW.fxml"));
        Parent croot=cloader.load();


        ChatController chatCtrl =
                cloader.<ChatController>getController();
        chatCtrl.setServer(server);

        ctrl.setChatController(chatCtrl);
        ctrl.setParent(croot);

        primaryStage.setTitle("MPP chat");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();




    }


}
