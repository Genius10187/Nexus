package com.meti.client;

import com.meti.util.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import static com.meti.client.ClientProperties.executorService;
import static com.meti.client.ClientProperties.logger;
public class ClientMain extends Application {
    private static final URL clientCreatorFXML = ClientMain.class
            .getResource("/fxml/ClientCreator.fxml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        initFXML();
    }

    private void initFXML() throws IOException {
        FXMLLoader loader = new FXMLLoader(clientCreatorFXML);
        Parent parent = loader.load();
        ClientCreator controller = loader.getController();

        controller.setExecutorService(executorService);
        Utility.buildStage(parent);

        logger.log(Level.INFO, "Initialized ClientCreator FXML");
    }

    @Override
    public void stop() {
        executorService.shutdown();

        //TODO: handler service
    }

    public static void main(String[] args) {
        launch(args);
    }
}
