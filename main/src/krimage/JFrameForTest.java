package krimage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Kit
 */
public class JFrameForTest extends JFrame{
    public JFrameForTest()
    {
        setTitle("GBTest Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,700); 
        setVisible(true);
        setBackground(Color.white);
        //pack();
    }
}
