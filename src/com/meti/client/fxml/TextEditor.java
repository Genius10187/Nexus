package com.meti.client.fxml;

import com.meti.client.Client;
import com.meti.server.asset.Asset;
import com.meti.server.asset.text.TextChange;
import com.meti.util.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import static com.meti.Main.getInstance;
import static com.meti.util.Utility.castIfOfInstance;

public class TextEditor extends Editor {
    @FXML
    private TextArea display;

    private Asset<?> asset;

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

    public void setAsset(Asset<?> asset) {
        this.asset = asset;
    }

    private void init(Asset<?> asset) {
        Object content = asset.getContent();
        ArrayList<?> textList = castIfOfInstance(content, ArrayList.class);
        textList.forEach((Consumer<Object>) o -> display.appendText(castIfOfInstance(o, String.class) + "\n"));
    }

    @FXML
    public void update(KeyEvent event) throws IOException {
        TextChange change = new TextChange(asset.getFile().getPath());
        String c = event.getText();
        change.setType(TextChange.CHAR_ADDED);
        change.setCharacter(c);

        client.send(change, true);
    }
}
