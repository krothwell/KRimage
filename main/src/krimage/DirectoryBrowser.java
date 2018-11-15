/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Component;
import java.io.File;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Kit
 */
public class DirectoryBrowser extends JFrame
{
    private JFileChooser directoryBrowser;
    private final DirectoryList dl;

    public DirectoryBrowser(final DirectoryList directoryList)
    {
        dl = directoryList;
        directoryBrowser = new JFileChooser();
        directoryBrowser.setDialogTitle("Browse and select a directory to add a new location");
        directoryBrowser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       
    }
    
    public JFileChooser addDirBrowser()
    {
        return directoryBrowser;
    }
    
    public void setDisplay()
    {
        directoryBrowser.showOpenDialog(this);
    }
    
    /**
     * Gets directory name and location from JFileChooser if an option has
     * been selected and calls the Directory list objects addDirectoryToList
     * method.
     * @param e 
     */
    public void addDirSelected(Component e)
    {   
        if (directoryBrowser.showOpenDialog(e) == JFileChooser.APPROVE_OPTION)
        {
            String folderPath = directoryBrowser.getSelectedFile().getAbsolutePath();
            String folder = directoryBrowser.getSelectedFile().getName();
            dl.addDirectoryToList(folder, folderPath);
        }
    }

    
}