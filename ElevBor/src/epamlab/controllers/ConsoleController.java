package epamlab.controllers;

import java.util.List;

import org.apache.log4j.Logger;








import epamlab.constant.ConstantElevator;
import epamlab.container.Elevator;
import epamlab.container.Floor;
import epamlab.people.Passenger;


public class ConsoleController extends AbstractController{
		
	private static final Logger LOG=Logger.getLogger(ConsoleController.class);
	
	
	public ConsoleController(List<Floor> floors) {
		currentFloor = 1;
		elevator = new Elevator();
		upDirection = true;
		this.floors = floors;

	}
	@Override
	protected void elevatorUp() {
		currentFloor++;
		System.out.println("Moving ELEVATOR from story="+(currentFloor-1)+" to story="+currentFloor);
		LOG.info("Moving ELEVATOR from story="+(currentFloor-1)+" to story="+currentFloor);
		
	}

	@Override
	protected void elevatorDown() {
		currentFloor--;
		System.out.println("Moving ELEVATOR from story="+(currentFloor-1)+" to story="+currentFloor);
		LOG.info("Moving ELEVATOR from story="+(currentFloor+1)+" to story="+currentFloor);
		
	}

	@Override
	public void peopleGoingLIft(int destenationStory, Passenger passenger) {
		synchronized (floors.get(currentFloor - 1).getDispatchStoryContainer()) {
			
			
			floors.get(currentFloor - 1).getDispatchStoryContainer().remove(passenger);
			elevator.getElevatorContainer().add(passenger);
				System.out.println(" BOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
				LOG.info(" BOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
			}
		
	        elevator.getCountMovingInto();




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
				synchronized (elevator.getElevatorContainer()) {
						elevator.getElevatorContainer().remove(passenger);
						floors.get(destenationStory - 1).getArrivalStoryContainer().add(passenger);
							
							System.out.println(" DEBOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
							LOG.info(" DEBOADING_OF_PASSENGER  passangerID="+passenger.getPassengerId()+" on story"+currentFloor);
						
						
						
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
					
					
					
						synchronized(this){
							this.notify();	 
							}
					
					waiting.notify();
				}
			}
		
		}
		@Override
		public void validation() {
			LOG.info("Start validation  \n");
			
			System.out.println("Start validation \n");
			int numbeArrived=0;
			for (Floor f : floors) {
				LOG.info("check floor depart : Number floor: " + f.getIdFloor()+ " is empty?  " + f.getDispatchStoryContainer().isEmpty()
						+ "\n");
				System.out.println("check floor : Number floor: " + f.getIdFloor()+ " is empty?  " + f.getDispatchStoryContainer().isEmpty()
						+ "\n");
				
				for (Passenger p : f.getArrivalStoryContainer()) {
					LOG.info("check floor arrive: Number floor: "
							+ f.getIdFloor()
							+ " is finish story equals number floor ?: "
							+ (f.getIdFloor() == p.getDestinationStory()) + "\n");
					System.out.println("check floor arrive: Number floor: "
							+ f.getIdFloor()
							+ " is finish story equals number floor ?: "
							+ (f.getIdFloor() == p.getDestinationStory()) + "\n");
						numbeArrived++;
				}
			}
			
			
			LOG.info("it was created passengers : "
					+ createdPassangers+ " it was need created: "
					+ ConstantElevator.passengersNumber + "\n");
			System.out.println("it was created passengers : "
					+ createdPassangers+ " it was need created: "
					+ ConstantElevator.passengersNumber + "\n");
			
			LOG.info("it WAS CREATED PASSENGERS: "
						+  createdPassangers + " IT WAS DEBOADING: "
						+ numbeArrived + "\n");
			System.out.println("it WAS CREATED PASSENGERS: "
						+  createdPassangers + " IT WAS DEBOADING: "
						+ numbeArrived + "\n");
			
			
		}
		
		

}
