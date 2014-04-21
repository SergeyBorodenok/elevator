package epamlab.reader;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class ReaderConfig {
	private int storiesNumber;
	private int elevatorCapacity;
	private int passengersNumber;
	private int animationBoost;
	
	/* Start read config file and find data*/
	public ReaderConfig(ResourceBundle resourceBundle){
		Enumeration<String> fileContext=resourceBundle.getKeys();
		while (fileContext.hasMoreElements()) {
			String element = (String) fileContext.nextElement();
			if ("storiesNumber".equals(element)) {
				storiesNumber=Integer.valueOf(resourceBundle.getString(element));
			}
			else if("elevatorCapacity".equals(element)) {
				elevatorCapacity=Integer.valueOf(resourceBundle.getString(element));
			}else if ("passengersNumber".equals(element)) {
				passengersNumber=Integer.valueOf(resourceBundle.getString(element));
			}else if("animationBoost".equals(element)){
				animationBoost=Integer.valueOf(resourceBundle.getString(element));
			}else{
				System.err.println("Problem with resource file");
			}
		}
		
	}
	public int getAnimationBoost() {
		return animationBoost;
	}
	public void setAnimationBoost(int animationBoost) {
		this.animationBoost = animationBoost;
	}
	public int getStoriesNumber() {
		return storiesNumber;
	}
	public int getElevatorCapacity() {
		return elevatorCapacity;
	}
	public int getPassengersNumber() {
		return passengersNumber;
	}
	
}
