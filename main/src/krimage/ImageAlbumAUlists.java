/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Kit
 */
public class ImageAlbumAUlists extends JPanel{
    private int albumRowSelected;
    private JList albumListBox;
    private ImageAlbumData dataModel;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JScrollPane scrollPane;
    private String albumNameSelected;
    private JPopupMenu albumPopUp;
    private DefaultListModel dlm;
    private UnassignedList albumUnassignedListBox;
    private AssignedList albumAssignedListBox;
    private DefaultListModel dlmAssigned;
    private DefaultListModel dlmUnassigned;
    private ImageObj img;
    
    public ImageAlbumAUlists(JPanel imgObj)
    {
        dataModel = new ImageAlbumData();
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridx = 0;
        gbc.gridy =0;
        this.setLayout(layout);
        
        img = (ImageObj)imgObj;
        ArrayList[] auArray = dataModel.getImagesAlbumsArray(img);
        ArrayList<String> assignedArray = auArray[0];
        ArrayList<String> unassignedArray = auArray[1];
        
        
        dlmAssigned = dataModel.buildDlm(assignedArray);
        dlmUnassigned = dataModel.buildDlm(unassignedArray);
        
        albumAssignedListBox = new AssignedList(dlmAssigned, this);
        albumUnassignedListBox = new UnassignedList(dlmUnassigned, this);
        //albumAssignedListBox.addMouseListener(this);
        //albumUnassignedListBox.addMouseListener(this);
        
        JLabel lblAssigned = new JLabel("Albums assigned");
        JLabel lblUnassigned = new JLabel("Unassigned");

        this.add(albumAssignedListBox, gbc);
        gbc.gridy = 1;
        this.add(albumUnassignedListBox, gbc);
    }
    
    public String getNameOfAlbumSelected()
    {
        return albumNameSelected;
    }
    
    /**
     * takes a string and passes to data model object's isDuplicateAlbum
     * method as argument. If true is returned, adds a duplication error label
     * to the add album dialog.
     * @param albumName 
     */
    
    public void assignAlbum(String albumname, int albumIndex, String mode)
    {
        albumNameSelected = albumname;
        albumRowSelected = albumIndex;
        String filePath = img.getFile().getAbsolutePath();
        if (albumname!=null)
        {
            if (mode == "assign")
            {
                dlmUnassigned.removeElementAt(albumRowSelected);
                dataModel.assignAlbumToImage(albumNameSelected, filePath);
                dlmAssigned.add(0,albumNameSelected);
            }
            else if (mode == "unassign")
            {
                dlmAssigned.removeElementAt(albumRowSelected);
                dataModel.unassignAlbumToImage(albumNameSelected, filePath);
                dlmUnassigned.add(0,albumNameSelected);
            }
        }
    }

}
