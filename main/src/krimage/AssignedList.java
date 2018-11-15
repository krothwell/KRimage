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
import javax.swing.SwingUtilities;

/**
 *
 * @author Kit
 */
public class AssignedList extends KRList implements MouseListener, ActionListener
{
    ImageAlbumAUlists mainPnl;
    public AssignedList(DefaultListModel dlm, ImageAlbumAUlists main)
    {
        removeAddBtn();
        mainPnl = main;
        setLblTitle("Albums assigned");
        setDLM(dlm);
        getList().addMouseListener(this);
        setRenderer(new ImageIcon(getClass().getClassLoader().getResource(
                "resources/albumIcon.jpg")), new Font("Arial", Font.PLAIN, 14));
        
        getList().addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ( SwingUtilities.isLeftMouseButton(e) )
        {
            mainPnl.assignAlbum(
                    (String)getList().getSelectedValue(),
                    getList().locationToIndex(e.getPoint()),
                    "unassign"); 
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
        
    }
}
