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
public abstract class NumberFactory {
    private NumberFactory(){}
    
    public static _Number getNumber(short number){
        if(number==0){
            return new Zero();
        }
        
        if(number==1){
            return new One();
        }
        
        if(number==2){
            return new Two();
        }
        
        if(number==3){
            return new Three();
        }
        
        if(number==4){
            return new Four();
        }
        
        if(number==5){
            return new Five();
        }
        
        if(number==6){
            return new Six();
        }
        
        if(number==7){
            return new Seven();
        }
        
        if(number==8){
            return new Eight();
        }
        
        if(number==9){
            return new Nine();
        }
        
        return null;
    }
}
