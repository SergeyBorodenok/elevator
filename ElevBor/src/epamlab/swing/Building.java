package epamlab.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JComponent;


import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;



import org.apache.log4j.Logger;

import epamlab.constant.ConstantElevator;

import epamlab.container.Floor;
import epamlab.controllers.AbstractController;

import epamlab.people.Passenger;


public class Building extends JComponent implements ActionListener {

private static final Logger LOG=Logger.getLogger(Building.class);
Timer buildintimer=new Timer(40, this);
//private Elevator elevator;
private AbstractController controller;
private List <Floor> floors=new ArrayList<Floor>();
private JButton start;
private boolean completionTransportation=false;

 Image imgBuilding=new ImageIcon("src/Resurces/Pictures/Thitrh.png").getImage();


Graphics g;
int y=200;

public Building(List <Floor> floors,AbstractController controller2,JButton start) {
	super();
	
	this.floors=floors;
	this.controller=controller2;
	this.start=start;
	buildintimer.start();
	setLayout(new BorderLayout());
	
	setPreferredSize(new Dimension(800, (ConstantElevator.storiesNumber*204+100)));
	
	
	
	
	
	
	
    
}

public void paint(Graphics g){
	g=(Graphics2D)g;
	int i=0;
	for( ;i!=ConstantElevator.storiesNumber;i++){	
	
		g.drawImage(imgBuilding,0,y*i,null);		
	
	
  
	}
	
	
	if(!controller.isFloorEmpty()|| !controller.getElevator().getElevatorContainer().isEmpty()){
	    g.drawImage(controller.getElevator().imgLiftOpen,0,controller.liftY,null );
	   }else if(!controller.isFloorEmpty()|| !controller.getElevator().getElevatorContainer().isEmpty()){
		g.drawImage(controller.getElevator().imgLiftClose,0,controller.liftY,null );
	   }
	    
	synchronized (controller.getElevator().getElevatorContainer()) {
			
			
			for(Passenger p:controller.getElevator().getElevatorContainer()){
				
			
					
				 if(p.getDestinationStory()==controller.getCurentLeve()) { 
				 
					 g.drawImage(p.imgPassenArrived,((260+p.getPassengerId())-250)+p.getX(),ConstantElevator.storiesNumber*200-(200*p.getDestinationStory()),100,159,null);
				 
				
				 }
				 
			}
	   }
	   
	    for(Floor f:floors){
			synchronized(f.getDispatchStoryContainer()){
			for(Passenger p :f.getDispatchStoryContainer()){
				
				g.drawImage(p.imgPassenger,(230+p.getPassengerId()*10)+p.getX(),(ConstantElevator.storiesNumber*200)-(200*p.getStartStory()),120,180,null);
				
				 		
				}
			}		
			
		}
	    
	    
	    
	
	 
	  
	
    
}

//@Override
public void actionPerformed(ActionEvent event) {
		
		if(controller.getElevator().getCountGoingOut()==ConstantElevator.passengersNumber && !completionTransportation){
			LOG.info("COMPLETION_TRANSPORTATION");
			controller.validation();
			start.setText("VIEW LOG FILE");
			completionTransportation=true;
		}
		controller.botherConsol();
		controller.botherConsolOut();
		repaint();
		
	
		
	
}



	
}
