package view;


import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;

import core.App;


public class BtnEdit extends Button {
	private String 	objType;
	private int 	objId;

	public BtnEdit(String text, String type, String id) {
		super(text);
		
		objType = type;
		objId = Integer.parseInt(id);
		
		setBackground(new Color(26, 141, 148));
		setHoverBackgroundColor(new Color(58, 171, 178));
		setPressedBackgroundColor(new Color(10, 132, 140));
		setFont(App.GET.getFont(18));
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				UserScreen s = (UserScreen)App.GET.getCurrentScene();
//				
//				
//				
//				JDialog dd = null;
//				JPanel inner = null;
//
//				if (objType.equals("Asteroid")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing asteroid", Dialog.ModalityType.DOCUMENT_MODAL);
//					Asteroid t = DataBaseAPI.GET.getAsteroidByID(objId);
//					inner = new AddAsteroidPanel(dd, t);
//				}
//				
//				if (objType.equals("Planet")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing planet", Dialog.ModalityType.DOCUMENT_MODAL);
//					Planet t = DataBaseAPI.GET.getPlanetByID(objId);
//					inner = new AddPlanetPanel(dd, t);
//				}
//				
//				if (objType.equals("Star")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing star", Dialog.ModalityType.DOCUMENT_MODAL);
//					Star t = DataBaseAPI.GET.getStarById(objId);
//					inner = new AddStarPanel(dd, t);
//				}
//				
//				if (objType.equals("Hole")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing black hole", Dialog.ModalityType.DOCUMENT_MODAL);
//					BlackHole t = DataBaseAPI.GET.getHoleById(objId);
//					inner = new AddHolePanel(dd, t);
//				}
//				
//				if (objType.equals("Comet")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing comet", Dialog.ModalityType.DOCUMENT_MODAL);
//					Comet t = DataBaseAPI.GET.getCometById(objId);
//					inner = new AddCometPanel(dd, t);
//				}
//				
//				if (objType.equals("Galaxy")) {
//					dd = new JDialog(App.GET.getFrame(), "Editing galaxy", Dialog.ModalityType.DOCUMENT_MODAL);
//					Galaxy t = DataBaseAPI.GET.getGalaxyById(objId);
//					inner = new AddGalaxyPanel(dd, t);
//				}
				
				
//				dd.add(inner);
//				dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
//				dd.setLocationRelativeTo(null);
//				dd.setResizable(false);
//				dd.setVisible(true);
			}
		});
	
		
	}
	
}
