package krimage;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.BorderFactory.createDashedBorder;
import javax.swing.border.*;


public class Style extends JFrame
{
    //SQLite db = new SQLite();
    //int colorR, colorG, colorB;
    
    
    // Colors: red = 176, 23, 31; blue = 61, 89, 171; green 48, 128, 20; purple 180, 82, 205;
    
    // systemColor takes RGB values from database
    Color systemExtraLightGrey = new Color(240, 240, 240);
    Color systemLightGrey = new Color(227, 227, 227);
    Color systemDarkGrey = new Color(163, 163, 163);
    Color systemExtraDarkGrey = new Color(89, 89, 89);
    
    
    // Borders
    public EmptyBorder borderCustom(int top, int right, int bottom, int left)
    {
        EmptyBorder borderCustom = new EmptyBorder(top, right, bottom, left);
        return borderCustom;
    }
    
    EmptyBorder border30 = new EmptyBorder(30, 30, 30, 30); 
    EmptyBorder border20 = new EmptyBorder(20, 20, 20, 20); 
    EmptyBorder border10 = new EmptyBorder(10, 10, 10, 10);
    EmptyBorder borderScroll = new EmptyBorder(0,0,0,0); 
    
    Border redBorder = BorderFactory.createLineBorder (Color.red, 2); 
    
    // Fonts
    
    // Regular
    Font fontXL = new Font("Arial", Font.PLAIN, 50);
    Font fontL = new Font("Arial", Font.PLAIN, 25);
    Font fontM = new Font("Arial", Font.PLAIN,18);
    Font fontS = new Font("Arial", Font.PLAIN,15);
    Font fontXS = new Font("Arial", Font.PLAIN, 13);
    // Bold
    Font fontXLb = new Font("Arial", Font.BOLD, 50);
    Font fontLb = new Font("Arial", Font.BOLD, 25);
    Font fontMb = new Font("Arial", Font.BOLD,18);
    Font fontSb = new Font("Arial", Font.BOLD,15);
    Font fontXSb = new Font("Arial", Font.BOLD, 13);
    

    
    
    // Smooth Scrolling
    public void smoothScroll(JScrollPane s)
    {
        s.getVerticalScrollBar().setUnitIncrement(20);
    }
    
    
    // Center a frame on the screen (vertically and horizontally)
    public void centerFrame(JFrame f)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
    }
    
    
    
    // Creates a pop-up frame with appropriate warning message and button to close pop-up
    public void createPopUpFrame(String message, int width, int height)
    {
        JFrame f = new JFrame();
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        f.add(p);
        JLabel l = new JLabel(message, SwingConstants.CENTER);
        l.setForeground(Color.white);
        l.setBorder(border20);
        f.add(p);
        stylePopUpFrame(f, width, height);
        gbc.gridy = 0;
        p.add(l, gbc);
        JButton b = new JButton("Ok");
        gbc.gridy = 1;
        p.add(b, gbc);
        buttonToCloseFrame(b, f);
    }
    
   
    // Style a custom pop up box where 'createPopUpFrame' is too restrictive - no title bar, red bg, custom sizes
    public void stylePopUpFrame(JFrame f, int width, int height)
    {
        f.setSize(width, height);
        centerFrame(f);
        f.setUndecorated(true);
        f.setResizable(false);
        f.setVisible(true);
        f.setVisible(true);
    }
    
    
    //ActionListener to close current frame. Pass through the button and the frame to close as arguments
    public void buttonToCloseFrame(JButton b, final JFrame f)
    {
        b.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    f.dispose();
                }
            });
    }
    
}
