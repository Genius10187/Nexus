package com.meti.asset.image;

import com.meti.asset.Asset;
import com.meti.asset.AssetBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/11/2017
 */

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
        int[][] content = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                content[x][y] = image.getRGB(x, y);
            }
        }

        Asset asset = new ImageAsset(file);
        asset.setContent(content);
        return asset;
    }
}
