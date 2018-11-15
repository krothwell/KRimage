/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Kit
 */
public class ImageAlbumList extends KRList implements MouseListener, ActionListener{
    
    private int albumRowSelected;
    private ImageAlbumData manageData;
    private DefaultListModel dlm;
    private String albumNameSelected;
    private ImageGrid imgGrid;
    private SwingWorker sw;
    private JMenuItem menuRemoveAlbum;
    private JMenuItem menuRenameAlbum;
    
    public ImageAlbumList(ImageGrid imageGrid)
    {
        imgGrid = imageGrid;
        setLblTitle("My albums");
        manageData = new ImageAlbumData();
        dlm = manageData.getDlm();
        setDLM(dlm);
        getList().addMouseListener(this);
        setRenderer(new ImageIcon(getClass().getClassLoader().getResource(
                "resources/albumIcon.jpg")),new Font("Arial", Font.PLAIN, 14) );
        getAddBtn().addActionListener(this);
    }
    
    public ImageAlbumData getDataManager()
    {
        return manageData;
    }

    public void getAlbumNameFromDialog()
    {
        KRalertDialog imageAlbumNameDialog = new KRalertDialog(this, "add");
    }
    
    public JPopupMenu buildAlbumPopUp()
    {
        menuRenameAlbum = new JMenuItem("Rename album");
        menuRemoveAlbum = new JMenuItem("Remove album");
        
        JPopupMenu albumPopUp = new JPopupMenu();
        
        albumPopUp.add(menuRenameAlbum);
        albumPopUp.add(menuRemoveAlbum);
        
        menuRemoveAlbum.addActionListener(this);
        menuRenameAlbum.addActionListener(this);

        return albumPopUp;
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
    
    public boolean isDuplicateAlbum(String albumName)
    {
        return (manageData.isDuplicateAlbum(albumName));
    }
    
    public void addAlbumNameToList(String albumName)
    {
        manageData.addNewAlbum(albumName);
       //System.out.println(manageData.getLastAddedIndex());
       //System.out.println(manageData.getElementAt(manageData.getLastAddedIndex()));
        int listIndex = manageData.getLastAddedIndex();
        String listItem = (String)manageData.getElementAt(listIndex);
        dlm.add(listIndex, listItem);
    }
    
    public void removeAlbumNameFromList()
    {
        manageData.removeAlbum(albumRowSelected);
        dlm.removeElementAt(albumRowSelected);
    }
    
    public void renameAlbumNameFromList(String newName)
    {
        manageData.renameAlbum(albumRowSelected, newName);
        dlm.setElementAt((Object)newName, albumRowSelected);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void assignAlbum()
    {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ( SwingUtilities.isRightMouseButton(e) )
        {
            albumRowSelected = getList().locationToIndex(e.getPoint());
            getList().setSelectedIndex(albumRowSelected);
            getList().setComponentPopupMenu(buildAlbumPopUp());
            albumNameSelected = (String)getList().getSelectedValue(); 
        }
        else if(SwingUtilities.isLeftMouseButton(e))
        {
            albumRowSelected = getList().locationToIndex(e.getPoint());
            albumNameSelected = (String)getList().getSelectedValue(); 
                    //if (!e.getValueIsAdjusting())
        //{
            sw = imgGrid.getSwingWorker();
            if (sw!=null)
            {
                sw.cancel(true);
            }
            String fileName = manageData.getAlbumFileFromName(albumNameSelected);
            imgGrid.addImagesFromAlbum(fileName);
            imgGrid.clearGrid();
            imgGrid.revalidate();
            imgGrid.repaint();
            
       // }
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuRemoveAlbum)
        {
            KRalertDialog removeAlbumDialog = new KRalertDialog(this, "remove");
        }
        else if (e.getSource() == menuRenameAlbum)
        {
            KRalertDialog renameAlbumDialog = new KRalertDialog(this, "rename");
        }
        else if(e.getSource()==getAddBtn())
        {
            KRalertDialog addAlbumDialog = new KRalertDialog(this, "add");
        }
        
        
    }
}
