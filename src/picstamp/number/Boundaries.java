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
public class Boundaries {
    private final int WIDTH, HEIGHT;
    
    public Boundaries(int height, int width){
        this.HEIGHT = height;
        this.WIDTH = width;
    }
    
    public int getWidth(){
        return this.WIDTH;
    }
    
    public int getHeight(){
        return this.HEIGHT;
    }
}
