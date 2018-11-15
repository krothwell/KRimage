/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 *
 * @author Kit
 */
public class UnassignedList extends KRList implements MouseListener
{
    ImageAlbumAUlists mainPnl;
    
    public UnassignedList(DefaultListModel dlm, ImageAlbumAUlists main)
    {
        removeAddBtn();
        mainPnl = main;
        setLblTitle("Albums unassigned");
        setDLM(dlm);
        getList().addMouseListener(this);
        setRenderer(new ImageIcon(getClass().getClassLoader().getResource(
                "resources/albumUnassignedIcon.jpg")), new Font("Arial", Font.ITALIC, 14));
        getList().addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if ( SwingUtilities.isLeftMouseButton(e) )
        {
            mainPnl.assignAlbum(
                    (String)getList().getSelectedValue(),
                    getList().locationToIndex(e.getPoint()),
                    "assign"); 
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
