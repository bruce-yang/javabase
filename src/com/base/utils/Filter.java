package com.base.utils;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jhlabs.image.EmbossFilter;

public class Filter {
    /**
     * Java�˾�
     * @param args
     */
    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new URL("http://www.baidu.com/img/baidu_sylogo1.gif"));
            BufferedImage timg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            new EmbossFilter().filter(img, timg);
            JFrame f = new JFrame("Java�˾�");
            f.getContentPane().add(new JLabel(new ImageIcon(timg)), BorderLayout.CENTER);
            f.setSize(img.getWidth(), img.getHeight() + 20);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
