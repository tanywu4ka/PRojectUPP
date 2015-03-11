package view;



import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import algo.Edge;
import algo.EdgeWeightedGraph;
import core.App;
import core.Config;
import core.DBApi;
import core.Utils;
import entity.City;
import entity.Road;
import entity.StaffMember;
import graphics.RoadMap;
import view.Button;
import view.Field;
import view.Label;
import view.Pass;
import view.Register;

public class LoginScreen extends JPanel {
	
	private JTextField login;
	private JTextField pass;
	private DBApi api;
	
	public LoginScreen() {
		super();
		
		configView();
		
		api = DBApi.GET;
	}
	
	private void configView() {
		setBackground(new Color(34, 34, 34));
		setBounds(0, 0, App.GET.getWidth() + 10, App.GET.getHeight() + 10);
		setLayout(null);

		Label label = new Label("Log in");
		label.setBounds(460, 200, 100, 50);
		label.setForeground(new Color(255, 255, 255));
		label.setFont(App.GET.getFont(35));
		add(label);
		
		login = new Field();
		login.setBounds(420, 280, 180, 35);
		add(login);
		
		pass = new Pass();
		pass.setBounds(420, 330, 180, 35);
		add(pass);
		
		JButton go = new Button("Enter");
		go.setBounds(420, 380, 180, 40);
		add(go);
		
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkLogin();
			}
		});
		
//		JButton register = new Button("Register");
//		register.setBounds(420, 430, 180, 40);
//		add(register);
//		
//		register.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				final JDialog dd = new JDialog(App.GET.getFrame(), "Registration", Dialog.ModalityType.DOCUMENT_MODAL);
//				
//				JPanel inner = new Register(dd);
//				dd.add(inner);
//				dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
//				dd.setLocationRelativeTo(null);
//				dd.setResizable(false);
//				dd.setVisible(true);
//			}
//		});
		
		
		
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
				login.requestFocus(); 
		    } 
		} );
			
		pass.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					checkLogin();
				}
			}
		});
		
	}
	
	private void checkLogin() {
		
		StaffMember member = api.getStaffMember(login.getText(), pass.getText());
		
		if (member != null) {
			//Utils.alert("You are " + member.getRole());
			
			if (member.getRole().equals(Config.ADMIN_ROLE)) {
				App.GET.makeAdmin(true);
				System.out.println("ADMIN SET");
				App.GET.setScreen(new AdminScreen());
			} else {
				System.out.println("STAFF");
			}
			
			App.GET.setUser(member);
			
			//App.GET.setScreen
		} else {
			Utils.alert("Wrong login/password");
		}
		
//		
//		EdgeWeightedGraph cityGraph = new EdgeWeightedGraph(10);
//		
//		List<Road> roads = api.getRoads();
//		for(Road r: roads) {
//			//System.out.println(r);
//			cityGraph.addEdge(new Edge(r.getCity_one(), r.getCity_two(), r.getRoad_distance()));
//		}
//		
//		for(Edge e: cityGraph.adj(1)) {
//			System.out.println(e);
//		}
		
//		final JDialog dd = new JDialog(App.GET.getFrame(), "Choose route", Dialog.ModalityType.DOCUMENT_MODAL);
//		
//		JPanel inner = new RoadMap(dd);
//		dd.add(inner);
//		dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
//		dd.setLocationRelativeTo(null);
//		dd.setResizable(false);
//		dd.setVisible(true);
		
		
//		if (DataBaseAPI.GET.userExists(login.getText(), pass.getText())) {
//			User u = DataBaseAPI.GET.getExistingUser(login.getText(), pass.getText());
//			
//			if (u.getUser_permission() == 5) {
//				App.GET.makeAdmin(true);
//			} else {
//				App.GET.makeAdmin(false);
//			}
//			
//			App.GET.setUser(u);
//			App.GET.setScreen(new UserScreen());
//		} else {
//			Utils.alert("Wrong login/password");
//		}
	}

}
