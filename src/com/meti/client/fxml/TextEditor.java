package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.asset.Asset;
import com.meti.server.asset.AssetChange;
import com.meti.server.asset.text.TextChange;
import com.meti.util.Utility;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static com.meti.Main.getInstance;
import static com.meti.util.Utility.castIfOfInstance;

public class TextEditor extends Editor implements Initializable {
    @FXML
    private TextArea display;

    private int counter = 60;

    @Override
    public void update(AssetChange change) {
        if (change instanceof TextChange) {
            display.setText(((TextChange) change).getValue());
        }
    }

    @Override
    public Class[] getAssetChangeClasses() {
        return new Class[]{
                TextChange.class
        };
    }

    @Override
    public String[] getExtensions() {
        return new String[]{
                "txt"
        };
    }

    @Override
    public void load(Asset<?> asset, Client client) {
        TextEditor controller = Utility.castIfOfInstance(getInstance().buildStage("assets\\fxml\\TextEditor.fxml", null), TextEditor.class);
        controller.setAsset(asset);
        controller.setClient(client);

        castIfOfInstance(controller, TextEditor.class).init(asset);
    }

    private void init(Asset<?> asset) {
        Object content = asset.getContent();
        display.setText(Utility.castIfOfInstance(content, String.class));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                counter--;

                if (counter == 0) {
                    if (client != null && display != null && asset != null) {
                        TextChange change = new TextChange(asset.getFile().getPath());
                        change.setValue(display.getText());

                        try {
                            client.send(change, true);


                        } catch (IOException e) {
                            getInstance().log(Level.WARNING, e);
                        }
                    }
                    counter = 60;
                }
            }
        };

        timer.start();
    }

    @FXML
    public void update(KeyEvent event) {
        counter = 60;
    }
}
