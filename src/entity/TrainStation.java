package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "trainStations")
public class TrainStation {
	
	@OrderBy("orderId")
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int orderId;
	private int stationHours;
	private int stationMinutes;
	private int stationCost;
	private int stationTrainId;
	private int cityId;
	
	public TrainStation() {
		
	}
	
	public TrainStation(int order, String hour, String min, String cost, int trainId, int cId) {
		orderId = order;
		stationHours = Integer.parseInt(hour);
		stationMinutes = Integer.parseInt(min);
		stationCost = Integer.parseInt(cost);
		stationTrainId = trainId;
		cityId = cId;
	}

	public int getId() {
		return id;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getStationHours() {
		return stationHours;
	}

	public int getStationMinutes() {
		return stationMinutes;
	}

	public int getStationCost() {
		return stationCost;
	}

	public int getStationTrainId() {
		return stationTrainId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	

}
