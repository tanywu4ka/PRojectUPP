package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "roads")
public class Road {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 	id;
	private int 	city_one;
	private int 	city_two;
	private int 	road_distance;
	
	public Road() {
		
	}

	public int getId() {
		return id;
	}

	public int getCity_one() {
		return city_one;
	}

	public int getCity_two() {
		return city_two;
	}

	public int getRoad_distance() {
		return road_distance;
	}

	@Override
	public String toString() {
		return "From: " + city_one + " to: " + city_two + ", distance: " + road_distance;
	}
	
	
}
