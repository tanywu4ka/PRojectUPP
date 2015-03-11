package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JTextField;

import core.App;

public class Button extends JButton {
	
	public Button() {
		//super(text);
		
		setFont(App.GET.getFont(25));
		setForeground(new Color(0, 119, 0));
		setHorizontalAlignment(JTextField.CENTER);
	}
	
	
	 protected Color hoverBackgroundColor;
	 protected Color pressedBackgroundColor;


     public Button(String text) {
     	
         super(text);
         super.setContentAreaFilled(false);
         this.setCursor(new Cursor(Cursor.HAND_CURSOR));
         setFont(App.GET.getFont(25));
         this.setBorderPainted(false);
         this.setFocusPainted(false);
         this.setHoverBackgroundColor(new Color(10, 150, 10));
         this.setPressedBackgroundColor(new Color(5, 100, 5));
         this.setForeground(new Color(255, 255, 255));
         this.setBackground(new Color(0, 119, 0));
     }

     @Override
     protected void paintComponent(Graphics g) {
         if (getModel().isPressed()) {
             g.setColor(pressedBackgroundColor);
         } else if (getModel().isRollover()) {
             g.setColor(hoverBackgroundColor);
         } else {
             g.setColor(getBackground());
         }
         g.fillRect(0, 0, getWidth(), getHeight());
         super.paintComponent(g);
     }

     @Override
     public void setContentAreaFilled(boolean b) {
     }

     public Color getHoverBackgroundColor() {
         return hoverBackgroundColor;
     }

     public void setHoverBackgroundColor(Color hoverBackgroundColor) {
         this.hoverBackgroundColor = hoverBackgroundColor;
     }

     public Color getPressedBackgroundColor() {
         return pressedBackgroundColor;
     }

     public void setPressedBackgroundColor(Color pressedBackgroundColor) {
         this.pressedBackgroundColor = pressedBackgroundColor;
     }

}
