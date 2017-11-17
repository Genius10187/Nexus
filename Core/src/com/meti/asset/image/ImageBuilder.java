package com.meti.asset.image;

import com.meti.asset.Asset;
import com.meti.asset.AssetBuilder;
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

//TODO: handle images
public class ImageBuilder implements AssetBuilder {
    @Override
    public String[] getExtensions() {
        return new String[]{
                "jpeg"
        };
    }

    //there should be a purpose to this class
    @Override
    public Asset build(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);

        //should work
        Buffer<Buffer<Integer>> content = new Buffer<>(image.getWidth(), image.getHeight());
        for (int x = 0; x < content.size(); x++) {
            //and here too!
            for (int y = 0; y < content.get(x).size(); y++) {
                //TODO: handle generics here
                content.get(x, c).put(y, image.getRGB(x, y), Buffer.class);
            }
        }

        Asset asset = new ImageAsset(file);
        asset.setContent(content);
        return asset;
    }
}
