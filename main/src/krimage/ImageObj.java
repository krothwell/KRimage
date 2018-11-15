/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Kit
 */
public class ImageObj extends JPanel implements MouseListener{
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JPanel jpanel;
    private BufferedImage img;
    private BufferedImage scaledImg;
    private Color krDarkBlue = new Color(33, 68, 115);
    private Color krDeepBlue = new Color(70, 133, 175);
    private int topInsetOffset;
    private int imgWidth;
    private int imgHeight;
    private int panelSize;
    private ImageGrid ig;
    private File file;
    private ImageIcon icon;
    private JLabel jlbl;
    private int imgIndex;
    
    public ImageObj(File afile, int pSize, ImageGrid imageGrid, int index)
    {
        imgIndex = index;
        file = afile;
        ig = imageGrid;
        panelSize = pSize;
        this.addMouseListener(ig);
        
        layout = new GridBagLayout();
        
        this.setLayout(layout);
        
        gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);
        
        
        img = getBufferedImg();
        setImgDimensions(img);
        scaledImg = resizeImg(img, imgWidth, imgHeight);
        this.addMouseListener(this);
        icon = new ImageIcon(scaledImg);
        jlbl = new JLabel(icon);
        jlbl.setBorder(
                BorderFactory.createMatteBorder(0,0,4,3,krDeepBlue));
        this.setOpaque(true);
        this.setBackground(null);
        //this.setBorder(BorderFactory.createLineBorder (Color.white, 5));
        this.setPreferredSize(new Dimension(panelSize,panelSize));
        this.add(jlbl, gbc);
        img = null;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public ImageGrid getImageGrid()
    {
        return ig;
    }
    public int getImageIndex()
    {
        return imgIndex;
    }
    
    public BufferedImage resizeImg(BufferedImage image, int width, int height) 
    {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
    
    public BufferedImage getBufferedImg()
    {
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bimg;
    }
    
    public void setImgDimensions(BufferedImage bufferedImg)
    {
        double simgHeight = bufferedImg.getHeight();
        double simgWidth = bufferedImg.getWidth();
        double difference = 0;
        double percentageDiff = 0;
        
        if (simgHeight < simgWidth)
        {
            difference = simgWidth - simgHeight;
            //
            percentageDiff = (100.0/simgHeight) * difference;
            //System.out.println(percentageDiff);
            imgWidth = (int)((double)panelSize + (panelSize * (percentageDiff/100.0)));
            imgHeight = panelSize;
        }
        else if (simgHeight > simgWidth)
        {
            difference = simgHeight - simgWidth;
            percentageDiff = (100/simgWidth) * difference;
            imgHeight = (int)((double) panelSize + (panelSize * (percentageDiff/100.0)));
            imgWidth = panelSize;
        }
        else if (simgHeight == simgWidth)
        {
            imgHeight = panelSize;
            imgWidth = panelSize;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == this)
        {
            if (e.getClickCount() == 2)
        {
            ImageDetails imgr = new ImageDetails(this, "normal");
        }
        this.setComponentPopupMenu(ig.buildImageOptionsPopUp());
        //this.setOpaque(true);
        repaint();
        ig.addToSelectedArray(this);
        this.setBackground(krDarkBlue);
        }
        
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        //ImageDetails imageDetails = new ImageDetails(this, img);
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
}
