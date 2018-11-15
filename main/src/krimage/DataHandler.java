/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Kit
 */
public class DataHandler {
    public DataHandler()
    {
        
    }
    
    public boolean isDuplicateStrAinList(String folderPath, ArrayList<String[]> array, int stringIndex)
    {
        boolean duplicate = false;
        if (array!= null)
        {
            for (int i = 0; i < array.size(); i++)
                if (array.get(i)[stringIndex].equals(folderPath))
                {
                    duplicate = true;
                }
        }
        return duplicate;  
    }
    
    public DataInputStream readFileInput(String filename)
    {
        DataInputStream aNameInput = null;
        try
        {
            aNameInput = new DataInputStream(new FileInputStream(filename));
        }
        catch (IOException ioe)
        {
            aNameInput = null;
        }
        return aNameInput;
    }
    
    public DataOutputStream readFileOutput(String filename)
    {
        DataOutputStream aNameOutput = null;
        try
        {
            aNameOutput = new DataOutputStream(new FileOutputStream(filename));
        }
        catch (IOException ioe)
        {
            System.out.println("File containing location paths could not be found" +ioe);
            ioe.printStackTrace();
        }
        return aNameOutput;
    }
    
    
    public void closeFileInput(DataInputStream input)
    {
        try
        {
            input.close();
        }
            catch(IOException ioe)
        {
            System.out.println("File failed to close");
            System.exit(1);
        }
    }
    public void closeFileOutPut(DataOutputStream output)
    {
        try
        {
            output.close();
        }
            catch(IOException ioe)
        {
            System.out.println("File failed to close");
            System.exit(1);
        }
    }
    public void sortArrayListStrArray(
            ArrayList<String[]> stringArray,
            int strArrayIndex)
    {          
        Collections.sort(stringArray,new Comparator<String[]>() 
        {
            public int compare(String[] strings, String[] otherStrings)
            {

                return strings[strArrayIndex].compareTo(otherStrings[strArrayIndex]);
            }
        });
    }
}
