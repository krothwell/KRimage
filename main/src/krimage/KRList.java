/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krimage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author Kit
 */
public class KRList extends JPanel{
    
    private JList aListBox;
    
    private GridBagConstraints gbc;
    
    private JScrollPane scrollPane;
    
    private KRlistCellRenderer renderer;
    
    private JPanel lblTitlePnl;
    private JPanel listPnl;
    
    private FlowLayout titleLayout;
    private GridBagLayout listLayout;
    private BoxLayout topLayout;
    
    private Color krLightBlue = new Color(201,230,255);
    private Color krDarkBlue = new Color(33, 68, 115);
    
    private JButton addBtn;
    
    private JLabel lblTitle;
    
    private ImageIcon addIcon;
    private ImageIcon addIcon2;
    
    public KRList()
    {
        lblTitlePnl = new JPanel();
        listPnl = new JPanel();
        
        topLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        titleLayout = new FlowLayout(FlowLayout.RIGHT,20,0);
        listLayout = new GridBagLayout();
        
        this.setLayout(topLayout);
        lblTitlePnl.setLayout(titleLayout);
        listPnl.setLayout(listLayout);
        
        this.setBorder(BorderFactory.createLineBorder(krLightBlue, 2));
        lblTitlePnl.setBorder(
                BorderFactory.createMatteBorder(0,0,2,0,krLightBlue));
        
        addIcon = new ImageIcon(getClass().getClassLoader().getResource(
                "resources/addIcon.jpg"));
        addIcon2 = new ImageIcon(getClass().getClassLoader().getResource(
                "resources/addIcon2.jpg"));
        addBtn = new JButton(addIcon);
        addBtn.setRolloverIcon(addIcon2);
        addBtn.setBorder(BorderFactory.createLineBorder(krDarkBlue, 2));

        lblTitle = new JLabel("My albums");
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTitle.setForeground(krLightBlue);
        lblTitlePnl.add(lblTitle);
        lblTitlePnl.add(addBtn);
        lblTitlePnl.setBackground(krDarkBlue);
        
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx=1;
        gbc.weighty=1;
        gbc.gridx = 0;
        gbc.gridy =0;
        
        
        aListBox = new JList();
        aListBox.setBackground(krDarkBlue);
        aListBox.setFixedCellWidth(200);

        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
	scrollPane.getViewport().add(aListBox );
        listPnl.add(scrollPane, gbc);
        
        this.add(lblTitlePnl);
	this.add(listPnl);
    }
    
    /**
     * Removes the add + button which normally sits alongside the list title
     * if not desired.
     */
    public void removeAddBtn()
    {
        lblTitlePnl.remove(addBtn);
        this.revalidate();
        this.repaint();
    }
    
    /**
     * @param title sets the text of the label used for title.
     */
    public void setLblTitle(String title)
    {
        lblTitle.setText(title);
    }
    
    /**
     * @param icon the image icon appearing next to list entries
     * @param font style of the list text
     */
    public void setRenderer(ImageIcon icon, Font font)
    {
        renderer = new KRlistCellRenderer(icon, font);
        aListBox.setCellRenderer(renderer);
    }
    
    public void setDLM(DefaultListModel dlm)
    {
        aListBox.setModel(dlm);
    }
    
    /**
     * @return for extended class to add/remove list elements, etc.
     */
    public JList getList()
    {
        return aListBox;
    }
    
    /**
     * @return for extended class to add listeners etc.
     */
    public JButton getAddBtn()
    {
        return addBtn;
    }

   
}
