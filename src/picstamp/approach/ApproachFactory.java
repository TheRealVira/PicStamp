/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

/**
 *
 * @author vira
 */
public abstract class ApproachFactory {
    
    public static final String GRAPHICAL_APPROACH = "GRAPHICS";
    public static final String BITWISE_APPROACH = "BITWISE";
    
    private ApproachFactory(){}
    
    public static _Approach getApproach(String approach){
        if(approach==null){
            return null;
        }
        
        if(approach.toUpperCase().equals(GRAPHICAL_APPROACH)){
            return new GraphicsApproach();
        }
        
        if(approach.toUpperCase().equals(BITWISE_APPROACH)){
            return new BitwiseApproach();
        }
        
        return null;
    }
}
