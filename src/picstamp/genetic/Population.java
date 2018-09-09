/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import picstamp.Bitmap;
import picstamp.Pair;
import static picstamp.approach.GeneticApproach.CHILDS_PER_GENERATION;
import picstamp.number.Position;

/**
 *
 * @author vira
 */
public class Population {
    private static Random RAND;
    public Bitmap[] children;
    
    public Population(Bitmap blankSlate, Random rand) throws CloneNotSupportedException{
        if(RAND==null){
            RAND = rand;
        }
        
        children = generateChildren(blankSlate);
    }
    
    private static Bitmap[] generateChildren(Bitmap blankSlate) throws CloneNotSupportedException{
        Bitmap[]children = new Bitmap[CHILDS_PER_GENERATION];
        for (int child = 0; child < CHILDS_PER_GENERATION; child++) {
            children[child] = (Bitmap)blankSlate.clone();
            for (int x = 0; x < blankSlate.getBoundaries().getWidth(); x++) {
                for (int y = 0; y < blankSlate.getBoundaries().getHeight(); y++) {
                    setRandomPixel(children[child], new Position(x, y));
                }
            }
        }
        
        return children;
    }
    
    private static void setRandomPixel(Bitmap bitmap, Position pos){
        if(RAND.nextBoolean()){
            bitmap.setPixel(
                    pos,
                    new byte[]{0x00});
            return;
        }

        bitmap.setPixel(
                pos,
                new byte[]{0x7F});
    }
    
    public List<Pair<Bitmap, Double>> getClosestChild(Bitmap goal){
        List<Pair<Bitmap, Double>> toRet = new ArrayList<>();
        
        for(Bitmap child: children){
            toRet.add(new Pair(child,compare(child, goal)));
        }
        
        Collections.sort(toRet,
                (Pair<Bitmap, Double> lhs, Pair<Bitmap, Double> rhs) -> lhs.u > rhs.u ? -1 : (lhs.u < rhs.u) ? 1 : 0
        );
        
        return toRet;
    }
    
    private static double compare(Bitmap child, Bitmap goal){
        double correct=0;
        for (int x = 0; x < child.getBoundaries().getWidth(); x++) {
            for (int y = 0; y < child.getBoundaries().getHeight(); y++) {
                Position pos = new Position(x,y);
                
                if(Arrays.equals(child.getPixel(pos), goal.getPixel(pos))){
                    correct++;
                    continue;
                }
                
                correct+=hasCorrectPixelSomewhere(child, pos);
            }
        }
        
        return correct/(child.getBoundaries().getHeight()*child.getBoundaries().getWidth());
    }
    
    private static double hasCorrectPixelSomewhere(Bitmap bitmap, Position pos){
        for (int x = -2; x < 3; x++) {
            for (int y = -2; y < 3; y++) {
                if((x==0&&y==0)||
                        pos.getX()+x<0||pos.getX()+x>bitmap.getBoundaries().getWidth()||
                        pos.getY()+y<0||pos.getY()+y>bitmap.getBoundaries().getHeight()){
                    continue;
                }
                
                if(Arrays.equals(bitmap.getPixel(pos), new byte[]{0x00})){
                    if(((x==-1||x==1)&&(y==-1||y==1))){
                        return 1;
                    }
                    
                    return .9d;
                }
            }
        }
        
        return 0;
    }
}
