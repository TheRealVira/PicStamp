/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.number;

/**
 *
 * @author vira
 */
public class Position {
    private final int X, Y;
    
    public Position(int x, int y){
        this.X = x;
        this.Y = y;
    }
    
    public int getX(){
        return this.X;
    }
    
    public int getY(){
        return this.Y;
    }
}
