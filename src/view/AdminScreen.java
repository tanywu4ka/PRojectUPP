package view;

import entity.City;
import entity.RouteName;
import entity.RouteStop;
import graphics.RoadMap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.App;
import core.DBApi;
import core.Pair;
import core.Utils;

public class AdminScreen extends JPanel {
	
	private JPanel sideBar;
	private JPanel resultTab;
	private JPanel wrap;
	private CityList cityList;
	
	private DBApi api = DBApi.GET;
	
	private ArrayList<Pair<City, Boolean>> routeStations = new ArrayList<>();
	
	public AdminScreen() {
		super();
		
		setBackground(new Color(235, 235, 235));
		setBounds(0, 0, App.GET.getWidth() + 10, App.GET.getHeight() + 10);
		setLayout(null);
		
		sideBar = new JPanel();
		sideBar.setBackground(new Color(35, 35, 35));
		sideBar.setLayout(null);
		sideBar.setBounds(0,  0, 250, App.GET.getHeight() + 10);
		
		add(sideBar);
		
		int y = 15;
		
		SideBarItem getSats = new SideBarItem("Add route");
		getSats.setBounds(0, y, 250, 40);
		sideBar.add(getSats);
		getSats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showAddRouteScreen();
			}
		});
		
		y += 40;
		
		SideBarItem showRotues = new SideBarItem("Routes");
		showRotues.setBounds(0, y, 250, 40);
		sideBar.add(showRotues);
		showRotues.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRouteList();
			}
		});
		
		
		resultTab = new ResultTab();
		wrap = Utils.wrapWithBorderLayoutPanel(resultTab);
		add(wrap);
		
	}
	
	
	private Field routeNameField;
	
	private void showAddRouteScreen() {
		resultTab.removeAll();
		resultTab.add(Utils.getResultTitle("Add route panel", 450));
		
		Button addRoute = new Button("Edit route points");
		addRoute.setBounds(0, 70, 250, 50);
		resultTab.add(addRoute);
		
		addRoute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRouteWindow();
			}
		});
		
		Button saveRoute = new Button("Save route");
		saveRoute.setBounds(270, 70, 180, 50);
		resultTab.add(saveRoute);
		
		saveRoute.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveRoute();
			}
		});
		
		Label routeNameLabel = new Label("Route name:");
		routeNameLabel.setBounds(10, 150, 130, 25);
		resultTab.add(routeNameLabel);
		
		routeNameField = new Field();
		routeNameField.setBounds(150, 150, 130, 25);
		resultTab.add(routeNameField);
		
		cityList = new CityList();
		cityList.setBounds(0, 200, 300, 0);
		resultTab.add(cityList);
		
		int width = 450;
		resultTab.setPreferredSize(new Dimension(width, App.GET.getHeight() - 10));
		
		wrap.validate();
		wrap.repaint();
	}
	
	private void showRouteWindow() {
		final JDialog dd = new JDialog(App.GET.getFrame(), "Interactive route editor", Dialog.ModalityType.DOCUMENT_MODAL);
		
		dd.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Window closing");
				cityList.update();
				resultTab.setPreferredSize(new Dimension(450, cityList.getHeight() + 200));
				wrap.validate();wrap.repaint();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		routeStations = new ArrayList<>();
		JPanel inner = new RoadMap(dd, routeStations, true);
		dd.add(inner);
		dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
		dd.setLocationRelativeTo(null);
		dd.setResizable(false);
		dd.setVisible(true);
	}
	
	private void saveRoute() {
		String routeName = routeNameField.getText().trim();
		
		if (!routeName.isEmpty()) {
			if (routeStations.size() < 2) {
				Utils.alert("Route must have at least 2 stops");
			} else {
				
				int routeId = api.addRouteName(routeName);
				int order = 0;
				
				for(Pair<City, Boolean> pair: routeStations) {
					api.AddRouteStop(routeId, order++, pair.first.getId(), pair.second);
				}
				
				Utils.alert("Route has been added");
				//show empty add route window
				showAddRouteScreen();
			}
			
		} else {
			Utils.alert("Route name is empty");
		}
	}
	
	public class CityList extends JPanel {
		
		public CityList() {
			setLayout(null);
			setPreferredSize(new Dimension(120, 50));
		}
		
		public void update() {
			removeAll();
			
			int y = 0;

			for (Pair<City, Boolean> p: routeStations) {
				if (p.second) {
					JLabel cityLabel = new Label(p.first.getCity_name());
					cityLabel.setFont(App.GET.getFont(22));
					cityLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
					cityLabel.setBounds(10, y, 140, 50);
					cityLabel.setBackground(new Color(199,199,199));
					cityLabel.setOpaque(true);
					add(cityLabel);
					
//					if (y > 0) {
//						NumTextField f = new NumTextField();
//						f.setHorizontalAlignment(JTextField.CENTER);
//						f.setBounds(175, y - 30, 70, 50);
//						f.setFont(App.GET.getFont(22));
//						add(f);
//					}
					
					y += 60;
				}
				
			}
			
			
			setBounds(10, 200, 300, y);
		}
		
	}
	
	public void showRouteList() {
		resultTab.removeAll();
		resultTab.add(Utils.getResultTitle("Routes:", 450));
		
		int y = 80;
		int x = 10;
		
		List<RouteName> routeNames = api.getRouteNames();
		
		for(final RouteName route: routeNames) {
			Label routeLabel = new Label(route.getRoute_name());
			routeLabel.setBounds(x, y, 200, 40);
			resultTab.add(routeLabel);
			
			Button viewBtn = new Button("View");
			viewBtn.setBounds(x + 250, y + 5, 80, 35);
			viewBtn.setFont(App.GET.getFont(18));
			resultTab.add(viewBtn);
			
			final List<RouteStop> routeStops = api.getRouteStops(route);
			
			viewBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//show route without allowing to change it
					routeStations = new ArrayList<>();
					
					for(RouteStop stop: routeStops) {
						City c = api.getCityById(stop.getCity_id());
						Pair<City, Boolean> pair = new Pair<City, Boolean>(c, stop.isIs_stop());
						routeStations.add(pair);
					}
					
					final JDialog dd = new JDialog(App.GET.getFrame(), "View route: " + route.getRoute_name(), Dialog.ModalityType.DOCUMENT_MODAL);
					
					JPanel inner = new RoadMap(dd, routeStations, false);
					dd.add(inner);
					dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
					dd.setLocationRelativeTo(null);
					dd.setResizable(false);
					dd.setVisible(true);
					
				}
			});
			
			
			y += 50;
			
			
			for(RouteStop stop: routeStops) {
				ArrayList<String> stopData = Utils.toArrayRouteStop(stop);
				resultTab.add(Utils.getRow(stopData, y, ""));
				y += 50;
			}
			
		}

		int width = 450;
		resultTab.setPreferredSize(new Dimension(width, y+20));



		wrap.validate();
		wrap.repaint();
	}
	

}












