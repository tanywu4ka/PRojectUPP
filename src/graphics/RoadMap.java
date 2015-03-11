package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algo.Edge;
import algo.EdgeWeightedGraph;
import core.Config;
import core.DBApi;
import core.Pair;
import entity.City;
import entity.Road;



public class RoadMap extends JPanel {
	
	private JDialog dd;
	
	private final int mapWidth = 800;
	private final int mapHeight = 600;
	
	private int mouseX;
	private int mouseY;
	
	private List<City> cities = DBApi.GET.getCities();
	private List<Road> roads = DBApi.GET.getRoads();
	
	private ArrayList<Point> points = new ArrayList<>();
	private ArrayList<Pair<City, Boolean>> routeStations;
	
	private boolean editable;
	
	private BufferedImage cityMarker, cityMarkerSelected, cityMarkerNoStop;
	
	private boolean inRoute(City other) {
		for(Pair<City, Boolean> p: routeStations) {
			if (p.first.getId() == other.getId()) {
				return true;
			}
		}
		return false;
	}
	

	public RoadMap(JDialog d, ArrayList<Pair<City, Boolean>> routeStations, boolean isEditable) {
		dd = d;
		editable = isEditable;
		this.routeStations = routeStations;
		
		try {
			cityMarker = ImageIO.read(new File("data\\" + Config.CITY_MARKER));
			cityMarkerSelected = ImageIO.read(new File("data\\" + Config.CITY_MARKER_SELECTED));
			cityMarkerNoStop = ImageIO.read(new File("data\\" + Config.CITY_MARKER_NO_STOP));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		addMouseListeners();
	}
	
	private void addMouseListeners() {
		final EdgeWeightedGraph cityGraph = new EdgeWeightedGraph(10);
		for(Road r: roads) {
			cityGraph.addEdge(new Edge(r.getCity_one(), r.getCity_two(), r.getRoad_distance()));
		}
		
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                
                if (!editable) {
                	return;
                }
                
                Point p = new Point(mouseX, mouseY);
                //System.out.println(p);
                points.add(p);
                
                Point pt = new Point(mouseX, mouseY);
                
                for (City c: cities) {
                	Point cityPt = new Point(c.getMap_x() + 13, c.getMap_y() + 13);
                	
                	double distance = pt.distance(cityPt);

                	if (distance <= 20) {
                		
						if (SwingUtilities.isRightMouseButton(e)) {
							if ( routeStations.size() > 0) {
								City lastCity = routeStations.get(routeStations.size() - 1).first;
								if (c.getId() == lastCity.getId()) {
									routeStations.remove(routeStations.size() - 1);
									
									if (routeStations.size() > 1) {
										routeStations.get(routeStations.size()-1).second = true;
									}
								} else {
									for(Pair<City, Boolean> pair: routeStations) {
										if (pair.first.getId() == c.getId() && routeStations.get(0).first.getId() != c.getId()) {
											pair.second = false;
											break;
										}
									}
								}
							}
						} else {
							if (routeStations.size() > 0) {
								
								if (inRoute(c)) {
									for(Pair<City, Boolean> pair: routeStations) {
										if (pair.first.getId() == c.getId() && pair.second == false) {
											pair.second = true;
										}
									}
								} else {
									boolean isAdj = false;
		                			for(Edge ed: cityGraph.adj(routeStations.get(routeStations.size()-1).first.getId())) {
		                				if (ed.either() == c.getId() || ed.other(ed.either()) == c.getId()) {
		                					isAdj = true;
		                					break;
		                				}
		                			}
		                			
		                			if (isAdj) {
		                				routeStations.add(new Pair<City, Boolean>(c, true));
		                			}
								}
	                			
	                			
	                		} else {
	                			routeStations.add(new Pair<City, Boolean>(c, true));
	                		}
						}

                		
                		
                		break;
                	} // end if distance

    	        }

                repaint();
            }
        });
		
	}
	
	public Dimension getPreferredSize() {
        return new Dimension(mapWidth,mapHeight);
    }
	
	private void drawInstructions(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.BOLD, 15));
		
		g.drawImage(cityMarker, 10, 10, null);
		g.drawString("Station", 45, 28);
		
		g.drawImage(cityMarkerSelected, 10, 40, null);
		g.drawString("Route point with stop", 45, 58);
		
		g.drawImage(cityMarkerNoStop, 10, 70, null);
		g.drawString("Route point without stop", 45, 88);
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, mapWidth, mapHeight);
        g.setColor(Color.BLACK);
        
        Graphics2D g2d = (Graphics2D)g;
        
        drawInstructions(g);
        
       
        g.setColor(new Color(0, 38, 53));
        g2d.setStroke(new BasicStroke(5));
        //draw edges
        for(Road r: roads) {
        	
        	Point p1 = null, p2 = null;
        	
        	for(City c: cities) {
        		if (c.getId() == r.getCity_one()) {
        			p1 = new Point(c.getMap_x(), c.getMap_y());
        		}
        		
    			if (c.getId() == r.getCity_two()) {
    				p2 = new Point(c.getMap_x(), c.getMap_y());
        		}
        	}
			g.drawLine(p1.x + 13, p1.y + 13, p2.x + 13, p2.y + 13);
        }
        
        
        g.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
        Color cityNameColor = new Color(0, 183, 255);
        
        //draw cities markers
        for (City c: cities) {
        	g.drawImage(cityMarker, c.getMap_x(), c.getMap_y(), null);
        }
         
        g2d.setStroke(new BasicStroke(5));
        
        g.setColor(Color.GREEN);
        if (routeStations.size() == 1) {
        	drawCity(routeStations.get(0), g);
        }
        
        if (routeStations.size() > 1) {
        	for(int i = 0; i < routeStations.size() - 1; ++i) {
	        	City a = routeStations.get(i).first;
	        	City b = routeStations.get(i + 1).first;
	        	
	        	g.drawLine(a.getMap_x() + 13, a.getMap_y() + 13, b.getMap_x() + 13, b.getMap_y() + 13);
	        	
	        	drawCity(routeStations.get(i), g);
	        }
        	
        	drawCity(routeStations.get(routeStations.size() - 1), g);
        }
        
      //draw city names
        for (City c: cities) {
			g.setColor(cityNameColor);
			g.drawString(c.getCity_name(), c.getMap_x() + 35, c.getMap_y() + 30);
        }
	        
	    
	 }
	
	private void drawCity(Pair<City, Boolean> cityPair, Graphics g) {
		City last = cityPair.first;
    	if (cityPair.second == true) {
    		g.drawImage(cityMarkerSelected, last.getMap_x(), last.getMap_y(), null);
    	} else {
    		g.drawImage(cityMarkerNoStop, last.getMap_x(), last.getMap_y(), null);
    	}
	}
	
}
