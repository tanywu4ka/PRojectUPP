package core;

import entity.Train;

public class TrainDays {
	private Train train;
	private boolean [] isDay = new boolean[7];
	
	public TrainDays(Train t) {
		train = t;
		
		String dayString = t.getTrainDays();
		String [] days = dayString.split(";");
		
		for(int i = 0; i < 7; ++i) {
			isDay[i] = Boolean.valueOf(days[i]);
		}
	}
	
	public boolean isOnday(int i) {
		return isDay[i];
	}
	
	public Train getTrain() {
		return train;
	}
}
