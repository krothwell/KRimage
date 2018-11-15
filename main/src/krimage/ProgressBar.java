/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 *
 * @author Kit
 */
public class ProgressBar extends JPanel{
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private KRimage main;
    private DataInputStream lPathInput;
    private int imgProgressNumber;
    private FF ff = new FF();
    private JProgressBar progressBar;
    private int locationTotal;
    private int imageTotal;
    JPanel progressBarPnl;
    JLabel progressLbl1;
    JLabel progressLbl2;
    Color krDarkBlue = new Color(33, 68, 115);
    Color systemDarkGrey = new Color(100, 100, 100);
    private Color krLightBlue = new Color(201,230,255);
    
    public ProgressBar(final KRimage mainController)
    {
        main = mainController;
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        
        this.setLayout(layout);
        this.setBackground(krDarkBlue);
        progressBar = new JProgressBar();
        progressBar.setVisible(false);
        
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0,0,20);
        progressBar.setMinimum(0);
        progressBar.setBorder(
                BorderFactory.createLineBorder(krLightBlue,2));
        progressBar.setBackground(krDarkBlue);
        progressBar.setForeground(krLightBlue);
        //progressLbl1 = new JLabel();
        progressLbl2 = new JLabel();
        //progressLbl1.setForeground(systemDarkGrey);
        progressLbl2.setForeground(krLightBlue);
        
        FlowLayout fl = new FlowLayout(FlowLayout.RIGHT, 2, 2);
        
        progressBarPnl = new JPanel();
        progressBarPnl.setLayout(fl);
        progressBarPnl.add(progressLbl2);
        progressBarPnl.add(progressBar);
        progressBarPnl.setBackground(krDarkBlue);
        
        //progressLbl.setAlignmentX(SwingConstants.RIGHT);
        //this.add(progressLbl1, gbc);
        this.add(progressBarPnl,gbc);
    }
    
    public JProgressBar getJProgressBar()
    {
        return progressBar;
    }
    
    public void setProgress()
    {
        progressBar.setValue(progressBar.getValue()+1);
        /**progressLbl1.setText("Image "
                + imageNum + " of " + locImgNum
                + " from location " + locationNum + " of " 
                + locationTotal + " (\""+location+"\").");*/
                
        progressLbl2.setText("Processing images: "
                + progressBar.getValue() + " of " + imageTotal + " ");
    }
    
    public void setProcessComplete()
    {
        //progressLbl1.setVisible(false);
        progressLbl2.setText(imageTotal + " images processed.");
        progressBar.setVisible(false);
    }
    
    public void setMaxValue(int i)
    {
        imageTotal= i;
        progressBar.setValue(0);
        progressBar.setMaximum(i);
        progressBar.setVisible(true);
        
    }
    
    public int getImageNumberFromPath(String path)
    {
        File dir = new File(path);
        File[] imgList = dir.listFiles(ff);
        int imgNumber = imgList.length;
        return imgNumber;
    }
    
    /**public int getImageTotal(String directoryPath)
    {
        lPathInput = main.readLocationFileInput("location_paths");
        locationTotal = 0;
        int imgNumber = 0;
        try
        {
            String path;
            
            while(true)
            {
                if (lPathInput!=null)
                {
                    path = lPathInput.readUTF();
                    imgNumber += getImageNumberFromPath(path);
                    locationTotal++;
                }
                else
                {
                    break;
                }
            }
            
        }
        catch (EOFException eof)
        {
            System.out.println("Finished reading file");
        }
        catch (IOException ioe)
        {
            System.out.println("File could not be read");
        }
        finally 
        {
            main.closeLocationFile(lPathInput);
        }
        imageTotal = imgNumber;
        return imgNumber;
    }*/
}
