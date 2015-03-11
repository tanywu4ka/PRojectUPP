package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;


@Entity
@Table(name = "route_stops")
public class RouteStop {
	
	@OrderBy("order_id")
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 	id;
	private int 	route_id;
	private int 	order_id;
	private int 	city_id;
	private boolean is_stop;
	
	public RouteStop() {
		
	}
	
	public RouteStop(int routeId, int orderId, int cityId, boolean isStop) {
		route_id = routeId;
		order_id = orderId;
		city_id = cityId;
		is_stop = isStop;
	}

	public int getId() {
		return id;
	}

	public int getRoute_id() {
		return route_id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public int getCity_id() {
		return city_id;
	}

	public boolean isIs_stop() {
		return is_stop;
	}
	
	

}
