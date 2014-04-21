package epamlab.factory;



import epamlab.container.Floor;



public class FloorFactory {
	public Floor getClassFromFactory(int idFloor){
        return new Floor(idFloor);
    }
		
}
