package com.meti.client.editor;

import com.meti.asset.Asset;
import com.meti.client.Editor;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.io.File;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/14/2017
 */
public class ImageEditor extends Editor {
    @FXML
    private ImageView imageView;

    @Override
    public File getLocation() {
        return new File("Client\\assets\\fxml\\editor\\ImageEditor.fxml");
    }

    @Override
    public String[] getExtensions() {
        //there's a whole bunch of them
        return new String[]{
                "jpeg",
                "jpg",
                "img",
                "png"
        };
    }

    @Override
    public void load(Asset asset) {
        int[][] pixels = (int[][]) asset.getContent();

        WritableImage image = new WritableImage(pixels.length, pixels[0].length);
        PixelWriter writer = image.getPixelWriter();
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                //pixels is of rgb, not argb, may not work
                //NEVER MIND
                writer.setArgb(x, y, pixels[x][y]);
            }
        }

        imageView.setImage(image);
    }
}
