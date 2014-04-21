package epamlab.factory;



import epamlab.people.Passenger;

public class PassengerFactory {
	public Passenger getClassFromFactory(int maxFloor,int idPassenger){
        return new Passenger(maxFloor,idPassenger);
    }
}
