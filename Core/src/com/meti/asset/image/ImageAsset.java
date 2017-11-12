package com.meti.asset.image;

import com.meti.asset.Asset;
import com.meti.util.Buffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/11/2017
 */
public class ImageAsset extends Asset {
    public ImageAsset(File location) throws IOException {
        super(location);

        BufferedImage image = ImageIO.read(location);

        //should work
        Buffer<Buffer<Integer>> content = new Buffer<>(image.getWidth(), image.getHeight());
        for (int x = 0; x < content.size(); x++) {
            for (int y = 0; y < content.get(x).size(); y++) {
                content.get(x).put(y, image.getRGB(x, y));
            }
        }

        setContent(content);
    }
}
