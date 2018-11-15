/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Kit
 */
public class ImageDetails extends JFrame implements 
        ComponentListener,
        ActionListener, 
        MouseListener{
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int screenHeight = screenSize.height;
    private int screenWidth = screenSize.width;
    private int frameHeight;
    private int frameWidth;
    private BorderLayout layout;
    private BufferedImage bufferedImg;
    private JPanel imagePanel; 
    private JPanel mainPanel;
    private BufferedImage scaledImg;
    private GridBagLayout imageLayout;
    private GridBagConstraints imggbc;
    private int imgWidth;
    private int imgHeight;
    private ImageIcon icon;
    private JLabel jlbl;
    private ImageObj parentImg;
    private DataHandler dataHandler;
    private ImageAlbumData dataManager;
    private Color krLightBlue = new Color(201,230,255);
    private int imgIndex;
    private ImageGrid ig;
    private ImageAlbumAUlists albumList;
    private ArrayList<ImageObj> imgObjArray;
    private JPanel browsePanel;
    private FlowLayout browseLayout;
    private ImageObj nextImage;
    private ImageObj prevImage;
    private ImageIcon nextIcon;
    private ImageIcon prevIcon;
    private JButton nextBtn;
    private JButton prevBtn;
    private JButton btnFullScreen;
    private JButton btnMaxImage;
    private JButton btnImageAlbums;
    private ImageIcon fullScreenIcon;
    private ImageIcon maxImageIcon;
    private ImageIcon imageAlbumsIcon;
    private String thisMode;
    private Color krDarkBlue = new Color(33, 68, 115);

    private JScrollPane centreScroll;
    private JPanel centrePanel;
    private JPanel topMenu;
    private GridBagLayout topLayout;
    private JPanel resizeOptions;
    private FlowLayout resizeLayout;
    private GridBagConstraints topgbc;
    private Boolean isAlbumsShowing = false;
    private Boolean isFullScreen = false;
    
    
    public ImageDetails(ImageObj imgObj, String mode)
    {
        thisMode = mode;
        this.setVisible(true);
        
        buildThis(imgObj);
        
        dataManager = new ImageAlbumData();
    }
    
    public void buildThis(ImageObj imgObj)
    {
        ig = imgObj.getImageGrid();
        imgObjArray = ig.getImgObjArray();
        imgIndex = imgObj.getImageIndex();
        
        frameHeight = (int)(screenHeight*0.7);
        frameWidth = (int)(screenWidth*0.6);
        
        albumList = new ImageAlbumAUlists(imgObj);
        
        parentImg = imgObj;
        bufferedImg = parentImg.getBufferedImg();

        imagePanel = new JPanel();
        imageLayout = new GridBagLayout();
        imagePanel.setLayout(imageLayout);
        
        imggbc = new GridBagConstraints();
        imggbc.fill = GridBagConstraints.BOTH;
        imggbc.gridx=0;
        imggbc.gridy=0;
        imggbc.weightx=1;
        imggbc.weighty=1;
        imggbc.anchor = GridBagConstraints.CENTER;
        
        setImgDimensions(bufferedImg);
        
        scaledImg = parentImg.resizeImg(bufferedImg, imgWidth, imgHeight);
        icon = new ImageIcon(scaledImg);
        jlbl = new JLabel(icon);
        
        topMenu = new JPanel();
        topLayout = new GridBagLayout();
        topMenu.setLayout(topLayout);
        topgbc = new GridBagConstraints();
        topgbc.gridx = 0;
        topgbc.weightx=1;
        topgbc.anchor = GridBagConstraints.WEST;
        
        resizeOptions = new JPanel();
        resizeLayout = new FlowLayout(FlowLayout.LEFT, 0,0);
        resizeOptions.setLayout(resizeLayout);
        
        fullScreenIcon = new ImageIcon (getClass().getClassLoader().getResource(
                "resources/fullIcon.jpg"));
        maxImageIcon = new ImageIcon (getClass().getClassLoader().getResource(
                "resources/maxIcon.jpg"));
        imageAlbumsIcon = new ImageIcon (getClass().getClassLoader().getResource(
                "resources/detailsIcon.jpg"));
        
        btnFullScreen = new JButton(fullScreenIcon);
        btnMaxImage = new JButton(maxImageIcon);
        btnImageAlbums = new JButton(imageAlbumsIcon);
        
        btnFullScreen.addActionListener(this);
        btnMaxImage.addActionListener(this);
        btnImageAlbums.addActionListener(this);
        
        btnFullScreen.setBackground(krDarkBlue);
        btnMaxImage.setBackground(krDarkBlue);
        btnImageAlbums.setBackground(krDarkBlue);
        
        resizeOptions.add(btnFullScreen);
        resizeOptions.add(btnMaxImage);
        resizeOptions.add(btnImageAlbums);
        resizeOptions.setBackground(krDarkBlue);
        
        topMenu.add(resizeOptions,topgbc);
        
        browsePanel = new JPanel();
        browseLayout = new FlowLayout(FlowLayout.RIGHT, 5,0);
        browsePanel.setLayout(browseLayout);
        browsePanel.setBackground(krDarkBlue);
        topgbc.anchor = GridBagConstraints.EAST;
        topgbc.gridx = 1;
        topMenu.add(browsePanel,topgbc);
        
        topMenu.setBackground(krDarkBlue);
        
        try
        {
            prevImage = imgObjArray.get(imgIndex-1);
            prevIcon = new ImageIcon(getClass().getClassLoader().getResource(
                "resources/backArrow.png"));
            prevBtn = new JButton(prevIcon);
            prevBtn.addActionListener(this);
            prevBtn.setBackground(krLightBlue);
            browsePanel.add(prevBtn);
        } 
        catch (Exception e){}
        try
        {
            nextImage = imgObjArray.get(imgIndex+1);
            nextIcon = new ImageIcon(getClass().getClassLoader().getResource(
                "resources/forwardArrow.png"));
            nextBtn = new JButton(nextIcon);
            nextBtn.setBackground(krLightBlue);
            nextBtn.addActionListener(this);
            browsePanel.add(nextBtn);
        } 
        catch (Exception e){}        
        
        imagePanel.add(jlbl,imggbc);
        imagePanel.setBackground(krLightBlue);
        

        layout = new BorderLayout();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(layout);
        mainPanel.add(imagePanel, BorderLayout.CENTER); 
        mainPanel.add(topMenu, BorderLayout.NORTH);
        this.add(mainPanel);
        if(thisMode.equals("full"))
        {
            fullScreen();
        }
        else if(thisMode.equals("max"))
        {
            maxImage();
        }
        else if(thisMode.equals("normal"))
        {
            this.pack();
        }
        else if(thisMode.equals("albums"))
        {
            thisMode = "normal";
            showImgDetails();
            this.pack();
        }
        else if(thisMode.equals("albumsFull"))
        {
            thisMode = "full";
            showImgDetails();
            fullScreen();
        }
        this.addComponentListener(this);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
    }
    
    public void fullScreen()
    {
        //this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        isFullScreen = true;
    }
    
    public void maxImage()
    {
        this.fullScreen();
        if (imgWidth < bufferedImg.getWidth() || imgHeight < bufferedImg.getHeight())
        {
            this.remove(mainPanel);
            centreScroll = new JScrollPane();
            centreScroll.setBorder(null);
            centreScroll.getVerticalScrollBar().setUnitIncrement(16);
            BorderLayout fullLayout = new BorderLayout();
            centrePanel = new JPanel();
            centrePanel.setBackground(krLightBlue);
            this.add(centrePanel, BorderLayout.CENTER);
            scaledImg = parentImg.resizeImg(
                    bufferedImg, 
                    bufferedImg.getWidth(), 
                    bufferedImg.getHeight());
            fullScreenIcon = new ImageIcon();
            fullScreenIcon.setImage(scaledImg);
            jlbl.setIcon(fullScreenIcon);
            jlbl.addMouseListener(this);
            centrePanel.addMouseListener(this);
            centrePanel.add(jlbl);
            centreScroll.setViewportView(centrePanel);
            this.add(centreScroll, BorderLayout.CENTER);

            this.revalidate();
        }
    }
    
    public void maxToFullScreen()
    {
        this.remove(centrePanel);
        this.remove(centreScroll);
        this.add(mainPanel);
        jlbl.removeMouseListener(this);
        centrePanel.removeMouseListener(this);
        setImgDimensions(bufferedImg);
        scaledImg = parentImg.resizeImg(bufferedImg, imgWidth, imgHeight);
        icon.setImage(scaledImg);
        jlbl.setIcon(icon);
        imagePanel.add(jlbl,imggbc);
        this.revalidate();
        this.repaint();
        isFullScreen = true;
    }
    
    public void setModeNext()
    {
        if (isAlbumsShowing == true && isFullScreen == true)
        {
            thisMode = "albumsFull";
        }
        else if(isAlbumsShowing == true)
        {
            thisMode = "albums";
        }      
    }
    
     public void showImgDetails()
    {
        if (isAlbumsShowing == true)
        {
            mainPanel.remove(albumList);
            isAlbumsShowing = false;
        }
        else
        {
            mainPanel.add(albumList, BorderLayout.WEST);
            isAlbumsShowing = true;
        }
        if (isFullScreen == false)
        {
            this.pack();
        }
        this.revalidate();
    }
     public void fullScreenToNormal()
     {
        isFullScreen = false;
        frameHeight = (int)(screenHeight*0.7);
        frameWidth = (int)(screenWidth*0.6);
        setImgDimensions(bufferedImg);
        scaledImg = parentImg.resizeImg(bufferedImg, imgWidth, imgHeight);
        icon.setImage(scaledImg);
        this.pack();
        this.setLocationRelativeTo(null);
     }
    
    public ImageAlbumData getData()
    {
        return dataManager;
    }
        
    public void setImgDimensions(BufferedImage bufferedImg)
    {
        double simgHeight = bufferedImg.getHeight();
        double simgWidth = bufferedImg.getWidth();
        double difference = 0;
        double percentageDiff = 0;
        double preferredImgH = frameHeight;
        double preferredImgW = frameWidth;
        
        if (simgHeight < simgWidth)
        {
            difference = simgWidth - simgHeight;
            //
            percentageDiff = (100.0/simgWidth) * difference;
            //System.out.println(percentageDiff);
            
            if (bufferedImg.getWidth() < preferredImgW)
            {
                imgWidth = (int)simgWidth;
                imgHeight = (int)simgHeight;
            }
            else 
            {
                imgWidth = (int)preferredImgW;
                imgHeight = (int)((double)imgWidth - (imgWidth * (percentageDiff/100.0)));
            }
        }
        else if (simgHeight > simgWidth)
        {
            difference = simgHeight - simgWidth;
            percentageDiff = (100/simgHeight) * difference;
            
            if (bufferedImg.getHeight() < preferredImgH)
            {
                imgWidth = (int)simgWidth;
                imgHeight = (int)simgHeight;
            }
            else 
            {
                imgHeight = (int)preferredImgH;
                imgWidth = (int)((double)imgHeight - (imgHeight * (percentageDiff/100.0)));
            }
        }
        else if (simgHeight == simgWidth)
        {
            imgHeight = (int)preferredImgH;
            imgWidth = (int)preferredImgH;
        }   
    }

    @Override
    public void componentResized(ComponentEvent e) {
        frameHeight = this.getHeight();
        frameWidth = this.getWidth();
        frameHeight = (int)(this.getHeight()*0.8);
        frameWidth = (int)(this.getWidth()*0.7);
        setImgDimensions(bufferedImg);
        
        scaledImg = parentImg.resizeImg(bufferedImg, imgWidth, imgHeight);
        icon.setImage(scaledImg);
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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==nextBtn)
        {
            setModeNext();
            ImageDetails nextImgD = new ImageDetails(nextImage, thisMode);
            this.dispose();
        }
        else if (e.getSource()==prevBtn)
        {
            setModeNext();
            ImageDetails prevImgD = new ImageDetails(prevImage, thisMode);
            this.dispose();
        }
        else if (e.getSource() == btnFullScreen)
        {
            if (thisMode.equals("normal"))
            {
                thisMode = "full";
                fullScreen();
            }
            else if (thisMode.equals("full"))
            {
                thisMode = "normal";
                fullScreenToNormal();
            }
        } 
        else if (e.getSource() == btnMaxImage)
        {
            maxImage();
        }
        else if (e.getSource() == btnImageAlbums)
        {
            showImgDetails();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()==jlbl||e.getSource()==centrePanel)
        {
            if(thisMode.equals("full"))
            {
                maxToFullScreen();
            }
            else if(thisMode.equals("normal"))
            {
                maxToFullScreen();
                fullScreenToNormal();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
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