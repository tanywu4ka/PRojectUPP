package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "cities")
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 		id;
	private String 		city_name;
	private int 		map_x;
	private int 		map_y;
	
	public City() {
		
	}

	public int getId() {
		return id;
	}

	public String getCity_name() {
		return city_name;
	}

	public int getMap_x() {
		return map_x;
	}

	public int getMap_y() {
		return map_y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "City: " + city_name;
	}
	
	

}
