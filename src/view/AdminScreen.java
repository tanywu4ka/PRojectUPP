package view;

import entity.City;
import entity.RouteName;
import entity.RouteStop;
import entity.Train;
import entity.Wagon;
import graphics.RoadMap;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.App;
import core.DBApi;
import core.Pair;
import core.StationElement;
import core.Utils;
import core.TrainDays;

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
		
		y += 40;
		
		SideBarItem addTrain = new SideBarItem("Add Train");
		addTrain.setBounds(0, y, 250, 40);
		sideBar.add(addTrain);
		addTrain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showAddTrainWindow();
			}
		});
		
		y += 40;
		
		SideBarItem trainList = new SideBarItem("Train list");
		trainList.setBounds(0, y, 250, 40);
		sideBar.add(trainList);
		trainList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showTrainList();
			}
		});
		
		
		resultTab = new ResultTab();
		wrap = Utils.wrapWithBorderLayoutPanel(resultTab);
		add(wrap);
		
	}
	
	private String [] fullDays = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	
	
	private void showTrainList() {
		resultTab.removeAll();
		resultTab.add(Utils.getResultTitle("Train List", 450));
		
		List<Train> trains = api.getAllTrains();
		
		ArrayList<TrainDays> trainDayList = new ArrayList<>();
		
		for(Train t: trains) {
			trainDayList.add(new TrainDays(t));
		}
		
		int y = 80;
		int x = 10;
		
		for(int i = 0; i < 7; ++i) {
			Label dayLabel = new Label(fullDays[i] + ":");
			dayLabel.setBounds(x, y, 200, 40);
			resultTab.add(dayLabel);
			y += 60;
			ArrayList<String> headline = new ArrayList<>();
			headline.add("Num.");
			headline.add("Dept. time");
			headline.add("Route");
			resultTab.add(Utils.getRow(headline, y, ""));
			y += 50;
			for(TrainDays td: trainDayList) {
				if (td.isOnday(i)) {
					ArrayList<String> trainData = new ArrayList<>();
					trainData.add("" + td.getTrain().getId());
					trainData.add("" + td.getTrain().getTrainHours() + ":" + td.getTrain().getTrainMinutes());
					trainData.add(api.getRouteNameById(td.getTrain().getTrainRouteId()));
					
					resultTab.add(Utils.getRow(trainData, y, ""));
					y += 50;
					
				}
			}
			
			
		}
		
		System.out.println("Y: " + y);
		
		int width = 450;
		resultTab.setPreferredSize(new Dimension(width, y + 20));
		
		System.gc ();
		System.runFinalization ();
		
		wrap.validate();
		wrap.repaint();
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
		
		System.gc ();
		System.runFinalization ();
		
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
			
			int pos = 1;
			for(RouteStop stop: routeStops) {
				if (stop.isIs_stop()) {
					ArrayList<String> stopData = new ArrayList<>();
					stopData.add("" + (pos++));
					stopData.add(""+DBApi.GET.getCityById(stop.getCity_id()).getCity_name());
					resultTab.add(Utils.getRow(stopData, y, ""));
					y += 50;
				}
				
			}
			
		}

		int width = 450;
		resultTab.setPreferredSize(new Dimension(width, y+20));

		System.gc ();
		System.runFinalization ();

		wrap.validate();
		wrap.repaint();
	}
	
	private String[] days = new String[] {"Sun.", "Mon.", "Tues.", "Wed.", "Thurs.", "Fri.", "Sat."};
	private CheckBox [] daysCheckboxes = new CheckBox[7];
	
	private JPanel cities, routesPanel;
	private JComboBox hours, mins;
	private ArrayList<Wagon> wagonsList;
	private JComboBox routeNames;
	private ArrayList<StationElement> stationElements;
	
	private void saveTrain() {
		//boolean [] days, String hours, String mins, int routeId
		boolean [] days = new boolean[7];
		boolean atLeastOneDay = false;
		
		for(int i = 0; i < daysCheckboxes.length; ++i) {
			days[i] = daysCheckboxes[i].isSelected();
			if (!atLeastOneDay && days[i]) {
				atLeastOneDay = true;
			}
		}
		
		if (!atLeastOneDay) {
			Utils.alert("No days selected");
			return;
		}
		
		if (wagonsList.size() == 0) {
			Utils.alert("No wagons added");
			return;
		}
		
		for(StationElement e: stationElements) {
			if (e.getOrder() > 0 &&  e.getCost().getText().trim().isEmpty() ) {
				Utils.alert("No free stations (yet)");
				return;
			}
		}
		
		
		String h = hours.getSelectedItem().toString();
		String m = mins.getSelectedItem().toString();
		int routeId = Utils.getRouteNameByName(routeNames.getSelectedItem().toString()).getId();
		int trainId = api.addTrain(days, h, m, routeId);
		
		//add stations
		for(int i = 0; i < stationElements.size(); ++i) {
			StationElement e = stationElements.get(i);
			
			if (i == 0) {
				api.addTrainStaiton(i, "0", "0", "0", trainId, e.getCityId());
			} else {
				api.addTrainStaiton(i, e.getHours().getSelectedItem().toString(), e.getMins().getSelectedItem().toString(), 
						e.getCost().getText().trim(), trainId, e.getCityId());
			}
			
			
		}
		
		//add wagons
		for(Wagon w: wagonsList) {
			w.setTrainId(trainId);
			api.addWagon(w);
		}
		
		Utils.alert("Train added");
		
		showAddTrainWindow();
	}
	
	public void showAddTrainWindow() {
		resultTab.removeAll();
		resultTab.add(Utils.getResultTitle("Add train:", 200));
		
		wagonsList =  new ArrayList<>();
		
		Button save = new Button("Save");
		save.setBounds(580, 80, 150, 50);
		resultTab.add(save);
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				saveTrain();
			}
		});
		
		Button wagons = new Button("Wagons");
		wagons.setBounds(15, 80, 150, 50);
		resultTab.add(wagons);
		
		wagons.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog dd = new JDialog(App.GET.getFrame(), "Manage wagons: ", Dialog.ModalityType.DOCUMENT_MODAL);
				
				JPanel inner = new WagonAddPanel(wagonsList);
				dd.add(inner);
				dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
				dd.setLocationRelativeTo(null);
				dd.setResizable(false);
				dd.setVisible(true);
			}
		});
		
		int y = 180;
		int x = 10;
		
		{
			JPanel dayRow = new JPanel();
			dayRow.setLayout(null);
			
			Label daysLabel = new Label("Days: ");
			daysLabel.setBounds(0, 0, 70, 30);
			dayRow.add(daysLabel);
			
			ArrayList<CheckBox> dayCheckBoxes = new ArrayList<>();
			int dayX = 70;
			int checkPos = 0;
			for(String day: days) {
				CheckBox ch = new CheckBox(day);
				dayCheckBoxes.add(ch);
				ch.setBounds(dayX, 3, 70, 30);
				dayX += 70;
				dayRow.add(ch);
				daysCheckboxes[checkPos++] = ch;
			}
			//dayRow.setBackground(Color.BLUE);
			dayRow.setBounds(x, y, 700, 40);
	
			resultTab.add(dayRow);
		}
		
		y += 50;
		
		{
			JPanel timeRow = new JPanel();
			timeRow.setLayout(null);
			
			Label timeLabel = new Label("Time: ");
			timeLabel.setBounds(0, 0, 70, 30);
			timeRow.add(timeLabel);
			
			int timeX = 70;
			
			hours = Utils.getComboHours();
			hours.setBounds(timeX, 3, 50, 30);
			timeRow.add(hours);
			
			timeX += 60;
			
			mins = Utils.getComboMinutes();
			mins.setBounds(timeX, 3, 50, 30);
			timeRow.add(mins);
			
			timeRow.setBounds(x, y, 700, 40);
			//timeRow.setBackground(Color.BLUE);
			resultTab.add(timeRow);
		}
		
		y += 50;
		
		//routes
		{
			routesPanel = new JPanel();
			routesPanel.setLayout(null);
			
			
			Label routeLabel = new Label("Route: ");
			routeLabel.setBounds(0, 0, 70, 30);
			routesPanel.add(routeLabel);
			
			int routeX = 70;
			
			cities = new JPanel();
			//cities.setBackground(Color.RED);
			cities.setLayout(null);
			routesPanel.add(cities);
			
			Label stationNameLAbel = new Label("Station:");
			stationNameLAbel.setBounds(20, 50, 140, 30);
			routesPanel.add(stationNameLAbel);
			
			Label timeToLabel = new Label("Time to:");
			timeToLabel.setBounds(185, 50, 135, 30);
			routesPanel.add(timeToLabel);
			
			Label priceToLabel = new Label("Price to:");
			priceToLabel.setBounds(330, 50, 80, 30);
			routesPanel.add(priceToLabel);
			
			
			routeNames = Utils.getComboRoutes();
			routeNames.setBounds(routeX, 3, 250, 30);
			routesPanel.add(routeNames);
			
			routeNames.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent event) {
					if (event.getStateChange() == ItemEvent.SELECTED) {
				          String item = (String)event.getItem();
				          updateRoutedCities(item);
				       }
				}
			});
			
			routeX += 270;
			
			Button viewBtn = new Button("View");
			viewBtn.setBounds(routeX, 3, 100, 30);
			routesPanel.add(viewBtn);
			
			viewBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String currentRouteName = routeNames.getSelectedItem().toString();
					updateRouteStationsByName(currentRouteName);
					
					final JDialog dd = new JDialog(App.GET.getFrame(), "View route: " + currentRouteName, Dialog.ModalityType.DOCUMENT_MODAL);
					
					JPanel inner = new RoadMap(dd, routeStations, false);
					dd.add(inner);
					dd.setSize(inner.getPreferredSize().width, inner.getPreferredSize().height);
					dd.setLocationRelativeTo(null);
					dd.setResizable(false);
					dd.setVisible(true);
				}
			});
			
			
			//new row with cities
			updateRoutedCities(routeNames.getSelectedItem().toString());
			
			//routesPanel.setBackground(Color.GREEN);
			resultTab.add(routesPanel);
			
			y += routesPanel.getHeight();
		}
		
		//resultTab.setBackground(Color.BLUE);
		int width = 450;
		
		resultTab.setPreferredSize(new Dimension(width, y));
		
		System.gc ();
		System.runFinalization ();

		wrap.validate();
		wrap.repaint();
	}
	
	private void updateRouteStationsByName(String routeName) {
		RouteName currentName = Utils.getRouteNameByName(routeName);
		List<RouteStop> routeStops = api.getRouteStops(currentName);
		
		routeStations = new ArrayList<>();
		
		for(RouteStop stop: routeStops) {
			City c = api.getCityById(stop.getCity_id());
			Pair<City, Boolean> pair = new Pair<City, Boolean>(c, stop.isIs_stop());
			routeStations.add(pair);
		}
	}

	
	
	private void updateRoutedCities(String item) {
		cities.removeAll();
		int cityY = 0;
		updateRouteStationsByName(item);
		
		int stationCount = routeStations.size();
		stationElements = new ArrayList<>();
		int order = 0;
		
		for (Pair<City, Boolean> p: routeStations) {
			if (p.second) {
				
				JLabel cityLabel = new Label(p.first.getCity_name());
				cityLabel.setFont(App.GET.getFont(22));
				cityLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				cityLabel.setBounds(10, cityY, 140, 50);
				cityLabel.setBackground(new Color(199,199,199));
				cityLabel.setOpaque(true);
				cities.add(cityLabel);
				
				StationElement stElem = new StationElement();
				stElem.setOrder(order);
				stElem.setCityId(p.first.getId());
				
				if (order > 0) {
					JComboBox hours = Utils.getComboHours();
					hours.setBounds(175, cityY, 65, 50);
					cities.add(hours);

					JComboBox mins = Utils.getComboMinutes();
					mins.setBounds(245, cityY, 65, 50);
					cities.add(mins);
					
					NumTextField f = new NumTextField();
					f.setHorizontalAlignment(JTextField.CENTER);
					f.setBounds(320, cityY, 80, 50);
					f.setFont(App.GET.getFont(22));
					cities.add(f);
					
					stElem.setHours(hours);
					stElem.setMins(mins);
					stElem.setCost(f);
					
				}
				
				
				stationElements.add(stElem);
				
				++order;
				cityY += 60;
			}
			
		}

		cities.setBounds(10, 100, 500, cityY);
		
		routesPanel.setBounds(10, 280, 700, 100 + cities.getHeight());
		
		cities.validate();cities.repaint();
		
		resultTab.setPreferredSize(new Dimension(450, cities.getHeight() + 420));
		
		wrap.validate();wrap.repaint();
	}
	
}












