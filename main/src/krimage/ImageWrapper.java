/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kit
 */
public class ImageWrapper {
    
    ArrayList<ImageObj> imgArray;
    String directory;
            
    public ImageWrapper(String locationPath, 
                        int panelSize, 
                        ImageGrid grid, 
                        ProgressBar progressBar)
    {
        imgArray = new ArrayList();
        directory = locationPath;
        
       
    }
    
    public ArrayList<ImageObj> getImgs()
    {
        return imgArray;
    }
    public String getLocation()
    {
        return directory;
    }
}
