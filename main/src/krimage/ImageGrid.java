/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author Kit
 */
public class ImageGrid extends JPanel implements MouseListener, ActionListener{
    private BoxLayout layout;
    private int panelSize;
    private int thisWidth;
    private int imgWidth;
    private int imgHeight;
    private int rowWidth;
    private int rowImgs;
    private int rowPnlIndex = -1;
    private int imgProgressNumber;
    private int locationCount;
    private DataHandler fileHandler = new DataHandler();
    private KRimage main; 
    private ArrayList<ImageWrapper> imgArray;
    private ImageWrapper images;
    private ArrayList <ImageObj> imgObjArray;
    private ArrayList <ImageObj> selectedImagesArray;
    private ArrayList<JPanel> rowPnlArray;
    private ProgressBar pBar;
    private Color lBlue = new Color(201,230,255);
    private SwingWorker<Void, ImageObj> worker;
    private JMenuItem menuAddToAlbum;
    
    Image dimg;

    
    FF ff = new FF();
    
    
    public ImageGrid(KRimage mainController, ProgressBar progressBar)
    {
        super();
        this.addMouseListener(this);
        selectedImagesArray = new ArrayList();
        main = mainController;
        pBar = progressBar;
        buildThis(main);
    }
    
    public void addToSelectedArray(ImageObj image)
    {
        if (selectedImagesArray.size()>0)
        {
            selectedImagesArray.get(0).setBackground(null);
        }
        
        selectedImagesArray.clear();
        selectedImagesArray.add(image);
    }
    
    public ImageObj getSelectedImage()
    {
        try{
            return selectedImagesArray.get(0);
        }
        catch(IndexOutOfBoundsException oob)
        {
            return null;
        }
    }
    
    public void buildThis(final KRimage mainController)
    {
        rowWidth = 0;
        rowImgs = 0;
        
        imgArray = new ArrayList();
        panelSize = 200;
        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setOpaque(true);
        this.setBackground(lBlue);
        
        //fillImageArray();
        //thisWidth = main.getWidth();
        //
    }
    
    public void startAddImagesWorker(File[] imgList)
    {
        worker = new SwingWorker<Void, ImageObj>() 
        {
            @Override
            protected Void doInBackground() throws Exception 
            {
                for(int i = 0; i < imgList.length; i++)
                {
                    if(!isCancelled())
                    {
                        ImageObj image = new ImageObj(imgList[i], panelSize, getMe(), i);
                        pBar.setProgress();
                        imgObjArray.add(image);
                        publish(image);
                    }
                    else
                    { 
                        System.out.println("Worker cancelled: " + isCancelled());
                        imgObjArray = new ArrayList();
                        rowPnlArray = new ArrayList();
                        clearGrid();
                        rowPnlIndex = -1;
                        getMe().revalidate();
                        getMe().repaint();
                        break;
                    }
                        
                }
                    
            return null;
            }

            @Override
            protected void process(List<ImageObj> chunks) 
            {
                ImageObj image = chunks.get(chunks.size()-1);
                addImageToGrid(image);
                //fillEntireGrid();
            }
                
            

            @Override
            protected void done() {
                //fillGridFromArray(imgObjArray);
                fillEntireGrid();
                pBar.setProcessComplete(); 
            }
            
            
        };
        worker.execute();
    }
    
    public void addImagesFromAlbum(String albumName)
    {
        try
        {
            Thread.sleep(100);
        }
        catch(Exception e)
        {
            System.out.println("interruption");
        }
        
        rowPnlIndex = -1;
        this.clearGrid();
        imgObjArray = new ArrayList();
        this.fillGridFromArray(imgObjArray);
        
        
        ArrayList<String> pathArray = main.getImageAlbumList().getDataManager().getAlbumImagePaths(albumName);
        File[] fileList = new File[pathArray.size()];
        String path = null;
        for (int i = 0 ; i<pathArray.size(); i ++)
        {
            path = pathArray.get(i);
            fileList[i] = new File(path);
        }
        startAddImagesWorker(fileList);
        this.fillGridFromArray(imgObjArray);
        pBar.setMaxValue(fileList.length);
    }
    
    public void addImagesFromDir(String path)
    {
        try
        {
            Thread.sleep(100);
        }
        catch(Exception e)
        {
            System.out.println("interruption");
        }
        
        rowPnlIndex = -1;
        this.clearGrid();
        imgObjArray = new ArrayList();
        
        FF ff = new FF();
        File dir = new File(path);
        File[] imgList = dir.listFiles(ff);
        if (imgList == null)
        {
            KRalertDialog missingDir = new KRalertDialog(this, "dirmissing");
        }
        else
        {
            pBar.setMaxValue(imgList.length);

            System.out.println("Reading images from " + path);
            startAddImagesWorker(imgList);
            this.fillGridFromArray(imgObjArray);
            this.revalidate();
            this.repaint();
        }
       
    }
    
    public ImageGrid getMe()
    {
        return this;
    }
    
    public ArrayList<ImageObj> getImgObjArray()
    {
        return imgObjArray;
    }
    
    //*/
    public int getThisWidth()
    {
        if (main != null)
        {
            thisWidth = main.getCentreWidth();
        }
        //else
        //{
         //   thisWidth = 0;
        //}
        return thisWidth;
    }
    
    public void addImageWrap(int arrayIndex, String dir)
    {
        this.clearGrid();
        locationCount++;
        imgArray.add(arrayIndex, new ImageWrapper(dir, panelSize, this, pBar));
    }
    
    public void clearGrid()
    {
        this.removeAll();
    }
    
    public void addImageToGrid(ImageObj anImg)
    {
        getThisWidth();
        if (rowPnlIndex == -1)
        {
            rowPnlArray = new ArrayList();
            rowWidth = 0;
            rowImgs = 0;
        }
        if (rowWidth == 0)
        {
            JPanel rowPanel = buildRowPnl(); 
            rowPnlArray.add(rowPanel);
            rowPnlIndex++;
            
            this.add(rowPnlArray.get(rowPnlIndex));
        }
        
        
            rowWidth += panelSize;
            rowImgs++;
            
        if (rowWidth > thisWidth && rowImgs == 1)
        {
            rowPnlArray.get(rowPnlIndex).add(anImg);
            rowImgs = 0;
            rowWidth = 0;
        }
        else if(rowWidth > thisWidth)
        {
            rowWidth = panelSize;
            rowImgs=1;
            JPanel rowPanel = buildRowPnl(); 
            rowPnlArray.add(rowPanel);
            rowPnlIndex++;
            this.add(rowPnlArray.get(rowPnlIndex));
            rowPanel.add(anImg);
        }
        else
        {
        rowPnlArray.get(rowPnlIndex).add(anImg);
        } 
        //rowPnlArray.get(rowPnlIndex).revalidate();
        //rowPnlArray.get(rowPnlIndex).repaint();
        //this.repaint();
        //this.revalidate();
    }
    
    public void fillGridFromArray(ArrayList<ImageObj> imageObjArray)
    {
        rowPnlIndex = -1;
        if (imageObjArray!=null)
        {
            for (int img = 0; img < imageObjArray.size(); img++)
            {
                
                addImageToGrid(imageObjArray.get(img));
            }
        }
        
    }
    
    public SwingWorker getSwingWorker()
    {
        return worker;
    }
    
    public JPopupMenu buildImageOptionsPopUp()
    {
        menuAddToAlbum = new JMenuItem("Assign or remove album(s)");
        
        JPopupMenu albumPopUp = new JPopupMenu();
        
        albumPopUp.add(menuAddToAlbum);
        
        menuAddToAlbum.addActionListener(this);
        //jpnl.add(albumPopUp);
        return albumPopUp;
    }
    
    public void fillEntireGrid()
    {
        clearGrid();
        rowWidth = 0;
        rowImgs = 0;
        
        fillGridFromArray(imgObjArray);

    }
    
    public JPanel buildRowPnl()
    {
        FlowLayout rowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);
        JPanel jpanel = new JPanel();
        jpanel.setLayout(rowLayout);
        jpanel.setBackground(lBlue);
        return jpanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        if ( SwingUtilities.isRightMouseButton(e) )
        {
            //if (e.getSource()!= this)
            //{
                
            //}
            //for (int i = 0; i < imgObjArray.size();i++)
            if (e.getSource() == this)
            {
               // if (selectedImagesArray.size()>0)
                {
                    this.setComponentPopupMenu(buildImageOptionsPopUp());
                }
            }
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.getSelectedImage()!=null)
        {
            if (e.getSource()==menuAddToAlbum)
            {
                ImageDetails viewImgAlbums = new ImageDetails(
                        this.getSelectedImage(), "albums");
            }
        }
        else
        {
            KRalertDialog imageSelectedDialog = new KRalertDialog(
                    new JPanel(),"noneselected");
        }

    }
}
