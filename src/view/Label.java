package view;

import java.awt.Color;

import javax.swing.JLabel;

import core.App;

public class Label extends JLabel {

	public Label(String text) {
		super(text);
		
		configView();
	}
	
	private void configView() {
		setFont(App.GET.getFont(18));
		setForeground(new Color(0, 0, 0));
		
		
	}
}
