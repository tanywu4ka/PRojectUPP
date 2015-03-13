package entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wagons")
public class Wagon {
	
	public enum WagonType {
		REGULAR,
		COUPE,
		SW
	}
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int trainId;
	
	@Enumerated(EnumType.STRING)
	private WagonType wagonType;
	

	public Wagon(WagonType type) {
		wagonType = type;
		
		switch (type) {
		case REGULAR: {

		} break;
		
		case COUPE: {
			
		} break;
				
		case SW: {
			
		} break;

		default:
			break;
		}
	}
	
	public Wagon() {
		this(WagonType.REGULAR);
	}

	public WagonType getWagonType() {
		return wagonType;
	}

	public void setWagonType(WagonType wagonType) {
		this.wagonType = wagonType;
	}


	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	

}
