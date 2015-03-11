package view;

import core.Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import core.App;

public class SideBarItem extends JButton {

	private static SideBarItem currentItem;
	private boolean isSelected;
	private boolean isRed;
	private SideBarItem me;
	
	protected Color hoverBackgroundColor;
	protected Color pressedBackgroundColor;
	
	private boolean hasIcon;
	
	public SideBarItem(String text) {
		super(text);
		me = this;
		hasIcon = true;
		isRed = false;
		
		 super.setContentAreaFilled(false);
         this.setCursor(new Cursor(Cursor.HAND_CURSOR));
         setFont(App.GET.getFont(21));
         this.setBorderPainted(false);
         this.setFocusPainted(false);
         this.setForeground(new Color(255, 255, 255));
         this.setBackground(new Color(0, 119, 0));

		setHorizontalAlignment(JTextField.LEFT);
		setBorder(new EmptyBorder(0, 40, 0 ,0));
		
		setHoverBackgroundColor(new Color(58, 58, 58));
		setBackground(new Color(35, 35, 35));
		setPressedBackgroundColor(new Color(100, 100, 100));
		
		isSelected = false;
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SideBarItem.setCurrent(me);
			}
		});
		
	}
	
	public void setSelected(boolean state) {
		isSelected = state;
	}
	
	public static void setCurrent(SideBarItem item) {
		if (currentItem != null) {
			currentItem.setSelected(false);
			currentItem.validate();currentItem.repaint();
		}
		currentItem = item;
		item.setSelected(true);
		currentItem.validate();currentItem.repaint();
		
	}
	
	
	
	public boolean isRed() {
		return isRed;
	}

	public void setRed(boolean isRed) {
		this.isRed = isRed;
	}

	@Override
    protected void paintComponent(Graphics g) {
		
		if (isSelected == false) {
	        if (getModel().isPressed()) {
	            g.setColor(pressedBackgroundColor);
	        } else if (getModel().isRollover()) {
	            g.setColor(hoverBackgroundColor);
	        } else {
	            g.setColor(getBackground());
	        }
	        
        } else {
        	if (isRed) {
        		g.setColor(new Color(249, 62, 62));
        	} else {
        		g.setColor(new Color(0, 119, 9));
        	}
        	 
        }
		
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
        
        if (hasIcon) {
        	g.drawImage(Utils.getAdaptedImage("data\\light_icon.png", 15, 15), 15, 15, null);
        }
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
