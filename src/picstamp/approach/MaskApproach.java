/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

import java.io.IOException;
import java.util.Arrays;
import picstamp.Bitmap;
import picstamp.FileManager;
import picstamp.number.Position;

/**
 *
 * @author vira
 */
class MaskApproach implements _Approach {
    
    public static String MASKS = "./masks";
    public static String FILE_BASENAME = "/output_image";

    @Override
    public void action(String baseFile, byte[] color) throws IOException {
        for (int i = 0; i < 10; i++) {
            Bitmap base = new Bitmap(baseFile);
            Bitmap mask = new Bitmap(MASKS+FILE_BASENAME+i+".bmp");
            
            for (int x = 0; x < mask.getBoundaries().getWidth(); x++) {
                for (int y = 0; y < mask.getBoundaries().getHeight(); y++) {
                    byte[] currentPixel = mask.getPixel(new Position(x, y));
                    if(Arrays.equals(currentPixel,new byte[]{0x00})){
                        base.setPixel(new Position(x, y), color);
                    }
                }
            }
            
            base.save(FileManager.generateCircleFilename((short)i));
        }
    }
    
}
