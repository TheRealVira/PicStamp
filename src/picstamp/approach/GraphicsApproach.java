/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import picstamp.FileManager;

/**
 *
 * @author vira
 */
class GraphicsApproach implements _Approach{

    @Override
    public void action(String baseFile, byte[] color) throws IOException {
        for (short i = 0; i < 10; i++) {
            BufferedImage image = ImageIO.read(new File(baseFile));
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(
                    new Color(ByteBuffer
                        .wrap(color)
                        .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                        .get()));
            
            Rectangle2D r2d = graphics.getFontMetrics(graphics.getFont()).getStringBounds(i + "", graphics);
            graphics.drawString(i + "", (int)(image.getWidth()/2-r2d.getX()/2), (int)(image.getHeight()/2-r2d.getY()/2));
            ImageIO.write(image, "bmp", new File(FileManager.generateCircleFilename(i)));
        }
    }
    
}
