/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Kit
 */
public class DirectoryData
{
    ArrayList<String[]> dInfoArray;
    DefaultListModel dlm;
    DataHandler dataHandler;
    String strDnamesFile = "directory_names";
    String strDpathsFile = "directory_paths";
    DataInputStream dNameInput;
    DataInputStream dPathInput;
    DataOutputStream dNameOutput;
    DataOutputStream dPathOutput;
    DirectoryList master;
    int dirIndex;
    
    public DirectoryData(DirectoryList listMaster)
    {
        master = listMaster;
        dataHandler = new DataHandler();
        dInfoArray = new ArrayList();
        dlm = new DefaultListModel();
        fillDinfoArray();
        buildDlm();
        
    }
    
    public void buildDlm()
    {
            
        for (int i=0; i < dInfoArray.size(); i++)
        {
            dlm.addElement(dInfoArray.get(i)[0]);
        }
            
    }
    
    public DefaultListModel getDlm()
    {
        return dlm;
    }
    
    /**
     * Checks through array of directory names and folder paths to check if the
     * folder path entered as an argument has already been entered. Returns
     * true if it has, otherwise false. 
     * @param folderPath used to check against array values
     * @return 
     */
    public boolean isDuplicateDirectory(String folderPath)
    {
        boolean duplicate = false;
        if (dInfoArray!= null)
        {
            for (int i = 0; i < dInfoArray.size(); i++)
                if (dInfoArray.get(i)[1].equals(folderPath))
                {
                    duplicate = true;
                    break;
                }
        }
        return duplicate;  
    }
    
    public String getDirPath(int index)
    {
        return dInfoArray.get(index)[1];
    }
    
    public void setDirIndex(String folderPath)
    {
        for (int i = 0; i<dInfoArray.size(); i++)
        {
            String dir = dInfoArray.get(i)[1];
            if (folderPath.equals(dir))
            {
                dirIndex = i;
            }
        }
        System.out.println("folder path added to array index " + dirIndex);
        
    }
    
    /**
     * creates size 2 string array, adds folder name and path to the array
     * and then adds the array to dInfoArray array list. Calls the
     * sortArrayListStrArray method from the instantiated DataHandler object to
     * sort the array alphabetically by the folder name. setLocationFiles
     * is then called.
     * @param folder name of folder added to [0] of string array
     * @param folderPath directory pat added to [1] of string array.
     */
    public void addNewDir(String folder, String folderPath)
    {
        String[] dArray = new String[2];
        dArray[0] = folder;
        dArray[1] = folderPath;
        dInfoArray.add(dArray);
        dataHandler.sortArrayListStrArray(dInfoArray, 0);
        setLocationFiles();
    }
    
    public void removeDir(int dirIndex)
    {
        dInfoArray.remove(dirIndex);
        setLocationFiles();
    }
    
    /**
     * Iterates through dInfoArray comparing the folder name to the value
     * stored at [0] of the string array value at the index. If it does, then
     * the integer lastAddedIndex is set to the index number and returned.
     * @param folder
     * @return 
     */
    public int getLastAddedIndex(String folder)
    {
        int lastAddedIndex = -1;
        for(int i = 0; i < dInfoArray.size(); i++)
        {
            if (dInfoArray.get(i)[0].equals(folder))
            {
                lastAddedIndex = i;
                break;
            }
        }
        
        return lastAddedIndex;
    }
    
    /**
     * First creates two DataOutputStreams. One is to write to a file storing
     * the folder names, the other is to write to a file storing the folder
     * paths. The dInfoArray is then iterated through and the value of each
     * iteration is stored to the folder names file and folder paths file
     * respectively. Once the array has been iterated through, the output 
     * streams are closed (DataHandler methods are used to open 
     * and close the output streams). 
     */
    public void setLocationFiles()
    {
        try
        {
            dNameOutput = dataHandler.readFileOutput(strDnamesFile);
            dPathOutput = dataHandler.readFileOutput(strDpathsFile);
                for (int i = 0; i < dInfoArray.size(); i++)
                {
                    dNameOutput.writeUTF(dInfoArray.get(i)[0]);
                    dPathOutput.writeUTF(dInfoArray.get(i)[1]);
                }
        }
        catch (IOException ioe)
        {
                System.out.println("Could not open file to store location");
                System.exit(3);
                ioe.printStackTrace();
        }
        finally
        {
            dataHandler.closeFileOutPut(dNameOutput);
            dataHandler.closeFileOutPut(dPathOutput);
        }
    }
    
    /**
     * Wipes dInfoArray and then opens two DataInputStreams. One reads a file
     * holding directory names, the other reads the paths of each directory.
     * if the directory names file can be found, each file entry is add to a
     * string array and then the array is added to dInfo array. InputStreams 
     * are opened and closed with methods from the DataHandler object. 
     */
    public void fillDinfoArray()
    {
        dInfoArray = new ArrayList();
        dNameInput = dataHandler.readFileInput(strDnamesFile);
        dPathInput = dataHandler.readFileInput(strDpathsFile);
        if (dNameInput!=null)
        {
            try
            {
                while(true)
                {
                    String[] directoryInfo = new String[2];
                    if (dNameInput!=null)
                    {
                        String directoryName = dNameInput.readUTF();
                        String directoryPath = dPathInput.readUTF();
                        directoryInfo[0] = directoryName;
                        directoryInfo[1] = directoryPath;
                        dInfoArray.add(directoryInfo);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            catch (EOFException eof)
            {
                System.out.println("End of file");
            }
            catch (IOException ioe)
            {
                System.out.println("Error processing file at fillDinfoArray");
                System.exit(2);
            }
            finally
            {
                dataHandler.closeFileInput(dNameInput);
                dataHandler.closeFileInput(dPathInput);
                System.out.println("Closed files (fillDinfoArray)");
            }
        }
        
    }
}
