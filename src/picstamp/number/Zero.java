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
class Zero implements _Number {

    @Override
    public void push(Bitmap bitmap, byte[] color) {
        PushHelper.pushUpper(bitmap, color);
        PushHelper.pushUpperLeft(bitmap, color);
        PushHelper.pushUpperRight(bitmap, color);
        PushHelper.pushLowerLeft(bitmap, color);
        PushHelper.pushLowerRight(bitmap, color);
        PushHelper.pushLower(bitmap, color);
        
        PushHelper.pushLine(bitmap,
                new Position(bitmap.getBoundaries().getWidth()/3,
                        bitmap.getBoundaries().getHeight()/3),
                new Position(bitmap.getBoundaries().getWidth()-bitmap.getBoundaries().getWidth()/3,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/3), color);
        
        PushHelper.pushLine(bitmap,
                new Position(bitmap.getBoundaries().getWidth()-bitmap.getBoundaries().getWidth()/3,
                        bitmap.getBoundaries().getHeight()/3),
                new Position(bitmap.getBoundaries().getWidth()/3,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/3), color);
    }
    
}
