package com.meti.client;

import com.meti.util.LoggerHandler;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientMain {
    private static final Logger logger = Logger.getLogger("Application");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClientInput clientInput = ClientInput.invoke(new ClientInput(scanner));
        InetAddress address = clientInput.getAddress();
        int port = clientInput.getPort();

        init(address, port);
        run();
    }

    public static void init(InetAddress address, int port) {
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);

        Handler handler = new LoggerHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);

        logger.addHandler(handler);
        logger.log(Level.INFO, "Initializing application");

        try {
            if(address != null && port != -1) {
                Socket socket = new Socket(address, port);
                ClientHandler clientHandler = new ClientHandler();
                clientHandler.perform(socket);
            }
            else{
                throw new RuntimeException("Invalid parameters passed into socket");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error in initializing util " + e);
            System.exit(-1);
        }
    }

    public static void run() {
        logger.log(Level.INFO, "Running application");
    }

    public static void stop() {
        //TODO: handle client stop
    }

    private static class ClientInput {
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
    }
}
