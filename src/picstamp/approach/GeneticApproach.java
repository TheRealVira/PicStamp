/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import picstamp.Bitmap;
import picstamp.FileManager;
import picstamp.Pair;
import static picstamp.approach.MaskApproach.FILE_BASENAME;
import static picstamp.approach.MaskApproach.MASKS;
import static picstamp.approach.MaskApproach.mask;
import picstamp.number.Position;

/**
 *
 * @author vira
 */
public class GeneticApproach implements _Approach{
    
    private static Random RAND;
    
    GeneticApproach(){if(RAND==null){
        RAND = new Random();
    }}
    
    public static int GENERATIONS = 1000;
    public static int CHILDS_PER_GENERATION = 20;
    public static double MUTATION_RATE = .5d;
    public static double CROSSOVER_RATE = .8d;
    
    @Override
    public void action(String baseFile, byte[] color) throws IOException {
        for (int i = 0; i < 10; i++) {
            Bitmap base = new Bitmap(baseFile);
            Bitmap goal = new Bitmap(MASKS+FILE_BASENAME+i+".bmp");
            
            Bitmap[]children=new Bitmap[0];
            try {
                children = generateChildren(new Bitmap("./blank_slate.bmp"));
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GeneticApproach.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            List<Pair<Bitmap, Double>> sortedChildren = getClosestChild(children, goal);
            for (int generation = 0; generation < GENERATIONS; generation++) {
                Bitmap newGen = breed(sortedChildren.get(0).t,
                        sortedChildren.get(1).t,
                        new Bitmap("./blank_slate.bmp"));
                
                try {
                    children = mutateChildren(newGen);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(GeneticApproach.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                sortedChildren = getClosestChild(children, goal);
                if(sortedChildren.get(0).u==1.0d){
                    // Already achieved goal image.
                    children[0] = sortedChildren.get(0).t;
                    
                    break;
                }
                
                if(generation==GENERATIONS){
                    children[0] = sortedChildren.get(0).t;
                }
            }
            
            mask(base, children[0], color);
            base.save(FileManager.generateCircleFilename((short)i));
        }
    }
    
    private static Bitmap[] mutateChildren(Bitmap blankSlate) throws CloneNotSupportedException{
        Bitmap[]children = new Bitmap[CHILDS_PER_GENERATION];
        for (int child = 0; child < CHILDS_PER_GENERATION; child++) {
            children[child] = (Bitmap)blankSlate.clone();
            mutate(children[child]);
        }
        
        return children;
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
    
    private static List<Pair<Bitmap, Double>> getClosestChild(Bitmap[] children, Bitmap goal){
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
                if(Arrays.equals(child.getPixel(new Position(x, y)), goal.getPixel(new Position(x, y)))){
                    correct++;
                }
            }
        }
        
        return correct/(child.getBoundaries().getHeight()*child.getBoundaries().getWidth());
    }
    
    private static Bitmap breed(Bitmap parent1, Bitmap parent2, Bitmap base){
        /*
        i = 0...100
        j = 0...1600
        r1 = randomBool(50%)
        r2 = randomBool(5%) <- variierbar
        children[i][j] = r2 ? r1 : (r1 ? predator1[j] : predator2[j])*/
        for (int x = 2; x < parent1.getBoundaries().getWidth(); x++) {
            for (int y = 2; y < parent1.getBoundaries().getHeight(); y++) {
                Position pos = new Position(x, y);
                
                base.setPixel(new Position(x, y), RAND.nextDouble()<.05d?
                        parent1.getPixel(pos):
                        parent2.getPixel(pos));
            }
        }
        
        return base;
    }
    
    private static void mutate(Bitmap bitmap){
        int x = bitmap.getBoundaries().getWidth();
        int y = bitmap.getBoundaries().getHeight();
        
        x *= RAND.nextDouble();
        y *= RAND.nextDouble();
        
        if(Arrays.equals(bitmap.getPixel(new Position(x, y)), new byte[]{0x0})){
            bitmap.setPixel(
                new Position(x, y), new byte[]{0x7F});
        }
        else{
            bitmap.setPixel(
                new Position(x, y), new byte[]{0x0});
        }
        
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
}
