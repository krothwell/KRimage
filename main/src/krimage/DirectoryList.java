/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Kit
 */
public class DirectoryList extends KRList implements 
        ActionListener,
        MouseListener
{
    private int dirIndex;
    private DirectoryBrowser dirBrowser = new DirectoryBrowser(this);
    final private ImageGrid imgGrid;
    private DirectoryData manageData;
    private DefaultListModel dlm;
    private JScrollPane scrollPane;
    private JMenuItem menuRemoveDirectory;
    private SwingWorker<Void, ImageObj> sw;
    private String dirNameSelected;
    private KRimage kr;
    private String duplicateDirStr;
    
    public DirectoryList(final ImageGrid imageGrid, KRimage krimage)
    {
        kr = krimage;
        setLblTitle("Image directories");
        manageData = new DirectoryData(this);
        dlm = manageData.getDlm();
        setDLM(dlm);
        getList().addMouseListener(this);
        setRenderer(new ImageIcon(getClass().getClassLoader().getResource(
                "resources/dirIcon.jpg")), new Font("Arial", Font.PLAIN, 14));
        
        manageData = new DirectoryData(this);
        getList().addMouseListener(this);
        imgGrid = imageGrid;
        getAddBtn().addActionListener(this);
    }
    
    public JPopupMenu buildDirectoryPopUp()
    {
        menuRemoveDirectory = new JMenuItem("Remove directory");
        
        JPopupMenu albumPopUp = new JPopupMenu();
        
        albumPopUp.add(menuRemoveDirectory);
        
        menuRemoveDirectory.addActionListener(this);
        
        return albumPopUp;
    }
    
    public String getDirDuplicateName()
    {
        return duplicateDirStr;
    }
    
    /**
     * checks if directory path is a duplicate by calling isDuplicateDirectory
     * method from manageData object. If returned false, calls addNewDir from
 manageData object and then sets dirIndex by calling the method
 getLastAddedIndex from manageData object. It uses the index to add
 the folder name to the default list model which updates the list. 
 The list item is then selected and the directory name set. loadImages
 is then called.
     * @param folder
     * @param folderPath 
     */
    public void addDirectoryToList(String folder, String folderPath)
    {       
        if (!manageData.isDuplicateDirectory(folderPath))
        {
            manageData.addNewDir(folder, folderPath);
            dirIndex = manageData.getLastAddedIndex(folder);
            dlm.add(dirIndex,folder);
            getList().setSelectedIndex(dirIndex);
            dirNameSelected = (String)getList().getSelectedValue(); 
            loadImages();
        }
        else
        {
            duplicateDirStr = folder;
            KRalertDialog duplicateDialog = new KRalertDialog(this,"dirDup");
            System.out.println("File path is a duplicate");
        }
    }
    
    public void removeDirectory(int dirIndex)
    {
        dlm.remove(dirIndex);
        manageData.removeDir(dirIndex);
    }
    
        @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuRemoveDirectory)
        {
            removeDirectory(dirIndex);
        }
        else if (e.getSource() == getAddBtn())
        {
            kr.getDirBrowser().addDirSelected(getAddBtn());
        }
        
    }
    
    public void loadImages()
    {
        sw = imgGrid.getSwingWorker();
        if (sw!=null)
        {
            sw.cancel(true);
        }
        String dirPath = manageData.getDirPath(dirIndex);
        imgGrid.clearGrid();
        imgGrid.addImagesFromDir(dirPath);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        if ( SwingUtilities.isRightMouseButton(e) )
        {
            dirIndex = getList().locationToIndex(e.getPoint());
            getList().setSelectedIndex(dirIndex);
            getList().setComponentPopupMenu(buildDirectoryPopUp());
            dirNameSelected = (String)getList().getSelectedValue(); 
        }
        else if(SwingUtilities.isLeftMouseButton(e))
        {
            dirIndex = getList().locationToIndex(e.getPoint());
            loadImages();
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     
    }

    @Override
    public void mouseExited(MouseEvent e) {
     
    }





}
