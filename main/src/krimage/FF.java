/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author Kit
 */
public class FF implements FilenameFilter{

    @Override
    public boolean accept(File dir, String filename) 
    {
        String name = filename.toLowerCase();
        boolean valid = false;
        if (
            name.endsWith("jpg") ||
            name.endsWith("png") ||
            name.endsWith("gif") ||
            name.endsWith("jpeg")
            )
        {
            valid = true;
        }
        return valid; 
    }
}
