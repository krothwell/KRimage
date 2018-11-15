/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template fileMenu, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javafx.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author Kit
 */
class MenuBar extends JMenuBar implements ActionListener{
    private JMenu fileMenu;
    private JMenu imageMenu;
    KRimage main;
    int returnVal;
    private File fileSelected;
    
    private final JMenuItem menuAddDirectory;
    private final JMenuItem menuAddAlbum;
    
    private final JMenuItem menuViewImage;
    private final JMenuItem menuViewImageFull;
    private final JMenuItem menuViewImageMax;
    private final JMenuItem menuViewImageAlbums;      
    
    private Color krDarkBlue = new Color(33, 68, 115);
    private Color krLightBlue = new Color(200, 222, 252);
    
    Font menuFont = new Font("Arial", Font.PLAIN, 14);
    
    public MenuBar(final KRimage mainController)
    {
        UIManager.put("Menu.selectionBackground",krLightBlue);
	UIManager.put("Menu.selectionForeground",krDarkBlue);
        UIManager.put("MenuItem.selectionBackground", krDarkBlue);
        UIManager.put("MenuItem.selectionForeground", krLightBlue);
        main = mainController;
        fileMenu = new JMenu("File");
        imageMenu = new JMenu("Image");
        
        menuAddDirectory = new JMenuItem("Add image directory");
        menuAddAlbum = new JMenuItem("Add new album");
        
        menuViewImage = new JMenuItem("View image");
        menuViewImageFull = new JMenuItem("View image full screen");
        menuViewImageMax = new JMenuItem("View maximum sized image");
        menuViewImageAlbums = new JMenuItem("Assign or remove image album(s)");
        
        menuAddDirectory.setBackground(krLightBlue);
        menuAddAlbum.setBackground(krLightBlue);
        menuViewImage.setBackground(krLightBlue);
        menuViewImageFull.setBackground(krLightBlue);
        menuViewImageMax.setBackground(krLightBlue);
        menuViewImageAlbums.setBackground(krLightBlue);
        menuAddAlbum.setForeground(krDarkBlue);
        menuAddDirectory.setForeground(krDarkBlue);
        menuViewImage.setForeground(krDarkBlue);
        menuViewImageFull.setForeground(krDarkBlue);
        menuViewImageMax.setForeground(krDarkBlue);
        menuViewImageAlbums.setForeground(krDarkBlue);
        
        menuAddDirectory.addActionListener(this);
        menuAddAlbum.addActionListener(this);
        menuViewImage.addActionListener(this);
        menuViewImageFull.addActionListener(this);
        menuViewImageMax.addActionListener(this);
        menuViewImageAlbums.addActionListener(this);       
                
        fileMenu.add(menuAddAlbum);
        fileMenu.add(menuAddDirectory);
        
        imageMenu.add(menuViewImage);
        imageMenu.add(menuViewImageFull);
        imageMenu.add(menuViewImageMax);
        imageMenu.add(menuViewImageAlbums);
        
        fileMenu.setFont(menuFont);
        imageMenu.setFont(menuFont);
        fileMenu.setForeground(krLightBlue);
        imageMenu.setForeground(krLightBlue);
        fileMenu.setMnemonic('F');
        imageMenu.setMnemonic('I');
        
        this.setBackground(krDarkBlue);
        this.add(fileMenu);
        this.add(imageMenu);
        
    }

   
    public void actionPerformed(
            java.awt.event.ActionEvent e) 
    {
        String folderPath;
        if (e.getSource() == menuAddDirectory)
        {            
            main.getDirBrowser().addDirSelected(menuAddDirectory);
        }
        else if (e.getSource() == menuAddAlbum)
        {            
            main.getImageAlbumList().getAlbumNameFromDialog();
        }
        
        else if(main.getSelectedImg()!=null)
        {
            if (e.getSource() == menuViewImage)
            {            
                ImageDetails viewImg = new ImageDetails(
                        main.getSelectedImg(), "normal");
            }
            else if (e.getSource() == menuViewImageAlbums)
            {            
                ImageDetails viewImg = new ImageDetails(
                        main.getSelectedImg(), "albums");
            }
            else if (e.getSource() == menuViewImageFull)
            {            
                ImageDetails viewImg = new ImageDetails(
                        main.getSelectedImg(), "full");
            }
            else if (e.getSource() == menuViewImageMax)
            {            
                ImageDetails viewImg = new ImageDetails(
                        main.getSelectedImg(), "max");
            }
        }
        else if(main.getSelectedImg()==null)
        {
            KRalertDialog imageSelectedDialog = new KRalertDialog(new JPanel(),"noneselected");
        }
        
        

    }


}
