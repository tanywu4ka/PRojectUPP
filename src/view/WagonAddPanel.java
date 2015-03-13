package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import core.App;
import core.Utils;
import entity.Wagon;

public class WagonAddPanel extends JPanel {
	private ArrayList<Wagon> wagons;
	private JPanel wrap;
	private JPanel wagonLayout;

	private JComboBox types;
	
	public WagonAddPanel(ArrayList<Wagon> w) {
		super();
		
		wagons = w;
		
		setLayout(null);
		
		setBounds(0, 0, 480, 300);
		
		wagonLayout = new JPanel();
		wagonLayout.setLayout(null);
		
		wagonLayout.setBackground(Color.WHITE);
		
		wrap = Utils.wrapWithBorderLayoutPanel(wagonLayout);
		wrap.setBounds(0, 0, 200, 270);
		add(wrap);
		
		types = Utils.getDropDown(new String[] {"Regular", "Coupe", "SW"});
		types.setBounds(240, 100, 100, 30);
		add(types);
		
		Button addBtn = new Button("Add");
		addBtn.setBounds(350, 100, 80, 30);
		addBtn.setFont(App.GET.getFont(18));
		add(addBtn);
		
		addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String type = types.getSelectedItem().toString();
				Wagon w = null;
				if (type.equals("Regular")) {
					w = new Wagon();
				}
				
				if (type.equals("Coupe")) {
					w = new Wagon(Wagon.WagonType.COUPE);			
				}
				
				if (type.equals("SW")) {
					w = new Wagon(Wagon.WagonType.SW);	
				}
				
				wagons.add(w);
				
				updateWagonList();
			}
		});
		
		updateWagonList();

		wrap.validate();
		wrap.repaint();
	}
	
	private void updateWagonList() {
		wagonLayout.removeAll();
		int height = 5;
		
		for (Wagon w: wagons) {
			JLabel label = new JLabel(w.getWagonType().toString(), SwingConstants.CENTER);
			label.setOpaque(true);
			label.setBackground(new Color(149, 217, 128));
			label.setFont(App.GET.getFont(28));
			label.setBounds(10, height, 170, 45);
			wagonLayout.add(label);
			height += 60;
		}
		
		wagonLayout.setPreferredSize(new Dimension(180, height));
		wrap.validate();
		wrap.repaint();
	}
	

}
