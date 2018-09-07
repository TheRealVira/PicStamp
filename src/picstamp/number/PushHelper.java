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
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2-width/4,
                        height/10),
                new Position(
                        width/2+width/4,
                        height/10),
                color
        );
    }
    
    public static void pushUpperLeft(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2-width/4,
                        height+height/10),
                new Position(
                        width/2-width/4,
                        height/2),
                color
        );
    }
    
    public static void pushUpperRight(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2+width/4,
                        height+height/10),
                new Position(
                        width/2+width/4,
                        height/2),
                color
        );
    }
    
    public static void pushMiddle(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2-width/4,
                        height/2),
                new Position(
                        width/2+width/4,
                        height/2),
                color
        );
    }
    
    public static void pushLowerLeft(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2-width/4,
                        height/2),
                new Position(
                        width/2-width/4,
                        height-height/10),
                color
        );
    }
    
    public static void pushLowerRight(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2+width/4,
                        height/2),
                new Position(
                        width/2+width/4,
                        height-height/10),
                color
        );
    }
    
    public static void pushLower(Bitmap bitmap, byte[] color){
        Boundaries b = bitmap.getBoundaries();
        int width=b.getWidth(),
            height=b.getHeight();
        
        pushLine(
                bitmap,
                new Position(
                        width/2-width/4,
                        height-height/10),
                new Position(
                        width/2+width/4,
                        height-height/10),
                color
        );
    }
    
    private static void pushLine(Bitmap bitmap, Position from, Position to, byte[] color){
        do{
            bitmap.setPixel(from, color);
            
            Pair<Double, Double> normalized = 
                    Position.normalize(to.subtract(from));
            
            if(Math.abs(normalized.t)>Math.abs(normalized.u)){
                from.setX(from.getX()+(int)Math.floor(normalized.t));
                continue;
            }
            
            if(Math.abs(normalized.t)<Math.abs(normalized.u)){
                from.setY(from.getY()+(int)Math.floor(normalized.u));
                continue;
            }
            
            if(normalized.t!=0&&normalized.t.equals(Float.NaN)){
                from.setX(from.getX()+(int)Math.floor(normalized.t));
            }
            
        }while(!from.equals(to));
    }
}
