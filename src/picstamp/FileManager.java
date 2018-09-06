/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author vira
 */
public abstract class FileManager {
    private FileManager(){}
    
    public static String generateCircleFilename(short number){
        return new StringBuilder()
                .append("./output_image")
                .append(number)
                .append(".bmp").toString();
    }
    
    public static void saveFile(String filename, byte[] data) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        }
    }
    
    public static byte[] loadFile(String filename) throws IOException{
        return Files.readAllBytes(Paths.get(filename));
    }
}
