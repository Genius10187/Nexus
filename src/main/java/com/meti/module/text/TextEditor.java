package com.meti.module.text;

import com.meti.client.editor.Editor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.File;

public class TextEditor implements Editor {

  @FXML
  private TextArea output;

  @Override
  public String[] getExtensions() {
    return new String[]{
        "txt"
    };
  }

  @Override
  public void load(File location) {
    //TODO: handle textEditor file

    output.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

      }
    });
  }
}
