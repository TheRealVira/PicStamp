/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import picstamp.approach.ApproachFactory;

/**
 *
 * @author vira
 */
public class PicStamp {
    /*
        SETTINGS
    */
    private static final String BASE_NAME = "/home/vira/NetBeansProjects/PicStamp/src/picstamp/base.bmp";
    private static final String APPROACH = ApproachFactory.BITWISE_APPROACH;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            ApproachFactory
                    .getApproach(APPROACH)
                    .action(
                            BASE_NAME,
                            new byte[]{0x0}
                    );
        } catch (IOException ex) {
            Logger.getLogger(PicStamp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
