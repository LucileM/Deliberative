package template;

import java.util.List;
import logist.topology.Topology.City;

public class State {
	City positionVehicle;
	List<City> positionTaks;
	List<Boolean> isDeliver;
	
	public State(City vehicleCurrentCity, List<City> positionTaks, List<Boolean> isDeliver) {
		this.positionVehicle = vehicleCurrentCity;
		this.positionTaks = positionTaks;
		this.isDeliver = isDeliver;
	}
	
	public State(){}
}

