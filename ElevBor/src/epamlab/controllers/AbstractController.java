package epamlab.controllers;

import java.util.List;




import epamlab.constant.ConstantElevator;
import epamlab.container.Elevator;
import epamlab.container.Floor;
import epamlab.people.Passenger;


public abstract class AbstractController {
	
	private Floor curFloor;
	protected List<Floor> floors;	
	protected int currentFloor;
	protected Elevator elevator;

	protected boolean upDirection;
	private boolean openDoor=false;
	private boolean isLevelEmpty = false;
	private boolean isElevatorEmpty = true;
	protected boolean ready = false;	
	protected boolean goneInto = false;
	protected boolean goneOut = false;
	protected int createdPassangers = 0;
	protected Object waiting = new Object();
	public int liftY=(ConstantElevator.storiesNumber*200)-200;
	protected boolean wasPaint=false;
	protected boolean wasPaintOut=false;
	
	
	public Elevator getElevator() {
		return elevator;
	}

	public void setElevator(Elevator elevator) {
		this.elevator = elevator;
	}

	public boolean getOpenDoor(){
		return openDoor;
	}
	
	public List<Floor> getLevels() {

		return floors;
	}
		
		/** waitControllerPriedet   
		 * wait until elevator arrived on floor 
		 * 
		 * check on moving up direction
		 * 
		 * wait on start floor up direction
		 * 
		 * check on moving down direction
		 * 
		 * wait on start floor down direction
		 * 
		 * */
		
		public void waitControllerArrived(int startStory, int destenitionStory) {
			synchronized (floors.get(startStory - 1).getDispatchStoryContainer()) {
				
				try {
					floors.get(startStory - 1).getDispatchStoryContainer().wait();		

				} catch (InterruptedException e) {
					System.err.println("Thread was interruped ");
				}
			}
			
			if(startStory>destenitionStory && upDirection){
				synchronized(elevator){			
						goneInto = true;
						elevator.notify();
						
								
				}
				
				synchronized(floors.get(startStory - 1).getDispatchStoryContainer()){
				try {
					
					floors.get(startStory - 1).getDispatchStoryContainer().wait();
				} catch (InterruptedException e) {
					
					System.err.println("Thread was interruped ");
				}
				}
			}
			
			if(startStory<destenitionStory && !upDirection){
				synchronized(elevator){			
						goneInto = true;
						
						elevator.notify();
					
								
				}
				
				
				synchronized(floors.get(startStory - 1).getDispatchStoryContainer()){
				try {
					
					floors.get(startStory - 1).getDispatchStoryContainer().wait();
				} catch (InterruptedException e) {
					
					System.err.println("Thread was interruped ");
				}
				}
			}

		}

		
		/**
		 * get up passenger on the floor
		 * */
		
		public void botherOnLevel() {
			
				
				while (poolGoInto()) {
					synchronized (floors.get(currentFloor - 1).getDispatchStoryContainer()) {
						
					floors.get(currentFloor - 1).getDispatchStoryContainer()
							.notify();
		        
					}
				
		         
					moveInto();
				
				
				
				
			}

		}

		
		/**
		 * wait why people don't move
		 * */
		public void moveInto() {
			synchronized (elevator) {
				while (!goneInto) {
					try {
						elevator.wait();
					} catch (InterruptedException e) {
						
						System.err.println("Thread was interruped ");
					}
				}
			}
			
			
			goneInto=false;
			
		}

		
		
		
		
		
		/**
		 * Wake up people on the floor
		 * */
		public void botherOnLift(){
			while(poolGoOut()){
				synchronized(floors.get(currentFloor-1).getArrivalStoryContainer()){
					floors.get(currentFloor-1).getArrivalStoryContainer().notifyAll();
					openDoor=true;
				}
				moveOut();
			}
			
		}
		
		
		
		/** wait while people go out */
		public void moveOut() {
			synchronized (elevator) {
				while (!goneOut) {
					openDoor=true;
					try {
						elevator.wait();
					} catch (InterruptedException e) {
						System.err.println("Thread was interruped ");
					}
				}
			}
			goneOut=false;
			
		}
		
		public synchronized Object getObgect() {
			return waiting;
		}

		public int getCreatedPassangers() {

			return createdPassangers;
		}
		
		
		
		
		
		/**
		 * wait ready to exit
		 * */
		public void Ready() {
			synchronized (waiting) {
				while (!ready) {
					try {
						waiting.wait();
					} catch (InterruptedException e) {
						
						System.err.println("Thread was interruped ");
					}
				}
			}
			ready = false;
		}
		
		
		
		/**
		 * somebody want enter elevator
		 * */
		public boolean poolGoInto() {

			synchronized (floors.get(currentFloor - 1).getDispatchStoryContainer()) {
				for (Passenger passenger : floors.get(currentFloor - 1)
						.getDispatchStoryContainer()) {

					if (currentFloor == passenger.getStartStory() &&passenger.getStartStory()<passenger.getDestinationStory()&& upDirection && elevator.getElevatorContainer().size()<elevator.getElevatorCapacity()) {
					
						return true;
					}else if (currentFloor == passenger.getStartStory() &&passenger.getStartStory()>passenger.getDestinationStory()&& !upDirection && elevator.getElevatorContainer().size()<elevator.getElevatorCapacity()){
						
						return true;
					}


				}
			}

			return false;
		}

		/** 
		 * somebody want exit elevator
		 * */
		public  boolean poolGoOut() {

			 synchronized(floors.get(currentFloor-1).getArrivalStoryContainer()){

			for (Passenger passenger : elevator.getElevatorContainer()) {

				if (currentFloor == passenger.getDestinationStory()
						&& !elevator.getElevatorContainer().isEmpty()) {
					
					return true;
				}

			}

			}
			return false;
		}
		
		public int getCurentLeve(){
			return currentFloor;
		}
		
		
		/** moving Elevator on the floor*/
		public void movingElevator() {
			if (upDirection) {
				if (hasElevatorUp()) {
					if (isElevatorEmpty() && isFloorEmpty()) {
						
						elevatorUp();
						return;
					}
					if (!isElevatorEmpty()) {
						botherOnLift();
						
					}
					if (!isFloorEmpty()
							&& elevator.getElevatorContainer().size() < elevator
									.getElevatorCapacity()) {
						botherOnLevel();
					}
					elevatorUp();
				
					return;
				}
			} else if (!upDirection) {
				if (hasElevatorDown()) {
					if (isElevatorEmpty() && isFloorEmpty()) {
						elevatorDown();
						
						return;
					}
					if (!isElevatorEmpty()) {
						
						
						
						
						botherOnLift();
						
					}
					if (!isFloorEmpty()) {
						
						botherOnLevel();
						
					}
					elevatorDown();
				
					return;

				}
				
			}
		}
		public synchronized Floor getFloor() {
			curFloor = floors.get(currentFloor - 1);

			return curFloor;
		}
		public synchronized boolean getIsEmptyElevator() {

			return isElevatorEmpty;

		}

		public synchronized boolean getIsEmptyLevel() {

			return isLevelEmpty;
		}

		public boolean isElevatorEmpty() {
			
			if (elevator.getElevatorContainer().isEmpty()) {
				isElevatorEmpty = true;
			} else {

				isElevatorEmpty = false;
			}

			return isElevatorEmpty;

		}

		public boolean isFloorEmpty() {
		
			curFloor = getFloor();
			if (curFloor.getDispatchStoryContainer().isEmpty()) {
				isLevelEmpty = true;
			} else {

				isLevelEmpty = false;
			}

			return isLevelEmpty;

		}
		
		private boolean hasElevatorUp() {
		
			if (currentFloor != ConstantElevator.storiesNumber && currentFloor < ConstantElevator.storiesNumber) {
				openDoor=true;
				return true;
			}
			upDirection = false;
			return false;
		}

		private boolean hasElevatorDown() {
		
			if (currentFloor >1) {
				return true;
			}
			openDoor=true;
			upDirection = true;
			return false;
		}
		
		  public void botherConsol(){
			  synchronized(floors){
				  wasPaint=true;
				  floors.notify();
			  }
		  } 
		  
		  public void botherConsolOut(){
			  synchronized(floors){
				  wasPaintOut=true;
				  floors.notify();
			  }
		  }
		  /*
			 * The method do validate when processing was successful or was aborted.
			 * The method input results of validation into log file or if animationBoots
			 * bigger null type result on messagDialog too.
			 */
		  
		  
			
		protected abstract void elevatorUp();
		protected abstract void elevatorDown();
		
		/** people get going go to the elevator*/
		public abstract void peopleGoingLIft(int destenationStory,Passenger passenger);
		
		/**
		 * people wake up and go on the floor
		 * */
		
		public  abstract void  peopleGoingHome(int destenationStory,Passenger passenger);
		
		public abstract void setCreaedPassangers();
		/**
		 * validation end of transportation 
		 * */
		public abstract void validation();
}
