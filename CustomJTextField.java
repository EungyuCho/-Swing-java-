package team_p1;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomJTextField extends JTextField{
     
    private BufferedImage image = null;
     
    public CustomJTextField(int i){
        super(i);
        super.setBorder(new EmptyBorder(2,2,2,2));
        super.setOpaque(false);
         
    }
    public void paintComponent(Graphics g){
        if(image == null || getWidth() != image.getWidth() || getHeight() != image.getHeight()){
             
            image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
            Graphics2D gd = (Graphics2D)image.getGraphics();
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); 
            gd.setPaint(new GradientPaint(0,0, Color.gray, 0,getHeight(),Color.white.brighter(), true));
            gd.fillRoundRect(0, 0, getWidth(), getHeight(),10,10);
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
            gd.dispose();
        }
        g.drawImage(image,0,0,null);
        super.paintComponent(g);
    }
}