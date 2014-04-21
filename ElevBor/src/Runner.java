

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import epamlab.Runnable.TransportationTask;
import epamlab.constant.ConstantElevator;

import epamlab.container.Floor;
import epamlab.controllers.AbstractController;
import epamlab.controllers.ConsoleController;
import epamlab.controllers.SwingController;
import epamlab.factory.FloorFactory;
import epamlab.factory.PassengerFactory;

import epamlab.people.Passenger;
import epamlab.reader.ReaderConfig;

import epamlab.swing.MainWindows;


public class Runner {
	private static final Logger LOG=Logger.getLogger(Runner.class);
	
	public static void main(String[] args) {
		ResourceBundle resourceBundle=ResourceBundle.getBundle("config");
		ReaderConfig read=new ReaderConfig(resourceBundle);
		ConstantElevator.storiesNumber=read.getStoriesNumber();
		ConstantElevator.elevatorCapacity=read.getElevatorCapacity();
		ConstantElevator.passengersNumber=read.getPassengersNumber();
		ConstantElevator.animationBoost=read.getAnimationBoost();
		List <TransportationTask>  transportationTask=new ArrayList<TransportationTask>();
		/* created  floors from factory*/
		int i=1;
		List<Floor> floors=new ArrayList<Floor>();
		FloorFactory floorFactory=new FloorFactory();
		while (i<ConstantElevator.storiesNumber+1) {
			floors.add(floorFactory.getClassFromFactory(i));
			i++;
		}
		
		/* set passengers on the floor */
		int j=0;
	
		AbstractController controller;
		if (ConstantElevator.animationBoost==0) {
			 controller=new ConsoleController(floors);
		}else{
			 controller=new SwingController(floors);
		}
		
		
		PassengerFactory passFactory=new PassengerFactory();
		while (j<ConstantElevator.passengersNumber) {
			Passenger pass=passFactory.getClassFromFactory(ConstantElevator.storiesNumber, j);
			for (Floor floor : floors) {
				if (floor.getIdFloor()==pass.getStartStory()) {
					floor.addPassenger(pass);
					
				}
			}
			transportationTask.add(new TransportationTask(pass,controller));
			j++;
		}
		controller.getElevator().setElevatorCapacity(ConstantElevator.elevatorCapacity);
		/* check ending transportation and move elevator*/
		
		if(controller.getCreatedPassangers()!=ConstantElevator.passengersNumber){
			     controller.Ready();
		}
		 if (ConstantElevator.animationBoost!=0) {
			 MainWindows window=new  MainWindows(floors,controller,transportationTask);
		}
	
		 if (ConstantElevator.animationBoost==0) {
				LOG.info("START_TRANSPORTATION" );
			}
		 if (ConstantElevator.animationBoost!=0) {
			 synchronized(controller){
					try {
						controller.wait();
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
				}
		}
	
		if (ConstantElevator.animationBoost!=0) {
			while (controller.getElevator().getCountGoingOut()!=ConstantElevator.passengersNumber && !"ABORTED".equals(TransportationTask.getHandling())) {
				
				controller.movingElevator();
				
				
			}

		}else{
			while (controller.getElevator().getCountGoingOut()!=ConstantElevator.passengersNumber ) {
				
				controller.movingElevator();
				
				
			}
		}
		
		
		if (ConstantElevator.animationBoost==0) {
			LOG.info("COMPLETION_TRANSPORTATION" );
			controller.validation();
		}
	}
	
}
