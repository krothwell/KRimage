/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;

/**
 *
 * @author Kit
 */
public class ImageAlbumData //extends AbstractListModel
{
    DataHandler dataHandler = new DataHandler();
    DataInputStream aNameInput;
    DataInputStream aFileInput;
    DataInputStream aFilesInput;
    DataInputStream aCountInput;
    DataOutputStream aNameOutput;
    DataOutputStream aFileOutPut;
    DataOutputStream aFilesOutput;
    DataOutputStream aCountOutput;
    String strAnamesFile = "album_names"; 
    String strAfilesFile = "album_files"; 
    ArrayList <String []> aInfoArray;
    String noFileYet = "NoFileYet";
    int lastElementProcessedIndex;
    DefaultListModel dlm;
    
    public ImageAlbumData()        
    {

        aInfoArray = new ArrayList();
        fillAinfoArray();
        dlm = new DefaultListModel();
        buildDlm();
    }
    
    //@Override
    public int getSize() {
        if (aInfoArray == null)
        {
            this.fillAinfoArray();
        }
        return aInfoArray.size();
    }
    
    public void buildDlm()
    {
            
        for (int i=0; i < aInfoArray.size(); i++)
        {
            dlm.addElement(aInfoArray.get(i)[0]);
        }
            
    }
    
    public DefaultListModel getDlm()
    {
        return dlm;
    }
    
    /**
     * builds DefaultListModel using values from array entered as argument
     * @param arrayList
     * @return 
     */
    public DefaultListModel buildDlm(ArrayList<String> arrayList)
    {
        DefaultListModel aDlm = new DefaultListModel();
        for (int i=0; i < arrayList.size(); i++)
        {
            aDlm.addElement(arrayList.get(i));
        }
        return aDlm;
    }
    
    public int getLastAddedIndex()
    {
        return lastElementProcessedIndex;
    }
    
    //@Override
    public Object getElementAt(int index) 
    {        
        if (aInfoArray == null)
        {
            this.fillAinfoArray();
        }
        //System.out.println("data is " + aInfoArray.get(index)[0]);
        return aInfoArray.get(index)[0];
    }
    
    public boolean isDuplicateAlbum(String albumName)
    {
        return dataHandler.isDuplicateStrAinList(albumName, aInfoArray, 0);
    }
    
    /**
     * calls fillAinfoArray to ensure that the existing names have been added,
     * adds string array to array putting the album name at [0] and the default 
     * string stored in noFileYet at [1] , then calls 
     * dataHandler.sortArrayListStrArray. It then writes over file "album_names"
     * and "album_Files" with array values.
     */
    public void addNewAlbum(String albumName)
    {
        if (!dataHandler.isDuplicateStrAinList(albumName, aInfoArray, 0))
        {
            fillAinfoArray();        
            String[] albumInfo = new String[2];
            albumInfo[0] = albumName;
            albumInfo[1] = noFileYet;
            aInfoArray.add(albumInfo);
            dataHandler.sortArrayListStrArray(aInfoArray, 0);
            setAlbumToFiles(albumName);
        }
    }
    
    /**
     * calls fillAinfoArray to ensure that the existing names have been added,
 checks through array to find string and then removes the array entry
 and then calls setAlbumToFiles to overwrite the files with new info. 
     * @param albumName
     * @return 
     */
    public void removeAlbum(int index)
    {
        fillAinfoArray();
        String albumName = aInfoArray.get(index)[0];
        String albumFileName = aInfoArray.get(index)[1];
        File albumFile = new File(albumFileName);
        Path path = FileSystems.getDefault().getPath(albumFileName);
        try
        {
            Files.delete(path); 
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
         
        aInfoArray.remove(index);
        setAlbumToFiles(albumName);
        System.gc();
    }
    
    public void renameAlbum(int index, String newName)
    {
        fillAinfoArray();
        aInfoArray.get(index)[0] = newName;
        setAlbumToFiles(newName);
    }
    
    public void setAlbumToFiles(String albumName)
    {
        aNameOutput = dataHandler.readFileOutput(strAnamesFile);
        aFilesOutput = dataHandler.readFileOutput(strAfilesFile);
        for (int i =0; i < aInfoArray.size(); i++)
            try
            {
                if (aInfoArray.get(i)[0].equals(albumName))
                {
                    lastElementProcessedIndex = i;
                }
                aNameOutput.writeUTF(aInfoArray.get(i)[0]);
                aFilesOutput.writeUTF(aInfoArray.get(i)[1]);
            }
            catch (IOException ioe)
            {
                System.out.println("Could not open file to store location"
                + " (addNewAlbum)");
                ioe.printStackTrace();
                System.exit(3);
            }
            finally
            {     
                System.out.println("Finished writing to files (addNewAlbum");
            }
            dataHandler.closeFileOutPut(aNameOutput);
            dataHandler.closeFileOutPut(aFilesOutput);
    }
    
    /**
     * use the aInfoArray to check through file names. Read through all of these
     * files to see if they contain the image album path. If they do add the 
     * album name to album assigned array, otherwise an album unassigned array.
     * @return imageAlbumsData array list of 2 string array lists with albums
     * assigned and unassigned
     */
    public ArrayList[] getImagesAlbumsArray(ImageObj img)
        {
            fillAinfoArray();
            String imgPath = img.getFile().getAbsolutePath();
            ArrayList[] imageAlbumsData = new ArrayList[2];
            ArrayList<String> albumsAssigned = new ArrayList();
            ArrayList<String> albumsUnassigned = new ArrayList();
            imageAlbumsData[0] = albumsAssigned;
            imageAlbumsData[1] = albumsUnassigned;
            for (int i = 0; i < aInfoArray.size(); i++)
            {
                String fileName = aInfoArray.get(i)[1];
                Boolean assigned = false;
                if (!fileName.equals(noFileYet))
                {
                    aFilesInput = dataHandler.readFileInput(fileName);
                    if (aFilesInput!=null)
                    {
                        try
                        {
                            while(true)
                            {
                                String compareFilePath = aFilesInput.readUTF();
                                if (imgPath.equals(compareFilePath))
                                {
                                    albumsAssigned.add(aInfoArray.get(i)[0]);
                                    assigned = true;
                                    break;
                                }
                            }
                        }
                        catch(EOFException eof)
                        {
                            if (assigned == false)
                            {
                                albumsUnassigned.add(aInfoArray.get(i)[0]);
                                System.out.println("Finished reading file");
                            }
                        }  
                        catch(IOException ioe)
                        {
                            System.out.println("Couldn't read file ");
                            ioe.printStackTrace();
                            System.exit(5);
                        }
                    }
                    else
                    {
                        albumsUnassigned.add(aInfoArray.get(i)[0]);
                    }
                    dataHandler.closeFileInput(aFilesInput);
                        
                }
                else
                {
                    albumsUnassigned.add(aInfoArray.get(i)[0]);
                }
            }
            return imageAlbumsData;
        }
    
    /**
     * iterates through aInfoArray when it finds the index the album name is on,
     * returns the file name found on that index and sets 
     * lastElementProcessedIndex to the index number.
     * @param albumName
     * @return 
     */
    public String getAlbumFileFromName(String albumName)
    {
        fillAinfoArray();
        String fileName = "no file found";
        for (int i = 0; i<aInfoArray.size(); i++)
        {
            if (aInfoArray.get(i)[0].equals(albumName))
            {
                fileName = aInfoArray.get(i)[1];
                lastElementProcessedIndex = i;
                break;
            }
        }
        return fileName;
    }
    
    /**
     * takes file name and uses it to retrieve an array of the image paths 
     * associated with the album. The image path is added to this array and
     * then the album file is overwritten with the appended array. 
     */ 
    public ArrayList<String> getAlbumImagePaths(String fileName)
    {
        ArrayList<String> imagePathArray = new ArrayList();
        aFileInput = dataHandler.readFileInput(fileName);
        if (aFileInput == null)
        {
            return imagePathArray;
        }
        else
        {
            try
            {
                while(true)
                {
                    String filePath = aFileInput.readUTF();
                    imagePathArray.add(filePath);
                }
            }
            catch(EOFException eof)
            {
                    System.out.println("Finished reading file " + fileName);
            }
            catch(IOException ioe)
            {
                System.out.println("Couldn't read file " + fileName);
                ioe.printStackTrace();
                System.exit(5);
            }
            finally
            {
                dataHandler.closeFileInput(aFileInput);
            }
            return imagePathArray;
        }
    }
    
    /**
     * uses the file "album_count" which contains the number of albums that
     * have been created, the number is converted to an int and then incremented
     * to be used as a unique album name. The incremented number is written back
 to the file and then concatenated to a string which is stored at 
 albumIndex of aInfoArray, then the setAlbumToFiles method is called.
     * @param albumIndex 
     */
    public void createAlbumFileName(int albumIndex)
    {
        String acountstr = null;
        aCountInput = dataHandler.readFileInput("album_count");
        if (aCountInput == null)
        {
            acountstr = "0";
        }
        else
        {
            try
            {
                acountstr = aCountInput.readUTF();
            }
            catch (IOException ioe)
            {
                System.out.println("Error processing file at createAlbumFileName");
                System.exit(5);
            }
            finally
            {
                dataHandler.closeFileInput(aCountInput);
                System.out.println("Closed files (createAlbumFileName)");
            }
        }
        
        int countIncrement = Integer.parseInt(acountstr) + 1;
        aCountOutput = dataHandler.readFileOutput("album_count");
        try
        {
            aCountOutput.writeUTF(Integer.toString(countIncrement));
        }
        catch (IOException ioe)
        {
            System.out.println("Error processing file at createAlbumFileName");
            System.exit(5);
        }
        finally
        {
            dataHandler.closeFileOutPut(aCountOutput);
            System.out.println("Closed files (createAlbumFileName)");
        }
        
        String fileName = "KRimageAlbum" + countIncrement;
        aInfoArray.get(albumIndex)[1] = fileName;
        setAlbumToFiles(aInfoArray.get(albumIndex)[0]);
    }
    
     /**
     * calls getAlbumFileFromName to get file name associated with album. If the
     * file name is noFileYet createAlbumFile is called to create the file name.
     * getAlbumsImagePaths method is then called and then the image path is
     * added to the array. The array values are then written on to the album 
     * file.
     */
    public void assignAlbumToImage(String albumName, String imagePath)
    {
        String albumFile = getAlbumFileFromName(albumName);
        if (albumFile.equals(noFileYet))
        {
            createAlbumFileName(lastElementProcessedIndex);
            albumFile = aInfoArray.get(lastElementProcessedIndex)[1];
        }
        ArrayList<String>imagePathArray = getAlbumImagePaths(albumFile);
        System.out.println(imagePathArray);
        imagePathArray.add(imagePath);
        writeImagePathArrayToFile(imagePathArray, albumFile);
    }
    
    public void writeImagePathArrayToFile(
            ArrayList<String> pathArray,
            String fileName)
    {
        aFileOutPut = dataHandler.readFileOutput(fileName);
        
        for (int i = 0; i<pathArray.size(); i++)
        {
            try 
            {
                aFileOutPut.writeUTF(pathArray.get(i));
            }
            catch(IOException ioe)
            {
                System.out.println("Couldn't read file " + fileName);
                ioe.printStackTrace();
                System.exit(6);
            }
        }
        dataHandler.closeFileOutPut(aFileOutPut);
    }
    
    /**
     * calls getAlbumFileFromName to get the file name of an album, then creates
     * an array of the file paths in the album file by calling 
     * getAlbumImagePaths. Iterates through the array checking to find the index
     * where the path is stored and removes it. Then it calls 
     * writeImagePathArrayToFile to overwrite the album file with the array
     * values.
     * @param albumName
     * @param imagePath 
     */
     public void unassignAlbumToImage(String albumName, String imagePath)
     {
         String albumFile = getAlbumFileFromName(albumName);
         ArrayList<String>imagePathsArray = getAlbumImagePaths(albumFile);
         for(int i = 0; i<imagePathsArray.size();i++)
         {
             if (imagePathsArray.get(i).equals(imagePath))
             {
                imagePathsArray.remove(i);
                break;
             }
         }
         writeImagePathArrayToFile(imagePathsArray, albumFile);
     }
    
    /**
     * iterates through "album_names" and "album_Files" creating a String array
     * at each iteration storing the album name and album file at [0] and [1]
     * respectively. The string array is then added to an array list.
     */
    public void fillAinfoArray()
    {
        aInfoArray = new ArrayList();
        aNameInput = dataHandler.readFileInput(strAnamesFile);
        aFilesInput = dataHandler.readFileInput(strAfilesFile);
            try
            {
                while(true)
                {
                    String[] albumInfo = new String[2];
                    if (aNameInput!=null)
                    {
                        String albumName = aNameInput.readUTF();
                        String albumFile = aFilesInput.readUTF();
                        albumInfo[0] = albumName;
                        aFileInput = dataHandler.readFileInput(albumFile);
                        if (aFileInput == null)
                        {
                            albumFile = noFileYet;
                        }
                        else
                        {
                        dataHandler.closeFileInput(aFileInput);
                        }
                        albumInfo[1] = albumFile;
                        aInfoArray.add(albumInfo);
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
                System.out.println("Error processing file at fillAinfoArray");
                System.exit(2);
            }
            finally
            {
                dataHandler.closeFileInput(aNameInput);
                dataHandler.closeFileInput(aFilesInput);
                System.out.println("Closed files (fillAinfoArray)");
            }
    }

}
