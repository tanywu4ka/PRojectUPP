package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import core.App;

public class SplashScreen extends JPanel {

	private BufferedImage image;
	private BufferedImage image2;
	
	public SplashScreen() {
		super();
		
		configView();
	}
	
	private void configView() {
		setBounds(0, 0, App.GET.getWidth() + 10, App.GET.getHeight() + 10);
		setBackground(new Color(0, 0, 0));
		ImagePanel("data\\splash.png");
	}


    public void ImagePanel(String pic) {
       try {                
          image = ImageIO.read(new File(pic));
       } catch (IOException ex) {
    	   	ex.printStackTrace();
       }
       
       validate();
       repaint();
      
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 105, 5, null);          
    }
    
    

	
}