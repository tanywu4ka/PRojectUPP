package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "route_names")
public class RouteName {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 	id;
	private String route_name;
	
	public RouteName() {
		
	}
	
	public RouteName(String name) {
		route_name = name;
	}

	public int getId() {
		return id;
	}

	public String getRoute_name() {
		return route_name;
	}

	@Override
	public String toString() {
		return "Route: id: " + id + ", name: " + route_name;
	}
	
	

}
