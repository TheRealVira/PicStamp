/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

import java.io.IOException;
import picstamp.Bitmap;
import picstamp.FileManager;
import picstamp.number.NumberFactory;

/**
 *
 * @author vira
 */
class BitwiseApproach implements _Approach{

    @Override
    public void action(String baseFile, byte[] color) throws IOException {
        for (int i = 0; i < 10; i++) {
            Bitmap base = new Bitmap(baseFile);
            
            NumberFactory.getNumber((short) i).push(base, color);
            base.save(FileManager.generateCircleFilename((short)i));
        }
    }
    
}
