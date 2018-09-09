/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.number;

import picstamp.Pair;

/**
 *
 * @author vira
 */
public class Position {
    private final Pair<Integer, Integer> position;
    private Double Length;
    
    public Position(int x, int y){
        position = new Pair(x,y);
    }
    
    public int getX(){
        return this.position.t;
    }
    
    public int getY(){
        return this.position.u;
    }
    
    public void setX(int x){
        this.position.t = x;
    }
    
    public void setY(int y){
        this.position.u = y;
    }
    
    public Position add(Position point){
        return new Position(
            this.getX() -point.getX(),
            this.getY() -point.getY());
    }
    
    public Position subtract(Position point){
        return new Position(
            this.getX() -point.getX(),
            this.getY() -point.getY());
    }
    
    public double getLength(){
        if(this.Length==null){
            this.Length = Math.sqrt(
                    this.getX()*this.getX()+
                    this.getY()*this.getY());
        }
        
        return this.Length;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Position.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Position toComp = (Position) obj;
        if(toComp.getX()!=this.getX()||toComp.getY()!=this.getY()){
            return false;
        }

        return true;
    }
    
    public static Pair<Double, Double> normalize(Position point){
        return new Pair(
                    point.getX() / point.getLength(),
                    point.getY() / point.getLength());
    }
}
