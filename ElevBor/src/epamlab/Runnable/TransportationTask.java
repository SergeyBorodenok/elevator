package epamlab.Runnable;

import org.apache.log4j.Logger;


import epamlab.constant.TransportationState;

import epamlab.controllers.AbstractController;

import epamlab.people.Passenger;


public class TransportationTask implements Runnable {
	
    private Passenger passenger;
   private AbstractController controller;
   private static String handling="";
   private Thread tranportationTask;
	
	
	
	public TransportationTask(Passenger passenger,AbstractController controller2) {
		super();
		this.passenger = passenger;
		this.controller = controller2;
		tranportationTask=new Thread(this);
		tranportationTask.start();
	}

	


	@Override
	public void run() {
		
		Thread th=Thread.currentThread();
		if (th==tranportationTask) {
		//Do create and last Passenger message the controller to start; 
		controller.setCreaedPassangers();
		passenger.setTransportState1(TransportationState.IN_PROGRESS);
		//Do sleep on the level before controller not bother;
		
		
			controller.waitControllerArrived(passenger.getStartStory(),passenger.getDestinationStory());
		
	
		
		
		//Do sleep on the Lift before controller not bother;
		controller.peopleGoingLIft(passenger.getDestinationStory(),passenger);
		
		//Do go on the level and bother Lift;
		
		controller.peopleGoingHome(passenger.getDestinationStory(),passenger);
		passenger.setTransportState1(TransportationState.COMPLETED);
	
		
		}
		
		}
		
	
	
	
	
	public static String getHandling(){
		
		return handling;
	}
	
	public Passenger getPassenger(){
		return passenger;
	}
	public void stop() {
		handling = "ABORTED";	
		if(tranportationTask.isAlive()){
		if (!tranportationTask.isInterrupted()) {
			 
			
			passenger.setTransportState1(TransportationState.ABORTED);
			tranportationTask.stop();
			tranportationTask.interrupt();
			//tranportationTask=null;
		}
		}
	}
}
