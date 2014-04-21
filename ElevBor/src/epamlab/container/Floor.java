package epamlab.container;

import java.util.ArrayList;
import java.util.List;


import epamlab.people.Passenger;

public class Floor {
	private List<Passenger> dispatchStoryContainer=new ArrayList<Passenger>();
	private List<Passenger> arrivalStoryContainer=new ArrayList<Passenger>();
	private boolean ready;
	private final int idFloor;
	public Floor(int idFloor) {
		this.idFloor=idFloor;
	}
	public int getIdFloor() {
		return idFloor;
	}
	public List<Passenger> getArrivalStoryContainer() {
		return arrivalStoryContainer;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public List<Passenger> getDispatchStoryContainer() {
		return dispatchStoryContainer;
	}
	
	
	
	@Override
	public String toString() {
		return "Floor [passengers=" + dispatchStoryContainer + ", idFloor=" + idFloor + "]";
	}
	public void addPassenger(Passenger pass) {
		dispatchStoryContainer.add(pass);
	}
	
}
