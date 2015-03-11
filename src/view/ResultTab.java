package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import core.App;

public class ResultTab extends JPanel {

	public ResultTab() {
		super();

		setLayout(null);

		setPreferredSize(new Dimension(App.GET.getWidth() - 260, App.GET.getHeight() - 20));
		//setBounds(250, 0, App.GET.getWidth() - 250, App.GET.getHeight() );
	}
	
}
