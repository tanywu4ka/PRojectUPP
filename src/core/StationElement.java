package core;

import javax.swing.JComboBox;

import view.NumTextField;

public class StationElement {
	private JComboBox hours;
	private JComboBox mins;
	private NumTextField cost;
	private int order;
	private int cityId;
	
	public StationElement() {
		
	}


	public JComboBox getHours() {
		return hours;
	}


	public void setHours(JComboBox hours) {
		this.hours = hours;
	}


	public JComboBox getMins() {
		return mins;
	}


	public void setMins(JComboBox mins) {
		this.mins = mins;
	}


	public NumTextField getCost() {
		return cost;
	}


	public void setCost(NumTextField cost) {
		this.cost = cost;
	}


	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	public int getCityId() {
		return cityId;
	}


	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	
	
	
}