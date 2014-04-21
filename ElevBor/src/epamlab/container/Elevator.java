package epamlab.container;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import epamlab.constant.ConstantElevator;
import epamlab.people.Passenger;

public class Elevator {
	private int elevatorCapacity;
	private int elevatorConteinerSi_e;
	private List<Passenger> elevatorContainer=new ArrayList<Passenger>();
    private int countMovingInto=0;
    private int countGoingOut=0;
    public Image imgLiftClose=new ImageIcon("src/Resurces/Pictures/ElevatorClose.png").getImage();
    public  Image imgLiftOpen=new ImageIcon("src/Resurces/Pictures/ElevatorOpen.png").getImage();
    private int LiftSpeed=50;
    public int layer1=0;
    public int layer2=-508;
    public int x=0;
    public int y=(ConstantElevator.storiesNumber*200)-200;
    public boolean up=true;
    public Elevator() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Elevator(int elevatorCapacity, int elevatorConteinerSiE,
			List<Passenger> elevatorContainer, int countMoving) {
		super();
		this.elevatorCapacity = elevatorCapacity;
		elevatorConteinerSi_e = elevatorConteinerSiE;
		this.elevatorContainer = elevatorContainer;
	
	}

	public boolean isUp(){
		if(y>0 && up==true){
			up=true;
			return true;
		}
		up=false;
		return false;
	}
	public boolean isDown(){
		if(y<(ConstantElevator.storiesNumber*200)-200 && up==false){
			up=false;
			return true;
		}
		up=true;
		return false;
	}
	
    public void MoveUp(){  	
    y-=100;
    }
   public void stop(){
	   
	   
   }
    public void MoveDown(){    	
	   y+=100;
	   up=false;
	   layer1+=LiftSpeed;
    }
	
	public synchronized void upCountMoveingInto(){
		
		countMovingInto++;
	} 
	public synchronized void upCountGoingOut(){
		 countGoingOut++;
	}
	
public int getCountGoingOut(){
		
		return  countGoingOut;
	}
	public int getCountMovingInto(){
		
		return countMovingInto;
	}
	public List<Passenger> getElevatorContainer() {
		return elevatorContainer;
	}

	
	public void setElevatorContainer(List<Passenger> elevatorContainer) {
		this.elevatorContainer = elevatorContainer;
	}

	public void setElevatorCapacity(int elevatorCapacity){
	
		this.elevatorCapacity=elevatorCapacity;
	 	
	}
	
	public int getElevatorContenerSi_e(){
		//System.out.println("Размер ли_та "+elevatorContainer.size());
		elevatorConteinerSi_e=elevatorContainer.size();
		return elevatorConteinerSi_e;
	}
	public int getElevatorCapacity(){
		
		return elevatorCapacity;
	}

	@Override
	public String toString() {
		return "Elevator [elevatorContainer=" + elevatorContainer + "]";
	}
	
}
