package com.meti.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class ClientMain extends Application {
    private static final File clientCreatorFXML = new File("assets\\fxml\\ClientCreator.fxml");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    /*    Scanner scanner = new Scanner(System.in);

        ClientInput clientInput = ClientInput.invoke(new ClientInput(scanner));
        InetAddress address = clientInput.getAddress();
        int port = clientInput.getPort();*/

        //10/10 throws an exception

        //TODO: add client creator
        FXMLLoader loader = new FXMLLoader(clientCreatorFXML.toURI().toURL());
        Parent parent = loader.load();
        ClientCreator controller = loader.getController();

        //TODO: put in utility file
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();


    }

    /*private static class ClientInput {
        private Scanner scanner;
        private InetAddress address;
        private int port;

        public ClientInput(Scanner scanner) {
            this.scanner = scanner;
        }

        public InetAddress getAddress() {
            return address;
        }

        public int getPort() {
            return port;
        }

        public static ClientInput invoke(ClientInput clientInput) {
            System.out.println("Enter in an IP address to connect to:");

            boolean addressValid = false;
            clientInput.address = null;
            do {
                try {
                    String addressString = clientInput.scanner.nextLine();
                    clientInput.address = InetAddress.getByName(addressString);

                    addressValid = true;
                } catch (UnknownHostException e) {
                    System.out.println("Invalid address");
                }
            } while (!addressValid);

            System.out.println("Enter in a port to connect to");

            boolean portValid = false;
            clientInput.port = -1;
            do {
                try {
                    String portString = clientInput.scanner.nextLine();
                    clientInput.port = Integer.parseInt(portString);

                    portValid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid port");
                }
            } while (!portValid);
            return clientInput;
        }
    }*/
}
