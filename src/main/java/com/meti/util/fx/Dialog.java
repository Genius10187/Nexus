package com.meti.util.fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/3/2017
 */
public class Dialog {

  private static final File dialogFile = new File("/fxml/util/Dialog.fxml");

  @FXML
  private TextArea output;

  public static Dialog loadDialog() throws IOException {
    FXMLLoader loader = new FXMLLoader(dialogFile.toURI().toURL());
    Parent parent = loader.load();
    Dialog controller = loader.getController();
    Scene scene = new Scene(parent);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.show();

    return controller;
  }

  public void setException(Exception e) {
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    setMessage(writer.toString());
  }

  public void setMessage(String string) {
    output.setText(string);
  }
}
