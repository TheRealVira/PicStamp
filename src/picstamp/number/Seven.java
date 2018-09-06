/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.number;

import picstamp.Bitmap;

/**
 *
 * @author vira
 */
class Seven implements _Number{

    @Override
    public void push(Bitmap bitmap, byte[] color) {
        PushHelper.pushUpper(bitmap, color);
        PushHelper.pushUpperRight(bitmap, color);
        PushHelper.pushLowerRight(bitmap, color);
    }
    
}
