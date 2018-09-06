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
abstract class PushHelper {
    public static void pushUpper(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()+bitmap.getBoundaries().getHeight()/10),
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()+bitmap.getBoundaries().getHeight()/10),
                color
        );
    }
    
    public static void pushUpperLeft(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()+bitmap.getBoundaries().getHeight()/10),
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                color
        );
    }
    
    public static void pushUpperRight(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()+bitmap.getBoundaries().getHeight()/10),
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                color
        );
    }
    
    public static void pushMiddle(Bitmap bitmap, byte[] color){
        
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                color
        );
    }
    
    public static void pushLowerLeft(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/10),
                color
        );
    }
    
    public static void pushLowerRight(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()/2),
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/10),
                color
        );
    }
    
    public static void pushLower(Bitmap bitmap, byte[] color){
        pushLine(
                bitmap,
                new Position(
                        bitmap.getBoundaries().getWidth()/2-bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/10),
                new Position(
                        bitmap.getBoundaries().getWidth()/2+bitmap.getBoundaries().getWidth()/4,
                        bitmap.getBoundaries().getHeight()-bitmap.getBoundaries().getHeight()/10),
                color
        );
    }
    
    private static void pushLine(Bitmap bitmap, Position from, Position to, byte[] color){
        //throw new NotImplementedException();
        bitmap.setPixel(to, color);
        bitmap.setPixel(from, color);
    }
}
