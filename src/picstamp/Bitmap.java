/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picstamp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import picstamp.number.Boundaries;
import picstamp.number.Position;

/**
 * Header = 14 bytes
 * |-> SIGNATUR =   2 bytes
 * |-> FILESIZE =   4 bytes
 * |-> RESERVED =   4 bytes
 * |-> DaOFFSET =   4 bytes
 * InfoHeader = 40 bytes
 * |-> SIZE     =   4 bytes =   int
 * |-> WIDTH    =   2 bytes =   short
 * |-> HEIGHT   =   2 bytes =   short
 * |-> PLANES   =   2 bytes =   short
 * |-> BpP      =   2 bytes =   short
 * @author vira
 */
public class Bitmap {
    private byte[] betweenData;
    private byte[] pixelData;
    
    /*
        FILE - HEADER
    */
    private short SIGNATURE;
    private int FILE_SIZE;
    private int RESERVED;
    private int DATA_OFFSET;
    
    /*
        INFO - HEADER
    */
    private int HEADER_SIZE;
    private int WIDTH;
    private int HEIGHT;
    private short PLANES;
    private short BpP;
    private int COMPRESSION;
    private int BITMAP_DATA_SIZE;
    private int HORIZONTAL_RESOLUTION;
    private int VERTICAL_RESOLUTION;
    private int COLORS_IN_COLORPALETTE;
    private int IMPORTANT_COLORS_COUNT;
    
    public Boundaries getBoundaries(){
        return new Boundaries(HEIGHT-1, WIDTH-1);
    }
    
    private byte[] test;
    
    public Bitmap(String file){
        try {
            load(FileManager.loadFile(file));
        } catch (IOException ex) {
            Logger.getLogger(Bitmap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void load(byte[] source){
        loadHeader(source);
        loadDIB_Header(source);
        
        betweenData  = copySubArray(
                source,
                0x0E + HEADER_SIZE,
                DATA_OFFSET - 0x0E - HEADER_SIZE);
        pixelData   = copySubArray(source, DATA_OFFSET, source.length-DATA_OFFSET);
        
        test = source;
    }
    
    private void loadHeader(byte[] source){
        SIGNATURE    = ByteBuffer
            .wrap(copySubArray(source, 0x00, 0x02))
            .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getShort();
        FILE_SIZE    = ByteBuffer
            .wrap(copySubArray(source, 0x02, 0x04))
            .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        RESERVED    = ByteBuffer
            .wrap(copySubArray(source, 0x06, 0x04))
            .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        DATA_OFFSET = ByteBuffer
                .wrap(copySubArray(source, 0x0A, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }
    
    private void loadDIB_Header(byte[] source){
        HEADER_SIZE = ByteBuffer
                .wrap(copySubArray(source, 0x0E, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        WIDTH   = ByteBuffer
                .wrap(copySubArray(source, 0x12, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        HEIGHT  = ByteBuffer
                .wrap(copySubArray(source, 0x16, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        PLANES  = ByteBuffer
                .wrap(copySubArray(source, 0x1A, 0x02))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getShort();
        BpP     = ByteBuffer
                .wrap(copySubArray(source, 0x1C, 0x02))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getShort();
        COMPRESSION = ByteBuffer
                .wrap(copySubArray(source, 0x1E, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        BITMAP_DATA_SIZE = ByteBuffer
                .wrap(copySubArray(source, 0x22, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        HORIZONTAL_RESOLUTION = ByteBuffer
                .wrap(copySubArray(source, 0x26, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        VERTICAL_RESOLUTION = ByteBuffer
                .wrap(copySubArray(source, 0x2A, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        COLORS_IN_COLORPALETTE = ByteBuffer
                .wrap(copySubArray(source, 0x2E, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
        IMPORTANT_COLORS_COUNT = ByteBuffer
                .wrap(copySubArray(source, 0x32, 0x04))
                .order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt();
    }
    
    public void save(String name) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        saveHeader(baos);
        saveDIBHeader(baos);
        baos.write(betweenData);
        baos.write(pixelData);
        FileManager.saveFile(name, baos.toByteArray());
    }
    
    private void saveHeader(ByteArrayOutputStream baos) throws IOException{
        baos.write(ByteBuffer
                .allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(SIGNATURE).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(FILE_SIZE).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(RESERVED).array());
        baos.write(ByteBuffer.
                allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(DATA_OFFSET).array());
    }
    
    private void saveDIBHeader(ByteArrayOutputStream baos) throws IOException{
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(HEADER_SIZE).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(WIDTH).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(HEIGHT).array());
        baos.write(ByteBuffer
                .allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(PLANES).array());
        baos.write(ByteBuffer
                .allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort(BpP).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(COMPRESSION).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(BITMAP_DATA_SIZE).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(HORIZONTAL_RESOLUTION).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(VERTICAL_RESOLUTION).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(COLORS_IN_COLORPALETTE).array());
        baos.write(ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(IMPORTANT_COLORS_COUNT).array());
    }
    
    public void setPixel(Position pos, byte[] color){
        if(isOutOfBounds(pos)){
            return;
        }
        
        overrideSubArray(pixelData, getIndexOfPosition(pos), color);
    }
    
    public byte[] getPixel(Position pos){
        return copySubArray(pixelData, getIndexOfPosition(pos), BpP/8);
    }
    
    private boolean isOutOfBounds(Position pos){
        return pos.getX()<0||
                pos.getX()>=WIDTH || 
                pos.getY()<0 || 
                pos.getY()>=HEIGHT;
    }
    
    private int getIndexOfPosition(Position pos){
        return (HEIGHT-pos.getY()-1)*HEIGHT+pos.getX()*(BpP/8);
    }
    
    private static byte[] copySubArray(byte[] source, int index, int size){
        byte[] toRet = new byte[size];
        System.arraycopy(source, index, toRet, 0, size);
        return toRet;
    }
    
    private static void overrideSubArray(byte[] source, int index, byte[] paste){
        for (int i = index; i < index+paste.length; i++) {
            source[i]=paste[i-index];
        }
    }
}