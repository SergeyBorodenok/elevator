package epamlab.people;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;


import epamlab.constant.TransportationState;

public class Passenger {
	private int startStory=0;
	private int destinationStory=0;
	private int passengerId;
	private TransportationState transportState1;
	public int x=0;
	public int y=0;
	
	
	public  Image imgPassenger=new ImageIcon("src/Resurces/Pictures/fairy20.gif").getImage();
	public  Image imgPassenArrived=new ImageIcon("src/Resurces/Pictures/ForElevator1.gif").getImage();
	
	public Passenger(){	
		
	}
	
	public Passenger(int maxFloor,int passengerId) {
		transportState1=TransportationState.NOT_STARTED;
		
		Random rand = new Random();
		this.passengerId=passengerId;
		while (startStory==destinationStory) {
		this.startStory=rand.nextInt(maxFloor)+1;
		this.destinationStory=rand.nextInt(maxFloor)+1;
		}
	
	}
    public void setX(int x){
		
		this.x=x;
	}
	public int getX(){
		
		return x;
	}
	
	
	
	
	
		
	
	
	public TransportationState getTransportState1() {
		return transportState1;
	}

	public void setTransportState1(TransportationState transportState1) {
		this.transportState1 = transportState1;
	}

	public int getStartStory() {
		return startStory;
	}
	
	public int getDestinationStory() {
		return destinationStory;
	}
	public int getPassengerId() {
		return passengerId;
	}
	@Override
	public String toString() {
		return "|Passenger :startStory: " + startStory + "; destinationStory: "
				+ destinationStory + "; passengerId: " + passengerId + ";|";
	}
	
}
