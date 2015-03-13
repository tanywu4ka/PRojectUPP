package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "trains")
public class Train {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 	id;
	private String 	trainDays;
	private int 	trainHours;
	private int 	trainMinutes;
	private int 	trainRouteId;
	
	public Train() {
		
	}
	
	public Train(boolean [] days, String hours, String mins, int routeId) {
		trainHours = Integer.parseInt(hours);
		trainMinutes = Integer.parseInt(mins);
		trainRouteId = routeId;
		
		String res = "";
		for(int i = 0; i < days.length; ++i) {
			res += days[i];
			if (i != days.length - 1) {
				res += ";";
			}
		}
		trainDays = res;
	}

	public int getTrainHours() {
		return trainHours;
	}

	public void setTrainHours(int trainHours) {
		this.trainHours = trainHours;
	}

	public int getTrainMinutes() {
		return trainMinutes;
	}

	public void setTrainMinutes(int trainMinutes) {
		this.trainMinutes = trainMinutes;
	}

	public int getTrainRouteId() {
		return trainRouteId;
	}

	public void setTrainRouteId(int trainRouteId) {
		this.trainRouteId = trainRouteId;
	}

	public int getId() {
		return id;
	}

	public String getTrainDays() {
		return trainDays;
	}
	
	
	
}
