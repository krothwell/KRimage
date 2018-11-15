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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Kit
 */
public class KRalertDialog extends JFrame implements ActionListener{
    private JPanel jpanel = new JPanel();
    
    private GridBagConstraints gbc = new GridBagConstraints();
    private JLabel instruction = new JLabel();
    private JTextField txtBox = new JTextField();
    private JButton btnok = new JButton("OK");
    private JButton btncancel = new JButton("Cancel");
    private String txtBoxTxt;
    private JLabel txtConfirm;
    private JPanel btnPanel;
    private JPanel row1Panel;
    private JPanel row2Panel;
    private JPanel errorPnl;
    private FlowLayout errorRow;
    private JLabel errorMessage;
    
    FlowLayout rowLayout1;
    FlowLayout rowLayout2;
    FlowLayout rowLayout3;
    ImageAlbumList aMaster;
    DirectoryList dMaster;
    String strType;
            
    public KRalertDialog(JPanel listMaster, String type)
    {
        txtBox.setHorizontalAlignment(JTextField.CENTER);
        errorRow = new FlowLayout(FlowLayout.CENTER,15,0);
        errorPnl = new JPanel();
        errorPnl.setLayout(errorRow);
        
        errorMessage = new JLabel();
        errorMessage.setForeground(Color.red);
        
        errorPnl.add(errorMessage);
        strType = type;
        try
        {
            aMaster = (ImageAlbumList)listMaster;
        }
        catch(ClassCastException cee)
        {
            System.out.println("album list failed conversion,"
                    + "should load directory list instead");
        }
        try
        {
            dMaster = (DirectoryList)listMaster;
        }
        catch(ClassCastException cee)
        {
            System.out.println("dir list failed conversion,"
                    + "should load album list instead");
        }
        
        this.setMinimumSize(new Dimension(300,100));
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        GridLayout layout = new GridLayout(0,1);
        jpanel.setLayout(layout);
        
        row1Panel = new JPanel();
        btnPanel = new JPanel();
        row2Panel = new JPanel();
        FlowLayout rowLayout1 = new FlowLayout(FlowLayout.CENTER,15,10);
        FlowLayout rowLayout2 = new FlowLayout(FlowLayout.CENTER,15,0);
        FlowLayout rowLayout3 = new FlowLayout(FlowLayout.CENTER,15,0);
        btnPanel.setLayout(rowLayout3);
        row1Panel.setLayout(rowLayout1);
        row2Panel.setLayout(rowLayout2);
        txtConfirm = new JLabel();
        
        
        btnPanel.add(btncancel);
        btnPanel.add(btnok);
        
        this.add(jpanel);

        
        if ( type.equals("add") )
        {
            buildThisAdd();
        }
        else if (type.equals("rename"))
        {
            buildThisRename(aMaster.getNameOfAlbumSelected());
        }
        else if ( type.equals("remove") )
        {
            buildThisRemove();
        }
        else if ( type.equals("dirDup") )
        {
            buildThisDirDuplicate(dMaster.getDirDuplicateName());
        }
        else if ( type.equals("noneselected") )
        {
            buildThisNoneSelected();
        }
        else if ( type.equals("dirmissing") )
        {
            buildThisDirMissing();
        }
        
        btnok.addActionListener(this);
        btncancel.addActionListener(this);
        
        this.setVisible(true);
        this.pack();
      
    }
    
    public void buildThisAdd()
    {
        row1Panel.add(instruction);
        row2Panel.add(txtBox);
        jpanel.add(row1Panel);
        instruction.setText("Enter the name of your new album:");
        txtBox.setColumns(20);
        jpanel.add(row1Panel);
        jpanel.add(row2Panel);

        jpanel.add(btnPanel);

    }
    
    public void buildThisNoneSelected()
    {
        row1Panel.add(instruction);
        instruction.setText("<html>You must select an image to use this option.<br><br>"
                + "You can select images with the mouse button from albums or locations containing images.");
        
        btnPanel.remove(btncancel);
        jpanel.add(row1Panel);
        jpanel.add(btnPanel);
    }
    
    public void buildThisDirDuplicate(String dirName)
    {
        row1Panel.add(instruction);
        instruction.setText("Path for directory \""
                +dirName+ "\" has already been added");
        
        btnPanel.remove(btncancel);
        jpanel.add(row1Panel);
        jpanel.add(btnPanel);
    }
    
    public void buildThisDirMissing()
    {
        row1Panel.add(instruction);
        instruction.setText("Can't load images, the directory has been probably been moved or deleted.");
        
        btnPanel.remove(btncancel);
        jpanel.add(row1Panel);
        jpanel.add(btnPanel);
    }
    
    public void buildThisRename(String albumName)
    {
        setTextBoxValue(albumName);
        row1Panel.add(instruction);
        row2Panel.add(txtBox);
        jpanel.add(row1Panel);
        instruction.setText("Enter the new name for the album\""+ albumName +"\":");
        txtBox.setColumns(20);
        jpanel.add(row1Panel);
        jpanel.add(row2Panel);
        jpanel.add(btnPanel);
    }
    
    public void buildThisRemove()
    {
        row1Panel.add(instruction);
        row2Panel.add(txtConfirm);
        txtConfirm.setText("Click \"OK\" to confirm or select \"Cancel\"");
        jpanel.add(row1Panel);
        jpanel.add(row2Panel);
        //instruction.setAlignmentX(TOP_ALIGNMENT);
        String albumName = aMaster.getNameOfAlbumSelected();
        instruction.setText("<html>Are you sure you want to remove the album"
                + " \""+ albumName + "\"?");
        jpanel.add(btnPanel);
    }
    
    public void addErrorRow(String emessage)
    {
        errorMessage.setText(emessage);
        jpanel.add(errorPnl);
        this.pack();
        this.revalidate();
    }
    
    public String getTextBoxValue()
    {
        txtBoxTxt = txtBox.getText();
        return txtBoxTxt;
    }
    
    public void setTextBoxValue(String text)
    {
        txtBox.setText(text);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btncancel)
        {
            this.dispose();
        }
        else if (e.getSource() == btnok)
        {
            /**
             * if type of dialog is to add an album, check if the name is a
             * duplicate and add an error if it is
             */
            if (strType.equals("add"))
            {
                getTextBoxValue();
                if(aMaster.isDuplicateAlbum(txtBoxTxt))
                {
                    addErrorRow("The album name you have entered already exists"
                            + ", please try again.");
                }
                else if(txtBoxTxt.length()>50|| txtBoxTxt.length()<1)
                {
                    addErrorRow("The album name you have entered must be"
                            + " between 1 and 50 characters");
                }
                else
                {
                aMaster.addAlbumNameToList(txtBoxTxt);
                this.dispose();
                }
            }
            else if (strType.equals("remove"))
            {
                aMaster.removeAlbumNameFromList();
                this.dispose();
            }
            else if (strType.equals("rename"))
            {
                getTextBoxValue();
                aMaster.renameAlbumNameFromList(txtBoxTxt);
                this.dispose();
            }
            else if (strType.equals("dirDup"))
            {
                this.dispose();
            }
            else if (strType.equals("noneselected"))
            {
                this.dispose();
            }
            else if (strType.equals("dirmissing"))
            {
                this.dispose();
            }
        }
    }    
}
