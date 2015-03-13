package core;


import java.awt.Dimension;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import entity.StaffMember;
import view.AdminScreen;
import view.LoginScreen;
import view.SplashScreen;


public enum App {
	GET;
	
	private JFrame  f;
	private JPanel wrapper;
	private JPanel currentPanel;
	private boolean isAdmin;
	private StaffMember currentUser;
	
	App() {
		isAdmin = false;
	}
	
	public int getWidth() {
		return 1000;
	}
	
	public int getHeight() {
		return 700;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	
	public void makeAdmin(boolean state) {
		isAdmin = state;
	}
	
	public void setUser(StaffMember u) {
		currentUser = u;
	}
	
	public StaffMember getUser()
	{
		return currentUser;
	}
	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	wrapper = new JPanel();
        		wrapper.setPreferredSize(new Dimension(getWidth(), getHeight()));
        		wrapper.setLayout(null);
        		//final SplashScreen s = new SplashScreen();
//        		currentPanel = s;
//        		wrapper.add(s);
        		
        		AdminScreen a = new AdminScreen();
        		currentPanel = a;
        		wrapper.add(a);

            	f = new JFrame("Ticket Kassa");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ImageIcon img = new ImageIcon("data\\icon.png");
                f.setIconImage(img.getImage());
                f.add(wrapper);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setResizable(false);
                f.setVisible(true); 
                
        		SwingUtilities.invokeLater(new Runnable() {
        			
        			@Override
        			public void run() {
        				//DBApi.GET.dummy();
        				//App.GET.setScreen(new LoginScreen());
        			}
        		});
            }
        });
	}
	
	
	
	public void setScreen(JPanel panel) {
		wrapper.remove(currentPanel);
		wrapper.add(panel);
		currentPanel = panel;
		
		wrapper.validate();
		wrapper.repaint();
	}

	public JPanel getCurrentScene() {
		return currentPanel;
	}
	
	public JFrame getFrame() {
		return f;
	}
	
	
	public Font getFont(int size) {
		return new Font("Segoe UI", Font.PLAIN, size);
	}
}