package com.meti.client;

import com.meti.util.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain extends Application {

  private static final URL clientCreatorFXML = ClientMain.class
          .getResource("/fxml/client/ClientCreator.fxml");

  private final ExecutorService executorService = Executors.newCachedThreadPool();

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

    FXMLLoader loader = new FXMLLoader(clientCreatorFXML);
    Parent parent = loader.load();
    ClientCreator controller = loader.getController();

    controller.setExecutorService(executorService);

    //TODO: put in utility file
    Utility.buildStage(parent);
  }

  @Override
  public void stop() throws Exception {
    executorService.shutdown();

    //TODO: handler service
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
