package epamlab.controllers;

import java.util.List;

import org.apache.log4j.Logger;

import epamlab.constant.ConstantElevator;
import epamlab.container.Elevator;
import epamlab.container.Floor;
import epamlab.people.Passenger;
import epamlab.swing.MessageDailog;

public class SwingController extends AbstractController{
	private static final Logger LOG=Logger.getLogger(SwingController.class);	
	
	
	public SwingController(List<Floor> floors) {
		currentFloor = 1;
		elevator = new Elevator();
		upDirection = true;
		this.floors = floors;

	}
	@Override
	protected void elevatorUp() {
		 liftY-=200;
			currentFloor++;
			LOG.info("Moving ELEVATOR from story="+(currentFloor-1)+" to story="+currentFloor);
			
				MessageDailog.writeMessage("Moving ELEVATOR from story="+(currentFloor-1)+" to story="+currentFloor);
			
		
	}

	@Override
	protected void elevatorDown() {
		 liftY+=200;
			currentFloor--;
			LOG.info("Moving ELEVATOR from story="+(currentFloor+1)+" to story="+currentFloor);
			
				MessageDailog.writeMessage("Moving ELEVATOR from story="+(currentFloor+1)+" to story="+currentFloor);
			
		
	}

	@Override
	public void peopleGoingLIft(int destenationStory, Passenger passenger) {
		
			waitUpdateImageGoneInto(passenger);
		
		synchronized (floors.get(currentFloor - 1).getDispatchStoryContainer()) {
					
					
					floors.get(currentFloor - 1).getDispatchStoryContainer()
							.remove(passenger);
					elevator.getElevatorContainer().add(passenger);
					
				
			        elevator.getCountMovingInto();
		}
		
		
		
		synchronized (elevator) {
			goneInto = true;
			
			elevator.notify();
			
		}
		synchronized (floors.get(destenationStory - 1)
				.getArrivalStoryContainer()) {
			try {
			
				floors.get(destenationStory - 1).getArrivalStoryContainer().wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

		}
		
	}

	@Override
	public void peopleGoingHome(int destenationStory, Passenger passenger) {
		
			synchronized(waiting){
				waitUpdatePainGoneOut(passenger);
				
			}
		
		
		synchronized (elevator.getElevatorContainer()) {
					
					elevator.getElevatorContainer().remove(passenger);
					floors.get(destenationStory - 1).getArrivalStoryContainer().add(passenger);
					
					
					
					elevator.upCountGoingOut();
		}
		
		
		
		synchronized (elevator) {
			goneOut = true;
			
			elevator.notify();
			
		}
		
	}
		
	

	@Override
	public void setCreaedPassangers() {
		synchronized (waiting) {
			createdPassangers++;
			
			if (createdPassangers == ConstantElevator.passengersNumber) {
				ready = true;
				
				
				if (ConstantElevator.animationBoost==0) {
					synchronized(this){
						this.notify();	 
						}
				}
				waiting.notify();
			}
		}
		
	}
	
	
	private void waitUpdateImageGoneInto(Passenger passenger){
		   
		   synchronized(floors){
			   if(!wasPaint){
				   try {
					floors.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			   LOG.info(" BOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
			   MessageDailog.writeMessage(" BOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
			   
			   for(double i=0;i>-(200+passenger.getPassengerId()*10);i--){
				   i=i-ConstantElevator.animationBoost;
				   
				   passenger.setX((int)i);
				   try {
					floors.wait();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			   } 
			   
		   }
		
		   wasPaint=false;
		   
	   }
	private void waitUpdatePainGoneOut(Passenger passenger){
		  synchronized(floors){
			   if(!wasPaintOut){
				   try {
					floors.wait();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			   }
			   LOG.info(" DEBOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
			   MessageDailog.writeMessage(" DEBOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
			   for(int i=1;i<580;i++){
				  i+=ConstantElevator.animationBoost;
				   passenger.setX(i);
				   try {
					floors.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			   } 
			   
		   }
		
		   wasPaintOut=false;
		  
	  }
	@Override
	public void validation() {
		LOG.info("Start validation  \n");
		int numbeArrived=0;
			MessageDailog.writeMessage("Start validation  \n");
		
		for (Floor f : floors) {
			LOG.info("check floor depart : \n Number floor: " + f.getIdFloor()+ " is empty?  " + f.getDispatchStoryContainer().isEmpty()
					+ "\n");
			
				MessageDailog.writeMessage("check floor depart : Number floor: " + f.getIdFloor()+ " is empty?  " + f.getDispatchStoryContainer().isEmpty()
					+ "\n");
			
			for (Passenger p : f.getArrivalStoryContainer()) {
				LOG.info("check floor arrive: Number passenger: "
						+ p.getPassengerId()
						+ " is finish story equals number floor ?: "
						+ (f.getIdFloor() == p.getDestinationStory()) + "\n");
				
					MessageDailog.writeMessage("check floor arrive: Number passenger: "
						+ p.getPassengerId()
						+ " is finish story equals number floor ?: "
						+ (f.getIdFloor() == p.getDestinationStory()) + "\n");
						numbeArrived++;	
				
			}
		}
		LOG.info("it was created passengers : "
				+ createdPassangers+ " it was need created: "
				+ ConstantElevator.passengersNumber + "\n");
		
			MessageDailog.writeMessage("it was created passengers : "
					+ createdPassangers+ " it was need created: "
					+ ConstantElevator.passengersNumber + "\n");
			
			LOG.info("it WAS CREATED PASSENGERS: "
					+  createdPassangers + " IT WAS DEBOADING: "
					+  numbeArrived + "\n");
		
			MessageDailog.writeMessage("it WAS CREATED PASSENGERS: "
					+  createdPassangers + " IT WAS DEBOADING: "
					+  numbeArrived + "\n");
			
		
		
	}
}
