/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import static java.util.logging.Logger.global;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Kit
 */
public class KRimage extends JFrame implements ComponentListener
{    
    private GridBagConstraints gbc;
    private BorderLayout layout;
    private JFrameForTest jframe;

    private JPanel mainPanel;
    
    private MenuBar menuBar;
    private ImageGrid imageGrid;
    private ImageAlbumList imageAlbumList;
    private DirectoryList directoryList;
    private DirectoryBrowser dirBrowser;
    
    private JScrollPane centreScroll;
    
    private DataHandler dataHandler;
    private ProgressBar progressBar;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenHeight = screenSize.height;
    private int screenWidth = screenSize.width;
    private GridBagLayout listsLayout;
    private GridBagConstraints gbcI;
    private JPanel listsPnl;
    
    
    public KRimage()
    {
        setTitle("KRimage");
        listsLayout = new GridBagLayout();
        gbcI = new GridBagConstraints();
        gbcI.fill=GridBagConstraints.BOTH;
        gbcI.weightx =1;
        gbcI.weighty = 1;
        listsPnl = new JPanel();
        listsPnl.setLayout(listsLayout);
        
        
        this.addComponentListener(this);
        this.setVisible(true);
        centreScroll = new JScrollPane();
        centreScroll.setBorder(null);
        centreScroll.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel = new JPanel();
        menuBar = new MenuBar(this);
        layout = new BorderLayout();
        mainPanel.setLayout(layout);
        this.setSize(new Dimension(((int)((float)screenWidth * 0.8)),
                                   ((int)((float)screenHeight * 0.8))));
        this.setMinimumSize(new Dimension(800,600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(menuBar);
        
        this.add(mainPanel);
          
        progressBar = new ProgressBar(this);
        imageGrid = new ImageGrid(this, progressBar);
        imageAlbumList = new ImageAlbumList(imageGrid);
        directoryList = new DirectoryList(imageGrid, this);
        dirBrowser = new DirectoryBrowser(directoryList);
        
        gbcI.gridy = 0;
        listsPnl.add(imageAlbumList,gbcI);
        gbcI.gridy=1;
        listsPnl.add(directoryList,gbcI);
        
        mainPanel.add(listsPnl, BorderLayout.WEST);
        mainPanel.add(progressBar, BorderLayout.SOUTH);
        mainPanel.add(imageGrid, BorderLayout.CENTER);
        
        centreScroll.setViewportView(imageGrid);
        mainPanel.add(centreScroll, BorderLayout.CENTER);
        revalidate();
        this.setLocationRelativeTo(null);
        
    }
    
    
    public DirectoryBrowser getDirBrowser()
    {
        return dirBrowser;
    }
    
    public ImageAlbumList getImageAlbumList()
    {
        return imageAlbumList;
    }
   
    /**
     * @param args the command line arguments
     */
    
    public int getCentreWidth()
    {
        if (imageGrid!=null)
        {
            return centreScroll.getWidth()-20;
        }
        else
        {
            return 600;
        }
    }
    
    public ImageObj getSelectedImg()
    {
        return imageGrid.getSelectedImage();
    }
    
    @Override
    public void componentResized(ComponentEvent e) {
        if (imageGrid !=null)
        {
            imageGrid.fillEntireGrid();
        }
        this.revalidate();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
    
    
    public static void main(String[] args) 
    {   
        KRimage testPanel = new KRimage();
    }
}
