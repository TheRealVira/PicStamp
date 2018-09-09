/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp.approach;

import java.io.IOException;
import java.util.Arrays;
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
import picstamp.genetic.Population;
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
    public static double MUTATION_RATE = .05d;
    
    // Needs to be divisible by 4
    public static final int CHILDS_PER_GENERATION = 100;
    
    @Override
    public void action(String baseFile, byte[] color) throws IOException {
        for (int i = 0; i < 10; i++) {
            Bitmap base = new Bitmap(baseFile);
            Bitmap goal = new Bitmap(MASKS+FILE_BASENAME+i+".bmp");
            
            Population pop = null;
            try {
                pop = new Population(new Bitmap("./blank_slate.bmp"), RAND);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(GeneticApproach.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int generation = GENERATIONS;
            
            List<Pair<Bitmap,Double>>ordered=pop.getClosestChild(goal);
            while((generation--)>0||ordered.get(0).u==1.0d){
                try {
                    pop.children = breed(
                            ordered.subList(0, CHILDS_PER_GENERATION-1),
                            "./blank_slate.bmp");
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(GeneticApproach.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ordered = pop.getClosestChild(goal);
                
                System.out.println(i+" : FIT : "+ ordered.get(0).u);
            }
            
            mask(base, ordered.get(0).t, color);
            base.save(FileManager.generateCircleFilename((short)i));
        }
    }
    
    private static Bitmap[] breed(List<Pair<Bitmap, Double>> parents, String blankFile) throws CloneNotSupportedException{
        Bitmap[] toRet = new Bitmap[CHILDS_PER_GENERATION];
        
        int counter = 0;
        for (int i = 1; i < parents.size()*.4d; i++) {
            for (int child = 0; child < 2; child++) {
                Bitmap dolly = (Bitmap) parents.get(i).t.clone();
                for (int x = 2; x < parents.get(i).t.getBoundaries().getWidth(); x++) {
                    for (int y = 2; y < parents.get(i).t.getBoundaries().getHeight(); y++) {
                        if(RAND.nextBoolean()){
                            continue;
                        }
                        
                        Position pos = new Position(x, y);
                        
                        dolly.setPixel(pos, parents.get(0).t.getPixel(pos));
                    }
                }
                
                int mutateRatio = (int)(dolly.getBoundaries().getHeight()*dolly.getBoundaries().getWidth()*MUTATION_RATE);
                for (int j = 0; j < mutateRatio; j++) {
                    mutate(dolly);
                }
                toRet[counter++]=dolly;
            }
            
            toRet[toRet.length-i-1] = parents.get(i).t;
        }
        
        toRet[toRet.length-1] = parents.get(0).t;
        
        return toRet;
    }
    
    private static void mutate(Bitmap bitmap){
        if(RAND.nextDouble()>=MUTATION_RATE){
            return;
        }
        
        int x = bitmap.getBoundaries().getWidth();
        int y = bitmap.getBoundaries().getHeight();

        x *= RAND.nextDouble();
        y *= RAND.nextDouble();
        
        Position pos = new Position(x,y);
        
            bitmap.setPixel(
                    pos,
                    new byte[]{0x00});

    }
}
