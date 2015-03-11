package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import core.App;
import core.Utils;

public class Register extends JPanel {
	
	private JDialog 		dd;
	private int 			width;
	private int 			height;
	private Field 			name;
	private Pass 			pass;
	private NumTextField 	phone;
	
	public Register(JDialog d) {
		super();
		dd = d;
		
		setView();
	}
	
	private void setView() {
		width = 400;
		height = 330;
		
		int x = 10;
		int y = 100;
		int w = 150;
		int w2 = 200;
		int h = 30;
		int x2 = 170;
		
		setLayout(null);
		setPreferredSize(new Dimension(width, height));
		Font f = App.GET.getFont(20);
		//add(Utils.getAddTitle("Register", width));
		
		Label label;
		
		/////////////////////////////
		
		label = new Label("Name");
		label.setBounds(x, y, w, h);
		add(label);
		
		name = new Field();
		name.setBounds(x2, y, w2, h);
		add(name);
		
		y += 40;
		
		/////////////////////////////
		
		label = new Label("Pass");
		label.setBounds(x, y, w, h);
		add(label);
		
		pass = new Pass();
		pass.setBounds(x2, y, w2, h);
		add(pass);
		
		y += 40;
		
		/////////////////////////////
		
		label = new Label("Phone");
		label.setBounds(x, y, w, h);
		add(label);
		
		phone = new NumTextField();
		phone.setBounds(x2, y, w2, h);
		add(phone);
		
		y += 40;
		
		/////////////////////////////
		
		Button add = new Button("Register");
		add.setBounds(x2, height - 80, w2, 40);
		add.setFont(f);
		add(add);
		
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = Utils.parseIntField(name.getText().trim());
				String userpass = Utils.parseIntField(pass.getText().trim());
				String userphone = Utils.parseIntField(phone.getText().trim());

				Utils.log("Phone is " + userphone);
				if (username.isEmpty() || userpass.isEmpty()) {
					Utils.alert("Fill in name and pass fields");
				} else  {
					//DataBaseAPI.GET.addUser(username, userpass, userphone);
					dd.setVisible(false);
				}
			}
		});
		
	}

}
