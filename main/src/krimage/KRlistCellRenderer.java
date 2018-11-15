/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

/**
 *
 * @author Kit
 */
public class KRlistCellRenderer extends JLabel implements ListCellRenderer{
    Color krDarkBlue = new Color(33, 68, 115);
    Color krLightBlue = new Color(200, 222, 252);
    Border paddingBorder = BorderFactory.createEmptyBorder(5,10,5,10);
    ImageIcon icon;
    Font thisFont;
    public KRlistCellRenderer(ImageIcon imgicon, Font font)
    {
        thisFont = font;
        setOpaque(true);
        icon = imgicon;
    }
    @Override
    public Component getListCellRendererComponent(
            JList list, 
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus)
    {
        this.setText(value.toString());
        {
            this.setBackground(krDarkBlue);
            this.setForeground(krLightBlue);
            this.setBorder(paddingBorder);
            this.setIcon(icon);
            this.setFont(thisFont);
        }
        if (isSelected)
        {
            this.setBackground(Color.black);
        }

    return this;     
    }

}
