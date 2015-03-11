package view;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.App;

public class DelBtn extends Button {
	private String 	objType;
	private int 	objId;

	public DelBtn(String text, String type, String id) {
		super(text);
		
		objType = type;
		objId = Integer.parseInt(id);
		
		setBackground(new Color(249, 62, 62));
		setHoverBackgroundColor(new Color(250, 98, 98));
		setPressedBackgroundColor(new Color(248, 49, 49));
		setFont(App.GET.getFont(18));
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
//				UserScreen s = (UserScreen)App.GET.getCurrentScene();
//				
//				if (objType.equals("Asteroid")) {
//					DataBaseAPI.GET.removeAsteroid(objId);
//					s.showAllAsteroids();
//				}
//				
//				if (objType.equals("Planet")) {
//					DataBaseAPI.GET.removePlanet(objId);
//					s.showAllPlanets();
//				}
//				
//				if (objType.equals("Star")) {
//					DataBaseAPI.GET.removeStar(objId);
//					s.showAllStars();
//				}
//				
//				if (objType.equals("Hole")) {
//					DataBaseAPI.GET.removeHole(objId);
//					s.showAllHoles();
//				}
//				
//				if (objType.equals("Comet")) {
//					DataBaseAPI.GET.removeComet(objId);
//					s.showAllComets();
//				}
//				
//				if (objType.equals("Galaxy")) {
//					DataBaseAPI.GET.removeGalaxy(objId);
//					s.showAllGalaxies();
//				}
			}
		});
	
		
	}
	
}
